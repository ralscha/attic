
package ch.ech.xmlns.ech_0085._1;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;choice>
 *         &lt;element name="allRefused" type="{http://www.ech.ch/xmlns/eCH-0085/1}allRefusedType"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element name="getAhvvnResponse" type="{http://www.ech.ch/xmlns/eCH-0085/1}getAhvvnResponseType"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "allRefused",
    "getAhvvnResponse"
})
@XmlRootElement(name = "listOfGetAhvvnResponse")
public class ListOfGetAhvvnResponse {

    protected AllRefusedType allRefused;
    protected List<GetAhvvnResponseType> getAhvvnResponse;

    /**
     * Gets the value of the allRefused property.
     * 
     * @return
     *     possible object is
     *     {@link AllRefusedType }
     *     
     */
    public AllRefusedType getAllRefused() {
        return allRefused;
    }

    /**
     * Sets the value of the allRefused property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllRefusedType }
     *     
     */
    public void setAllRefused(AllRefusedType value) {
        this.allRefused = value;
    }

    /**
     * Gets the value of the getAhvvnResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getAhvvnResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetAhvvnResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetAhvvnResponseType }
     * 
     * 
     */
    public List<GetAhvvnResponseType> getGetAhvvnResponse() {
        if (getAhvvnResponse == null) {
            getAhvvnResponse = new ArrayList<GetAhvvnResponseType>();
        }
        return this.getAhvvnResponse;
    }

}
