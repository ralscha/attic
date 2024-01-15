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

public class ArticleImpl extends DefaultHandler implements Unmarshallable, Article {

    private String url;
    private boolean zeus_urlSet;
    private String headline_text;
    private boolean zeus_headline_textSet;
    private String source;
    private boolean zeus_sourceSet;
    private String media_type;
    private boolean zeus_media_typeSet;
    private String cluster;
    private boolean zeus_clusterSet;
    private String tagline;
    private boolean zeus_taglineSet;
    private String document_url;
    private boolean zeus_document_urlSet;
    private String harvest_time;
    private boolean zeus_harvest_timeSet;
    private String access_registration;
    private boolean zeus_access_registrationSet;
    private String access_status;
    private boolean zeus_access_statusSet;
    private String id;
    private boolean zeus_idSet;

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

    public ArticleImpl() {
        zeus_urlSet = false;
        zeus_headline_textSet = false;
        zeus_sourceSet = false;
        zeus_media_typeSet = false;
        zeus_clusterSet = false;
        zeus_taglineSet = false;
        zeus_document_urlSet = false;
        zeus_harvest_timeSet = false;
        zeus_access_registrationSet = false;
        zeus_access_statusSet = false;
        zeus_idSet = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        zeus_urlSet = true;
    }

    public String getHeadline_text() {
        return headline_text;
    }

    public void setHeadline_text(String headline_text) {
        this.headline_text = headline_text;
        zeus_headline_textSet = true;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        zeus_sourceSet = true;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
        zeus_media_typeSet = true;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
        zeus_clusterSet = true;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
        zeus_taglineSet = true;
    }

    public String getDocument_url() {
        return document_url;
    }

    public void setDocument_url(String document_url) {
        this.document_url = document_url;
        zeus_document_urlSet = true;
    }

    public String getHarvest_time() {
        return harvest_time;
    }

    public void setHarvest_time(String harvest_time) {
        this.harvest_time = harvest_time;
        zeus_harvest_timeSet = true;
    }

    public String getAccess_registration() {
        return access_registration;
    }

    public void setAccess_registration(String access_registration) {
        this.access_registration = access_registration;
        zeus_access_registrationSet = true;
    }

    public String getAccess_status() {
        return access_status;
    }

    public void setAccess_status(String access_status) {
        this.access_status = access_status;
        zeus_access_statusSet = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        zeus_idSet = true;
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
        writer.write("<article");

        // Handle attributes (if needed)
        if (zeus_idSet) {
            writer.write(" id=\"");
            writer.write(escapeAttributeValue(id));
            writer.write("\"");
        }
        writer.write(">");
        writer.write("\n");

        // Handle child elements
        if (url != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<url>");
            writer.write(this.url);
            writer.write("</url>\n");
        }

        if (headline_text != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<headline_text>");
            writer.write(this.headline_text);
            writer.write("</headline_text>\n");
        }

        if (source != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<source>");
            writer.write(this.source);
            writer.write("</source>\n");
        }

        if (media_type != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<media_type>");
            writer.write(this.media_type);
            writer.write("</media_type>\n");
        }

        if (cluster != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<cluster>");
            writer.write(this.cluster);
            writer.write("</cluster>\n");
        }

        if (tagline != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<tagline>");
            writer.write(this.tagline);
            writer.write("</tagline>\n");
        }

        if (document_url != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<document_url>");
            writer.write(this.document_url);
            writer.write("</document_url>\n");
        }

        if (harvest_time != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<harvest_time>");
            writer.write(this.harvest_time);
            writer.write("</harvest_time>\n");
        }

        if (access_registration != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<access_registration>");
            writer.write(this.access_registration);
            writer.write("</access_registration>\n");
        }

        if (access_status != null) {
            writer.write(new StringBuffer(indent).append("  ").toString());
            writer.write("<access_status>");
            writer.write(this.access_status);
            writer.write("</access_status>\n");
        }

        writer.write(indent);
        writer.write("</article>\n");
    }

    /** Indicates if we are in the url element */
    private boolean zeus_inUrl = false;

    /** Indicates if we are in the headline_text element */
    private boolean zeus_inHeadline_text = false;

    /** Indicates if we are in the source element */
    private boolean zeus_inSource = false;

    /** Indicates if we are in the media_type element */
    private boolean zeus_inMedia_type = false;

    /** Indicates if we are in the cluster element */
    private boolean zeus_inCluster = false;

    /** Indicates if we are in the tagline element */
    private boolean zeus_inTagline = false;

    /** Indicates if we are in the document_url element */
    private boolean zeus_inDocument_url = false;

    /** Indicates if we are in the harvest_time element */
    private boolean zeus_inHarvest_time = false;

    /** Indicates if we are in the access_registration element */
    private boolean zeus_inAccess_registration = false;

    /** Indicates if we are in the access_status element */
    private boolean zeus_inAccess_status = false;

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

