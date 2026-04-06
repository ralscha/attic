
package ch.ech.xmlns.ech_0011._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="reportetPerson" type="{http://www.ech.ch/xmlns/eCH-0011/3}reportedPersonType"/>
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
    "reportetPerson"
})
@XmlRootElement(name = "personRoot")
public class PersonRoot {

    @XmlElement(required = true)
    protected ReportedPersonType reportetPerson;

    /**
     * Gets the value of the reportetPerson property.
     * 
     * @return
     *     possible object is
     *     {@link ReportedPersonType }
     *     
     */
    public ReportedPersonType getReportetPerson() {
        return reportetPerson;
    }

    /**
     * Sets the value of the reportetPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportedPersonType }
     *     
     */
    public void setReportetPerson(ReportedPersonType value) {
        this.reportetPerson = value;
    }

}
