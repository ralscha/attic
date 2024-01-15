
package com.trivadis;

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
 *         &lt;element name="GetMandatorShortResult" type="{http://www.trivadis.com/}ArrayOfMandatorShort" minOccurs="0"/>
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
    "getMandatorShortResult"
})
@XmlRootElement(name = "GetMandatorShortResponse")
public class GetMandatorShortResponse {

    @XmlElement(name = "GetMandatorShortResult")
    protected ArrayOfMandatorShort getMandatorShortResult;

    /**
     * Gets the value of the getMandatorShortResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMandatorShort }
     *     
     */
    public ArrayOfMandatorShort getGetMandatorShortResult() {
        return getMandatorShortResult;
    }

    /**
     * Sets the value of the getMandatorShortResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMandatorShort }
     *     
     */
    public void setGetMandatorShortResult(ArrayOfMandatorShort value) {
        this.getMandatorShortResult = value;
    }

}
