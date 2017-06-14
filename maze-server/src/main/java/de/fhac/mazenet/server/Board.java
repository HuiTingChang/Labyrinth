package de.fhac.mazenet.server;

import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;

import java.util.*;

public class Board extends BoardType {

    private TreasureType currentTreasure;

    /**
     * Erzeugt ein Board von einem BoardType. Dabei wird currentTreasure von
     * Board <b>nicht</b> gesetzt, da es keine Objektvariable von BoardType ist
     *
     * @param boardType Instanz von BoardType, aus der eine Instanz von Board generiert
     *                  wird
     */
    public Board(BoardType boardType) {
        super();
        PositionType forbiddenPositionType = boardType.getForbidden();
        forbidden = (forbiddenPositionType != null) ? new Position(
                forbiddenPositionType) : null;
        shiftCard = new Card(boardType.getShiftCard());
        // XXX: Warum? Initialisierung?
        this.getRow();
        for (int i = 0; i < 7; i++) {
            this.getRow().add(i, new Row());
            this.getRow().get(i).getCol();
            for (int j = 0; j < 7; j++) {
                // new Card, damit keine Referenzen, sondern richte Kopien
                // erstellt werden
                this.getRow()
                        .get(i)
                        .getCol()
                        .add(j,
                                new Card(boardType.getRow().get(i).getCol()
                                        .get(j)));
            }
        }
        // es darf keine boardinitialisierung mehr durchgefuehrt werden, da
        // diese unsere Kopie ueberschreiben wuerde
    }

    public Board() {
        super();
        forbidden = null;
        this.getRow();
        // Erst werden alle Karten mit einer Standardkarte belegt
        for (int i = 0; i < 7; i++) {
            this.getRow().add(i, new Row());
            this.getRow().get(i).getCol();
            for (int j = 0; j < 7; j++) {
                this.getRow().get(i).getCol()
                        .add(j, new Card(Card.CardShape.I, Card.Orientation.D0, null));
            }

        }
        // Dann wird das Spielfeld regelkonform aufgebaut
        generateInitialBoard();
    }

