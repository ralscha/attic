
import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import common.util.*;
import com.odi.util.*;


/* Annahmen:
 ** Gerade/Ungerade: 2/4, 3/3, 4/2    (81.8 %)
 ** Tiefe/Hohe     : 2/4, 3/3, 4/2    (79.9 %)  Tiefe -> 1 - 22
 ** Tiefe/Hohe Endz: 2/4, 3/3, 4/2    (80.8 %)  Tiefe -> 0 - 4
 ** 3er Klassen    : 5 oder 6 Klassen (88.3 %) 1-3,4-6,....
 ** 5er Klassen    : 5 oder 6 Klassen (88.3 %) 1-5,6-10,....
 ** 9er Klassen    : 4 oder 5 Klassen (98.9 %) 1-9,....
 ** Quersumme      : 30 - 49          (?? %) Ziffern der Zahlen
 ** Summe          : 100 - 199         (?? %) 
 ** Summe Diff     : 20 - 39          (84 %)
 ** Summe End      : 20 - 34          (??.7 %)
 ** Verbund        : NICHTS oder ZWILLING (86.2 %)
*/
public class TipMakerII {

	public java.util.List createTips(int numberOfTips) {
		java.util.List tipsList = new ArrayList();

		Random r = new Random();
		Ziehung zie;
	
		int n = 0;
		while(n < numberOfTips) {

			java.util.BitSet b = new java.util.BitSet(46);
			
			int cnt = 0;
			while (cnt < 6) {
				int num = 1 + Math.abs(r.nextInt()) % 45;
				if (!b.get(num)) {
					b.set(num);
					++cnt;
				}
			}
			int[] auswahl = new int[6];
			int ix = 0;
			for (int i = 1; i <= 45; ++i) {
				if (b.get(i)) {
					auswahl[ix++] = i;
				}
			}
			
			zie = new Ziehung(0, 0, new Date(), auswahl, 0, null, null, null);
			
			//TESTEN

			int gerade = zie.getZiehungStatistik().getGerade();
			if ((gerade < 2) || (gerade > 4))
				continue;

			VerbundType vt = zie.getZiehungStatistik().getVerbund();
			if ((!vt.equals(VerbundType.NICHTS)) && (!vt.equals(VerbundType.ZWILLING)))
				continue;

			int summe = zie.getZiehungStatistik().getSumme();
			if ((summe < 100) || (summe > 199))
				continue;

			int summediff = zie.getZiehungStatistik().getSummeDifferenzen();
			if ((summediff < 20) || (summediff > 39))
				continue;

			int summeEnd = zie.getZiehungStatistik().getEndZiffernSumme();
			if ((summeEnd < 20) || (summeEnd > 34))
				continue;

			int k3 = zie.getZiehungStatistik().getDreierKlassen();
			if ((k3 < 5) || (k3 > 6))
				continue;

			int k5 = zie.getZiehungStatistik().getFuenferKlassen();
			if ((k5 < 5) || (k5 > 6))
				continue;

			int k9 = zie.getZiehungStatistik().getNeunerKlassen();
			if ((k9 < 4) || (k9 > 5))
				continue;

			int quer = zie.getZiehungStatistik().getQuersumme();
			if ((quer < 30) || (quer > 49))
				continue;

			int tief = zie.getZiehungStatistik().getTiefe();
			if ((tief < 2) || (tief > 4))
				continue;

			int tiefE = zie.getZiehungStatistik().getTiefeEndziffern();
			if ((tiefE < 2) || (tiefE > 4))
				continue;
				
			n++;
			tipsList.add(zie);
			/*
			int z[] = zie.getZahlen();
		
			for (int y = 0; y < 6; y++) 
				System.out.print(z[y]+" ");
			System.out.println();		
			*/
			//System.out.println(zie);
		}
		return tipsList;
		
		
	}
	
	public static void main(String args[]) {
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
		
		com.odi.Transaction tr = DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
		for (int r = 0; r < 10; r++) {
		java.util.List tips = new TipMakerII().createTips(14);
		//java.util.List tips = new TipMakerII().createRandomTips(14);
		
		
		Iterator it = auss.iterator();
		
		
		int dreier = 0;
		int vierer = 0;
		int fuenfer = 0;
		int sechser = 0;
		int fuenferplus = 0;
		
		while(it.hasNext()) {

			
			Ziehung zie = (Ziehung)it.next();
			int za[] = zie.getZahlen();
			int zz = zie.getZusatzZahl();
			for (int x = 0; x < tips.size(); x++) {			
				int zb[] = ((Ziehung)tips.get(x)).getZahlen();
				int w = getWin(zb, za, zz);
				switch(w) {
					case 3 : dreier++; break;
					case 4 : vierer++; break;
					case 5 : fuenfer++; break;
					case 51 : fuenferplus++; break;
					case 6 : sechser++; break;
				}
			}
			
		}
		
		if (dreier > 0)
			System.out.print(dreier + "x3, ");
			
		if (vierer > 0)
			System.out.print(vierer + "x4, ");
			
		if (fuenfer > 0)				
			System.out.print(fuenfer + "x5, ");
			
		if (fuenferplus > 0)				
			System.out.print(fuenferplus + "x5p, ");
			
		if (sechser > 0)				
			System.out.print(sechser + "x6");												
		
		System.out.println();
		}
		DbManager.commitTransaction(tr);
		
		DbManager.shutdown();
	}


	private static int getWin(int tip[], int auss[], int zz) {
        int i,j,win = 0;

        for (i = 0; i < 6; i++)
            for (j = 0; j < 6; j++)
                if (tip[i] == auss[j]) win++;

        if (win == 5) {
            for (i = 0; i < 6; i++)
                if (tip[i] == zz) return(51);
        }

        return (win);

    }
    
    public java.util.List createRandomTips(int numberOfTips) {
		java.util.List tipsList = new ArrayList();

		Random r = new Random();
		Ziehung zie;
	
		int n = 0;
		while(n < numberOfTips) {

			java.util.BitSet b = new java.util.BitSet(46);
			
			int cnt = 0;
			while (cnt < 6) {
				int num = 1 + Math.abs(r.nextInt()) % 45;
				if (!b.get(num)) {
					b.set(num);
					++cnt;
				}
			}
			int[] auswahl = new int[6];
			int ix = 0;
			for (int i = 1; i <= 45; ++i) {
				if (b.get(i)) {
					auswahl[ix++] = i;
				}
			}
			
			zie = new Ziehung(0, 0, new Date(), auswahl, 0, null, null, null);
			tipsList.add(zie);
			n++;	
		}
		return tipsList;
    }
}