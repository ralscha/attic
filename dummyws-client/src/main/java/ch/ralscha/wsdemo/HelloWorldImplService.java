
package ch.ralscha.wsdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import ch.ralscha.wsdemo.HelloWorld;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "HelloWorldImplService", targetNamespace = "http://wsdemo.ralscha.ch/", wsdlLocation = "http://localhost:8080/cxf/HelloWorld?wsdl")
public class HelloWorldImplService
    extends Service
{

    private final static URL HELLOWORLDIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ch.ralscha.wsdemo.HelloWorldImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ch.ralscha.wsdemo.HelloWorldImplService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/cxf/HelloWorld?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/cxf/HelloWorld?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        HELLOWORLDIMPLSERVICE_WSDL_LOCATION = url;
    }

    public HelloWorldImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloWorldImplService() {
        super(HELLOWORLDIMPLSERVICE_WSDL_LOCATION, new QName("http://wsdemo.ralscha.ch/", "HelloWorldImplService"));
    }

    /**
     * 
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldImplPort")
    public HelloWorld getHelloWorldImplPort() {
        return super.getPort(new QName("http://wsdemo.ralscha.ch/", "HelloWorldImplPort"), HelloWorld.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldImplPort")
    public HelloWorld getHelloWorldImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://wsdemo.ralscha.ch/", "HelloWorldImplPort"), HelloWorld.class, features);
    }

}