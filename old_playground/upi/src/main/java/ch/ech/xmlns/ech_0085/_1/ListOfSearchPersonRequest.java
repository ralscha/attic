
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
 *         &lt;element name="item">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="itemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="searchPersonRequest" type="{http://www.ech.ch/xmlns/eCH-0085/1}searchPersonRequestType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "item"
})
@XmlRootElement(name = "listOfSearchPersonRequest")
public class ListOfSearchPersonRequest {

    @XmlElement(required = true)
    protected List<ListOfSearchPersonRequest.Item> item;

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
     * {@link ListOfSearchPersonRequest.Item }
     * 
     * 
     */
    public List<ListOfSearchPersonRequest.Item> getItem() {
        if (item == null) {
            item = new ArrayList<ListOfSearchPersonRequest.Item>();
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
     *         &lt;element name="searchPersonRequest" type="{http://www.ech.ch/xmlns/eCH-0085/1}searchPersonRequestType"/>
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
        "searchPersonRequest"
    })
    public static class Item {

        protected int itemId;
        @XmlElement(required = true)
        protected SearchPersonRequestType searchPersonRequest;

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
         * Gets the value of the searchPersonRequest property.
         * 
         * @return
         *     possible object is
         *     {@link SearchPersonRequestType }
         *     
         */
        public SearchPersonRequestType getSearchPersonRequest() {
            return searchPersonRequest;
        }

        /**
         * Sets the value of the searchPersonRequest property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchPersonRequestType }
         *     
         */
        public void setSearchPersonRequest(SearchPersonRequestType value) {
            this.searchPersonRequest = value;
        }

    }

}
