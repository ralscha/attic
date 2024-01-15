
import org.xml.sax.*;
import java.lang.reflect.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;
import java.util.*;
import java.io.*;

public class Lax extends org.xml.sax.HandlerBase {

	// LAX translates XML content into method calls on this object
	private Vector _vecHandlers = null;
	private Vector _vecTags = null;
	private static Class[] _caNoArgs = null;
	private static Class[] _caAttrList = null;
	private static Class[] _caString = null;
	private SimpleErrorHandler _seh = new SimpleErrorHandler();

	// Initialize class arrays used for reflection
	static {
		_caNoArgs = new Class[] {};
		_caAttrList = new Class[] {org.xml.sax.AttributeList.class};
		_caString = new Class[] {java.lang.String.class};
	}
	
	/**
	 * Lax default constructor
	 */
	public Lax() {
		super();
		_vecHandlers = new Vector();
		_vecTags = new Vector();
	}
	
	/**
	 * Lax ctor with a single handler
	 */
	public Lax(Object handler_) {
		super();
		_vecHandlers = new Vector();
		_vecTags = new Vector();
		addHandler(handler_);
	}
	
	/**
	 * Add a handler to the list of handler objects.
	 * @param objHandler_ java.lang.Object
	 */
	public void addHandler(Object objHandler_) {
		_vecHandlers.addElement(objHandler_);
	}
	
	/**
	 * Handle an incoming block of text by calling the textOf method for the
	 * current tag.
	 */
	public void characters(char[] caChars, int iStart, int iEnd) throws SAXException {
		String sCurrentTag = sCurrentTag();
		
		if (sCurrentTag != null) {
			int i;
			String sTextMethodName = "textOf" + sCurrentTag;
			String sArg = null;
			
			// Call every text method for current tag found in the list of handlers.
			for (i = 0; i < _vecHandlers.size(); i++) {
				Object oThisHandler = _vecHandlers.elementAt(i);
				Method mTextMethod = mFindMethod(oThisHandler, sTextMethodName, _caString);
				if (mTextMethod != null) {
					try {
						if (sArg == null) {
							sArg = new String(caChars, iStart, iEnd);
						}
						mTextMethod.invoke(oThisHandler, new Object[] { sArg });
					} catch (InvocationTargetException ex) {
						System.err.println(ex);
					} catch (IllegalAccessException ex) {
						System.err.println(ex);
					}
				}
			}
		}
	}
	
	/**
	 * endDocument method comment.
	 */
	public void endDocument() throws org.xml.sax.SAXException {
	}
	
	/**
	 * Call all end tag methods in the handler list
	 */
	public void endElement(String sTag) throws SAXException {
		int i;
		String sEndMethodName = "end" + sTag;

		// Call every tag start method for this tag found in the list of handlers.
		for (i = 0; i < _vecHandlers.size(); i++) {
			Object oThisHandler = _vecHandlers.elementAt(i);
			Method mEndMethod = mFindMethod(oThisHandler, sEndMethodName, _caNoArgs);
			if (mEndMethod != null) {
				try {
					mEndMethod.invoke(oThisHandler, new Object[] {});
				} catch (InvocationTargetException ex) {
					System.err.println(ex);
				} catch (IllegalAccessException ex) {
					System.err.println(ex);
				}
			}
		}
		popTag();
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
	 * Return a method of object oHandler 
	 * with the given name and argument list, or null if not found
	 * @return java.lang.reflect.Method
	 * @param oHandler java.lang.Object - The handler object to search for a method.
	 * @param sTag java.lang.String - The tag to find.
	 */
	private Method mFindMethod(Object oHandler, String sMethodName, Class[] caArgs) {
		Method m = null;
		Class classOfHandler = oHandler.getClass();

		// Find a method with the given name and argument list
		try {
			m = classOfHandler.getMethod(sMethodName, caArgs);
		} catch (NoSuchMethodException ex) {
			// Ignore exception - no such method exists.
		}
		return m;
	}
	
	/**
	 * Reimplement this method to use a parser from a different vendor. See your
	 * parser package documentation for details.
	 * @param isValidating boolean
	 * @param documentHandler org.xml.sax.DocumentHandler
	 * @param errorHandler org.xml.sax.ErrorHandler
	 * @param inputStream java.io.InputStream
	 */
	public boolean parseDocument(boolean isValidating, HandlerBase handler, File f) {
		try {
			// Get a "parser factory", an an object that creates parsers
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

			// Set up the factory to create the appropriate type of parser
			saxParserFactory.setValidating(isValidating);
			saxParserFactory.setNamespaceAware(false); // Not this month...

			SAXParser parser = saxParserFactory.newSAXParser();

			parser.parse(f, handler);
			return true;
		} catch (Exception ex) {
			System.err.println("Exception: " + ex);
			return false;
		}
	}
	
	/**
	 * Pop tag off of tag stack.
	 */
	private void popTag() {
		_vecTags.removeElementAt(_vecTags.size() - 1);
	}
	
	/**
	 * Push tag onto tag stack.
	 * @param sTag java.lang.String
	 */
	private void pushTag(String sTag) {
		_vecTags.addElement(sTag);
	}
	
	/**
	 * Return tag at top of tag stack. At any particular point in the parse,
	 * this string represents the tag being processed.
	 * @return java.lang.String
	 */
	private String sCurrentTag() {
		int iIndex = _vecTags.size() - 1;
		if (iIndex >= 0) {
			return (String)(_vecTags.elementAt(_vecTags.size() - 1));
		} else {
			return null;
		}
	}
	
	/**
	 * startDocument method comment.
	 */
	public void startDocument() throws org.xml.sax.SAXException {
	}
	/**
	 * Call all start methods for this tag.
	 */
	public void startElement(String sTag, AttributeList alAttrs) {
		int i;
		String sStartMethodName = "start" + sTag;

		pushTag(sTag);

		// Call every tag start method for this tag found in the list of handlers.
		for (i = 0; i < _vecHandlers.size(); i++) {
			Object oThisHandler = _vecHandlers.elementAt(i);
			Method mStartMethod = mFindMethod(oThisHandler, sStartMethodName, _caAttrList);
			if (mStartMethod == null) {
				mStartMethod = mFindMethod(oThisHandler, sStartMethodName, _caNoArgs);
			}
			if (mStartMethod != null) {
				try {
					// Call start method with or without attribute list
					Class[] caMethodArgs = mStartMethod.getParameterTypes();
					if (caMethodArgs.length == 0) {
						mStartMethod.invoke(oThisHandler, new Object[] {});
					} else {
						mStartMethod.invoke(oThisHandler, new Object[] {alAttrs});
					}
				} catch (InvocationTargetException ex) {
					System.err.println(ex);
				} catch (IllegalAccessException ex) {
					System.err.println(ex);
				}
			}
		}
	}
	
	/**
	 * warning method comment.
	 */
	public void warning(SAXParseException ex) throws SAXException {
		_seh.warning(ex);
	}
}