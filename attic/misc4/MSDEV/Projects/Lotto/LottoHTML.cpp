#include "stdafx.h"
#include "lottocla.h"
#include <fstream.h>
#include <iomanip.h>

#include "LottoHTML.h"


void CLottoHTML::Sort(ENTRY45& f, INTARRAY45 z)
{
 	int i, j;

 	for (i = 0; i < 45; i++)
	{
		f[i].zahl = i+1;
		f[i].data = z[i];
	} 

	for (i = 44; i >= 0; i--)
		for (j = 1; j <= i; j++)
			if (f[j-1].data < f[j].data)
			{
				Swap(f[j-1].zahl, f[j].zahl);
				Swap(f[j-1].data, f[j].data); 	
			}
}

void CLottoHTML::KopfHTML(ofstream& out, const char* title)
{
	out << "<html>\n";
	out << "<head><title>" << title << "</title></head>\n";
	out << "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" LINK=\"#0000FF\" ALINK=\"#FF0000\" VLINK=\"#400040\">\n";
	out << "<p>\n";
	out << "<table width=100%>\n";
  	out << "  <tr>\n";
	out << "    <td align=left valign=top width=15%><a href=\"Lotto.htm\">Lottopage</a>\n";
   out << "    <td align=center><h2>" << title << "</h2>\n";
   out << "    <td align=right valign=top width=15%><i>" << date.GetDay() << '.';
   out << date.GetMonth() << '.' << date.GetYear() << "</i>\n";
   out << "  </tr>\n";
   out << "</table>\n";
   out << "<p>\n";
}

void CLottoHTML::OutAlleZiehungenHTML()
{
	int jahr;
	INTARRAY6 zahlen;
	
	CString fileName("/OZiehung.htm");
	
	ofstream out(path + fileName);
	KopfHTML(out, "Ziehungen 1970 - 1995");

	out << "<table border>\n  <tr>\n";

	for (jahr = 1970; jahr <= 1982; jahr++)
		out << "    <td><a href=\"#" << jahr << "\">" << jahr << "</a>\n";
   out << "  </tr>\n  <tr>\n";
   for (jahr = 1983; jahr <= 1995; jahr++)
		out << "    <td><a href=\"#" << jahr << "\">" << jahr << "</a>\n";
   out << "  </tr>\n</table>" << endl;    
   
	out << "<p>\n<pre>\n";
	jahr = 0;
	
	for (int i = 0; i < lottozahlen.GetAnzahlAusspielungen(); i++)
	{
		if (lottozahlen[i].GetJahr() != 1996)
		{
			if (jahr != lottozahlen[i].GetJahr())
			{
				out << "<br>" << endl;
				if (lottozahlen[i].GetJahr() != 1970)
					out << "<a href=\"#Anfang\">An den Anfang</a>" << endl;
				out << "<br>" << endl;
				out << "<a name=\"" << lottozahlen[i].GetJahr() << "\"" << "></a>\n";
				out << "<b>Nr.  Jahr  Zahl1  Zahl2  Zahl3  Zahl4  Zahl5  Zahl6  Zusatzzahl</b>\n";
				jahr = lottozahlen[i].GetJahr();
			}
			out << setw(2) << lottozahlen[i].GetNr() << "   ";
			out << setw(4) << jahr << "  ";
			lottozahlen[i].GetZahlen(zahlen);
			for (int j = 0; j < 6; j++)
				out << setw(5) << zahlen[j] << "  ";
			out << setw(5) << lottozahlen[i].GetZZ() << endl;
		}
	}
	out << "</pre>\n<a href=\"#Anfang\">An den Anfang</a>\n</body>\n</html>\n";
	out.close();	
}

