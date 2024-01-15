
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
 *         &lt;element name="GetRoleResult" type="{http://www.trivadis.com/}ArrayOfRole" minOccurs="0"/>
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
    "getRoleResult"
})
@XmlRootElement(name = "GetRoleResponse")
public class GetRoleResponse {

    @XmlElement(name = "GetRoleResult")
    protected ArrayOfRole getRoleResult;

    /**
     * Gets the value of the getRoleResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRole }
     *     
     */
    public ArrayOfRole getGetRoleResult() {
        return getRoleResult;
    }

    /**
     * Sets the value of the getRoleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRole }
     *     
     */
    public void setGetRoleResult(ArrayOfRole value) {
        this.getRoleResult = value;
    }

}
