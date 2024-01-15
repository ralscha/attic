import java.util.*;

public class ZiehungChoiceEvent extends EventObject
{
    protected int from, to;

    public ZiehungChoiceEvent(Object source, int from, int to)
    {
        super(source);
        this.from = from;
        this.to   = to;
    }

    public int getFrom()
    {
        return(from);
    }

    public int getTo()
    {
        return(to);
    }

}