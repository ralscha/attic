
package ch.ralscha.wsdemo.ds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "DocumentServiceImplService", targetNamespace = "http://wsdemo.ralscha.ch/", wsdlLocation = "http://localhost:8080/cxf/DocumentService?wsdl")
public class DocumentServiceImplService
    extends Service
{

    private final static URL DOCUMENTSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ch.ralscha.wsdemo.ds.DocumentServiceImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ch.ralscha.wsdemo.ds.DocumentServiceImplService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/cxf/DocumentService?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/cxf/DocumentService?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        DOCUMENTSERVICEIMPLSERVICE_WSDL_LOCATION = url;
    }

    public DocumentServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DocumentServiceImplService() {
        super(DOCUMENTSERVICEIMPLSERVICE_WSDL_LOCATION, new QName("http://wsdemo.ralscha.ch/", "DocumentServiceImplService"));
    }

    /**
     * 
     * @return
     *     returns DocumentService
     */
    @WebEndpoint(name = "DocumentServiceImplPort")
    public DocumentService getDocumentServiceImplPort() {
        return super.getPort(new QName("http://wsdemo.ralscha.ch/", "DocumentServiceImplPort"), DocumentService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DocumentService
     */
    @WebEndpoint(name = "DocumentServiceImplPort")
    public DocumentService getDocumentServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://wsdemo.ralscha.ch/", "DocumentServiceImplPort"), DocumentService.class, features);
    }

}
