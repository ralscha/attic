
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeOfRecord_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="typeOfRecord_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="MAIN"/>
 *     &lt;enumeration value="SECONDARY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeOfRecord_Type")
@XmlEnum
public enum TypeOfRecordType {

    MAIN,
    SECONDARY;

    public String value() {
        return name();
    }

    public static TypeOfRecordType fromValue(String v) {
        return valueOf(v);
    }

}
