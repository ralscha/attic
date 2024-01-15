public class Ziehung {

	// Attributes
	private int[] zahlen;
	private int zusatzZahl;
	private int nr;
	private int jahr;
	private String datum;
	private String joker;
	
	public Ziehung(String nr, String jahr, String datum, String z1, String z2, String z3,
		            String z4, String z5, String z6, String zusatzZahl, String joker) throws NumberFormatException {

		this.nr = Integer.parseInt(nr);
		this.jahr = Integer.parseInt(jahr);
		this.datum = datum;
		
		zahlen = new int[6];
		zahlen[0] = Integer.parseInt(z1); 
		zahlen[1] = Integer.parseInt(z2);
		zahlen[2] = Integer.parseInt(z3);
		zahlen[3] = Integer.parseInt(z4);
		zahlen[4] = Integer.parseInt(z5);
		zahlen[5] = Integer.parseInt(z6);
			
		this.zusatzZahl = Integer.parseInt(zusatzZahl);
		this.joker = joker;
	}

	
	public int getNr() {
		return nr;
	}
	
	public int getJahr() {
		return jahr;
	}
	
	public String getDatum() {
		return datum;
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
	
	
    public int getWin(int tip[]) {
        int i,j,win = 0;

        for (i = 0; i < 6; i++)
            for (j = 0; j < 6; j++)
                if (tip[i] == zahlen[j]) win++;

        if (win == 5) {
            for (i = 0; i < 6; i++)
                if (tip[i] == zusatzZahl) return(51);
        }

        return (win);

    }
    
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (nr < 100)
		   sb.append(" ");
		if (nr < 10)
		   sb.append(" ");
		sb.append(nr);
		sb.append("/");
		sb.append(jahr);
		sb.append(", ");
		sb.append(datum);		
		return sb.toString();
	}
	
}