    private void generateInitialBoard() {
        // fixedCards:
        // Die festen, unveraenderbaren Karten auf dem Spielbrett
        setCard(0, 0, new Card(Card.CardShape.L, Card.Orientation.D90, null));
        setCard(0, 2,
                new Card(Card.CardShape.T, Card.Orientation.D0, TreasureType.SYM_13));
        setCard(0, 4,
                new Card(Card.CardShape.T, Card.Orientation.D0, TreasureType.SYM_14));
        setCard(0, 6, new Card(Card.CardShape.L, Card.Orientation.D180, null));
        setCard(2, 0, new Card(Card.CardShape.T, Card.Orientation.D270,
                TreasureType.SYM_15));
        setCard(2, 2, new Card(Card.CardShape.T, Card.Orientation.D270,
                TreasureType.SYM_16));
        setCard(2, 4,
                new Card(Card.CardShape.T, Card.Orientation.D0, TreasureType.SYM_17));
        setCard(2, 6, new Card(Card.CardShape.T, Card.Orientation.D90,
                TreasureType.SYM_18));
        setCard(4, 0, new Card(Card.CardShape.T, Card.Orientation.D270,
                TreasureType.SYM_19));
        setCard(4, 2, new Card(Card.CardShape.T, Card.Orientation.D180,
                TreasureType.SYM_20));
        setCard(4, 4, new Card(Card.CardShape.T, Card.Orientation.D90,
                TreasureType.SYM_21));
        setCard(4, 6, new Card(Card.CardShape.T, Card.Orientation.D90,
                TreasureType.SYM_22));
        setCard(6, 0, new Card(Card.CardShape.L, Card.Orientation.D0, null));
        setCard(6, 2, new Card(Card.CardShape.T, Card.Orientation.D180,
                TreasureType.SYM_23));
        setCard(6, 4, new Card(Card.CardShape.T, Card.Orientation.D180,
                TreasureType.SYM_24));
        setCard(6, 6, new Card(Card.CardShape.L, Card.Orientation.D270, null));

        // die freien verschiebbaren Teile auf dem Spielbrett
        ArrayList<Card> freeCards = new ArrayList<Card>();
        Random random = new Random();
        if (Settings.TESTBOARD) {
            random.setSeed(Settings.TESTBOARD_SEED);
        }
        // 15 mal L-shape (6 (sym) + 9 (ohne))
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_01));
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_02));
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_03));
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_04));
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_05));
        freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_06));

        for (int i = 0; i < 9; i++) {
            freeCards.add(new Card(Card.CardShape.L, Card.Orientation.fromValue(random
                    .nextInt(4) * 90), null));
        }

        // 13 mal I-shape
        for (int i = 0; i < 13; i++) {
            freeCards.add(new Card(Card.CardShape.I, Card.Orientation.fromValue(random
                    .nextInt(4) * 90), null));
        }

        // 6 mal T-shape
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_07));
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_08));
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_09));
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_10));
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_11));
        freeCards.add(new Card(Card.CardShape.T, Card.Orientation.fromValue(random
                .nextInt(4) * 90), TreasureType.SYM_12));

        if (!Settings.TESTBOARD)
            Collections.shuffle(freeCards);

        int k = 0;
        for (int i = 1; i < 7; i += 2) {
            for (int j = 0; j < 7; j += 1) {
                setCard(i, j, freeCards.get(k++));
            }
        }
        for (int i = 1; i < 7; i += 2) {
            for (int j = 0; j < 7; j += 2) {
                setCard(j, i, freeCards.get(k++));
            }
        }
        this.setShiftCard(freeCards.get(k));
        getCard(0, 0).getPin().getPlayerID().add(1);
        getCard(0, 6).getPin().getPlayerID().add(2);
        getCard(6, 0).getPin().getPlayerID().add(3);
        getCard(6, 6).getPin().getPlayerID().add(4);

        // Start als Schatz hinterlegen
        getCard(0, 0).setTreasure(TreasureType.START_01);
        getCard(0, 6).setTreasure(TreasureType.START_02);
        getCard(6, 0).setTreasure(TreasureType.START_03);
        getCard(6, 6).setTreasure(TreasureType.START_04);

        Debug.print(this.toString(), DebugLevel.DEBUG);
    }

    // Ausgabe des Spielbretts als AsciiArt
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board [currentTreasure=" + currentTreasure + "]\n"); //$NON-NLS-1$ //$NON-NLS-2$
        sb.append(Messages.getString("Board.Board")); //$NON-NLS-1$
        sb.append(" ------ ------ ------ ------ ------ ------ ------ \n"); //$NON-NLS-1$
        for (int i = 0; i < getRow().size(); i++) {
            StringBuilder line1 = new StringBuilder("|"); //$NON-NLS-1$
            StringBuilder line2 = new StringBuilder("|"); //$NON-NLS-1$
            StringBuilder line3 = new StringBuilder("|"); //$NON-NLS-1$
            StringBuilder line4 = new StringBuilder("|"); //$NON-NLS-1$
            StringBuilder line5 = new StringBuilder("|"); //$NON-NLS-1$
            StringBuilder line6 = new StringBuilder("|"); //$NON-NLS-1$
            for (int j = 0; j < getRow().get(i).getCol().size(); j++) {
                Card c = new Card(getCard(i, j));
                if (c.getOpenings().isTop()) {
                    line1.append("##  ##|"); //$NON-NLS-1$
                    line2.append("##  ##|"); //$NON-NLS-1$
                } else {
                    line1.append("######|"); //$NON-NLS-1$
                    line2.append("######|"); //$NON-NLS-1$
                }
                if (c.getOpenings().isLeft()) {
                    line3.append("  "); //$NON-NLS-1$
                    line4.append("  "); //$NON-NLS-1$
                } else {
                    line3.append("##"); //$NON-NLS-1$
                    line4.append("##"); //$NON-NLS-1$
                }
                if (c.getPin().getPlayerID().size() != 0) {
                    line3.append("S"); //$NON-NLS-1$
                } else {
                    line3.append(" "); //$NON-NLS-1$
                }
                if (c.getTreasure() != null) {
                    String name = c.getTreasure().name();
                    switch (name.charAt(1)) {
                        case 'Y':
                            // Symbol
                            line3.append("T"); //$NON-NLS-1$
                            break;
                        case 'T':
                            // Startpunkt
                            line3.append("S"); //$NON-NLS-1$
                            break;
                    }
                    line4.append(name.substring(name.length() - 2));
                } else {
                    line3.append(" "); //$NON-NLS-1$
                    line4.append("  "); //$NON-NLS-1$
                }
                if (c.getOpenings().isRight()) {
                    line3.append("  |"); //$NON-NLS-1$
                    line4.append("  |"); //$NON-NLS-1$
                } else {
                    line3.append("##|"); //$NON-NLS-1$
                    line4.append("##|"); //$NON-NLS-1$
                }
                if (c.getOpenings().isBottom()) {
                    line5.append("##  ##|"); //$NON-NLS-1$
                    line6.append("##  ##|"); //$NON-NLS-1$
                } else {
                    line5.append("######|"); //$NON-NLS-1$
                    line6.append("######|"); //$NON-NLS-1$
                }
            }
            sb.append(line1.toString() + "\n"); //$NON-NLS-1$
            sb.append(line2.toString() + "\n"); //$NON-NLS-1$
            sb.append(line3.toString() + "\n"); //$NON-NLS-1$
            sb.append(line4.toString() + "\n"); //$NON-NLS-1$
            sb.append(line5.toString() + "\n"); //$NON-NLS-1$
            sb.append(line6.toString() + "\n"); //$NON-NLS-1$
            sb.append(" ------ ------ ------ ------ ------ ------ ------ \n"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    public void setCard(int row, int col, Card c) {
        //FIXME
        // Muss ueberschrieben werden, daher zuerst entfernen und dann...
        this.getRow().get(row).getCol().remove(col);
        // ...hinzufuegen
        this.getRow().get(row).getCol().add(col, c);
    }

    public CardType getCard(int row, int col) {
        return this.getRow().get(row).getCol().get(col);
    }

    // Fuehrt nur das Hereinschieben der Karte aus!!!
    public void proceedShift(MoveMessageType move) {
        Debug.print(
                Messages.getString("Board.proceedShiftFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        Position sm = new Position(move.getShiftPosition());
        if (sm.getCol() % 6 == 0) { // Col=6 oder 0
            if (sm.getRow() % 2 == 1) {
                // horizontal schieben
                int row = sm.getRow();
                int start = (sm.getCol() + 6) % 12; // Karte die rausgenommen
                // wird
                setShiftCard(getCard(row, start));

                if (start == 6) {
                    for (int i = 6; i > 0; --i) {
                        setCard(row, i, new Card(getCard(row, i - 1)));
                    }
                } else {// Start==0
                    for (int i = 0; i < 6; ++i) {
                        setCard(row, i, new Card(getCard(row, i + 1)));
                    }
                }
            }
        } else if (sm.getRow() % 6 == 0) {
            if (sm.getCol() % 2 == 1) {
                // vertikal schieben
                int col = sm.getCol();
                int start = (sm.getRow() + 6) % 12; // Karte die rausgenommen
                // wird
                setShiftCard(getCard(start, col));
                if (start == 6) {
                    for (int i = 6; i > 0; --i) {
                        setCard(i, col, new Card(getCard(i - 1, col)));
                    }
                } else {// Start==0
                    for (int i = 0; i < 6; ++i) {
                        setCard(i, col, new Card(getCard(i + 1, col)));
                    }
                }

            }
        }
        forbidden = sm.getOpposite();
        Card c = null;
        c = new Card(move.getShiftCard());
        // Wenn Spielfigur auf neuer shiftcard steht,
        // muss dieser wieder aufs Brett gesetzt werden
        // Dazu wird Sie auf die gerade hereingeschoben
        // Karte gesetzt
        if (!shiftCard.getPin().getPlayerID().isEmpty()) {
            // Figur zwischenspeichern
            CardType.Pin temp = shiftCard.getPin();
            // Figur auf SchiebeKarte löschen
            shiftCard.setPin(new CardType.Pin());
            // Zwischengespeicherte Figut auf
            // neuer Karte plazieren
            c.setPin(temp);
        }
        setCard(sm.getRow(), sm.getCol(), c);
    }

    /**
     * gibt zurueck ob mit dem Zug der aktuelle Schatz erreicht wurde
     *
     * @param move
     * @param currPlayer
     * @return
     */
    public boolean proceedTurn(MoveMessageType move, Integer currPlayer) {
        Debug.print(
                Messages.getString("Board.proceedTurnFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        // XXX ACHTUNG wird nicht mehr auf Richtigkeit überprüft!!!
        this.proceedShift(move);
        Position target = new Position(move.getNewPinPos());
        Debug.print(String.format(Messages.getString("Board.playerMovesTo"),
                target.getRow(), target.getCol()), DebugLevel.DEFAULT);

        movePlayer(findPlayer(currPlayer), target, currPlayer);
        Card c = new Card(getCard(target.getRow(), target.getCol()));
        return (c.getTreasure() == currentTreasure);

    }

    protected void movePlayer(PositionType oldPos, PositionType newPos,
                              Integer playerID) {
        Debug.print(Messages.getString("Board.movePlayerFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        getCard(oldPos.getRow(), oldPos.getCol()).getPin().getPlayerID()
                .remove(playerID);
        getCard(newPos.getRow(), newPos.getCol()).getPin().getPlayerID()
                .add(playerID);
    }

    public Board fakeShift(MoveMessageType move) {
        Debug.print(Messages.getString("Board.fakeShiftFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        Board fake = (Board) this.clone();
        fake.proceedShift(move);
        return fake;
    }

    @Override
    public Object clone() {
        Board clone = new Board();
        if (forbidden == null) {
            clone.forbidden = null;
        } else {
            clone.forbidden = new Position(this.forbidden);
        }
        clone.shiftCard = new Card(this.shiftCard);
        clone.currentTreasure = this.currentTreasure;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                clone.setCard(i, j, new Card(this.getCard(i, j)));
            }
        }
        return clone;
    }

    public boolean validateTransition(MoveMessageType move, Integer playerID) {
        Debug.print(
                Messages.getString("Board.validateTransitionFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        PositionType movePosition = move.getShiftPosition();
        CardType moveShiftCard = move.getShiftCard();
        if (movePosition == null || moveShiftCard == null)
            return false;
        Position sm = new Position(movePosition);
        // Ueberpruefen ob das Reinschieben der Karte gueltig ist
        if (!sm.isLoosePosition() || sm.equals(forbidden)) {
            System.err.println(String.format(Messages
                    .getString("Board.forbiddenPostitionShiftCard"), movePosition.getRow(), movePosition.getCol())); //$NON-NLS-1$
            return false;
        }
        Card sc = new Card(moveShiftCard);
        if (!sc.equals(shiftCard)) {
            System.err.println(Messages
                    .getString("Board.shiftCardIllegallyChanged")); //$NON-NLS-1$
            return false;
        }
        // Ueberpruefen ob der Spielzug gueltig ist
        Board fakeBoard = this.fakeShift(move);
        Position playerPosition = new Position(fakeBoard.findPlayer(playerID));
        Debug.print(
                String.format(Messages
                                .getString("Board.playerWantsToMoveFromTo"), //$NON-NLS-1$
                        playerPosition.getRow(), playerPosition.getCol(), move
                                .getNewPinPos().getRow(), move.getNewPinPos()
                                .getCol()), DebugLevel.VERBOSE);
        Debug.print(
                Messages.getString("Board.boardAfterShifting"), DebugLevel.DEBUG); //$NON-NLS-1$
        Debug.print(fakeBoard.toString(), DebugLevel.DEBUG);
        if (fakeBoard.pathPossible(playerPosition, move.getNewPinPos())) {
            Debug.print(
                    Messages.getString("Board.legalMove"), DebugLevel.VERBOSE); //$NON-NLS-1$
            return true;
        }
        Debug.print(String.format(Messages.getString("Board.positionNotReachable"), move
                        .getNewPinPos().getRow(), move.getNewPinPos()
                        .getCol()), //$NON-NLS-1$
                DebugLevel.DEFAULT);
        return false;
    }

    public boolean pathPossible(PositionType oldPos, PositionType newPos) {
        Debug.print(
                Messages.getString("Board.pathPossibleFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        if (oldPos == null || newPos == null)
            return false;
        Position oldP = new Position(oldPos);
        Position newP = new Position(newPos);
        return getAllReachablePositions(oldP).contains(newP);
    }

    public List<Position> getAllReachablePositions(PositionType position) {
        Debug.print(
                Messages.getString("Board.getAllReachablePositionsFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        List<Position> erreichbarePositionen = new ArrayList<Position>();
        PathInfo[][] erreichbar = getAllReachablePositionsMatrix(position);
        for (int i = 0; i < erreichbar.length; i++) {
            for (int j = 0; j < erreichbar[0].length; j++) {
                if (erreichbar[i][j].getStepsFromSource() > -1) {
                    erreichbarePositionen.add(new Position(i, j));
                }
            }
        }
        return erreichbarePositionen;
    }

    public PathInfo[][] getAllReachablePositionsMatrix(PositionType position) {
        PathInfo[][] erreichbar = new PathInfo[7][7];
        for (int i = 0; i < erreichbar.length; i++) {
            for (int j = 0; j < erreichbar[i].length; j++) {
                erreichbar[i][j]=new PathInfo();
            }
        }
        return getAllReachablePositionsMatrix(new Position(position), erreichbar, 0, null);
    }

    private PathInfo[][] getAllReachablePositionsMatrix(Position position,
                                                        PathInfo[][] erreichbar, int step, Position cameFrom) {
        erreichbar[position.getRow()][position.getCol()].setStepsFromSource(step);
        erreichbar[position.getRow()][position.getCol()].setCameFrom(cameFrom);
        final List<Position> directReachablePositions = getDirectReachablePositions(position);
        for (Position p1 : directReachablePositions) {
            if (erreichbar[p1.getRow()][p1.getCol()].getStepsFromSource() < 0 || erreichbar[p1.getRow()][p1.getCol()].getStepsFromSource() > step + 1) {
                getAllReachablePositionsMatrix(p1, erreichbar, step + 1, position);
            }
        }
        return erreichbar;
    }

    private List<Position> getDirectReachablePositions(PositionType position) {
        List<Position> positionen = new ArrayList<Position>();
        CardType k = this.getCard(position.getRow(), position.getCol());
        CardType.Openings openings = k.getOpenings();
        if (openings.isLeft()) {
            if (position.getCol() - 1 >= 0
                    && getCard(position.getRow(), position.getCol() - 1)
                    .getOpenings().isRight()) {
                positionen.add(new Position(position.getRow(), position
                        .getCol() - 1));
            }
        }
        if (openings.isTop()) {
            if (position.getRow() - 1 >= 0
                    && getCard(position.getRow() - 1, position.getCol())
                    .getOpenings().isBottom()) {
                positionen.add(new Position(position.getRow() - 1, position
                        .getCol()));
            }
        }
        if (openings.isRight()) {
            if (position.getCol() + 1 <= 6
                    && getCard(position.getRow(), position.getCol() + 1)
                    .getOpenings().isLeft()) {
                positionen.add(new Position(position.getRow(), position
                        .getCol() + 1));
            }
        }
        if (openings.isBottom()) {
            if (position.getRow() + 1 <= 6
                    && getCard(position.getRow() + 1, position.getCol())
                    .getOpenings().isTop()) {
                positionen.add(new Position(position.getRow() + 1, position
                        .getCol()));
            }
        }
        return positionen;
    }

    public Position findPlayer(Integer PlayerID) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                CardType.Pin pinsOnCard = getCard(i, j).getPin();
                for (Integer pin : pinsOnCard.getPlayerID()) {
                    if (pin == PlayerID) {
                        Position pos = new Position(i,j);
                        return pos;
                    }
                }
            }
        }
        // Pin nicht gefunden.
        // XXX: Darf eigentlich nicht vorkommen
        return null;
    }

    public PositionType findTreasure(TreasureType treasureType) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                TreasureType treasure = getCard(i, j).getTreasure();
                if (treasure == treasureType) {
                    PositionType pos = new PositionType();
                    pos.setCol(j);
                    pos.setRow(i);
                    return pos;
                }
            }
        }
        // Schatz nicht gefunden, kann nur bedeuten, dass Schatz sich auf
        // Schiebekarte befindet
        return null;
    }

    public void setTreasure(TreasureType t) {
        currentTreasure = t;
    }

    public TreasureType getTreasure() {
        return currentTreasure;
    }
}
