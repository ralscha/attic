import jgl.Queue;

public class SyncQueue extends Queue
{
    private boolean end = false;

    public SyncQueue()
    {
        super();
    }

    public String toString()
    {
        return "SyncQueue( " + super.toString() + " )";
    }

    public synchronized void push(Object object)
    {
        super.push(object);
        notify();
    }

    public synchronized void setEnd()
    {
        end = true;
        notify();
    }

    public synchronized boolean isEnd()
    {
        return end;
    }

    public synchronized Object pop()
    {
        while (isEmpty() && !isEnd())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e) {}
        }
        if (isEnd() && isEmpty()) return(null);
        else return super.pop();
    }


}