package de.fhac.mazenet.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLServerSocketFactory;

import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.ErrorType;
import de.fhac.mazenet.server.generated.MoveMessageType;
import de.fhac.mazenet.server.generated.TreasureType;
import de.fhac.mazenet.server.networking.Connection;
import de.fhac.mazenet.server.timeouts.TimeOutManager;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.tools.DebugLevel;
import de.fhac.mazenet.server.userinterface.UI;

public class Game extends Thread 
{

    /**
     * beinhaltet die Spieler, die mit dem Server verbunden sind und die durch
     * die ID zugreifbar sind
     */
    private HashMap<Integer, Player> spieler;
    private ServerSocket serverSocket;
    private ServerSocket sslServerSocket;
    private TimeOutManager timeOutManager;
    private Board spielBrett;
    /**
     * Defaultwert -1, solange kein Gewinner feststeht
     */
    private Integer winner = -1;
    private UI userinterface;
    private int playerCount;
    private List<TreasureType> foundTreasures;

    public Game() 
    {
        Debug.addDebugger(System.out, Settings.DEBUGLEVEL);
        Debug.print(Messages.getString("Game.Constructor"), DebugLevel.VERBOSE); //$NON-NLS-1$
        winner = -1;
        spieler = new HashMap<Integer, Player>();
        timeOutManager = new TimeOutManager();
        foundTreasures = new ArrayList<TreasureType>();
    }

