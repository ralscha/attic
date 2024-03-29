//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.30 at 12:40:13 PM CET 
//


package ch.admin.astra.fabasoftadapter.csubfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectType;


/**
 * <p>Java class for CSUBFILEType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CSUBFILEType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objaddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objurl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parent" type="{urn:schemas.fabasoft.com:CSUBFILE}ObjectType" minOccurs="0"/>
 *         &lt;element name="child" type="{urn:schemas.fabasoft.com:CSUBFILE}ObjectType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="CSUBFILE")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSUBFILEType", propOrder = {
    "objname",
    "objaddress",
    "objurl",
    "parent",
    "child"
})
public class CSUBFILEType {

    @XmlElement(required = true)
    protected String objname;
    @XmlElement(required = true)
    protected String objaddress;
    @XmlElement(required = true)
    protected String objurl;
    protected ObjectType parent;
    protected List<ObjectType> child;

    /**
     * Gets the value of the objname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjname() {
        return objname;
    }

    /**
     * Sets the value of the objname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjname(String value) {
        this.objname = value;
    }

    /**
     * Gets the value of the objaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjaddress() {
        return objaddress;
    }

    /**
     * Sets the value of the objaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjaddress(String value) {
        this.objaddress = value;
    }

    /**
     * Gets the value of the objurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjurl() {
        return objurl;
    }

    /**
     * Sets the value of the objurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjurl(String value) {
        this.objurl = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setParent(ObjectType value) {
        this.parent = value;
    }

    /**
     * Gets the value of the child property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the child property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChild().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectType }
     * 
     * 
     */
    public List<ObjectType> getChild() {
        if (child == null) {
            child = new ArrayList<ObjectType>();
        }
        return this.child;
    }

}
