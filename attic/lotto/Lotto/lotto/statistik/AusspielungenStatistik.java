package lotto.statistik;

import lotto.*;
import com.odi.*;
import com.odi.util.*;
import java.util.*;


public abstract class AusspielungenStatistik {

	private int totalUngerade;
	private int totalGerade;
	private OSHashMap  verteilungSumme;
	private OSHashMap  verteilungVerbund;
	private int anzahlAusspielungen = 0;
	private int paar[][] = new int[45][45];
	private int haeufigkeit[] = new int[45];

	// 7 -> 0 bis 6
	private int verteilungHaeufigkeitGerade[] = new int[7];
	private int verteilungTiefe[] = new int[7];
	private int verteilungTiefeEndziffern[] = new int[7];


	private OSHashMap  verteilungDreierKlassen;
	private OSHashMap  verteilungFuenferKlassen;
	private OSHashMap  verteilungNeunerKlassen;

	private OSHashMap  verteilungEndZiffernSumme;
	private OSHashMap  verteilungQuersumme;
	private OSHashMap  verteilungSummeDifferenzen;

	public AusspielungenStatistik() {
		verteilungSumme = new OSHashMap();
		verteilungVerbund = new OSHashMap();

		verteilungDreierKlassen = new OSHashMap();
		verteilungFuenferKlassen = new OSHashMap();
		verteilungNeunerKlassen = new OSHashMap();

		verteilungEndZiffernSumme = new OSHashMap();
		verteilungQuersumme = new OSHashMap();
		verteilungSummeDifferenzen = new OSHashMap();
		

		VerbundType[] vb = VerbundType.getTypesAsArray();
		for (int i = 0; i < vb.length; i++) 
			verteilungVerbund.put(vb[i].getExternal(), new Integer(0));
	}

	protected abstract boolean inRange(int nr, int jahr);

	public void updateStatistik(Ziehung z) {

		int zahlen[];

		if (inRange(z.getNr(), z.getJahr())) {
			anzahlAusspielungen++;
			totalGerade += z.getZiehungStatistik().getGerade();
			totalUngerade += z.getZiehungStatistik().getUngerade();

			//Summen
			incValue(verteilungSumme, new Integer(z.getZiehungStatistik().getSumme()));
			incValue(verteilungEndZiffernSumme,
         			new Integer(z.getZiehungStatistik().getEndZiffernSumme()));
			incValue(verteilungQuersumme, new Integer(z.getZiehungStatistik().getQuersumme()));
			incValue(verteilungSummeDifferenzen,
         			new Integer(z.getZiehungStatistik().getSummeDifferenzen()));

			//Verbund
			incValue(verteilungVerbund, z.getZiehungStatistik().getVerbund().getExternal());

			//Klassen
			incValue(verteilungDreierKlassen,
         			new Integer(z.getZiehungStatistik().getDreierKlassen()));
			incValue(verteilungFuenferKlassen,
         			new Integer(z.getZiehungStatistik().getFuenferKlassen()));
			incValue(verteilungNeunerKlassen,
         			new Integer(z.getZiehungStatistik().getNeunerKlassen()));


			zahlen = z.getZahlen();

			//Häufigkeit
			for (int i = 0; i < 6; i++)
				haeufigkeit[zahlen[i] - 1]++;


			//Paar
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 6; j++)
					if (i != j)
						paar[zahlen[i] - 1][zahlen[j] - 1]++;


			verteilungHaeufigkeitGerade[z.getZiehungStatistik().getGerade()]++;
			verteilungTiefe[z.getZiehungStatistik().getTiefe()]++;
			verteilungTiefeEndziffern[z.getZiehungStatistik().getTiefeEndziffern()]++;
		}
	}


	public int getTotalGerade() {
		return totalGerade;
	}

	public int getTotalUngerade() {
		return totalUngerade;
	}

	public int getAnzahlAusspielungen() {
		return anzahlAusspielungen;
	}

	public int[] getHaeufigkeit() {
		return haeufigkeit;
	}

	public Map getVerteilungSumme() {
		return verteilungSumme;
	}

	public int[][] getPaar() {
		return paar;
	}

	public Map getVerteilungVerbund() {
		return verteilungVerbund;
	}

	public int[] getVerteilungHaeufigkeitGerade() {
		return verteilungHaeufigkeitGerade;
	}

	public int[] getVerteilungTiefeEndziffern() {
		return verteilungTiefeEndziffern;
	}

	public int[] getVerteilungTiefe() {
		return verteilungTiefe;
	}


	public Map getVerteilungDreierKlassen() {
		return verteilungDreierKlassen;
	}
	public Map getVerteilungFuenferKlassen() {
		return verteilungDreierKlassen;
	}
	public Map getVerteilungNeunerKlassen() {
		return verteilungDreierKlassen;
	}


	public Map getVerteilungEndZiffernSumme() {
		return verteilungEndZiffernSumme;
	}

	public Map getVerteilungQuersumme() {
		return verteilungQuersumme;
	}

	public Map getVerteilungSummeDifferenzen() {
		return verteilungSummeDifferenzen;
	}

	//Helper Method
	private void incValue(Map map, Object key) {
		Integer value = (Integer) map.get(key);
		if (value != null)
			map.put(key, new Integer(value.intValue() + 1));
		else
			map.put(key, new Integer(1));
	}
}