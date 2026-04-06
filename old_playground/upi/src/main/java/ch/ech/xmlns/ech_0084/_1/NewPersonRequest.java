
package ch.ech.xmlns.ech_0084._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0044._1.NamedPersonIdType;


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
 *         &lt;element name="header" type="{http://www.ech.ch/xmlns/eCH-0084/1}headerType"/>
 *         &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
 *         &lt;element name="reason" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="noSearchDone" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;pattern value="true"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="currentValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationWeakType"/>
 *         &lt;element name="historicalValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationShortOptPlusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="placeOfBirth" type="{http://www.ech.ch/xmlns/eCH-0084/1}placeOfBirthType"/>
 *         &lt;element name="mothersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="fathersName" type="{http://www.ech.ch/xmlns/eCH-0084/1}fullName_Type" minOccurs="0"/>
 *         &lt;element name="isTwin" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "", propOrder = {
    "header",
    "localPersonId",
    "reason",
    "noSearchDone",
    "currentValues",
    "historicalValues",
    "placeOfBirth",
    "mothersName",
    "fathersName",
    "isTwin",
    "dateOfDeath"
})
@XmlRootElement(name = "newPersonRequest")
public class NewPersonRequest {

    @XmlElement(required = true)
    protected HeaderType header;
    protected NamedPersonIdType localPersonId;
    protected Short reason;
    protected Boolean noSearchDone;
    @XmlElement(required = true)
    protected PersonInformationWeakType currentValues;
    protected List<PersonInformationShortOptPlusType> historicalValues;
    @XmlElement(required = true)
    protected PlaceOfBirthType placeOfBirth;
    @XmlElementRef(name = "mothersName", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<FullNameType> mothersName;
    @XmlElementRef(name = "fathersName", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<FullNameType> fathersName;
    protected Boolean isTwin;
    @XmlElementRef(name = "dateOfDeath", namespace = "http://www.ech.ch/xmlns/eCH-0084/1", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dateOfDeath;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the localPersonId property.
     * 
     * @return
     *     possible object is
     *     {@link NamedPersonIdType }
     *     
     */
    public NamedPersonIdType getLocalPersonId() {
        return localPersonId;
    }

    /**
     * Sets the value of the localPersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedPersonIdType }
     *     
     */
    public void setLocalPersonId(NamedPersonIdType value) {
        this.localPersonId = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setReason(Short value) {
        this.reason = value;
    }

    /**
     * Gets the value of the noSearchDone property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoSearchDone() {
        return noSearchDone;
    }

    /**
     * Sets the value of the noSearchDone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoSearchDone(Boolean value) {
        this.noSearchDone = value;
    }

    /**
     * Gets the value of the currentValues property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInformationWeakType }
     *     
     */
    public PersonInformationWeakType getCurrentValues() {
        return currentValues;
    }

    /**
     * Sets the value of the currentValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationWeakType }
     *     
     */
    public void setCurrentValues(PersonInformationWeakType value) {
        this.currentValues = value;
    }

    /**
     * Gets the value of the historicalValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historicalValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistoricalValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PersonInformationShortOptPlusType }
     * 
     * 
     */
    public List<PersonInformationShortOptPlusType> getHistoricalValues() {
        if (historicalValues == null) {
            historicalValues = new ArrayList<PersonInformationShortOptPlusType>();
        }
        return this.historicalValues;
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
     * Gets the value of the isTwin property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTwin() {
        return isTwin;
    }

    /**
     * Sets the value of the isTwin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTwin(Boolean value) {
        this.isTwin = value;
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

}
