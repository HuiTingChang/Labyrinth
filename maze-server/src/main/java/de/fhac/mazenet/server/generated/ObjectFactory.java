//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CardType }
     * 
     */
    public CardType createCardType() {
        return new CardType();
    }

    /**
     * Create an instance of {@link BoardType }
     * 
     */
    public BoardType createBoardType() {
        return new BoardType();
    }

    /**
     * Create an instance of {@link WinMessageType }
     * 
     */
    public WinMessageType createWinMessageType() {
        return new WinMessageType();
    }

    /**
     * Create an instance of {@link MazeCom }
     * 
     */
    public MazeCom createMazeCom() {
        return new MazeCom();
    }

    /**
     * Create an instance of {@link LoginMessageType }
     * 
     */
    public LoginMessageType createLoginMessageType() {
        return new LoginMessageType();
    }

    /**
     * Create an instance of {@link LoginReplyMessageType }
     * 
     */
    public LoginReplyMessageType createLoginReplyMessageType() {
        return new LoginReplyMessageType();
    }

    /**
     * Create an instance of {@link AwaitMoveMessageType }
     * 
     */
    public AwaitMoveMessageType createAwaitMoveMessageType() {
        return new AwaitMoveMessageType();
    }

    /**
     * Create an instance of {@link MoveMessageType }
     * 
     */
    public MoveMessageType createMoveMessageType() {
        return new MoveMessageType();
    }

    /**
     * Create an instance of {@link AcceptMessageType }
     * 
     */
    public AcceptMessageType createAcceptMessageType() {
        return new AcceptMessageType();
    }

    /**
     * Create an instance of {@link DisconnectMessageType }
     * 
     */
    public DisconnectMessageType createDisconnectMessageType() {
        return new DisconnectMessageType();
    }

    /**
     * Create an instance of {@link PositionType }
     * 
     */
    public PositionType createPositionType() {
        return new PositionType();
    }

    /**
     * Create an instance of {@link TreasuresToGoType }
     * 
     */
    public TreasuresToGoType createTreasuresToGoType() {
        return new TreasuresToGoType();
    }

    /**
     * Create an instance of {@link CardType.Openings }
     * 
     */
    public CardType.Openings createCardTypeOpenings() {
        return new CardType.Openings();
    }

    /**
     * Create an instance of {@link CardType.Pin }
     * 
     */
    public CardType.Pin createCardTypePin() {
        return new CardType.Pin();
    }

    /**
     * Create an instance of {@link BoardType.Row }
     * 
     */
    public BoardType.Row createBoardTypeRow() {
        return new BoardType.Row();
    }

    /**
     * Create an instance of {@link WinMessageType.Winner }
     * 
     */
    public WinMessageType.Winner createWinMessageTypeWinner() {
        return new WinMessageType.Winner();
    }

}
