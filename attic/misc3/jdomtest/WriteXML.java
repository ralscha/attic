import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class WriteXML {

  public static void main(String[] args) {

    
    try {
      
      Element root = new Element("myRootElement"); 
      Document doc = new Document(root); 
      root.addContent("Der Contents");

      Element element = new Element("test");
      root.addContent(element);
      element.addContent("Save cocoon.properties in <TOMCAT_HOME>/conf"); 

      XMLOutputter fmt = new XMLOutputter();
      fmt.output(doc, System.out);


    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}