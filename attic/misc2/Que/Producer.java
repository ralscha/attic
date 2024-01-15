import java.io.*;

class Producer extends Thread
{
    private PipedWriter pw = null;

    public Producer()
    {
        pw = new PipedWriter();
    }

    public PipedWriter getPipedWriter()
    {
        return (pw);
    }

    public void run()
    {
        if (pw != null)
        {
            PrintWriter print = new PrintWriter(pw);
            for (int i = 0; i < 1000; i++)
            {
                print.println(String.valueOf(i));
                System.out.println("Producer " + i);
            }
            System.out.println("EXIT PRODUCER");
            try
            {
                pw.close();
            }
            catch (IOException e)
            {
                System.err.println("Produer run: " + e);
            }
        }
    }

}