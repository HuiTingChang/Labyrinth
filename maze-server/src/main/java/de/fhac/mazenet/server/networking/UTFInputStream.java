package de.fhac.mazenet.server.networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UTFInputStream {

	private InputStream is;

	public UTFInputStream(InputStream stream) {
		this.is = stream;
	}

	public String readUTF8() throws IOException {
		ByteBuffer bf = ByteBuffer.wrap(this.readNBytes(4));
		bf.order(ByteOrder.BIG_ENDIAN); // Java always use hostorder. See javadoc
		byte[] bytes = this.readNBytes(bf.getInt(0));
		return new String(bytes, "UTF-8"); //$NON-NLS-1$
	}

	public void close() throws IOException {
		this.is.close();
	}

	private byte[] readNBytes(int n) throws IOException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		byte buf[] = new byte[n];
		int readcount = 0;
		int lastreadcount;
		while (readcount < n) {
			lastreadcount = this.is.read(buf, readcount, n - readcount);
			if (lastreadcount == -1) {
				throw new EOFException(String.format(
						Messages.getString("UTFInputStream.EOFException"), //$NON-NLS-1$
						readcount, n));
			}
			readcount += lastreadcount;

		}

		return buf;
	}
}