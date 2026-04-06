
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0011._3.BirthplaceType;


/**
 * Note: this type is used in responses only.
 *       
 * 
 * <p>Java class for valuesStoredUnderAhvvn_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="valuesStoredUnderAhvvn_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastUpdateTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="person" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationType"/>
 *         &lt;element name="nameOnPassport" type="{http://www.ech.ch/xmlns/eCH-0084/1}baseNameUPI_Type" minOccurs="0"/>
 *         &lt;element name="placeOfBirth" type="{http://www.ech.ch/xmlns/eCH-0011/3}birthplaceType" minOccurs="0"/>
 *         &lt;element name="mothersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="fathersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
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
@XmlType(name = "valuesStoredUnderAhvvn_Type", propOrder = {
    "lastUpdateTimestamp",
    "person",
    "nameOnPassport",
    "placeOfBirth",
    "mothersName",
    "fathersName",
    "dateOfDeath"
})
public class ValuesStoredUnderAhvvnType {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdateTimestamp;
    @XmlElement(required = true)
    protected PersonInformationType person;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nameOnPassport;
    protected BirthplaceType placeOfBirth;
    protected FullNameType mothersName;
    protected FullNameType fathersName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfDeath;

    /**
     * Gets the value of the lastUpdateTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    /**
     * Sets the value of the lastUpdateTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdateTimestamp(XMLGregorianCalendar value) {
        this.lastUpdateTimestamp = value;
    }

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInformationType }
     *     
     */
    public PersonInformationType getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationType }
     *     
     */
    public void setPerson(PersonInformationType value) {
        this.person = value;
    }

    /**
     * Gets the value of the nameOnPassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOnPassport() {
        return nameOnPassport;
    }

    /**
     * Sets the value of the nameOnPassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOnPassport(String value) {
        this.nameOnPassport = value;
    }

    /**
     * Gets the value of the placeOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link BirthplaceType }
     *     
     */
    public BirthplaceType getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Sets the value of the placeOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BirthplaceType }
     *     
     */
    public void setPlaceOfBirth(BirthplaceType value) {
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
     * Gets the value of the dateOfDeath property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * Sets the value of the dateOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfDeath(XMLGregorianCalendar value) {
        this.dateOfDeath = value;
    }

}
