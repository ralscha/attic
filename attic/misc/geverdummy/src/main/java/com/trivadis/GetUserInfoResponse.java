
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
 *         &lt;element name="GetUserInfoResult" type="{http://www.trivadis.com/}ArrayOfUserInfo" minOccurs="0"/>
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
    "getUserInfoResult"
})
@XmlRootElement(name = "GetUserInfoResponse")
public class GetUserInfoResponse {

    @XmlElement(name = "GetUserInfoResult")
    protected ArrayOfUserInfo getUserInfoResult;

    /**
     * Gets the value of the getUserInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUserInfo }
     *     
     */
    public ArrayOfUserInfo getGetUserInfoResult() {
        return getUserInfoResult;
    }

    /**
     * Sets the value of the getUserInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUserInfo }
     *     
     */
    public void setGetUserInfoResult(ArrayOfUserInfo value) {
        this.getUserInfoResult = value;
    }

}
