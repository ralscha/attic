
package com.trivadis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MandatorShort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MandatorShort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AstraApplicationname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AstraMandatorname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MandatorShort", propOrder = {
    "astraApplicationname",
    "astraMandatorname"
})
@XmlSeeAlso({
    Mandator.class
})
public class MandatorShort {

    @XmlElement(name = "AstraApplicationname")
    protected String astraApplicationname;
    @XmlElement(name = "AstraMandatorname")
    protected String astraMandatorname;

    /**
     * Gets the value of the astraApplicationname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAstraApplicationname() {
        return astraApplicationname;
    }

    /**
     * Sets the value of the astraApplicationname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAstraApplicationname(String value) {
        this.astraApplicationname = value;
    }

    /**
     * Gets the value of the astraMandatorname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAstraMandatorname() {
        return astraMandatorname;
    }

    /**
     * Sets the value of the astraMandatorname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAstraMandatorname(String value) {
        this.astraMandatorname = value;
    }

}
