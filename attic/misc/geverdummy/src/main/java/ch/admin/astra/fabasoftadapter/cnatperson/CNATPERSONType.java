//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.30 at 12:40:05 PM CET 
//


package ch.admin.astra.fabasoftadapter.cnatperson;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectType;


/**
 * <p>Java class for CNATPERSONType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CNATPERSONType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objaddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objurl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Vorname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nachname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hauptadresse" type="{urn:schemas.fabasoft.com:CNATPERSON}HauptadresseType"/>
 *         &lt;element name="Geschaeftsnummer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mobiltelefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="E-Mail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sprachcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createdat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changedat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KreditorenNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DebitorenNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="child" type="{urn:schemas.fabasoft.com:CNATPERSON}ObjectType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="CNATPERSON")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CNATPERSONType", propOrder = {
    "objname",
    "objaddress",
    "objurl",
    "vorname",
    "nachname",
    "hauptadresse",
    "geschaeftsnummer",
    "telefax",
    "mobiltelefon",
    "eMail",
    "sprachcode",
    "createdat",
    "changedat",
    "state",
    "kreditorenNr",
    "debitorenNr",
    "child"
})
public class CNATPERSONType {

    @XmlElement(required = true)
    protected String objname;
    @XmlElement(required = true)
    protected String objaddress;
    @XmlElement(required = true)
    protected String objurl;
    @XmlElement(name = "Vorname")
    protected String vorname;
    @XmlElement(name = "Nachname")
    protected String nachname;
    @XmlElement(name = "Hauptadresse", required = true)
    protected HauptadresseType hauptadresse;
    @XmlElement(name = "Geschaeftsnummer")
    protected String geschaeftsnummer;
    @XmlElement(name = "Telefax")
    protected String telefax;
    @XmlElement(name = "Mobiltelefon")
    protected String mobiltelefon;
    @XmlElement(name = "E-Mail")
    protected String eMail;
    @XmlElement(name = "Sprachcode")
    protected String sprachcode;
    protected String createdat;
    protected String changedat;
    protected String state;
    @XmlElement(name = "KreditorenNr")
    protected String kreditorenNr;
    @XmlElement(name = "DebitorenNr")
    protected String debitorenNr;
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
     * Gets the value of the vorname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Sets the value of the vorname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVorname(String value) {
        this.vorname = value;
    }

    /**
     * Gets the value of the nachname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Sets the value of the nachname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNachname(String value) {
        this.nachname = value;
    }

    /**
     * Gets the value of the hauptadresse property.
     * 
     * @return
     *     possible object is
     *     {@link HauptadresseType }
     *     
     */
    public HauptadresseType getHauptadresse() {
        return hauptadresse;
    }

    /**
     * Sets the value of the hauptadresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link HauptadresseType }
     *     
     */
    public void setHauptadresse(HauptadresseType value) {
        this.hauptadresse = value;
    }

    /**
     * Gets the value of the geschaeftsnummer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeschaeftsnummer() {
        return geschaeftsnummer;
    }

    /**
     * Sets the value of the geschaeftsnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeschaeftsnummer(String value) {
        this.geschaeftsnummer = value;
    }

    /**
     * Gets the value of the telefax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefax() {
        return telefax;
    }

    /**
     * Sets the value of the telefax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefax(String value) {
        this.telefax = value;
    }

    /**
     * Gets the value of the mobiltelefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobiltelefon() {
        return mobiltelefon;
    }

    /**
     * Sets the value of the mobiltelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobiltelefon(String value) {
        this.mobiltelefon = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the sprachcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSprachcode() {
        return sprachcode;
    }

    /**
     * Sets the value of the sprachcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSprachcode(String value) {
        this.sprachcode = value;
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
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the kreditorenNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKreditorenNr() {
        return kreditorenNr;
    }

    /**
     * Sets the value of the kreditorenNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKreditorenNr(String value) {
        this.kreditorenNr = value;
    }

    /**
     * Gets the value of the debitorenNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebitorenNr() {
        return debitorenNr;
    }

    /**
     * Sets the value of the debitorenNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebitorenNr(String value) {
        this.debitorenNr = value;
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
