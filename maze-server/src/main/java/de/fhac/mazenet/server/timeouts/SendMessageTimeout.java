package de.fhac.mazenet.server.timeouts;

import de.fhac.mazenet.server.generated.ErrorType;
import de.fhac.mazenet.server.networking.Connection;

import java.util.TimerTask;

public class SendMessageTimeout extends TimerTask {

	private Connection con;

	public SendMessageTimeout(Connection con) {
		this.con = con;
	}

	@Override
	public void run() {
		this.con.disconnect(ErrorType.TIMEOUT);
	}

}
