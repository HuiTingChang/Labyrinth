package de.fhac.mazenet.server.timeouts;

import de.fhac.mazenet.server.Game;

import java.util.TimerTask;

public class LoginTimeOut extends TimerTask {
	private Game currentGame;

	public LoginTimeOut(Game currentGame) {
		super();
		this.currentGame = currentGame;
	}

	@Override
	public void run() {
		currentGame.closeServerSocket();
	}
	

}
