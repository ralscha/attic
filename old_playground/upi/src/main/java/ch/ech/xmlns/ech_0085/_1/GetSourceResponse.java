
package ch.ech.xmlns.ech_0085._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
 *         &lt;element name="inputAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *         &lt;choice>
 *           &lt;element name="accepted">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="sedexIdSource" type="{http://www.ech.ch/xmlns/eCH-0090/1}participantIdType"/>
 *                     &lt;element name="sedexIdSourceUndefined" type="{http://www.ech.ch/xmlns/eCH-0011/3}unknownType"/>
 *                   &lt;/choice>
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
@XmlType(name = "", propOrder = {
    "timestamp",
    "inputAhvvn",
    "accepted",
    "refused"
})
@XmlRootElement(name = "getSourceResponse")
public class GetSourceResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlSchemaType(name = "unsignedLong")
    protected long inputAhvvn;
    protected GetSourceResponse.Accepted accepted;
    protected GetSourceResponse.Refused refused;

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
     *     {@link GetSourceResponse.Accepted }
     *     
     */
    public GetSourceResponse.Accepted getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSourceResponse.Accepted }
     *     
     */
    public void setAccepted(GetSourceResponse.Accepted value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link GetSourceResponse.Refused }
     *     
     */
    public GetSourceResponse.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSourceResponse.Refused }
     *     
     */
    public void setRefused(GetSourceResponse.Refused value) {
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
     *       &lt;choice>
     *         &lt;element name="sedexIdSource" type="{http://www.ech.ch/xmlns/eCH-0090/1}participantIdType"/>
     *         &lt;element name="sedexIdSourceUndefined" type="{http://www.ech.ch/xmlns/eCH-0011/3}unknownType"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sedexIdSource",
        "sedexIdSourceUndefined"
    })
    public static class Accepted {

        protected String sedexIdSource;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String sedexIdSourceUndefined;

        /**
         * Gets the value of the sedexIdSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSedexIdSource() {
            return sedexIdSource;
        }

        /**
         * Sets the value of the sedexIdSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSedexIdSource(String value) {
            this.sedexIdSource = value;
        }

        /**
         * Gets the value of the sedexIdSourceUndefined property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSedexIdSourceUndefined() {
            return sedexIdSourceUndefined;
        }

        /**
         * Sets the value of the sedexIdSourceUndefined property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSedexIdSourceUndefined(String value) {
            this.sedexIdSourceUndefined = value;
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
