import java.net.*;
import java.io.*;
import java.util.*;

public class WhatsNew
{
    Vector urlList;
    static String urlfile = "url.txt";

    void loadURLfile()
    {
        String line;
        urlList = new Vector();

        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(urlfile));

            while((line = in.readLine()) != null)
            {
                urlList.addElement(line);
            }

            in.close();
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println(urlfile + " not found");
        }
        catch (IOException error)
        {
            System.out.println("IOException: " + error);
        }


    }

    public WhatsNew()
    {

        loadURLfile();

        Enumeration enum = urlList.elements();
        while(enum.hasMoreElements())
        {
            String page = (String) enum.nextElement();
            new PageHeadVisitor(page);
        }
    }

    public static void main (String[] args)
    {
        new WhatsNew();
    }

}