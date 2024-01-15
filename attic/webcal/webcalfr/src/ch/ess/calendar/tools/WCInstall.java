package ch.ess.calendar.tools;

import java.io.*;

public class WCInstall {

  private final static String help = "java ch.ess.calendar.tools.WCInstall <smtp> <senderaddress> <httpport>";

  public static void main(String[] args) {
    if (args.length == 3) {
      String smtp   = args[0];
      String sender = args[1];
      int port = 80;
      try {
        port = Integer.parseInt(args[2]);
      } catch (NumberFormatException nfe) {}
      
      install(smtp, sender, port);
      System.out.println("install successful");

    } else {
      System.err.println(help);
    }
  }
  

  

  public static void install(String smtp, String sender, int port) {
    try {
      File currentDir = new File(".");
      String currentPathString = currentDir.getCanonicalPath();
      currentPathString += File.separator;
    
      String currentPathStringR = replaceBackslash(currentPathString);
    
      
      createWebXMLFile(currentPathStringR, smtp, sender);      
      createServerXMLFile(currentPathStringR, String.valueOf(port));
      createInstallServiceFile(currentPathStringR);
      
      

    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  
  }

  private static String replaceBackslash(String str) {
    StringBuffer sb = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '\\') {
        sb.append("/");
      } else {
        sb.append(str.charAt(i));
      }
    }
    return sb.toString();
  }


  


  private static void createInstallServiceFile(String path) throws IOException {
        
    BufferedReader br = new BufferedReader(new FileReader(path + "install_nt_service.bat.template"));
    PrintWriter pw = new PrintWriter(new FileWriter(path + "install_nt_service.bat"));
    
    String line;
    while ((line = br.readLine()) != null) {

      int pos1 = line.indexOf("$CURRENT_DIR$");

      if (pos1 != -1) {
        String output = line.substring(0, pos1);
        output += path;
        output += line.substring(pos1+"$CURRENT_DIR$".length());
        pw.println(output);                 
      } else {
        pw.println(line);
      }
    }

    pw.close();
    br.close();
  }



  private static void createWebXMLFile(String path, String smtp, String sender) throws IOException {
        
    BufferedReader br = new BufferedReader(new FileReader(path + "web.xml.template"));
    PrintWriter pw = new PrintWriter(new FileWriter(path + "webapps" + File.separator + "webcal" + File.separator +  "web.xml"));
    
    String line;
    while ((line = br.readLine()) != null) {

      int pos1 = line.indexOf("$SMTP$");
      int pos2 = line.indexOf("$SENDER$");

      if (pos1 != -1) {
        String output = line.substring(0, pos1);
        output += smtp;
        output += line.substring(pos1+6);
        pw.println(output);
      } else if (pos2 != -1) {
        String output = line.substring(0, pos2);
        output += sender;
        output += line.substring(pos2+8);
        pw.println(output);                
      } else {
        pw.println(line);
      }
    }

    pw.close();
    br.close();
    
    //UpdateJar uj = new UpdateJar();
    //uj.update(path + File.separator + "webapps" + File.separator + "webcal.war", "web.xml");
          
  }




  private static void createServerXMLFile(String path, String port) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path + "etc" + File.separator + "jetty.xml.template"));
    PrintWriter pw = new PrintWriter(new FileWriter(path + "etc" + File.separator + "jetty.xml"));
    
    String line;
    while ((line = br.readLine()) != null) {
      int pos = line.indexOf("$HTTPPORT$");
      if (pos != -1) {
        String output = line.substring(0, pos);
        output += port;
        output += line.substring(pos+10);
        pw.println(output);
      } else {
        pw.println(line);
      }
    }

    pw.close();
    br.close();
  }


}