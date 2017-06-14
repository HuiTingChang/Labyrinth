//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für TreasuresToGoType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TreasuresToGoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="player" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="treasures" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreasuresToGoType", propOrder = {
    "player",
    "treasures"
})
public class TreasuresToGoType {

    protected int player;
    protected int treasures;

    /**
     * Ruft den Wert der player-Eigenschaft ab.
     * 
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Legt den Wert der player-Eigenschaft fest.
     * 
     */
    public void setPlayer(int value) {
        this.player = value;
    }

    /**
     * Ruft den Wert der treasures-Eigenschaft ab.
     * 
     */
    public int getTreasures() {
        return treasures;
    }

    /**
     * Legt den Wert der treasures-Eigenschaft fest.
     * 
     */
    public void setTreasures(int value) {
        this.treasures = value;
    }

}
