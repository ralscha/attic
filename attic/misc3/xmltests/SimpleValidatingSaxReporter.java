
/*
 * Sample code for "SAX Appeal", by Mark Johnson, JavaWorld, March 2000.
 * Code is may be used for any legal purpose, including commercial
 * purposes, with no warranty expressed or implied.
 * email: mark.johnson@javaworld.com
 */
import org.xml.sax.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;

/**
 * Simple class demonstrates how to use SAX by reporting all SAX events
 */
public class SimpleValidatingSaxReporter extends org.xml.sax.HandlerBase {

	private SimpleErrorHandler _seh = new SimpleErrorHandler();

public SimpleValidatingSaxReporter() {
	super();
}
/**
 * Handle incoming characters event
 */
public void characters(char[] arg1, int arg2, int arg3) throws org.xml.sax.SAXException {
	String sSubstring;
	if (arg3 >= 10) {
		sSubstring = new String(arg1, arg2, 10) + "...";
	} else {
		sSubstring = new String(arg1, arg2, arg3);
	}
	System.out.println("%%% characters(\"" + sSubstring + "\"," +
		arg2 + ", " + arg3 + ")");
}
/**
 * Handle endDocument event.
 */
public void endDocument() throws org.xml.sax.SAXException {
	System.out.println("[End of document]");
	System.out.println("%%%endDocument()");
}
/**
 * Handle endElement event.
 */
public void endElement(String arg1) throws org.xml.sax.SAXException {
	System.out.println("</" + arg1 + ">");
	System.out.println("%%%endElement(\"" + arg1 + "\")");
}
/**
 * error method comment.
 */
public void error(SAXParseException ex) throws SAXException {
	_seh.error(ex);
}
/**
 * fatalError method comment.
 */
public void fatalError(SAXParseException ex) throws SAXException {
	_seh.fatalError(ex);
}
/**
 * Handle ignorableWhitespace event.
 */
public void ignorableWhitespace(char[] arg1, int arg2, int arg3) throws org.xml.sax.SAXException {
	String sSubstring;
	if (arg3 >= 10) {
		sSubstring = new String(arg1, arg2, 10) + "...";
	} else {
		sSubstring = new String(arg1, arg2, arg3);
	}
		
	// Annoying, but turn it on if you want to see it
//	System.out.println("%%% ignorableWhitespace(\"" + sSubstring + "\"," +
//		arg2 + ", " + arg3 + ")");
}
/**
 * Parse file using SimpleValidatingSaxReporter.
 */
public static void main(String args[]) {
	SimpleValidatingSaxReporter ssr = new SimpleValidatingSaxReporter();
	try {
		ssr.parseDocument(true, ssr, args[0]);
	} catch (Exception ex) {
		System.err.println(ex);
	}
}
/**
 * Reimplement this method to use a parser from a different vendor. See your
 * parser package documentation for details.
 * @param isValidating boolean
 * @param documentHandler org.xml.sax.DocumentHandler
 * @param errorHandler org.xml.sax.ErrorHandler
 * @param sFilename java.lang.String
 */
protected void parseDocument(boolean isValidating, HandlerBase handler, String sFilename) {
	try {
		// Get a "parser factory", an an object that creates parsers
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		// Set up the factory to create the appropriate type of parser
		saxParserFactory.setValidating(isValidating);
		saxParserFactory.setNamespaceAware(false); // Not this month...

		SAXParser parser = saxParserFactory.newSAXParser();

		parser.parse(new File(sFilename), handler);
	} catch (Exception ex) {
		System.err.println("Exception: " + ex);
		System.exit(2);
	}
}
/**
 * Handle processingInstruction event.
 */
public void processingInstruction(String arg1, String arg2) throws org.xml.sax.SAXException {
}
/**
 * Handle setDocumentLocator event.
 */
public void setDocumentLocator(org.xml.sax.Locator arg1) {
}
/**
 * Handle startDocument event.
 */
public void startDocument() throws org.xml.sax.SAXException {
	System.out.println("[Beginning of document]");
	System.out.println("%%%startDocument()");
}
/**
 * Handle startElement event.
 */
public void startElement(String arg1, org.xml.sax.AttributeList arg2) throws org.xml.sax.SAXException {
	System.out.println("</" + arg1 + ">");
	System.out.println("%%%startElement(\"" + arg1 + "\", [AttributeList]" + ")");
}
/**
 * warning method comment.
 */
public void warning(SAXParseException ex) throws SAXException {
	_seh.warning(ex);
}
}