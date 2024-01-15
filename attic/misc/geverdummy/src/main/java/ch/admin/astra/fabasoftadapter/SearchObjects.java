
package ch.admin.astra.fabasoftadapter;

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
 *         &lt;element name="objclass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appkeys" type="{http://www.astra.admin.ch/FabasoftAdapter}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="createdat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changedat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="owner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="root" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "objclass",
    "name",
    "appkeys",
    "createdat",
    "changedat",
    "owner",
    "root"
})
@XmlRootElement(name = "SearchObjects")
public class SearchObjects {

    protected String objclass;
    protected String name;
    protected ArrayOfString appkeys;
    protected String createdat;
    protected String changedat;
    protected String owner;
    protected String root;

    /**
     * Gets the value of the objclass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjclass() {
        return objclass;
    }

    /**
     * Sets the value of the objclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjclass(String value) {
        this.objclass = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the appkeys property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getAppkeys() {
        return appkeys;
    }

    /**
     * Sets the value of the appkeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAppkeys(ArrayOfString value) {
        this.appkeys = value;
    }

    /**
     * Gets the value of the createdat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedat() {
        return createdat;
    }

    /**
     * Sets the value of the createdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedat(String value) {
        this.createdat = value;
    }

    /**
     * Gets the value of the changedat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangedat() {
        return changedat;
    }

    /**
     * Sets the value of the changedat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangedat(String value) {
        this.changedat = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the root property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoot() {
        return root;
    }

    /**
     * Sets the value of the root property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoot(String value) {
        this.root = value;
    }

}
