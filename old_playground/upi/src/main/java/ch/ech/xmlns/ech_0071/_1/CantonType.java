
package ch.ech.xmlns.ech_0071._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cantonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cantonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cantonId" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonIdType"/>
 *         &lt;element name="cantonAbbreviation" type="{http://www.ech.ch/xmlns/eCH-0071/1}cantonAbbreviationType"/>
 *         &lt;element name="cantonLongName" type="{http://www.ech.ch/xmlns/eCH-0071/1}string40Type"/>
 *         &lt;element name="cantonDateOfChange" type="{http://www.ech.ch/xmlns/eCH-0071/1}dateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cantonType", propOrder = {
    "cantonId",
    "cantonAbbreviation",
    "cantonLongName",
    "cantonDateOfChange"
})
public class CantonType {

    @XmlSchemaType(name = "integer")
    protected int cantonId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    protected CantonAbbreviationType cantonAbbreviation;
    @XmlElement(required = true)
    protected String cantonLongName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar cantonDateOfChange;

    /**
     * Gets the value of the cantonId property.
     * 
     */
    public int getCantonId() {
        return cantonId;
    }

    /**
     * Sets the value of the cantonId property.
     * 
     */
    public void setCantonId(int value) {
        this.cantonId = value;
    }

    /**
     * Gets the value of the cantonAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link CantonAbbreviationType }
     *     
     */
    public CantonAbbreviationType getCantonAbbreviation() {
        return cantonAbbreviation;
    }

    /**
     * Sets the value of the cantonAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CantonAbbreviationType }
     *     
     */
    public void setCantonAbbreviation(CantonAbbreviationType value) {
        this.cantonAbbreviation = value;
    }

    /**
     * Gets the value of the cantonLongName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCantonLongName() {
        return cantonLongName;
    }

    /**
     * Sets the value of the cantonLongName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCantonLongName(String value) {
        this.cantonLongName = value;
    }

    /**
     * Gets the value of the cantonDateOfChange property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCantonDateOfChange() {
        return cantonDateOfChange;
    }

    /**
     * Sets the value of the cantonDateOfChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCantonDateOfChange(XMLGregorianCalendar value) {
        this.cantonDateOfChange = value;
    }

}
