
package ch.ech.xmlns.wupift._1;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  
 * 				File to post to UPI File Transfer web service. This corresponds to an UPI request compressed in ZIP format. Each ZIP file must contain only one UPI request.
 * 			
 * 
 * <p>Java class for postFileRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="postFileRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upiZIPRequest" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "postFileRequestType", propOrder = {
    "upiZIPRequest"
})
public class PostFileRequestType {

    @XmlElement(required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler upiZIPRequest;

    /**
     * Gets the value of the upiZIPRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getUpiZIPRequest() {
        return upiZIPRequest;
    }

    /**
     * Sets the value of the upiZIPRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpiZIPRequest(DataHandler value) {
        this.upiZIPRequest = value;
    }

}
