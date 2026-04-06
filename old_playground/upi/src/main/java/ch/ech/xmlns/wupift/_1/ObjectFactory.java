
package ch.ech.xmlns.wupift._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ech.xmlns.wupift._1 package. 
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

    private final static QName _GetFileResponse_QNAME = new QName("http://www.ech.ch/xmlns/wupift/1", "getFileResponse");
    private final static QName _PostFileRequest_QNAME = new QName("http://www.ech.ch/xmlns/wupift/1", "postFileRequest");
    private final static QName _GetFileRequest_QNAME = new QName("http://www.ech.ch/xmlns/wupift/1", "getFileRequest");
    private final static QName _PostFileResponse_QNAME = new QName("http://www.ech.ch/xmlns/wupift/1", "postFileResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ech.xmlns.wupift._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PostFileResponseType }
     * 
     */
    public PostFileResponseType createPostFileResponseType() {
        return new PostFileResponseType();
    }

    /**
     * Create an instance of {@link GetFileResponseType }
     * 
     */
    public GetFileResponseType createGetFileResponseType() {
        return new GetFileResponseType();
    }

    /**
     * Create an instance of {@link PostFileRequestType }
     * 
     */
    public PostFileRequestType createPostFileRequestType() {
        return new PostFileRequestType();
    }

    /**
     * Create an instance of {@link GetFileRequestType }
     * 
     */
    public GetFileRequestType createGetFileRequestType() {
        return new GetFileRequestType();
    }

    /**
     * Create an instance of {@link PostFileResponseType.Accepted }
     * 
     */
    public PostFileResponseType.Accepted createPostFileResponseTypeAccepted() {
        return new PostFileResponseType.Accepted();
    }

    /**
     * Create an instance of {@link PostFileResponseType.Refused }
     * 
     */
    public PostFileResponseType.Refused createPostFileResponseTypeRefused() {
        return new PostFileResponseType.Refused();
    }

    /**
     * Create an instance of {@link GetFileResponseType.Accepted }
     * 
     */
    public GetFileResponseType.Accepted createGetFileResponseTypeAccepted() {
        return new GetFileResponseType.Accepted();
    }

    /**
     * Create an instance of {@link GetFileResponseType.Refused }
     * 
     */
    public GetFileResponseType.Refused createGetFileResponseTypeRefused() {
        return new GetFileResponseType.Refused();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/wupift/1", name = "getFileResponse")
    public JAXBElement<GetFileResponseType> createGetFileResponse(GetFileResponseType value) {
        return new JAXBElement<GetFileResponseType>(_GetFileResponse_QNAME, GetFileResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostFileRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/wupift/1", name = "postFileRequest")
    public JAXBElement<PostFileRequestType> createPostFileRequest(PostFileRequestType value) {
        return new JAXBElement<PostFileRequestType>(_PostFileRequest_QNAME, PostFileRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/wupift/1", name = "getFileRequest")
    public JAXBElement<GetFileRequestType> createGetFileRequest(GetFileRequestType value) {
        return new JAXBElement<GetFileRequestType>(_GetFileRequest_QNAME, GetFileRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostFileResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ech.ch/xmlns/wupift/1", name = "postFileResponse")
    public JAXBElement<PostFileResponseType> createPostFileResponse(PostFileResponseType value) {
        return new JAXBElement<PostFileResponseType>(_PostFileResponse_QNAME, PostFileResponseType.class, null, value);
    }

}
