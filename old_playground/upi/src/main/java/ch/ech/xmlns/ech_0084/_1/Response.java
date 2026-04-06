
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="header" type="{http://www.ech.ch/xmlns/eCH-0084/1}headerType"/>
 *         &lt;choice>
 *           &lt;element name="accepted">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                     &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="manualProcessing" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *           &lt;element name="refused">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="reason">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *                           &lt;enumeration value="1"/>
 *                           &lt;enumeration value="3"/>
 *                           &lt;enumeration value="4"/>
 *                           &lt;enumeration value="10"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="detailedReason" minOccurs="0">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;maxLength value="50"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="comment" minOccurs="0">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;maxLength value="100"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
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
    "timestamp",
    "header",
    "accepted",
    "manualProcessing",
    "refused"
})
@XmlRootElement(name = "response")
public class Response {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(required = true)
    protected HeaderType header;
    protected Response.Accepted accepted;
    protected Object manualProcessing;
    protected Response.Refused refused;

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

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
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link Response.Accepted }
     *     
     */
    public Response.Accepted getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Response.Accepted }
     *     
     */
    public void setAccepted(Response.Accepted value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the manualProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getManualProcessing() {
        return manualProcessing;
    }

    /**
     * Sets the value of the manualProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setManualProcessing(Object value) {
        this.manualProcessing = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link Response.Refused }
     *     
     */
    public Response.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link Response.Refused }
     *     
     */
    public void setRefused(Response.Refused value) {
        this.refused = value;
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
     *         &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *         &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type" minOccurs="0"/>
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
        "valuesStoredUnderAhvvn"
    })
    public static class Accepted {

        @XmlSchemaType(name = "unsignedLong")
        protected long ahvvn;
        protected ValuesStoredUnderAhvvnType valuesStoredUnderAhvvn;

        /**
         * Gets the value of the ahvvn property.
         * 
         */
        public long getAhvvn() {
            return ahvvn;
        }

        /**
         * Sets the value of the ahvvn property.
         * 
         */
        public void setAhvvn(long value) {
            this.ahvvn = value;
        }

        /**
         * Gets the value of the valuesStoredUnderAhvvn property.
         * 
         * @return
         *     possible object is
         *     {@link ValuesStoredUnderAhvvnType }
         *     
         */
        public ValuesStoredUnderAhvvnType getValuesStoredUnderAhvvn() {
            return valuesStoredUnderAhvvn;
        }

        /**
         * Sets the value of the valuesStoredUnderAhvvn property.
         * 
         * @param value
         *     allowed object is
         *     {@link ValuesStoredUnderAhvvnType }
         *     
         */
        public void setValuesStoredUnderAhvvn(ValuesStoredUnderAhvvnType value) {
            this.valuesStoredUnderAhvvn = value;
        }

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
     *         &lt;element name="reason">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *               &lt;enumeration value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="detailedReason" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="comment" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
        "reason",
        "detailedReason",
        "comment"
    })
    public static class Refused {

        protected short reason;
        protected String detailedReason;
        protected String comment;

        /**
         * Gets the value of the reason property.
         * 
         */
        public short getReason() {
            return reason;
        }

        /**
         * Sets the value of the reason property.
         * 
         */
        public void setReason(short value) {
            this.reason = value;
        }

        /**
         * Gets the value of the detailedReason property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDetailedReason() {
            return detailedReason;
        }

        /**
         * Sets the value of the detailedReason property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDetailedReason(String value) {
            this.detailedReason = value;
        }

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComment(String value) {
            this.comment = value;
        }

    }

}
