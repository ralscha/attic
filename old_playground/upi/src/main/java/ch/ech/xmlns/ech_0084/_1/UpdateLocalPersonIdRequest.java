
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
 *         &lt;element name="oldLocalPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType"/>
 *         &lt;element name="newLocalPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType"/>
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
    "oldLocalPersonId",
    "newLocalPersonId"
})
@XmlRootElement(name = "updateLocalPersonIdRequest")
public class UpdateLocalPersonIdRequest {

    @XmlElement(required = true)
    protected HeaderType header;
    @XmlSchemaType(name = "unsignedLong")
    protected Long ahvvn;
    @XmlElement(required = true)
    protected NamedPersonIdType oldLocalPersonId;
    @XmlElement(required = true)
    protected NamedPersonIdType newLocalPersonId;

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
     * Gets the value of the oldLocalPersonId property.
     * 
     * @return
     *     possible object is
     *     {@link NamedPersonIdType }
     *     
     */
    public NamedPersonIdType getOldLocalPersonId() {
        return oldLocalPersonId;
    }

    /**
     * Sets the value of the oldLocalPersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedPersonIdType }
     *     
     */
    public void setOldLocalPersonId(NamedPersonIdType value) {
        this.oldLocalPersonId = value;
    }

    /**
     * Gets the value of the newLocalPersonId property.
     * 
     * @return
     *     possible object is
     *     {@link NamedPersonIdType }
     *     
     */
    public NamedPersonIdType getNewLocalPersonId() {
        return newLocalPersonId;
    }

    /**
     * Sets the value of the newLocalPersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedPersonIdType }
     *     
     */
    public void setNewLocalPersonId(NamedPersonIdType value) {
        this.newLocalPersonId = value;
    }

}
