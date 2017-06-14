package de.fhac.mazenet.server.networking;

import de.fhac.mazenet.server.generated.MazeCom;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class XmlOutStream extends UTFOutputStream {

	private Marshaller marshaller;

	public XmlOutStream(OutputStream out) {
		super(out);
		// Anlegen der JAXB-Komponenten
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MazeCom.class);
			this.marshaller = jaxbContext.createMarshaller();
			this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (JAXBException e) {
			Debug.print(Messages.getString("XmlOutStream.ErrorInitialisingJAXBComponent"), DebugLevel.DEFAULT); //$NON-NLS-1$
		}
	}

	/**
	 * Versenden einer XML Nachricht
	 * 
	 * @param mc
	 */
	public void write(MazeCom mc) {
		// generierung des fertigen XML
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			this.marshaller.marshal(mc, byteArrayOutputStream);
			Debug.print(Messages.getString("XmlOutStream.Written"), DebugLevel.DEBUG); //$NON-NLS-1$
			Debug.print(new String(byteArrayOutputStream.toByteArray()), DebugLevel.DEBUG);
			// Versenden des XML
			this.writeUTF8(new String(byteArrayOutputStream.toByteArray()));
			Debug.print(String.format(Messages.getString("XmlOutStream.SendMessageTypToID"), mc.getMcType().value(), mc.getId()), //$NON-NLS-1$
					DebugLevel.VERBOSE);
			this.flush();
		} catch (IOException e) {
			Debug.print(Messages.getString("XmlOutStream.errorSendingMessage"), DebugLevel.DEFAULT); //$NON-NLS-1$
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}

}
