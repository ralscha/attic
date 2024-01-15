import lotto.*;
import lotto.extract.*;
import lotto.html.*;
import lotto.util.*;
import java.util.*;
import java.text.*;

public class QuotenRunner implements Runnable {

    private Thread myThread;
    private Controller controller;

    private final int MAX_LOOP = 60;
    private final int WAIT_TIME = 450; /* seconds (15 minutes) */

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");    
    private double nextLottoGewinnsumme, nextJokerGewinnsumme;
    
    public QuotenRunner(Controller c) {
        this.controller = c;       

        myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        Ausspielungen auss;
        
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "START");

        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        DbManager.startUpdateTransaction();

        auss = DbManager.getAusspielungen();
        Ziehung zie = auss.getLast();
        
        int loopCount = 0;
        boolean ok = false;

        while ((Thread.currentThread() == myThread) && (loopCount < MAX_LOOP) && (!ok) ) {

				ok = extract(zie, new TeletextLottoExtractor());
   
            if (!ok) ok = extract(zie, new SwissLottoExtractor());


            if (!ok) {
                try {
                    myThread.sleep(WAIT_TIME*1000);
                } catch(InterruptedException ex) { return; }
            }
            loopCount++;
        }

        if (ok) {
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "LottoGewinnquote : " + zie.getLottoGewinnquote());
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "JokerGewinnquote : " + zie.getJokerGewinnquote());
        }

        DbManager.commitTransaction();

        if (ok) {
            DbManager.startReadTransaction();

            auss = DbManager.getAusspielungen();
	        LottoHTMLProducer lhp = new LottoHTMLProducer(AppProperties.getInstance().getProperty("htmlPath"), 
	                                                      AppProperties.getInstance().getProperty("htmlQuotenPath"),
	                                                      auss);
            lhp.writeQuoten(nextLottoGewinnsumme, nextJokerGewinnsumme, 
                            controller.calculateNextDate(controller.getNextDate()));
            //lhp.writeLottoGewinnquotenReverseOrder(1999);
            //lhp.writeJokerGewinnquotenReverseOrder(1999);
            DbManager.commitTransaction();
        }
        DbManager.shutdown();

        if (ok) {
            new FileSender(AppProperties.getInstance().getProperty("htmlQuotenPath"), "quoten file");
            //new DatabaseZipper();
        }

        //controller.setWaitTime();

        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "END");
    }


    private boolean extract(Ziehung zie, LottoExtractor le) {
        LottoGewinnquote lgq = null;
        JokerGewinnquote jgq = null;

        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "extract: "+dateFormat.format(controller.getNextDate().getTime()));

        try {
            if ((lgq = le.extractLottoGewinnquote(controller.getNextDate())) != null) {
                zie.setLottoGewinnquote(lgq);
            } else {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extract:  LottoGewinnquoten Format falsch");
                return false;
            }
        } catch (NewDataNotAvailableException e) {
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "extract:  Keine neuen Lottoquoten");
            return false;
        }

        try {
            if ((jgq = le.extractJokerGewinnquote(controller.getNextDate())) != null) {
                zie.setJokerGewinnquote(jgq);
            } else {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extract:  JokerGewinnquoten Format falsch");
                return false;
            }
        } catch (NewDataNotAvailableException e) {
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "extract:  Keine neuen Jokerquoten");
            return false;
        }

        nextJokerGewinnsumme = le.getNextJokerGewinnsumme();
        nextLottoGewinnsumme = le.getNextLottoGewinnsumme();

        return true;

    }


}