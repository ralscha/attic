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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.oreilly.xml.XmlRpcConfiguration;

import helma.xmlrpc.XmlRpc;
import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;

/**
 * <b><code>SchedulerClient</code></b> is an XML-RPC client
 *   that makes XML-RPC requests to <code>Scheduler</code>.
 *
 * @author Brett McLaughlin
 * @version 1.0
 */
public class SchedulerClient {

    /**
     * <p>
     * Add events to the Scheduler.
     * </p>
     *
     * @param client <code>XmlRpcClient</code> to connect to
     */
    public static void addEvents(XmlRpcClient client)
        throws XmlRpcException, IOException {

        System.out.println("\nAdding events...\n");

        // Parameters for events
        Vector params = new Vector();

        // Add an event for next month
        params.addElement("Proofread final draft");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        params.addElement(cal.getTime());

        // Add the event
        if (((Boolean)client.execute("scheduler.addEvent", params))
                            .booleanValue()) {
            System.out.println("Event added.");
        } else {
            System.out.println("Could not add event.");
        }

        // Add an event for tomorrow
        params.clear();
        params.addElement("Submit final draft");

        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        params.addElement(cal.getTime());

        // Add the event
        if (((Boolean)client.execute("scheduler.addEvent", params))
                            .booleanValue()) {
            System.out.println("Event added.");
        } else {
            System.out.println("Could not add event.");
        }

    }

    /**
     * <p>
     * List the events currently in the Scheduler.
     * </p>
     *
     * @param client <code>XmlRpcClient</code> to connect to
     */
    public static void listEvents(XmlRpcClient client)
        throws XmlRpcException, IOException {

        System.out.println("\nListing events...\n");

        // Get the events in the scheduler
        Vector params = new Vector();
        Vector events =
            (Vector)client.execute("scheduler.getListOfEvents", params);
        for (int i=0; i<events.size(); i++) {
            System.out.println((String)events.elementAt(i));
        }
    }

    /**
     * <p>
     * Static entry point for the demo.
     * </p>
     */
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println(
                "Usage: java SchedulerClient [configFile]");
            System.exit(-1);
        }


        try {
            // Load Configuration File
            XmlRpcConfiguration config =
                new XmlRpcConfiguration(args[0]);

            // Use the Apache Xerces SAX Parser Implementation
            XmlRpc.setDriver(config.getDriverClass());

            // Connect to server
            XmlRpcClient client =
                new XmlRpcClient("http://" +
                                 config.getHostname() + ":" +
                                 config.getPortNumber());

            // Add some events
            addEvents(client);

            // List events
            listEvents(client);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
