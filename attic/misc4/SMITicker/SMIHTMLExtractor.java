import java.io.*;
import java.net.*;
import java.util.*;

public class SMIHTMLExtractor implements Runnable {

    
    private final static String stockHost = "http://www.swissquote.ch/fcgi-bin/parsefquote?quote=0";
    private final static String fileInput = "parsefquote.html";
    private final static boolean WEB = false;

    private Calendar time;
    private double last, pctChg, netChg, high, low;

    private Vector listeners = new Vector();
    private Thread myThread = null;
    private int min;

    public SMIHTMLExtractor(int min) {
        this.min = min;
        time = new GregorianCalendar();       
        last = pctChg = netChg = high = low = 0.0d;
        if (min > 0) start();
    }

    public void setUpdateTime(int min) {
        this.min = min;
        stop();        
        if (min > 0) start();
        
    }

    public void start() {
        if (myThread != null) myThread = null;
        myThread = new Thread(this);
        myThread.start();               
    }

    public void stop() {
        myThread = null;
    }

    public void run() {
        if (min == 0) return;

        while(Thread.currentThread() == myThread) {
            update();
            try {
                  myThread.sleep((long)min*60000L);
            } catch (InterruptedException e) { }
        }
    }


    public void update() {
        URL stockURL;
        URLConnection stockConn;
        BufferedReader inStream;
        String inputLine;
        String change;
        String timeStr, lastStr, changeStr, highStr, lowStr;
        timeStr = lastStr = changeStr = highStr = lowStr = null;
        StringTokenizer st;
        Vector vect = new Vector();
        Calendar time_tmp;
        double last_tmp, pctChg_tmp, netChg_tmp, high_tmp, low_tmp;


        try {

            if (WEB) {
                stockURL = new URL(stockHost);
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(stockURL.openStream())));
            }
            else 
                inStream = new BufferedReader(new TagFilterReader(new FileReader(fileInput)));

            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens()) {
                    vect.addElement(st.nextToken());
                }
            }
            inStream.close();

            int i = 0;
            boolean found = false;
            while ((i < vect.size()) && (!found)) {
                if (((String)vect.elementAt(i)).equalsIgnoreCase("low")) {      
                    timeStr = (String)vect.elementAt(i+1);
                    lastStr = (String)vect.elementAt(i+2);
                    changeStr = (String)vect.elementAt(i+3);
                    highStr = (String)vect.elementAt(i+4);
                    lowStr = (String)vect.elementAt(i+5);
                    found = true;
                }
                i++;
            }
           
            if (found) {
                try {
                    last_tmp = ConvHelper.string2double(lastStr);
                    high_tmp = ConvHelper.string2double(highStr);
                    low_tmp  = ConvHelper.string2double(lowStr);
                    netChg_tmp = ConvHelper.getNetChg(changeStr);
                    pctChg_tmp = ConvHelper.getPctChg(changeStr);
                    time_tmp   = ConvHelper.getCalendar(timeStr);

                    time.setTime(time_tmp.getTime());
                    last = last_tmp;
                    high = high_tmp;
                    low  = low_tmp;
                    netChg = netChg_tmp;
                    pctChg = pctChg_tmp;
                   
                } catch (NumberFormatException nfe) {
                    System.out.println(nfe);
                }
            }
            

            notifyListeners();

        } catch (MalformedURLException e) {
            System.out.println("MalforemdURLException: "+e);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }

    }

    public Calendar getTime() {
        return time;
    }

    public double getLast() {
        return last;
    }

    public double getLow() {
        return low;
    }

    public double getHigh() {
        return high;
    }

    public double getNetChg() {
        return netChg;
    }

    public double getPctChg() {
        return pctChg;
    }

    public synchronized void addListener(SMIHTMLExtractorListener listener) {
        listeners.addElement(listener);
    }

    public synchronized void removeListener(SMIHTMLExtractorListener listener) {
        listeners.removeElement(listener);
    }


    void notifyListeners() {
        Vector thisList = new Vector();
        SMIHTMLExtractorEvent thisEvent = new SMIHTMLExtractorEvent(this);

        synchronized(this) {
            thisList = (Vector)listeners.clone();
        }

        for (int elem = 0; elem < thisList.size(); elem++) {
            ((SMIHTMLExtractorListener)thisList.elementAt(elem)).newDataArrived(thisEvent);
        }
    }
      
}