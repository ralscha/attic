package ch.ess.common.web;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/** A response wrapper that takes everything the client
 * would normally output and saves it in one big
 * character array.
 */
public class CharArrayWrapper extends HttpServletResponseWrapper {
    //~ Instance fields ========================================================

    private CharArrayWriter charWriter;

    //~ Constructors ===========================================================

    /**
     * Initializes wrapper.
     *
     * <p>First, this constructor calls the parent
     * constructor. That call is crucial so that the response
     * is stored and thus setHeader, setStatus, addCookie,
     * and so forth work normally.</p>
     *
     * <p>Second, this constructor creates a CharArrayWriter
     * that will be used to accumulate the response.</p>
     */
    public CharArrayWrapper(HttpServletResponse response) {
        super(response);
        charWriter = new CharArrayWriter();
    }

    //~ Methods ================================================================

    /**
     * When servlets or JSP pages ask for the Writer,
     * don't give them the real one. Instead, give them
     * a version that writes into the character array.
     * The filter needs to send the contents of the
     * array to the client (perhaps after modifying it).
     */
    public PrintWriter getWriter() {
        return (new PrintWriter(charWriter));
    }

    /**
     * Get a String representation of the entire buffer.
     *
     * <p>Be sure <strong>not</strong> to call this method multiple times
     * on the same wrapper. The API for CharArrayWriter
     * does not guarantee that it "remembers" the previous
     * value, so the call is likely to make a new String
     * every time.</p>
     */
    public String toString() {
        return (charWriter.toString());
    }

    /** Get the underlying character array. */
    public char[] toCharArray() {
        return (charWriter.toCharArray());
    }
}