void CLottoHTML::OutHaeufigkeitHTML()
{

	ENTRY45 feld[4];
	INTARRAY45 zahlen[4];
	int i;
	
	CString fileName("/OHaeuf.htm");
	ofstream out(path + fileName);
	KopfHTML(out, "H&auml;ufigkeit");
	out << "<center>\n<h4>Sortiert nach Zahlen</h4>\n<p>\n";
	out << "<table border width=45%>\n  <tr>\n    <th align=center>Zahl\n";
	out << "    <th align=center colspan=4>Anzahl Ausspielungen\n  </tr>\n  <tr>\n";
	out << "    <th width=24%>&#160;\n";
	out << "    <th width=19% align=center>NEU\n";
	out << "    <th width=19% align=center>45\n";
   out << "    <th width=19% align=center>42\n";
   out << "    <th width=19% align=center>ALLE\n";
	out << "  </tr>" << endl;

	lottozahlenneu.GetHaeufigkeit(zahlen[0]);
	lottozahlenaus45.GetHaeufigkeit(zahlen[1]);
	lottozahlenaus42.GetHaeufigkeit(zahlen[2]);
	lottozahlen.GetHaeufigkeit(zahlen[3]);

   Sort(feld[0], zahlen[0]);
	Sort(feld[1], zahlen[1]);
	Sort(feld[2], zahlen[2]);
	Sort(feld[3], zahlen[3]);

	for (i = 0; i < 45; i++)
	{
		out << "  <tr>\n";
		out << "    <td align=center><b>" << i+1 << "</b>\n";
		out << "    <td align=center>" << zahlen[0][i] << '\n';
		out << "    <td align=center>" << zahlen[1][i] << '\n';
		out << "    <td align=center>" << zahlen[2][i] << '\n';
		out << "    <td align=center>" << zahlen[3][i] << '\n';
		out << "  </tr>\n";
	}
	out << "</table>" << endl;
	Erklaerung(out);
	out << "</center>\n</body>\n</html>" << endl;
	out.close();

	fileName = "/OHaeuf2.htm";
	out.open(path + fileName);;
	KopfHTML(out, "H&auml;ufigkeit");
	out << "<center>\n<h4>Sortiert nach H&auml;ufigkeit</h4>\n<p>\n";

	out << "<table border width=90%>\n";
	out << "  <tr>\n";
	out << "    <th align=center colspan=2>NEU\n";
	out << "    <th align=center colspan=2>45\n";
	out << "	   <th align=center colspan=2>42\n";
	out << "    <th align=center colspan=2>ALLE\n";
	out << "  </tr>\n";
	out << "  <tr>\n";
	out << "    <th align=center>Zahl\n";
	out << "    <th align=center>Aussp.\n";
	out << "    <th align=center>Zahl\n";
	out << "    <th align=center>Aussp.\n";
	out << "    <th align=center>Zahl\n";
	out << "    <th align=center>Aussp.\n";
	out << "    <th align=center>Zahl\n";
	out << "    <th align=center>Aussp.\n";
	out << "  </tr>\n";

	for (i = 0; i < 45; i++)
	{
		out << "  <tr>\n";
		out << "    <td align=center><b>" << feld[0][i].zahl << "</b>\n";
		out << "    <td align=center>" << feld[0][i].data << '\n';
		out << "    <td align=center><b>" << feld[1][i].zahl << "</b>\n";
		out << "    <td align=center>" << feld[1][i].data << '\n';
		out << "    <td align=center><b>" << feld[2][i].zahl << "</b>\n";
		out << "    <td align=center>" << feld[2][i].data << '\n';
		out << "    <td align=center><b>" << feld[3][i].zahl << "</b>\n";
		out << "    <td align=center>" << feld[3][i].data << '\n';
		out << "  </tr>\n";
	}

   out << "</table>\n";
	Erklaerung(out);
	out << "</center>\n</body>\n</html>" << endl;
	out.close();
}

void CLottoHTML::Erklaerung(ofstream& out)
{
	out << "<p>&#160;\n<p>\n<table>\n<caption><b>Erklärung</b></caption>\n";
	out << "  <tr>\n    <td align=right><b>NEU</b>\n";
	out << "    <td>Ab Ausspielung Nr. 14/1990  (Einf&uuml;hrung neues Ziehungsger&auml;t)\n";
	out << "  </tr>\n";
	out << "  <tr>\n";
	out << "    <td  align=right><b>45</b>\n";
	out << "    <td>Ab Ausspielung Nr.  1/1986  (Einf&uuml;hrung der Zahlen 43, 44 und 45)\n";
	out << "  </tr>\n";
	out << "  <tr>\n";
	out << "    <td  align=right><b>42</b>\n";
	out << "    <td>Ab Ausspielung Nr. 14/1979  (Einf&uuml;hrung der Zahlen 41 und 42)\n";
	out << "  </tr>\n";
	out << "  <tr>\n";
	out << "    <td  align=right><b>ALLE</b>\n";
	out << "    <td>Ab der ersten Ausspielung (1/1970)\n";
	out << "  </tr>\n</table>\n" << endl;
}


