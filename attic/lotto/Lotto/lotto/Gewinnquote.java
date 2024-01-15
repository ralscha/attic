package lotto;

import java.util.Date;

public abstract class Gewinnquote{

	public boolean showSlotName=false; // look at toString method ...
	
	// Attributes
	private double jackpot;	
	private double quote6;	
	private double quote5plus;	
	private double quote5;	
	private double quote4;
	private double quote3;
	private double quote2;
	
	private int anzahl6;
	private int anzahl5plus;	
	private int anzahl5;
	private int anzahl4;
	private int anzahl3;
	private int anzahl2;
	
	////////////////////////////////////////////////////////
	// Constructor
	public Gewinnquote() {
		jackpot = 0;
		quote6 = 0;
		quote5plus = 0;
		quote5 = 0;
		quote4 = 0;
		quote3 = 0;
		quote2 = 0;
		anzahl6 = 0;
		anzahl5plus = 0;
		anzahl5 = 0;
		anzahl4 = 0;
		anzahl3 = 0;
		anzahl2 = 0;
		
	}

	public Gewinnquote(double jackpot, double quote6, double quote5plus, double quote5,
		                double quote4, double quote3, double quote2,
		                int anzahl6, int anzahl5plus, int anzahl5, int anzahl4, 
		                int anzahl3, int anzahl2) {
		                	
		this.jackpot = jackpot;
		this.quote6  = quote6;		   
		this.quote5plus  = quote5plus;
		this.quote5  = quote5;
		this.quote4  = quote4;
		this.quote3  = quote3;
		this.quote2  = quote2;		                
		                
		this.anzahl6 = anzahl6;                
		this.anzahl5plus = anzahl5plus;	
		this.anzahl5 = anzahl5;
		this.anzahl4 = anzahl4;
		this.anzahl3 = anzahl3;
		this.anzahl2 = anzahl2;
		                
	}
	
	// Operations
	public double getQuote(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.JACKPOT))
			return jackpot;
		else if (index.equals(GewinnquoteType.RICHTIGE_6))
			return quote6;
		else if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			return quote5plus;	
		else if (index.equals(GewinnquoteType.RICHTIGE_5))
			return quote5;
		else if (index.equals(GewinnquoteType.RICHTIGE_4))
			return quote4;
		else if (index.equals(GewinnquoteType.RICHTIGE_3))
			return quote3;
		else if (index.equals(GewinnquoteType.RICHTIGE_2))
			return quote2;
		
		return -1;
	}
		
	public int getAnzahl(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.JACKPOT))
			throw new IllegalArgumentException("JACKPOT hat keine Anzahl");
		
		else if (index.equals(GewinnquoteType.RICHTIGE_6))
			return anzahl6;
		else if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			return anzahl5plus;	
		else if (index.equals(GewinnquoteType.RICHTIGE_5))
			return anzahl5;
		else if (index.equals(GewinnquoteType.RICHTIGE_4))
			return anzahl4;
		else if (index.equals(GewinnquoteType.RICHTIGE_3))
			return anzahl3;
		else if (index.equals(GewinnquoteType.RICHTIGE_2))
			return anzahl2;
		
		return -1;
	}
	
	public void setQuote(GewinnquoteType index, double quote)	{
		if (index.equals(GewinnquoteType.JACKPOT))
			jackpot = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_6))
			quote6 = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			quote5plus = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_5))
			quote5 = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_4))
			quote4 = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_3))
			quote3 = quote;
		else if (index.equals(GewinnquoteType.RICHTIGE_2))
			quote2 = quote;
	}
	
	public void setAnzahl(GewinnquoteType index, int anzahl) {
		if (index.equals(GewinnquoteType.JACKPOT))
			throw new IllegalArgumentException("JACKPOT hat keine Anzahl");
		
		else if (index.equals(GewinnquoteType.RICHTIGE_6))
			anzahl6 = anzahl;
		else if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			anzahl5plus = anzahl;
		else if (index.equals(GewinnquoteType.RICHTIGE_5))
			anzahl5 = anzahl;
		else if (index.equals(GewinnquoteType.RICHTIGE_4))
			anzahl4 = anzahl;
		else if (index.equals(GewinnquoteType.RICHTIGE_3))
			anzahl3 = anzahl;
		else if (index.equals(GewinnquoteType.RICHTIGE_2))
			anzahl2 = anzahl;
	}

		
	public String toString() {
		StringBuffer ret;
		ret = new StringBuffer(super.toString());
		
		if (showSlotName)
			ret.append(" jackpot");
		ret.append(" ").append(jackpot);
		
		if (showSlotName)
			ret.append(" quote6");
		ret.append(" ").append(quote6);
		
		if (showSlotName)
			ret.append(" quote5plus");
		ret.append(" ").append(quote5plus);
		
		if (showSlotName)
			ret.append(" quote5");
		ret.append(" ").append(quote5);

		if (showSlotName)
			ret.append(" quote4");
		ret.append(" ").append(quote4);
		
		if (showSlotName)
			ret.append(" quote3");
		ret.append(" ").append(quote3);
		
		if (showSlotName)
			ret.append(" quote2");
		ret.append(" ").append(quote2);

		
		if (showSlotName)
			ret.append(" anzahl6");
		ret.append(" ").append(anzahl6);
		
		if (showSlotName)
			ret.append(" anzahl5plus");
		ret.append(" ").append(anzahl5plus);
		
		if (showSlotName)
			ret.append(" anzahl5");
		ret.append(" ").append(anzahl5);
		
		if (showSlotName)
			ret.append(" anzahl4");
		ret.append(" ").append(anzahl4);
		
		if (showSlotName)
			ret.append(" anzahl3");
		ret.append(" ").append(anzahl3);
		
		if (showSlotName)
			ret.append(" anzahl2");
		ret.append(" ").append(anzahl2);

		
		return ret.toString();
	}
}