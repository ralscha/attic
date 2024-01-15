package ch.sr.lotto.statistic;

import java.util.*;

import ch.sr.lotto.db.*;


public abstract class AusspielungenStatistik {

	private int totalUngerade;
	private int totalGerade;
	private Map  verteilungSumme;
	private Map  verteilungVerbund;
	private int anzahlAusspielungen = 0;
	private int paar[][] = new int[45][45];
	private int haeufigkeit[] = new int[45];

	// 7 -> 0 bis 6
	private int verteilungHaeufigkeitGerade[] = new int[7];
	private int verteilungTiefe[] = new int[7];
	private int verteilungTiefeEndziffern[] = new int[7];


	private Map  verteilungDreierKlassen;
	private Map  verteilungFuenferKlassen;
	private Map  verteilungNeunerKlassen;

	private Map  verteilungEndZiffernSumme;
	private Map  verteilungQuersumme;
	private Map  verteilungSummeDifferenzen;

	public AusspielungenStatistik() {
		verteilungSumme = new HashMap();
		verteilungVerbund = new HashMap();

		verteilungDreierKlassen = new HashMap();
		verteilungFuenferKlassen = new HashMap();
		verteilungNeunerKlassen = new HashMap();

		verteilungEndZiffernSumme = new HashMap();
		verteilungQuersumme = new HashMap();
		verteilungSummeDifferenzen = new HashMap();
		

		VerbundType[] vb = VerbundType.getTypesAsArray();
		for (int i = 0; i < vb.length; i++) 
			verteilungVerbund.put(vb[i].getExternal(), new Integer(0));
	}

	protected abstract boolean inRange(int nr, int jahr);

	public void updateStatistik(Draw z) {

		int zahlen[];

		if (inRange(z.getNo().intValue(), z.getYear().intValue())) {
			anzahlAusspielungen++;
      
      DrawStatistic ds = new DrawStatistic(z.getNumbers());

			totalGerade +=ds.getGerade();
			totalUngerade += ds.getUngerade();

			//Summen
			incValue(verteilungSumme, new Integer(ds.getSumme()));
			incValue(verteilungEndZiffernSumme,
         			new Integer(ds.getEndZiffernSumme()));
			incValue(verteilungQuersumme, new Integer(ds.getQuersumme()));
			incValue(verteilungSummeDifferenzen,
         			new Integer(ds.getSummeDifferenzen()));

			//Verbund
			incValue(verteilungVerbund, ds.getVerbund().getExternal());

			//Klassen
			incValue(verteilungDreierKlassen,
         			new Integer(ds.getDreierKlassen()));
			incValue(verteilungFuenferKlassen,
         			new Integer(ds.getFuenferKlassen()));
			incValue(verteilungNeunerKlassen,
         			new Integer(ds.getNeunerKlassen()));


			zahlen = z.getNumbers();

			//Häufigkeit
			for (int i = 0; i < 6; i++)
				haeufigkeit[zahlen[i] - 1]++;


			//Paar
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 6; j++)
					if (i != j)
						paar[zahlen[i] - 1][zahlen[j] - 1]++;


			verteilungHaeufigkeitGerade[ds.getGerade()]++;
			verteilungTiefe[ds.getTiefe()]++;
			verteilungTiefeEndziffern[ds.getTiefeEndziffern()]++;
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
		return verteilungFuenferKlassen;
	}
	public Map getVerteilungNeunerKlassen() {
		return verteilungNeunerKlassen;
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