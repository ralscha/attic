/*--

 Copyright (C) 2000 Brett McLaughlin. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
    this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions, the disclaimer that follows these conditions,
    and/or other materials provided with the distribution.

 3. Products derived from this software may not be called "Java and XML", nor may
    "Java and XML" appear in their name, without prior written permission from
    Brett McLaughlin (brett@newInstance.com).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 JDOM PROJECT  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software was originally created by Brett McLaughlin <brett@newInstance.com>.
 For more  information on "Java and XML", please see <http://www.oreilly.com/catalog/javaxml/>
 or <http://www.newInstance.com>.

 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.Builder;
import org.jdom.input.DOMBuilder;

/**
 * <b><code>XmlRpcConfiguration</code></b> is a utility class
 *   that will load configuration information for XML-RPC servers
 *   and clients to use.
 *
 * @author Brett McLaughlin
 * @version 1.0
 */
public class XmlRpcConfiguration {

    /** The stream to read the XML configuration from */
    private InputStream in;

    /** Port number server runs on */
    private int portNumber;

    /** Hostname server runs on */
    private String hostname;

    /** SAX Driver Class to load */
    private String driverClass;

    /** Handlers to register in XML-RPC server */
    private Hashtable handlers;

    /**
     * <p>
     * This will set a filename to read configuration
     *   information from.
     * </p>
     *
     * @param filename <code>String</code> name of
     *                 XML configuration file.
     */
    public XmlRpcConfiguration(String filename)
        throws IOException {

        this(new FileInputStream(filename));
    }

    /**
     * <p>
     * This will set a filename to read configuration
     *   information from.
     * </p>
     *
     * @param in <code>InputStream</code> to read
     *           configuration information from.
     */
    public XmlRpcConfiguration(InputStream in)
        throws IOException {

        this.in = in;
        portNumber = 0;
        hostname = "";
        handlers = new Hashtable();

        // Parse the XML configuration information
        parseConfiguration();
    }

    /**
     * <p>
     * This returns the port number the server listens on.
     * </p>
     *
     * @return <code>int</code> - number of server port.
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * <p>
     * This returns the hostname the server listens on.
     * </p>
     *
     * @return <code>String</code> - hostname of server.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * <p>
     * This returns the SAX driver class to load.
     * </p>
     *
     * @return <code>String</code> - name of SAX driver class.
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * <p>
     * This returns the handlers the server should register.
     * </p>
     *
     * @return <code>Hashtable</code> of handlers.
     */
    public Hashtable getHandlers() {
        return handlers;
    }

    /**
     * <p>
     * Parse the XML configuration information and
     *   make it available to clients.
     * </p>
     *
     * @throws <code>IOException</code> when errors occur.
     */
    private void parseConfiguration() throws IOException {

        try {
            // Request DOM Implementation and Xerces Parser
            Builder builder =
                new DOMBuilder("org.jdom.adapters.XercesDOMAdapter");

            // Get the Configuration Document, with validation
            Document doc = builder.build(in);

            // Get the root element
            Element root = doc.getRootElement();

            // Get the JavaXML namespace
            Namespace ns = Namespace.getNamespace("JavaXML",
                           "http://www.oreilly.com/catalog/javaxml/");

            // Load the hostname, port, and handler class
            hostname = root.getChild("hostname", ns).getContent();
            driverClass = root.getChild("parserClass", ns).getContent();
            portNumber =
                Integer.parseInt(root.getChild("port", ns).getContent());

            // Get the handlers
            List handlerElements =
                root.getChild("xmlrpc-server", ns)
                    .getChild("handlers", ns)
                    .getChildren("handler", ns);

            Iterator i = handlerElements.iterator();
            while (i.hasNext()) {
                Element current = (Element)i.next();
                handlers.put(current.getChild("identifier", ns).getContent(),
                             current.getChild("class", ns).getContent());
            }
        } catch (JDOMException e) {
            throw new IOException(e.getMessage());
        }
    }
}