package de.fhac.mazenet.server.userinterface.betterUI;

import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.tools.DebugLevel;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class StreamToTextArea extends OutputStream {

	private final JTextArea textArea;

	private final StringBuilder sb = new StringBuilder();

	private int[] buffer = new int[1];

	private static int count;

	public StreamToTextArea(final JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) {
		// Handelt es sich bei einem Zeichen um ein ASCII-Zeichen wird ein Byte
		// zur Codierung benötig
		// (0 – 127) → 0xxx xxx
		//
		// Handelt es sich um ein anderes Zeichen werden 2 bis 4 Bytes zur
		// Codierung benötigt
		// Das erste Byte beginnt mit einer Anzahl von Einsen, die der Anzahl
		// von zu verwendenden Bytes entspricht, gefolgt von einer 0
		// Jedes folgende Byte beginnt mit 10
		// Beispiel
		// Ein 3 Byte-Code sieht wie folgt aus: 1110 xxxx | 10xx xxxx | 10xx
		// xxxx
		// Damit verbleiben 16 Bits zur Darstellung eines Unicode Zeichens
		// System.out.println(b);// + " als char: " + ((char) b));
		//
		// http://de.wikipedia.org/wiki/UTF-8
		int last8 = b & 0xff;
		if ((last8 & 0xf0) == 0xf0) {
			// 11110xxx -> 3 bit Data 3blocks following
			buffer[0] = (last8 & 7) << (3 * 6);
			count = 2;

		} else if ((last8 & 0xe0) == 0xe0) {
			// 1110xxxx -> 4 bit Data 2block following
			buffer[0] = (last8 & 15) << (2 * 6);
			count = 1;
		} else if ((last8 & 0xc0) == 0xc0) {
			// 110xxxxx -> 5 bit Data 1block following
			buffer[0] = (last8 & 31) << (1 * 6);
			count = 0;

		} else if ((last8 & 0xa0) == 0xa0) {
			// 10xxxxxx -> Datablock (6bit)
			buffer[count] += (last8 & 63) << (6 * count);
			count--;

		} else {
			// 0xxxxxxx -> 7bit Data (ASCII)
			buffer = new int[1];
			buffer[0] = last8;
			count = -1;
		}
		if (count == -1) {
			if (Settings.DEBUGLEVEL.value() >= DebugLevel.DEBUG.value())
				System.out.printf("Unicode Wert: %x  Binär: %8s\n", buffer[0], //$NON-NLS-1$
						Integer.toBinaryString(buffer[0]));
			this.write(buffer, 0, buffer.length);
		}
	}

	public void write(int[] bytes, int offset, int length) {
		// System.out.println("bytes: "+bytes+"offset: "+offset+"length: "+length);
		String s = new String(bytes, offset, length);
		if (s.equals("\r")) //$NON-NLS-1$
			return;
		if (s.equals("\n")) { //$NON-NLS-1$
			final String text = sb.toString() + "\n"; //$NON-NLS-1$
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textArea.append(text);
					textArea.setCaretPosition(textArea.getDocument()
							.getLength());
				}
			});
			sb.setLength(0);
			return;
		}

		sb.append(s);

	}

	public void write2(int b) throws IOException {
		char c = '1';
		if (c == '\r')
			return;

		if (c == '\n') {
			final String text = sb.toString() + "\n"; //$NON-NLS-1$
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textArea.append(text);
					textArea.setCaretPosition(textArea.getDocument()
							.getLength());
				}
			});
			sb.setLength(0);
			return;
		}

		sb.append(c);

	}

	public JTextArea getTextArea() {
		return textArea;
	}

}