    public static Article unmarshal(File file) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new FileReader(file));
    }

    public static Article unmarshal(File file, boolean validate) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new FileReader(file), validate);
    }

    public static Article unmarshal(InputStream inputStream) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new InputStreamReader(inputStream));
    }

    public static Article unmarshal(InputStream inputStream, boolean validate) throws IOException {
        // Delegate to the unmarshal(Reader) method
        return unmarshal(new InputStreamReader(inputStream), validate);
    }

    public static Article unmarshal(Reader reader) throws IOException {
        // Delegate with default validation value
        return unmarshal(reader, false);
    }

    public static Article unmarshal(Reader reader, boolean validate) throws IOException {
        ArticleImpl article = new ArticleImpl();
        article.setCurrentUNode(article);
        article.setParentUNode(null);
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
            parser.setContentHandler(article);
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
        return article;
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
        if ((localName.equals("article")) && (!zeus_thisNodeHandled)) {
            // Handle ourselves
            for (int i=0, len=atts.getLength(); i<len; i++) {
                String attName= atts.getLocalName(i);
                String attValue = atts.getValue(i);
                if (attName.equals("id")) {
                    setId(attValue);
                }
            }
            zeus_thisNodeHandled = true;
            return;
        } else {
            // Delegate handling
            if (localName.equals("url")) {
                this.zeus_inUrl = true;
                return;
            }
            if (localName.equals("headline_text")) {
                this.zeus_inHeadline_text = true;
                return;
            }
            if (localName.equals("source")) {
                this.zeus_inSource = true;
                return;
            }
            if (localName.equals("media_type")) {
                this.zeus_inMedia_type = true;
                return;
            }
            if (localName.equals("cluster")) {
                this.zeus_inCluster = true;
                return;
            }
            if (localName.equals("tagline")) {
                this.zeus_inTagline = true;
                return;
            }
            if (localName.equals("document_url")) {
                this.zeus_inDocument_url = true;
                return;
            }
            if (localName.equals("harvest_time")) {
                this.zeus_inHarvest_time = true;
                return;
            }
            if (localName.equals("access_registration")) {
                this.zeus_inAccess_registration = true;
                return;
            }
            if (localName.equals("access_status")) {
                this.zeus_inAccess_status = true;
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

        if (localName.equals("url")) {
            this.zeus_inUrl = false;
            return;
        }

        if (localName.equals("headline_text")) {
            this.zeus_inHeadline_text = false;
            return;
        }

        if (localName.equals("source")) {
            this.zeus_inSource = false;
            return;
        }

        if (localName.equals("media_type")) {
            this.zeus_inMedia_type = false;
            return;
        }

        if (localName.equals("cluster")) {
            this.zeus_inCluster = false;
            return;
        }

        if (localName.equals("tagline")) {
            this.zeus_inTagline = false;
            return;
        }

        if (localName.equals("document_url")) {
            this.zeus_inDocument_url = false;
            return;
        }

        if (localName.equals("harvest_time")) {
            this.zeus_inHarvest_time = false;
            return;
        }

        if (localName.equals("access_registration")) {
            this.zeus_inAccess_registration = false;
            return;
        }

        if (localName.equals("access_status")) {
            this.zeus_inAccess_status = false;
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
        if (zeus_inUrl) {
            if (this.url == null) {
                this.url = text;
            } else {
                this.url = new StringBuffer(this.url).append(text).toString();
            }
            return;
        }

        if (zeus_inHeadline_text) {
            if (this.headline_text == null) {
                this.headline_text = text;
            } else {
                this.headline_text = new StringBuffer(this.headline_text).append(text).toString();
            }
            return;
        }

        if (zeus_inSource) {
            if (this.source == null) {
                this.source = text;
            } else {
                this.source = new StringBuffer(this.source).append(text).toString();
            }
            return;
        }

        if (zeus_inMedia_type) {
            if (this.media_type == null) {
                this.media_type = text;
            } else {
                this.media_type = new StringBuffer(this.media_type).append(text).toString();
            }
            return;
        }

        if (zeus_inCluster) {
            if (this.cluster == null) {
                this.cluster = text;
            } else {
                this.cluster = new StringBuffer(this.cluster).append(text).toString();
            }
            return;
        }

        if (zeus_inTagline) {
            if (this.tagline == null) {
                this.tagline = text;
            } else {
                this.tagline = new StringBuffer(this.tagline).append(text).toString();
            }
            return;
        }

        if (zeus_inDocument_url) {
            if (this.document_url == null) {
                this.document_url = text;
            } else {
                this.document_url = new StringBuffer(this.document_url).append(text).toString();
            }
            return;
        }

        if (zeus_inHarvest_time) {
            if (this.harvest_time == null) {
                this.harvest_time = text;
            } else {
                this.harvest_time = new StringBuffer(this.harvest_time).append(text).toString();
            }
            return;
        }

        if (zeus_inAccess_registration) {
            if (this.access_registration == null) {
                this.access_registration = text;
            } else {
                this.access_registration = new StringBuffer(this.access_registration).append(text).toString();
            }
            return;
        }

        if (zeus_inAccess_status) {
            if (this.access_status == null) {
                this.access_status = text;
            } else {
                this.access_status = new StringBuffer(this.access_status).append(text).toString();
            }
            return;
        }

    }

}
