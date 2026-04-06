
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Type describing the header of each message.
 * 
 * <p>Java class for headerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="headerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sedexId" type="{http://www.ech.ch/xmlns/eCH-0090/1}participantIdType"/>
 *         &lt;element name="declarationNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="declarationLocalReference">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="orderingTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerType", propOrder = {
    "sedexId",
    "declarationNumber",
    "declarationLocalReference",
    "orderingTimestamp"
})
public class HeaderType {

    @XmlElement(required = true)
    protected String sedexId;
    protected long declarationNumber;
    @XmlElement(required = true)
    protected String declarationLocalReference;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderingTimestamp;

    /**
     * Gets the value of the sedexId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSedexId() {
        return sedexId;
    }

    /**
     * Sets the value of the sedexId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSedexId(String value) {
        this.sedexId = value;
    }

    /**
     * Gets the value of the declarationNumber property.
     * 
     */
    public long getDeclarationNumber() {
        return declarationNumber;
    }

    /**
     * Sets the value of the declarationNumber property.
     * 
     */
    public void setDeclarationNumber(long value) {
        this.declarationNumber = value;
    }

    /**
     * Gets the value of the declarationLocalReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeclarationLocalReference() {
        return declarationLocalReference;
    }

    /**
     * Sets the value of the declarationLocalReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeclarationLocalReference(String value) {
        this.declarationLocalReference = value;
    }

    /**
     * Gets the value of the orderingTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderingTimestamp() {
        return orderingTimestamp;
    }

    /**
     * Sets the value of the orderingTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderingTimestamp(XMLGregorianCalendar value) {
        this.orderingTimestamp = value;
    }

}
