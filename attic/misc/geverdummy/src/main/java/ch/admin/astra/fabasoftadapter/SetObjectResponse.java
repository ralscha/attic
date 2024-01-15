
package ch.admin.astra.fabasoftadapter;

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
 *         &lt;element name="SetObjectResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "setObjectResult"
})
@XmlRootElement(name = "SetObjectResponse")
public class SetObjectResponse {

    @XmlElement(name = "SetObjectResult")
    protected boolean setObjectResult;

    /**
     * Gets the value of the setObjectResult property.
     * 
     */
    public boolean isSetObjectResult() {
        return setObjectResult;
    }

    /**
     * Sets the value of the setObjectResult property.
     * 
     */
    public void setSetObjectResult(boolean value) {
        this.setObjectResult = value;
    }

}
