package de.fhac.mazenet.server.tools;

public enum DebugLevel {
	NONE(10), DEFAULT(20), VERBOSE(30), DEBUG(40);
	final int value;

	DebugLevel(int v) {
		value = v;
	}

	public int value() {
		return value;
	}

	public static DebugLevel fromValue(int v) {
		for (DebugLevel c : DebugLevel.values()) {
			if (c.value == v) {
				return c;
			}
		}
		throw new IllegalArgumentException(v + ""); //$NON-NLS-1$
	}
}
