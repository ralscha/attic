//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.30 at 12:40:13 PM CET 
//


package ch.admin.astra.fabasoftadapter.csubfile;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the admin.astra.fabasoftadapter.csubfile package. 
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

    private final static QName _CSUBFILE_QNAME = new QName("urn:schemas.fabasoft.com:CSUBFILE", "CSUBFILE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: admin.astra.fabasoftadapter.csubfile
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CSUBFILEType }
     * 
     */
    public CSUBFILEType createCSUBFILEType() {
        return new CSUBFILEType();
    }

    /**
     * Create an instance of {@link ObjectType }
     * 
     */
    public ObjectType createObjectType() {
        return new ObjectType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CSUBFILEType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:schemas.fabasoft.com:CSUBFILE", name = "CSUBFILE")
    public JAXBElement<CSUBFILEType> createCSUBFILE(CSUBFILEType value) {
        return new JAXBElement<CSUBFILEType>(_CSUBFILE_QNAME, CSUBFILEType.class, null, value);
    }

}
