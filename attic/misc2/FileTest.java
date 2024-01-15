import java.io.*;
import java.util.*;

public class FileTest
{

    static final String separator = System.getProperty("file.separator");

    public FileTest(String args[])
    {

        Vector files1 = new Vector();
        Vector files2 = new Vector();

        File t;


        File f1 = new File(args[0]);
        File f2 = new File(args[1]);

        String lst1[] = f1.list();
        String lst2[] = f2.list();

        for (int i = 0; i < lst1.length; i++)
            files1.addElement(lst1[i]);

        for (int i = 0; i < lst2.length; i++)
            files2.addElement(lst2[i]);

        String fileStr;
        long time1, time2;
        Enumeration e = files1.elements();
        while(e.hasMoreElements())
        {
            fileStr = (String)e.nextElement();

            if (files2.contains(fileStr))
            {
                time1 = new File(args[0]+separator+fileStr).lastModified();
                time2 = new File(args[1]+separator+fileStr).lastModified();
                if (time1 < time2)
                    System.out.println(args[1]+separator+fileStr+" ist neuer");
                else if (time1 > time2)
                    System.out.println(args[0]+separator+fileStr+" ist neuer");
                else
                    System.out.println(fileStr+" sind beide gleich alt");
            }
        }
    }

    public static void main(String args[])
    {
        if (args.length == 2)
            new FileTest(args);
        else
            System.out.println("Aufruf: java FileTest <dir1> <dir2>");
    }

}
