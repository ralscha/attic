import java.io.*;
import java.net.*;

public class URLTest
{

    public URLTest(String tourl)
    {
        try
        {
            URL goURL = new URL(tourl);

             BufferedReader d = new BufferedReader(new TagFilterReader(new InputStreamReader(goURL.openStream())));

            String inputLine;

            while ((inputLine = d.readLine()) != null)
            {
                System.out.println(inputLine);
            }
            d.close();

        }
        catch (MalformedURLException e)
        {
            System.out.println("URL ist nicht korrekt:"+e);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }
    }

    public static void main(String args[])
    {
        if (args.length == 1)
    	    new URLTest(args[0]);
    	else
    	    System.out.println("Adresse angeben");
    }



}