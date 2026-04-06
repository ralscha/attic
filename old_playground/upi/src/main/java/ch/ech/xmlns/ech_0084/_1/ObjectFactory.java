
package ch.ech.xmlns.ech_0084._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ech.xmlns.ech_0084._1 package. 
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

    private final static QName _NewPersonRequestMothersName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "mothersName");
    private final static QName _NewPersonRequestDateOfDeath_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "dateOfDeath");
    private final static QName _NewPersonRequestFathersName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "fathersName");
    private final static QName _PersonInformationShortOptPlusTypeFirstNames_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "firstNames");
    private final static QName _PersonInformationExtOptTypeOriginalName_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "originalName");
    private final static QName _PlaceOfBirthTypeForeignCountryForeignBirthTown_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0084/1", "foreignBirthTown");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ech.xmlns.ech_0084._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link MergePersonOrUpdateLocalPersonIdRequest }
     * 
     */
    public MergePersonOrUpdateLocalPersonIdRequest createMergePersonOrUpdateLocalPersonIdRequest() {
        return new MergePersonOrUpdateLocalPersonIdRequest();
    }

    /**
     * Create an instance of {@link MergePersonsRequest }
     * 
     */
    public MergePersonsRequest createMergePersonsRequest() {
        return new MergePersonsRequest();
    }

    /**
     * Create an instance of {@link PersonInformationShortOptType }
     * 
     */
    public PersonInformationShortOptType createPersonInformationShortOptType() {
        return new PersonInformationShortOptType();
    }

    /**
     * Create an instance of {@link PersonInformationType }
     * 
     */
    public PersonInformationType createPersonInformationType() {
        return new PersonInformationType();
    }

    /**
     * Create an instance of {@link PersonInformationExtOptType }
     * 
     */
    public PersonInformationExtOptType createPersonInformationExtOptType() {
        return new PersonInformationExtOptType();
    }

    /**
     * Create an instance of {@link PlaceOfBirthType }
     * 
     */
    public PlaceOfBirthType createPlaceOfBirthType() {
        return new PlaceOfBirthType();
    }

    /**
     * Create an instance of {@link PersonInformationShortOptPlusType }
     * 
     */
    public PersonInformationShortOptPlusType createPersonInformationShortOptPlusType() {
        return new PersonInformationShortOptPlusType();
    }

    /**
     * Create an instance of {@link PersonInformationWeakType }
     * 
     */
    public PersonInformationWeakType createPersonInformationWeakType() {
        return new PersonInformationWeakType();
    }

    /**
     * Create an instance of {@link PersonInformationExtType }
     * 
     */
    public PersonInformationExtType createPersonInformationExtType() {
        return new PersonInformationExtType();
    }

    /**
     * Create an instance of {@link UpdateCurrentValuesOrAddEntryToHistoryRequest }
     * 
     */
    public UpdateCurrentValuesOrAddEntryToHistoryRequest createUpdateCurrentValuesOrAddEntryToHistoryRequest() {
        return new UpdateCurrentValuesOrAddEntryToHistoryRequest();
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link EchoLatestResponseRequest }
     * 
     */
    public EchoLatestResponseRequest createEchoLatestResponseRequest() {
        return new EchoLatestResponseRequest();
    }

    /**
     * Create an instance of {@link NewPersonRequest }
     * 
     */
    public NewPersonRequest createNewPersonRequest() {
        return new NewPersonRequest();
    }

    /**
     * Create an instance of {@link FullNameType }
     * 
     */
    public FullNameType createFullNameType() {
        return new FullNameType();
    }

    /**
     * Create an instance of {@link Response.Accepted }
     * 
     */
    public Response.Accepted createResponseAccepted() {
        return new Response.Accepted();
    }

    /**
     * Create an instance of {@link Response.Refused }
     * 
     */
    public Response.Refused createResponseRefused() {
        return new Response.Refused();
    }

    /**
     * Create an instance of {@link UpdateLocalPersonIdRequest }
     * 
     */
    public UpdateLocalPersonIdRequest createUpdateLocalPersonIdRequest() {
        return new UpdateLocalPersonIdRequest();
    }

    /**
     * Create an instance of {@link AddEntryToHistoryRequest }
     * 
     */
    public AddEntryToHistoryRequest createAddEntryToHistoryRequest() {
        return new AddEntryToHistoryRequest();
    }

    /**
     * Create an instance of {@link ErasePersonRequest }
     * 
     */
    public ErasePersonRequest createErasePersonRequest() {
        return new ErasePersonRequest();
    }

    /**
     * Create an instance of {@link UpdateCurrentValuesRequest }
     * 
     */
    public UpdateCurrentValuesRequest createUpdateCurrentValuesRequest() {
        return new UpdateCurrentValuesRequest();
    }

    /**
     * Create an instance of {@link MergePersonOrUpdateLocalPersonIdRequest.Person }
     * 
     */
    public MergePersonOrUpdateLocalPersonIdRequest.Person createMergePersonOrUpdateLocalPersonIdRequestPerson() {
        return new MergePersonOrUpdateLocalPersonIdRequest.Person();
    }

    /**
     * Create an instance of {@link EraseLocalPersonIdRequest }
     * 
     */
    public EraseLocalPersonIdRequest createEraseLocalPersonIdRequest() {
        return new EraseLocalPersonIdRequest();
    }

    /**
     * Create an instance of {@link MergePersonsRequest.Person }
     * 
     */
    public MergePersonsRequest.Person createMergePersonsRequestPerson() {
        return new MergePersonsRequest.Person();
    }

    /**
     * Create an instance of {@link ValuesStoredUnderAhvvnType }
     * 
     */
    public ValuesStoredUnderAhvvnType createValuesStoredUnderAhvvnType() {
        return new ValuesStoredUnderAhvvnType();
    }

    /**
     * Create an instance of {@link PersonInformationShortOptType.Nationality }
     * 
     */
    public PersonInformationShortOptType.Nationality createPersonInformationShortOptTypeNationality() {
        return new PersonInformationShortOptType.Nationality();
    }

    /**
     * Create an instance of {@link PersonInformationType.Nationality }
     * 
     */
    public PersonInformationType.Nationality createPersonInformationTypeNationality() {
        return new PersonInformationType.Nationality();
    }

    /**
     * Create an instance of {@link PersonInformationExtOptType.Nationality }
     * 
     */
    public PersonInformationExtOptType.Nationality createPersonInformationExtOptTypeNationality() {
        return new PersonInformationExtOptType.Nationality();
    }

    /**
     * Create an instance of {@link PlaceOfBirthType.SwissTown }
     * 
     */
    public PlaceOfBirthType.SwissTown createPlaceOfBirthTypeSwissTown() {
        return new PlaceOfBirthType.SwissTown();
    }

    /**
     * Create an instance of {@link PlaceOfBirthType.ForeignCountry }
     * 
     */
    public PlaceOfBirthType.ForeignCountry createPlaceOfBirthTypeForeignCountry() {
        return new PlaceOfBirthType.ForeignCountry();
    }

    /**
     * Create an instance of {@link PersonInformationShortOptPlusType.Nationality }
     * 
     */
    public PersonInformationShortOptPlusType.Nationality createPersonInformationShortOptPlusTypeNationality() {
        return new PersonInformationShortOptPlusType.Nationality();
    }

    /**
     * Create an instance of {@link PersonInformationWeakType.Nationality }
     * 
     */
    public PersonInformationWeakType.Nationality createPersonInformationWeakTypeNationality() {
        return new PersonInformationWeakType.Nationality();
    }

    /**
     * Create an instance of {@link PersonInformationExtType.Nationality }
     * 
     */
    public PersonInformationExtType.Nationality createPersonInformationExtTypeNationality() {
        return new PersonInformationExtType.Nationality();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "mothersName", scope = NewPersonRequest.class)
    public JAXBElement<FullNameType> createNewPersonRequestMothersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestMothersName_QNAME, FullNameType.class, NewPersonRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "dateOfDeath", scope = NewPersonRequest.class)
    public JAXBElement<XMLGregorianCalendar> createNewPersonRequestDateOfDeath(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NewPersonRequestDateOfDeath_QNAME, XMLGregorianCalendar.class, NewPersonRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "fathersName", scope = NewPersonRequest.class)
    public JAXBElement<FullNameType> createNewPersonRequestFathersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestFathersName_QNAME, FullNameType.class, NewPersonRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "firstNames", scope = PersonInformationShortOptPlusType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationShortOptPlusTypeFirstNames(String value) {
        return new JAXBElement<String>(_PersonInformationShortOptPlusTypeFirstNames_QNAME, String.class, PersonInformationShortOptPlusType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "firstNames", scope = PersonInformationExtOptType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationExtOptTypeFirstNames(String value) {
        return new JAXBElement<String>(_PersonInformationShortOptPlusTypeFirstNames_QNAME, String.class, PersonInformationExtOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "originalName", scope = PersonInformationExtOptType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationExtOptTypeOriginalName(String value) {
        return new JAXBElement<String>(_PersonInformationExtOptTypeOriginalName_QNAME, String.class, PersonInformationExtOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "mothersName", scope = PersonInformationExtOptType.class)
    public JAXBElement<FullNameType> createPersonInformationExtOptTypeMothersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestMothersName_QNAME, FullNameType.class, PersonInformationExtOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "dateOfDeath", scope = PersonInformationExtOptType.class)
    public JAXBElement<XMLGregorianCalendar> createPersonInformationExtOptTypeDateOfDeath(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NewPersonRequestDateOfDeath_QNAME, XMLGregorianCalendar.class, PersonInformationExtOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "fathersName", scope = PersonInformationExtOptType.class)
    public JAXBElement<FullNameType> createPersonInformationExtOptTypeFathersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestFathersName_QNAME, FullNameType.class, PersonInformationExtOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "originalName", scope = PersonInformationWeakType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationWeakTypeOriginalName(String value) {
        return new JAXBElement<String>(_PersonInformationExtOptTypeOriginalName_QNAME, String.class, PersonInformationWeakType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "originalName", scope = PersonInformationExtType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationExtTypeOriginalName(String value) {
        return new JAXBElement<String>(_PersonInformationExtOptTypeOriginalName_QNAME, String.class, PersonInformationExtType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "mothersName", scope = PersonInformationExtType.class)
    public JAXBElement<FullNameType> createPersonInformationExtTypeMothersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestMothersName_QNAME, FullNameType.class, PersonInformationExtType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "dateOfDeath", scope = PersonInformationExtType.class)
    public JAXBElement<XMLGregorianCalendar> createPersonInformationExtTypeDateOfDeath(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NewPersonRequestDateOfDeath_QNAME, XMLGregorianCalendar.class, PersonInformationExtType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FullNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "fathersName", scope = PersonInformationExtType.class)
    public JAXBElement<FullNameType> createPersonInformationExtTypeFathersName(FullNameType value) {
        return new JAXBElement<FullNameType>(_NewPersonRequestFathersName_QNAME, FullNameType.class, PersonInformationExtType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "originalName", scope = PersonInformationType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationTypeOriginalName(String value) {
        return new JAXBElement<String>(_PersonInformationExtOptTypeOriginalName_QNAME, String.class, PersonInformationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "firstNames", scope = PersonInformationShortOptType.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPersonInformationShortOptTypeFirstNames(String value) {
        return new JAXBElement<String>(_PersonInformationShortOptPlusTypeFirstNames_QNAME, String.class, PersonInformationShortOptType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0084/1", name = "foreignBirthTown", scope = PlaceOfBirthType.ForeignCountry.class)
    public JAXBElement<String> createPlaceOfBirthTypeForeignCountryForeignBirthTown(String value) {
        return new JAXBElement<String>(_PlaceOfBirthTypeForeignCountryForeignBirthTown_QNAME, String.class, PlaceOfBirthType.ForeignCountry.class, value);
    }

}
