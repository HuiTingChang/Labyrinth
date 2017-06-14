package de.fhac.mazenet.server.userinterface.mazeFX;/**
 * Created by Richard Zameitat on 25.05.2016.
 */

import de.fhac.mazenet.server.*;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.CardType;
import de.fhac.mazenet.server.generated.MoveMessageType;
import de.fhac.mazenet.server.generated.PositionType;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;
import de.fhac.mazenet.server.userinterface.UI;
import de.fhac.mazenet.server.userinterface.mazeFX.objects.CardFX;
import de.fhac.mazenet.server.userinterface.mazeFX.objects.PlayerFX;
import de.fhac.mazenet.server.userinterface.mazeFX.util.FakeTranslateBinding;
import de.fhac.mazenet.server.userinterface.mazeFX.util.Translate3D;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MazeFX extends Application implements UI {

    // DIRTY HACK FOR GETTING AN INSTANCE STARTS HERE
    // (JavaFX ist not very good at creating instances ...)
    private static CountDownLatch instanceCreated = new CountDownLatch(1);
    private static MazeFX lastInstance = null;

    public MazeFX() {

    }

    public synchronized static MazeFX newInstance() {
        new Thread(() -> Application.launch(MazeFX.class)).start();
        try {
            instanceCreated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        MazeFX instance = lastInstance;
        lastInstance = null;
        instanceCreated = new CountDownLatch(1);
        return instance;
    }

    private void instanceReady() {
        lastInstance = this;
        instanceCreated.countDown();
    }
    // END OF HACK

    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 7;

    // private static final Translate3D SHIFT_CARD_TRANSLATE = new
    // Translate3D(0,-0.2,-(BOARD_HEIGHT/2.+1));
    private static final Translate3D SHIFT_CARD_TRANSLATE = new Translate3D(0, -3.3, 0);

    private Game game;

    private Stage primaryStage;
    private Parent root;
    private C_MainUI controller;
    private Group scene3dRoot;
    private Board boardFromLastTurn;

    private CardFX[][] boardCards;
    private CardFX shiftCard;
    private Map<Integer, PlayerFX> players;
    private Map<Integer, PlayerStatFX> playerStats = new HashMap<>();
    private PlayerFX currentPlayer;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.onCloseRequestProperty().setValue(e -> {
            System.exit(0);
        });
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/MainUI.fxml")); //$NON-NLS-1$
        fxmlLoader.setResources(ResourceBundle.getBundle("locale"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        init3dStuff();

        controller.addStartServerListener(this::startActionPerformed);
        controller.addStopServerListener(this::stopActionPerformed);

        primaryStage.setTitle(Messages.getString("MazeFX.WindowTitle")); //$NON-NLS-1$
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        instanceReady();
    }

    private PlayerStatFX createPlayerStat(int teamId) throws IOException {
        return new PlayerStatFX(teamId);
    }

    private void updatePlayerStats(List<Player> stats, Integer current) {
        currentPlayer = players.get(current);
        stats.forEach(p -> {
            try {
                PlayerStatFX stat = playerStats.get(p.getID());
                if (stat == null) {
                    playerStats.put(p.getID(), stat = createPlayerStat(p.getID()));
                    controller.addPlayerStat(stat.root);
                }
                stat.update(p);
                stat.active(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        playerStats.get(current).active(true);

    }

    private void startActionPerformed() {
        controller.gameStarted();
        Debug.print("MazeFX.startactionPerformed", DebugLevel.DEFAULT); //$NON-NLS-1$
        Settings.DEFAULT_PLAYERS = controller.getMaxPlayer();
        if (game == null) {
            setGame(new Game());
        }
        String[] arguments = new String[0];
        game.parsArgs(arguments);
        game.setUserinterface(this);
        controller.clearPlayerStats();
        // $NON-NLS-1$
        game.start();
    }

    private void stopActionPerformed() {
        Debug.print("MazeFX.stopactionPerformed", DebugLevel.DEFAULT); //$NON-NLS-1$
        if (game != null) {
            game.stopGame();
            game = null;
        }
        game = new Game();
        controller.gameStopped();
    }

    private void init3dStuff() {
        // scene graph
        scene3dRoot = new Group();

        Pane parent3d = controller.getParent3D();
        SubScene sub3d = controller.getSub3D();
        // replacing original Subscene with antialised one ...
        // TODO: do it in a nicer way!
        parent3d.getChildren().remove(sub3d);
        sub3d = new SubScene(scene3dRoot, 300, 300, true, SceneAntialiasing.BALANCED);
        parent3d.getChildren().add(0, sub3d);
        sub3d.heightProperty().bind(parent3d.heightProperty());
        sub3d.widthProperty().bind(parent3d.widthProperty());

        Rotate camRotY = new Rotate(15, Rotate.Y_AXIS);
        Rotate camRotX = new Rotate(-40, Rotate.X_AXIS);
        Translate camTranZ = new Translate(0, 0, -15);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(camRotY, camRotX, camTranZ);

        camTranZ.zProperty().bind(controller.getCamZoomSlide().valueProperty());

        // create rotation animations
        // rotate right
        RotateTransition camRotR = new RotateTransition(Duration.millis(3000), camera);
        camRotR.setByAngle(360);
        camRotR.setInterpolator(Interpolator.LINEAR);
        camRotR.setCycleCount(Animation.INDEFINITE);
        camRotR.setAutoReverse(false);
        camRotR.setAxis(new Point3D(0, 1, 0));
        controller.addCamRotateRightStartListener(camRotR::play);
        controller.addCamRotateRightStopListener(camRotR::stop);

        // rotate left
        RotateTransition camRotL = new RotateTransition(Duration.millis(3000), camera);
        camRotL.setByAngle(-360);
        camRotL.setInterpolator(Interpolator.LINEAR);
        camRotL.setCycleCount(Animation.INDEFINITE);
        camRotL.setAutoReverse(false);
        camRotL.setAxis(new Point3D(0, 1, 0));
        controller.addCamRotateLeftStartListener(camRotL::play);
        controller.addCamRotateLeftStopListener(camRotL::stop);

        // stop all animations when focus is lost
        primaryStage.focusedProperty().addListener((ov, o, n) -> {
            if (!n) {
                camRotR.stop();
                camRotL.stop();
            }
        });

        // add stuff to scene graph
        scene3dRoot.getChildren().add(camera);
        sub3d.setFill(Color.ANTIQUEWHITE);
        sub3d.setCamera(camera);

        Box c1 = new Box(1, 0.1, 1);
        c1.setMaterial(new PhongMaterial(Color.RED));
        c1.setDrawMode(DrawMode.FILL);
        // scene3dRoot.getChildren().add(c1);
    }

    private static Translate3D getCardTranslateForPosition(int x, int z) {
        final double midX = BOARD_WIDTH / 2.;
        final double midZ = BOARD_HEIGHT / 2.;
        final double offX = 0.5;
        final double offZ = -0.5;
        double newX = (x - midX) * 1 + offX;
        double newZ = (midZ - z) * 1 + offZ;
        return new Translate3D(newX, 0, newZ);
    }

    private static Translate3D getCardShiftBy(PositionType posT) {
        if (posT.getCol() == 0)
            return new Translate3D(1, 0, 0);
        if (posT.getCol() == BOARD_WIDTH - 1)
            return new Translate3D(-1, 0, 0);

        if (posT.getRow() == 0)
            return new Translate3D(0, 0, -1);
        if (posT.getRow() == BOARD_HEIGHT - 1)
            return new Translate3D(0, 0, 1);

        return new Translate3D(0, 0, 0); // no idea!
    }

    private static Translate3D getCardTranslateForShiftStart(PositionType posT) {
        return getCardTranslateForPosition(posT.getCol(), posT.getRow()).translate(getCardShiftBy(posT).invert());
    }

    private List<CardFX> updateAndGetShiftedCards(PositionType shiftPos) {
        List<CardFX> cards = new LinkedList<>();
        cards.add(shiftCard);
        CardFX oldShiftCard = shiftCard;

        if (shiftPos.getCol() == 0) {
            int sRow = shiftPos.getRow();
            cards.add(shiftCard = boardCards[sRow][BOARD_WIDTH - 1]);
            for (int x = BOARD_WIDTH - 1; x > 0; cards.add(boardCards[sRow][x] = boardCards[sRow][x - 1]), x--)
                ;
            boardCards[sRow][0] = oldShiftCard;
        } else if (shiftPos.getCol() == BOARD_WIDTH - 1) {
            int sRow = shiftPos.getRow();
            cards.add(shiftCard = boardCards[sRow][0]);
            for (int x = 0; x < BOARD_WIDTH - 1; cards.add(boardCards[sRow][x] = boardCards[sRow][x + 1]), x++)
                ;
            boardCards[sRow][BOARD_WIDTH - 1] = oldShiftCard;
        } else if (shiftPos.getRow() == 0) {
            int sCol = shiftPos.getCol();
            cards.add(shiftCard = boardCards[BOARD_HEIGHT - 1][sCol]);
            for (int z = BOARD_HEIGHT - 1; z > 0; cards.add(boardCards[z][sCol] = boardCards[z - 1][sCol]), z--)
                ;
            boardCards[0][sCol] = oldShiftCard;
        } else if (shiftPos.getRow() == BOARD_HEIGHT - 1) {
            int sCol = shiftPos.getCol();
            cards.add(shiftCard = boardCards[0][sCol]);
            for (int z = 0; z < BOARD_HEIGHT - 1; cards.add(boardCards[z][sCol] = boardCards[z + 1][sCol]), z++)
                ;
            boardCards[BOARD_HEIGHT - 1][sCol] = oldShiftCard;
        }

        return cards;
    }

    private void clearBoard() {
        currentPlayer = null;
        if (players != null) {
            playerStats = new HashMap<>();
            scene3dRoot.getChildren().removeAll(players.values());
            players = null;
        }
        if (shiftCard != null) {
            shiftCard.removeFrom(scene3dRoot);
            shiftCard = null;
        }
        if (boardCards != null) {
            for (CardFX[] ca : boardCards) {
                for (CardFX c : ca) {
                    c.removeFrom(scene3dRoot);
                }
            }
        }
    }

    private void initFromBoard(Board b) {
        //System.out.println(b);

        clearBoard();
        players = new HashMap<>();
        boardCards = new CardFX[BOARD_HEIGHT][BOARD_WIDTH];
        for (int z = 0; z < BOARD_HEIGHT; z++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                CardType ct = b.getCard(z, x);
                CardFX card3d = new CardFX(ct, scene3dRoot);
                boardCards[z][x] = card3d;
                getCardTranslateForPosition(x, z).applyTo(card3d);
                scene3dRoot.getChildren().add(card3d);
                CardType.Pin pin = ct.getPin();
                if (pin != null) {
                    pin.getPlayerID().forEach(pid -> {
                        PlayerFX player = new PlayerFX(pid, card3d);
                        players.put(pid, player);
                        scene3dRoot.getChildren().add(player);
                    });
                }
            }
        }
        CardType ct = b.getShiftCard();
        shiftCard = new CardFX(ct, scene3dRoot);
        SHIFT_CARD_TRANSLATE.applyTo(shiftCard);
        scene3dRoot.getChildren().add(shiftCard);
    }

    private void animateMove(MoveMessageType mm, Board b, List<Position> path, long mvD, long shifD, boolean treasureReached) {
        final Duration durBefore = Duration.millis(shifD / 3);
        final Duration durShift = Duration.millis(shifD / 3);
        final Duration durAfter = Duration.millis(shifD / 3);

        final Duration durMove = Duration.millis(mvD);

        final PlayerFX pin = currentPlayer != null ? currentPlayer : players.getOrDefault(1, null);
        FakeTranslateBinding pinBind = null;
        //if (pin.getBoundCard() != null) {
        //    pinBind = new FakeTranslateBinding(pin, pin.getBoundCard(), pin.getOffset());
        //    pin.unbindFromCard();
        //    pinBind.bind();
        //}
        //FakeTranslateBinding pinBind_final = pinBind;

        CardFX shiftCardC = shiftCard;
        Card c = new Card(mm.getShiftCard());
        PositionType newCardPos = mm.getShiftPosition();
        int newRotation = c.getOrientation().value();

        Translate3D newCardBeforeShiftT = getCardTranslateForShiftStart(newCardPos);

        // before shift
        RotateTransition cardRotateBeforeT = new RotateTransition(durBefore, shiftCardC);
        cardRotateBeforeT.setToAngle(newRotation);
        TranslateTransition cardTranslateBeforeT = new TranslateTransition(durBefore, shiftCardC);
        cardTranslateBeforeT.setToX(newCardBeforeShiftT.x);
        cardTranslateBeforeT.setToY(newCardBeforeShiftT.y);
        cardTranslateBeforeT.setToZ(newCardBeforeShiftT.z);
        Animation animBefore = new ParallelTransition(cardRotateBeforeT, cardTranslateBeforeT);

        // shifting
        Translate3D shiftTranslate = getCardShiftBy(newCardPos);
        List<CardFX> shiftCards = updateAndGetShiftedCards(newCardPos);
        Animation[] shiftAnims = new Animation[shiftCards.size()];
        int i = 0;
        for (CardFX crd : shiftCards) {
            TranslateTransition tmpT = new TranslateTransition(durShift, crd);
            tmpT.setByX(shiftTranslate.x);
            tmpT.setByY(shiftTranslate.y);
            tmpT.setByZ(shiftTranslate.z);
            shiftAnims[i++] = tmpT;
        }
        Animation animShift = new ParallelTransition(shiftAnims);

        // after
        TranslateTransition animAfter = new TranslateTransition(durAfter, shiftCard);
        animAfter.setToX(SHIFT_CARD_TRANSLATE.x);
        animAfter.setToY(SHIFT_CARD_TRANSLATE.y);
        animAfter.setToZ(SHIFT_CARD_TRANSLATE.z);


        SequentialTransition allTr = new SequentialTransition(animBefore, animShift, animAfter);


        // SequentialTransition completeShiftAnim = new
        // SequentialTransition(animBefore,animShift,animAfter);
        // completeShiftAnim.play();

        // unbind player from card and move the pin
        /*
         * ExecuteTransition unbindTr = new ExecuteTransition(()->{
		 * System.out.println("unbind"); pin.unbindFromCard(); });/
		 **/
        for (PositionType newPinPos : path) {
//            System.out.print(" to: ("+newPinPos.getRow()+"/"+newPinPos.getCol()+")");
//            System.out.println();
            Translate3D newPinOffset = pin.getOffset();
            Translate3D newPinTr = getCardTranslateForPosition(newPinPos.getCol(), newPinPos.getRow())
                    .translate(newPinOffset);
            TranslateTransition moveAnim = new TranslateTransition(durMove, pin);
            moveAnim.setToX(newPinTr.x);
            moveAnim.setToY(newPinTr.y);
            moveAnim.setToZ(newPinTr.z);
            allTr.getChildren().add(moveAnim);
        }
        allTr.setInterpolator(Interpolator.LINEAR);
        allTr.setOnFinished(e -> {
            //System.out.println("yoyo .. done!");
            //if (pinBind_final != null) {
            //    pinBind_final.unbind();
            //}
            if (treasureReached) {
                boardCards[mm.getNewPinPos().getRow()][mm.getNewPinPos().getCol()].getTreasure().treasureFound();
            }
            pin.bindToCard(boardCards[mm.getNewPinPos().getRow()][mm.getNewPinPos().getCol()]);
        });
        allTr.play();


		/*
         * ExecuteTransition rebindTr = new ExecuteTransition(()->{
		 * pin.bindToCard(boardCards[newPinPos.getRow()][newPinPos.getCol()]);
		 * System.out.println("bind"); });
		 * 
		 * 
		 * 
		 * ExecuteTransition debugTr = new ExecuteTransition(()->{
		 * System.out.println("\n\nSTARTING TRANSITION!"); });
		 * 
		 * PauseTransition debugTr2 = new PauseTransition(durMove);/
		 **/

    }

    private List<Position> getPathtoPosition(Position target, final PathInfo[][] reachableMatrix) {
        List<Position> path = new LinkedList<>();
        int stepsToGo = reachableMatrix[target.getRow()][target.getCol()].getStepsFromSource();
        // System.out.println("stepstogo: " + stepsToGo);
        if (stepsToGo == 0) {
            return path;
        } else {
            List<Position> toGo = getPathtoPosition(reachableMatrix[target.getRow()][target.getCol()].getCameFrom(), reachableMatrix);
            for (Position p : toGo) {
                path.add(p);
            }
            path.add(target);
            return path;
        }
    }

    @Override
    public void displayMove(MoveMessageType mm, Board b, long moveDelay, long shiftDelay, boolean treasureReached) {
        boardFromLastTurn.proceedShift(mm);
        //System.out.println(b.toString());

        PositionType finalPinPos = mm.getNewPinPos();
        PositionType currentPosition = boardFromLastTurn.findPlayer(currentPlayer.getPlayerID());
        PathInfo[][] reach = boardFromLastTurn.getAllReachablePositionsMatrix(new Position(currentPosition));

        /*   Debug  */
        /*
        System.out.printf("   ");

        for (int i = 0; i <7; i++) {
            System.out.printf("%2d ",i);
        }
        System.out.println();
        for (int i = 0; i <8; i++) {
            System.out.printf("---");
        }
        System.out.println();
        for (int i = 0; i <7; i++) {
            System.out.printf("%2d|",i);
            for (int j = 0; j < 7; j++) {
                System.out.printf("%2d ",reach[i][j]);
            }
            System.out.println();

        }
        System.out.println("start from: "+currentPosition.getRow()+"/"+currentPosition.getCol());
        System.out.println("try to reach: "+finalPinPos.getRow()+"/"+finalPinPos.getCol());
        /* Debug Ende */

        List<Position> path = getPathtoPosition(new Position(finalPinPos), reach);

        System.out.print("Pfad: ");
        for (PositionType p : path) {
            System.out.print("(" + p.getRow() + "/" + p.getCol() + ") ");
        }
        System.out.println();

        Platform.runLater(() -> animateMove(mm, b, path, moveDelay, shiftDelay, treasureReached));


        long delay = path.size() * moveDelay + shiftDelay;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boardFromLastTurn = (Board) b.clone();
    }

    @Override
    public void updatePlayerStatistics(List<Player> stats, Integer current) {
        Platform.runLater(() -> this.updatePlayerStats(stats, current));

    }

    @Override
    public void init(Board b) {
        boardFromLastTurn = (Board) b.clone();
        // Needed to avoid JavaFX initialition Errors
        // see https://rterp.wordpress.com/2015/04/04/javafx-toolkit-not-initialized-solved/
        //JFXPanel startJFX=new JFXPanel();
        //initFromBoard(b);
        Platform.runLater(() -> initFromBoard(b));
    }

    @Override
    public void setGame(Game g) {
        this.game = g;
        if (g == null) {
            // Platform.runLater(()->clearBoard());
        } else {
            // g.start();
        }
    }

    @Override
    public void gameEnded(Player winner) {
        controller.gameStopped();
    }
}
