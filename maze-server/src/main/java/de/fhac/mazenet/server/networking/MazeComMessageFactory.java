package de.fhac.mazenet.server.networking;

import de.fhac.mazenet.server.Board;
import de.fhac.mazenet.server.Player;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.generated.WinMessageType.Winner;

import java.util.HashMap;
import java.util.List;

public class MazeComMessageFactory {

	static private ObjectFactory of = new ObjectFactory();

	public MazeCom createLoginReplyMessage(int newID) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.LOGINREPLY);
		mc.setId(newID);
		mc.setLoginReplyMessage(of.createLoginReplyMessageType());
		mc.getLoginReplyMessage().setNewID(newID);
		return mc;
	}

	public MazeCom createAcceptMessage(int playerID, ErrorType et) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.ACCEPT);
		mc.setId(playerID);
		mc.setAcceptMessage(of.createAcceptMessageType());
		mc.getAcceptMessage().setErrorCode(et);
		mc.getAcceptMessage().setAccept(et == ErrorType.NOERROR);
		return mc;
	}

	public MazeCom createWinMessage(int playerID, int winnerId, String name,
			Board b) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.WIN);
		mc.setId(playerID);
		mc.setWinMessage(of.createWinMessageType());
		Winner w = of.createWinMessageTypeWinner();
		w.setId(winnerId);
		w.setValue(name);
		mc.getWinMessage().setWinner(w);
		mc.getWinMessage().setBoard(b);
		return mc;
	}

	public MazeCom createDisconnectMessage(int playerID, String name,
			ErrorType et) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.DISCONNECT);
		mc.setId(playerID);
		mc.setDisconnectMessage(of.createDisconnectMessageType());
		mc.getDisconnectMessage().setErrorCode(et);
		mc.getDisconnectMessage().setName(name);
		return mc;
	}
	 
	public MazeCom createMoveMessage(int playerID, CardType ct, PositionType sc, 
			PositionType sp)
	{
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.MOVE);
		mc.setId(playerID);
		mc.setMoveMessage(of.createMoveMessageType());
		mc.getMoveMessage().setShiftCard(ct);
		mc.getMoveMessage().setNewPinPos(sc);
		mc.getMoveMessage().setShiftPosition(sp);
		
		return mc;
	}

	public MazeCom createAwaitMoveMessage(HashMap<Integer, Player> Player,
			Integer currPlayer, Board brett, List<TreasureType> foundTreasures) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.AWAITMOVE);
		mc.setId(Player.get(currPlayer).getID());
		mc.setAwaitMoveMessage(of.createAwaitMoveMessageType());
		// Brett uebergeben
		mc.getAwaitMoveMessage().setBoard(brett);
		mc.getAwaitMoveMessage().setTreasure(
				Player.get(currPlayer).getCurrentTreasure());
		mc.getAwaitMoveMessage().getFoundTreasures().addAll(foundTreasures);
		for (Integer playerID : Player.keySet()) 
		{
			TreasuresToGoType ttg = of.createTreasuresToGoType();
			ttg.setPlayer(playerID);
			ttg.setTreasures(Player.get(playerID).treasuresToGo());
			mc.getAwaitMoveMessage().getTreasuresToGo().add(ttg);
		}

		return mc;
	}

	public MazeCom createLoginMessage(String string) {
		MazeCom mc = of.createMazeCom();
		mc.setMcType(MazeComType.LOGIN);
		mc.setId(-1);
		mc.setLoginMessage(of.createLoginMessageType());
		mc.getLoginMessage().setName(string);
		return mc;
	}
}
