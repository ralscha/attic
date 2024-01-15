
import java.io.*;
import java.util.*;

public class HTMLProducer
{
    private Hashtable variables = new Hashtable();
    private BufferedReader br1 = null;
    private BufferedReader br2 = null;

    public HTMLProducer(String fileName) throws FileNotFoundException
    {
        br1 = new BufferedReader(new FileReader(fileName));
    }

    public HTMLProducer(String fileName1, String fileName2) throws FileNotFoundException
    {
        br1 = new BufferedReader(new FileReader(fileName1));
        br2 = new BufferedReader(new FileReader(fileName2));
    }


    public void addVariable(String varName, String value)
    {
        variables.put(varName, value);
    }

    public void addVariable(String varName, int value)
    {
        variables.put(varName, String.valueOf(value));
    }


    public void printHTML(String outFileName)
    {
        PrintWriter pr = null;
        try
        {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(outFileName)));
            printHTML(pr);
            pr.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    public void printHTML(PrintWriter pr)
    {
        Enumeration e;
        String line;
        String key;
        StringBuffer sb = new StringBuffer();
        Hashtable intht = new Hashtable();
        int ix;

        e = variables.keys();
        while(e.hasMoreElements())
        {
            key = (String)e.nextElement();
            sb.setLength(0);
            sb.append("%%");
            sb.append(key);
            sb.append("%%");
            intht.put(sb.toString(), key);
        }

        try
        {
            if (br1 != null)
            {
                while((line = br1.readLine()) != null)
                {
                    if (line.indexOf("%%") != -1)
                    {
                        e = intht.keys();
                        while(e.hasMoreElements() && (line.indexOf("%%") != -1))
                        {
                            key = (String)e.nextElement();

                            while((ix = line.indexOf(key)) != -1)
                            {
                                sb.setLength(0);
                                sb.append(line.substring(0, ix));
                                sb.append(variables.get(intht.get(key)));
                                sb.append(line.substring(ix+key.length()));
                                line = sb.toString();
                            }
                        }
                    }
                    pr.println(line);
                }
                br1.close();
            }
            if (br2 != null)
            {
                while((line = br2.readLine()) != null)
                {
                    if (line.indexOf("%%") != -1)
                    {
                        e = intht.keys();
                        while(e.hasMoreElements() && (line.indexOf("%%") != -1))
                        {
                            key = (String)e.nextElement();

                            while((ix = line.indexOf(key)) != -1)
                            {
                                sb.setLength(0);
                                sb.append(line.substring(0, ix));
                                sb.append(variables.get(intht.get(key)));
                                sb.append(line.substring(ix+key.length()));
                                line = sb.toString();
                            }
                        }
                    }
                    pr.println(line);
                }
                br2.close();
            }

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }


    public static void main(String args[])
    {
        if (args.length == 2)
        {
            try
            {
                HTMLProducer hp = new HTMLProducer(args[0]);
                hp.addVariable("pagetitle", "Die ist der Seitentitel");
                hp.addVariable("title", "Dies ist der H2 Titel");
                hp.addVariable("TEST1", "T1");
                hp.addVariable("TEST2", "T2");
                hp.addVariable("TEST3", "T3");
                hp.printHTML(args[1]);
            }
            catch (FileNotFoundException fe)
            {
                System.out.println("File "+args[0]+" not found");
            }
        }
        else
            System.out.println("Aufruf: java HTMLProducer <templatefile> <outputfile>");
    }
}
