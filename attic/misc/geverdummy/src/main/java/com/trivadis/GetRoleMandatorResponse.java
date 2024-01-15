
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
 *         &lt;element name="GetRoleMandatorResult" type="{http://www.trivadis.com/}ArrayOfRole" minOccurs="0"/>
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
    "getRoleMandatorResult"
})
@XmlRootElement(name = "GetRoleMandatorResponse")
public class GetRoleMandatorResponse {

    @XmlElement(name = "GetRoleMandatorResult")
    protected ArrayOfRole getRoleMandatorResult;

    /**
     * Gets the value of the getRoleMandatorResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRole }
     *     
     */
    public ArrayOfRole getGetRoleMandatorResult() {
        return getRoleMandatorResult;
    }

    /**
     * Sets the value of the getRoleMandatorResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRole }
     *     
     */
    public void setGetRoleMandatorResult(ArrayOfRole value) {
        this.getRoleMandatorResult = value;
    }

}
