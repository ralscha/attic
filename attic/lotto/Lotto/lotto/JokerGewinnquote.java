package lotto;

public class JokerGewinnquote extends Gewinnquote {

	public JokerGewinnquote() {
		super();
	}
	
	public JokerGewinnquote(double jackpot, double quote6, double quote5, 
									 double quote4, double quote3, double quote2, 
									 int anzahl6, int anzahl5, int anzahl4, int anzahl3, int anzahl2) {
		
		super(jackpot, quote6, 0, quote5, quote4, quote3, quote2,
		      anzahl6, 0, anzahl5, anzahl4, anzahl3, anzahl2);
	}

	// Operations
	public double getQuote(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			throw new IllegalArgumentException("RICHTIGE_5_PLUS im Joker nicht vorhanden");
		
		return super.getQuote(index);
	}
		
	public int getAnzahl(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			throw new IllegalArgumentException("RICHTIGE_5_PLUS im Joker nicht vorhanden");

		return super.getAnzahl(index);
	}
	
	public void setQuote(GewinnquoteType index, double quote)	{
		if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			throw new IllegalArgumentException("RICHTIGE_5_PLUS im Joker nicht vorhanden");

		super.setQuote(index, quote);
	}
	
	public void setAnzahl(GewinnquoteType index, int anzahl) {
		if (index.equals(GewinnquoteType.RICHTIGE_5_PLUS))
			throw new IllegalArgumentException("RICHTIGE_5_PLUS im Joker nicht vorhanden");

		super.setAnzahl(index, anzahl);
	}
}