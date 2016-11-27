
package ch.ralscha.wsdemo.ds;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ralscha.wsdemo.ds package. 
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

    private final static QName _UpdateDocument_QNAME = new QName("http://wsdemo.ralscha.ch/", "updateDocument");
    private final static QName _SaveDocument_QNAME = new QName("http://wsdemo.ralscha.ch/", "saveDocument");
    private final static QName _DeleteDocumentResponse_QNAME = new QName("http://wsdemo.ralscha.ch/", "deleteDocumentResponse");
    private final static QName _GetDocumentContent_QNAME = new QName("http://wsdemo.ralscha.ch/", "getDocumentContent");
    private final static QName _DeleteDocument_QNAME = new QName("http://wsdemo.ralscha.ch/", "deleteDocument");
    private final static QName _GetDocumentContentResponse_QNAME = new QName("http://wsdemo.ralscha.ch/", "getDocumentContentResponse");
    private final static QName _UpdateDocumentResponse_QNAME = new QName("http://wsdemo.ralscha.ch/", "updateDocumentResponse");
    private final static QName _SaveDocumentResponse_QNAME = new QName("http://wsdemo.ralscha.ch/", "saveDocumentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ralscha.wsdemo.ds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDocumentContent }
     * 
     */
    public GetDocumentContent createGetDocumentContent() {
        return new GetDocumentContent();
    }

    /**
     * Create an instance of {@link DeleteDocumentResponse }
     * 
     */
    public DeleteDocumentResponse createDeleteDocumentResponse() {
        return new DeleteDocumentResponse();
    }

    /**
     * Create an instance of {@link DeleteDocument }
     * 
     */
    public DeleteDocument createDeleteDocument() {
        return new DeleteDocument();
    }

    /**
     * Create an instance of {@link GetDocumentContentResponse }
     * 
     */
    public GetDocumentContentResponse createGetDocumentContentResponse() {
        return new GetDocumentContentResponse();
    }

    /**
     * Create an instance of {@link UpdateDocument }
     * 
     */
    public UpdateDocument createUpdateDocument() {
        return new UpdateDocument();
    }

    /**
     * Create an instance of {@link SaveDocument }
     * 
     */
    public SaveDocument createSaveDocument() {
        return new SaveDocument();
    }

    /**
     * Create an instance of {@link DsSessionDto }
     * 
     */
    public DsSessionDto createDsSessionDto() {
        return new DsSessionDto();
    }

    /**
     * Create an instance of {@link SaveDocumentResponse }
     * 
     */
    public SaveDocumentResponse createSaveDocumentResponse() {
        return new SaveDocumentResponse();
    }

    /**
     * Create an instance of {@link DsDocumentDto }
     * 
     */
    public DsDocumentDto createDsDocumentDto() {
        return new DsDocumentDto();
    }

    /**
     * Create an instance of {@link DsPropertyDto }
     * 
     */
    public DsPropertyDto createDsPropertyDto() {
        return new DsPropertyDto();
    }

    /**
     * Create an instance of {@link UpdateDocumentResponse }
     * 
     */
    public UpdateDocumentResponse createUpdateDocumentResponse() {
        return new UpdateDocumentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "updateDocument")
    public JAXBElement<UpdateDocument> createUpdateDocument(UpdateDocument value) {
        return new JAXBElement<UpdateDocument>(_UpdateDocument_QNAME, UpdateDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "saveDocument")
    public JAXBElement<SaveDocument> createSaveDocument(SaveDocument value) {
        return new JAXBElement<SaveDocument>(_SaveDocument_QNAME, SaveDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "deleteDocumentResponse")
    public JAXBElement<DeleteDocumentResponse> createDeleteDocumentResponse(DeleteDocumentResponse value) {
        return new JAXBElement<DeleteDocumentResponse>(_DeleteDocumentResponse_QNAME, DeleteDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "getDocumentContent")
    public JAXBElement<GetDocumentContent> createGetDocumentContent(GetDocumentContent value) {
        return new JAXBElement<GetDocumentContent>(_GetDocumentContent_QNAME, GetDocumentContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "deleteDocument")
    public JAXBElement<DeleteDocument> createDeleteDocument(DeleteDocument value) {
        return new JAXBElement<DeleteDocument>(_DeleteDocument_QNAME, DeleteDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentContentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "getDocumentContentResponse")
    public JAXBElement<GetDocumentContentResponse> createGetDocumentContentResponse(GetDocumentContentResponse value) {
        return new JAXBElement<GetDocumentContentResponse>(_GetDocumentContentResponse_QNAME, GetDocumentContentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "updateDocumentResponse")
    public JAXBElement<UpdateDocumentResponse> createUpdateDocumentResponse(UpdateDocumentResponse value) {
        return new JAXBElement<UpdateDocumentResponse>(_UpdateDocumentResponse_QNAME, UpdateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdemo.ralscha.ch/", name = "saveDocumentResponse")
    public JAXBElement<SaveDocumentResponse> createSaveDocumentResponse(SaveDocumentResponse value) {
        return new JAXBElement<SaveDocumentResponse>(_SaveDocumentResponse_QNAME, SaveDocumentResponse.class, null, value);
    }

}