void CLottoHTML::OutVerbundHTML()
{
	VERBUNDARRAY verbundneu, verbund45, verbund42, verbund;
	int i;
	char* zeile[11] = {"0", "2", "3", "4", "5", "6", "2 + 2", "3 + 3", 
	                  "2 + 3", "2 + 2 + 2", "2 + 4"};
	
	CString fileName("/OFolgen.htm");
	ofstream out(path + fileName);
	lottozahlenneu.GetTotalVerbund(verbundneu);
	lottozahlenaus45.GetTotalVerbund(verbund45);
	lottozahlenaus42.GetTotalVerbund(verbund42);
	lottozahlen.GetTotalVerbund(verbund);

	KopfHTML(out, "Aufeinanderfolgende Zahlen");

	out << "<center>\n" \
		 << "<table border width=60%>\n" \
		 << "  <tr>\n" \
		 << "    <th align=center>Aufeinanderfolgende Zahlen\n" \
		 << "    <th align=center colspan=4>Anzahl Ausspielungen\n" \
		 << "  </tr>\n" \
		 << "  <tr>\n" \
		 << "    <th align=center>pro Ausspielung\n" \
		 << "    <th width=15% align=center>NEU\n" \
		 << "    <th width=15% align=center>45\n" \
		 << "    <th width=15% align=center>42\n" \
		 << "    <th width=15% align=center>ALLE\n" \
 		 << "  </tr>" << endl;
	
	for (i = 0; i < 11; i++)
	{
		out << "  <tr>\n    <td align=center><b>" << zeile[i] << "</b>\n" \
		    << "    <td align=center>" << verbundneu[i] << '\n' \
			 << "    <td align=center>" << verbund45[i] << '\n' \
		 	 << "    <td align=center>" << verbund42[i] << '\n' \
			 << "    <td align=center>" << verbund[i] << "\n  </tr>\n";
	}

	out << "</table>\n";
	Erklaerung(out);

	out << "<p>&#160;\n<p>\n<table width=60%>\n<caption><b>Beispiele</b></caption>\n";
   out << "<tr><td>Ausspielung: <code>18/1994 4 23 37 38 40 44</code><br>\n";
	out << "Die Zahlen 37 und 38 sind Nachbarn auf der Zahlengerade und damit geh&ouml;rt\n";
	out << "die Ziehung zur Kategorie: <i>Aufeinanderfolgende Zahlen 2</i></tr>\n<tr><td>&#160;</tr>";
	out << "<tr><td>Ausspielung: <code>19/1994 6 7 8 25 26 36</code><br>\n";
	out << "6, 7 und 8 sowie 25 und 26 folgen unmittelbar aufeinander<br>\n";
	out << "Kategorie: <i>Aufeinanderfolgende Zahlen 2 + 3</i></tr>\n";
	out << "</table>\n</center>\n</body>\n</html>" << endl;

	out.close();

}

void CLottoHTML::OutGeradeUngeradeHTML()
{
	GERARRAY hgerneu, hger42, hger45, hgeralle;
	int i;

	lottozahlenneu.GetTotalGer(hgerneu);
	lottozahlenaus45.GetTotalGer(hger45);
	lottozahlenaus42.GetTotalGer(hger42);
	lottozahlen.GetTotalGer(hgeralle);
	
	CString fileName("/OGU.htm");
	ofstream out(path + fileName);
	
	KopfHTML(out, "Gerade / Ungerade");

	out << "<center>\n<table border width=60%>\n" \
       << "<tr>\n    <th align=center>GERADE Zahlen\n" \
       << "    <th align=center colspan=4>Anzahl Ausspielungen\n" \
       << "</tr>\n  <tr>\n" \
		 << "    <th align=center>pro Ausspielung\n" \
		 << "    <th width=15% align=center>NEU\n"	\
		 << "    <th width=15% align=center>45\n" \
		 << "    <th width=15% align=center>42\n" \
		 << "    <th width=15% align=center>ALLE\n  </tr>\n";
	
	for (i = 0; i < 7; i++)
	{
		out << "  <tr>\n    <td align=center><b>" << i << "</b>\n" \
		    << "    <td align=center>" << hgerneu[i] << '\n' \
			 << "    <td align=center>" << hger45[i] << '\n' \
		 	 << "    <td align=center>" << hger42[i] << '\n' \
			 << "    <td align=center>" << hgeralle[i] << "\n  </tr>\n";
	}

	out << "</table>\n</center>\n";
	out << "<p>&#160;\n<p>" << endl;
  
   out << "<center>\n<table border width=60%>\n<CAPTION><b>TOTAL</b></CAPTION>\n" \
       << "  <tr>\n    <th width=20%>&#160;\n    <th align=center width=40%>Gerade\n    <th align=center width=40%>Ungerade\n" \
  		 << "  </tr>\n  <tr>\n    <td align=center><b>NEU</b>\n" \
 		 << "    <td align=center>" << lottozahlenneu.GetTotalGerade() << '\n' \
		 << "    <td align=center>" << lottozahlenneu.GetTotalUngerade() << '\n' \
		 << "  </tr>\n  <tr>\n    <td align=center><b>45</b>\n" \
		 << "    <td align=center>" << lottozahlenaus45.GetTotalGerade() << '\n' \
		 << "    <td align=center>" << lottozahlenaus45.GetTotalUngerade() << '\n' \
 		 << "  </tr>\n  <tr>\n    <td align=center><b>42</b>\n" \
		 << "    <td align=center>" << lottozahlenaus42.GetTotalGerade() << '\n' \
		 << "    <td align=center>" << lottozahlenaus42.GetTotalUngerade() << '\n' \
		 << "  </tr>\n  <tr>\n    <td align=center><b>ALLE</b>\n"	\
		 << "    <td align=center>" << lottozahlen.GetTotalGerade() << '\n' \
		 << "    <td align=center>" << lottozahlen.GetTotalUngerade() << '\n' \
		 << "  </tr>\n</table>\n";

	Erklaerung(out);
	out << "</center>\n</body>\n</html>" << endl;
	out.close();
}


