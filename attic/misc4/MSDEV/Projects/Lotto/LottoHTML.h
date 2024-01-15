#ifndef __LOTTOHTML_H
#define __LOTTOHTML_H

#include <fstream.h>

struct entry
{
	int zahl;
	int data;
};

typedef entry ENTRY45[45];

class CLottoHTML
{
	public:
	void OutZiehungen1996();
		CLottoHTML() { }
		void CreatePages(const char* p, CTime& d);

	private:
		CLottozahlen lottozahlen;
		CLottozahlen lottozahlenaus42;
		CLottozahlen lottozahlenaus45;
		CLottozahlen lottozahlenneu;
		CTime date;
		CString path;

		void Swap(int& a, int& b) { int h;	h = a; a = b; b = h;}

		void Sort(ENTRY45& f, INTARRAY45 z);
 	   void KopfHTML(ofstream& out, const char*  title);
		void Erklaerung(ofstream& out);
		void OutAlleZiehungenHTML();
		void OutHaeufigkeitHTML();
		void OutVerbundHTML();
		void OutGeradeUngeradeHTML();
		void OutAusstehendHTML();
		void OutTips();
};

#endif