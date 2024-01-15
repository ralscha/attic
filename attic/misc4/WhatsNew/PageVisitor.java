import java.util.*;
import java.io.*;
import java.net.*;

public class PageVisitor extends Thread
{

    private final static int MAX_THREADS = 4;

    // The following CrowdController object is critical to the operation
    // of the entire program: it enforces a maximum number of entities (here:
    // threads). The object is declared static so that only one instance is ever
    // created. All PageVisitor threads therefore have one single "cop" which
    // limits the number of threads (without this cop, the recursive algorithm
    // and hypertext structure of the Web would lead to an exponential explosion
    // in Threads.. a situation which would quickly lead to program failure.

    static CrowdController threadLimiter = new CrowdController( MAX_THREADS );


    // The following global hashtable is declared static so that only one
    // instance is ever created. All PageVisitor threads therefore access this
    // same hashtable which acts as a database of page URLs already encountered.

    static Hashtable pageDatabase = new Hashtable();    // empty to begin with


    URL pageToFetch;            // communication var between go(..) and run()

    //-------------------------------------------------------------------
    // Constructor
    // Given a String address of a Web page, transform address to URL object
    // and launch the thread for this page URL. If the URL could not be built,
    // don't even bother creating the thread (silently return).
    //-------------------------------------------------------------------
    public PageVisitor (String pageAddress)
    {
        try
        {
            pageToFetch = new URL(pageAddress);
            setName(pageAddress);           // label this thread with the page name
            start();                        // start the thread at run()
        }
        catch (MalformedURLException badURL)
        {
            System.out.println(pageAddress + " is bad URL.. not starting thread for this one!");
        }
    }

    //-------------------------------------------------------------------
    // The body of the thread (and also the heart of this program)
    // in pseudo-code:
    //
    //   request running permission (by obtaining a ticket)
    //   load pageToFetch
    //   extract hypertext links from page
    //   for all found links do
    //     if new link
    //       record link in database (so that we avoid it next time)
    //       recurse for new page (launch new thread running same algorithm)
    //   release ticket
    //   die (thread terminates)
    //-------------------------------------------------------------------
    public void run()
    {

        int ticket;             // thread can only run for real if it gets a ticket
        String webPage;         // an entire Web page cached in a String
        Vector pageLinks;       // bag to accumulate found URLs in

        // before processing the page (and possibly spawning new threads to
        // process referenced pages), obtain a ticket (i.e. a license) to do
        // so. (getTicket() will block if max number of threads are
        // already running. When another thread releases its ticket, only then
        // will a blocked thread be able to run() for real.

        ticket = threadLimiter.getTicket();
        // System.out.println("Thread '" + getName() + "' got ticket "+ ticket);

        webPage     = loadPage(pageToFetch);
        pageLinks   = extractHyperTextLinks(webPage);

        System.out.println(getName() + " has " + pageLinks.size() + " links.");

        // Now process all found URLs

        Enumeration enum = pageLinks.elements();
        while(enum.hasMoreElements())
        {
            String page = (String) enum.nextElement();

            if (!alreadyVisited(page))
            {
                markAsVisited(page);

                // print hypertext link relationship to console
                System.out.println(getName() + " --> " + page);

                // and recursively start up a new thread to handle new page
                new PageVisitor(page);

            }
            else
            {
                // System.out.println("Already visited: " + page);
            }
        }

        // We're done with our page, so release ticket and let another thread
        // process a page (returning our ticket will un-block some other
        // waiting thread.

        // System.out.println("Thread '" + getName() + "' releases ticket "+ ticket);
        threadLimiter.returnTicket(ticket);

        // insert some brief, random delay here before running off the end of the
        // world (of run()). This randomizes the scheduling of threads waiting to
        // obtain a ticket.

        try
        {
            Thread.sleep( (int) (Math.random()*200) );
        } catch (Exception e) {}
    }

    //-------------------------------------------------------------------
    // Given a valid WWW page URL, fetch and return the page as a big String.
    //-------------------------------------------------------------------
    protected String loadPage(URL page)
    {
        HTTP http;

        http = new HTTP();
        return http.downloadWWWPage(page);
    }

    //-------------------------------------------------------------------
    // Given a String containing a legal HTML page, extract all
    // http://.... -style strings.
    // Return list of links as a Vector of Strings.
    //-------------------------------------------------------------------
    protected Vector extractHyperTextLinks(String page)
    {

        int lastPosition = 0;           // position of "http:" substring in page
        int endOfURL;                   // pos of end of http://........
        String link;                    // the link we're after
        Vector bagOfLinks = new Vector();  // a bag to accumulate interesting links

        while(lastPosition != -1 )
        {
            lastPosition = page.indexOf("http://", lastPosition);

            if (lastPosition != -1)
            {

                endOfURL = page.indexOf(">", lastPosition + 1 );

                // extract found hypertext link
                link = page.substring(lastPosition, endOfURL);
                link = link.trim();
                if (link.endsWith("\""))
                {
                    link = link.substring(0, link.length() - 1 );
                }

                // ignore refereces
                if (link.indexOf("#") != -1)
                {
                    link = link.substring(0, link.indexOf("#"));
                }

                // discard links which point explicitly to images
                if ( link.endsWith(".gif") || link.endsWith(".jpg"))
                {
                    ;
                }
                else
                {    // collect all others
                    bagOfLinks.addElement( link );
                    // System.out.println( link );
                }

                lastPosition++;     // skip current link
            }
        }
        return bagOfLinks;
    }

    //-------------------------------------------------------------------
    // Find out whether a page has already been encountered in the past.
    //-------------------------------------------------------------------
    protected boolean alreadyVisited(String pageAddr)
    {
        return pageDatabase.containsKey(pageAddr);
    }

    //-------------------------------------------------------------------
    // Mark a page as "visited before". Subsequent encounters with this
    // page will then skip it.
    //-------------------------------------------------------------------
    protected void markAsVisited(String pageAddr)
    {
        pageDatabase.put(pageAddr, pageAddr);   // add page to DB
    }

}

