
package ch.ech.xmlns.ech_0071._1;

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
 *         &lt;element name="validFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="cantons">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="canton" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonType" maxOccurs="26" minOccurs="26"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="districts">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="district" type="{http://www.ech.ch/xmlns/eCH-0071/1}districtType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="municipalities">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="municipality" type="{http://www.ech.ch/xmlns/eCH-0071/1}municipalityType" maxOccurs="unbounded"/>
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
    "validFrom",
    "cantons",
    "districts",
    "municipalities"
})
@XmlRootElement(name = "nomenclature")
public class Nomenclature {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validFrom;
    @XmlElement(required = true)
    protected Nomenclature.Cantons cantons;
    @XmlElement(required = true)
    protected Nomenclature.Districts districts;
    @XmlElement(required = true)
    protected Nomenclature.Municipalities municipalities;

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidFrom(XMLGregorianCalendar value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the cantons property.
     * 
     * @return
     *     possible object is
     *     {@link Nomenclature.Cantons }
     *     
     */
    public Nomenclature.Cantons getCantons() {
        return cantons;
    }

    /**
     * Sets the value of the cantons property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nomenclature.Cantons }
     *     
     */
    public void setCantons(Nomenclature.Cantons value) {
        this.cantons = value;
    }

    /**
     * Gets the value of the districts property.
     * 
     * @return
     *     possible object is
     *     {@link Nomenclature.Districts }
     *     
     */
    public Nomenclature.Districts getDistricts() {
        return districts;
    }

    /**
     * Sets the value of the districts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nomenclature.Districts }
     *     
     */
    public void setDistricts(Nomenclature.Districts value) {
        this.districts = value;
    }

    /**
     * Gets the value of the municipalities property.
     * 
     * @return
     *     possible object is
     *     {@link Nomenclature.Municipalities }
     *     
     */
    public Nomenclature.Municipalities getMunicipalities() {
        return municipalities;
    }

    /**
     * Sets the value of the municipalities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nomenclature.Municipalities }
     *     
     */
    public void setMunicipalities(Nomenclature.Municipalities value) {
        this.municipalities = value;
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
     *         &lt;element name="canton" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonType" maxOccurs="26" minOccurs="26"/>
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
        "canton"
    })
    public static class Cantons {

        @XmlElement(required = true)
        protected List<CantonType> canton;

        /**
         * Gets the value of the canton property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the canton property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCanton().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CantonType }
         * 
         * 
         */
        public List<CantonType> getCanton() {
            if (canton == null) {
                canton = new ArrayList<CantonType>();
            }
            return this.canton;
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
     *         &lt;element name="district" type="{http://www.ech.ch/xmlns/eCH-0071/1}districtType" maxOccurs="unbounded"/>
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
        "district"
    })
    public static class Districts {

        @XmlElement(required = true)
        protected List<DistrictType> district;

        /**
         * Gets the value of the district property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the district property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDistrict().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DistrictType }
         * 
         * 
         */
        public List<DistrictType> getDistrict() {
            if (district == null) {
                district = new ArrayList<DistrictType>();
            }
            return this.district;
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
     *         &lt;element name="municipality" type="{http://www.ech.ch/xmlns/eCH-0071/1}municipalityType" maxOccurs="unbounded"/>
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
        "municipality"
    })
    public static class Municipalities {

        @XmlElement(required = true)
        protected List<MunicipalityType> municipality;

        /**
         * Gets the value of the municipality property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the municipality property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMunicipality().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MunicipalityType }
         * 
         * 
         */
        public List<MunicipalityType> getMunicipality() {
            if (municipality == null) {
                municipality = new ArrayList<MunicipalityType>();
            }
            return this.municipality;
        }

    }

}
