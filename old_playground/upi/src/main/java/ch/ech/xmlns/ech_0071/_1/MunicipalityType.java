
package ch.ech.xmlns.ech_0071._1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for municipalityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="municipalityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="historyMunicipalityId" type="{http://www.ech.ch/xmlns/eCH-0071/1}histIdType"/>
 *         &lt;element name="districtHistId" type="{http://www.ech.ch/xmlns/eCH-0071/1}histIdType"/>
 *         &lt;element name="cantonAbbreviation" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonAbbreviationType"/>
 *         &lt;element name="municipalityId" type="{http://www.ech.ch/xmlns/eCH-0071/1}municipalityIdType"/>
 *         &lt;element name="municipalityLongName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string40Type"/>
 *         &lt;element name="municipalityShortName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string24Type"/>
 *         &lt;element name="municipalityEntryMode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="11"/>
 *               &lt;maxInclusive value="13"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="municipalityStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="municipalityAdmissionNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="1000"/>
 *               &lt;maxInclusive value="9999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="municipalityAdmissionMode" type="{http://www.ech.ch/xmlns/eCH-0071/1}admissionModeType"/>
 *         &lt;element name="municipalityAdmissionDate" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType"/>
 *         &lt;element name="municipalityAbolitionNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="1001"/>
 *               &lt;maxInclusive value="9999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="municipalityAbolitionMode" type="{http://www.ech.ch/xmlns/eCH-0071/1}abolitionMode" minOccurs="0"/>
 *         &lt;element name="municipalityAbolitionDate" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType" minOccurs="0"/>
 *         &lt;element name="municipalityDateOfChange" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "municipalityType", propOrder = {
    "historyMunicipalityId",
    "districtHistId",
    "cantonAbbreviation",
    "municipalityId",
    "municipalityLongName",
    "municipalityShortName",
    "municipalityEntryMode",
    "municipalityStatus",
    "municipalityAdmissionNumber",
    "municipalityAdmissionMode",
    "municipalityAdmissionDate",
    "municipalityAbolitionNumber",
    "municipalityAbolitionMode",
    "municipalityAbolitionDate",
    "municipalityDateOfChange"
})
public class MunicipalityType {

    @XmlSchemaType(name = "integer")
    protected int historyMunicipalityId;
    @XmlSchemaType(name = "integer")
    protected int districtHistId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    protected CantonAbbreviationType cantonAbbreviation;
    protected int municipalityId;
    @XmlElement(required = true)
    protected String municipalityLongName;
    @XmlElement(required = true)
    protected String municipalityShortName;
    protected int municipalityEntryMode;
    protected int municipalityStatus;
    protected int municipalityAdmissionNumber;
    @XmlElement(required = true)
    protected BigInteger municipalityAdmissionMode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar municipalityAdmissionDate;
    protected Integer municipalityAbolitionNumber;
    protected BigInteger municipalityAbolitionMode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar municipalityAbolitionDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar municipalityDateOfChange;

    /**
     * Gets the value of the historyMunicipalityId property.
     * 
     */
    public int getHistoryMunicipalityId() {
        return historyMunicipalityId;
    }

    /**
     * Sets the value of the historyMunicipalityId property.
     * 
     */
    public void setHistoryMunicipalityId(int value) {
        this.historyMunicipalityId = value;
    }

    /**
     * Gets the value of the districtHistId property.
     * 
     */
    public int getDistrictHistId() {
        return districtHistId;
    }

    /**
     * Sets the value of the districtHistId property.
     * 
     */
    public void setDistrictHistId(int value) {
        this.districtHistId = value;
    }

    /**
     * Gets the value of the cantonAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link CantonAbbreviationType }
     *     
     */
    public CantonAbbreviationType getCantonAbbreviation() {
        return cantonAbbreviation;
    }

    /**
     * Sets the value of the cantonAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CantonAbbreviationType }
     *     
     */
    public void setCantonAbbreviation(CantonAbbreviationType value) {
        this.cantonAbbreviation = value;
    }

    /**
     * Gets the value of the municipalityId property.
     * 
     */
    public int getMunicipalityId() {
        return municipalityId;
    }

    /**
     * Sets the value of the municipalityId property.
     * 
     */
    public void setMunicipalityId(int value) {
        this.municipalityId = value;
    }

    /**
     * Gets the value of the municipalityLongName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipalityLongName() {
        return municipalityLongName;
    }

    /**
     * Sets the value of the municipalityLongName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipalityLongName(String value) {
        this.municipalityLongName = value;
    }

    /**
     * Gets the value of the municipalityShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipalityShortName() {
        return municipalityShortName;
    }

    /**
     * Sets the value of the municipalityShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipalityShortName(String value) {
        this.municipalityShortName = value;
    }

    /**
     * Gets the value of the municipalityEntryMode property.
     * 
     */
    public int getMunicipalityEntryMode() {
        return municipalityEntryMode;
    }

    /**
     * Sets the value of the municipalityEntryMode property.
     * 
     */
    public void setMunicipalityEntryMode(int value) {
        this.municipalityEntryMode = value;
    }

    /**
     * Gets the value of the municipalityStatus property.
     * 
     */
    public int getMunicipalityStatus() {
        return municipalityStatus;
    }

    /**
     * Sets the value of the municipalityStatus property.
     * 
     */
    public void setMunicipalityStatus(int value) {
        this.municipalityStatus = value;
    }

    /**
     * Gets the value of the municipalityAdmissionNumber property.
     * 
     */
    public int getMunicipalityAdmissionNumber() {
        return municipalityAdmissionNumber;
    }

    /**
     * Sets the value of the municipalityAdmissionNumber property.
     * 
     */
    public void setMunicipalityAdmissionNumber(int value) {
        this.municipalityAdmissionNumber = value;
    }

    /**
     * Gets the value of the municipalityAdmissionMode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMunicipalityAdmissionMode() {
        return municipalityAdmissionMode;
    }

    /**
     * Sets the value of the municipalityAdmissionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMunicipalityAdmissionMode(BigInteger value) {
        this.municipalityAdmissionMode = value;
    }

    /**
     * Gets the value of the municipalityAdmissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMunicipalityAdmissionDate() {
        return municipalityAdmissionDate;
    }

    /**
     * Sets the value of the municipalityAdmissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMunicipalityAdmissionDate(XMLGregorianCalendar value) {
        this.municipalityAdmissionDate = value;
    }

    /**
     * Gets the value of the municipalityAbolitionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMunicipalityAbolitionNumber() {
        return municipalityAbolitionNumber;
    }

    /**
     * Sets the value of the municipalityAbolitionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMunicipalityAbolitionNumber(Integer value) {
        this.municipalityAbolitionNumber = value;
    }

    /**
     * Gets the value of the municipalityAbolitionMode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMunicipalityAbolitionMode() {
        return municipalityAbolitionMode;
    }

    /**
     * Sets the value of the municipalityAbolitionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMunicipalityAbolitionMode(BigInteger value) {
        this.municipalityAbolitionMode = value;
    }

    /**
     * Gets the value of the municipalityAbolitionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMunicipalityAbolitionDate() {
        return municipalityAbolitionDate;
    }

    /**
     * Sets the value of the municipalityAbolitionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMunicipalityAbolitionDate(XMLGregorianCalendar value) {
        this.municipalityAbolitionDate = value;
    }

    /**
     * Gets the value of the municipalityDateOfChange property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMunicipalityDateOfChange() {
        return municipalityDateOfChange;
    }

    /**
     * Sets the value of the municipalityDateOfChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMunicipalityDateOfChange(XMLGregorianCalendar value) {
        this.municipalityDateOfChange = value;
    }

}
