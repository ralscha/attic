
class SleepThread extends Thread
{
    private Sync s;
    private int no;

    public SleepThread(Sync s, int i)
    {
        this.s = s;
        no = i;
    }

    public void run()
    {
        s.inc();
        System.out.println("Start SleepThread #"+no);

        try
        {
            sleep((int)(Math.random() * 1000));
        }
        catch (InterruptedException e) { }

        System.out.println("End SleepThread #"+no);
        s.dec();
    }

}