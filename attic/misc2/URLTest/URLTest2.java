import java.io.*;
import java.net.*;

public class URLTest2
{

    public URLTest2(String tourl)
    {
        try
        {
            Socket goSocket = new Socket(tourl, 80);

            PrintWriter out = new PrintWriter(goSocket.getOutputStream());
            BufferedReader d = new BufferedReader(new InputStreamReader(goSocket.getInputStream()));
            out.println("GET /Ralph/Gifs/java44.gif HTTP/1.0");
            out.println();
            out.flush();

            String inputLine;

            while ((inputLine = d.readLine()) != null)
            {
                System.out.println(inputLine);
            }
            d.close();

        }
        catch (UnknownHostException e)
        {
            System.out.println("Host ist nicht bekannt:"+e);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }
    }

    public static void main(String args[])
    {
        new URLTest2("home.inm.ch");
    }



}