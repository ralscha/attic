import java.io.*;
import java.util.*;

public class FileTest2
{

    static final String separator = System.getProperty("file.separator");

    public FileTest2(String args[])
    {
        Vector v = new Vector();
        int z[] = {11,21,31,44,55,66};

        v.addElement(new Tip(10, z));
        for (int i = 0; i < z.length; i++)
            z[i] = z[i]+2;

        v.addElement(new Tip(20, z));
        for (int i = 0; i < z.length; i++)
           z[i] = z[i]+2;

        v.addElement(new Tip(30, z));
        for (int i = 0; i < z.length; i++)
            z[i] = z[i]+2;

        v.addElement(new Tip(40, z));
        for (int i = 0; i < z.length; i++)
            z[i] = z[i]+2;


        try
        {
            FileOutputStream f = new FileOutputStream("test");
        	ObjectOutput s  =  new ObjectOutputStream(f);
	        s.writeObject(v);
    	    s.flush();
    	}
    	catch (IOException e)
    	{
    	    System.out.println(e);
    	}
    }

    public static void main(String args[])
    {
        new FileTest2(args);
    }

}
