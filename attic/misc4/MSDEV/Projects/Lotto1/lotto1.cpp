#include "lottocla.h"
#include <iostream.h>
#include <fstream.h>
#include <iomanip.h>
#include "ziehung.h"

CLottozahlen lottozahlen;
CLottozahlen lottozahlenaus42;
CLottozahlen lottozahlenaus45;
CLottozahlen lottozahlenneu;

struct entry
{
	int zahl;
	int data;
};

typedef entry ENTRY45[45];

void Swap(int& a, int& b)
{
	int h;
	h = a; 
	a = b;
	b = h;
}

void Sort(ENTRY45& f, INTARRAY45 z)
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

void KopfHTML(ofstream& out, const char* title)
{
	out << "<html>\n";
	out << "<head><title>" << title << "</title></head>\n";
	out << "<body bgcolor=#C0C0C0>\n";
	out << "<p>\n";
	out << "<table width=100%>\n";
  	out << "  <tr>\n";
	out << "    <td align=left valign=top width=15%><img src=\"home.gif\"><a href=\"HomePage.htm\">Homepage</a>\n";
   out << "    <td align=center><h2>" << title << "</h2>\n";
   out << "    <td align=right valign=top width=15%><i>27.1.1996</i>\n";
   out << "  </tr>\n";
   out << "</table>\n";
   out << "<p>\n";
}

void OutAlleZiehungenHTML()
{
	int jahr;
	INTARRAY6 zahlen;
	
	ofstream out("OZiehung.htm");
	KopfHTML(out, "Alle Ziehungen des Schweiz. Zahlenlottos");

	out << "<table border>\n  <tr>\n";

	for (jahr = 1996; jahr >= 1987; jahr--)
		out << "    <td><a href=\"#" << jahr << "\">" << jahr << "</a>\n";
   out << "  </tr>\n  <tr>\n";
   for (jahr = 1986; jahr >= 1977; jahr--)
		out << "    <td><a href=\"#" << jahr << "\">" << jahr << "</a>\n";
   out << "  </tr>\n  <tr>\n";    
   for (jahr = 1976; jahr >= 1970; jahr--)
		out << "    <td><a href=\"#" << jahr << "\">" << jahr << "</a>\n";
   out << "    <td colspan=3>&#160;\n  </tr>\n</table>" << endl;

	out << "<p>\n<pre>\n";
	jahr = 0;
				
	for (int i = lottozahlen.GetAnzahlAusspielungen()-1; i >= 0; i--)
	{
		if (jahr != lottozahlen[i].GetJahr())
		{
			out << "<br>" << endl;
			if (lottozahlen[i].GetJahr() != 1996)
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
	out << "</pre>\n<a href=\"#Anfang\">An den Anfang</a>\n</body>\n</html>\n";
	out.close();	
}

void OutAusstehend()
{
  	
  	INTARRAY45 zahlen45;
	int i;

	if (lottozahlen.GetAnzahlAusspielungen() > 0)
	{
		ofstream out("Ausstehend.txt");
		lottozahlen.GetAusstehend(zahlen45);

		//Ausgabe
		for (i = 0; i < 45; i++)
			out << zahlen45[i] << '\n';
		out << endl;	

		out.close();
	}
}

void OutHaeufigkeitHTML()
{
	ENTRY45 feld[4];
	INTARRAY45 zahlen[4];
	int i, g;
	
	char* title[4] = {"Ab Ausspielung Nr. 14/1990 (Einf&uuml;hrung neues Ziehungsger&auml;t)",
							"Ab Ausspielung Nr. 1/1986 (Einf&uuml;hrung der Zahlen 43, 44 und 45)",
							"Ab Ausspielung Nr. 14/1979 (Einf&uuml;hrung der Zahlen 41 und 42)",
							"Ab der ersten Ausspielung (1/1970)"};

	char* id[4]    = {"\"NEU\"", "\"45\"", "\"42\"", "\"ALLE\""};

	ofstream out("OHaeufig.htm");
	KopfHTML(out, "H&auml;ufigkeit");
	out << "<center>\n<a href=\"#NEU\">Ab Ausspielung Nr. 14/1990</a><br>\n" \
       << "<a href=\"#45\">Ab Ausspielung Nr.  1/1986</a><br>\n" \
       << "<a href=\"#42\">Ab Ausspielung Nr. 14/1979</a><br>\n" \
       << "<a href=\"#ALLE\">Ab der ersten Ausspielung</a><br>\n</center>\n" \
       << "<p>&#160;\n<p>" << endl;

	lottozahlenneu.GetHaeufigkeit(zahlen[0]);
	lottozahlenaus45.GetHaeufigkeit(zahlen[1]);
	lottozahlenaus42.GetHaeufigkeit(zahlen[2]);
	lottozahlen.GetHaeufigkeit(zahlen[3]);

   Sort(feld[0], zahlen[0]);
	Sort(feld[1], zahlen[1]);
	Sort(feld[2], zahlen[2]);
	Sort(feld[3], zahlen[3]);

	for (g = 0; g < 4; g++)
	{
		out << "<center>\n<table border width=90%>\n" \
			 << "<a name=" << id[g] << "></a>" \
       	 << "<caption><b>" << title[g] << "</b></caption>\n" \
       	 << "  <tr>\n" \
		 	 << "    <th colspan=2 align=center>Sortiert nach Zahlen\n" \
       	 << "    <th>" \
       	 << "    <th colspan=2 align=center>Sortiert nach Ausspielungen\n" \
       	 << "  </tr>\n  <tr>\n  <th width=25%>Zahl\n    <th width=25%>Anzahl Ausspielungen\n" \
       	 << "    <th>\n    <th width=25%>Zahl\n" \
       	 << "    <th width=25%>Anzahl Ausspielungen\n  </tr>" << endl;


		for (i = 0; i < 45; i++)
		{
			out << "  <tr>\n";
			out << "    <td align=center>" << i+1 << '\n';
			out << "    <td align=center><b>" << zahlen[g][i] << "</b>\n";
			out << "    <td>\n";
			out << "    <td align=center><b>" << feld[g][i].zahl << "</b>\n";
			out << "    <td align=center>" << feld[g][i].data << '\n';
			out << "  </tr>\n";
		}
		out << "</table>\n</center>\n<p>" << endl;
	}
    
	out << "</body>\n</html>" << endl;
	out.close();
}


void OutHaeufigkeit()
{
	INTARRAY45 zahlen45;
	int i;
	ofstream out("Haeufigkeit.txt");

	lottozahlenneu.GetHaeufigkeit(zahlen45);
	if (lottozahlenneu.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 45; i++)
			out << zahlen45[i] << endl;
		out << endl;	
	}

	lottozahlenaus45.GetHaeufigkeit(zahlen45);
	if (lottozahlenaus45.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 45; i++)
			out << zahlen45[i] << endl;
		out << endl;	
	}

	lottozahlenaus42.GetHaeufigkeit(zahlen45);
	if (lottozahlenaus42.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 45; i++)
			out << zahlen45[i] << endl;
		out << endl;	
	}

	lottozahlen.GetHaeufigkeit(zahlen45);
	if (lottozahlen.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 45; i++)
			out << zahlen45[i] << endl;
		out << endl;	
	}


	out.close();

}

