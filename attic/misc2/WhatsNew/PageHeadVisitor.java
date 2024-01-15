import java.util.*;
import java.io.*;
import java.net.*;

public class PageHeadVisitor extends Thread
{

    private final static int MAX_THREADS = 4;
    static CrowdController threadLimiter = new CrowdController(MAX_THREADS);
    URL pageToFetch;


    public PageHeadVisitor (String pageAddress)
    {
        try
        {
            pageToFetch = new URL(pageAddress);
            setName(pageAddress);
            start();
        }
        catch (MalformedURLException badURL)
        {
            System.out.println(pageAddress + " is bad URL.. not starting thread for this one!");
        }
    }

    public void run()
    {
        int ticket;
        String webPage;
        String host;
        String file;

        try
        {
            ticket = threadLimiter.getTicket();

            host = pageToFetch.getHost();
            file = pageToFetch.getFile();

            HTTP http = new HTTP();
            DataInputStream in = new DataInputStream(http.getWWWHeadStream(host, file));

            System.out.println(getName() + ";" + in.readLine());

            threadLimiter.returnTicket(ticket);
        }
        catch (IOException error)
        {
            System.out.println("IOException: " + error);
        }

    }

}

