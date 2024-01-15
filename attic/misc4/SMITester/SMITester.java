
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SMITester extends Frame implements Runnable
{
    Thread runner;

    public SMITester()
    {
        super("SMITester");
        addWindowListener(new Exit());

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        dispatchEvent(new WindowEvent(SMITester.this, WindowEvent.WINDOW_CLOSING));
                                    }
                                });
        add(exitButton);
        setSize(100,100);
        setVisible(true);

    }

    public void start()
    {
        runner = new Thread(this);
        runner.setPriority(Thread.MIN_PRIORITY);
        runner.start();
    }

    public void run()
    {
        double last = 5840.31;
        double high = 5840.31;
        double low  = 5840.31;
        double zu;
        int h;
        Random r = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        String lastStr = String.valueOf(last);
        String highStr = String.valueOf(high);
        String lowStr = String.valueOf(low);

        String update = "08:00";
        int hour = 8;
        int minutes = 0;

        while((Thread.currentThread() == runner))
        {
            /*
            System.out.println(update+" "+lastStr+" "+highStr+" "+lowStr);
            */
            try
            {
                HTMLProducer hp = new HTMLProducer("smi.txt");
                hp.addVariable("last", lastStr);
                hp.addVariable("high", highStr);
                hp.addVariable("low", lowStr);
                hp.addVariable("update", update);
                hp.printHTML("d:\\cafe\\projects\\SMITicker\\smi.txt");
                hp=null;
            }
            catch (IOException ioe)
            {
                System.out.println(ioe);
                System.exit(0);
            }

            minutes++;
            if (minutes > 59)
            {
                hour++;
                minutes = 0;
            }
            sb.setLength(0);
            if (hour < 10)
                sb.append('0');
            sb.append(String.valueOf(hour));
            sb.append(':');
            if (minutes < 10)
                sb.append('0');
            sb.append(String.valueOf(minutes));
            update = sb.toString();

            zu = r.nextDouble();
            if (zu > 0.4223)
                last = last + 10.0 + (zu*3.0);
            else
                last = last - (10.0 + (zu*3.0));

            lastStr = String.valueOf(last).substring(0,7);
            last = Double.valueOf(lastStr).doubleValue();
            if (last > high)
            {
                high = last;
                highStr = lastStr;
            }
            else if (last < low)
            {
                low  = last;
                lowStr = lastStr;
            }

            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException ie) { }
      	}
    }

    public static void main(String args[])
    {
        SMITester st = new SMITester();
        st.start();
    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }

}

