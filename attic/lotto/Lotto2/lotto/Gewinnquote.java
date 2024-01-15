package lotto;

import java.sql.*;

public class Gewinnquote
{
    public final static int J   = 5;
    public final static int R6  = 0;
	public final static int R5P = 1;
	public final static int R5  = 2;
	public final static int R4  = 3;
	public final static int R3  = 4;

	private int nr, jahr;
	private double jackpot, r6quote, r5Pquote, r5quote, r4quote, r3quote;
    private int  r6anzahl, r5Panzahl, r5anzahl, r4anzahl, r3anzahl;

    public Gewinnquote(ResultSet rs) throws SQLException {
        nr       = rs.getInt(1);
        jahr     = rs.getInt(2);
        jackpot  = rs.getDouble(3);
        r6anzahl = rs.getInt(4);
        r6quote  = rs.getDouble(5);
        r5Panzahl= rs.getInt(6);
        r5Pquote = rs.getDouble(7);
        r5anzahl = rs.getInt(8);
        r5quote  = rs.getDouble(9);
        r4anzahl = rs.getInt(10);
        r4quote  = rs.getDouble(11);
        r3anzahl = rs.getInt(12);
        r3quote  = rs.getDouble(13);
    }

	public Gewinnquote(int nr, int jahr, double jackpot, int r6a, double r6q,
	                   int r5pa, double r5pq, int r5a, double r5q, int r4a,
	                   double r4q, int r3a, double r3q)
	{
	    this.nr       = nr;
        this.jahr     = jahr;
        this.jackpot  = jackpot;
        this.r6anzahl = r6a;
        this.r6quote  = r6q;
        this.r5Panzahl= r5pa;
        this.r5Pquote = r5pq;
        this.r5anzahl = r5a;
        this.r5quote  = r5q;
        this.r4anzahl = r4a;
        this.r4quote  = r4q;
        this.r3anzahl = r3a;
        this.r3quote  = r3q;
	}

	public void prepareSQLStmt(PreparedStatement sqlS) throws SQLException {
        sqlS.setInt(1,   nr);
        sqlS.setInt(2,   jahr);
        sqlS.setDouble(3, jackpot);
        sqlS.setInt(4,  r6anzahl);
        sqlS.setDouble(5, r6quote);
        sqlS.setInt(6,  r5Panzahl);
        sqlS.setDouble(7, r5Pquote);
        sqlS.setInt(8,  r5anzahl);
        sqlS.setDouble(9, r5quote);
        sqlS.setInt(10, r4anzahl);
        sqlS.setDouble(11,r4quote);
        sqlS.setInt(12, r3anzahl);
        sqlS.setDouble(13,r3quote);

	}

    public boolean equals(Object obj)
    {
	    if (obj instanceof Gewinnquote)
	    {
	        Gewinnquote z = (Gewinnquote)obj;
    	    return (jahr == z.getJahr()) && (nr == z.getNr());
	    }
    	return false;
    }

    public String toString()
    {
	    return getClass().getName() + "[" + nr + "/" + jahr + "]";
    }


	public int getJahr()
	{
		return jahr;
	}

	public int getNr()
	{
		return nr;
	}

	public double getQuote(int w)
	{
	    switch(w)
	    {
	        case J   : return (jackpot);
	        case R6  : return (r6quote);
	        case R5P : return (r5Pquote);
	        case R5  : return (r5quote);
	        case R4  : return (r4quote);
	        case R3  : return (r3quote);
	        default  : return(-1);
	    }
	}

	public int getAnzahl(int w)
	{
	    switch(w)
	    {
	        case R6  : return (r6anzahl);
	        case R5P : return (r5Panzahl);
	        case R5  : return (r5anzahl);
	        case R4  : return (r4anzahl);
	        case R3  : return (r3anzahl);
	        default  : return(-1);
	    }
	}



}

