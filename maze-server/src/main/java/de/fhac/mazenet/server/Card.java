package de.fhac.mazenet.server;

import de.fhac.mazenet.server.generated.CardType;
import de.fhac.mazenet.server.generated.TreasureType;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;

import java.util.ArrayList;
import java.util.List;

public class Card extends CardType {
	public enum CardShape {
		L, T, I;
	}

	public enum Orientation {

		D0(0), D90(90), D180(180), D270(270);

		final int value;

		Orientation(int v) {
			value = v;
		}

		public int value() {
			return value;
		}

		public static Orientation fromValue(int v) {
			for (Orientation c : Orientation.values()) {
				if (c.value == v) {
					return c;
				}
			}
			throw new IllegalArgumentException(String.valueOf(v));
		}
	}

	public Card(CardType c) {
		super();
		this.setOpenings(new Openings());
		this.getOpenings().setBottom(c.getOpenings().isBottom());
		this.getOpenings().setLeft(c.getOpenings().isLeft());
		this.getOpenings().setRight(c.getOpenings().isRight());
		this.getOpenings().setTop(c.getOpenings().isTop());
		this.setTreasure(c.getTreasure());
		this.setPin(new Pin());
		if (c.getPin() != null) {
			this.pin.getPlayerID().addAll(c.getPin().getPlayerID());
		} else {
			this.setPin(null);
		}
	}

	//TODO
	public List<Card> getPossibleRotations() {
		List<Card> cards = new ArrayList<Card>();
		// Reihenfolge und fehlende breaks wichtig
		switch (getShape()) {
		case L:
		case T:
			cards.add(new Card(getShape(), Orientation.D180, getTreasure()));
			cards.add(new Card(getShape(), Orientation.D270, getTreasure()));
		case I:
			cards.add(new Card(getShape(), Orientation.D0, getTreasure()));
			cards.add(new Card(getShape(), Orientation.D90, getTreasure()));
			break;
		default:
			System.err.print(Messages.getString("Card.invalidShape")); //$NON-NLS-1$
			break;
		}
		return cards;
	}

