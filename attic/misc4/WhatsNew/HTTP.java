import java.net.*;
import java.io.*;

//-------------------------------------------------------------------
// Class HTTP contains some high-level methods to download Web pages.
//-------------------------------------------------------------------

class HTTP
{

    public final static int HTTP_PORT = 80;     // well-known WWW port

    DataInputStream in;         // the enhanced textline input stream

    //-------------------------------------------------------------------
    // Given a valid page URL, grab it off the Net and return it as a single
    // String.
    //-------------------------------------------------------------------
    public String downloadWWWPage(URL pageURL)
    {
        String host, file;
        InputStream pageStream = null;

        host = pageURL.getHost();
        file = pageURL.getFile();

        //System.out.println("Host to contact: '" + host +"'");
        //System.out.println("File to fetch  : '" + file +"'");
        try
        {
            pageStream = getWWWPageStream(host, file);
            if (pageStream == null)
                return "";
        }
        catch (Exception error)
        {
            System.out.println("get(host, file) failed!" + error);
            return "";
        }

        DataInputStream in = new DataInputStream(pageStream);
        StringBuffer pageBuffer = new StringBuffer();
        String line;

        try
        {
            while ((line = in.readLine()) != null)
            {
                // System.out.println(line);
                pageBuffer.append(line);
            }
            in.close();
        }
        catch (Exception error)
        {
            System.out.println("IOException: " + error);
        }

        return pageBuffer.toString();
    }

    //-------------------------------------------------------------------
    // Given a host and file spec, connect to the Web server and request
    // the file document, read in the HTTP response and return an inputstream
    // containing the requested Web document
    //-------------------------------------------------------------------
    public InputStream getWWWPageStream (String host, String file)
                            throws IOException, UnknownHostException
    {

        Socket          httpPipe;   // the TCP socket to the Web server
        InputStream     inn;        // the raw byte input stream from server
        OutputStream    outt;       // the raw byte output stream to server
        PrintStream     out;        // the enhanced textline output stream
        InetAddress     webServer;  // the address of the Web server

        webServer = InetAddress.getByName(host);

        httpPipe = new Socket(webServer, HTTP_PORT);
        if (httpPipe == null)
        {
            System.out.println("Socket to Web server creation failed.");
            return null;
        }

        inn  = httpPipe.getInputStream();    // get raw streams
        outt = httpPipe.getOutputStream();

        in   = new DataInputStream(inn);     // turn into higher-level ones
        out  = new PrintStream(outt);

        if (inn==null || outt==null)
        {
            System.out.println("Failed to open streams to socket.");
            return null;
        }

        // send GET HTTP request
        out.println("GET " + file + " HTTP/1.0\n");

        // read response until blank separator line
        String response;
        while ((response = in.readLine()).length() > 0 )
        {
            System.out.println(response);
        }

        return in;      // return InputStream to allow client to read resource
    }


    public InputStream getWWWHeadStream (String host, String file)
                            throws IOException, UnknownHostException
    {

        Socket          httpPipe;   // the TCP socket to the Web server
        InputStream     inn;        // the raw byte input stream from server
        OutputStream    outt;       // the raw byte output stream to server
        PrintStream     out;        // the enhanced textline output stream
        InetAddress     webServer;  // the address of the Web server

        webServer = InetAddress.getByName(host);

        httpPipe = new Socket(webServer, HTTP_PORT);
        if (httpPipe == null)
        {
            System.out.println("Socket to Web server creation failed.");
            return null;
        }

        inn  = httpPipe.getInputStream();    // get raw streams
        outt = httpPipe.getOutputStream();

        in   = new DataInputStream(inn);     // turn into higher-level ones
        out  = new PrintStream(outt);

        if (inn==null || outt==null)
        {
            System.out.println("Failed to open streams to socket.");
            return null;
        }

        // send GET HTTP request
        out.println("HEAD " + file + " HTTP/1.0\n");

        return in;      // return InputStream to allow client to read resource
    }

} // End of Class HTTP
