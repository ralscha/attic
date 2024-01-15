package lotto;

import java.util.*;
import java.io.*;
import COM.odi.util.*;

public class Ausspielungen {
	public final static int ALLE  = 0;
	public final static int AUS42 = 1;
	public final static int AUS45 = 2;
	public final static int W2    = 3; /* 1997 */
	public final static int CYEAR = 4; /* aktuelles Jahr */

    private final static int NUMSTATS = 5;

    int ausstehend[] = new int[45];
    private OSVectorList ziehungen;
    private LottoStats ls[];

	public Ausspielungen() {	    	    
	    ziehungen = new OSVectorList();
    	ls = new LottoStats[NUMSTATS];
    	ls[0] = new LottoStatsAlle();
    	ls[1] = new LottoStatsAus42();
    	ls[2] = new LottoStatsAus45();
    	ls[3] = new LottoStatsW2();
    	ls[4] = new LottoStatsCYear();
    }

    public void addZiehung(Calendar date, int nr, int jahr,
        	               int zahlen[], int zz, String joker,
	                       LottoGewinnquote lgq, JokerGewinnquote jgq) {
	                        
       	Ziehung help = new Ziehung(date, nr, jahr, zahlen, zz, joker, lgq, jgq);
       	addZiehung(help);
/*
    	ziehungen.add(help);

        for (int i = 0; i < NUMSTATS; i++)
            ls[i].updateStats(help);

        for (int i = 0; i < 45; i++)
            ausstehend[i]++;
            
        for (int i = 0; i < 6; i++)
       		ausstehend[zahlen[i]-1] = 0;
*/                  
	}

    public void addZiehung(Ziehung zie) {
    	ziehungen.add(zie);

        for (int i = 0; i < NUMSTATS; i++)
            ls[i].updateStats(zie);

        for (int i = 0; i < 45; i++)
            ausstehend[i]++;
        
        int zahlen[] = zie.getZahlen();    
        for (int i = 0; i < 6; i++)
       		ausstehend[zahlen[i]-1] = 0;                    
        
    }

	public void addZiehung(Calendar date, int nr, int jahr,
	                       int zahlen[], int zz, String joker) {
        addZiehung(date, nr, jahr, zahlen, zz, joker, null, null);
    }

	public int getTotalGerade(int art) {
	    return ls[art].getTotalGerade();
	}

	public int getTotalUngerade(int art) {
	    return ls[art].getTotalUngerade();
	}

	public int getAnzahlAusspielungen(int art) {
	    return ls[art].getAnzahlAusspielungen();
	}

	public int[] getAusstehend() {
	    return ausstehend;
	}

	public int[] getHaeufigkeit(int art) {
	    return ls[art].getHaeufigkeit();
	}

	public int[] getTotalSumme(int art) {
	    return ls[art].getTotalSumme();
	}

	public int[][] getPaar(int art) {
	    return ls[art].getPaar();
	}

	public int[] getTotalVerbund(int art) {
	    return ls[art].getTotalVerbund();
	}

	public int[] getHaeufigkeitGerade(int art) {
	    return ls[art].getHaeufigkeitGerade();
	}

	public Ziehung getLast() {
	    return (Ziehung)ziehungen.lastElement();
	}	

    public Iterator iterator() {
        return ziehungen.iterator();
    }

	public ListIterator getListIterator(int pos) {
		return ziehungen.listIterator(pos);
	}

}

