package lotto;

import com.odi.*;
import com.odi.util.*;
import lotto.statistik.*;
import java.util.*;

public class Ausspielungen {

	public boolean showSlotName = false; // look at toString method ...

	// Attributes
	private OSVectorList ziehungen;
	private int[] ausstehend;
	private OSHashMap aussStats;

	public Ausspielungen() {
		ausstehend = new int[45];
		ziehungen = new OSVectorList();
		aussStats = new OSHashMap();
		aussStats.put(AusspielungenType.ALLE.getExternal(), new AusspielungenStatistik_Alle());
		aussStats.put(AusspielungenType.AUS42.getExternal(), new AusspielungenStatistik_42());
		aussStats.put(AusspielungenType.AUS45.getExternal(), new AusspielungenStatistik_45());
		aussStats.put(AusspielungenType.AB1997.getExternal(), new AusspielungenStatistik_1997());
		aussStats.put(AusspielungenType.CURRENT_YEAR.getExternal(), new AusspielungenStatistik_CurrentYear());
	}

	public void addZiehung(int nr, int jahr, Date datum, int zahlen[], int zusatzZahl,
                       	String joker) {
		addZiehung(nr, jahr, datum, zahlen, zusatzZahl, joker, null, null);
	}

	public void addZiehung(int nr, int jahr, Date datum, int zahlen[], int zusatzZahl,
                       	String joker, LottoGewinnquote lgq, JokerGewinnquote jgq) {

		Ziehung z = new Ziehung(nr, jahr, datum, zahlen, zusatzZahl, joker, lgq, jgq);
		addZiehung(z);
	}

	public void addZiehung(Ziehung zie) {
		ziehungen.add(zie);

		Iterator it = aussStats.values().iterator();
		while (it.hasNext()) {
			((AusspielungenStatistik) it.next()).updateStatistik(zie);
		}

		for (int i = 0; i < 45; i++)
			ausstehend[i]++;

		int zahlen[] = zie.getZahlen();
		for (int i = 0; i < 6; i++)
			ausstehend[zahlen[i] - 1] = 0;
	}

	public AusspielungenStatistik getAusspielungenStatistik(AusspielungenType at) {		
		return (AusspielungenStatistik) aussStats.get(at.getExternal());
	}

	public int[] getAusstehend() {
		return ausstehend;
	}

	public Iterator iterator() {
		return ziehungen.iterator();
	}

	public ListIterator getListIterator(int pos) {
		return ziehungen.listIterator(pos);
	}


	public Ziehung getLastZiehung() {
		return (Ziehung) ziehungen.get(ziehungen.size()-1);
	}

	public String toString() {

		StringBuffer ret;
		ret = new StringBuffer(super.toString());

		if (showSlotName)
			ret.append(" ziehungen");
		ret.append(" ").append(ziehungen);

		if (showSlotName)
			ret.append(" ausstehend");
		ret.append(" ").append(ausstehend);

		return ret.toString();
	}

}