import com.ibm.xml.parsers.DOMParser;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class XMLTests {
	public static void main (String args[]) {
		// the first argument should be a filename.
		if (args.length > 0) {
			String filename = args[0];
			try {
				new XMLTests(filename);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	public static void traverseDOMBranch(Node node) {
		// do what you want with this node here...
		System.out.println(node.getNodeName() + ":"+node.getNodeValue());
		if (node.hasChildNodes()) {
			NodeList nl = node.getChildNodes();
			int size = nl.getLength();
			for (int i = 0; i < size; i++) {
				traverseDOMBranch(nl.item(i));
			}
		}
	}

	public XMLTests(String filename) throws Exception {
		DOMParser parser = new DOMParser();
		Errors errors = new Errors();
		parser.setErrorHandler(errors);
		parser.parse(filename);
		//*** The document is the root of the DOM tree.
		Document document = parser.getDocument();

		Element root = (Element)document.getDocumentElement();
		traverseDOMBranch(root);

	}

	class Errors implements ErrorHandler {
		public void warning(SAXParseException ex) {
			System.err.println(ex);
		}
		public void error(SAXParseException ex) {
			System.err.println(ex);
		}
		public void fatalError(SAXParseException ex) throws SAXException {
			System.err.println(ex);
		}
	}
}