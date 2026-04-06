
package ch.ech.xmlns.ech_0086._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import ch.ech.xmlns.ech_0084._1.FullNameType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ech.xmlns.ech_0086._1 package. 
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

    private final static QName _CompareDataRequestDataToCompareOriginalName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0086/1", "originalName");
    private final static QName _CompareDataRequestDataToCompareFathersName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0086/1", "fathersName");
    private final static QName _CompareDataRequestDataToCompareDateOfDeath_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0086/1", "dateOfDeath");
    private final static QName _CompareDataRequestDataToCompareMothersName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0086/1", "mothersName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ech.xmlns.ech_0086._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CompareDataResponse }
     * 
     */
    public CompareDataResponse createCompareDataResponse() {
        return new CompareDataResponse();
    }

    /**
     * Create an instance of {@link CompareDataRequest }
     * 
     */
    public CompareDataRequest createCompareDataRequest() {
        return new CompareDataRequest();
    }

    /**
     * Create an instance of {@link CompareDataRequest.DataToCompare }
     * 
     */
    public CompareDataRequest.DataToCompare createCompareDataRequestDataToCompare() {
        return new CompareDataRequest.DataToCompare();
    }

    /**
     * Create an instance of {@link CompareDataResponse.ComparedData }
     * 
     */
    public CompareDataResponse.ComparedData createCompareDataResponseComparedData() {
        return new CompareDataResponse.ComparedData();
    }

    /**
     * Create an instance of {@link RefusedType }
     * 
     */
    public RefusedType createRefusedType() {
        return new RefusedType();
    }

    /**
     * Create an instance of {@link CompareDataRequest.DataToCompare.Nationality }
     * 
     */
    public CompareDataRequest.DataToCompare.Nationality createCompareDataRequestDataToCompareNationality() {
        return new CompareDataRequest.DataToCompare.Nationality();
    }

    /**
     * Create an instance of {@link CompareDataResponse.ComparedData.DifferentData }
     * 
     */
    public CompareDataResponse.ComparedData.DifferentData createCompareDataResponseComparedDataDifferentData() {
        return new CompareDataResponse.ComparedData.DifferentData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0086/1", name = "originalName", scope = CompareDataRequest.DataToCompare.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createCompareDataRequestDataToCompareOriginalName(String value) {
        return new JAXBElement<String>(_CompareDataRequestDataToCompareOriginalName_QNAME, String.class, CompareDataRequest.DataToCompare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0086/1", name = "fathersName", scope = CompareDataRequest.DataToCompare.class)
    public JAXBElement<FullNameType> createCompareDataRequestDataToCompareFathersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_CompareDataRequestDataToCompareFathersName_QNAME, FullNameType.class, CompareDataRequest.DataToCompare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0086/1", name = "dateOfDeath", scope = CompareDataRequest.DataToCompare.class)
    public JAXBElement<XMLGregorianCalendar> createCompareDataRequestDataToCompareDateOfDeath(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CompareDataRequestDataToCompareDateOfDeath_QNAME, XMLGregorianCalendar.class, CompareDataRequest.DataToCompare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0086/1", name = "mothersName", scope = CompareDataRequest.DataToCompare.class)
    public JAXBElement<FullNameType> createCompareDataRequestDataToCompareMothersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_CompareDataRequestDataToCompareMothersName_QNAME, FullNameType.class, CompareDataRequest.DataToCompare.class, value);
    }

}