void OutVerbund()
{
	VERBUNDARRAY verbund;
	int i;
	ofstream out("Verbund.txt");

	lottozahlenneu.GetTotalVerbund(verbund);
	if (lottozahlenneu.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 11; i++)
			out << verbund[i] << endl;
		out << endl;
	}

	lottozahlenaus45.GetTotalVerbund(verbund);
	if (lottozahlenaus45.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 11; i++)
			out << verbund[i] << endl;
		out << endl;	
	}

	lottozahlenaus42.GetTotalVerbund(verbund);
	if (lottozahlenaus42.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 11; i++)
			out << verbund[i] << endl;
		out << endl;	
	}

	lottozahlen.GetTotalVerbund(verbund);
	if (lottozahlen.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 11; i++)
			out << verbund[i] << endl;
		out << endl;
	}


	out.close();

}

void OutVerbundHTML()
{
	VERBUNDARRAY verbundneu, verbund45, verbund42, verbund;
	int i;
	char* zeile[11] = {"0", "2", "3", "4", "5", "6", "2 + 2", "3 + 3", 
	                  "2 + 3", "2 + 2 + 2", "2 + 4"};
	
	ofstream out("OFolgen.htm");
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

	out << "</table>\n</center>\n" \
		 << "<p>&#160;\n<p>\n<center>\n<b>Erklärung</b>:<br>\n" \
       << "<b>NEU</b>  = Ab Ausspielung Nr. 14/1990  (Einf&uuml;hrung neues Ziehungsger&auml;t)<br>\n" \
       << "<b>45</b>   = Ab Ausspielung Nr.  1/1986  (Einf&uuml;hrung der Zahlen 43, 44 und 45)<br>\n" \
       << "<b>42</b>   = Ab Ausspielung Nr. 14/1979  (Einf&uuml;hrung der Zahlen 41 und 42)<br>\n" \
       << "<b>ALLE</b> = Ab der ersten Ausspielung (1/1970)\n" \
       << "<p>\n<b>Beispiele</b>:<br>\nAusspielung: 18/1994 4 23 37 38 40 44 <br>\n" \
		 << "Die Zahlen 37 und 38 sind Nachbarn auf der Zahlengerade und damit geh&ouml;rt<br>\n" \
		 << "die Ziehung zur Kategorie: <i>Aufeinanderfolgende Zahlen 2</i><br>\n<p>\n" \
		 << "Ausspielung: 19/1994 6 7 8 25 26 36<br>\n" \
		 << "6, 7 und 8 sowie 25 und 26 folgen unmittelbar aufeinander<br>\n" \
		 << "Kategorie: <i>Aufeinanderfolgende Zahlen 2 + 3</i><b>\n" \
		 << "</center>\n</body>\n</html>" << endl;

	out.close();

}

