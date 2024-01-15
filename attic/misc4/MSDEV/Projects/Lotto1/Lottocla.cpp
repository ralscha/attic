

#include "lottocla.h"


IMPLEMENT_SERIAL(CZiehung, CObject, 1)
IMPLEMENT_SERIAL(CLottozahlen, CObject, 1)


void CZiehung::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		ar << WORD(nr) << WORD(jahr);
		for (int i = 0; i < 6; i++)
			ar << WORD(zahlen[i]);
		ar << WORD(zz) << WORD(vb) << WORD(gerade) << WORD(summe);
	}
	else
	{
		WORD w;
		
		ar >> w; nr = w;
		ar >> w; jahr = w;

		for (int i = 0; i < 6; i++)
		{
			ar >> w;
			zahlen[i] = w;
		}
		ar >> w; zz = w;
		ar >> w; vb = Verbund(w);
		ar >> w; gerade = w;
		ar >> w; summe = w;
	}
}

void CLottozahlen::Serialize(CArchive& ar)
{
	ziehungen.Serialize(ar);
}

CZiehung::Verbund CZiehung::SucheVerbund(INTARRAY6& zahl)
{
	int summe = 0;

	//2 minus 1
	if ((zahl[1] - zahl[0]) == 1)
		summe += 10000;
	//3 minus 2
	if ((zahl[2] - zahl[1]) == 1)
		summe += 1000;
	//4 minus 3
	if ((zahl[3] - zahl[2]) == 1)
		summe += 100;
	//5 minus 4
	if ((zahl[4] - zahl[3]) == 1)
		summe += 10;
	//6 minus 5
	if ((zahl[5] - zahl[4]) == 1)
		summe += 1;

	switch (summe)
	{
		case     1:
		case    10:
		case   100:
		case  1000:
		case 10000: return Zwilling;

		case    11:
		case   110:
		case  1100:
		case 11000: return Drilling;

		case   111:
		case  1110:
		case 11100: return Vierling;

		case  1111:
		case 11110: return Fuenfling;

		case 11111: return Sechsling;

		case   101:
		case  1001:
		case  1010:
		case 10001:
		case 10010:
		case 10100: return Doppelzwilling;

		case  1011:
		case  1101:
		case 10011:
		case 10110:
		case 11001:
		case 11010: return DrillingZwilling;

		case 10101: return Dreifachzwilling;

		case 10111:
		case 11101: return VierlingZwilling;

		case 11011: return Doppeldrilling;

		default : return Nichts;
	}
}

//Konstruktoren
CZiehung::CZiehung(INTARRAY6& zahl, int z, int n, int j)
{
	gerade = summe = 0;

	for (int i = 0; i < 6; i++)
	{
		zahlen[i] = zahl[i];
		if (!Odd(zahlen[i]))
			gerade++;
		summe += zahlen[i];
	}
	zz = z; jahr = j;
	nr = n;
	vb = SucheVerbund(zahl);
}

CZiehung::CZiehung()
{
	gerade = summe = zz = jahr = nr = 0;
	vb = Nichts;

	for (int i = 0; i < 6; i++)
		zahlen[i] = 0;
}

CZiehung::CZiehung(const CZiehung& zie)
{
	for (int i = 0; i < 6; i++)
		zahlen[i] = zie.zahlen[i];
	zz = zie.zz;
	nr = zie.nr;
	jahr = zie.jahr;
	vb = zie.vb;
	gerade = zie.gerade;
	summe = zie.summe;
}

const CZiehung& CZiehung::operator=(const CZiehung& zie)
{

	for (int i = 0; i < 6; i++)
		zahlen[i] = zie.zahlen[i];
	zz = zie.zz;
	nr = zie.nr;
	jahr = zie.jahr;
	vb = zie.vb;
	gerade = zie.gerade;
	summe = zie.summe;

	return *this;	
} 

void CZiehung::GetZahlen(INTARRAY6& zahl) const
{
	for (int i = 0; i < 6; i++)
		zahl[i] = zahlen[i];
}


char CLottozahlen::datfilename[] = "d:\\msdev\\projects\\lottocommon\\lotto.dat";

