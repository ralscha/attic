
import java.util.*;
import java.io.*;

public class WordList
{
    private Hashtable ht;

    public WordList()
    {
        ht = new Hashtable();
    }

    public void load(DataInputStream dis)
    {
        int no;

        try
        {
            no = dis.readInt();
            for (int i = 0; i < no; i++)
                ht.put(new Integer(dis.readInt()), dis.readUTF());
        }
        catch (IOException e)
        {
            System.err.println("WordList: " + e);
        }

    }

    public void save(DataOutputStream dos)
    {
        Enumeration enum;
        Integer ne;

        try
        {
            enum = ht.keys();
            dos.writeInt(ht.size());

            while(enum.hasMoreElements())
            {
                ne = (Integer)enum.nextElement();
                dos.writeInt(ne.intValue());
                dos.writeUTF((String)ht.get(ne));
            }
            dos.flush();
        }
        catch (IOException e)
        {
            System.err.println("WordList: " + e);
        }
    }

    public String getWord(Integer hashCode)
    {
        return ((String)ht.get(hashCode));
    }

    public void putWord(String word)
    {
        ht.put(new Integer(word.hashCode()), word);
    }

}