#include "ziehung.h"

void ZiehungderLottozahlen(CLottozahlen& lottozahlen, ofstream& out)
{
	CLottozahlen ziehungen;
	
	INTARRAY45 punkte;
	int zufall;
	int i, x, summe;
	INTARRAY6 auswahl;
	BOOL nichtgefunden;
	BOOL nochmal;
	BOOL wahl[45];
	int versuche;

	int hgerauswahl;
	int summenauswahl;
	int verbundauswahl;

	VERBUNDARRAY verbund;
	SUMARRAY summen;
	GERARRAY hger;
	INTARRAY45 ausstehend;
	INTARRAY45 haeufigkeit;

	int verbundtotal = 0;
	int summentotal = 0;
	int hgertotal = 0;
	int ausstehendtotal = 0;
	int haeufigkeittotal = 0;
	int punkttotal = 0;
	for (i = 0; i < 45; i++)
		punkte[i] = 0;

	srand((unsigned)time(NULL));

	lottozahlen.GetAusstehend(ausstehend);
	lottozahlen.GetHaeufigkeit(haeufigkeit);
	lottozahlen.GetTotalSumme(summen);
	lottozahlen.GetTotalVerbund(verbund);
	lottozahlen.GetTotalGer(hger);

	//Hochrechnen der Häufigkeit Zahlen 41 bis 45
	long help;
	help = (long)haeufigkeit[40] * (long)lottozahlen.GetAnzahlAusspielungen()
									/ (long)(lottozahlen.GetAnzahlAusspielungen()-475);
	haeufigkeit[40] = (int)help;

	help = (long)haeufigkeit[41] * (long)lottozahlen.GetAnzahlAusspielungen()
									/ (long)(lottozahlen.GetAnzahlAusspielungen()-475);
	haeufigkeit[41] = (int)help;

	help = (long)haeufigkeit[42] * (long)lottozahlen.GetAnzahlAusspielungen()
									/ (long)(lottozahlen.GetAnzahlAusspielungen()-827);
	haeufigkeit[42] = (int)help;

	help = (long)haeufigkeit[43] * (long)lottozahlen.GetAnzahlAusspielungen()
									/ (long)(lottozahlen.GetAnzahlAusspielungen()-827);
	haeufigkeit[43] = (int)help;

	help = (long)haeufigkeit[44] * (long)lottozahlen.GetAnzahlAusspielungen()
									/ (long)(lottozahlen.GetAnzahlAusspielungen()-827);
	haeufigkeit[44] = (int)help;

	//Total ausrechnen
	for (i = 0; i < 45; i++)
	{
		ausstehendtotal  += ausstehend[i];
		haeufigkeittotal += haeufigkeit[i];
		punkte[i] = ausstehend[i]*10 + haeufigkeit[i];
		//punkte[i] = ausstehend[i]*5 + haeufigkeit[i];
		punkttotal += punkte[i];
	}
		
	summentotal = verbundtotal = hgertotal = lottozahlen.GetAnzahlAusspielungen();
	
	// Eingabe Anzahl
	int anzahlzr;
	cout << "Wieviele Zahlenreihen sollen gezogen werden : ";
	cin >> anzahlzr;

	for (int n = 0; n < anzahlzr; n++)
	{
		nochmal = FALSE;
		do
		{
			// Auswahl gerade ungerade
			zufall = rand() % hgertotal + 1;
			x = 0;
			summe = hger[x];
			while (summe < zufall)
				summe += hger[++x];
			hgerauswahl = x;

			// Auswahl der Summe
			zufall = rand() % summentotal + 1;
			x = 0;
			summe = summen[x];
			while (summe < zufall)
				summe += summen[++x];
			summenauswahl = x+21;

			// Auswahl des Verbunds
			zufall = rand() % verbundtotal + 1;
			x = 0;
			summe = verbund[x];
			while (summe < zufall)
				summe += verbund[++x];
			verbundauswahl = x;

			versuche = 0;
			// Ziehung der Lottozahlen
			do
			{
				nichtgefunden = FALSE;
				for (i = 0; i < 45; i++)
					wahl[i] = FALSE;

				for (i = 0; i < 6; i++)
				{
					do
					{	
						zufall = rand() % punkttotal + 1;
						x = 0;
						summe = punkte[x];
						while (summe < zufall)
							summe += punkte[++x];

					} while (wahl[x]);
					wahl[x] = TRUE;
				}

				x = 0;
				for (i = 0; i < 45; i++)
					if (wahl[i])
						auswahl[x++] = i + 1;

				CZiehung zie(auswahl, 0, 0, 0);

				//Testen
				if (zie.GetGerade() != hgerauswahl)
					nichtgefunden = TRUE;
				if (zie.GetVerbund() != verbundauswahl)
					nichtgefunden = TRUE;
				if ((summenauswahl - zie.GetSumme() > 	10) || (summenauswahl - zie.GetSumme() < -10))
					nichtgefunden = TRUE;

				if (nichtgefunden)
				{
					versuche++;
					if (versuche >= 100)
					{
						nochmal = TRUE;
						nichtgefunden = FALSE;
					}
				}
				else
					nochmal = FALSE;

			} while (nichtgefunden);

		} while (nochmal);

		ziehungen.AddZiehung(auswahl, 99, n, 1994);
		cout.width(3);
		cout << n << ". Zahlenreihe : ";
		for (i = 0; i < 6; i++)
		{
			cout.width(4);
			cout << auswahl[i];
		}
		cout << endl;

	} // Hauptschleife

	// Speichern
	int count = ziehungen.GetAnzahlAusspielungen();

	INTARRAY6 zahlen;
	if (count > 0)
	{
		out << "<center>\n<table width=42%>\n";
	
		for (int i = 0; i < count; i++)
		{
			ziehungen[i].GetZahlen(zahlen);
			
			if (i == 0)
			{
				out << "  <tr>\n";
  			   for (int j = 0; j < 6; j++)
  			   	out << "    <td width=7%>" << zahlen[j] << '\n';
				out << "  </tr>\n";
			}
			else
			{
				out << "  <tr>\n";
				for (int j = 0; j < 6; j++)
  			   	out << "    <td>" << zahlen[j] << '\n';
				out << "  </tr>\n";
			}
		}
		out << "</table>\n<p>\n";
		out << "<small>und falls jemand mit diesen Zahlen Lottomillion&auml;r wird, auf";
		out << "der Homepage befindet sich meine E-Mail Adresse .....</small>\n";
		out << "</center>\n</body>\n</html>\n";
	}
}

/* ASCII-Speichern
// Speichern
	int count = ziehungen.GetAnzahlAusspielungen();

	INTARRAY6 zahlen;
	if (count > 0)
	{
		for (int i = 0; i < count; i++)
		{
			ziehungen[i].GetZahlen(zahlen);
			for (int j = 0; j < 6; j++)
			{
				out.width(2);
				out << zahlen[j];
				if (j == 5)
					out << endl;
				else
					out << ",";
			}
		}
	}
*/