CLottozahlen::CLottozahlen()
{
	int i, j;

	totalungerade = totalgerade = 0;

	for (i = 0; i < 45; i++)
	{
		ausstehend[i] = 0;
		haeufigkeit[i] = 0;
	}

	for (i = 0; i < 235; i++)
		totalsumme[i] = 0;

	for (i = 0; i < 11; i++)
		totalverbund[i] = 0;

	for (i = 0; i < 45; i++)
		for (j = 0; j < 45; j++)
			paar[i][j] = 0;

	for (i = 0; i < 7; i++)
		haeufger[i] = 0;
}

/* Wird einmal nach dem Laden aufgerufen */
void CLottozahlen::CalculateDatas()
{
	CZiehung help;
	INTARRAY6 zahlen;
	int i, j, n;

	for (n = 0; n < ziehungen.GetSize(); n++)
	{
		help = ziehungen[n];
		help.GetZahlen(zahlen);

		totalgerade += help.GetGerade();
		totalungerade += help.GetUngerade();

		totalverbund[help.GetVerbund()]++;
		totalsumme[help.GetSumme()-21]++;

		haeufger[help.GetGerade()]++;

		for (i = 0; i < 45; i++)
			ausstehend[i]++;

		for (i = 0; i < 6; i++)
		{
			haeufigkeit[zahlen[i]-1]++;
			ausstehend[zahlen[i]-1] = 0;
		}

		for (i = 0; i < 6; i++)
			for (j = 0; j < 6; j++)
				if (i != j)
					paar[zahlen[i]-1][zahlen[j]-1]++;
	
	}
}

void CLottozahlen::AddZiehung(INTARRAY6& zahl, int z, int n, int ja)
{
	int i, j;

	CZiehung help(zahl, z, n, ja);
	ziehungen.Add(help);

	totalgerade += help.GetGerade();
	totalungerade += help.GetUngerade();

	totalverbund[help.GetVerbund()]++;
	totalsumme[help.GetSumme()-21]++;

	haeufger[help.GetGerade()]++;

	for (i = 0; i < 45; i++)
		ausstehend[i]++;

	for (i = 0; i < 6; i++)
	{
		haeufigkeit[zahl[i]-1]++;
		ausstehend[zahl[i]-1] = 0;
	}

	for (i = 0; i < 6; i++)
		for (j = 0; j < 6; j++)
			if (i != j)
				paar[zahl[i]-1][zahl[j]-1]++;

}

void CLottozahlen::GetAusstehend(INTARRAY45& zahl) const
{
	for (int i = 0; i < 45; i++)
		zahl[i] = ausstehend[i];
}

void CLottozahlen::GetHaeufigkeit(INTARRAY45& zahl) const
{
	for (int i = 0; i < 45; i++)
		zahl[i] = haeufigkeit[i];
}

void CLottozahlen::GetTotalSumme(SUMARRAY& sum) const
{
	for (int i = 0; i < 235; i++)
		sum[i] = totalsumme[i];
}

void CLottozahlen::GetPaar(PAARARRAY& pa) const
{
	for (int i = 0; i < 45; i++)
		for (int j = 0; j < 45; j++)
			pa[i][j] = paar[i][j];
}

void CLottozahlen::GetTotalVerbund(VERBUNDARRAY& verb) const
{
	for (int i = 0; i < 11; i++)
		verb[i] = totalverbund[i];
}

void CLottozahlen::GetTotalGer(GERARRAY& ger) const
{
	for (int i = 0; i < 7; i++)
		ger[i] = haeufger[i];
}

CZiehung& CLottozahlen::operator [] (int loc)
{
	return ziehungen[loc];
}

void CLottozahlen::SaveData()
{
	CFile theFile;
	theFile.Open(datfilename, CFile::modeCreate | CFile::modeWrite);
	CArchive archive(&theFile, CArchive::store);
	Serialize(archive);	
	archive.Close();
	theFile.Close();

}

void CLottozahlen::LoadData(int ls)
{
	
	CFile theFile(datfilename, CFile::modeRead);
	CArchive archive(&theFile, CArchive::load);
	
	Serialize(archive);
	archive.Close();
	theFile.Close();
	switch(ls)
	{
		case alle : break;
		case aus42: ziehungen.RemoveAt(0, 475); break;
		case aus45: ziehungen.RemoveAt(0, 827); break;
		case neu  : ziehungen.RemoveAt(0, 1049); break; 
	}
													 
	CalculateDatas();
}
