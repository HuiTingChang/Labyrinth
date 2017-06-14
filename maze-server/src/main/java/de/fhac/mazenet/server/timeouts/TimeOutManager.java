package de.fhac.mazenet.server.timeouts;

import de.fhac.mazenet.server.Game;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.networking.Connection;

import java.util.HashMap;
import java.util.Timer;

public class TimeOutManager extends Timer {

	private LoginTimeOut loginTimeOut;
	private HashMap<Integer, SendMessageTimeout> sendMessageTimeout;

	public TimeOutManager() {
		super("TimeOuts", true); //$NON-NLS-1$
		this.sendMessageTimeout = new HashMap<Integer, SendMessageTimeout>();
	}

	public void startLoginTimeOut(Game currentGame) {
		loginTimeOut = new LoginTimeOut(currentGame);
		this.schedule(loginTimeOut, Settings.LOGINTIMEOUT);
	}

	public void stopLoginTimeOut() {
		loginTimeOut.cancel();
	}

	public void startSendMessageTimeOut(int playerId, Connection c) {
		sendMessageTimeout.put(playerId, new SendMessageTimeout(c));
		this.schedule(sendMessageTimeout.get(playerId), Settings.SENDTIMEOUT);
	}

	public void stopSendMessageTimeOut(int playerId) {
		if (sendMessageTimeout.containsKey(playerId))
			sendMessageTimeout.get(playerId).cancel();
	}
}
