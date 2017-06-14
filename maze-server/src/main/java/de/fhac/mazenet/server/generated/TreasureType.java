//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.05.27 um 09:04:47 AM CEST 
//


package de.fhac.mazenet.server.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für treasureType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="treasureType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Start01"/>
 *     &lt;enumeration value="Start02"/>
 *     &lt;enumeration value="Start03"/>
 *     &lt;enumeration value="Start04"/>
 *     &lt;enumeration value="sym01"/>
 *     &lt;enumeration value="sym02"/>
 *     &lt;enumeration value="sym03"/>
 *     &lt;enumeration value="sym04"/>
 *     &lt;enumeration value="sym05"/>
 *     &lt;enumeration value="sym06"/>
 *     &lt;enumeration value="sym07"/>
 *     &lt;enumeration value="sym08"/>
 *     &lt;enumeration value="sym09"/>
 *     &lt;enumeration value="sym10"/>
 *     &lt;enumeration value="sym11"/>
 *     &lt;enumeration value="sym12"/>
 *     &lt;enumeration value="sym13"/>
 *     &lt;enumeration value="sym14"/>
 *     &lt;enumeration value="sym15"/>
 *     &lt;enumeration value="sym16"/>
 *     &lt;enumeration value="sym17"/>
 *     &lt;enumeration value="sym18"/>
 *     &lt;enumeration value="sym19"/>
 *     &lt;enumeration value="sym20"/>
 *     &lt;enumeration value="sym21"/>
 *     &lt;enumeration value="sym22"/>
 *     &lt;enumeration value="sym23"/>
 *     &lt;enumeration value="sym24"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "treasureType")
@XmlEnum
@SuppressWarnings("nls")
public enum TreasureType {

	@XmlEnumValue("Start01")
    START_01("Start01"),
    @XmlEnumValue("Start02")
    START_02("Start02"),
    @XmlEnumValue("Start03")
    START_03("Start03"),
    @XmlEnumValue("Start04")
    START_04("Start04"),
    @XmlEnumValue("sym01")
    SYM_01("sym01"),
    @XmlEnumValue("sym02")
    SYM_02("sym02"),
    @XmlEnumValue("sym03")
    SYM_03("sym03"),
    @XmlEnumValue("sym04")
    SYM_04("sym04"),
    @XmlEnumValue("sym05")
    SYM_05("sym05"),
    @XmlEnumValue("sym06")
    SYM_06("sym06"),
    @XmlEnumValue("sym07")
    SYM_07("sym07"),
    @XmlEnumValue("sym08")
    SYM_08("sym08"),
    @XmlEnumValue("sym09")
    SYM_09("sym09"),
    @XmlEnumValue("sym10")
    SYM_10("sym10"),
    @XmlEnumValue("sym11")
    SYM_11("sym11"),
    @XmlEnumValue("sym12")
    SYM_12("sym12"),
    @XmlEnumValue("sym13")
    SYM_13("sym13"),
    @XmlEnumValue("sym14")
    SYM_14("sym14"),
    @XmlEnumValue("sym15")
    SYM_15("sym15"),
    @XmlEnumValue("sym16")
    SYM_16("sym16"),
    @XmlEnumValue("sym17")
    SYM_17("sym17"),
    @XmlEnumValue("sym18")
    SYM_18("sym18"),
    @XmlEnumValue("sym19")
    SYM_19("sym19"),
    @XmlEnumValue("sym20")
    SYM_20("sym20"),
    @XmlEnumValue("sym21")
    SYM_21("sym21"),
    @XmlEnumValue("sym22")
    SYM_22("sym22"),
    @XmlEnumValue("sym23")
    SYM_23("sym23"),
    @XmlEnumValue("sym24")
    SYM_24("sym24");
    private final String value;

    TreasureType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TreasureType fromValue(String v) {
        for (TreasureType c: TreasureType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
