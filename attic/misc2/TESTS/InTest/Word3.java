
import java.util.*;
import java.io.*;

public class Word3
{
    private int total;
    private Hashtable w3ht;

    public Word3()
    {
        w3ht = new Hashtable();
        total = 0;
    }

    public void load(DataInputStream dis)
    {
        int no;
        try
        {
            total = dis.readInt();
            no = dis.readInt();
            for (int i = 0; i < no; i++)
                w3ht.put(new Integer(dis.readInt()), new Integer(dis.readInt()));
        }
        catch (IOException e)
        {
            System.err.println("Word3: " + e);
        }

    }

    public void save(DataOutputStream dos)
    {
        Enumeration enum;
        Integer ne;

        try
        {
            enum = w3ht.keys();
            dos.writeInt(total);
            dos.writeInt(w3ht.size());

            while(enum.hasMoreElements())
            {
                ne = (Integer)enum.nextElement();
                dos.writeInt(ne.intValue());
                dos.writeInt(((Integer)w3ht.get(ne)).intValue());
            }
        }
        catch (IOException e)
        {
            System.err.println("WordList: " + e);
        }
    }

    public Integer get()
    {
        /* Monte-Carlo */
        Enumeration enum;
        Random r = new Random();
        int rnd;
        Integer key = null;
        int t, sum;

        sum = 0;
        rnd = Math.abs((r.nextInt() % total) + 1);

        enum = w3ht.keys();
        while((enum.hasMoreElements()) && (sum <= rnd))
        {
            key = (Integer)enum.nextElement();
            t = ((Integer)w3ht.get(key)).intValue();
            sum += t;
        }

        return (key);
    }

    public void put(Integer wordhashcode)
    {
        total++;

        if (w3ht.containsKey(wordhashcode))
        {
            Integer no = (Integer)w3ht.get(wordhashcode);
            w3ht.put(wordhashcode, new Integer(no.intValue() + 1));
        }
        else
        {
            w3ht.put(wordhashcode, new Integer(1));
        }
    }
}