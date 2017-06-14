//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="LoginMessage" type="{}LoginMessageType"/>
 *         &lt;element name="LoginReplyMessage" type="{}LoginReplyMessageType"/>
 *         &lt;element name="AwaitMoveMessage" type="{}AwaitMoveMessageType"/>
 *         &lt;element name="MoveMessage" type="{}MoveMessageType"/>
 *         &lt;element name="AcceptMessage" type="{}AcceptMessageType"/>
 *         &lt;element name="WinMessage" type="{}WinMessageType"/>
 *         &lt;element name="DisconnectMessage" type="{}DisconnectMessageType"/>
 *       &lt;/choice>
 *       &lt;attribute name="mcType" use="required" type="{}MazeComType" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "loginMessage",
    "loginReplyMessage",
    "awaitMoveMessage",
    "moveMessage",
    "acceptMessage",
    "winMessage",
    "disconnectMessage"
})
@XmlRootElement(name = "MazeCom")
public class MazeCom {

    @XmlElement(name = "LoginMessage")
    protected LoginMessageType loginMessage;
    @XmlElement(name = "LoginReplyMessage")
    protected LoginReplyMessageType loginReplyMessage;
    @XmlElement(name = "AwaitMoveMessage")
    protected AwaitMoveMessageType awaitMoveMessage;
    @XmlElement(name = "MoveMessage")
    protected MoveMessageType moveMessage;
    @XmlElement(name = "AcceptMessage")
    protected AcceptMessageType acceptMessage;
    @XmlElement(name = "WinMessage")
    protected WinMessageType winMessage;
    @XmlElement(name = "DisconnectMessage")
    protected DisconnectMessageType disconnectMessage;
    @XmlAttribute(name = "mcType", required = true)
    protected MazeComType mcType;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Ruft den Wert der loginMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LoginMessageType }
     *     
     */
    public LoginMessageType getLoginMessage() {
        return loginMessage;
    }

    /**
     * Legt den Wert der loginMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginMessageType }
     *     
     */
    public void setLoginMessage(LoginMessageType value) {
        this.loginMessage = value;
    }

    /**
     * Ruft den Wert der loginReplyMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LoginReplyMessageType }
     *     
     */
    public LoginReplyMessageType getLoginReplyMessage() {
        return loginReplyMessage;
    }

    /**
     * Legt den Wert der loginReplyMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginReplyMessageType }
     *     
     */
    public void setLoginReplyMessage(LoginReplyMessageType value) {
        this.loginReplyMessage = value;
    }

    /**
     * Ruft den Wert der awaitMoveMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AwaitMoveMessageType }
     *     
     */
    public AwaitMoveMessageType getAwaitMoveMessage() {
        return awaitMoveMessage;
    }

    /**
     * Legt den Wert der awaitMoveMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AwaitMoveMessageType }
     *     
     */
    public void setAwaitMoveMessage(AwaitMoveMessageType value) {
        this.awaitMoveMessage = value;
    }

    /**
     * Ruft den Wert der moveMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MoveMessageType }
     *     
     */
    public MoveMessageType getMoveMessage() {
        return moveMessage;
    }

    /**
     * Legt den Wert der moveMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MoveMessageType }
     *     
     */
    public void setMoveMessage(MoveMessageType value) {
        this.moveMessage = value;
    }

    /**
     * Ruft den Wert der acceptMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AcceptMessageType }
     *     
     */
    public AcceptMessageType getAcceptMessage() {
        return acceptMessage;
    }

    /**
     * Legt den Wert der acceptMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptMessageType }
     *     
     */
    public void setAcceptMessage(AcceptMessageType value) {
        this.acceptMessage = value;
    }

    /**
     * Ruft den Wert der winMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link WinMessageType }
     *     
     */
    public WinMessageType getWinMessage() {
        return winMessage;
    }

    /**
     * Legt den Wert der winMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link WinMessageType }
     *     
     */
    public void setWinMessage(WinMessageType value) {
        this.winMessage = value;
    }

    /**
     * Ruft den Wert der disconnectMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DisconnectMessageType }
     *     
     */
    public DisconnectMessageType getDisconnectMessage() {
        return disconnectMessage;
    }

    /**
     * Legt den Wert der disconnectMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DisconnectMessageType }
     *     
     */
    public void setDisconnectMessage(DisconnectMessageType value) {
        this.disconnectMessage = value;
    }

    /**
     * Ruft den Wert der mcType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MazeComType }
     *     
     */
    public MazeComType getMcType() {
        return mcType;
    }

    /**
     * Legt den Wert der mcType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MazeComType }
     *     
     */
    public void setMcType(MazeComType value) {
        this.mcType = value;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
