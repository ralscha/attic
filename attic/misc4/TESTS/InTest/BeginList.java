
import java.util.*;
import java.io.*;


public class BeginList
{
    private int total;
    private Hashtable ht;

    Integer a1, a2;

    public BeginList()
    {
        ht = new Hashtable();
        total = 0;
    }

    public void load(DataInputStream dis)
    {
        int no;
        String key;
        Union un;

        try
        {
            total = dis.readInt();
            no = dis.readInt();
            for (int i = 0; i < no; i++)
            {
                key = dis.readUTF();
                un = new Union();
                un.load(dis);
                ht.put(key, un);
            }
        }
        catch (IOException e)
        {
            System.err.println("BeginList: " + e);
        }

    }

    public void save(DataOutputStream dos)
    {
        Enumeration enum;
        String key;

        try
        {
            enum = ht.keys();
            dos.writeInt(total);
            dos.writeInt(ht.size());

            while(enum.hasMoreElements())
            {
                key = (String)enum.nextElement();
                dos.writeUTF(key);
                ((Union)ht.get(key)).save(dos);
            }
        }
        catch (IOException e)
        {
            System.err.println("BeginList: " + e);
        }
    }



    public void mc()
    {
        /* Monte-Carlo */
        Enumeration enum;
        Random r = new Random();
        int rnd;
        Union un = null;
        String key;
        int t, sum;

        sum = 0;
        rnd = Math.abs((r.nextInt() % total) + 1);

        enum = ht.keys();
        while((enum.hasMoreElements()) && (sum < rnd))
        {
            key = (String)enum.nextElement();
            un = (Union)ht.get(key);
            t = un.getNumber();
            sum += t;
        }

        if (un != null)
        {
            a1 = un.getHash1();
            a2 = un.getHash2();
        }
        else
        {
            a1 = null;
            a2 = null;
        }
    }

    public Integer getA1()
    {
        return (a1);
    }

    public Integer getA2()
    {
        return (a2);
    }

    public void put(Integer w1, Integer w2)
    {
        total++;
        String key = w1.toString()+w2.toString();

        if (ht.containsKey(key))
        {
            Union elem = (Union)ht.get(key);
            elem.inc();
            ht.put(key, elem);
        }
        else
        {
            ht.put(key, new Union(w1, w2));
        }
    }
}


class Union
{
    Integer hash1, hash2;
    int number;

    Union()
    {
    }

    Union(Integer h1, Integer h2)
    {
        hash1 = h1;
        hash2 = h2;
        number = 1;
    }

    void inc()
    {
        number++;
    }

    int getNumber()
    {
        return (number);
    }

    Integer getHash1()
    {
        return (hash1);
    }

    Integer getHash2()
    {
        return (hash2);
    }

    void save(DataOutputStream dos)
    {
        try
        {
            dos.writeInt(hash1.intValue());
            dos.writeInt(hash2.intValue());
            dos.writeInt(number);
        }
        catch (IOException e)
        {
            System.err.println("Union : " + e);
        }
    }

    void load(DataInputStream dis)
    {
        try
        {
            hash1 = new Integer(dis.readInt());
            hash2 = new Integer(dis.readInt());
            number = dis.readInt();
        }
        catch (IOException e)
        {
            System.err.println("Union : " + e);
        }
    }

}
