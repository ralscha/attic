import java.io.*;
import java.util.*;


public class InTest
{
    public static void main(String args[])
    {
        DataInputStream dis;
        DataOutputStream dos;
        File mvsfile;
        WordList wl = new WordList();
        Words12 w12 = new Words12();
        BeginList bl = new BeginList();
        String input, help;
        Vector words, hash, files;
        int ix;

        Integer endmark = new Integer(0);


        if (args.length >= 1)
        {
            mvsfile = new File("mvs.dat");
            if (mvsfile.exists())
            {
                try
                {
                    dis = new DataInputStream(new BufferedInputStream(new FileInputStream(mvsfile)));
                    w12.load(dis);
                    bl.load(dis);
                    wl.load(dis);
                    dis.close();
                }
                catch (IOException e)
                {
                   System.err.println("InTest: " + e);
                }

            }

            if (args[0].equals("G"))
            {
                String anfang;
                StringBuffer line = new StringBuffer();
                Integer a1 = new Integer(0);
                Integer a2 = new Integer(0);
                Integer next;

                if (mvsfile.exists())
                {
                    bl.mc();
                    a1 = bl.getA1();
                    a2 = bl.getA2();
                    if (a1 != null)
                    {
                        line.append(wl.getWord(a1)).append(" ").append(wl.getWord(a2)).append(" ");
                        next = w12.getNext(a1, a2);

                        while (!next.equals(endmark))
                        {
                            line.append(wl.getWord(next)).append(" ");
                            if (line.length() > 60)
                            {
                                System.out.println(line);
                                line.setLength(0);
                            }
                            a1 = a2;
                            a2 = next;
                            next = w12.getNext(a1, a2);
                        }
                        System.out.println(line);
                    }
                }
                else
                {
                    System.out.println("Noch keine Daten");
                }
                System.exit(0);
            }

            else
            {
                files = new Vector();

                if (args[0].equals("all"))
                {
                    File f = new File(".");
                    String lst[] = f.list();

                    for (int i = 0; i < lst.length; i++)
                    {
                        if (lst[i].endsWith(".txt"))
                            files.addElement(lst[i]);
                    }

                }
                else
                {
                    for (int i = 0; i < args.length; i++)
                        files.addElement(args[i]);
                }

                for (int i = 0; i < files.size(); i++)
                {
                    words = new Vector();
                    hash = new Vector();

                    try
                    {
                        dis = new DataInputStream(new BufferedInputStream(new FileInputStream((String)files.elementAt(i))));

                        System.out.println("Reading ... : " + (String)files.elementAt(i));

                        while ((input = dis.readLine()) != null)
                        {
                            StringTokenizer st = new StringTokenizer(input);
                            while (st.hasMoreTokens())
                            {
                                help = st.nextToken();
                                if (!help.equals("-"))
                                {
                                    wl.putWord(help);
                                    hash.addElement(new Integer(help.hashCode()));
                                    words.addElement(help);
                                }
                            }
                        }
                    }
                    catch (FileNotFoundException e)
                    {
                        System.err.println("InTest: " + e);
                    }
                    catch (IOException e)
                    {
                        System.err.println("InTest: " + e);
                    }

                    ix = 0;
                    String ws;

                    while (ix+2 < hash.size())
                    {
                        w12.put((Integer)hash.elementAt(ix), (Integer)hash.elementAt(ix+1),
                                 (Integer)hash.elementAt(ix+2));

                        if (ix == 0)
                        {
                            ws = (String)words.elementAt(ix);
                            if (Character.isUpperCase(ws.charAt(0)))
                                bl.put((Integer)hash.elementAt(ix), (Integer)hash.elementAt(ix+1));
                        }
                        else
                        {
                            ws = (String)words.elementAt(ix-1);
                            if ((Character.isUpperCase(ws.charAt(0))) && (ws.endsWith(".")))
                                bl.put((Integer)hash.elementAt(ix), (Integer)hash.elementAt(ix+1));
                        }

                        ix++;
                    }

                    w12.put((Integer)hash.elementAt(ix), (Integer)hash.elementAt(ix+1),
                                 endmark);

                }

                /* Save */
                try
                {
                    dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(mvsfile)));
                    w12.save(dos);
                    bl.save(dos);
                    wl.save(dos);
                    dos.close();
                }
                catch (IOException e)
                {
                    System.err.println("InTest: " + e);
                }
            }
        }
    }
}