void CLottoHTML::OutAusstehendHTML()
{
	ENTRY45 feld;
	INTARRAY45 zahlen;
	int i = 0;
	int j = 0;

	ofstream out(path + "//OAuss.htm");
	
	lottozahlen.GetAusstehend(zahlen);
	Sort(feld, zahlen);

	KopfHTML(out, "Seit wann ausstehend?");

	out << "<center>\n<table border width=50%>\n  <tr>\n";
   out << "    <th align=center width=10%>Anzahl Ausspielungen\n";
	out << "    <th align=center colspan=6>Ausstehende Zahlen\n";
	out << "  </tr>" << endl;


	while (i < 39)
	{
		out << "  <tr>\n";
      out << "    <td align=center><b>" << feld[i].data << "</b>\n";
		out << "    <td align=center>" << feld[i].zahl << '\n';

		j = 1;
		while (feld[i+1].data == feld[i].data)
		{
			i++; j++;           
			out << "    <td align=center>" << feld[i].zahl << '\n';
		}
		i++;
		for (j; j < 6; j++)
			out << "    <td align=center>&#160;\n";

		out << "  </tr>\n";
	}

   // Die letzten 6 
	out << "  <tr>\n    <td align=center><b>" << feld[i].data << "</b>\n";
	for (; i < 45; i++)
		out << "    <td align=center>" << feld[i].zahl << '\n';	
			 
	out << "  </tr>\n</table>\n</center>\n";
	out << "</body>\n</html>\n";

	out.close();
}

void CLottoHTML::OutTips()
{
	CString fileName("/OTips.htm");
	ofstream out(path + fileName);;
	KopfHTML(out, "Tips f&uuml;r die n&auml;chste Ausspielung");

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
	
	for (int n = 0; n < 10; n++)
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
		out << "der Homepage befindet sich meine E-Mail Adresse .....:-)</small>\n";
		out << "</center>\n</body>\n</html>\n";
	}

	out.close();
}

void CLottoHTML::CreatePages(const char* p, CTime& d)
{
	date = d;
	path = p;

	lottozahlen.LoadData(CLottozahlen::alle);
	lottozahlenaus42.LoadData(CLottozahlen::aus42);
   lottozahlenaus45.LoadData(CLottozahlen::aus45);;
   lottozahlenneu.LoadData(CLottozahlen::neu);
	
	OutAusstehendHTML(); 
	//OutAlleZiehungenHTML(); 
	OutHaeufigkeitHTML();  
	OutVerbundHTML();
	OutGeradeUngeradeHTML();
	OutTips();
	OutZiehungen1996();
}


void CLottoHTML::OutZiehungen1996()
{
	INTARRAY6 zahlen;
	
	ofstream out(path + "//OZ1996.htm");
	KopfHTML(out, "Ziehungen 1996");
		
	out << "<center>\n<table border width=50%>\n";
	out << "  <tr>\n";
	out << "    <th align=right>Nr.\n";
	out << "    <th align=center colspan=6>Gewinnzahlen\n";
	out << "    <th align=right>Zz\n";
	out << "  </tr>\n";
	
	BOOL first = TRUE;
	for (int i = 0; i < lottozahlenneu.GetAnzahlAusspielungen(); i++)
	{
		if (lottozahlenneu[i].GetJahr() == 1996)
		{
			out << "  <tr>\n";
			if (first)
			{
				out << "    <td align=right width=14%><b>" << lottozahlenneu[i].GetNr() << "</b>\n";
				lottozahlenneu[i].GetZahlen(zahlen);
				for (int j = 0; j < 6; j++)
					out << "    <td align=right width=12%>" << zahlen[j] << '\n';
				out << "    <td align=right width=14%>" << lottozahlenneu[i].GetZZ() << '\n';
				first = FALSE;
			}
			else
			{
				out << "    <td align=right><b>" << lottozahlenneu[i].GetNr() << "</b>\n";
				lottozahlenneu[i].GetZahlen(zahlen);
				for (int j = 0; j < 6; j++)
					out << "    <td align=right>" << zahlen[j] << '\n';
				out << "    <td align=right>" << lottozahlenneu[i].GetZZ() << '\n';
		
			}			
			out << "  </tr>\n";
		}
	}

	out << "</table>\n</center>\n</body>\n</html>" << endl;
	out.close();

}
