
public class Sync
{

    private int nothreads = 0;

    public synchronized void inc()
    {
        nothreads++;
    }

    public synchronized void dec()
    {
        nothreads--;
        if (nothreads == 0)
            notify();
    }

    public synchronized int get()
    {
        return nothreads;
    }

    public synchronized void waiting()
    {
        while (get() > 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e) {}
        }
    }

}