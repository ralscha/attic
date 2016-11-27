
package ch.ralscha.wsdemo.ds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://wsdemo.ralscha.ch/}dsSessionDto" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://wsdemo.ralscha.ch/}dsDocumentDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDocument", propOrder = {
    "arg0",
    "arg1",
    "arg2"
})
public class UpdateDocument {

    protected DsSessionDto arg0;
    protected String arg1;
    protected DsDocumentDto arg2;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link DsSessionDto }
     *     
     */
    public DsSessionDto getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DsSessionDto }
     *     
     */
    public void setArg0(DsSessionDto value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg1(String value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link DsDocumentDto }
     *     
     */
    public DsDocumentDto getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DsDocumentDto }
     *     
     */
    public void setArg2(DsDocumentDto value) {
        this.arg2 = value;
    }

}
