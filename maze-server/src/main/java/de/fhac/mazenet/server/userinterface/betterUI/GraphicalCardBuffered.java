package de.fhac.mazenet.server.userinterface.betterUI;

import de.fhac.mazenet.server.Card;
import de.fhac.mazenet.server.Card.CardShape;
import de.fhac.mazenet.server.Card.Orientation;
import de.fhac.mazenet.server.Messages;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.TreasureType;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GraphicalCardBuffered extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 7583185643671311612L;
	private Image shape;
	private Image treasure;
	private List<Integer> pin;
	private TexturePaint paintBuffer = null;
	private CardShape cardShape;
	private Orientation cardOrientation;
	private TreasureType cardTreasure;
	private int maxSize;
	private int minSize;

	public GraphicalCardBuffered() {
		super();
		// Debuging mit Hintergrundfarbe um Framegröße besser erkennen zu können
		// setBackground(Color.blue);
		loadShape(CardShape.T, Orientation.D0);
		loadTreasure(null);
		loadPins(null);
		addComponentListener(this);
		maxSize = 150;
		minSize = 50;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public void setCard(Card c) {
		loadShape(c.getShape(), c.getOrientation());
		loadTreasure(c.getTreasure());
		loadPins(c.getPin().getPlayerID());
		componentResized(new ComponentEvent(this, -1));
	}

	public void loadShape(CardShape cs, Orientation co) {
		if (cs == this.cardShape && co == this.cardOrientation) {
			return;
		}
		this.cardShape = cs;
		this.cardOrientation = co;
		try {
			URL url = GraphicalCardBuffered.class
					.getResource(Settings.IMAGEPATH + cs.toString() + co.value() + Settings.IMAGEFILEEXTENSION);
			Debug.print(Messages.getString("GraphicalCardBuffered.Load") + url.toString(), DebugLevel.DEBUG); //$NON-NLS-1$
			shape = ImageIO.read(url);

		} catch (IOException e) {
		}
		updatePaint();
	}

	public void loadTreasure(TreasureType t) {
		if (t == this.cardTreasure) {
			return;
		}
		this.cardTreasure = t;
		if (t != null) {
			treasure = ImageResources.getImage(t.value()).getScaledInstance((int) (this.getWidth() * 0.5) ,
					(int) (this.getHeight() * 0.5), Image.SCALE_SMOOTH);
		} else {
			treasure = null;
		}
		updatePaint();
	}

	public void loadPins(List<Integer> list) {
		if (list != null && list.size() != 0)
			pin = list;
		else
			pin = null;
		updatePaint();
	}

	private void updatePaint() {
		int w = 0, h = 0;
		if (shape == null) {
			paintBuffer = null;
			return;
		}
		w = h = Math.min(shape.getWidth(null), shape.getHeight(null));

		if (w <= 0 || h <= 0) {
			paintBuffer = null;
			return;
		}

		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2 = buff.createGraphics();
		if (shape != null) {
			g2.drawImage(shape, 0, 0, null);
		}
		if (treasure != null) {
			int zentrum = h / 2 - treasure.getHeight(null) / 2;
			g2.drawImage(treasure, zentrum, zentrum, null);
		}
		if (pin != null) {
			g2.setColor(Color.BLACK);
			int height = 15;
			int width = 20;
			int zentrum = h / 2 - height / 2;
			g2.fillOval(zentrum, zentrum, height, width);
			char[] number = { (pin.get(0).toString()).charAt(0) };
			g2.drawChars(number, 0, 1, zentrum, zentrum);
		}
		paintBuffer = new TexturePaint(buff, new Rectangle(0, 0, w, h));
		g2.dispose();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (paintBuffer != null) {
			int w = shape.getWidth(null);
			int h = shape.getHeight(null);
			Insets in = getInsets();

			int x = in.left;
			int y = in.top;
			w = w - in.left - in.right;
			h = h - in.top - in.bottom;

			if (w >= 0 && h >= 0) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setPaint(paintBuffer);
				g2.fillRect(x, y, w, h);
			}
		}
	}

	public void blinkCard(long millis, int n) {
		Image save = shape;
		for (int i = 0; i < n; i++) {
			shape = null;
			updatePaint();
			try {
				Thread.sleep(millis / n);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shape = save;
			updatePaint();
		}
	}

	public void blinkPlayer(long millis, int n) {
		List<Integer> save = pin;
		for (int i = 0; i < n; i++) {
			pin = null;
			updatePaint();
			try {
				Thread.sleep(millis / n);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pin = save;
			updatePaint();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// System.out.println("h"+getSize().height + " w" + getSize().width);
		Dimension d = getSize();
		int size = Math.min(maxSize, Math.min(d.height, d.width));
		size = Math.max(minSize, size);
		if (shape != null) {
			Image temp = shape;
			shape = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			shape = temp.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		}
		setSize(size, size);
		updatePaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		componentResized(e);
	}

}