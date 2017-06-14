package de.fhac.mazenet.server.config;

import de.fhac.mazenet.server.tools.DebugLevel;
import de.fhac.mazenet.server.userinterface.UI;
import de.fhac.mazenet.server.userinterface.betterUI.BetterUI;
import de.fhac.mazenet.server.userinterface.mazeFX.MazeFX;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class Settings {
	public static int PORT = 5123;
    public static int SSL_PORT = 5124;

    public static String SSL_CERT_STORE="maze-ssl.jks";
    public static String SSL_CERT_STORE_PASSWD="NukabWiod";

    /**
	 * Die maximal erlaubte Laenge des Loginnamens
	 */
	public static int MAX_NAME_LENGTH = 30;

	/**
	 * Den Detailgrad der Ausgaben festlegen
	 */
	public final static DebugLevel DEBUGLEVEL = DebugLevel.DEFAULT;

	/**
	 * Startwert fuer die Spieleranzahl Kann aber noch veraendert werden,
	 * deshalb nicht final
	 */
	public static int DEFAULT_PLAYERS = 1;
	public  static String IMAGEFILEEXTENSION = ".png"; //$NON-NLS-1$
	/**
	 * Auf das angehaengte / achten
	 */
	public  static String IMAGEPATH = "/images/"; //$NON-NLS-1$
	public  static Locale LOCALE = new Locale("de"); //$NON-NLS-1$
	/**
	 * Die Zeit in Milisekunden, nach der ein Logintimeout eintritt LOGINTIMEOUT
	 * = 60000 entspricht einer Minute
	 */
	public  static long LOGINTIMEOUT = 2 * 60000;
	public  static int LOGINTRIES = 3;
	/**
	 * Die Zeit in Milisekunden, die die Animation eines Zug (die Bewegung des
	 * Pins) benoetigen soll
	 */
	public static int MOVEDELAY = 300;
	/**
	 * Die maximale Anzahl der Versuche einen gueltigen Zug zu uebermitteln
	 */
	public static int MOVETRIES = 3;
	public static long SENDTIMEOUT = 1 * 20 * 1000;
	/**
	 * Die Zeit in Milisekunden, die das Einschieben der Shiftcard dauern soll
	 */
	public static int SHIFTDELAY = 1000;
	/**
	 * Wenn TESTBOARD = true ist, dann ist das Spielbrett bei jedem Start
	 * identisch (zum Debugging)
	 */
	public static boolean TESTBOARD = true;
	/**
	 * Hiermit lassen sich die Testfaelle anpassen (Pseudozufallszahlen)
	 */
	public static long TESTBOARD_SEED = 0;
	/**
	 * USERINTERFACE definiert die zu verwendende GUI Gueltige Werte:
	 * BetterUI(), GraphicalUI()
	 */
	public static UI USERINTERFACE = new BetterUI();

	@SuppressWarnings("nls")
	public static void reload(String path){
		Properties prop = new Properties();
		try {
			InputStream propStream=Settings.class.getResourceAsStream(path);
			prop.load(propStream);;
			propStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MAX_NAME_LENGTH = Integer.parseInt(prop.get("MAX_NAME_LENGTH").toString());
		DEFAULT_PLAYERS = Integer.parseInt(prop.get("DEFAULT_PLAYERS").toString()); 
		IMAGEFILEEXTENSION = prop.getProperty("IMAGEFILEEXTENSION").toString(); 
		IMAGEPATH = prop.getProperty("IMAGEPATH").toString(); 
		LOCALE = new Locale(prop.get("LOCALE").toString()); 
		LOGINTIMEOUT = Integer.parseInt(prop.get("LOGINTIMEOUT").toString());
		LOGINTRIES = Integer.parseInt(prop.get("LOGINTRIES").toString()); 
		MOVEDELAY = Integer.parseInt(prop.get("MOVEDELAY").toString()); 
		MOVETRIES = Integer.parseInt(prop.get("MOVETRIES").toString()); 
		PORT = Integer.parseInt(prop.get("PORT").toString());
		SSL_PORT = Integer.parseInt(prop.get("SSL_PORT").toString());
		SSL_CERT_STORE = prop.getProperty("SSL_CERT_STORE");
		SSL_CERT_STORE_PASSWD =prop.getProperty("SSL_CERT_STORE_PASSWD");
		SENDTIMEOUT = Integer.parseInt(prop.get("SENDTIMEOUT").toString());//.split(" * ")[0])*Integer.parseInt(prop.get("SENDTIMEOUT").toString().split(" * ")[1])*Integer.parseInt(prop.get("SENDTIMEOUT").toString().split(" * ")[2]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		SHIFTDELAY = Integer.parseInt(prop.get("SHIFTDELAY").toString()); 
		TESTBOARD = Boolean.parseBoolean(prop.get("TESTBOARD").toString()); //$NON-NLS-1$
		TESTBOARD_SEED = Integer.parseInt(prop.get("TESTBOARD_SEED").toString());
		String ui=prop.get("USERINTERFACE").toString();
		switch(ui){
			case "BetterUI":
				USERINTERFACE=new BetterUI();
				break;
			case "MazeFX":
				USERINTERFACE= MazeFX.newInstance();
				break;
			default:
				USERINTERFACE=null;
				break;

		}
		//print();
	}
	
	private Settings() {
	}
	
	@SuppressWarnings("nls")
	public static void print(){
		//TODO vervollstaendigen
		System.out.println("Imagepath: "+Settings.IMAGEPATH);
		System.out.println("Imageext: "+Settings.IMAGEFILEEXTENSION);

	}
}
