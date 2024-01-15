import java.io.*;
import java.util.*;
import org.xml.sax.SAXException;
import org.apache.struts.digester.*;

public class ConfigReader {
  
  private DatabaseConfig dc;
  
  public void setDatabaseConfig(DatabaseConfig dc) {
    this.dc = dc;
  }

  public DatabaseConfig getDatabaseConfig() {
    return dc;
  }

  public void read(String fileName) {

    File file = new File(fileName);

    // Define a digester for our use
    Digester digester = new Digester();
    //digester.setDebug(999);
    //digester.setValidating(true);

    //add rules
    digester.push(this);

    digester.addObjectCreate("database", "DatabaseConfig");
    digester.addCallMethod("database/driver", "setDriver", 1);
    digester.addCallParam("database/driver", 0);
    digester.addCallMethod("database/policy", "setPolicy", 1);
    digester.addCallParam("database/policy", 0);
    digester.addCallMethod("database/url", "setUrl", 1);
    digester.addCallParam("database/url", 0);
    digester.addCallMethod("database/user", "setUser", 1);
    digester.addCallParam("database/user", 0);
    digester.addCallMethod("database/password", "setPassword", 1);
    digester.addCallParam("database/password", 0);
    digester.addCallMethod("database/package", "setPackageName", 1);
    digester.addCallParam("database/package", 0);
    digester.addCallMethod("database/outputdir", "setOutputDir", 1);
    digester.addCallParam("database/outputdir", 0);
    digester.addSetNext("database", "setDatabaseConfig", "DatabaseConfig");

    // Parse the specified XML file
    try {
      digester.parse(file);
    } catch (IOException e) {
      System.out.println("IOException: " + e);
      e.printStackTrace(System.out);
      System.exit(2);
    }
    catch (SAXException e) {
      System.out.println("SAXException: " + e);
      e.printStackTrace(System.out);
      System.exit(3);
    }
  
  }
   
}