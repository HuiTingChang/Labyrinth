/*
 * Connection regelt die serverseitige Protokollarbeit
 */
package de.fhac.mazenet.server.networking;

import de.fhac.mazenet.server.Board;
import de.fhac.mazenet.server.Game;
import de.fhac.mazenet.server.Player;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.timeouts.TimeOutManager;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;

import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Connection {

	private Socket socket;
	private Player player;
	private XmlInStream inFromClient;
	private XmlOutStream outToClient;
	private MazeComMessageFactory mazeComMessageFactory;
	private TimeOutManager tom;
	private Game currentGame;
	/**
	 * Speicherung des Sockets und oeffnen der Streams
	 * 
	 * @param s
	 *            Socket der Verbindung
	 */
	public Connection(Socket s, Game g, int newId) 
	{
		// TODO entfernen => nur fuer Test
		this.socket = s;
		this.currentGame = g;
		try {
			this.inFromClient = new XmlInStream(this.socket.getInputStream());
		} catch (IOException e) {
			System.err.println(Messages
					.getString("Connection.couldNotOpenInputStream")); //$NON-NLS-1$
		}
		try {
			this.outToClient = new XmlOutStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.err.println(Messages
					.getString("Connection.couldNotOpenOutputStream")); //$NON-NLS-1$
		}
		this.player = new Player(newId, this);
		this.mazeComMessageFactory = new MazeComMessageFactory();
		this.tom = new TimeOutManager();

	}

	/**
	 * Allgemeines senden einer fertigen MazeCom-Instanz
	 */
	public void sendMessage(MazeCom mc, boolean withTimer) {
		// Timer starten, der beim lesen beendet wird
		// Ablauf Timer = Problem User
		if (withTimer)
			this.tom.startSendMessageTimeOut(this.player.getID(), this);
		this.outToClient.write(mc);
	}

	/**
	 * Allgemeines empfangen einer MazeCom-Instanz
	 * 
	 * @return
	 */
	public MazeCom receiveMessage() 
	{
		MazeCom result = null;

		try {
			result = this.inFromClient.readMazeCom();
		} catch (UnmarshalException e) {
			Throwable xmle = e.getLinkedException();
			Debug.print(Messages.getString("Connection.XmlError") + xmle.getMessage(), DebugLevel.DEFAULT); //$NON-NLS-1$
		} catch (IOException e) {
			Debug.print(Messages.getString("XmlInStream.errorReadingMessage"), //$NON-NLS-1$
					DebugLevel.DEFAULT);
			Debug.print(
					Messages.getString("Connection.playerExitedUnexpected"), //$NON-NLS-1$
					DebugLevel.DEFAULT);
			// entfernen des Spielers
			this.currentGame.removePlayer(this.player.getID());
		}
		this.tom.stopSendMessageTimeOut(this.player.getID());
		return result;

	}

	/**
	 * Allgemeines erwarten eines Login
	 * 
	 * @param newId
	 * @return Neuer Player, bei einem Fehler jedoch null
	 */
	public Player login(int newId,Stack<Integer> availablePlayers) 
	{
		this.player = new Player(newId, this);
		LoginThread lt = new LoginThread(this, this.player, availablePlayers);
		lt.start();
		return this.player;
	}

	/**
	 * Anfrage eines Zuges beim Spieler
	 * 
	 * @param brett
	 *            aktuelles Spielbrett
	 * @return Valieder Zug des Spielers oder NULL
	 */
	public MoveMessageType awaitMove(HashMap<Integer, Player> spieler,
			Board brett, int tries, List<TreasureType> foundTreasures) 
	{
		if (spieler.get(player.getID()) != null && tries < Settings.MOVETRIES) 
		{
			sendMessage(mazeComMessageFactory.createAwaitMoveMessage(spieler,
					player.getID(), brett, foundTreasures), true);
			MazeCom result = this.receiveMessage();
			if (result != null && result.getMcType() == MazeComType.MOVE) 
			{
				if (currentGame.getBoard().validateTransition(
						result.getMoveMessage(), player.getID())) 
				{
					sendMessage(
							mazeComMessageFactory.createAcceptMessage(
									player.getID(), ErrorType.NOERROR), false);
					return result.getMoveMessage();
				}
				// nicht Regelkonform
				sendMessage(
						mazeComMessageFactory.createAcceptMessage(
								player.getID(), ErrorType.ILLEGAL_MOVE), false);
				return awaitMove(spieler, brett, ++tries, foundTreasures);
			}
			// XML nicht verwertbar
			sendMessage(mazeComMessageFactory.createAcceptMessage(
					player.getID(), ErrorType.AWAIT_MOVE), false);
			return awaitMove(spieler, brett, ++tries, foundTreasures);

		}
		disconnect(ErrorType.TOO_MANY_TRIES);

		return null;
	}

	@Deprecated
	public MoveMessageType awaitMove3(HashMap<Integer, Player> spieler,
			Board brett, int tries, List<TreasureType> foundTreasures) {
		this.sendMessage(this.mazeComMessageFactory.createAwaitMoveMessage(
				spieler, this.player.getID(), brett, foundTreasures), true);
		MazeCom result = this.receiveMessage();
		if (result != null && result.getMcType() == MazeComType.MOVE) {
			// Antwort mit NOERROR
			if (this.currentGame.getBoard().validateTransition(
					result.getMoveMessage(), this.player.getID())) {
				this.sendMessage(this.mazeComMessageFactory
						.createAcceptMessage(this.player.getID(),
								ErrorType.NOERROR), false);
				return result.getMoveMessage();
			} else if (tries < Settings.MOVETRIES)
				return illegalMove(spieler, brett, ++tries, foundTreasures);
			else {
				disconnect(ErrorType.TOO_MANY_TRIES);
				return null;
			}

		}
		// else nicht benoetigt wegen return-Statements im then
		this.sendMessage(
				this.mazeComMessageFactory.createAcceptMessage(
						this.player.getID(), ErrorType.AWAIT_MOVE), false);

		if (tries < Settings.MOVETRIES)
			return awaitMove3(spieler, brett, ++tries, foundTreasures);

		disconnect(ErrorType.TOO_MANY_TRIES);
		return null;
	}

	/**
	 * Erhaltener Move ist falsch gewesen => Fehler senden und neuen AwaitMove
	 * sende!
	 * 
	 * @param brett
	 *            aktuelles Spielbrett
	 * @return Zug des Spielers
	 */
	@Deprecated
	public MoveMessageType illegalMove(HashMap<Integer, Player> spieler,
			Board brett, int tries, List<TreasureType> foundTreasures) {
		this.sendMessage(
				this.mazeComMessageFactory.createAcceptMessage(
						this.player.getID(), ErrorType.ILLEGAL_MOVE), false);
		if (tries < Settings.MOVETRIES)
			return this.awaitMove3(spieler, brett, tries, foundTreasures);
		disconnect(ErrorType.TOO_MANY_TRIES);
		return null;
	}

	/**
	 * sendet dem Spieler den Namen des Gewinners sowie dessen ID und das
	 * Schlussbrett
	 * 
	 * @param winnerId
	 * @param name
	 * @param b
	 */
	public void sendWin(int winnerId, String name, Board b) {
		this.sendMessage(this.mazeComMessageFactory.createWinMessage(
				this.player.getID(), winnerId, name, b), false);
		try {
			this.inFromClient.close();
			this.outToClient.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Senden, dass Spieler diconnected wurde
	 * */
	public void disconnect(ErrorType et) {
		this.sendMessage(
				this.mazeComMessageFactory.createDisconnectMessage(
						this.player.getID(), this.player.getName(), et), false);
		try {
			this.inFromClient.close();
			this.outToClient.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// entfernen des Spielers
		this.currentGame.removePlayer(this.player.getID());
	}
}
