
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ch.ech.xmlns.ech_0007._3.SwissMunicipalityType;
import ch.ech.xmlns.ech_0008._2.CountryType;


/**
 * <p>Java class for birthplaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="birthplaceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="unknown" type="{http://www.ech.ch/xmlns/eCH-0011/3}unknownType"/>
 *         &lt;element name="swissTown">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="country">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.ech.ch/xmlns/eCH-0010/3}countryType">
 *                         &lt;enumeration value="CH"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="municipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="foreignCountry">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="country" type="{http://www.ech.ch/xmlns/eCH-0008/2}countryType"/>
 *                   &lt;element name="foreignBirthTown" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;whiteSpace value="collapse"/>
 *                         &lt;maxLength value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "birthplaceType", propOrder = {
    "unknown",
    "swissTown",
    "foreignCountry"
})
public class BirthplaceType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unknown;
    protected BirthplaceType.SwissTown swissTown;
    protected BirthplaceType.ForeignCountry foreignCountry;

    /**
     * Gets the value of the unknown property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnknown() {
        return unknown;
    }

    /**
     * Sets the value of the unknown property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnknown(String value) {
        this.unknown = value;
    }

    /**
     * Gets the value of the swissTown property.
     * 
     * @return
     *     possible object is
     *     {@link BirthplaceType.SwissTown }
     *     
     */
    public BirthplaceType.SwissTown getSwissTown() {
        return swissTown;
    }

    /**
     * Sets the value of the swissTown property.
     * 
     * @param value
     *     allowed object is
     *     {@link BirthplaceType.SwissTown }
     *     
     */
    public void setSwissTown(BirthplaceType.SwissTown value) {
        this.swissTown = value;
    }

    /**
     * Gets the value of the foreignCountry property.
     * 
     * @return
     *     possible object is
     *     {@link BirthplaceType.ForeignCountry }
     *     
     */
    public BirthplaceType.ForeignCountry getForeignCountry() {
        return foreignCountry;
    }

    /**
     * Sets the value of the foreignCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link BirthplaceType.ForeignCountry }
     *     
     */
    public void setForeignCountry(BirthplaceType.ForeignCountry value) {
        this.foreignCountry = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="country" type="{http://www.ech.ch/xmlns/eCH-0008/2}countryType"/>
     *         &lt;element name="foreignBirthTown" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;whiteSpace value="collapse"/>
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
        "country",
        "foreignBirthTown"
    })
    public static class ForeignCountry {

        @XmlElement(required = true)
        protected CountryType country;
        protected String foreignBirthTown;

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

        /**
         * Gets the value of the foreignBirthTown property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getForeignBirthTown() {
            return foreignBirthTown;
        }

        /**
         * Sets the value of the foreignBirthTown property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setForeignBirthTown(String value) {
            this.foreignBirthTown = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="country">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.ech.ch/xmlns/eCH-0010/3}countryType">
     *               &lt;enumeration value="CH"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="municipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
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
        "country",
        "municipality"
    })
    public static class SwissTown {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String country;
        @XmlElement(required = true)
        protected SwissMunicipalityType municipality;

        /**
         * Gets the value of the country property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountry(String value) {
            this.country = value;
        }

        /**
         * Gets the value of the municipality property.
         * 
         * @return
         *     possible object is
         *     {@link SwissMunicipalityType }
         *     
         */
        public SwissMunicipalityType getMunicipality() {
            return municipality;
        }

        /**
         * Sets the value of the municipality property.
         * 
         * @param value
         *     allowed object is
         *     {@link SwissMunicipalityType }
         *     
         */
        public void setMunicipality(SwissMunicipalityType value) {
            this.municipality = value;
        }

    }

}
