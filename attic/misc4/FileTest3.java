import java.io.*;
import java.util.*;

public class FileTest3
{

    public FileTest3(String args[])
    {
        Vector v;
        int z[];
        try
        {
            FileInputStream in = new FileInputStream("test");
        	ObjectInputStream s = new ObjectInputStream(in);
            v = (Vector)s.readObject();

            Enumeration e = v.elements();
            while (e.hasMoreElements())
            {
                Tip t = (Tip)e.nextElement();
               	System.out.println(t.getNr());
                z = t.getZahlen();
                for (int i = 0; i < z.length; i++)
                	System.out.print(z[i]+"   ");
                System.out.println();
                System.out.println("------------");
            }
            System.out.println("ENDE");
    	}
    	catch (ClassNotFoundException e)
    	{
    	    System.out.println(e);
    	}
    	catch (IOException e)
    	{
    	    System.out.println(e);
    	}
    }

    public static void main(String args[])
    {
        new FileTest3(args);
    }

}