    /**
     * Auf TCP Verbindungen warten und den Spielern die Verbindung ermoeglichen
     */
    public void init(int playerCount) 
    {

        Debug.print(Messages.getString("Game.initFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        // Socketinitialisierung aus dem Constructor in init verschoben. Sonst
        // Errors wegen Thread.
        // init wird von run (also vom Thread) aufgerufen, im Gegesatz zum
        // Constructor
        try 
        {

            //unencrypted
            serverSocket = new ServerSocket(Settings.PORT);

            //encrtypted

            // Setup SSL by setting keystore and password
            System.setProperty("javax.net.ssl.keyStorePassword", Settings.SSL_CERT_STORE_PASSWD);
            System.setProperty("javax.net.ssl.keyStore", Settings.SSL_CERT_STORE);
            
            //create SSLServerSocket
            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslServerSocket = ssf.createServerSocket(Settings.SSL_PORT);


        }
        catch (UnknownHostException e) 
	    {
        	Debug.print(Messages.getString("Game.portUsed"),DebugLevel.DEFAULT); //$NON-NLS-1$
		}
        catch (IOException e) {
            //FIXME differentiate between SSL Error und Port used error
        	System.out.println("HALLLLO");
            System.err.println(e.getLocalizedMessage());
            Debug.print(Messages.getString("Game.portUsed"),DebugLevel.DEFAULT); //$NON-NLS-1$
        }
        timeOutManager.startLoginTimeOut(this);
        Stack<Integer> availableIds = new Stack<>();
        List<String> connectedIPs = new ArrayList<>();

        for (int i = 1; i <= playerCount; i++) {
            availableIds.push(i);
        }
        if (!Settings.TESTBOARD)
            Collections.shuffle(availableIds);
        
        //preparing Tasks for SSL and unencrypted
        final CyclicBarrier barrier = new CyclicBarrier(2);
        final Socket[] mazeClient = {null};
        Runnable waitForConnectionTask = () -> {
            try {
                mazeClient[0] = serverSocket.accept();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println(Messages.getString("Game.errorWhileConnecting")); //$NON-NLS-1$
                System.err.println(e.getMessage());                }
        };
        Runnable waitForSSLConnectionTask = () -> {
            try {
                mazeClient[0] = sslServerSocket.accept();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println(Messages.getString("Game.errorWhileConnecting")); //$NON-NLS-1$
                System.err.println(e.getMessage());                }
        };
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<?> noSSLStatus =null;Future<?> SSLStatus=null;

        
        // Warten bis die Initialisierung durchgelaufen ist
        boolean spielbereit = false;
        while (!spielbereit) 
        {
            // FIXME: wenn sich kein Spieler verbindet, sollte ewig gewartet
            // werden
            while (!availableIds.isEmpty()) 
            {
                try {
                    int id = availableIds.pop();
                    Debug.print(Messages.getString("Game.waitingForPlayer") + " (" //$NON-NLS-1$ //$NON-NLS-2$
                            + (playerCount - availableIds.size()) + "/" + playerCount //$NON-NLS-1$
                            + ")", DebugLevel.DEFAULT); //$NON-NLS-1$

                    if(noSSLStatus == null || noSSLStatus.isDone()==true) {
                        noSSLStatus = pool.submit(waitForConnectionTask);
                    }
                    if(SSLStatus == null || SSLStatus.isDone()==true) {
                        SSLStatus = pool.submit(waitForSSLConnectionTask);
                    }
                    barrier.await();

                    String ip = mazeClient[0].getInetAddress().getHostAddress();
                    if (!connectedIPs.contains(ip)) 
                    {
                        if (!ip.equals("127.0.0.1")) 
                        { //$NON-NLS-1$
                            connectedIPs.add(ip);
                        }
                        Connection c = new Connection(mazeClient[0], this, id);
                        spieler.put(id, c.login(id, availableIds));
                    } 
                    else 
                    {
                        Debug.print(String.format(Messages.getString("Game.HostAlreadyConnected"), ip), //$NON-NLS-1$
                                DebugLevel.DEFAULT);
                    }
                } 
                catch (InterruptedException e) 
                {
                    Debug.print(Messages.getString("Game.playerWaitingTimedOut"), //$NON-NLS-1$
                            DebugLevel.DEFAULT);

                } 
                catch (BrokenBarrierException e)
                {
                    e.printStackTrace();
                }
            }
            spielbereit = true;
            for (Integer id : spieler.keySet()) 
            {
                Player p = spieler.get(id);
                if (!p.isInitialized()) // login des Players schlug fehl
                {
                    spielbereit = false;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdownNow();
        timeOutManager.stopLoginTimeOut();
        
        // Spielbrett generieren
        spielBrett = new Board();
        
        // Verteilen der Schatzkarten
        ArrayList<TreasureType> treasureCardPile = new ArrayList<TreasureType>();
        treasureCardPile.add(TreasureType.SYM_01);
        treasureCardPile.add(TreasureType.SYM_02);
        treasureCardPile.add(TreasureType.SYM_03);
        treasureCardPile.add(TreasureType.SYM_04);
        treasureCardPile.add(TreasureType.SYM_05);
        treasureCardPile.add(TreasureType.SYM_06);
        treasureCardPile.add(TreasureType.SYM_07);
        treasureCardPile.add(TreasureType.SYM_08);
        treasureCardPile.add(TreasureType.SYM_09);
        treasureCardPile.add(TreasureType.SYM_10);
        treasureCardPile.add(TreasureType.SYM_11);
        treasureCardPile.add(TreasureType.SYM_12);
        treasureCardPile.add(TreasureType.SYM_13);
        treasureCardPile.add(TreasureType.SYM_14);
        treasureCardPile.add(TreasureType.SYM_15);
        treasureCardPile.add(TreasureType.SYM_16);
        treasureCardPile.add(TreasureType.SYM_17);
        treasureCardPile.add(TreasureType.SYM_18);
        treasureCardPile.add(TreasureType.SYM_19);
        treasureCardPile.add(TreasureType.SYM_20);
        treasureCardPile.add(TreasureType.SYM_21);
        treasureCardPile.add(TreasureType.SYM_22);
        treasureCardPile.add(TreasureType.SYM_23);
        treasureCardPile.add(TreasureType.SYM_24);
        if (!Settings.TESTBOARD)
            Collections.shuffle(treasureCardPile);
        if (spieler.size() == 0) {
            System.err.println(Messages.getString("Game.noPlayersConnected")); //$NON-NLS-1$
            stopGame();
            return;
        }
        int anzCards = treasureCardPile.size() / spieler.size();
        int i = 0;
        for (Integer player : spieler.keySet()) 
        {
            ArrayList<TreasureType> cardsPerPlayer = new ArrayList<TreasureType>();
            for (int j = i * anzCards; j < (i + 1) * anzCards; j++) 
            {
                cardsPerPlayer.add(treasureCardPile.get(j));
            }
            spieler.get(player).setTreasure(cardsPerPlayer);
            ++i;
        }

    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            } else
                Debug.print(Messages.getString("Game.serverSocketNull"), DebugLevel.DEFAULT); //$NON-NLS-1$
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Player> playerToList() {
        Debug.print(Messages.getString("Game.playerToListFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        List<Player> erg = new ArrayList<Player>();
        for (Integer id : spieler.keySet()) {
            erg.add(spieler.get(id));
        }
        return erg;
    }

    public void singleTurn(Integer currPlayer) 
    {
        /**
         * Connection.awaitMove checken -> Bei Fehler illegalMove -> liefert
         * neuen Zug
         */
        Debug.print(Messages.getString("Game.singleTurnFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        userinterface.updatePlayerStatistics(playerToList(), currPlayer);
        TreasureType t = spieler.get(currPlayer).getCurrentTreasure();
        spielBrett.setTreasure(t);
        Debug.print(Messages.getString("Game.boardBeforeMoveFromPlayerWithID") + currPlayer, //$NON-NLS-1$
                DebugLevel.VERBOSE);
        Debug.print(spielBrett.toString(), DebugLevel.DEBUG);
        MoveMessageType move = spieler.get(currPlayer).getConToClient().awaitMove(spieler, this.spielBrett, 0,
                foundTreasures);
        boolean found = false;
        if (move != null) 
        {
            // proceedTurn gibt zurueck ob der Spieler seinen Schatz erreicht
            // hat
            if (spielBrett.proceedTurn(move, currPlayer)) 
            {
                found = true;
                Debug.print(String.format(Messages.getString("Game.foundTreasure"), spieler.get(currPlayer).getName(), //$NON-NLS-1$
                        currPlayer), DebugLevel.DEFAULT);
                foundTreasures.add(t);
                // foundTreasure gibt zurueck wieviele
                // Schaetze noch zu finden sind
                if (spieler.get(currPlayer).foundTreasure() == 0) {
                    winner = currPlayer;
                }
            }
            userinterface.displayMove(move, spielBrett, Settings.MOVEDELAY, Settings.SHIFTDELAY, found);
        } 
        else 
        {
            Debug.print(Messages.getString("Game.gotNoMove"), DebugLevel.DEFAULT); //$NON-NLS-1$
        }
    }

    public Board getBoard() {
        return spielBrett;
    }

    /**
     * Aufraeumen nach einem Spiel
     */
    public void cleanUp() {
        Debug.print(Messages.getString("Game.cleanUpFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        if (winner > 0) {
            for (Integer playerID : spieler.keySet()) {
                Player s = spieler.get(playerID);
                s.getConToClient().sendWin(winner, spieler.get(winner).getName(), spielBrett);
            }
            userinterface.updatePlayerStatistics(playerToList(), winner);
            Debug.print(String.format(Messages.getString("Game.playerIDwon"), spieler //$NON-NLS-1$
                    .get(winner).getName(), winner), DebugLevel.DEFAULT);

        } else {
            // Iterator<Integer> playerID = spieler.keySet().iterator();
            // while (playerID.hasNext() ) {
            // Player s = spieler.get(playerID.next());
            // s.getConToClient().disconnect(ErrorType.NOERROR);
            // }
            while (spieler.size() > 0) {
                Player s = spieler.get(spieler.keySet().iterator().next());
                s.getConToClient().disconnect(ErrorType.NOERROR);

            }
        }
        stopGame();
    }

    public boolean somebodyWon() {
        return winner != -1;

    }

    public static void main(String[] args) 
    {
        Settings.reload("/config.properties"); //$NON-NLS-1$
        Locale.setDefault(Settings.LOCALE);
        Game currentGame = new Game();
        currentGame.parsArgs(args);
        
        // UI ist BetterUI und loest game.run()-Methode aus 
        currentGame.userinterface = Settings.USERINTERFACE;
        currentGame.userinterface.init(new Board());
        currentGame.userinterface.setGame(currentGame);
    }

    public void setUserinterface(UI userinterface) {
        this.userinterface = userinterface;
    }

    public void parsArgs(String args[]) {
        playerCount = Settings.DEFAULT_PLAYERS;
        for (String arg : args) {
            String playerFlag = "-n"; //$NON-NLS-1$
            if (arg.startsWith(playerFlag)) {
                playerCount = Integer.valueOf(arg.substring(playerFlag.length()));
            }
        }
    }

    public void run() 
    {
        Debug.print(Messages.getString("Game.runFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        Debug.print(Messages.getString("Game.startNewGame"), DebugLevel.DEFAULT); //$NON-NLS-1$
        // TODO Configfile austauschbar machen
        
        // erfolgt Initialisierung aller Spieler
        init(playerCount);
        if (spieler.isEmpty()) {
            return;
        }
                
        userinterface.init(spielBrett);
        
        Integer currPlayer = nextPlayer(0);
        userinterface.updatePlayerStatistics(playerToList(), currPlayer);
        
        // start playing
        while (!somebodyWon()) 
        {
            Debug.print(String.format(Messages.getString("Game.playersTurn"), spieler.get(currPlayer).getName(), //$NON-NLS-1$
                    currPlayer), DebugLevel.DEFAULT);
            singleTurn(currPlayer);
            try 
            {
                currPlayer = nextPlayer(currPlayer);
            } 
            catch (NoSuchElementException e)
            {
                Debug.print(Messages.getString("Game.AllPlayersLeft"), DebugLevel.DEFAULT); //$NON-NLS-1$
                stopGame();
            }
        }
        cleanUp();
    }

    private Integer nextPlayer(Integer currPlayer) throws NoSuchElementException 
    {
        Debug.print(Messages.getString("Game.nextPlayerFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        Iterator<Integer> iDIterator = spieler.keySet().iterator();
        Integer id = -1;
        while (iDIterator.hasNext()) {
            id = iDIterator.next();
            if (id == currPlayer) {
                break;
            }
        }
        if (iDIterator.hasNext()) {
            return iDIterator.next();
        }
        // Erste ID zurueckgeben,
        return spieler.keySet().iterator().next();
    }

    public void removePlayer(int id) {
        Debug.print(Messages.getString("Game.removePlayerFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        this.spieler.remove(id);
        Debug.print(String.format(Messages.getString("Game.playerIDleftGame"), id), //$NON-NLS-1$
                DebugLevel.DEFAULT);
    }

    public void stopGame() {
        Debug.print(Messages.getString("Game.stopFkt"), DebugLevel.VERBOSE); //$NON-NLS-1$
        Debug.print(Messages.getString("Game.stopGame"), DebugLevel.DEFAULT); //$NON-NLS-1$
        userinterface.gameEnded(spieler.get(winner));
        winner = -2;
        userinterface.setGame(null);
        timeOutManager.cancel();
        closeServerSocket();
    }
}