void OutGeradeUngeradeHTML()
{
	GERARRAY hgerneu, hger42, hger45, hgeralle;
	int i;

	lottozahlenneu.GetTotalGer(hgerneu);
	lottozahlenaus45.GetTotalGer(hger45);
	lottozahlenaus42.GetTotalGer(hger42);
	lottozahlen.GetTotalGer(hgeralle);

	ofstream out("OGU.htm");
	
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
		 << "  </tr>\n</table>\n" \
		 << "<p>&#160;\n<p>\nErklärung:<br>\n" \
       << "<b>NEU</b>  = Ab Ausspielung Nr. 14/1990  (Einf&uuml;hrung neues Ziehungsger&auml;t)<br>\n" \
       << "<b>45</b>   = Ab Ausspielung Nr.  1/1986  (Einf&uuml;hrung der Zahlen 43, 44 und 45)<br>\n" \
       << "<b>42</b>   = Ab Ausspielung Nr. 14/1979  (Einf&uuml;hrung der Zahlen 41 und 42)<br>\n" \
       << "<b>ALLE</b> = Ab der ersten Ausspielung (1/1970)\n" \
       << "</center>\n</body>\n</html>" << endl;

	out.close();
}

void OutGeradeUngerade()
{
	GERARRAY hger;
	int i;
	ofstream out("GeradeUngerade.txt");

	lottozahlenneu.GetTotalGer(hger);
	if (lottozahlenneu.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 7; i++)
			out << hger[i] << endl;
		out << lottozahlenneu.GetTotalGerade() << endl;
		out << lottozahlenneu.GetTotalUngerade() << endl << endl;
	}

	lottozahlenaus45.GetTotalGer(hger);
	if (lottozahlenaus45.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 7; i++)
			out << hger[i] << endl;
		out << lottozahlenaus45.GetTotalGerade() << endl;
		out << lottozahlenaus45.GetTotalUngerade() << endl << endl;
	}

	lottozahlenaus42.GetTotalGer(hger);
	if (lottozahlenaus42.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 7; i++)
			out << hger[i] << endl;
		out << lottozahlenaus42.GetTotalGerade() << endl;
		out << lottozahlenaus42.GetTotalUngerade() << endl << endl;
	}

	lottozahlen.GetTotalGer(hger);
	if (lottozahlen.GetAnzahlAusspielungen() > 0)
	{
		for (i = 0; i < 7; i++)
			out << hger[i] << endl;
		out << lottozahlen.GetTotalGerade() << endl;
		out << lottozahlen.GetTotalUngerade() << endl << endl;
	}


	out.close();

}

