import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class PrettyPrinter {

  public static void main(String[] args) {

    PrintStream out = System.out;

    try {
      // Request document building without validation
      SAXBuilder builder = new SAXBuilder(false);
      Document doc = builder.build(new File("test.xml"));

      // Get the root element
      Element root = doc.getRootElement();

      // Print servlet information
      List servlets = root.getChildren("servlet");
      out.println("This WAR has "+ servlets.size() +" registered servlets:");
      Iterator i = servlets.iterator();
      while (i.hasNext()) {
        Element servlet = (Element) i.next();
        out.print("\t" + servlet.getChildTextTrim("servlet-name") +
                  " for " + servlet.getChildTextTrim("servlet-class"));
        List initParams = servlet.getChildren("init-param");
        out.println(" (it has " + initParams.size() + " init params)");
      }

      // Print security role information
      List securityRoles = root.getChildren("security-role");
      if (securityRoles.size() == 0) {
        out.println("This WAR contains no roles");
      }
      else {
        Element securityRole = (Element) securityRoles.get(0);
        List roleNames = securityRole.getChildren("role-name");
        out.println("This WAR contains " + roleNames.size() + " roles:");
        i = roleNames.iterator();
        while (i.hasNext()) {
          Element e = (Element) i.next();
          out.println("\t" + e.getTextTrim() );
        }
      }

      // Print distributed information (notice this is out of order)
      List distrib = root.getChildren("distributed");
      if (distrib.size() == 0) {
        out.println("This WAR is not distributed");
      } else {
        out.println("This WAR is distributed");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}