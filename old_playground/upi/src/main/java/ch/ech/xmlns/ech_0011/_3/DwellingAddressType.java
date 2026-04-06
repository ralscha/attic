
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0010._3.SwissAddressInformationType;


/**
 * <p>Java class for dwellingAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dwellingAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;sequence>
 *             &lt;element name="EGID" type="{http://www.ech.ch/xmlns/eCH-0011/3}EGIDType"/>
 *             &lt;choice minOccurs="0">
 *               &lt;element name="EWID" type="{http://www.ech.ch/xmlns/eCH-0011/3}EWIDType"/>
 *               &lt;element name="householdID" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *           &lt;element name="withoutEGID">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="householdID" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="address" type="{http://www.ech.ch/xmlns/eCH-0010/3}swissAddressInformationType"/>
 *         &lt;element name="typeOfHousehold" type="{http://www.ech.ch/xmlns/eCH-0011/3}typeOfHouseholdType"/>
 *         &lt;element name="movingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dwellingAddressType", propOrder = {
    "egid",
    "ewid",
    "householdID",
    "withoutEGID",
    "address",
    "typeOfHousehold",
    "movingDate"
})
public class DwellingAddressType {

    @XmlElement(name = "EGID")
    @XmlSchemaType(name = "unsignedInt")
    protected Long egid;
    @XmlElement(name = "EWID")
    @XmlSchemaType(name = "unsignedInt")
    protected Long ewid;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String householdID;
    protected DwellingAddressType.WithoutEGID withoutEGID;
    @XmlElement(required = true)
    protected SwissAddressInformationType address;
    @XmlElement(required = true)
    protected String typeOfHousehold;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar movingDate;

    /**
     * Gets the value of the egid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEGID() {
        return egid;
    }

    /**
     * Sets the value of the egid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEGID(Long value) {
        this.egid = value;
    }

    /**
     * Gets the value of the ewid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEWID() {
        return ewid;
    }

    /**
     * Sets the value of the ewid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEWID(Long value) {
        this.ewid = value;
    }

    /**
     * Gets the value of the householdID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseholdID() {
        return householdID;
    }

    /**
     * Sets the value of the householdID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseholdID(String value) {
        this.householdID = value;
    }

    /**
     * Gets the value of the withoutEGID property.
     * 
     * @return
     *     possible object is
     *     {@link DwellingAddressType.WithoutEGID }
     *     
     */
    public DwellingAddressType.WithoutEGID getWithoutEGID() {
        return withoutEGID;
    }

    /**
     * Sets the value of the withoutEGID property.
     * 
     * @param value
     *     allowed object is
     *     {@link DwellingAddressType.WithoutEGID }
     *     
     */
    public void setWithoutEGID(DwellingAddressType.WithoutEGID value) {
        this.withoutEGID = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link SwissAddressInformationType }
     *     
     */
    public SwissAddressInformationType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link SwissAddressInformationType }
     *     
     */
    public void setAddress(SwissAddressInformationType value) {
        this.address = value;
    }

    /**
     * Gets the value of the typeOfHousehold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfHousehold() {
        return typeOfHousehold;
    }

    /**
     * Sets the value of the typeOfHousehold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfHousehold(String value) {
        this.typeOfHousehold = value;
    }

    /**
     * Gets the value of the movingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMovingDate() {
        return movingDate;
    }

    /**
     * Sets the value of the movingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMovingDate(XMLGregorianCalendar value) {
        this.movingDate = value;
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
     *         &lt;element name="householdID" type="{http://www.w3.org/2001/XMLSchema}token"/>
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
        "householdID"
    })
    public static class WithoutEGID {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String householdID;

        /**
         * Gets the value of the householdID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHouseholdID() {
            return householdID;
        }

        /**
         * Sets the value of the householdID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHouseholdID(String value) {
            this.householdID = value;
        }

    }

}
