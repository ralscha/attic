
package ch.ech.xmlns.ech_0086._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ch.ech.xmlns.ech_0044._1.NamedPersonIdType;
import ch.ech.xmlns.ech_0084._1.TypeOfRecordType;
import ch.ech.xmlns.ech_0084._1.ValuesStoredUnderAhvvnType;


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
 *         &lt;element name="refused" type="{http://www.ech.ch/xmlns/eCH-0086/1}refusedType"/>
 *         &lt;sequence>
 *           &lt;element name="sourceIdToCompareWith" type="{http://www.ech.ch/xmlns/eCH-0090/1}participantIdType" minOccurs="0"/>
 *           &lt;element name="comparedData" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                     &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                     &lt;choice>
 *                       &lt;element name="identicalData" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                       &lt;element name="differentData">
 *                         &lt;complexType>
 *                           &lt;complexContent>
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                               &lt;sequence>
 *                                 &lt;element name="latestAhvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
 *                                 &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
 *                                 &lt;element name="sourceIdToCompareWithNotFound" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                 &lt;element name="typeOfRecord" type="{http://www.ech.ch/xmlns/eCH-0084/1}typeOfRecord_Type" minOccurs="0"/>
 *                                 &lt;element name="shownDocument" type="{http://www.ech.ch/xmlns/eCH-0084/1}shownDocument_Type" minOccurs="0"/>
 *                                 &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
 *                               &lt;/sequence>
 *                             &lt;/restriction>
 *                           &lt;/complexContent>
 *                         &lt;/complexType>
 *                       &lt;/element>
 *                       &lt;element name="refused" type="{http://www.ech.ch/xmlns/eCH-0086/1}refusedType"/>
 *                     &lt;/choice>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
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
    "refused",
    "sourceIdToCompareWith",
    "comparedData"
})
@XmlRootElement(name = "compareDataResponse")
public class CompareDataResponse {

    protected RefusedType refused;
    protected String sourceIdToCompareWith;
    protected List<CompareDataResponse.ComparedData> comparedData;

    /**
     * Gets the value of the refused property.
     * 
     * @return
     *     possible object is
     *     {@link RefusedType }
     *     
     */
    public RefusedType getRefused() {
        return refused;
    }

    /**
     * Sets the value of the refused property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefusedType }
     *     
     */
    public void setRefused(RefusedType value) {
        this.refused = value;
    }

