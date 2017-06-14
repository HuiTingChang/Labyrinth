//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MazeComType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MazeComType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LOGIN"/>
 *     &lt;enumeration value="LOGINREPLY"/>
 *     &lt;enumeration value="AWAITMOVE"/>
 *     &lt;enumeration value="MOVE"/>
 *     &lt;enumeration value="ACCEPT"/>
 *     &lt;enumeration value="WIN"/>
 *     &lt;enumeration value="DISCONNECT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MazeComType")
@XmlEnum
public enum MazeComType {

    LOGIN,
    LOGINREPLY,
    AWAITMOVE,
    MOVE,
    ACCEPT,
    WIN,
    DISCONNECT;

    public String value() {
        return name();
    }

    public static MazeComType fromValue(String v) {
        return valueOf(v);
    }

}
