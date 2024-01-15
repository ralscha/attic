package lotto.page;

import lotto.*;
import com.odi.util.*;
import java.io.*;
import common.io.*;
import java.util.*;

public class Producer_Appl_Data extends PageProducer {
		
	private static final String dataFile = "lottowin.data";
	private static final String dataFrameFile = "win.data";
	
	public void producePage(Ausspielungen auss, Properties props) {
		StringBuffer sb = new StringBuffer();
		
		int zahlen[];

		try {
			PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath+dataFile)));
			DataOutputStream out1old = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(lottoFramePath+dataFrameFile)));

			PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath+dataFile)));

			
			int size = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getAnzahlAusspielungen();
        	ListIterator it = auss.getListIterator(size-21);

			while(it.hasNext()) {
				sb.setLength(0);
				Ziehung z = (Ziehung)it.next();
				
				sb.append(z.getNr());
				sb.append(';');
				sb.append(z.getJahr());
				sb.append(';');
				if (z.isWednesday())
					sb.append("Mi, ");
				else
					sb.append("Sa, ");
				sb.append(dateFormat.format(z.getDatum()));
				sb.append(';');
				
				zahlen = z.getZahlen();
				for (int i = 0; i < 6; i++) {
					sb.append(zahlen[i]);
					sb.append(';');	
				}
				
				sb.append(z.getZusatzZahl());
				sb.append(';');
				sb.append(z.getJoker());
				out1.println(sb.toString());
				out2.println(sb.toString());
				
			
				out1old.writeInt(z.getNr());
				out1old.writeInt(z.getJahr());				
				Calendar d = new GregorianCalendar();
				d.setTime(z.getDatum());
				out1old.writeInt(d.get(Calendar.DAY_OF_MONTH));
				out1old.writeInt(d.get(Calendar.MONTH)+1);
				out1old.writeInt(d.get(Calendar.YEAR));
				
				for (int i = 0; i < 6; i++)
					out1old.writeInt(zahlen[i]);
				out1old.writeInt(z.getZusatzZahl());
			}
			
        	out1.close();
			out1old.close();
			out2.close();
		} catch (IOException ioe) {
		   System.err.println(ioe);
		}
	}
}

 
