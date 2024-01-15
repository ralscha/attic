
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
 *         &lt;element name="GetUserForRoleResult" type="{http://www.trivadis.com/}ArrayOfUser" minOccurs="0"/>
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
    "getUserForRoleResult"
})
@XmlRootElement(name = "GetUserForRoleResponse")
public class GetUserForRoleResponse {

    @XmlElement(name = "GetUserForRoleResult")
    protected ArrayOfUser getUserForRoleResult;

    /**
     * Gets the value of the getUserForRoleResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUser }
     *     
     */
    public ArrayOfUser getGetUserForRoleResult() {
        return getUserForRoleResult;
    }

    /**
     * Sets the value of the getUserForRoleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUser }
     *     
     */
    public void setGetUserForRoleResult(ArrayOfUser value) {
        this.getUserForRoleResult = value;
    }

}
