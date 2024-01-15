package lotto;


import lotto.statistik.*;
import java.util.*;
import com.odi.*;
import com.odi.util.*;


public class Ziehung implements Comparable {

	public boolean showSlotName = false; // look at toString method ...

	// Attributes
	private int[] zahlen;
	private int zusatzZahl;
	private int nr;
	private int jahr;
	private long datum;
	private String joker;
	private LottoGewinnquote lottoGewinnquote;
	private JokerGewinnquote jokerGewinnquote;
	private ZiehungStatistik ziehungStatistik;


	////////////////////////////////////////////////////////
	// Constructor
	public Ziehung() {
		zahlen = new int[6];
		for (int i = 0; i < 6; i++) 
			zahlen[i] = 0;
		
		zusatzZahl = 0;
		nr = 0;
		jahr = 0;
		datum = 0;
		joker = null;
		lottoGewinnquote = null;
		jokerGewinnquote = null;
		ziehungStatistik = null;
	}


	public Ziehung(int nr, int jahr, Date datum, int[] zahlen, int zusatzZahl, 
						String joker, LottoGewinnquote lottoGewinnquote, JokerGewinnquote jokerGewinnquote) {

		setNr(nr);
		setJahr(jahr);
		setDatum(datum);
		setZahlen(zahlen);
		setZusatzZahl(zusatzZahl);
		setJoker(joker);
		setLottoGewinnquote(lottoGewinnquote);
		setJokerGewinnquote(jokerGewinnquote);
		ziehungStatistik = new ZiehungStatistik(zahlen);
	}

	public void setNr(int nr) {
		this.nr = nr;
	}
	
	public void setJahr(int jahr) {
		this.jahr = jahr;
	}
	
	public void setDatum(Date datum) {
		this.datum = datum.getTime();
	}

	public void setZahlen(int zahlen[]) {
		if (zahlen.length != 6)
			throw new IllegalArgumentException("laenge von feld zahlen ist "+zahlen.length+" anstatt 6");
		
		this.zahlen = new int[6];
		for (int i = 0; i < 6; i++) 
			this.zahlen[i] = zahlen[i];	
			
		ziehungStatistik = new ZiehungStatistik(zahlen);	
	}
	
	public void setZusatzZahl(int zusatzZahl) {
		this.zusatzZahl = zusatzZahl;
	}

	public void setJoker(String joker) {
		this.joker = joker;
	}

	public void setJokerGewinnquote(JokerGewinnquote jokerGewinnquote) {
		this.jokerGewinnquote = jokerGewinnquote;
	}
	
	public void setLottoGewinnquote(LottoGewinnquote lottoGewinnquote) {
		this.lottoGewinnquote = lottoGewinnquote;
	}
	
	public boolean isWednesday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date(datum));
		
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
      	return true;
      else
         return false;		
	}
	
	
	public int getNr() {
		return nr;
	}
	
	public int getJahr() {
		return jahr;
	}
	
	public Date getDatum() {
		return (new Date(datum));
	}

	public int[] getZahlen() {
		int z[] = new int[6];

	   for (int i = 0; i < 6; i++)
    		z[i] = zahlen[i];

    	return z;
	}

	
	public int getZusatzZahl() {
		return zusatzZahl;
	}
	
	public String getJoker() {
		return joker;
	}
	
	public JokerGewinnquote getJokerGewinnquote() {
		return jokerGewinnquote;
	}
	
	public LottoGewinnquote getLottoGewinnquote() {
		return lottoGewinnquote;
	}
	
	public ZiehungStatistik getZiehungStatistik() {
		return ziehungStatistik;
	}
	
	public String toString() {

		StringBuffer ret;
		ret = new StringBuffer(super.toString());

		if (showSlotName)
			ret.append(" nr");
		ret.append(" ").append(nr);
		
		if (showSlotName)
			ret.append(" jahr");
		ret.append(" ").append(jahr);
		
		if (showSlotName)
			ret.append(" datum");
		ret.append(" ").append(datum);
		
		if (showSlotName)
			ret.append(" zahlen");
		ret.append(" ");
		for (int i = 0; i < 6; i++) 
			ret.append(zahlen[i]).append(" ");
		
		if (showSlotName)
			ret.append(" zusatzZahl");
		ret.append(" ").append(zusatzZahl);
				
		if (showSlotName)
			ret.append(" joker");
		ret.append(" ").append(joker);
		
		if (showSlotName)
			ret.append(" lottoGewinnquote");
		ret.append(" ").append(lottoGewinnquote);
	
		if (showSlotName)
			ret.append(" jokerGewinnquote");
		ret.append(" ").append(jokerGewinnquote);
		
		if (showSlotName)
			ret.append(" ziehungStatistik");
		ret.append(" ").append(ziehungStatistik);
	
		return ret.toString();
	}

   public boolean equals(Object obj) {
   	try {
   		if (compareTo(obj) == 0)
   			return true;
      } catch (ClassCastException e) { }
      return false;
   }
	
	public int compareTo(Object obj) {
     	Ziehung z = (Ziehung)obj; 
      if (jahr < z.getJahr())
      	return -1;
		else if (jahr > z.getJahr())
			return 1;
		else {
			if (nr < z.getNr())
				return -1;
			else if (nr > z.getNr())
				return 1;
			else
				return 0;
		}			
	}
	
	public int hashCode() {
		return (jahr * 1000 + nr);
	}
	
}