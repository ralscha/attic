
package ch.ech.xmlns.ech_0084._1;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="person" maxOccurs="2" minOccurs="2">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" minOccurs="0"/>
 *                   &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
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
@XmlType(name = "", propOrder = {
    "header",
    "person"
})
@XmlRootElement(name = "mergePersonOrUpdateLocalPersonIdRequest")
public class MergePersonOrUpdateLocalPersonIdRequest {

    @XmlElement(required = true)
    protected HeaderType header;
    @XmlElement(required = true)
    protected List<MergePersonOrUpdateLocalPersonIdRequest.Person> person;

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
     * Gets the value of the person property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the person property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MergePersonOrUpdateLocalPersonIdRequest.Person }
     * 
     * 
     */
    public List<MergePersonOrUpdateLocalPersonIdRequest.Person> getPerson() {
        if (person == null) {
            person = new ArrayList<MergePersonOrUpdateLocalPersonIdRequest.Person>();
        }
        return this.person;
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
     *         &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" minOccurs="0"/>
     *         &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
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
        "ahvvn",
        "localPersonId"
    })
    public static class Person {

        @XmlSchemaType(name = "unsignedLong")
        protected Long ahvvn;
        protected NamedPersonIdType localPersonId;

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

    }

}
