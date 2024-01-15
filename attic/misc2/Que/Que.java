
import java.io.*;

public class Que
{
    public static void main(String[] args)
    {

        Producer p1 = new Producer();
        Consumer c1 = new Consumer(p1.getPipedWriter());

        p1.start();
        System.out.println("WAIT");
        try {Thread.sleep(5000);}
        catch (InterruptedException e)
        { System.err.println("main " + e); }
        c1.start();
    }
}