import lotto.html.*;
import lotto.util.*;
import java.util.*;

public class FileSender implements Runnable {

    private Thread myThread;
            
    private final int MAX_LOOP = 10;
    private final int WAIT_TIME = 450; /* seconds (7.5 minutes) */

    private String path = null;
    private String description = null;

    private Vector ftpconns;
    boolean[] okarray;

    public FileSender(String path, String description) {
        this.path = path;
        this.description = description;
        ftpconns = AppProperties.getInstance().getFtpConnections();
        okarray = new boolean[ftpconns.size()];
        
        for (int i = 0; i < okarray.length; i++) {
            okarray[i] = false;
        }
        
        myThread = new Thread(this);
        myThread.start();       
    }
    
    public void run() {
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "START sending "+description);
        
        int loopCount = 0;
        boolean ok = false;
        
        while ((Thread.currentThread() == myThread) && (loopCount < MAX_LOOP) && (!ok)) {    
            ok = sendFiles(path);            
            if (!ok) {
                try {
                    myThread.sleep(WAIT_TIME*1000);
                } catch(InterruptedException ex) { return; }
            }            
            loopCount++;
        }
    }
    
    private boolean sendFiles(String path) {
        boolean transferOK = true;                    
                        
        for (int i = 0; i < ftpconns.size(); i++) {
            if (okarray[i] == false) {
                FtpConnection ftpc = (FtpConnection)ftpconns.elementAt(i);
                FtpFiles ff = new FtpFiles(path, ftpc);        
                okarray[i] = ff.sendFiles();
                if (okarray[i] == false) transferOK = false;
            }
        } 
        
        return transferOK;
    }            
}
    