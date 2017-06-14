//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MoveMessageType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MoveMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="shiftPosition" type="{}positionType"/>
 *         &lt;element name="newPinPos" type="{}positionType"/>
 *         &lt;element name="shiftCard" type="{}cardType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveMessageType", propOrder = {

})
public class MoveMessageType {

    @XmlElement(required = true)
    protected PositionType shiftPosition;
    @XmlElement(required = true)
    protected PositionType newPinPos;
    @XmlElement(required = true)
    protected CardType shiftCard;

    /**
     * Ruft den Wert der shiftPosition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getShiftPosition() {
        return shiftPosition;
    }

    /**
     * Legt den Wert der shiftPosition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setShiftPosition(PositionType value) {
        this.shiftPosition = value;
    }

    /**
     * Ruft den Wert der newPinPos-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getNewPinPos() {
        return newPinPos;
    }

    /**
     * Legt den Wert der newPinPos-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setNewPinPos(PositionType value) {
        this.newPinPos = value;
    }

    /**
     * Ruft den Wert der shiftCard-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CardType }
     *     
     */
    public CardType getShiftCard() {
        return shiftCard;
    }

    /**
     * Legt den Wert der shiftCard-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CardType }
     *     
     */
    public void setShiftCard(CardType value) {
        this.shiftCard = value;
    }

}
