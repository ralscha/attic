package lotto.page;

import lotto.*;
import java.io.*;
import java.util.*;
import common.io.*;
import java.text.*;

public class Producer_Ziehungen_Txt extends PageProducer {
		
	private static final String htmlFile = "ziehung.txt";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public void producePage(Ausspielungen auss, Properties props) {
		StringBuffer sb = new StringBuffer();
		
		int zahlen[];
		Ziehung z;

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath+htmlFile)));
			Iterator it = auss.iterator();

      boolean showdate = false;

			while(it.hasNext()) {
				sb.setLength(0);
				z = (Ziehung)it.next();
				  
        if ((z.getNr() == 1) && (z.getJahr() == 1997)) {
          showdate = true;
        }

				if (z.getNr() < 100)
				  sb.append(' ');
				if (z.getNr() < 10)
				  sb.append(' ');
				sb.append(z.getNr()).append("  ");
				sb.append(z.getJahr()).append("  ");

        if (showdate)
          sb.append(dateFormat.format(z.getDatum()));
        else
          sb.append("          ");

        sb.append("  ");				
				zahlen = z.getZahlen();
				for (int j = 0; j < 6; j++) {
					if (zahlen[j] < 10)
						sb.append(' ');
					sb.append(' ').append(zahlen[j]).append(' ');
				}
				if (z.getZusatzZahl() < 10)
				  sb.append(' ');
				sb.append("   ").append(z.getZusatzZahl());
				
        sb.append("   ");
        sb.append(z.getJoker());
				out.println(sb.toString());
			}
			
        	out.close();
		} catch (IOException ioe) {
		   System.err.println(ioe);
		}
	}
}