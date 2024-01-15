import lotto.*;
import lotto.extract.*;
import lotto.html.*;
import lotto.util.*;
import java.util.*;
import java.text.*;

public class LottoRunner implements Runnable {

    private Thread myThread;
    private Controller controller;

    private final int MAX_LOOP = 60;
    private final int WAIT_TIME = 450; /* seconds */

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");    

    public LottoRunner(Controller c) {        
        this.controller = c;
        
        myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        Ziehung newZie = null;        
        int loopCount = 0;

        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "START");
        
        while((Thread.currentThread() == myThread) && (loopCount < MAX_LOOP) && (newZie == null) ) {

			newZie = extract(controller.getNextDate(), new TeletextLottoExtractor());

            if (newZie == null) 
            	newZie = extract(controller.getNextDate(), new SwissLottoExtractor());                
            
            if (newZie == null) {
                try {
                    myThread.sleep(WAIT_TIME*1000);
                } catch(InterruptedException ex) { return; }
            }
            loopCount++;
        }

        if (newZie != null) {
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Neue Ziehung : " + newZie);

            DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
            DbManager.startUpdateTransaction();
            Ausspielungen auss = DbManager.getAusspielungen();            
            newZie.setNr(controller.getNextNr());
            newZie.setJahr(controller.getNextJahr());                        
            auss.addZiehung(newZie);
            DbManager.commitTransaction();                
            
            DbManager.startReadTransaction();
            createHTMLPages();                    
            DbManager.commitTransaction();                
            DbManager.shutdown();     
            
            new FileSender(AppProperties.getInstance().getProperty("htmlPath"), "lotto files");    
            new QuotenRunner(controller);
        }
        
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "END");        

    }

    private Ziehung extract(Calendar nDate, LottoExtractor le) {
        Ziehung zie = null;
        
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "extract: "+dateFormat.format(nDate.getTime()));

        try {
            if ((zie = le.extractLottoNumbers(nDate)) == null) {                
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extract :  Lottozahlen Format falsch");
                return null;
            }
        } catch (NewDataNotAvailableException e) {
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "extract:  Keine neuen Lottozahlen");
            return null;
        }     

        zie.setLottoGewinnquote(null);
        zie.setJokerGewinnquote(null);
        
        return zie;

    }
    
    private void createHTMLPages() {	
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "createHTMLPages:  START");
        Ausspielungen auss = DbManager.getAusspielungen();
        LottoHTMLProducer lhp = new LottoHTMLProducer(AppProperties.getInstance().getProperty("htmlPath"), 
	                                                  AppProperties.getInstance().getProperty("htmlQuotenPath"),
                                                      auss);	    
        //lhp.writeTitle();
        //lhp.writeAktuelleAusspielung();
        //lhp.writeAlleZiehungenText();
        //lhp.writeHaeufigkeitOrderByHaeufigkeit();
        //lhp.writeHaeufigkeitOrderByZahlen();
        //lhp.writeVerbund();
        //lhp.writeJokerReverseOrder(1999);        
        //lhp.writeGeradeUngeradeChartData();
        //lhp.writeGeradeUngerade();
        //lhp.writeAusstehend();
        //lhp.writeTips();
        //lhp.writePaar();
        //lhp.writeWinSer();
        //lhp.writeWin();
        //lhp.writeZiehung1999ReverseOrder_1();
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "createHTMLPages: END");
    }
    
}