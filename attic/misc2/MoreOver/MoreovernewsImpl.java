/**
 * This class was generated from a set of XML constraints
 *   by the Enhydra Zeus XML Data Binding Framework. All
 *   source code in this file is constructed specifically
 *   to work with other Zeus-generated classes. If you
 *   modify this file by hand, you run the risk of breaking
 *   this interoperation, as well as introducing errors in
 *   source code compilation.
 *
 * * * * * MODIFY THIS FILE AT YOUR OWN RISK * * * * *
 *
 * To find out more about the Enhydra Zeus framework, you
 *   can point your browser at <http://zeus.enhydra.org>
 *   where you can download releases, join and discuss Zeus
 *   on user and developer mailing lists, and access source
 *   code. Please report any bugs through that website.
 */
// Global Implementation Import Statements
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

// Local Implementation Import Statements
import Article;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class MoreovernewsImpl extends DefaultHandler implements Unmarshallable, Moreovernews {

    private List articleList;

    /** The current node in unmarshalling */
    private Unmarshallable zeus_currentUNode;

    /** The parent node in unmarshalling */
    private Unmarshallable zeus_parentUNode;

    /** Whether this node has been handled */
    private boolean zeus_thisNodeHandled = false;

    /** The EntityResolver for SAX parsing to use */
    private static EntityResolver entityResolver;

    /** The ErrorHandler for SAX parsing to use */
    private static ErrorHandler errorHandler;

    public MoreovernewsImpl() {
        articleList = new LinkedList();
    }

    public List getArticleList() {
        return articleList;
    }

    public void setArticleList(List articleList) {
        this.articleList = articleList;
    }

    public void addArticle(Article article) {
        articleList.add(article);
    }

    public void removeArticle(Article article) {
        articleList.remove(article);
    }

    public void marshal(File file) throws IOException {
        // Delegate to the marshal(Writer) method
        marshal(new FileWriter(file));
    }

    public void marshal(OutputStream outputStream) throws IOException {
        // Delegate to the marshal(Writer) method
        marshal(new OutputStreamWriter(outputStream));
    }

    public void marshal(Writer writer) throws IOException {
        // Write out the XML declaration
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");

        // Now start the recursive writing
        writeXMLRepresentation(writer, "");

        // Close up
        writer.flush();
        writer.close();
    }

    protected void writeXMLRepresentation(Writer writer,
                                          String indent)
        throws IOException {

        writer.write(indent);
        writer.write("<moreovernews");

        // Handle attributes (if needed)
        writer.write(">");
        writer.write("\n");

        // Handle child elements
        for (Iterator i=articleList.iterator(); i.hasNext(); ) {
            ArticleImpl article = (ArticleImpl)i.next();
            article.writeXMLRepresentation(writer,
                new StringBuffer(indent).append("  ").toString());
        }
        writer.write(indent);
        writer.write("</moreovernews>\n");
    }

    private String escapeAttributeValue(String attributeValue) {
        String returnValue = attributeValue;
        for (int i = 0; i < returnValue.length(); i++) {
            char ch = returnValue.charAt(i);
            if (ch == '"') {
                returnValue = new StringBuffer()
                    .append(returnValue.substring(0, i))
                    .append("&quot;")
                    .append(returnValue.substring(i+1))
                    .toString();
            }
        }
        return returnValue;
    }

    /**
     * <p>
     *  This sets a SAX <code>EntityResolver</code> for this unmarshalling process.
     * </p>
     *
     * @param resolver the entity resolver to use.
     */
    public static void setEntityResolver(EntityResolver resolver) {
        entityResolver = resolver;
    }

    /**
     * <p>
     *  This sets a SAX <code>ErrorHandler</code> for this unmarshalling process.
     * </p>
     *
     * @param handler the entity resolver to use.
     */
    public static void setErrorHandler(ErrorHandler handler) {
        errorHandler = handler;
    }

    public static Moreovernews unmarshal(File file) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new FileReader(file));
    }

    public static Moreovernews unmarshal(File file, boolean validate) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new FileReader(file), validate);
    }

    public static Moreovernews unmarshal(InputStream inputStream) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new InputStreamReader(inputStream));
    }

    public static Moreovernews unmarshal(InputStream inputStream, boolean validate) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new InputStreamReader(inputStream), validate);
    }

    public static Moreovernews unmarshal(Reader reader) throws IOException {
        // Delegate with default validation value
        return unmarshal(reader, false);
    }

    public static Moreovernews unmarshal(Reader reader, boolean validate) throws IOException {
        MoreovernewsImpl moreovernews = new MoreovernewsImpl();
        moreovernews.setCurrentUNode(moreovernews);
        moreovernews.setParentUNode(null);
        // Load the XML parser
        XMLReader parser = null;
        String parserClass = System.getProperty("org.xml.sax.driver",
            "org.apache.xerces.parsers.SAXParser");
        try {
            parser = XMLReaderFactory.createXMLReader(parserClass);

            // Set entity resolver, if needed
            if (entityResolver != null) {
                parser.setEntityResolver(entityResolver);
            }

            // Set error handler, if needed
            if (errorHandler != null) {
                parser.setErrorHandler(errorHandler);
            }

            // Register content handler
            parser.setContentHandler(moreovernews);
        } catch (SAXException e) {
            throw new IOException("Could not load XML parser: " + 
                e.getMessage());
        }

        InputSource inputSource = new InputSource(reader);
        try {
            parser.setFeature("http://xml.org/sax/features/validation", new Boolean(validate).booleanValue());
            parser.parse(inputSource);
        } catch (SAXException e) {
            throw new IOException("Error parsing XML document: " + 
                e.getMessage());
        }

        // Return the resultant object
        return moreovernews;
    }

    public Unmarshallable getParentUNode() {
        return zeus_parentUNode;
    }

    public void setParentUNode(Unmarshallable parentUNode) {
        this.zeus_parentUNode = parentUNode;
    }

    public Unmarshallable getCurrentUNode() {
        return zeus_currentUNode;
    }

    public void setCurrentUNode(Unmarshallable currentUNode) {
        this.zeus_currentUNode = currentUNode;
    }

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
        throws SAXException {

        // Feed this to the correct ContentHandler
        Unmarshallable current = getCurrentUNode();
        if (current != this) {
            current.startElement(namespaceURI, localName, qName, atts);
            return;
        }

        // See if we handle, or we delegate
        if ((localName.equals("moreovernews")) && (!zeus_thisNodeHandled)) {
            // Handle ourselves
            for (int i=0, len=atts.getLength(); i<len; i++) {
                String attName= atts.getLocalName(i);
                String attValue = atts.getValue(i);
            }
            zeus_thisNodeHandled = true;
            return;
        } else {
            // Delegate handling
            if (localName.equals("article")) {
                ArticleImpl article = new ArticleImpl();
                current = getCurrentUNode();
                article.setParentUNode(current);
                article.setCurrentUNode(article);
                this.setCurrentUNode(article);
                article.startElement(namespaceURI, localName, qName, atts);
                // Add this value in
                articleList.add(article);
                return;
            }
        }
    }

    public void endElement(String namespaceURI, String localName,
                           String qName)
        throws SAXException {

        Unmarshallable current = getCurrentUNode();
        if (current != this) {
            current.endElement(namespaceURI, localName, qName);
            return;
        }

        Unmarshallable parent = getCurrentUNode().getParentUNode();
        if (parent != null) {
            parent.setCurrentUNode(parent);
        }
    }

    public void characters(char[] ch, int start, int len)
        throws SAXException {

        // Feed this to the correct ContentHandler
        Unmarshallable current = getCurrentUNode();
        if (current != this) {
            current.characters(ch, start, len);
            return;
        }

        String text = new String(ch, start, len);
        text = text.trim();
    }

}
