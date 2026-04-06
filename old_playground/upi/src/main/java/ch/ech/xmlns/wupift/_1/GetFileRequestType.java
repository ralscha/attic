
package ch.ech.xmlns.wupift._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 *  
 * 				Incoming ticket.
 * 			
 * 
 * <p>Java class for getFileRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getFileRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFileRequestType", propOrder = {
    "ticket"
})
public class GetFileRequestType {

    protected int ticket;

    /**
     * Gets the value of the ticket property.
     * 
     */
    public int getTicket() {
        return ticket;
    }

    /**
     * Sets the value of the ticket property.
     * 
     */
    public void setTicket(int value) {
        this.ticket = value;
    }

}
