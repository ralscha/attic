
package ch.ech.xmlns.ech_0085._1;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="getAhvvnRequest" type="{http://www.ech.ch/xmlns/eCH-0085/1}getAhvvnRequestType"/>
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
    "getAhvvnRequest"
})
@XmlRootElement(name = "listOfGetAhvvnRequest")
public class ListOfGetAhvvnRequest {

    @XmlElement(required = true)
    protected List<GetAhvvnRequestType> getAhvvnRequest;

    /**
     * Gets the value of the getAhvvnRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getAhvvnRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetAhvvnRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetAhvvnRequestType }
     * 
     * 
     */
    public List<GetAhvvnRequestType> getGetAhvvnRequest() {
        if (getAhvvnRequest == null) {
            getAhvvnRequest = new ArrayList<GetAhvvnRequestType>();
        }
        return this.getAhvvnRequest;
    }

}
