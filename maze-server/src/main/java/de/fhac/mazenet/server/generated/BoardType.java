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
 * <p>Java-Klasse für boardType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="boardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="row" maxOccurs="7" minOccurs="7">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="col" type="{}cardType" maxOccurs="7" minOccurs="7"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="OptionalAttirbute" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="shiftCard" type="{}cardType"/>
 *         &lt;element name="forbidden" type="{}positionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boardType", propOrder = {
    "row",
    "shiftCard",
    "forbidden"
})
public class BoardType {

    @XmlElement(required = true)
    protected List<Row> row;
    @XmlElement(required = true)
    protected CardType shiftCard;
    protected PositionType forbidden;

    /**
     * Gets the value of the row property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the row property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Row }
     * 
     * 
     */
    public List<Row> getRow() {
        if (row == null) {
            row = new ArrayList<Row>();
        }
        return this.row;
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

    /**
     * Ruft den Wert der forbidden-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getForbidden() {
        return forbidden;
    }

    /**
     * Legt den Wert der forbidden-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setForbidden(PositionType value) {
        this.forbidden = value;
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
     *         &lt;element name="col" type="{}cardType" maxOccurs="7" minOccurs="7"/>
     *       &lt;/sequence>
     *       &lt;attribute name="OptionalAttirbute" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "col"
    })
    public static class Row {

        @XmlElement(required = true)
        protected List<CardType> col;
        @XmlAttribute(name = "OptionalAttirbute")
        protected String optionalAttirbute;

        /**
         * Gets the value of the col property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the col property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCol().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CardType }
         * 
         * 
         */
        public List<CardType> getCol() {
            if (col == null) {
                col = new ArrayList<CardType>();
            }
            return this.col;
        }

        /**
         * Ruft den Wert der optionalAttirbute-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOptionalAttirbute() {
            return optionalAttirbute;
        }

        /**
         * Legt den Wert der optionalAttirbute-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOptionalAttirbute(String value) {
            this.optionalAttirbute = value;
        }

    }

}
