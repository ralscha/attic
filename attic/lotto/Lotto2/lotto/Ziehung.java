package lotto;

import lotto.util.*;
import java.util.Calendar;

public class Ziehung {
	public final static int NICHTS = 0;
	public final static int ZWILLING = 1;
	public final static int DRILLING = 2;
	public final static int VIERLING = 3;
	public final static int FUENFLING = 4;
	public final static int SECHSLING = 5;
	public final static int DOPPELZWILLING = 6;
	public final static int DOPPELDRILLING = 7;
	public final static int DRILLINGZWILLING = 8;
	public final static int DREIFACHZWILLING = 9;
	public final static int VIERLINGZWILLING = 10;

	private int zahlen[] = new int[6];
	private int zz;
	private int nr;
	private int jahr;
	private int vb;
	private int gerade;
	private int summe;

	private int year, month, date;
    private boolean wednesday;
	
	private String joker;
	
	private LottoGewinnquote lottogq;
	private JokerGewinnquote jokergq;

    public Ziehung() {
        year = month = date = 0;
        wednesday = false;
        zz = nr = jahr = vb = gerade = summe = 0;
        joker = null;
        for (int i = 0; i < 6; i++)
            zahlen[i] = 0;
        lottogq = null;
        jokergq = null;
    }

	public Ziehung(Calendar cal, int nr, int jahr,
	               int zahlen[], int zz, String joker) {
	                
	    setZahlen(zahlen);
	    setZZ(zz);
	    setJahr(jahr);
	    setNr(nr);
	    setDate(cal);
	    setJoker(joker);
        lottogq = null;
        jokergq = null;    	    
	}

	public Ziehung(Calendar cal, int nr, int jahr,
	               int zahlen[], int zz, String joker,
	               LottoGewinnquote lgq, JokerGewinnquote jgq) {
	                
	    setZahlen(zahlen);
	    setZZ(zz);
	    setJahr(jahr);
	    setNr(nr);
	    setDate(cal);
	    setJoker(joker);
        lottogq = lgq;
        jokergq = jgq;
	}



    public boolean equals(Object obj) {
        try { 
            Ziehung z = (Ziehung)obj; 
            return (jahr == z.getJahr()) && (nr == z.getNr()); 
        } catch (ClassCastException e) { }
        return false;
    }

    public String toString() {
	    StringBuffer sb = new StringBuffer();
	    sb.append(date+"."+(month+1)+"."+year).append(";");
	    sb.append(nr).append(";");
	    sb.append(jahr).append(";");
	    for (int i = 0; i < 6; i++)
	        sb.append(zahlen[i]).append(";");
	    sb.append(zz).append(";");
	    if (joker != null)
    	    sb.append(joker).append(";");
    	else
    	    sb.append(";");
  	    sb.append(summe).append(";");
	    sb.append(vb).append(";");
	    sb.append(gerade);
        return sb.toString();
    }    

    public boolean isWednesday() {
        return(wednesday);
    }


    public LottoGewinnquote getLottoGewinnquote() {
        return lottogq;
    }

    public void setLottoGewinnquote(LottoGewinnquote lgq) {
        lottogq = lgq;
    }       

    public JokerGewinnquote getJokerGewinnquote() {
        return jokergq;
    }

    public void setJokerGewinnquote(JokerGewinnquote jgq) {
        jokergq = jgq;
    }       

    public String getJoker() {
        return joker;
    }

    public void setJoker(String joker) {
        this.joker = joker;
    }

    public Calendar getDate() {
        return (new MyCalendar(year, month, date));
    }

    public void setDate(Calendar cal) {        
        year  = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date  = cal.get(Calendar.DATE);
        
        MyCalendar help = new MyCalendar(year, month, date);
        
        if (help.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
            wednesday = true;
        else
            wednesday = false;
            
        help = null;
    }


	public int getJahr() {
		return jahr;
	}
	
	public void setJahr(int jahr) {
	    this.jahr = jahr;
	}

	public int getNr() {
		return nr;
	}
	
	public void setNr(int nr) {
	    this.nr = nr;
	}

	public int getZZ() {
		return zz;
	}
	
	public void setZZ(int zz) {
	    this.zz = zz;
	}

	public int[] getZahlen() {
	    int z[] = new int[6];

	    for (int i = 0; i < 6; i++)
    		z[i] = zahlen[i];

    	return z;
	}

    public void setZahlen(int zahlen[]) {
	    gerade = summe = 0;
    	for (int i = 0; i < 6; i++) {
		    this.zahlen[i] = zahlen[i];
    		if (!isOdd(zahlen[i]))
	    		gerade++;
		    summe += zahlen[i];
		}
        this.vb = sucheVerbund(zahlen);
	}

	public int getGerade() {
		return gerade;
	}

	public int getUngerade() {
		return 6-gerade;
	}

	public int getSumme() {
		return summe;
	}

	public int getVerbund() {
		return vb;
	}


	private int sucheVerbund(int zahlen[]) {
	    int summe = 0;

	    //2 minus 1
    	if ((zahlen[1] - zahlen[0]) == 1)
	    	summe += 10000;
    	//3 minus 2
	    if ((zahlen[2] - zahlen[1]) == 1)
		    summe += 1000;
    	//4 minus 3
	    if ((zahlen[3] - zahlen[2]) == 1)
		    summe += 100;
    	//5 minus 4
	    if ((zahlen[4] - zahlen[3]) == 1)
		    summe += 10;
    	//6 minus 5
	    if ((zahlen[5] - zahlen[4]) == 1)
		    summe += 1;

	    switch (summe) {
	    	case     1:
		    case    10:
    		case   100:
	    	case  1000:
		    case 10000: return ZWILLING;

    		case    11:
	    	case   110:
		    case  1100:
    		case 11000: return DRILLING;

		    case   111:
    		case  1110:
	    	case 11100: return VIERLING;

    		case  1111:
	    	case 11110: return FUENFLING;

    		case 11111: return SECHSLING;

    		case   101:
	    	case  1001:
		    case  1010:
    		case 10001:
	    	case 10010:
		    case 10100: return DOPPELZWILLING;

		    case  1011:
    		case  1101:
	    	case 10011:
		    case 10110:
    		case 11001:
	    	case 11010: return DRILLINGZWILLING;

		    case 10101: return DREIFACHZWILLING;

    		case 10111:
	    	case 11101: return VIERLINGZWILLING;

	    	case 11011: return DOPPELDRILLING;

	    	default : return NICHTS;
    	}
    }

	private boolean isOdd(int z) {
	    return (z % 2 != 0);
	}

}
