                    
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.xml.sax.*;

public class SAX2Count 
    extends DefaultHandler {

    //
    // Constants
    //

    /** Default parser name. */
    private static final String 
        DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

    //
    // Data
    //

    private static boolean warmup = false;

    /** Elements. */
    private long elements;

    /** Attributes. */
    private long attributes;

    /** Characters. */
    private long characters;

    /** Ignorable whitespace. */
    private long ignorableWhitespace;

    //
    // Public static methods
    //

    /** Prints the output from the SAX callbacks. */
    public static void print(String parserName, String uri, boolean validate) {

        try {
            SAX2Count counter = new SAX2Count();

            XMLReader parser = (XMLReader)Class.forName(parserName).newInstance();
            parser.setContentHandler(counter);
            parser.setErrorHandler(counter);
            if (validate)
                parser.setFeature("http://xml.org/sax/features/validation", true);

            if (warmup) {
                parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
                parser.parse(uri);
                warmup = false;
            }
            long before = System.currentTimeMillis();
            parser.parse(uri);
            long after = System.currentTimeMillis();
            counter.printResults(uri, after - before);
        }
        catch (org.xml.sax.SAXParseException spe) {
            spe.printStackTrace(System.err);
        }
        catch (org.xml.sax.SAXException se) {
            if (se.getException() != null)
                se.getException().printStackTrace(System.err);
            else
                se.printStackTrace(System.err);
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

    } // print(String,String)

    //
    // DocumentHandler methods
    //

    /** Start document. */
    public void startDocument() {

        if (warmup)
            return;

        elements            = 0;
        attributes          = 0;
        characters          = 0;
        ignorableWhitespace = 0;

    } // startDocument()

    /** Start element. */
    public void startElement(String uri, String local, String raw, Attributes attrs) {
        if (warmup)
            return;

        elements++;
        if (attrs != null) {
            attributes += attrs.getLength();
        }

    } // startElement(String,AttributeList)

    /** Characters. */
    public void characters(char ch[], int start, int length) {

        if (warmup)
            return;

        characters += length;

    } // characters(char[],int,int);

    /** Ignorable whitespace. */
    public void ignorableWhitespace(char ch[], int start, int length) {

        if (warmup)
            return;

        ignorableWhitespace += length;

    } // ignorableWhitespace(char[],int,int);

    //
    // ErrorHandler methods
    //

    /** Warning. */
    public void warning(SAXParseException ex) {
        if (warmup)
            return;

        System.err.println("[Warning] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
    }

    /** Error. */
    public void error(SAXParseException ex) {
        if (warmup)
            return;

        System.err.println("[Error] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
    }

    /** Fatal error. */
    public void fatalError(SAXParseException ex) throws SAXException {
        if (warmup)
            return;

        System.err.println("[Fatal Error] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
//        throw ex;
    }

    /** Returns a string of the location. */
    private String getLocationString(SAXParseException ex) {
        StringBuffer str = new StringBuffer();

        String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1) 
                systemId = systemId.substring(index + 1);
            str.append(systemId);
        }
        str.append(':');
        str.append(ex.getLineNumber());
        str.append(':');
        str.append(ex.getColumnNumber());

        return str.toString();

    } // getLocationString(SAXParseException):String

    //
    // Public methods
    //

    /** Prints the results. */
    public void printResults(String uri, long time) {

        // filename.xml: 631 ms (4 elems, 0 attrs, 78 spaces, 0 chars)
        System.out.print(uri);
        System.out.print(": ");
        System.out.print(time);
        System.out.print(" ms (");
        System.out.print(elements);
        System.out.print(" elems, ");
        System.out.print(attributes);
        System.out.print(" attrs, ");
        System.out.print(ignorableWhitespace);
        System.out.print(" spaces, ");
        System.out.print(characters);
        System.out.print(" chars)");
        System.out.println();
    } // printResults(String,long)

    //
    // Main
    //

    /** Main program entry point. */
    public static void main(String argv[]) {

        // is there anything to do?
        if (argv.length == 0) {
            printUsage();
            System.exit(1);
        }

        // vars
        String  parserName = DEFAULT_PARSER_NAME;
        boolean validate = false;

        // check parameters
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];

            // options
            if (arg.startsWith("-")) {
                if (arg.equals("-p")) {
                    if (i == argv.length - 1) {
                        System.err.println("error: missing parser name");
                        System.exit(1);
                    }
                    parserName = argv[++i];
                    continue;
                }

                if (arg.equals("-w")) {
                    warmup = true;
                    continue;
                }

                if (arg.equals("-v")) {
                    validate = true;
                    continue;
                }

                if (arg.equals("-h")) {
                    printUsage();
                    System.exit(1);
                }
            }

            // print uri
            print(parserName, arg, validate);
        }

    } // main(String[])

    /** Prints the usage. */
    private static void printUsage() {

        System.err.println("usage: java sax.SAX2Count (options) uri ...");
        System.err.println();
        System.err.println("options:");
        System.err.println("  -p name  Specify SAX parser by name.");
        System.err.println("           Default parser: "+DEFAULT_PARSER_NAME);
        System.err.println("  -v       Turn on validation.");
        System.err.println("  -w       Warmup the parser before timing.");
        System.err.println("  -h       This help screen.");

    } // printUsage()

} // class SAX2Count
