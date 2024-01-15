import java.util.*;


public class SMIServerEvent extends EventObject
{
    Hashtable ht;
    public SMIServerEvent(Object source, Hashtable ht)
    {
        super(source);
        this.ht = (Hashtable)ht.clone();
    }

    public String getTime()
    {
        return((String)ht.get("Time"));
    }

    public String getLast()
    {
        return((String)(ht.get("Last")));
    }

    public String getLow()
    {
        return((String)(ht.get("Low")));
    }

    public String getHigh()
    {
        return((String)(ht.get("High")));
    }


    public String getOpen()
    {
        return((String)(ht.get("Open")));
    }

    public String getPrev()
    {
         return((String)(ht.get("Prev")));
    }

    public String getNetChg()
    {
        return((String)(ht.get("NetChg")));
    }

    public String getPctChg()
    {
        return((String)(ht.get("PctChg")));
    }


}