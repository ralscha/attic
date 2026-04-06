
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
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
 *         &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" minOccurs="0"/>
 *         &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
 *         &lt;element name="reason" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="person" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationExtType"/>
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
    "ahvvn",
    "localPersonId",
    "reason",
    "person"
})
@XmlRootElement(name = "addEntryToHistoryRequest")
public class AddEntryToHistoryRequest {

    @XmlElement(required = true)
    protected HeaderType header;
    @XmlSchemaType(name = "unsignedLong")
    protected Long ahvvn;
    protected NamedPersonIdType localPersonId;
    protected Short reason;
    @XmlElement(required = true)
    protected PersonInformationExtType person;

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
     * Gets the value of the ahvvn property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAhvvn() {
        return ahvvn;
    }

    /**
     * Sets the value of the ahvvn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAhvvn(Long value) {
        this.ahvvn = value;
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
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInformationExtType }
     *     
     */
    public PersonInformationExtType getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationExtType }
     *     
     */
    public void setPerson(PersonInformationExtType value) {
        this.person = value;
    }

}
