
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0007._3.SwissMunicipalityType;


/**
 * <p>Java class for secondaryResidenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="secondaryResidenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mainResidence" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
 *         &lt;element name="secondaryResidence">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reportingMunicipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
 *                   &lt;element name="arrivalDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="comesFrom" type="{http://www.ech.ch/xmlns/eCH-0011/3}destinationType"/>
 *                   &lt;element name="dwellingAddress" type="{http://www.ech.ch/xmlns/eCH-0011/3}dwellingAddressType"/>
 *                   &lt;element name="departureDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="goesTo" type="{http://www.ech.ch/xmlns/eCH-0011/3}destinationType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "secondaryResidenceType", propOrder = {
    "mainResidence",
    "secondaryResidence"
})
public class SecondaryResidenceType {

    @XmlElement(required = true)
    protected SwissMunicipalityType mainResidence;
    @XmlElement(required = true)
    protected SecondaryResidenceType.SecondaryResidence secondaryResidence;

    /**
     * Gets the value of the mainResidence property.
     * 
     * @return
     *     possible object is
     *     {@link SwissMunicipalityType }
     *     
     */
    public SwissMunicipalityType getMainResidence() {
        return mainResidence;
    }

    /**
     * Sets the value of the mainResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link SwissMunicipalityType }
     *     
     */
    public void setMainResidence(SwissMunicipalityType value) {
        this.mainResidence = value;
    }

    /**
     * Gets the value of the secondaryResidence property.
     * 
     * @return
     *     possible object is
     *     {@link SecondaryResidenceType.SecondaryResidence }
     *     
     */
    public SecondaryResidenceType.SecondaryResidence getSecondaryResidence() {
        return secondaryResidence;
    }

    /**
     * Sets the value of the secondaryResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondaryResidenceType.SecondaryResidence }
     *     
     */
    public void setSecondaryResidence(SecondaryResidenceType.SecondaryResidence value) {
        this.secondaryResidence = value;
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
     *         &lt;element name="reportingMunicipality" type="{http://www.ech.ch/xmlns/eCH-0007/3}swissMunicipalityType"/>
     *         &lt;element name="arrivalDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="comesFrom" type="{http://www.ech.ch/xmlns/eCH-0011/3}destinationType"/>
     *         &lt;element name="dwellingAddress" type="{http://www.ech.ch/xmlns/eCH-0011/3}dwellingAddressType"/>
     *         &lt;element name="departureDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="goesTo" type="{http://www.ech.ch/xmlns/eCH-0011/3}destinationType" minOccurs="0"/>
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
        "reportingMunicipality",
        "arrivalDate",
        "comesFrom",
        "dwellingAddress",
        "departureDate",
        "goesTo"
    })
    public static class SecondaryResidence {

        @XmlElement(required = true)
        protected SwissMunicipalityType reportingMunicipality;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar arrivalDate;
        @XmlElement(required = true)
        protected DestinationType comesFrom;
        @XmlElement(required = true)
        protected DwellingAddressType dwellingAddress;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar departureDate;
        protected DestinationType goesTo;

        /**
         * Gets the value of the reportingMunicipality property.
         * 
         * @return
         *     possible object is
         *     {@link SwissMunicipalityType }
         *     
         */
        public SwissMunicipalityType getReportingMunicipality() {
            return reportingMunicipality;
        }

        /**
         * Sets the value of the reportingMunicipality property.
         * 
         * @param value
         *     allowed object is
         *     {@link SwissMunicipalityType }
         *     
         */
        public void setReportingMunicipality(SwissMunicipalityType value) {
            this.reportingMunicipality = value;
        }

        /**
         * Gets the value of the arrivalDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getArrivalDate() {
            return arrivalDate;
        }

        /**
         * Sets the value of the arrivalDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setArrivalDate(XMLGregorianCalendar value) {
            this.arrivalDate = value;
        }

        /**
         * Gets the value of the comesFrom property.
         * 
         * @return
         *     possible object is
         *     {@link DestinationType }
         *     
         */
        public DestinationType getComesFrom() {
            return comesFrom;
        }

        /**
         * Sets the value of the comesFrom property.
         * 
         * @param value
         *     allowed object is
         *     {@link DestinationType }
         *     
         */
        public void setComesFrom(DestinationType value) {
            this.comesFrom = value;
        }

        /**
         * Gets the value of the dwellingAddress property.
         * 
         * @return
         *     possible object is
         *     {@link DwellingAddressType }
         *     
         */
        public DwellingAddressType getDwellingAddress() {
            return dwellingAddress;
        }

        /**
         * Sets the value of the dwellingAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link DwellingAddressType }
         *     
         */
        public void setDwellingAddress(DwellingAddressType value) {
            this.dwellingAddress = value;
        }

        /**
         * Gets the value of the departureDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDepartureDate() {
            return departureDate;
        }

        /**
         * Sets the value of the departureDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDepartureDate(XMLGregorianCalendar value) {
            this.departureDate = value;
        }

        /**
         * Gets the value of the goesTo property.
         * 
         * @return
         *     possible object is
         *     {@link DestinationType }
         *     
         */
        public DestinationType getGoesTo() {
            return goesTo;
        }

        /**
         * Sets the value of the goesTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link DestinationType }
         *     
         */
        public void setGoesTo(DestinationType value) {
            this.goesTo = value;
        }

    }

}
