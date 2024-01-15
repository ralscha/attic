#include "lottocla.h"
#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>
#include <time.h>

CLottozahlen lottozahlen;

void LoadLotto()
{
	CFile theFile("lotto.dat", CFile::modeRead);
	CArchive archive(&theFile, CArchive::load);
	lottozahlen.Serialize(archive);
	archive.Close();
	theFile.Close();
}


void TestZ()
{
	int zufall;
	int i;

	SUMARRAY summen, osum;

	int summentotal = lottozahlen.GetAnzahlAusspielungen();

	for (i = 0; i < 235; i++)
		osum[i] = 0;


	lottozahlen.GetTotalSumme(summen);

	for (int n = 0; n < 10000; n++)
	{
		// Auswahl der Summe
		zufall = rand() % summentotal + 1;
		int x = 0;
		int summe = summen[x];
		while (summe < zufall)
			summe += summen[++x];
		osum[x]++;
	}

	// Ausgabe
	ofstream out("Ausgabe3.txt");
	out << endl << "Summe\n";
	for (i = 0; i < 235; i++)
		out << osum[i] << endl;
	out.close();
}

 
int main()
{

	LoadLotto();
	srand((unsigned)time(NULL));
	TestZ();
	return 0;
}
