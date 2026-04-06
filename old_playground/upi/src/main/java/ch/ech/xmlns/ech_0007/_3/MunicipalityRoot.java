
package ch.ech.xmlns.ech_0007._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="swissMunicipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
 *         &lt;element name="swissAndFlMunicipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissAndFlMunicipalityType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "swissMunicipality",
    "swissAndFlMunicipality"
})
@XmlRootElement(name = "municipalityRoot")
public class MunicipalityRoot {

    protected SwissMunicipalityType swissMunicipality;
    protected SwissAndFlMunicipalityType swissAndFlMunicipality;

    /**
     * Gets the value of the swissMunicipality property.
     * 
     * @return
     *     possible object is
     *     {@link SwissMunicipalityType }
     *     
     */
    public SwissMunicipalityType getSwissMunicipality() {
        return swissMunicipality;
    }

    /**
     * Sets the value of the swissMunicipality property.
     * 
     * @param value
     *     allowed object is
     *     {@link SwissMunicipalityType }
     *     
     */
    public void setSwissMunicipality(SwissMunicipalityType value) {
        this.swissMunicipality = value;
    }

    /**
     * Gets the value of the swissAndFlMunicipality property.
     * 
     * @return
     *     possible object is
     *     {@link SwissAndFlMunicipalityType }
     *     
     */
    public SwissAndFlMunicipalityType getSwissAndFlMunicipality() {
        return swissAndFlMunicipality;
    }

    /**
     * Sets the value of the swissAndFlMunicipality property.
     * 
     * @param value
     *     allowed object is
     *     {@link SwissAndFlMunicipalityType }
     *     
     */
    public void setSwissAndFlMunicipality(SwissAndFlMunicipalityType value) {
        this.swissAndFlMunicipality = value;
    }

}