void OutAusstehendHTML()
{
	ENTRY45 feld;
	INTARRAY45 zahlen;
	int i;
	
	ofstream out("OAuss.htm");
	lottozahlen.GetAusstehend(zahlen);
	Sort(feld, zahlen);

	KopfHTML(out, "Seit wann ausstehend?");

	out << "<center>\n<table border width=92%>\n<CAPTION><i>Sortiert nach Ausspielungen</i></CAPTION>\n  <tr>\n";
		
	for (i = 0; i < 5; i++)
	{
		out << "    <th align=right width=4%>Zahl\n    <th align=center width=12%>Ausspiel.\n";
		if (i < 4) 
			out << "    <th width=3%>\n";
	}
   out << "  </tr>\n";

	for (i = 0; i < 9; i++)
	{
		out << "  <tr>\n    <td align=right>" << feld[i].zahl << endl;
		out << "    <td align=center><b>" << feld[i].data << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << feld[i+9].zahl << endl;
		out << "    <td align=center><b>" << feld[i+9].data << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << feld[i+18].zahl << endl;
		out << "    <td align=center><b>" << feld[i+18].data << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << feld[i+27].zahl << endl;
		out << "    <td align=center><b>" << feld[i+27].data << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << feld[i+36].zahl << endl;
		out << "    <td align=center><b>" << feld[i+36].data << "</b>\n";
		out << "  </tr>\n";
	}
	out << "</table>\n</center>\n";
	out << "<p>&#160;\n<p>\n";

	out << "<center>\n<table border width=92%>\n <caption align=left><i>Sortiert nach Zahlen</i></caption>\n  <tr>\n";
	for (i = 0; i < 5; i++)
	{
		out << "    <th align=right width=4%>Zahl\n    <th align=center width=12%>Ausspiel.\n";
		if (i < 4) 
			out << "    <th width=3%>\n";
	}
   out << "  </tr>\n";
  		
	for (i = 0; i < 9; i++)
	{
		out << "  <tr>\n    <td align=right>" << i+1 << endl;
		out << "    <td align=center><b>" << zahlen[i] << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << i+10 << endl;
		out << "    <td align=center><b>" << zahlen[i+9] << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << i+19 << endl;
		out << "    <td align=center><b>" << zahlen[i+18] << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << i+28 << endl;
		out << "    <td align=center><b>" << zahlen[i+27] << "</b>\n";
		out << "    <td>\n";
		out << "    <td align=right>" << i+37 << endl;
		out << "    <td align=center><b>" << zahlen[i+36] << "</b>\n";
		out << "  </tr>\n";
	}

	out << "</table>\n</center>\n<p>&#160;\n";
	out << "</body>\n</html>\n";

	out.close();
}

void Out1()
{
	int count = lottozahlen.GetAnzahlAusspielungen();

	INTARRAY6 zahlen;
	INTARRAY45 zahlen45a, zahlen45b;
	PAARARRAY paar;
	VERBUNDARRAY verbund;
	SUMARRAY zahlen235;
	GERARRAY hger;

	if (count > 0)
	{
		ofstream out("Ausgabe1.txt");
		for (int i = 0; i < count; i++)
		{
			out << lottozahlen[i].GetNr() << ",";
			out << lottozahlen[i].GetJahr() << ",";
			lottozahlen[i].GetZahlen(zahlen);
			for (int j = 0; j < 6; j++)
				out << zahlen[j] << ",";

			out << lottozahlen[i].GetZZ() << ",";
			out << lottozahlen[i].GetGerade() << ",";
			out << lottozahlen[i].GetUngerade() << ",";
			out << lottozahlen[i].GetSumme() << ",";
			out << int(lottozahlen[i].GetVerbund()) << endl;
		}
		out.close();
		out.open("Ausgabe2.txt");
		lottozahlen.GetAusstehend(zahlen45a);
		lottozahlen.GetHaeufigkeit(zahlen45b);
		lottozahlen.GetTotalSumme(zahlen235);
		lottozahlen.GetPaar(paar);
		lottozahlen.GetTotalVerbund(verbund);
		lottozahlen.GetTotalGer(hger);
		
		for (i = 0; i < 45; i++)
			out << zahlen45a[i] << endl;
		out << endl;
		out << "Haeufigkeit\n";
		for (i = 0; i < 45; i++)
			out << zahlen45b[i] << endl;
		out << endl;
		out << "Summe\n";
		for (i = 0; i < 235; i++)
			out << zahlen235[i] << endl;
		out << endl;
		out << "Verbund\n";
		for (i = 0; i < 11; i++)
			out << verbund[i] << endl;
		out << endl;
		out << "Paar\n";
		for (i = 0; i < 45; i++)
		{
			for (int j = 0; j < 45; j++)
				out << paar[i][j] << ",";
			out << endl;
		}
		out << endl;
		out << "HaeufGer\n";
		for (i = 0; i < 7; i++)
			out << hger[i] << endl;

		out << endl;
		out << lottozahlen.GetTotalGerade() << endl;
		out << lottozahlen.GetTotalUngerade() << endl;
		out.close();
	}
}

