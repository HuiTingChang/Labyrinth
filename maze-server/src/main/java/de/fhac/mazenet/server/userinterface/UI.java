package de.fhac.mazenet.server.userinterface;

import de.fhac.mazenet.server.Board;
import de.fhac.mazenet.server.Game;
import de.fhac.mazenet.server.Player;
import de.fhac.mazenet.server.generated.MoveMessageType;

import java.util.List;

public interface UI {

	// XXX: ich bin dafuer long in int zu aendern, damit man bei Timern mit
	// Delay nicht mehr casten muss. Auf meinem System, wuerde der maximale
	// Integer eine Wartezeit von abgerundet 24 Tagen zulassen. Ist meiner
	// Meinung ausreichend ;) ~jago
	public void displayMove(MoveMessageType mm, Board b, long moveDelay,
                            long shiftDelay, boolean treasureReached);

	public void updatePlayerStatistics(List<Player> stats, Integer current);
	public void init(Board b);
	public void setGame(Game g);
	public void gameEnded(Player winner);
}
