import java.io.*;

class Consumer extends Thread
{
    private PipedReader pr = null;

    public Consumer(PipedWriter pw)
    {
        try
        {
            this.pr = new PipedReader(pw);
        }
        catch (IOException e)
        {
            System.err.println("Consumer constructor : " + e);
        }

    }

    public void run()
    {
        if (pr != null)
        {
            try
            {
               BufferedReader br = new BufferedReader(pr);
               String str;

               while ((str = br.readLine()) != null)
               {
                    System.out.println("Consumer " + str);
               }
               System.out.println("EXIT CONSUMER");
               br.close();
               pr.close();
            }
            catch (IOException e)
            {
                System.err.println("Consumer run: " + e);
            }
        }
    }
}