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
 * <p>Java-Klasse für cardType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="cardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openings">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="top" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="bottom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="left" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="right" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pin">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="playerID" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="4" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="treasure" type="{}treasureType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cardType", propOrder = {
    "openings",
    "pin",
    "treasure"
})
public class CardType {

    @XmlElement(required = true)
    protected Openings openings;
    @XmlElement(required = true)
    protected Pin pin;
    @XmlSchemaType(name = "string")
    protected TreasureType treasure;

    /**
     * Ruft den Wert der openings-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Openings }
     *     
     */
    public Openings getOpenings() {
        return openings;
    }

    /**
     * Legt den Wert der openings-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Openings }
     *     
     */
    public void setOpenings(Openings value) {
        this.openings = value;
    }

    /**
     * Ruft den Wert der pin-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Pin }
     *     
     */
    public Pin getPin() {
        return pin;
    }

    /**
     * Legt den Wert der pin-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Pin }
     *     
     */
    public void setPin(Pin value) {
        this.pin = value;
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


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="top" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="bottom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="left" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="right" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "top",
        "bottom",
        "left",
        "right"
    })
    public static class Openings {

        protected boolean top;
        protected boolean bottom;
        protected boolean left;
        protected boolean right;

        /**
         * Ruft den Wert der top-Eigenschaft ab.
         * 
         */
        public boolean isTop() {
            return top;
        }

        /**
         * Legt den Wert der top-Eigenschaft fest.
         * 
         */
        public void setTop(boolean value) {
            this.top = value;
        }

        /**
         * Ruft den Wert der bottom-Eigenschaft ab.
         * 
         */
        public boolean isBottom() {
            return bottom;
        }

        /**
         * Legt den Wert der bottom-Eigenschaft fest.
         * 
         */
        public void setBottom(boolean value) {
            this.bottom = value;
        }

        /**
         * Ruft den Wert der left-Eigenschaft ab.
         * 
         */
        public boolean isLeft() {
            return left;
        }

        /**
         * Legt den Wert der left-Eigenschaft fest.
         * 
         */
        public void setLeft(boolean value) {
            this.left = value;
        }

        /**
         * Ruft den Wert der right-Eigenschaft ab.
         * 
         */
        public boolean isRight() {
            return right;
        }

        /**
         * Legt den Wert der right-Eigenschaft fest.
         * 
         */
        public void setRight(boolean value) {
            this.right = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="playerID" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="4" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "playerID"
    })
    public static class Pin {

        @XmlElement(type = Integer.class)
        protected List<Integer> playerID;

        /**
         * Gets the value of the playerID property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the playerID property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlayerID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getPlayerID() {
            if (playerID == null) {
                playerID = new ArrayList<Integer>();
            }
            return this.playerID;
        }

    }

}
