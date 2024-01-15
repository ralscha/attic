import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import COM.objectspace.voyager.*;

public class SMIServer extends Frame implements Runnable
{
    private final static String stockHost = "http://otf1.tszrh.ska.com/CTIB/OTFSNAP?RIC=.SSMI";
    private final static String fileInput = "c:\\cafe\\projects\\SMIServer\\smi.txt";
    private final static boolean WEB = false;


    private URL            stockURL;
    private URLConnection  stockConn;
    private BufferedReader inStream;
    private String         inputLine;
    private Hashtable hm;
    private int min;
    private Vector listeners = new Vector();
    private Thread runner;
    private List listbox;
    private Vector events;
    private Button startButton;
    private Button exitButton;

    public SMIServer()
    {
        super("SMIServer");
        events = new Vector();

        loadEvents();

        setLayout(new BorderLayout());
        addWindowListener(new Exit());
        listbox = new List();
        listbox.setFont(new Font("Courier", Font.PLAIN, 12));
        add("Center", listbox);

        startButton = new Button("Start");
        exitButton  = new Button("Exit");
        startButton.setFont(new Font("Courier", Font.PLAIN, 10));
        exitButton.setFont(new Font("Courier", Font.PLAIN, 10));

        startButton.addActionListener(new ActionListener()
                                      { public void actionPerformed(ActionEvent a)
                                        { startAction(); }});
        exitButton.addActionListener(new ActionListener()
                                      { public void actionPerformed(ActionEvent a)
                                        { exitAction(); }});

        Panel p = new Panel();
        p.setLayout(new GridLayout(1,2));
        p.add(startButton);
        p.add(exitButton);
        add("South", p);

        setSize(150,260);
        setVisible(true);

        hm = new Hashtable();
        this.min = 1;
        runner = new Thread(this);
        hm.put("Last", new Value(""));
        hm.put("Net Chg", new Value(""));
        hm.put("Pct Chg", new Value(""));
        hm.put("Open", new Value(""));
        hm.put("Close", new Value(""));
        hm.put("Close Date", new Value(""));
        hm.put("High", new Value(""));
        hm.put("Life High", new Value(""));
        hm.put("Date Life High", new Value(""));
        hm.put("Low", new Value(""));
        hm.put("Life Low", new Value(""));
        hm.put("Date Life Low", new Value(""));
        hm.put("Year High", new Value(""));
        hm.put("Year Low", new Value(""));
        hm.put("Updated", new Value(""));
        update();

    }

    public void run()
    {
        while(true)
        {
            try
            {
                  Thread.sleep((long)min*5000L);
//                Thread.sleep((long)min*60000L);
            }
            catch (InterruptedException e) { }

            update();
        }
    }


    public void update()
    {
        Value v;

        try
        {
            stockURL = new URL(stockHost);

            if (WEB)
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(stockURL.openStream())));
            else
                inStream = new BufferedReader(new TagFilterReader(new FileReader(fileInput)));

//            synchronized(this)
//            {
                while ((inputLine = inStream.readLine()) != null)
                {
                    v = (Value)hm.get(inputLine);
                    if (v != null)
                    {
                        inputLine = inStream.readLine();
                        if (inputLine != null)
                        {
                            v.setStr(inputLine.trim());
                        }
                    }
                }
//            }

            inStream.close();
            inStream = null;
            stockURL = null;
            stockConn = null;
            inputLine = null;
            listbox.add(getTime()+"  "+getLast());

            notifyListeners();

        }
        catch (MalformedURLException e)
        {
            System.out.println("MalforemdURLException: "+e);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }

    }

    void startAction()
    {
        runner.start();
    }

    void exitAction()
    {
        if (runner != null)
            runner.stop();

        System.exit(0);
    }

    public String getTime()
    {
        return(((Value)hm.get("Updated")).getStr());
    }

    public String getLast()
    {
        return(((Value)hm.get("Last")).getStr());
    }

    public String getLow()
    {
        return(((Value)hm.get("Low")).getStr());
    }

    public String getHigh()
    {
        return(((Value)hm.get("High")).getStr());
    }


    public String getOpen()
    {
        return(((Value)hm.get("Open")).getStr());
    }

    public String getPrev()
    {
        return(((Value)hm.get("Close")).getStr());
    }

    public String getNetChg()
    {
        return(((Value)hm.get("Net Chg")).getStr());
    }

    public String getPctChg()
    {
        return(((Value)hm.get("Pct Chg")).getStr()+" %");
    }



    class Value
    {
        private String str;

        Value(String str)
        {
            this.str = str;
        }

        void setStr(String str)
        {
            this.str = str;
        }

        String getStr()
        {
            return(str);
        }

    }

    public synchronized void addListener(SMIServerListener listener)
    {
        listeners.addElement(listener);

        if (!events.isEmpty())
        {
            for (int elem = 0; elem < events.size(); elem++)
            {
                listener.newData((SMIServerEvent)events.elementAt(elem));
            }

        }
    }

    public synchronized void removeListener(SMIServerListener listener)
    {
        listeners.removeElement(listener);
    }


    void notifyListeners()
    {
        Hashtable tht = new Hashtable();
        tht.put("Last", getLast());
        tht.put("Low", getLow());
        tht.put("High", getHigh());
        tht.put("Open", getOpen());
        tht.put("Prev", getPrev());
        tht.put("NetChg", getNetChg());
        tht.put("PctChg", getPctChg());
        tht.put("Time", getTime());

        Vector thisList = new Vector();
        SMIServerEvent thisEvent = new SMIServerEvent(this, tht);
        events.addElement(thisEvent);

        synchronized(this)
        {
            thisList = (Vector)listeners.clone();
        }

        for (int elem = 0; elem < thisList.size(); elem++)
        {

            if (thisList.elementAt(elem) instanceof VSMITicker)
                ((VSMITicker)thisList.elementAt(elem)).newData(thisEvent, new OneWay());

            else if (thisList.elementAt(elem) instanceof VTodayChartFrame)
                ((VTodayChartFrame)thisList.elementAt(elem)).newData(thisEvent, new OneWay());

            else
                ((SMIServerListener)thisList.elementAt(elem)).newData(thisEvent);
        }

        saveEvents();
    }

    void loadEvents()
    {
        try
        {
            FileInputStream in = new FileInputStream("events.data");
        	ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(in));
            events = (Vector)s.readObject();
            s.close();
    	}
    	catch (ClassNotFoundException e)
    	{
    	    System.out.println(e);
    	}
    	catch (FileNotFoundException fnfe) { }
    	catch (IOException e)
    	{
    	    System.out.println(e);
    	}
    }

    void saveEvents()
    {
        try
        {
            if (!events.isEmpty())
            {
                FileOutputStream f = new FileOutputStream("events.data");
               	ObjectOutput s  =  new ObjectOutputStream(new BufferedOutputStream(f));
                s.writeObject(events);
                s.close();
            }
        }
      	catch (IOException e)
    	{
    	    System.out.println(e);
    	}

    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }

    public static void main( String args[] )
    {

        try
        {
            Voyager.startup(7000);
	        VSMIServer vss = new VSMIServer("r4006174.itzrh.ska.com:7000/SMIServer" );
          	vss.liveForever();
	    	//Voyager.shutdown();
        }
        catch( VoyagerException exception )
        {
            System.err.println( exception );
        }
    }



}