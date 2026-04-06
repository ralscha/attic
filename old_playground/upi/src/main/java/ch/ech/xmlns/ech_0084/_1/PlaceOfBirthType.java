
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the place of birth.
 * 
 * <p>Java class for placeOfBirthType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="placeOfBirthType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="unknown" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="swissTown">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="historyMunicipalityId" type="{http://www.ech.ch/xmlns/eCH-0071/1}histIdType"/>
 *                   &lt;element name="municipalityName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string40Type"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="foreignCountry">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="country" type="{http://www.ech.ch/xmlns/eCH-0072/1}countryIdType"/>
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
@XmlType(name = "placeOfBirthType", propOrder = {
    "unknown",
    "swissTown",
    "foreignCountry"
})
public class PlaceOfBirthType {

    protected Object unknown;
    protected PlaceOfBirthType.SwissTown swissTown;
    protected PlaceOfBirthType.ForeignCountry foreignCountry;

    /**
     * Gets the value of the unknown property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnknown() {
        return unknown;
    }

    /**
     * Sets the value of the unknown property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnknown(Object value) {
        this.unknown = value;
    }

    /**
     * Gets the value of the swissTown property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceOfBirthType.SwissTown }
     *     
     */
    public PlaceOfBirthType.SwissTown getSwissTown() {
        return swissTown;
    }

    /**
     * Sets the value of the swissTown property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceOfBirthType.SwissTown }
     *     
     */
    public void setSwissTown(PlaceOfBirthType.SwissTown value) {
        this.swissTown = value;
    }

    /**
     * Gets the value of the foreignCountry property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceOfBirthType.ForeignCountry }
     *     
     */
    public PlaceOfBirthType.ForeignCountry getForeignCountry() {
        return foreignCountry;
    }

    /**
     * Sets the value of the foreignCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceOfBirthType.ForeignCountry }
     *     
     */
    public void setForeignCountry(PlaceOfBirthType.ForeignCountry value) {
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
     *         &lt;element name="country" type="{http://www.ech.ch/xmlns/eCH-0072/1}countryIdType"/>
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

        @XmlSchemaType(name = "integer")
        protected int country;
        @XmlElementRef(name = "foreignBirthTown", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
        protected JAXBElement<String> foreignBirthTown;

        /**
         * Gets the value of the country property.
         * 
         */
        public int getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         * 
         */
        public void setCountry(int value) {
            this.country = value;
        }

        /**
         * Gets the value of the foreignBirthTown property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getForeignBirthTown() {
            return foreignBirthTown;
        }

        /**
         * Sets the value of the foreignBirthTown property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setForeignBirthTown(JAXBElement<String> value) {
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
     *       &lt;choice>
     *         &lt;element name="historyMunicipalityId" type="{http://www.ech.ch/xmlns/eCH-0071/1}histIdType"/>
     *         &lt;element name="municipalityName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string40Type"/>
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
        "historyMunicipalityId",
        "municipalityName"
    })
    public static class SwissTown {

        @XmlSchemaType(name = "integer")
        protected Integer historyMunicipalityId;
        protected String municipalityName;

        /**
         * Gets the value of the historyMunicipalityId property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getHistoryMunicipalityId() {
            return historyMunicipalityId;
        }

        /**
         * Sets the value of the historyMunicipalityId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setHistoryMunicipalityId(Integer value) {
            this.historyMunicipalityId = value;
        }

        /**
         * Gets the value of the municipalityName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMunicipalityName() {
            return municipalityName;
        }

        /**
         * Sets the value of the municipalityName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMunicipalityName(String value) {
            this.municipalityName = value;
        }

    }

}
