
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import javax.xml.parsers.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class Test {

	public static void main(String[] args) throws Exception {
		
		// Get a "parser factory", an an object that creates parsers
		/*
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		// Set up the factory to create the appropriate type of parser
		saxParserFactory.setValidating(false);
		saxParserFactory.setNamespaceAware(false); // Not this month...

		SAXParser saxparser = saxParserFactory.newSAXParser();
		*/
				
		try {
            // Build the document with SAX and Xerces, no validation
            SAXBuilder builder = new SAXBuilder();
            // Create the document
            Document doc = builder.build(new File("outlook.xml"));

            // Output the document, use standard formatter
            XMLOutputter fmt = new XMLOutputter();
            fmt.output(doc, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    

	}

}