package lotto;


public class JokerGewinnquote {
    public final static int R6        = 0;
	public final static int R5        = 1;
	public final static int R4        = 2;
	public final static int R3        = 3;
	public final static int R2        = 4;
    public final static int JACKPOT   = 5;

	private double jackpot, r6quote,  r5quote,  r4quote,  r3quote,  r2quote;
    private int             r6anzahl, r5anzahl, r4anzahl, r3anzahl, r2anzahl;

    public JokerGewinnquote() {
        this.jackpot  = 0;
        this.r6anzahl = 0;
        this.r6quote  = 0;
        this.r5anzahl = 0;
        this.r5quote  = 0;
        this.r4anzahl = 0;
        this.r4quote  = 0;
        this.r3anzahl = 0;
        this.r3quote  = 0;
        this.r2anzahl = 0;
        this.r2quote  = 0;
        
    }

	public JokerGewinnquote(double jackpot, int r6a, double r6q,
	                   int r5a, double r5q, int r4a, double r4q, 
	                   int r3a, double r3q, int r2a, double r2q) {
        this.jackpot  = jackpot;
        this.r6anzahl = r6a;
        this.r6quote  = r6q;
        this.r5anzahl = r5a;
        this.r5quote  = r5q;
        this.r4anzahl = r4a;
        this.r4quote  = r4q;
        this.r3anzahl = r3a;
        this.r3quote  = r3q;
        this.r2anzahl = r2a;
        this.r2quote  = r2q;

	}

	public double getQuote(int w) {
	    switch(w) {
	        case JACKPOT   : return (jackpot);
	        case R6  : return (r6quote);
	        case R5  : return (r5quote);
	        case R4  : return (r4quote);
	        case R3  : return (r3quote);
	        case R2  : return (r2quote);
	        default  : return(-1);
	    }
	}

	public int getAnzahl(int w) {
	    switch(w) {
	        case R6  : return (r6anzahl);
	        case R5  : return (r5anzahl);
	        case R4  : return (r4anzahl);
	        case R3  : return (r3anzahl);
	        case R2  : return (r2anzahl);
	        default  : return(-1);
	    }
	}

    public void set(int w, int anzahl, double quote) {
	    switch(w) {
	        case JACKPOT : jackpot  = quote; break;
	        case R6  : r6anzahl = anzahl;
	                   r6quote  = quote;
	                   break;
	        case R5  : r5anzahl = anzahl;
	                   r5quote  = quote;
	                   break;
	        case R4  : r4anzahl = anzahl;
	                   r4quote  = quote;
	                   break;
	        case R3  : r3anzahl = anzahl;
	                   r3quote  = quote;
	                   break;
	        case R2  : r2anzahl = anzahl;
	                   r2quote  = quote;
	                   break;
	        default  : break;
	    }
    }

    public String toString() {
	    StringBuffer sb = new StringBuffer();
	    sb.append(jackpot).append(";");
	    sb.append(r6anzahl).append(";");
	    sb.append(r6quote).append(";");
	    sb.append(r5anzahl).append(";");
	    sb.append(r5quote).append(";");
	    sb.append(r4anzahl).append(";");
	    sb.append(r4quote).append(";");
	    sb.append(r3anzahl).append(";");
	    sb.append(r3quote).append(";");
   	    sb.append(r2anzahl).append(";");
	    sb.append(r2quote);
        return sb.toString();
    }


}

