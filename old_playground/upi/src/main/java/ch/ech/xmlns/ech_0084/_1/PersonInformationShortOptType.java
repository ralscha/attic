
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
import ch.ech.xmlns.ech_0044._1.DatePartiallyKnownType;


/**
 * Same as personInformationType, but every single subelement is optional.
 * 
 * <p>Java class for personInformationShortOptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personInformationShortOptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstNames" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="officialName" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.ech.ch/xmlns/eCH-0044/1}sexType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.ech.ch/xmlns/eCH-0044/1}datePartiallyKnownType" minOccurs="0"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personInformationShortOptType", propOrder = {
    "firstNames",
    "officialName",
    "sex",
    "dateOfBirth",
    "nationality"
})
public class PersonInformationShortOptType {

    @XmlElementRef(name = "firstNames", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstNames;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String officialName;
    protected String sex;
    protected DatePartiallyKnownType dateOfBirth;
    protected PersonInformationShortOptType.Nationality nationality;

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
     *     {@link PersonInformationShortOptType.Nationality }
     *     
     */
    public PersonInformationShortOptType.Nationality getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationShortOptType.Nationality }
     *     
     */
    public void setNationality(PersonInformationShortOptType.Nationality value) {
        this.nationality = value;
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
