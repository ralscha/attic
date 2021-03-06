
package ch.ralscha.wsdemo.ds;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "DocumentService", targetNamespace = "http://wsdemo.ralscha.ch/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DocumentService {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "updateDocument", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.UpdateDocument")
    @ResponseWrapper(localName = "updateDocumentResponse", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.UpdateDocumentResponse")
    public void updateDocument(
        @WebParam(name = "arg0", targetNamespace = "")
        DsSessionDto arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        DsDocumentDto arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "deleteDocument", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.DeleteDocument")
    @ResponseWrapper(localName = "deleteDocumentResponse", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.DeleteDocumentResponse")
    public void deleteDocument(
        @WebParam(name = "arg0", targetNamespace = "")
        DsSessionDto arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveDocument", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.SaveDocument")
    @ResponseWrapper(localName = "saveDocumentResponse", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.SaveDocumentResponse")
    public String saveDocument(
        @WebParam(name = "arg0", targetNamespace = "")
        DsSessionDto arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        DsDocumentDto arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        boolean arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        String arg6);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDocumentContent", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.GetDocumentContent")
    @ResponseWrapper(localName = "getDocumentContentResponse", targetNamespace = "http://wsdemo.ralscha.ch/", className = "ch.ralscha.wsdemo.ds.GetDocumentContentResponse")
    public String getDocumentContent(
        @WebParam(name = "arg0", targetNamespace = "")
        DsSessionDto arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}
