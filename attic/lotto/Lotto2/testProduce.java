
import lotto.*;
import lotto.html.*;
import lotto.util.*;
import java.util.*;
import COM.odi.util.*;
import COM.odi.*;

public class testProduce {
    
    
	public testProduce(String args[]) {
	
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
	    DbManager.startReadTransaction();
   
    
        Ausspielungen auss = DbManager.getAusspielungen();
        LottoHTMLProducer lhp = new LottoHTMLProducer(AppProperties.getInstance().getProperty("htmlPath"), 
                                                      AppProperties.getInstance().getProperty("htmlQuotenPath"),
	                                                  auss);	    
        /*        
        lhp.writeTitle();
        lhp.writeAktuelleAusspielung();
        //lhp.writeAlleZiehungenText();
        lhp.writeHaeufigkeitOrderByHaeufigkeit();
        lhp.writeHaeufigkeitOrderByZahlen();
        lhp.writeVerbund();
        lhp.writeJokerReverseOrder(1999);
        
        for (int i = 1970; i < 1999; i++)
            lhp.writeLottoGewinnquotenReverseOrder(i);
            
        lhp.writeJokerGewinnquotenReverseOrder(1999);
            
        lhp.writeGeradeUngeradeChartData();
        lhp.writeGeradeUngerade();
        lhp.writeAusstehend();
        lhp.writeTips();
        lhp.writePaar();
        lhp.writeWinSer();
        lhp.writeWin();
        
        lhp.writeAlleZiehungen();
        lhp.writeZiehung(1997);
        lhp.writeZiehung1999ReverseOrder_1();
        */
        DbManager.commitTransaction();                
        DbManager.shutdown();     
	
	}
		
    public static void main(String args[]) {
        new testProduce(args);
    }
}