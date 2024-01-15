import java.io.*;
import lotto.util.*;

public class testLog {
    
	public testLog(String args[]) {
	    
  	    DiskLog dl = DiskLog.getInstance();
	    
        for (int i = 0; i < 10010; i++) {
            dl.log(DiskLog.INFO, getClass().getName(), "Dies ist ein Test : "+String.valueOf(i));
   	    }
	}
		
    public static void main(String args[]) {
        new testLog(args);
    }
}