	public Card(CardShape shape, Orientation o, TreasureType t) {
		super();
		this.setOpenings(new Openings());
		this.setPin(new Pin());
		this.pin.getPlayerID();
		switch (shape) {
		case I:
			switch (o) {
			case D180:
			case D0:
				this.openings.setBottom(true);
				this.openings.setTop(true);
				this.openings.setLeft(false);
				this.openings.setRight(false);
				break;
			case D270:
			case D90:
				this.openings.setBottom(false);
				this.openings.setTop(false);
				this.openings.setLeft(true);
				this.openings.setRight(true);
				break;
			default:
				// TODO Wrong Rotation
				break;
			}
			break;
		case L:
			switch (o) {
			case D180:
				this.openings.setBottom(true);
				this.openings.setTop(false);
				this.openings.setLeft(true);
				this.openings.setRight(false);
				break;
			case D270:
				this.openings.setBottom(false);
				this.openings.setTop(true);
				this.openings.setLeft(true);
				this.openings.setRight(false);
				break;
			case D90:
				this.openings.setBottom(true);
				this.openings.setTop(false);
				this.openings.setLeft(false);
				this.openings.setRight(true);
				break;
			case D0:
				this.openings.setBottom(false);
				this.openings.setTop(true);
				this.openings.setLeft(false);
				this.openings.setRight(true);
				break;
			default:
				// TODO Wrong Rotation
				break;
			}
			break;
		case T:
			switch (o) {
			case D180:
				this.openings.setBottom(false);
				this.openings.setTop(true);
				this.openings.setLeft(true);
				this.openings.setRight(true);
				break;
			case D270:
				this.openings.setBottom(true);
				this.openings.setTop(true);
				this.openings.setLeft(false);
				this.openings.setRight(true);
				break;
			case D90:
				this.openings.setBottom(true);
				this.openings.setTop(true);
				this.openings.setLeft(true);
				this.openings.setRight(false);
				break;
			case D0:
				this.openings.setBottom(true);
				this.openings.setTop(false);
				this.openings.setLeft(true);
				this.openings.setRight(true);
				break;
			default:
				// TODO Wrong Rotation
				break;
			}
			break;
		default:
			// TODO Wrong Shape

		}
		this.treasure = t;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Codeoptimierung
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Card other = new Card((CardType) obj);
		if (this.treasure != other.getTreasure()) {
			Debug.print(
					Messages.getString("Card.treasureUnequal"), DebugLevel.DEFAULT); //$NON-NLS-1$
			return false;
		}
		for (Integer ID : this.getPin().getPlayerID()) {
			if (!other.getPin().getPlayerID().contains(ID)) {
				Debug.print(
						Messages.getString("Card.playerUnequal"), DebugLevel.DEFAULT); //$NON-NLS-1$
				return false;
			}
		}
		if (other.getShape() != this.getShape()) {
			Debug.print(
					Messages.getString("Card.shapeUnequal"), DebugLevel.DEFAULT); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append(" ------ \n"); //$NON-NLS-1$

		StringBuilder line1 = new StringBuilder("|"); //$NON-NLS-1$
		StringBuilder line2 = new StringBuilder("|"); //$NON-NLS-1$
		StringBuilder line3 = new StringBuilder("|"); //$NON-NLS-1$
		StringBuilder line4 = new StringBuilder("|"); //$NON-NLS-1$
		StringBuilder line5 = new StringBuilder("|"); //$NON-NLS-1$
		StringBuilder line6 = new StringBuilder("|"); //$NON-NLS-1$

		Card c = new Card(this);
		if (c.getOpenings().isTop()) {
			line1.append("##  ##|"); //$NON-NLS-1$
			line2.append("##  ##|"); //$NON-NLS-1$
		} else {
			line1.append("######|"); //$NON-NLS-1$
			line2.append("######|"); //$NON-NLS-1$
		}
		if (c.getOpenings().isLeft()) {
			line3.append("  "); //$NON-NLS-1$
			line4.append("  "); //$NON-NLS-1$
		} else {
			line3.append("##"); //$NON-NLS-1$
			line4.append("##"); //$NON-NLS-1$
		}
		if (c.getPin().getPlayerID().size() != 0) {
			line3.append("S"); //$NON-NLS-1$
		} else {
			line3.append(" "); //$NON-NLS-1$
		}
		if (c.getTreasure() != null) {
			String name = c.getTreasure().name();
			switch (name.charAt(1)) {
			case 'Y':
				// Symbol
				line3.append("T"); //$NON-NLS-1$
				break;
			case 'T':
				// Startpunkt
				line3.append("S"); //$NON-NLS-1$
				break;
			}

			line4.append(name.substring(name.length() - 2));
		} else {
			line3.append(" "); //$NON-NLS-1$
			line4.append("  "); //$NON-NLS-1$
		}
		if (c.getOpenings().isRight()) {
			line3.append("  |"); //$NON-NLS-1$
			line4.append("  |"); //$NON-NLS-1$
		} else {
			line3.append("##|"); //$NON-NLS-1$
			line4.append("##|"); //$NON-NLS-1$
		}
		if (c.getOpenings().isBottom()) {
			line5.append("##  ##|"); //$NON-NLS-1$
			line6.append("##  ##|"); //$NON-NLS-1$
		} else {
			line5.append("######|"); //$NON-NLS-1$
			line6.append("######|"); //$NON-NLS-1$
		}

		sb.append(line1.toString() + "\n"); //$NON-NLS-1$
		sb.append(line2.toString() + "\n"); //$NON-NLS-1$
		sb.append(line3.toString() + "\n"); //$NON-NLS-1$
		sb.append(line4.toString() + "\n"); //$NON-NLS-1$
		sb.append(line5.toString() + "\n"); //$NON-NLS-1$
		sb.append(line6.toString() + "\n"); //$NON-NLS-1$
		sb.append(" ------ \n"); //$NON-NLS-1$

		return sb.toString();
	}

	public CardShape getShape() {
		boolean[] open = new boolean[4];
		open[0] = getOpenings().isTop();
		open[1] = getOpenings().isRight();
		open[2] = getOpenings().isBottom();
		open[3] = getOpenings().isLeft();

		int indsum = 0;
		int anzop = 0;

		for (int i = 0; i < open.length; i++) {
			if (open[i]) {
				indsum += i;
				++anzop;
			}
		}
		if (anzop == 2 && indsum % 2 == 0) {
			return CardShape.I;
		} else if (anzop == 2 && indsum % 2 == 1) {
			return CardShape.L;
		} else if (anzop==3){
			return CardShape.T;
		} else {
			return null;
		}
	}

	public Orientation getOrientation() {
		switch (getShape()) {
		case I:
			if (getOpenings().isTop()) {
				return Orientation.D0;
			}
			return Orientation.D90;
		case L:
			if (getOpenings().isTop() && getOpenings().isRight()) {
				return Orientation.D0;
			} else if (getOpenings().isRight() && getOpenings().isBottom()) {
				return Orientation.D90;
			} else if (getOpenings().isBottom() && getOpenings().isLeft()) {
				return Orientation.D180;
			} else { // if(getOpenings().isLeft() && getOpenings().isTop()){
				return Orientation.D270;
			}
		case T:
			if (!getOpenings().isTop()) {
				return Orientation.D0;
			} else if (!getOpenings().isRight()) {
				return Orientation.D90;
			} else if (!getOpenings().isBottom()) {
				return Orientation.D180;
			} else {// if(!getOpenings().isLeft()){
				return Orientation.D270;
			}
		default:
			return null;
		}

	}

}
