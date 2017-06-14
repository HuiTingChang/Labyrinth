package de.fhac.mazenet.server.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UTFOutputStream {

	private OutputStream os;

	public UTFOutputStream(OutputStream stream) {
		os = stream;
	}

	public void writeUTF8(String text) throws IOException {
		byte[] bytes = text.getBytes("UTF-8"); //$NON-NLS-1$
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.order(ByteOrder.BIG_ENDIAN); // Transform to network order
		bf.putInt(bytes.length);
		os.write(bf.array());
		os.write(bytes);
	}

	public void flush() throws IOException {
		os.flush();
	}

	public void close() throws IOException {
		this.os.close();
	}
}
