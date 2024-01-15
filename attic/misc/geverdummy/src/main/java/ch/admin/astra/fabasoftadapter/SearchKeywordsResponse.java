
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
 *         &lt;element name="SearchKeywordsResult" type="{http://www.astra.admin.ch/FabasoftAdapter}ArrayOfString" minOccurs="0"/>
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
    "searchKeywordsResult"
})
@XmlRootElement(name = "SearchKeywordsResponse")
public class SearchKeywordsResponse {

    @XmlElement(name = "SearchKeywordsResult")
    protected ArrayOfString searchKeywordsResult;

    /**
     * Gets the value of the searchKeywordsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSearchKeywordsResult() {
        return searchKeywordsResult;
    }

    /**
     * Sets the value of the searchKeywordsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSearchKeywordsResult(ArrayOfString value) {
        this.searchKeywordsResult = value;
    }

}
