
public class Que2
{
    public static void main(String[] args)
    {
        Sync s = new Sync();

        for (int i = 0; i < 20; i++)
            new SleepThread(s, i).start();

        System.out.println("Gehe schlafen");
        s.waiting();
        System.out.println("Alle Threads beendet");
    }
}