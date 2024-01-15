import java.io.*;
import java.net.*;
import java.util.*;

public class LottoExtractor
{

    private final static String lottoHost = "http://194.158.230.224:9090/telenet/CH/281/1.t-html";
    private final static String fileInput = "lottonok.TXT";
    private final static boolean WEB = false;


    private URL            lottoURL;
    private URLConnection  lottoConn;
    private BufferedReader inStream;
    private String         inputLine;

    private int[] zahlen = new int[6];
    private int zz;
    private String datum;
    private String joker;
    private Hashtable quoten;

    public LottoExtractor()
    {
        quoten = new Hashtable();
        update();
        System.out.println(quoten);
    }

    public Hashtable getQuotenHt()
    {
        return(quoten);

    }

    public void update()
    {

        int ix,n;

        try
        {
            lottoURL = new URL(lottoHost);

            if (WEB)
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            else
                inStream = new BufferedReader(new TagFilterReader(new FileReader(fileInput)));

            int s = 0;
            StringTokenizer st;
            while ((inputLine = inStream.readLine()) != null)
            {
                if ((ix = inputLine.indexOf("LOTTO")) != -1)
                {
                    System.out.println(inputLine);
                    n = 0;
                    st = new StringTokenizer(inputLine.substring(ix+5).trim());
                    while (st.hasMoreTokens())
                    {
                        if (n == 6)
                            zz = Integer.parseInt(st.nextToken());
                        else
                            zahlen[n] = Integer.parseInt(st.nextToken());
                        n++;
                    }

                    for (int i = 0; i < 5; i++)
                    {
                        inputLine = inStream.readLine();

                        st = new StringTokenizer(inputLine.trim());
                        while (st.hasMoreTokens())
                        {

                            quoten.put(new Integer(s), st.nextToken());
                            s++;
                        }
                    }

                    inputLine = inStream.readLine(); //Erwartet
                    inputLine = inStream.readLine();
                    st = new StringTokenizer(inputLine.trim());
                    while (st.hasMoreTokens())
                    {
                        quoten.put(new Integer(s), st.nextToken());
                        s++;
                    }

                }
                else if ((ix = inputLine.indexOf("JOKER")) != -1)
                {
                    joker = inputLine.substring(ix+5).trim();

                    for (int i = 0; i < 5; i++)
                    {
                        inputLine = inStream.readLine();
                        st = new StringTokenizer(inputLine.trim());
                        while (st.hasMoreTokens())
                        {
                            quoten.put(new Integer(s), st.nextToken());
                            s++;
                        }
                    }

                    inputLine = inStream.readLine(); //Erwartet
                    inputLine = inStream.readLine();
                    st = new StringTokenizer(inputLine.trim());
                    while (st.hasMoreTokens())
                    {
                        quoten.put(new Integer(s), st.nextToken());
                        s++;
                    }
                }
                else if ((inputLine.indexOf("M I T T W O C H") != -1) ||
                            (inputLine.indexOf("S A M S T A G") != -1))
                {
                    inputLine = inStream.readLine();
                    datum = inputLine.trim();
                }

            }
            inStream.close();
            inStream = null;
            lottoURL = null;
            lottoConn = null;
            inputLine = null;

        }
        catch (MalformedURLException e)
        {
            System.out.println("MalforemdURLException: "+e);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }

    }

    public String getDatum()
    {
        return(datum);
    }

    public int[] getZahlen()
    {
        return zahlen;
    }

    public int getZZ()
    {
        return(zz);
    }

    public String getJoker()
    {
        return(joker);
    }

}