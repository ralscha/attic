
package ch.ech.xmlns.ech_0011._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anyPersonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="anyPersonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="swiss">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="placeOfOrigin" type="{http://www.ech.ch/xmlns/eCH-0011/3}placeOfOriginType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="foreigner">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="residencePermit" type="{http://www.ech.ch/xmlns/eCH-0006/2}residencePermitType"/>
 *                   &lt;element name="residencePermitTill" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="nameOnPassport" type="{http://www.ech.ch/xmlns/eCH-0044/1}baseNameType" minOccurs="0"/>
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
@XmlType(name = "anyPersonType", propOrder = {
    "swiss",
    "foreigner"
})
public class AnyPersonType {

    protected AnyPersonType.Swiss swiss;
    protected AnyPersonType.Foreigner foreigner;

    /**
     * Gets the value of the swiss property.
     * 
     * @return
     *     possible object is
     *     {@link AnyPersonType.Swiss }
     *     
     */
    public AnyPersonType.Swiss getSwiss() {
        return swiss;
    }

    /**
     * Sets the value of the swiss property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyPersonType.Swiss }
     *     
     */
    public void setSwiss(AnyPersonType.Swiss value) {
        this.swiss = value;
    }

    /**
     * Gets the value of the foreigner property.
     * 
     * @return
     *     possible object is
     *     {@link AnyPersonType.Foreigner }
     *     
     */
    public AnyPersonType.Foreigner getForeigner() {
        return foreigner;
    }

    /**
     * Sets the value of the foreigner property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyPersonType.Foreigner }
     *     
     */
    public void setForeigner(AnyPersonType.Foreigner value) {
        this.foreigner = value;
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
     *         &lt;element name="residencePermit" type="{http://www.ech.ch/xmlns/eCH-0006/2}residencePermitType"/>
     *         &lt;element name="residencePermitTill" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="nameOnPassport" type="{http://www.ech.ch/xmlns/eCH-0044/1}baseNameType" minOccurs="0"/>
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
        "residencePermit",
        "residencePermitTill",
        "nameOnPassport"
    })
    public static class Foreigner {

        @XmlElement(required = true)
        protected String residencePermit;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar residencePermitTill;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String nameOnPassport;

        /**
         * Gets the value of the residencePermit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResidencePermit() {
            return residencePermit;
        }

        /**
         * Sets the value of the residencePermit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResidencePermit(String value) {
            this.residencePermit = value;
        }

        /**
         * Gets the value of the residencePermitTill property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getResidencePermitTill() {
            return residencePermitTill;
        }

        /**
         * Sets the value of the residencePermitTill property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setResidencePermitTill(XMLGregorianCalendar value) {
            this.residencePermitTill = value;
        }

        /**
         * Gets the value of the nameOnPassport property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNameOnPassport() {
            return nameOnPassport;
        }

        /**
         * Sets the value of the nameOnPassport property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNameOnPassport(String value) {
            this.nameOnPassport = value;
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
     *         &lt;element name="placeOfOrigin" type="{http://www.ech.ch/xmlns/eCH-0011/3}placeOfOriginType" maxOccurs="unbounded"/>
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
        "placeOfOrigin"
    })
    public static class Swiss {

        @XmlElement(required = true)
        protected List<PlaceOfOriginType> placeOfOrigin;

        /**
         * Gets the value of the placeOfOrigin property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the placeOfOrigin property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlaceOfOrigin().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PlaceOfOriginType }
         * 
         * 
         */
        public List<PlaceOfOriginType> getPlaceOfOrigin() {
            if (placeOfOrigin == null) {
                placeOfOrigin = new ArrayList<PlaceOfOriginType>();
            }
            return this.placeOfOrigin;
        }

    }

}
