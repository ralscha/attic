
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ch.ech.xmlns.ech_0008._2.CountryType;


/**
 * <p>Java class for nationalityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nationalityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nationalityStatus" type="{http://www.ech.ch/xmlns/eCH-0011/3}nationalityStatusType"/>
 *         &lt;element name="country" type="{http://www.ech.ch/xmlns/eCH-0008/2}countryType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nationalityType", propOrder = {
    "nationalityStatus",
    "country"
})
public class NationalityType {

    @XmlElement(required = true)
    protected String nationalityStatus;
    protected CountryType country;

    /**
     * Gets the value of the nationalityStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalityStatus() {
        return nationalityStatus;
    }

    /**
     * Sets the value of the nationalityStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalityStatus(String value) {
        this.nationalityStatus = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setCountry(CountryType value) {
        this.country = value;
    }

}
