
package ch.ech.xmlns.ech_0085._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ech.xmlns.ech_0085._1 package. 
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

    private final static QName _GetAhvvnResponse_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "getAhvvnResponse");
    private final static QName _GetInfoPersonRequest_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "getInfoPersonRequest");
    private final static QName _GetInfoPersonResponse_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "getInfoPersonResponse");
    private final static QName _SearchPersonResponse_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "searchPersonResponse");
    private final static QName _SearchPersonRequest_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "searchPersonRequest");
    private final static QName _GetAhvvnRequest_QNAME = new QName("http://www.ech.ch/xmlns/eCH-0085/1", "getAhvvnRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ech.xmlns.ech_0085._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListOfSearchPersonResponse }
     * 
     */
    public ListOfSearchPersonResponse createListOfSearchPersonResponse() {
        return new ListOfSearchPersonResponse();
    }

    /**
     * Create an instance of {@link ListOfSearchPersonRequest }
     * 
     */
    public ListOfSearchPersonRequest createListOfSearchPersonRequest() {
        return new ListOfSearchPersonRequest();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnRequest }
     * 
     */
    public GetCancelledAndInactiveAhvvnRequest createGetCancelledAndInactiveAhvvnRequest() {
        return new GetCancelledAndInactiveAhvvnRequest();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse createGetCancelledAndInactiveAhvvnResponse() {
        return new GetCancelledAndInactiveAhvvnResponse();
    }

    /**
     * Create an instance of {@link GetSourceResponse }
     * 
     */
    public GetSourceResponse createGetSourceResponse() {
        return new GetSourceResponse();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList createGetCancelledAndInactiveAhvvnResponseInactiveAhvvnList() {
        return new GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList createGetCancelledAndInactiveAhvvnResponseCancelledAhvvnList() {
        return new GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList();
    }

    /**
     * Create an instance of {@link GetAhvvnResponseType }
     * 
     */
    public GetAhvvnResponseType createGetAhvvnResponseType() {
        return new GetAhvvnResponseType();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType }
     * 
     */
    public SearchPersonResponseType createSearchPersonResponseType() {
        return new SearchPersonResponseType();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType.Accepted }
     * 
     */
    public SearchPersonResponseType.Accepted createSearchPersonResponseTypeAccepted() {
        return new SearchPersonResponseType.Accepted();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType.Accepted.MaybeFound }
     * 
     */
    public SearchPersonResponseType.Accepted.MaybeFound createSearchPersonResponseTypeAcceptedMaybeFound() {
        return new SearchPersonResponseType.Accepted.MaybeFound();
    }

    /**
     * Create an instance of {@link SearchPersonRequestType }
     * 
     */
    public SearchPersonRequestType createSearchPersonRequestType() {
        return new SearchPersonRequestType();
    }

    /**
     * Create an instance of {@link GetInfoPersonResponseType }
     * 
     */
    public GetInfoPersonResponseType createGetInfoPersonResponseType() {
        return new GetInfoPersonResponseType();
    }

    /**
     * Create an instance of {@link AllRefusedType }
     * 
     */
    public AllRefusedType createAllRefusedType() {
        return new AllRefusedType();
    }

    /**
     * Create an instance of {@link ListOfSearchPersonResponse.Item }
     * 
     */
    public ListOfSearchPersonResponse.Item createListOfSearchPersonResponseItem() {
        return new ListOfSearchPersonResponse.Item();
    }

    /**
     * Create an instance of {@link GetInfoPersonRequestType }
     * 
     */
    public GetInfoPersonRequestType createGetInfoPersonRequestType() {
        return new GetInfoPersonRequestType();
    }

    /**
     * Create an instance of {@link ListOfSearchPersonRequest.Item }
     * 
     */
    public ListOfSearchPersonRequest.Item createListOfSearchPersonRequestItem() {
        return new ListOfSearchPersonRequest.Item();
    }

    /**
     * Create an instance of {@link GetAhvvnRequestType }
     * 
     */
    public GetAhvvnRequestType createGetAhvvnRequestType() {
        return new GetAhvvnRequestType();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnRequest.TimeInterval }
     * 
     */
    public GetCancelledAndInactiveAhvvnRequest.TimeInterval createGetCancelledAndInactiveAhvvnRequestTimeInterval() {
        return new GetCancelledAndInactiveAhvvnRequest.TimeInterval();
    }

    /**
     * Create an instance of {@link ListOfGetAhvvnRequest }
     * 
     */
    public ListOfGetAhvvnRequest createListOfGetAhvvnRequest() {
        return new ListOfGetAhvvnRequest();
    }

    /**
     * Create an instance of {@link GetSourceRequest }
     * 
     */
    public GetSourceRequest createGetSourceRequest() {
        return new GetSourceRequest();
    }

    /**
     * Create an instance of {@link ListOfGetAhvvnResponse }
     * 
     */
    public ListOfGetAhvvnResponse createListOfGetAhvvnResponse() {
        return new ListOfGetAhvvnResponse();
    }

    /**
     * Create an instance of {@link ListOfGetInfoPersonRequest }
     * 
     */
    public ListOfGetInfoPersonRequest createListOfGetInfoPersonRequest() {
        return new ListOfGetInfoPersonRequest();
    }

    /**
     * Create an instance of {@link ListOfGetInfoPersonResponse }
     * 
     */
    public ListOfGetInfoPersonResponse createListOfGetInfoPersonResponse() {
        return new ListOfGetInfoPersonResponse();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.TimeInterval }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.TimeInterval createGetCancelledAndInactiveAhvvnResponseTimeInterval() {
        return new GetCancelledAndInactiveAhvvnResponse.TimeInterval();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.Refused }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.Refused createGetCancelledAndInactiveAhvvnResponseRefused() {
        return new GetCancelledAndInactiveAhvvnResponse.Refused();
    }

    /**
     * Create an instance of {@link GetSourceResponse.Accepted }
     * 
     */
    public GetSourceResponse.Accepted createGetSourceResponseAccepted() {
        return new GetSourceResponse.Accepted();
    }

    /**
     * Create an instance of {@link GetSourceResponse.Refused }
     * 
     */
    public GetSourceResponse.Refused createGetSourceResponseRefused() {
        return new GetSourceResponse.Refused();
    }

    /**
     * Create an instance of {@link PersonIdentificationType }
     * 
     */
    public PersonIdentificationType createPersonIdentificationType() {
        return new PersonIdentificationType();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn createGetCancelledAndInactiveAhvvnResponseInactiveAhvvnListInactiveAhvvn() {
        return new GetCancelledAndInactiveAhvvnResponse.InactiveAhvvnList.InactiveAhvvn();
    }

    /**
     * Create an instance of {@link GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn }
     * 
     */
    public GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn createGetCancelledAndInactiveAhvvnResponseCancelledAhvvnListCancelledAhvvn() {
        return new GetCancelledAndInactiveAhvvnResponse.CancelledAhvvnList.CancelledAhvvn();
    }

    /**
     * Create an instance of {@link GetAhvvnResponseType.Accepted }
     * 
     */
    public GetAhvvnResponseType.Accepted createGetAhvvnResponseTypeAccepted() {
        return new GetAhvvnResponseType.Accepted();
    }

    /**
     * Create an instance of {@link GetAhvvnResponseType.Refused }
     * 
     */
    public GetAhvvnResponseType.Refused createGetAhvvnResponseTypeRefused() {
        return new GetAhvvnResponseType.Refused();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType.Refused }
     * 
     */
    public SearchPersonResponseType.Refused createSearchPersonResponseTypeRefused() {
        return new SearchPersonResponseType.Refused();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType.Accepted.Found }
     * 
     */
    public SearchPersonResponseType.Accepted.Found createSearchPersonResponseTypeAcceptedFound() {
        return new SearchPersonResponseType.Accepted.Found();
    }

    /**
     * Create an instance of {@link SearchPersonResponseType.Accepted.MaybeFound.Candidate }
     * 
     */
    public SearchPersonResponseType.Accepted.MaybeFound.Candidate createSearchPersonResponseTypeAcceptedMaybeFoundCandidate() {
        return new SearchPersonResponseType.Accepted.MaybeFound.Candidate();
    }

    /**
     * Create an instance of {@link SearchPersonRequestType.Nationality }
     * 
     */
    public SearchPersonRequestType.Nationality createSearchPersonRequestTypeNationality() {
        return new SearchPersonRequestType.Nationality();
    }

    /**
     * Create an instance of {@link GetInfoPersonResponseType.Accepted }
     * 
     */
    public GetInfoPersonResponseType.Accepted createGetInfoPersonResponseTypeAccepted() {
        return new GetInfoPersonResponseType.Accepted();
    }

    /**
     * Create an instance of {@link GetInfoPersonResponseType.Refused }
     * 
     */
    public GetInfoPersonResponseType.Refused createGetInfoPersonResponseTypeRefused() {
        return new GetInfoPersonResponseType.Refused();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAhvvnResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "getAhvvnResponse")
    public JAXBElement<GetAhvvnResponseType> createGetAhvvnResponse(GetAhvvnResponseType value) {
        return new JAXBElement<GetAhvvnResponseType>(_GetAhvvnResponse_QNAME, GetAhvvnResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoPersonRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "getInfoPersonRequest")
    public JAXBElement<GetInfoPersonRequestType> createGetInfoPersonRequest(GetInfoPersonRequestType value) {
        return new JAXBElement<GetInfoPersonRequestType>(_GetInfoPersonRequest_QNAME, GetInfoPersonRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoPersonResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "getInfoPersonResponse")
    public JAXBElement<GetInfoPersonResponseType> createGetInfoPersonResponse(GetInfoPersonResponseType value) {
        return new JAXBElement<GetInfoPersonResponseType>(_GetInfoPersonResponse_QNAME, GetInfoPersonResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersonResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "searchPersonResponse")
    public JAXBElement<SearchPersonResponseType> createSearchPersonResponse(SearchPersonResponseType value) {
        return new JAXBElement<SearchPersonResponseType>(_SearchPersonResponse_QNAME, SearchPersonResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersonRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "searchPersonRequest")
    public JAXBElement<SearchPersonRequestType> createSearchPersonRequest(SearchPersonRequestType value) {
        return new JAXBElement<SearchPersonRequestType>(_SearchPersonRequest_QNAME, SearchPersonRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAhvvnRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/eCH-0085/1", name = "getAhvvnRequest")
    public JAXBElement<GetAhvvnRequestType> createGetAhvvnRequest(GetAhvvnRequestType value) {
        return new JAXBElement<GetAhvvnRequestType>(_GetAhvvnRequest_QNAME, GetAhvvnRequestType.class, null, value);
    }

}
