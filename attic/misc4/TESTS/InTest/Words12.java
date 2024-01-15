
import java.util.*;
import java.io.*;

public class Words12
{
    private Hashtable ht;

    public Words12()
    {
        ht = new Hashtable();
    }

    public void load(DataInputStream dis)
    {
        int no;
        Word3 w3;
        String key;

        try
        {
            no = dis.readInt();
            for (int i = 0; i < no; i++)
            {
                key = dis.readUTF();
                w3 = new Word3();
                w3.load(dis);
                ht.put(key, w3);
            }
        }
        catch (IOException e)
        {
            System.err.println("Words12: " + e);
        }

    }

    public void save(DataOutputStream dos)
    {
        Enumeration enum;
        String key;
        Word3 w3;

        try
        {
            enum = ht.keys();
            dos.writeInt(ht.size());

            while(enum.hasMoreElements())
            {
                key = (String)enum.nextElement();
                dos.writeUTF(key);
                w3 = (Word3)ht.get(key);
                w3.save(dos);
            }
        }
        catch (IOException e)
        {
            System.err.println("Words12: " + e);
        }
    }

    public Integer getNext(Integer w1, Integer w2)
    {
        Word3 word3;
        String key = w1.toString()+w2.toString();

        word3 = (Word3)ht.get(key);
        if (word3 != null)
        {
             return (word3.get());
        }
        else
        {
            return (null);
        }
    }

    public void put(Integer w1, Integer w2, Integer w3)
    {
        Word3 word3;
        String key = w1.toString()+w2.toString();

        if (ht.containsKey(key))
        {
            word3 = (Word3)ht.get(key);
            word3.put(w3);
            ht.put(key, word3);
        }
        else
        {
            word3 = new Word3();
            word3.put(w3);
            ht.put(key, word3);
        }
    }

}