import lotto.*;
import lotto.util.*;
import java.util.*;

public class TipMaker {

    public static void main(String args[]) {
        new TipMaker();
    }


	public TipMaker() {
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        DbManager.startReadTransaction();
        Ausspielungen auss = DbManager.getAusspielungen();            
        
        Vector vect = createTips(auss, 100);
    
        Enumeration e = vect.elements();
        while(e.hasMoreElements()) {
            Ziehung zie = (Ziehung)e.nextElement();
            System.out.println(zie);
        }

        DbManager.commitTransaction();                
        DbManager.shutdown();     
    }
	
	
	public Vector createTips(Ausspielungen ausspielungen, int numberOfTips) {
	    Vector tipsVect = new Vector();
        Ziehung zie;

        Random r = new Random();
        int punkte[] = new int[45];
    	int zufall;
	    int i, x, summe;
	    int auswahl[] = new int[6];
    	boolean nichtgefunden;
	    boolean nochmal;
    	boolean wahl[] = new boolean[45];
	    int versuche;

	    int hgerauswahl;
    	int summenauswahl;
	    int verbundauswahl;

    	int verbund[];
	    int summen[];
	    int hger[];
	    int ausstehend[];
        int haeuf[];

    	int verbundtotal = 0;
	    int summentotal = 0;
    	int hgertotal = 0;
	    int ausstehendtotal = 0;
    	int haeufigkeittotal = 0;
	    int punkttotal = 0;

	    for (i = 0; i < 45; i++)
		    punkte[i] = 0;

	    ausstehend = ausspielungen.getAusstehend();
	    haeuf = ausspielungen.getHaeufigkeit(Ausspielungen.ALLE);
	    summen = ausspielungen.getTotalSumme(Ausspielungen.ALLE);
	    verbund = ausspielungen.getTotalVerbund(Ausspielungen.ALLE);
	    hger = ausspielungen.getHaeufigkeitGerade(Ausspielungen.ALLE);

        int haeufigkeit[] = new int[haeuf.length];
        for (x = 0; x < haeuf.length; x++)
            haeufigkeit[x] = haeuf[x];

    	//Hochrechnen der Häufigkeit Zahlen 41 bis 45
    	int anzalle = ausspielungen.getAnzahlAusspielungen(Ausspielungen.ALLE);
    	int anzaus42 = ausspielungen.getAnzahlAusspielungen(Ausspielungen.AUS42);
    	int anzaus45 = ausspielungen.getAnzahlAusspielungen(Ausspielungen.AUS45);

	    haeufigkeit[40] = haeufigkeit[40] * anzalle / anzaus42;
    	haeufigkeit[41] = haeufigkeit[41] * anzalle / anzaus42;
    	haeufigkeit[42] = haeufigkeit[42] * anzalle / anzaus42;
    	haeufigkeit[43] = haeufigkeit[43] * anzalle / anzaus45;
    	haeufigkeit[44] = haeufigkeit[44] * anzalle / anzaus45;

    	//Total ausrechnen
	    for (i = 0; i < 45; i++) {
	    	ausstehendtotal  += ausstehend[i];
		    haeufigkeittotal += haeufigkeit[i];
    		punkte[i] = ausstehend[i]*10 + haeufigkeit[i];
	    	//punkte[i] = ausstehend[i]*5 + haeufigkeit[i];
		    punkttotal += punkte[i];
    	}

    	summentotal = verbundtotal = hgertotal = ausspielungen.getAnzahlAusspielungen(Ausspielungen.ALLE);

	    for (int n = 0; n < numberOfTips; n++) {
		    nochmal = false;
    		do {
		    	// Auswahl gerade ungerade
			    zufall = Math.abs((r.nextInt() % hgertotal) + 1);
    			x = 0;
	    		summe = hger[x];
		    	while (summe < zufall)
			    	summe += hger[++x];
    			hgerauswahl = x;

	    		// Auswahl der Summe
		    	zufall = Math.abs((r.nextInt() % summentotal) + 1);
			    x = 0;
    			summe = summen[x];
	    		while (summe < zufall)
		    		summe += summen[++x];
			    summenauswahl = x+21;

    			// Auswahl des Verbunds
	    		zufall = Math.abs((r.nextInt() % verbundtotal) + 1);
		    	x = 0;
			    summe = verbund[x];
    			while (summe < zufall)
	    			summe += verbund[++x];
		    	verbundauswahl = x;

    			versuche = 0;
	    		// Ziehung der Lottozahlen
		    	do {
	    			nichtgefunden = false;
		    		for (i = 0; i < 45; i++)
			    		wahl[i] = false;

				    for (i = 0; i < 6; i++) {
	    				do {
			    			zufall = Math.abs((r.nextInt() % punkttotal) + 1);
				    		x = 0;
					    	summe = punkte[x];
						    while (summe < zufall)
    							summe += punkte[++x];

	    				} while (wahl[x]);
		    			wahl[x] = true;
			    	}

				    x = 0;
    				for (i = 0; i < 45; i++)
	    				if (wahl[i])
		    				auswahl[x++] = i + 1;


                    zie = new Ziehung(new GregorianCalendar(), 0, 0, auswahl, 0, null);

		    		//Testen
		    		if (zie.getGerade() != hgerauswahl)
			    		nichtgefunden = true;
				    if (zie.getVerbund() != verbundauswahl)
					    nichtgefunden = true;
    				if ((summenauswahl - zie.getSumme() > 	10) || (summenauswahl - zie.getSumme() < -10))
	    				nichtgefunden = true;

		    		if (nichtgefunden) {
				    	versuche++;
					    if (versuche >= 100) {
	    					nochmal = true;
		    				nichtgefunden = false;
			    		}
				    }
    				else
	    				nochmal = false;

		    	} while (nichtgefunden);

    		} while (nochmal);


            tipsVect.addElement(zie);

    	} // Hauptschleife

        return tipsVect;
	    
	}
	
}
