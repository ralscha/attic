package lotto;

public class LottoGewinnquote extends Gewinnquote {

	public LottoGewinnquote() {
		super();
	}
	
	public LottoGewinnquote(double jackpot, double quote6, double quote5plus, double quote5, 
									 double quote4, double quote3, 
									 int anzahl6, int anzahl5plus, int anzahl5, int anzahl4, int anzahl3) {
		
		super(jackpot, quote6, quote5plus, quote5, quote4, quote3, 0.0,
		      anzahl6, anzahl5plus, anzahl5, anzahl4, anzahl3, 0);
	}

	// Operations
	public double getQuote(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.RICHTIGE_2))
			throw new IllegalArgumentException("RICHTIGE_2 im Lotto nicht vorhanden");
		
		return super.getQuote(index);
	}
		
	public int getAnzahl(GewinnquoteType index) {
		if (index.equals(GewinnquoteType.RICHTIGE_2))
			throw new IllegalArgumentException("RICHTIGE_2 im Lotto nicht vorhanden");

		return super.getAnzahl(index);
	}
	
	public void setQuote(GewinnquoteType index, double quote)	{
		if (index.equals(GewinnquoteType.RICHTIGE_2))
			throw new IllegalArgumentException("RICHTIGE_2 im Lotto nicht vorhanden");

		super.setQuote(index, quote);
	}
	
	public void setAnzahl(GewinnquoteType index, int anzahl) {
		if (index.equals(GewinnquoteType.RICHTIGE_2))
			throw new IllegalArgumentException("RICHTIGE_2 im Lotto nicht vorhanden");

		super.setAnzahl(index, anzahl);
	}
}