/*
 * Copyright (C) MX4J.
 * All rights reserved.
 *
 * This software is distributed under the terms of the MX4J License version 1.0.
 * See the terms of the MX4J License in the documentation provided with this software.
 */



import java.net.Socket;

/**
 * This class invokes the shutdown section of the XML configuration file bundled
 * with this example. <br />
 * Refer to the ConfigurationLoader documentation for further information.
 *
 * @see ConfigurationStartup
 * @author <a href="mailto:biorn_steedom@users.sourceforge.net">Simone Bordet</a>
 * @version $Revision: 1.1 $
 */
public class ConfigurationShutdown
{
   public static void main(String[] args) throws Exception
   {
      String shutdownCommand = "shutdown";
      Socket socket = new Socket("127.0.0.1", 9876);
      socket.getOutputStream().write(shutdownCommand.getBytes());
      socket.close();
   }
}
