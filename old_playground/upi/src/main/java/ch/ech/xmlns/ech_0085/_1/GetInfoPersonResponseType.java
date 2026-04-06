
package ch.ech.xmlns.ech_0085._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0084._1.ValuesStoredUnderAhvvnType;


/**
 * <p>Java class for getInfoPersonResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInfoPersonResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="InputAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *         &lt;choice>
 *           &lt;element name="accepted">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="latestAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                     &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
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
@XmlType(name = "getInfoPersonResponseType", propOrder = {
    "timestamp",
    "inputAhvvn",
    "accepted",
    "refused"
})
public class GetInfoPersonResponseType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "InputAhvvn")
    @XmlSchemaType(name = "unsignedLong")
    protected long inputAhvvn;
    protected GetInfoPersonResponseType.Accepted accepted;
    protected GetInfoPersonResponseType.Refused refused;

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
     * Gets the value of the inputAhvvn property.
     * 
     */
    public long getInputAhvvn() {
        return inputAhvvn;
    }

    /**
     * Sets the value of the inputAhvvn property.
     * 
     */
    public void setInputAhvvn(long value) {
        this.inputAhvvn = value;
    }

    /**
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link GetInfoPersonResponseType.Accepted }
     *     
     */
    public GetInfoPersonResponseType.Accepted getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInfoPersonResponseType.Accepted }
     *     
     */
    public void setAccepted(GetInfoPersonResponseType.Accepted value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link GetInfoPersonResponseType.Refused }
     *     
     */
    public GetInfoPersonResponseType.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInfoPersonResponseType.Refused }
     *     
     */
    public void setRefused(GetInfoPersonResponseType.Refused value) {
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
     *         &lt;element name="latestAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *         &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
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
        "latestAhvvn",
        "valuesStoredUnderAhvvn"
    })
    public static class Accepted {

        @XmlSchemaType(name = "unsignedLong")
        protected long latestAhvvn;
        @XmlElement(required = true)
        protected ValuesStoredUnderAhvvnType valuesStoredUnderAhvvn;

        /**
         * Gets the value of the latestAhvvn property.
         * 
         */
        public long getLatestAhvvn() {
            return latestAhvvn;
        }

        /**
         * Sets the value of the latestAhvvn property.
         * 
         */
        public void setLatestAhvvn(long value) {
            this.latestAhvvn = value;
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
        "detailedReason"
    })
    public static class Refused {

        protected short reason;
        protected String detailedReason;

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

    }

}
