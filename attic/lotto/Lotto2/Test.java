
import lotto.*;
import lotto.html.*;
import lotto.util.*;
import lotto.extract.*;
import COM.odi.util.*;
import COM.odi.*;
import java.text.*;
import java.util.*;

public class Test {
    		
    public Test() {
        Ziehung help;
        LottoGewinnquote lgq;
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        Ausspielungen auss = DbManager.getAusspielungen();                
                
        DbManager.startReadTransaction();

        
        System.out.println(auss.getTotalGerade(Ausspielungen.ALLE));
        System.out.println(auss.getTotalUngerade(Ausspielungen.ALLE));
        System.out.println(auss.getAnzahlAusspielungen(Ausspielungen.ALLE));

        System.out.println("Ausstehend");    
        int ausstehend[] = auss.getAusstehend();
        int i;
        for (i = 0; i < ausstehend.length; i++)
            System.out.println(ausstehend[i]);
        
        System.out.println("Haeufigkeit");    
        int hauf[] = auss.getHaeufigkeit(Ausspielungen.ALLE); 
        for (i = 0; i < hauf.length; i++)
            System.out.println(hauf[i]);
        
        System.out.println("TotalSumme");    
	    int total[] = auss.getTotalSumme(Ausspielungen.ALLE);
        for (i = 0; i < total.length; i++)
            System.out.println(total[i]);

        System.out.println("Verbund");    	    
    	int verbund[] = auss.getTotalVerbund(Ausspielungen.ALLE);
        for (i = 0; i < verbund.length; i++)
            System.out.println(verbund[i]);

        System.out.println("Gerade");    
	    int ger[] = auss.getHaeufigkeitGerade(Ausspielungen.ALLE);
        for (i = 0; i < ger.length; i++)
            System.out.println(ger[i]);
        
        DbManager.commitTransaction();
        DbManager.shutdown();
        
    }
    		
    public static void main(String args[]) {
        new Test();
    }
}
