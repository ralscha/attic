
public class Que3
{
    public static void main(String[] args)
    {
        SleepTh st[] = new SleepTh[20];
        int i;

        for (i = 0; i < 20; i++)
        {
            st[i] = new SleepTh(i);
            st[i].start();
        }

        System.out.println("Gehe schlafen");

        try
        {
            for (i = 0; i < 20; i++)
            {
                st[i].join();
                System.out.println("join "+i);
            }
        }
        catch (InterruptedException e) { }


        System.out.println("Alle Threads beendet");
    }


}

class SleepTh extends Thread
{
    int no;

    public SleepTh(int no)
    {
        super();
        this.no = no;
    }

    public void run()
    {
        System.out.println("Start SleepTh #"+no);

        try
        {
            sleep((int)(Math.random() * 10000));
        }
        catch (InterruptedException e) { }

        System.out.println("End SleepTh #"+no);
    }

}
