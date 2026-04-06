
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0044._1.DatePartiallyKnownType;


/**
 * Extended person-related information element, where every single subelement is optional.
 * 
 * <p>Java class for personInformationExtOptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personInformationExtOptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeOfRecord" type="{http://www.ech.ch/xmlns/eCH-0084/1}typeOfRecord_Type" minOccurs="0"/>
 *         &lt;element name="shownDocument" type="{http://www.ech.ch/xmlns/eCH-0084/1}shownDocument_Type" minOccurs="0"/>
 *         &lt;element name="firstNames" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="officialName" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="originalName" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.ech.ch/xmlns/eCH-0044/1}sexType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.ech.ch/xmlns/eCH-0044/1}datePartiallyKnownType" minOccurs="0"/>
 *         &lt;element name="placeOfBirth" type="{http://www.ech.ch/xmlns/eCH-0084/1}placeOfBirthType" minOccurs="0"/>
 *         &lt;element name="mothersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="fathersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="nationality" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="nationalityStatus" type="{http://www.ech.ch/xmlns/eCH-0011/3}nationalityStatusType"/>
 *                   &lt;element name="countryId" type="{http://www.ech.ch/xmlns/eCH-0072/1}countryIdType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="dateOfDeath" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personInformationExtOptType", propOrder = {
    "typeOfRecord",
    "shownDocument",
    "firstNames",
    "officialName",
    "originalName",
    "sex",
    "dateOfBirth",
    "placeOfBirth",
    "mothersName",
    "fathersName",
    "nationality",
    "dateOfDeath"
})
public class PersonInformationExtOptType {

    @XmlSchemaType(name = "token")
    protected TypeOfRecordType typeOfRecord;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer shownDocument;
    @XmlElementRef(name = "firstNames", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstNames;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String officialName;
    @XmlElementRef(name = "originalName", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<String> originalName;
    protected String sex;
    protected DatePartiallyKnownType dateOfBirth;
    protected PlaceOfBirthType placeOfBirth;
    @XmlElementRef(name = "mothersName", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<FullNameType> mothersName;
    @XmlElementRef(name = "fathersName", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<FullNameType> fathersName;
    protected PersonInformationExtOptType.Nationality nationality;
    @XmlElementRef(name = "dateOfDeath", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dateOfDeath;

    /**
     * Gets the value of the typeOfRecord property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfRecordType }
     *     
     */
    public TypeOfRecordType getTypeOfRecord() {
        return typeOfRecord;
    }

    /**
     * Sets the value of the typeOfRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfRecordType }
     *     
     */
    public void setTypeOfRecord(TypeOfRecordType value) {
        this.typeOfRecord = value;
    }

    /**
     * Gets the value of the shownDocument property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShownDocument() {
        return shownDocument;
    }

    /**
     * Sets the value of the shownDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShownDocument(Integer value) {
        this.shownDocument = value;
    }

    /**
     * Gets the value of the firstNames property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFirstNames() {
        return firstNames;
    }

    /**
     * Sets the value of the firstNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFirstNames(JAXBElement<String> value) {
        this.firstNames = value;
    }

    /**
     * Gets the value of the officialName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficialName() {
        return officialName;
    }

    /**
     * Sets the value of the officialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficialName(String value) {
        this.officialName = value;
    }

    /**
     * Gets the value of the originalName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOriginalName() {
        return originalName;
    }

    /**
     * Sets the value of the originalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOriginalName(JAXBElement<String> value) {
        this.originalName = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link DatePartiallyKnownType }
     *     
     */
    public DatePartiallyKnownType getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatePartiallyKnownType }
     *     
     */
    public void setDateOfBirth(DatePartiallyKnownType value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the placeOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceOfBirthType }
     *     
     */
    public PlaceOfBirthType getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Sets the value of the placeOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceOfBirthType }
     *     
     */
    public void setPlaceOfBirth(PlaceOfBirthType value) {
        this.placeOfBirth = value;
    }

    /**
     * Gets the value of the mothersName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FullNameType }{@code >}
     *     
     */
    public JAXBElement<FullNameType> getMothersName() {
        return mothersName;
    }

    /**
     * Sets the value of the mothersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FullNameType }{@code >}
     *     
     */
    public void setMothersName(JAXBElement<FullNameType> value) {
        this.mothersName = value;
    }

    /**
     * Gets the value of the fathersName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FullNameType }{@code >}
     *     
     */
    public JAXBElement<FullNameType> getFathersName() {
        return fathersName;
    }

    /**
     * Sets the value of the fathersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FullNameType }{@code >}
     *     
     */
    public void setFathersName(JAXBElement<FullNameType> value) {
        this.fathersName = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInformationExtOptType.Nationality }
     *     
     */
    public PersonInformationExtOptType.Nationality getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationExtOptType.Nationality }
     *     
     */
    public void setNationality(PersonInformationExtOptType.Nationality value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the dateOfDeath property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * Sets the value of the dateOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDateOfDeath(JAXBElement<XMLGregorianCalendar> value) {
        this.dateOfDeath = value;
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
     *         &lt;element name="nationalityStatus" type="{http://www.ech.ch/xmlns/eCH-0011/3}nationalityStatusType"/>
     *         &lt;element name="countryId" type="{http://www.ech.ch/xmlns/eCH-0072/1}countryIdType" minOccurs="0"/>
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
        "nationalityStatus",
        "countryId"
    })
    public static class Nationality {

        @XmlElement(required = true)
        protected String nationalityStatus;
        @XmlSchemaType(name = "integer")
        protected Integer countryId;

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
         * Gets the value of the countryId property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCountryId() {
            return countryId;
        }

        /**
         * Sets the value of the countryId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCountryId(Integer value) {
            this.countryId = value;
        }

    }

}
