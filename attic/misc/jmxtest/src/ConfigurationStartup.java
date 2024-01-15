/*
 * Copyright (C) MX4J.
 * All rights reserved.
 *
 * This software is distributed under the terms of the MX4J License version 1.0.
 * See the terms of the MX4J License in the documentation provided with this software.
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import mx4j.tools.config.ConfigurationLoader;

import org.apache.commons.modeler.Registry;

/**
 * This example shows how to use the XML configuration files to load MBeans into
 * an MBeanServer. <br />
 * The main class is {@link ConfigurationLoader}, that is able to read the XML
 * configuration format defined by the MX4J project (see the online documentation
 * for details on the format).
 * A <code>ConfigurationLoader</code> is an MBean itself, and loads information
 * from one XML file into one MBeanServer. <br />
 * This example runs by specifying the path of an XML configuration file as a
 * program argument, such as
 * <pre>
 * java -classpath ... mx4j.examples.tools.config.ConfigurationStartup ./config.xml
 * </pre>
 * Refer to the documentation about the ConfigurationLoader for further information.
 * @see ConfigurationShutdown
 * @author <a href="mailto:biorn_steedom@users.sourceforge.net">Simone Bordet</a>
 * @version $Revision: 1.1 $
 */
public class ConfigurationStartup
{
   public static void main(String[] args) throws Exception
   {
      // The MBeanServer
      MBeanServer server = MBeanServerFactory.newMBeanServer();

      // The configuration loader

      /* Choice 1: as an external object */
      // ConfigurationLoader loader = new ConfigurationLoader(server);

      /* Choice 2: as a created MBean */
      // server.createMBean(ConfigurationLoader.class.getName(), ObjectName.getInstance("config:service=loader"), null);

      /* Choice 3: as a registered MBean */
      ConfigurationLoader loader = new ConfigurationLoader();
      server.registerMBean(loader, ObjectName.getInstance("config:service=loader"));

      // The XML file

      /* Choice 1: read it from classpath using classloaders
         Note: the directory that contains the XML file must be in the classpath */
       InputStream stream = ConfigurationStartup.class.getClassLoader().getResourceAsStream("config_jsr160.xml");
       Reader reader = new BufferedReader(new InputStreamReader(stream));


      // Read and execute the 'startup' section of the XML file
      loader.startup(reader);

      reader.close();
//
//      
//      URL url= ConfigurationStartup.class.getResource("/model.xml");
//    Registry registry = Registry.getRegistry(null, null);
//    registry.setMBeanServer(server);
//    registry.loadMetadata(url);
//
//    
//    registry.registerComponent( new HelloWorld(), ":mbean=helloworld", "helloWorld" );

      
      ObjectName name = new ObjectName(":mbean=helloworld");
      server.createMBean("HelloWorld", name, null);

      System.out.println("Application configured successfully");
   }
}
