//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.30 at 12:39:48 PM CET 
//


package ch.admin.astra.fabasoftadapter.cjurperson;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the admin.astra.fabasoftadapter.cjurperson package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CJURPERSON_QNAME = new QName("urn:schemas.fabasoft.com:CJURPERSON", "CJURPERSON");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: admin.astra.fabasoftadapter.cjurperson
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CJURPERSONType }
     * 
     */
    public CJURPERSONType createCJURPERSONType() {
        return new CJURPERSONType();
    }

    /**
     * Create an instance of {@link ObjectType }
     * 
     */
    public ObjectType createObjectType() {
        return new ObjectType();
    }

    /**
     * Create an instance of {@link HauptadresseType }
     * 
     */
    public HauptadresseType createHauptadresseType() {
        return new HauptadresseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CJURPERSONType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:schemas.fabasoft.com:CJURPERSON", name = "CJURPERSON")
    public JAXBElement<CJURPERSONType> createCJURPERSON(CJURPERSONType value) {
        return new JAXBElement<CJURPERSONType>(_CJURPERSON_QNAME, CJURPERSONType.class, null, value);
    }

}
