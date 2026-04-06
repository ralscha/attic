
package ch.ech.xmlns.ech_0085._1;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="timeInterval">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="since" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="until" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="refused">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="reason">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *                           &lt;enumeration value="1"/>
 *                           &lt;enumeration value="2"/>
 *                           &lt;enumeration value="3"/>
 *                           &lt;enumeration value="4"/>
 *                           &lt;enumeration value="5"/>
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
 *           &lt;sequence>
 *             &lt;element name="warningLastUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *             &lt;element name="cancelledAhvvnList">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                       &lt;element name="cancelledAhvvn">
 *                         &lt;complexType>
 *                           &lt;complexContent>
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                               &lt;sequence>
 *                                 &lt;element name="cancellationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                 &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                                 &lt;sequence minOccurs="0">
 *                                   &lt;element name="newAhvvnCandidate" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" maxOccurs="2" minOccurs="2"/>
 *                                 &lt;/sequence>
 *                               &lt;/sequence>
 *                             &lt;/restriction>
 *                           &lt;/complexContent>
 *                         &lt;/complexType>
 *                       &lt;/element>
 *                     &lt;/sequence>
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *             &lt;element name="inactiveAhvvnList">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                       &lt;element name="inactiveAhvvn">
 *                         &lt;complexType>
 *                           &lt;complexContent>
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                               &lt;sequence>
 *                                 &lt;element name="inactivationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                 &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                                 &lt;element name="newAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                               &lt;/sequence>
 *                             &lt;/restriction>
 *                           &lt;/complexContent>
 *                         &lt;/complexType>
 *                       &lt;/element>
 *                     &lt;/sequence>
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *           &lt;/sequence>
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
    "timeInterval",
    "refused",
    "warningLastUpdateTime",
    "cancelledAhvvnList",
    "inactiveAhvvnList"
})
@XmlRootElement(name = "getCancelledAndInactiveAhvvnResponse")
public class GetCancelledAndInactiveAhvvnResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(required = true)
    protected GetCancelledAndInactiveAhvvnResponse.TimeInterval timeInterval;
    protected GetCancelledAndInactiveAhvvnResponse.Refused refused;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar warningLastUpdateTime;
    protected GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList cancelledAhvvnList;
    protected GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList inactiveAhvvnList;

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
     * Gets the value of the timeInterval property.
     * 
     * @return
     *     possible object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.TimeInterval }
     *     
     */
    public GetCancelledAndInactiveAhvvnResponse.TimeInterval getTimeInterval() {
        return timeInterval;
    }

    /**
     * Sets the value of the timeInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.TimeInterval }
     *     
     */
    public void setTimeInterval(GetCancelledAndInactiveAhvvnResponse.TimeInterval value) {
        this.timeInterval = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.Refused }
     *     
     */
    public GetCancelledAndInactiveAhvvnResponse.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.Refused }
     *     
     */
    public void setRefused(GetCancelledAndInactiveAhvvnResponse.Refused value) {
        this.refused = value;
    }

    /**
     * Gets the value of the warningLastUpdateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getWarningLastUpdateTime() {
        return warningLastUpdateTime;
    }

    /**
     * Sets the value of the warningLastUpdateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setWarningLastUpdateTime(XMLGregorianCalendar value) {
        this.warningLastUpdateTime = value;
    }

    /**
     * Gets the value of the cancelledAhvvnList property.
     * 
     * @return
     *     possible object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList }
     *     
     */
    public GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList getCancelledAhvvnList() {
        return cancelledAhvvnList;
    }

    /**
     * Sets the value of the cancelledAhvvnList property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList }
     *     
     */
    public void setCancelledAhvvnList(GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList value) {
        this.cancelledAhvvnList = value;
    }

    /**
     * Gets the value of the inactiveAhvvnList property.
     * 
     * @return
     *     possible object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList }
     *     
     */
    public GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList getInactiveAhvvnList() {
        return inactiveAhvvnList;
    }

    /**
     * Sets the value of the inactiveAhvvnList property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList }
     *     
     */
    public void setInactiveAhvvnList(GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList value) {
        this.inactiveAhvvnList = value;
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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element name="cancelledAhvvn">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="cancellationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *                   &lt;sequence minOccurs="0">
     *                     &lt;element name="newAhvvnCandidate" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" maxOccurs="2" minOccurs="2"/>
     *                   &lt;/sequence>
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
        "cancelledAhvvn"
    })
    public static class CancelledAhvvnList {

        protected List<GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn> cancelledAhvvn;

        /**
         * Gets the value of the cancelledAhvvn property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cancelledAhvvn property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCancelledAhvvn().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn }
         * 
         * 
         */
        public List<GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn> getCancelledAhvvn() {
            if (cancelledAhvvn == null) {
                cancelledAhvvn = new ArrayList<GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn>();
            }
            return this.cancelledAhvvn;
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
         *         &lt;element name="cancellationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
         *         &lt;sequence minOccurs="0">
         *           &lt;element name="newAhvvnCandidate" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType" maxOccurs="2" minOccurs="2"/>
         *         &lt;/sequence>
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
            "cancellationTimestamp",
            "oldAhvvn",
            "newAhvvnCandidate"
        })
        public static class CancelledAhvvn {

            @XmlElement(required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar cancellationTimestamp;
            @XmlSchemaType(name = "unsignedLong")
            protected long oldAhvvn;
            @XmlElement(type = Long.class)
            @XmlSchemaType(name = "unsignedLong")
            protected List<Long> newAhvvnCandidate;

            /**
             * Gets the value of the cancellationTimestamp property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getCancellationTimestamp() {
                return cancellationTimestamp;
            }

            /**
             * Sets the value of the cancellationTimestamp property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setCancellationTimestamp(XMLGregorianCalendar value) {
                this.cancellationTimestamp = value;
            }

            /**
             * Gets the value of the oldAhvvn property.
             * 
             */
            public long getOldAhvvn() {
                return oldAhvvn;
            }

            /**
             * Sets the value of the oldAhvvn property.
             * 
             */
            public void setOldAhvvn(long value) {
                this.oldAhvvn = value;
            }

            /**
             * Gets the value of the newAhvvnCandidate property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the newAhvvnCandidate property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getNewAhvvnCandidate().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Long }
             * 
             * 
             */
            public List<Long> getNewAhvvnCandidate() {
                if (newAhvvnCandidate == null) {
                    newAhvvnCandidate = new ArrayList<Long>();
                }
                return this.newAhvvnCandidate;
            }

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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element name="inactiveAhvvn">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="inactivationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *                   &lt;element name="newAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
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
        "inactiveAhvvn"
    })
    public static class InactiveAhvvnList {

        protected List<GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn> inactiveAhvvn;

        /**
         * Gets the value of the inactiveAhvvn property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the inactiveAhvvn property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInactiveAhvvn().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn }
         * 
         * 
         */
        public List<GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn> getInactiveAhvvn() {
            if (inactiveAhvvn == null) {
                inactiveAhvvn = new ArrayList<GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn>();
            }
            return this.inactiveAhvvn;
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
         *         &lt;element name="inactivationTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="oldAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
         *         &lt;element name="newAhvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
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
            "inactivationTimestamp",
            "oldAhvvn",
            "newAhvvn"
        })
        public static class InactiveAhvvn {

            @XmlElement(required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar inactivationTimestamp;
            @XmlSchemaType(name = "unsignedLong")
            protected long oldAhvvn;
            @XmlSchemaType(name = "unsignedLong")
            protected long newAhvvn;

            /**
             * Gets the value of the inactivationTimestamp property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getInactivationTimestamp() {
                return inactivationTimestamp;
            }

            /**
             * Sets the value of the inactivationTimestamp property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setInactivationTimestamp(XMLGregorianCalendar value) {
                this.inactivationTimestamp = value;
            }

            /**
             * Gets the value of the oldAhvvn property.
             * 
             */
            public long getOldAhvvn() {
                return oldAhvvn;
            }

            /**
             * Sets the value of the oldAhvvn property.
             * 
             */
            public void setOldAhvvn(long value) {
                this.oldAhvvn = value;
            }

            /**
             * Gets the value of the newAhvvn property.
             * 
             */
            public long getNewAhvvn() {
                return newAhvvn;
            }

            /**
             * Sets the value of the newAhvvn property.
             * 
             */
            public void setNewAhvvn(long value) {
                this.newAhvvn = value;
            }

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
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *               &lt;enumeration value="5"/>
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
     *         &lt;element name="since" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="until" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "since",
        "until"
    })
    public static class TimeInterval {

        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar since;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar until;

        /**
         * Gets the value of the since property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSince() {
            return since;
        }

        /**
         * Sets the value of the since property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSince(XMLGregorianCalendar value) {
            this.since = value;
        }

        /**
         * Gets the value of the until property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getUntil() {
            return until;
        }

        /**
         * Sets the value of the until property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setUntil(XMLGregorianCalendar value) {
            this.until = value;
        }

    }

}
