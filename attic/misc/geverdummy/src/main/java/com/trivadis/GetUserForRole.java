
package com.trivadis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="applicationname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rolename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "applicationname",
    "rolename"
})
@XmlRootElement(name = "GetUserForRole")
public class GetUserForRole {

    protected String applicationname;
    protected String rolename;

    /**
     * Gets the value of the applicationname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationname() {
        return applicationname;
    }

    /**
     * Sets the value of the applicationname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationname(String value) {
        this.applicationname = value;
    }

    /**
     * Gets the value of the rolename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * Sets the value of the rolename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolename(String value) {
        this.rolename = value;
    }

}
