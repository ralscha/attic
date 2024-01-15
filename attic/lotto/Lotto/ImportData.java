
import java.io.*;

import lotto.*;
import common.util.*;
import java.util.*;
import com.odi.Transaction;

public class ImportData {
	
	static {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	}
	
	private final static COM.stevesoft.pat.Regex zieRegex = new COM.stevesoft.pat.Regex("^(\\d{1,2}).(\\d{1,2}).(\\d{4}).(\\d{1,3}).(\\d{4}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}).(\\d*).");
	private final static COM.stevesoft.pat.Regex gwRegex = new COM.stevesoft.pat.Regex("^(\\d{1,3}).(\\d{4}).([\\d.]+).([\\d]+).([\\d.]+).([\\d]+).([\\d.]+).([\\d]+).([\\d.]+).([\\d]+).([\\d.]+).([\\d]+).([\\d.]+)");

	public static void main(java.lang.String[] args) {
		try {
			

			BufferedReader brZ = new BufferedReader(new FileReader("ziehung.txt"));
			BufferedReader brG = new BufferedReader(new FileReader("gewinnquoten.txt"));
			BufferedReader brJ = new BufferedReader(new FileReader("jokergewinnquoten.txt"));
	
			/* Funktioniert nur mit folgendem Fix. Warum? 
			try {
				Class.forName("lotto.Ausspielungen");
			} catch(ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			}
			*/
			
			DbManager.initialize(AppProperties.getStringProperty("file.database"), true);
			Transaction tr = DbManager.startUpdateTransaction();
			Ausspielungen auss = DbManager.getAusspielungen();               
			
			int nr, jahr, zz;
			String joker;
			LottoGewinnquote lgq;
			JokerGewinnquote jgq;
			Date datum;
			int[] zahlen = new int[6];
						
			String lineZ, lineG, lineJ;
			while ((lineZ = brZ.readLine()) != null) {
				lineG = brG.readLine();
				lineJ = brJ.readLine();
				
				zieRegex.search(lineZ);
				if (zieRegex.didMatch()) {
					Calendar cal = new GregorianCalendar(Integer.parseInt(zieRegex.stringMatched(3)),
															   Integer.parseInt(zieRegex.stringMatched(2))-1,
															   	Integer.parseInt(zieRegex.stringMatched(1)));
					datum = cal.getTime();
					nr = Integer.parseInt(zieRegex.stringMatched(4));
					jahr = Integer.parseInt(zieRegex.stringMatched(5));
					zahlen[0] = Integer.parseInt(zieRegex.stringMatched(6));
					zahlen[1] = Integer.parseInt(zieRegex.stringMatched(7));
					zahlen[2] = Integer.parseInt(zieRegex.stringMatched(8));
					zahlen[3] = Integer.parseInt(zieRegex.stringMatched(9));
					zahlen[4] = Integer.parseInt(zieRegex.stringMatched(10));
					zahlen[5] = Integer.parseInt(zieRegex.stringMatched(11));
					zz = Integer.parseInt(zieRegex.stringMatched(12));
					joker = zieRegex.stringMatched(13);
					
					lgq = null;
					gwRegex.search(lineG);
					if (gwRegex.didMatch()) {
						lgq = new LottoGewinnquote(Double.parseDouble(gwRegex.stringMatched(3)),
														Double.parseDouble(gwRegex.stringMatched(5)),
														Double.parseDouble(gwRegex.stringMatched(7)),
														Double.parseDouble(gwRegex.stringMatched(9)),
														Double.parseDouble(gwRegex.stringMatched(11)),
														Double.parseDouble(gwRegex.stringMatched(13)),
														Integer.parseInt(gwRegex.stringMatched(4)),
														Integer.parseInt(gwRegex.stringMatched(6)),
														Integer.parseInt(gwRegex.stringMatched(8)),
														Integer.parseInt(gwRegex.stringMatched(10)),
														Integer.parseInt(gwRegex.stringMatched(12))  );
														
					}
					
					gwRegex.search(lineJ);
					if (gwRegex.didMatch()) {
						jgq = new JokerGewinnquote(Double.parseDouble(gwRegex.stringMatched(3)),
									Double.parseDouble(gwRegex.stringMatched(5)),
									Double.parseDouble(gwRegex.stringMatched(7)),
									Double.parseDouble(gwRegex.stringMatched(9)),
									Double.parseDouble(gwRegex.stringMatched(11)),
									Double.parseDouble(gwRegex.stringMatched(13)),
									Integer.parseInt(gwRegex.stringMatched(4)),
									Integer.parseInt(gwRegex.stringMatched(6)),
									Integer.parseInt(gwRegex.stringMatched(8)),
									Integer.parseInt(gwRegex.stringMatched(10)),
									Integer.parseInt(gwRegex.stringMatched(12))  );
					} else
						jgq = null;
					
					auss.addZiehung(nr, jahr, datum, zahlen, zz, joker, lgq, jgq);
				}	
			}
			
			brZ.close();
			brG.close();
			brJ.close();
			
			DbManager.commitTransaction(tr);            
			DbManager.shutdown();
		} catch (Throwable e) {
			System.out.println(e);
		}

	}
}