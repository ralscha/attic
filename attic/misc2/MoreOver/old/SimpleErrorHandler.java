
/**
 * Sample code by Mark Johnson, JavaWorld, April 2000.
 * Code is may be used for any legal purpose, including commercial
 * purposes, with no warranty expressed or implied.
 * email: mark.johnson@javaworld.com
 */
import java.io.*;
import org.xml.sax.*;

public class SimpleErrorHandler implements ErrorHandler {
	/**
	 * SimpleErrorHandler constructor comment.
	 */	 
	public SimpleErrorHandler() {
		super();
	}
	
	/**
	 * error method comment.
	 */
	public void error(SAXParseException ex) throws SAXException {
		File fInput = new File (ex.getSystemId());
		System.err.println("e: " + fInput.getPath() + ": line " + ex.getLineNumber() + ": " + ex);
	}
	
	/**
	 * fatalError method comment.
	 */
	public void fatalError(SAXParseException ex) throws SAXException {
		File fInput = new File(ex.getSystemId());
		System.err.println("E: " + fInput.getName() + ": line " + ex.getLineNumber() + ": " + ex);
	}
	
	/**
	 * warning method comment.
	 */
	public void warning(SAXParseException ex) throws SAXException {
		File fInput = new File(ex.getSystemId());
		System.err.println("w: " + fInput.getName() + ": line " + ex.getLineNumber() + ": " + ex);
	}
}