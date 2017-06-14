//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für AwaitMoveMessageType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AwaitMoveMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="board" type="{}boardType"/>
 *         &lt;element name="treasuresToGo" type="{}TreasuresToGoType" maxOccurs="4"/>
 *         &lt;element name="foundTreasures" type="{}treasureType" maxOccurs="24" minOccurs="0"/>
 *         &lt;element name="treasure" type="{}treasureType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AwaitMoveMessageType", propOrder = {
    "board",
    "treasuresToGo",
    "foundTreasures",
    "treasure"
})
public class AwaitMoveMessageType {

    @XmlElement(required = true)
    protected BoardType board;
    @XmlElement(required = true)
    protected List<TreasuresToGoType> treasuresToGo;
    @XmlSchemaType(name = "string")
    protected List<TreasureType> foundTreasures;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TreasureType treasure;

    /**
     * Ruft den Wert der board-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BoardType }
     *     
     */
    public BoardType getBoard() {
        return board;
    }

    /**
     * Legt den Wert der board-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BoardType }
     *     
     */
    public void setBoard(BoardType value) {
        this.board = value;
    }

    /**
     * Gets the value of the treasuresToGo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treasuresToGo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreasuresToGo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreasuresToGoType }
     * 
     * 
     */
    public List<TreasuresToGoType> getTreasuresToGo() {
        if (treasuresToGo == null) {
            treasuresToGo = new ArrayList<TreasuresToGoType>();
        }
        return this.treasuresToGo;
    }

    /**
     * Gets the value of the foundTreasures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundTreasures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundTreasures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreasureType }
     * 
     * 
     */
    public List<TreasureType> getFoundTreasures() {
        if (foundTreasures == null) {
            foundTreasures = new ArrayList<TreasureType>();
        }
        return this.foundTreasures;
    }

    /**
     * Ruft den Wert der treasure-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TreasureType }
     *     
     */
    public TreasureType getTreasure() {
        return treasure;
    }

    /**
     * Legt den Wert der treasure-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TreasureType }
     *     
     */
    public void setTreasure(TreasureType value) {
        this.treasure = value;
    }

}
