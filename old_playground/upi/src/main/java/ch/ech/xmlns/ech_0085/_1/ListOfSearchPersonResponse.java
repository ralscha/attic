
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
 *       &lt;choice>
 *         &lt;element name="allRefused" type="{http://www.ech.ch/xmlns/eCH-0085/1}allRefusedType"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element name="item">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="itemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                     &lt;element name="searchPersonResponse" type="{http://www.ech.ch/xmlns/eCH-0085/1}searchPersonResponseType"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
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
    "item"
})
@XmlRootElement(name = "listOfSearchPersonResponse")
public class ListOfSearchPersonResponse {

    protected AllRefusedType allRefused;
    protected List<ListOfSearchPersonResponse.Item> item;

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
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListOfSearchPersonResponse.Item }
     * 
     * 
     */
    public List<ListOfSearchPersonResponse.Item> getItem() {
        if (item == null) {
            item = new ArrayList<ListOfSearchPersonResponse.Item>();
        }
        return this.item;
    }


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
     *         &lt;element name="itemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="searchPersonResponse" type="{http://www.ech.ch/xmlns/eCH-0085/1}searchPersonResponseType"/>
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
        "itemId",
        "searchPersonResponse"
    })
    public static class Item {

        protected int itemId;
        @XmlElement(required = true)
        protected SearchPersonResponseType searchPersonResponse;

        /**
         * Gets the value of the itemId property.
         * 
         */
        public int getItemId() {
            return itemId;
        }

        /**
         * Sets the value of the itemId property.
         * 
         */
        public void setItemId(int value) {
            this.itemId = value;
        }

        /**
         * Gets the value of the searchPersonResponse property.
         * 
         * @return
         *     possible object is
         *     {@link SearchPersonResponseType }
         *     
         */
        public SearchPersonResponseType getSearchPersonResponse() {
            return searchPersonResponse;
        }

        /**
         * Sets the value of the searchPersonResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchPersonResponseType }
         *     
         */
        public void setSearchPersonResponse(SearchPersonResponseType value) {
            this.searchPersonResponse = value;
        }

    }

}
