
package ch.ech.xmlns.ech_0071._1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for districtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="districtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="districtHistId" type="{http://www.ech.ch/xmlns/eCH-0071/1}histIdType"/>
 *         &lt;element name="cantonId" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonIdType"/>
 *         &lt;element name="districtId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="100"/>
 *               &lt;maxInclusive value="9999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="districtLongName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string40Type"/>
 *         &lt;element name="districtShortName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string24Type"/>
 *         &lt;element name="districtEntryMode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="15"/>
 *               &lt;maxInclusive value="17"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="districtAdmissionNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="100"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="districtAdmissionMode" type="{http://www.ech.ch/xmlns/eCH-0071/1}admissionModeType"/>
 *         &lt;element name="districtAdmissionDate" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType"/>
 *         &lt;element name="districtAbolitionNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="101"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="districtAbolitionMode" type="{http://www.ech.ch/xmlns/eCH-0071/1}abolitionMode" minOccurs="0"/>
 *         &lt;element name="districtAbolitionDate" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType" minOccurs="0"/>
 *         &lt;element name="districtDateOfChange" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "districtType", propOrder = {
    "districtHistId",
    "cantonId",
    "districtId",
    "districtLongName",
    "districtShortName",
    "districtEntryMode",
    "districtAdmissionNumber",
    "districtAdmissionMode",
    "districtAdmissionDate",
    "districtAbolitionNumber",
    "districtAbolitionMode",
    "districtAbolitionDate",
    "districtDateOfChange"
})
public class DistrictType {

    @XmlSchemaType(name = "integer")
    protected int districtHistId;
    @XmlSchemaType(name = "integer")
    protected int cantonId;
    protected int districtId;
    @XmlElement(required = true)
    protected String districtLongName;
    @XmlElement(required = true)
    protected String districtShortName;
    protected int districtEntryMode;
    protected int districtAdmissionNumber;
    @XmlElement(required = true)
    protected BigInteger districtAdmissionMode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar districtAdmissionDate;
    protected Integer districtAbolitionNumber;
    protected BigInteger districtAbolitionMode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar districtAbolitionDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar districtDateOfChange;

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
     * Gets the value of the cantonId property.
     * 
     */
    public int getCantonId() {
        return cantonId;
    }

    /**
     * Sets the value of the cantonId property.
     * 
     */
    public void setCantonId(int value) {
        this.cantonId = value;
    }

    /**
     * Gets the value of the districtId property.
     * 
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * Sets the value of the districtId property.
     * 
     */
    public void setDistrictId(int value) {
        this.districtId = value;
    }

    /**
     * Gets the value of the districtLongName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictLongName() {
        return districtLongName;
    }

    /**
     * Sets the value of the districtLongName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictLongName(String value) {
        this.districtLongName = value;
    }

    /**
     * Gets the value of the districtShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictShortName() {
        return districtShortName;
    }

    /**
     * Sets the value of the districtShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictShortName(String value) {
        this.districtShortName = value;
    }

    /**
     * Gets the value of the districtEntryMode property.
     * 
     */
    public int getDistrictEntryMode() {
        return districtEntryMode;
    }

    /**
     * Sets the value of the districtEntryMode property.
     * 
     */
    public void setDistrictEntryMode(int value) {
        this.districtEntryMode = value;
    }

    /**
     * Gets the value of the districtAdmissionNumber property.
     * 
     */
    public int getDistrictAdmissionNumber() {
        return districtAdmissionNumber;
    }

    /**
     * Sets the value of the districtAdmissionNumber property.
     * 
     */
    public void setDistrictAdmissionNumber(int value) {
        this.districtAdmissionNumber = value;
    }

    /**
     * Gets the value of the districtAdmissionMode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDistrictAdmissionMode() {
        return districtAdmissionMode;
    }

    /**
     * Sets the value of the districtAdmissionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDistrictAdmissionMode(BigInteger value) {
        this.districtAdmissionMode = value;
    }

    /**
     * Gets the value of the districtAdmissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDistrictAdmissionDate() {
        return districtAdmissionDate;
    }

    /**
     * Sets the value of the districtAdmissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDistrictAdmissionDate(XMLGregorianCalendar value) {
        this.districtAdmissionDate = value;
    }

    /**
     * Gets the value of the districtAbolitionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDistrictAbolitionNumber() {
        return districtAbolitionNumber;
    }

    /**
     * Sets the value of the districtAbolitionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDistrictAbolitionNumber(Integer value) {
        this.districtAbolitionNumber = value;
    }

    /**
     * Gets the value of the districtAbolitionMode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDistrictAbolitionMode() {
        return districtAbolitionMode;
    }

    /**
     * Sets the value of the districtAbolitionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDistrictAbolitionMode(BigInteger value) {
        this.districtAbolitionMode = value;
    }

    /**
     * Gets the value of the districtAbolitionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDistrictAbolitionDate() {
        return districtAbolitionDate;
    }

    /**
     * Sets the value of the districtAbolitionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDistrictAbolitionDate(XMLGregorianCalendar value) {
        this.districtAbolitionDate = value;
    }

    /**
     * Gets the value of the districtDateOfChange property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDistrictDateOfChange() {
        return districtDateOfChange;
    }

    /**
     * Sets the value of the districtDateOfChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDistrictDateOfChange(XMLGregorianCalendar value) {
        this.districtDateOfChange = value;
    }

}
