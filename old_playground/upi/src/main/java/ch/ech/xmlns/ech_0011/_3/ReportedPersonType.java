
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reportedPersonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reportedPersonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person" type="{http://www.ech.ch/xmlns/eCH-0011/3}personType"/>
 *         &lt;element name="anyPerson" type="{http://www.ech.ch/xmlns/eCH-0011/3}anyPersonType"/>
 *         &lt;choice>
 *           &lt;element name="hasMainResidence" type="{http://www.ech.ch/xmlns/eCH-0011/3}mainResidenceType"/>
 *           &lt;element name="hasSecondaryResidence" type="{http://www.ech.ch/xmlns/eCH-0011/3}secondaryResidenceType"/>
 *           &lt;element name="hasOtherResidence" type="{http://www.ech.ch/xmlns/eCH-0011/3}otherResidenceType"/>
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
@XmlType(name = "reportedPersonType", propOrder = {
    "person",
    "anyPerson",
    "hasMainResidence",
    "hasSecondaryResidence",
    "hasOtherResidence"
})
public class ReportedPersonType {

    @XmlElement(required = true)
    protected PersonType person;
    @XmlElement(required = true)
    protected AnyPersonType anyPerson;
    protected MainResidenceType hasMainResidence;
    protected SecondaryResidenceType hasSecondaryResidence;
    protected OtherResidenceType hasOtherResidence;

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setPerson(PersonType value) {
        this.person = value;
    }

    /**
     * Gets the value of the anyPerson property.
     * 
     * @return
     *     possible object is
     *     {@link AnyPersonType }
     *     
     */
    public AnyPersonType getAnyPerson() {
        return anyPerson;
    }

    /**
     * Sets the value of the anyPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyPersonType }
     *     
     */
    public void setAnyPerson(AnyPersonType value) {
        this.anyPerson = value;
    }

    /**
     * Gets the value of the hasMainResidence property.
     * 
     * @return
     *     possible object is
     *     {@link MainResidenceType }
     *     
     */
    public MainResidenceType getHasMainResidence() {
        return hasMainResidence;
    }

    /**
     * Sets the value of the hasMainResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link MainResidenceType }
     *     
     */
    public void setHasMainResidence(MainResidenceType value) {
        this.hasMainResidence = value;
    }

    /**
     * Gets the value of the hasSecondaryResidence property.
     * 
     * @return
     *     possible object is
     *     {@link SecondaryResidenceType }
     *     
     */
    public SecondaryResidenceType getHasSecondaryResidence() {
        return hasSecondaryResidence;
    }

    /**
     * Sets the value of the hasSecondaryResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondaryResidenceType }
     *     
     */
    public void setHasSecondaryResidence(SecondaryResidenceType value) {
        this.hasSecondaryResidence = value;
    }

    /**
     * Gets the value of the hasOtherResidence property.
     * 
     * @return
     *     possible object is
     *     {@link OtherResidenceType }
     *     
     */
    public OtherResidenceType getHasOtherResidence() {
        return hasOtherResidence;
    }

    /**
     * Sets the value of the hasOtherResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherResidenceType }
     *     
     */
    public void setHasOtherResidence(OtherResidenceType value) {
        this.hasOtherResidence = value;
    }

}
