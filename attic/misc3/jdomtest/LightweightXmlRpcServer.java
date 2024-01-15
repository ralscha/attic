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


import java.util.Enumeration;
import java.io.IOException;
import java.util.Hashtable;

import helma.xmlrpc.XmlRpc;
import helma.xmlrpc.WebServer;

/**
 * <b><code>LightweightXmlRpcServer</code></b> is a utility class
 *   that will start an XML-RPC server listening for HTTP requests
 *   and register a set of handlers, defined in a configuration file.
 *
 * @author Brett McLaughlin
 * @version 1.0
 */
public class LightweightXmlRpcServer {

    /** The XML-RPC server utility class */
    private WebServer server;

    /** Configuration file to use */
    private XmlRpcConfiguration config;

    /**
     * <p>
     * This will store the configuration file for the server to use.
     * </p>
     *
     * @param configFile <code>String</code> filename to read for
     *                   configuration information.
     * @throws <code>IOException</code> when the server cannot read
     *         it's configuration information.
     */
    public LightweightXmlRpcServer(String configFile)
        throws IOException {

        config = new XmlRpcConfiguration(configFile);
    }

    /**
     * <p>
     * This will start up the server.
     * </p>
     *
     * @throws <code>IOException</code> when problems occur.
     */
    public void start() throws IOException {
        try {
            // Use Apache Xerces SAX Parser
            XmlRpc.setDriver(config.getDriverClass());

            System.out.println("Starting up XML-RPC Server...");
            server = new WebServer(config.getPortNumber());

            // Register handlers
            registerHandlers(config.getHandlers());

        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading SAX parser: " +
                e.getMessage());
        }
    }

    /**
     * <p>
     * This will register the handlers supplied in the XML-RPC
     *   server (typically from <code>{@link #getHandlers()}</code>.
     * </p>
     *
     * @param handlers <code>Hashtable</code> of handlers to register.
     */
    private void registerHandlers(Hashtable handlers) {
        Enumeration handlerNames = handlers.keys();

        // Loop through the requested handlers
        while (handlerNames.hasMoreElements()) {
            String handlerName = (String)handlerNames.nextElement();
            String handlerClass = (String)handlers.get(handlerName);

            // Add this handler to the server
            try {
                server.addHandler(handlerName,
                    Class.forName(handlerClass).newInstance());

                System.out.println("Registered handler " + handlerName +
                                   " to class " + handlerClass);
            } catch (Exception e) {
                System.out.println("Could not register handler " +
                                   handlerName + " with class " +
                                   handlerClass);
            }
        }
    }

    /**
     * <p>
     * Provide a static entry point.
     * </p>
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println(
                "Usage: " +
                "java com.oreilly.xml.LightweightXmlRpcServer " +
                "[configFile]");
            System.exit(-1);
        }

        try {
            // Load configuration information
            LightweightXmlRpcServer server =
                new LightweightXmlRpcServer(args[0]);

            // Start the server
            server.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