void OutAlleZiehungen()
{
	int count = lottozahlen.GetAnzahlAusspielungen();

	INTARRAY6 zahlen;
	
	if (count > 0)
	{
		ofstream out("Alle Ziehungen.txt");
		for (int i = 0; i < count; i++)
		{
			out << setw(2) << lottozahlen[i].GetNr();
			out << setw(5) << lottozahlen[i].GetJahr();
			lottozahlen[i].GetZahlen(zahlen);
			for (int j = 0; j < 6; j++)
				out << setw(3) << zahlen[j];
			out << setw(3) << lottozahlen[i].GetZZ() << endl;
		}
		out.close();
	
	}
}

void Conv()
{
	INTARRAY6 zahlen;
	int zz;
	int nr;
	int jahr;

	char zahls2[3]; zahls2[2] = '\0'; 
	char zahls4[5]; zahls4[4] = '\0';

	char buffer[50];
	
	ifstream in("Ausgabe1.txt");
	in.getline(buffer, 50);
		
	while (!in.eof())
	{
		// Nr
		zahls2[0] = buffer[0];
		zahls2[1] = buffer[1];
		nr = atoi(zahls2);

		//Jahr
		zahls4[0] = buffer[3];
		zahls4[1] = buffer[4];
		zahls4[2] = buffer[5];
		zahls4[3] = buffer[6];
		jahr = atoi(zahls4);

		for (int i = 0; i < 6; i++)
		{
			zahls2[0] = buffer[8+(i*3)];
			zahls2[1] = buffer[9+(i*3)];
			zahlen[i] = atoi(zahls2);
		}

		zahls2[0] = buffer[26];
		zahls2[1] = buffer[27];
		zz = atoi(zahls2);

		lottozahlen.AddZiehung(zahlen, zz, nr, jahr);

		in.getline(buffer, 50);
	}

	in.close();
}

void ZaehlenAusspielungen()
{
	int zaehler6aus42 = 0;
	int zaehler6aus45 = 0;
	int zaehlerneu    = 0;

	BOOL z42 = FALSE;
	BOOL z45 = FALSE;
	BOOL neu = FALSE;

	int count = lottozahlen.GetAnzahlAusspielungen();
	if (count > 0)
	{
		for (int i = 0; i < count; i++)
		{
			if ((lottozahlen[i].GetNr() == 14) && (lottozahlen[i].GetJahr() == 1979))
				z42 = TRUE;
			if ((lottozahlen[i].GetNr() == 1) && (lottozahlen[i].GetJahr() == 1986))
				z45 = TRUE;
			if ((lottozahlen[i].GetNr() == 14) && (lottozahlen[i].GetJahr() == 1990))
				neu = TRUE;

			if (z42) zaehler6aus42++;
			if (z45) zaehler6aus45++;
			if (neu) zaehlerneu++;
		}

		cout << "Total Ausspielungen:              " << count << endl;
		cout << "Total Ausspielungen seit 6 aus 42 " << zaehler6aus42 << endl;
		cout << "Total Ausspielungen seit 6 aus 45 " << zaehler6aus45 << endl;
		cout << "Total Ausspielungen seit 14/90    " << zaehlerneu << endl;
		cin.get();
	}
}


int main()
{
	
	//Umwandlung der Textdatei in die interne Darstellung
	/*Conv();
	lottozahlen.SaveData();
	lottozahlen.RemoveAll();	
	lottozahlen.LoadData(CLottozahlen::alle);*/

	lottozahlen.LoadData(CLottozahlen::alle);
	lottozahlenaus42.LoadData(CLottozahlen::aus42);
   lottozahlenaus45.LoadData(CLottozahlen::aus45);;
   lottozahlenneu.LoadData(CLottozahlen::neu);
	
	OutAusstehend();     // Die Ausstehenden als TXT-Datei ausgeben
	OutAusstehendHTML(); // Die Ausstehenden als HTML-Datei ausgeben
	
	OutAlleZiehungen();  // Alle Ziehungen als TXT-Datei ausgeben
	OutAlleZiehungenHTML(); // Alle Ziehungen als HTML-Datei

	OutHaeufigkeit();   // Häufigkeit aller 4 Arten ausgeben
	OutHaeufigkeitHTML();  

	OutVerbund();      // alle 4 Arten
	OutVerbundHTML();

	OutGeradeUngerade();  // alle 4 Arten
	OutGeradeUngeradeHTML();

	ofstream out("OTips.htm");
	KopfHTML(out, "Tips f&uuml;r die n&auml;chste Ausspielung");
	ZiehungderLottozahlen(lottozahlen, out);
	out.close();

	return 0;
}
