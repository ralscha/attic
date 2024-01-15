
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
 *         &lt;element name="GetUserForMandatorResult" type="{http://www.trivadis.com/}ArrayOfUser" minOccurs="0"/>
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
    "getUserForMandatorResult"
})
@XmlRootElement(name = "GetUserForMandatorResponse")
public class GetUserForMandatorResponse {

    @XmlElement(name = "GetUserForMandatorResult")
    protected ArrayOfUser getUserForMandatorResult;

    /**
     * Gets the value of the getUserForMandatorResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUser }
     *     
     */
    public ArrayOfUser getGetUserForMandatorResult() {
        return getUserForMandatorResult;
    }

    /**
     * Sets the value of the getUserForMandatorResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUser }
     *     
     */
    public void setGetUserForMandatorResult(ArrayOfUser value) {
        this.getUserForMandatorResult = value;
    }

}
