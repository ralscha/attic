import java.io.*;
import java.net.*;
import java.util.Date;

public class URLTest3
{

    public URLTest3(String tourl, String to)
    {
        try
        {
            URL goURL = new URL(tourl);
            URLConnection urlConn = null;
            urlConn = goURL.openConnection();

            Date d = new Date(urlConn.getLastModified());
            System.out.println(d);
            int len = urlConn.getContentLength();
            System.out.println(len);

            DataInputStream is = new DataInputStream(urlConn.getInputStream());


            DataOutputStream fos =  new DataOutputStream(new BufferedOutputStream(new FileOutputStream(to)));

            String inputLine;

            while ((inputLine = is.readLine()) != null)
            {
                fos.writeBytes(inputLine);
            }
            is.close();
            fos.close();

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
        if (args.length == 2)
    	    new URLTest3(args[0], args[1]);
    	else
    	    System.out.println("Adresse angeben");
    }



}