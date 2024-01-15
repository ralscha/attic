import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Location
{

    private final String ip2llhost = "cello.cs.uiuc.edu";

    public Location(String host)
    {
        String inputLine;
        StringTokenizer st;
        String token;
        String ll;
        String city;

        try
        {
            Socket ip2llsocket = new Socket(ip2llhost, 80);

            PrintStream out = new PrintStream(ip2llsocket.getOutputStream());
            DataInputStream is = new DataInputStream(ip2llsocket.getInputStream());

            System.err.println("Please wait...");

            out.println("GET /cgi-bin/slamm/ip2ll?name="+host+" HTTP/1.0");
            out.println();
            out.flush();

            inputLine = is.readLine();

            if (inputLine != null)
            {
                st = new StringTokenizer(inputLine, " ");
                token = st.nextToken(); /* HTTP Version */
                token = st.nextToken(); /* Status code */


                if (token.charAt(0) == '2')
                {
                    inputLine = is.readLine();


                    while (!inputLine.startsWith("<HR>"))
                    {
                        inputLine = is.readLine();
                    }

                    inputLine = is.readLine();
                    if ((inputLine.length() == 0) || (inputLine.startsWith("Unknown Domain")))
                        System.out.println(host+" not found");
                    else
                    {
                        ll = inputLine;
                        inputLine = is.readLine();
                        city = inputLine;

                        ll = removeTags(ll);
                        city = removeTags(city);

                        System.out.println(city);
                        System.out.println(ll);
                    }
                }

            }

            is.close();
            out.close();
            ip2llsocket.close();

        }
        catch (UnknownHostException e)
        {
            System.out.println("host unknown: "+e);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }
    }

    String removeTags(String str)
    {
        StringBuffer help = new StringBuffer(str.length());;
        char hc;
        int i = 0;

        while (i < str.length())
        {
            hc = str.charAt(i);

            if (hc == '<')
            {
                if (i > 0)
                    help.append(" ");

                while(hc != '>')
                {
                    i++;
                    hc = str.charAt(i);
                }
            }
            else
            {
                help.append(hc);
            }

            i++;
        }

        return (help.toString());
    }


    public static void main(String args[])
    {
        if (args.length == 1)
            new Location(args[0]);
        else
            System.out.println("Start: java Location host");
    }



}