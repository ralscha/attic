
package ch.ech.xmlns.wupift._1;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getFileResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getFileResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="accepted">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="upiZIPResponse" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="refused">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reason">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="10"/>
 *                         &lt;enumeration value="11"/>
 *                         &lt;enumeration value="100"/>
 *                         &lt;enumeration value="101"/>
 *                         &lt;enumeration value="102"/>
 *                         &lt;enumeration value="103"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="detailedReason" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="comment" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFileResponseType", propOrder = {
    "accepted",
    "refused"
})
public class GetFileResponseType {

    protected GetFileResponseType.Accepted accepted;
    protected GetFileResponseType.Refused refused;

    /**
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link GetFileResponseType.Accepted }
     *     
     */
    public GetFileResponseType.Accepted getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetFileResponseType.Accepted }
     *     
     */
    public void setAccepted(GetFileResponseType.Accepted value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link GetFileResponseType.Refused }
     *     
     */
    public GetFileResponseType.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetFileResponseType.Refused }
     *     
     */
    public void setRefused(GetFileResponseType.Refused value) {
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
     *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="upiZIPResponse" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/>
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
        "ticket",
        "upiZIPResponse"
    })
    public static class Accepted {

        protected int ticket;
        @XmlElement(required = true, type = String.class)
        @XmlAttachmentRef
        @XmlSchemaType(name = "anyURI")
        protected DataHandler upiZIPResponse;

        /**
         * Gets the value of the ticket property.
         * 
         */
        public int getTicket() {
            return ticket;
        }

        /**
         * Sets the value of the ticket property.
         * 
         */
        public void setTicket(int value) {
            this.ticket = value;
        }

        /**
         * Gets the value of the upiZIPResponse property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public DataHandler getUpiZIPResponse() {
            return upiZIPResponse;
        }

        /**
         * Sets the value of the upiZIPResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUpiZIPResponse(DataHandler value) {
            this.upiZIPResponse = value;
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
     *               &lt;enumeration value="10"/>
     *               &lt;enumeration value="11"/>
     *               &lt;enumeration value="100"/>
     *               &lt;enumeration value="101"/>
     *               &lt;enumeration value="102"/>
     *               &lt;enumeration value="103"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="detailedReason" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="comment" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
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
