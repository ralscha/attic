package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;


public class Producer_GeradeUngerade_ChartData extends PageProducer {
	private static final String datFiles[] = {"GUalle.txt", "GU42.txt", "GU45.txt", "GU97.txt"};		
				
	public void producePage(Ausspielungen auss, Properties props) {
		try {
			 
		int hger[];			
		AusspielungenType[] types = AusspielungenType.getTypesAsArray();

        for (int i = 0; i < 4; i++) {        	
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + datFiles[i])));
				out.println("\"\" 0 1 2 3 4 5 6");
				out.print("\"\"");
				
				hger = auss.getAusspielungenStatistik(types[i]).getVerteilungHaeufigkeitGerade();
				for (int j = 0; j < hger.length; j++)
				  out.print(" "+hger[j]);
				out.println();
				
				out.close();
        }        	

		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}