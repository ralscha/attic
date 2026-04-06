
package ch.ech.xmlns.ech_0085._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0084._1.PersonInformationShortOptType;
import ch.ech.xmlns.ech_0084._1.ValuesStoredUnderAhvvnType;


/**
 * <p>Java class for searchPersonResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchPersonResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;choice>
 *           &lt;element name="accepted">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="found">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                               &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="maybeFound">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="candidate" maxOccurs="unbounded">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                                         &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
 *                                         &lt;element name="historicalValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationShortOptType" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="notFound" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
 *                           &lt;enumeration value="2"/>
 *                           &lt;enumeration value="3"/>
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
@XmlType(name = "searchPersonResponseType", propOrder = {
    "timestamp",
    "accepted",
    "refused"
})
public class SearchPersonResponseType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    protected SearchPersonResponseType.Accepted accepted;
    protected SearchPersonResponseType.Refused refused;

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
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPersonResponseType.Accepted }
     *     
     */
    public SearchPersonResponseType.Accepted getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPersonResponseType.Accepted }
     *     
     */
    public void setAccepted(SearchPersonResponseType.Accepted value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPersonResponseType.Refused }
     *     
     */
    public SearchPersonResponseType.Refused getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPersonResponseType.Refused }
     *     
     */
    public void setRefused(SearchPersonResponseType.Refused value) {
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
     *         &lt;element name="found">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *                   &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="maybeFound">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="candidate" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *                             &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
     *                             &lt;element name="historicalValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationShortOptType" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="notFound" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
        "found",
        "maybeFound",
        "notFound"
    })
    public static class Accepted {

        protected SearchPersonResponseType.Accepted.Found found;
        protected SearchPersonResponseType.Accepted.MaybeFound maybeFound;
        protected Object notFound;

        /**
         * Gets the value of the found property.
         * 
         * @return
         *     possible object is
         *     {@link SearchPersonResponseType.Accepted.Found }
         *     
         */
        public SearchPersonResponseType.Accepted.Found getFound() {
            return found;
        }

        /**
         * Sets the value of the found property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchPersonResponseType.Accepted.Found }
         *     
         */
        public void setFound(SearchPersonResponseType.Accepted.Found value) {
            this.found = value;
        }

        /**
         * Gets the value of the maybeFound property.
         * 
         * @return
         *     possible object is
         *     {@link SearchPersonResponseType.Accepted.MaybeFound }
         *     
         */
        public SearchPersonResponseType.Accepted.MaybeFound getMaybeFound() {
            return maybeFound;
        }

        /**
         * Sets the value of the maybeFound property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchPersonResponseType.Accepted.MaybeFound }
         *     
         */
        public void setMaybeFound(SearchPersonResponseType.Accepted.MaybeFound value) {
            this.maybeFound = value;
        }

        /**
         * Gets the value of the notFound property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getNotFound() {
            return notFound;
        }

        /**
         * Sets the value of the notFound property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setNotFound(Object value) {
            this.notFound = value;
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
            "ahvvn",
            "valuesStoredUnderAhvvn"
        })
        public static class Found {

            @XmlSchemaType(name = "unsignedLong")
            protected long ahvvn;
            @XmlElement(required = true)
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
         *         &lt;element name="candidate" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
         *                   &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
         *                   &lt;element name="historicalValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationShortOptType" maxOccurs="unbounded" minOccurs="0"/>
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
            "candidate"
        })
        public static class MaybeFound {

            @XmlElement(required = true)
            protected List<SearchPersonResponseType.Accepted.MaybeFound.Candidate> candidate;

            /**
             * Gets the value of the candidate property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the candidate property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCandidate().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SearchPersonResponseType.Accepted.MaybeFound.Candidate }
             * 
             * 
             */
            public List<SearchPersonResponseType.Accepted.MaybeFound.Candidate> getCandidate() {
                if (candidate == null) {
                    candidate = new ArrayList<SearchPersonResponseType.Accepted.MaybeFound.Candidate>();
                }
                return this.candidate;
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
             *         &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
             *         &lt;element name="historicalValues" type="{http://www.ech.ch/xmlns/eCH-0084/1}personInformationShortOptType" maxOccurs="unbounded" minOccurs="0"/>
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
                "valuesStoredUnderAhvvn",
                "historicalValues"
            })
            public static class Candidate {

                @XmlSchemaType(name = "unsignedLong")
                protected long ahvvn;
                @XmlElement(required = true)
                protected ValuesStoredUnderAhvvnType valuesStoredUnderAhvvn;
                protected List<PersonInformationShortOptType> historicalValues;

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

                /**
                 * Gets the value of the historicalValues property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the historicalValues property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getHistoricalValues().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link PersonInformationShortOptType }
                 * 
                 * 
                 */
                public List<PersonInformationShortOptType> getHistoricalValues() {
                    if (historicalValues == null) {
                        historicalValues = new ArrayList<PersonInformationShortOptType>();
                    }
                    return this.historicalValues;
                }

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