    /**
     * Gets the value of the sourceIdToCompareWith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceIdToCompareWith() {
        return sourceIdToCompareWith;
    }

    /**
     * Sets the value of the sourceIdToCompareWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceIdToCompareWith(String value) {
        this.sourceIdToCompareWith = value;
    }

    /**
     * Gets the value of the comparedData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comparedData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComparedData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompareDataResponse.ComparedData }
     * 
     * 
     */
    public List<CompareDataResponse.ComparedData> getComparedData() {
        if (comparedData == null) {
            comparedData = new ArrayList<CompareDataResponse.ComparedData>();
        }
        return this.comparedData;
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
     *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="ahvvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *         &lt;choice>
     *           &lt;element name="identicalData" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *           &lt;element name="differentData">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="latestAhvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
     *                     &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
     *                     &lt;element name="sourceIdToCompareWithNotFound" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                     &lt;element name="typeOfRecord" type="{http://www.ech.ch/xmlns/eCH-0084/1}typeOfRecord_Type" minOccurs="0"/>
     *                     &lt;element name="shownDocument" type="{http://www.ech.ch/xmlns/eCH-0084/1}shownDocument_Type" minOccurs="0"/>
     *                     &lt;element name="valuesStoredUnderAhvvn" type="{http://www.ech.ch/xmlns/eCH-0084/1}valuesStoredUnderAhvvn_Type"/>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="refused" type="{http://www.ech.ch/xmlns/eCH-0086/1}refusedType"/>
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
        "ahvvn",
        "identicalData",
        "differentData",
        "refused"
    })
    public static class ComparedData {

        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar timestamp;
        @XmlSchemaType(name = "unsignedLong")
        protected long ahvvn;
        protected Object identicalData;
        protected CompareDataResponse.ComparedData.DifferentData differentData;
        protected RefusedType refused;

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
         * Gets the value of the identicalData property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getIdenticalData() {
            return identicalData;
        }

        /**
         * Sets the value of the identicalData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setIdenticalData(Object value) {
            this.identicalData = value;
        }

        /**
         * Gets the value of the differentData property.
         * 
         * @return
         *     possible object is
         *     {@link CompareDataResponse.ComparedData.DifferentData }
         *     
         */
        public CompareDataResponse.ComparedData.DifferentData getDifferentData() {
            return differentData;
        }

        /**
         * Sets the value of the differentData property.
         * 
         * @param value
         *     allowed object is
         *     {@link CompareDataResponse.ComparedData.DifferentData }
         *     
         */
        public void setDifferentData(CompareDataResponse.ComparedData.DifferentData value) {
            this.differentData = value;
        }

        /**
         * Gets the value of the refused property.
         * 
         * @return
         *     possible object is
         *     {@link RefusedType }
         *     
         */
        public RefusedType getRefused() {
            return refused;
        }

        /**
         * Sets the value of the refused property.
         * 
         * @param value
         *     allowed object is
         *     {@link RefusedType }
         *     
         */
        public void setRefused(RefusedType value) {
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
         *         &lt;element name="latestAhvn" type="{http://www.ech.ch/xmlns/eCH-0044/1}vnType"/>
         *         &lt;element name="localPersonId" type="{http://www.ech.ch/xmlns/eCH-0044/1}namedPersonIdType" minOccurs="0"/>
         *         &lt;element name="sourceIdToCompareWithNotFound" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *         &lt;element name="typeOfRecord" type="{http://www.ech.ch/xmlns/eCH-0084/1}typeOfRecord_Type" minOccurs="0"/>
         *         &lt;element name="shownDocument" type="{http://www.ech.ch/xmlns/eCH-0084/1}shownDocument_Type" minOccurs="0"/>
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
            "latestAhvn",
            "localPersonId",
            "sourceIdToCompareWithNotFound",
            "typeOfRecord",
            "shownDocument",
            "valuesStoredUnderAhvvn"
        })
        public static class DifferentData {

            @XmlSchemaType(name = "unsignedLong")
            protected long latestAhvn;
            protected NamedPersonIdType localPersonId;
            protected Object sourceIdToCompareWithNotFound;
            @XmlSchemaType(name = "token")
            protected TypeOfRecordType typeOfRecord;
            @XmlSchemaType(name = "unsignedShort")
            protected Integer shownDocument;
            @XmlElement(required = true)
            protected ValuesStoredUnderAhvvnType valuesStoredUnderAhvvn;

            /**
             * Gets the value of the latestAhvn property.
             * 
             */
            public long getLatestAhvn() {
                return latestAhvn;
            }

            /**
             * Sets the value of the latestAhvn property.
             * 
             */
            public void setLatestAhvn(long value) {
                this.latestAhvn = value;
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
             * Gets the value of the sourceIdToCompareWithNotFound property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getSourceIdToCompareWithNotFound() {
                return sourceIdToCompareWithNotFound;
            }

            /**
             * Sets the value of the sourceIdToCompareWithNotFound property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setSourceIdToCompareWithNotFound(Object value) {
                this.sourceIdToCompareWithNotFound = value;
            }

            /**
             * Gets the value of the typeOfRecord property.
             * 
             * @return
             *     possible object is
             *     {@link TypeOfRecordType }
             *     
             */
            public TypeOfRecordType getTypeOfRecord() {
                return typeOfRecord;
            }

            /**
             * Sets the value of the typeOfRecord property.
             * 
             * @param value
             *     allowed object is
             *     {@link TypeOfRecordType }
             *     
             */
            public void setTypeOfRecord(TypeOfRecordType value) {
                this.typeOfRecord = value;
            }

            /**
             * Gets the value of the shownDocument property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getShownDocument() {
                return shownDocument;
            }

            /**
             * Sets the value of the shownDocument property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setShownDocument(Integer value) {
                this.shownDocument = value;
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

    }

}
