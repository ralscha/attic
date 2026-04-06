
package ch.ech.xmlns.ech_0085._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ch.ech.xmlns.ech_0044._1.DatePartiallyKnownType;
import ch.ech.xmlns.ech_0084._1.FullNameType;
import ch.ech.xmlns.ech_0084._1.PlaceOfBirthType;


/**
 * <p>Java class for searchPersonRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchPersonRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstNames" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type"/>
 *         &lt;element name="officialName" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type"/>
 *         &lt;element name="originalName" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.ech.ch/xmlns/eCH-0044/1}sexType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.ech.ch/xmlns/eCH-0044/1}datePartiallyKnownType"/>
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
 *         &lt;element name="placeOfBirth" type="{http://www.ech.ch/xmlns/eCH-0084/1}placeOfBirthType"/>
 *         &lt;element name="mothersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="fathersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchPersonRequestType", propOrder = {
    "firstNames",
    "officialName",
    "originalName",
    "sex",
    "dateOfBirth",
    "nationality",
    "placeOfBirth",
    "mothersName",
    "fathersName"
})
public class SearchPersonRequestType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String firstNames;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String officialName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String originalName;
    protected String sex;
    @XmlElement(required = true)
    protected DatePartiallyKnownType dateOfBirth;
    protected SearchPersonRequestType.Nationality nationality;
    @XmlElement(required = true)
    protected PlaceOfBirthType placeOfBirth;
    protected FullNameType mothersName;
    protected FullNameType fathersName;

    /**
     * Gets the value of the firstNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstNames() {
        return firstNames;
    }

    /**
     * Sets the value of the firstNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstNames(String value) {
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
     *     {@link String }
     *     
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets the value of the originalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalName(String value) {
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
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPersonRequestType.Nationality }
     *     
     */
    public SearchPersonRequestType.Nationality getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPersonRequestType.Nationality }
     *     
     */
    public void setNationality(SearchPersonRequestType.Nationality value) {
        this.nationality = value;
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
     *     {@link FullNameType }
     *     
     */
    public FullNameType getMothersName() {
        return mothersName;
    }

    /**
     * Sets the value of the mothersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullNameType }
     *     
     */
    public void setMothersName(FullNameType value) {
        this.mothersName = value;
    }

    /**
     * Gets the value of the fathersName property.
     * 
     * @return
     *     possible object is
     *     {@link FullNameType }
     *     
     */
    public FullNameType getFathersName() {
        return fathersName;
    }

    /**
     * Sets the value of the fathersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullNameType }
     *     
     */
    public void setFathersName(FullNameType value) {
        this.fathersName = value;
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
