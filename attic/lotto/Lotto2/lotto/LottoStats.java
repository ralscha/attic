package lotto;


 abstract class LottoStats {

    protected int totalungerade;
    protected int totalgerade;

    protected int haeufigkeit[] = new int[45];
    protected int totalsumme[] = new int[235];
	protected int paar[][] = new int[45][45];
    protected int totalverbund[] = new int[11];
	protected int haeufger[] = new int[7];
	protected int anzahlAusspielungen = 0;

	protected abstract boolean inRange(int nr, int jahr);

    public void updateStats(Ziehung z) {
        int zahlen[];

        if (inRange(z.getNr(), z.getJahr())) {
            anzahlAusspielungen++;
            totalgerade   += z.getGerade();
            totalungerade += z.getUngerade();

         	totalverbund[z.getVerbund()]++;
    	    totalsumme[z.getSumme()-21]++;

            haeufger[z.getGerade()]++;

            zahlen = z.getZahlen();

        	for (int i = 0; i < 6; i++)
    		    haeufigkeit[zahlen[i]-1]++;

        	for (int i = 0; i < 6; i++)
    	    	for (int j = 0; j < 6; j++)
    		    	if (i != j)
    			    	paar[zahlen[i]-1][zahlen[j]-1]++;
    	}
	}


    public int getTotalGerade() {
	    return totalgerade;
	}

	public int getTotalUngerade() {
	    return totalungerade;
	}

	public int getAnzahlAusspielungen() {
	    return anzahlAusspielungen;
	}

	public int[] getHaeufigkeit() {
        return haeufigkeit;
	}

	public int[] getTotalSumme() {
	    return totalsumme;
	}

	public int[][] getPaar() {
	    return paar;
	}

	public int[] getTotalVerbund() {
	    return totalverbund;
	}

	public int[] getHaeufigkeitGerade() {
	    return haeufger;
	}

 }
