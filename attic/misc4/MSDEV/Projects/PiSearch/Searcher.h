#ifndef __SEARCHER_H
#define __SEARCHER_H

#include <fstream.h>
#include <afxtempl.h>

class Searcher
{
	private:
		long compares;
		
		int skip[10];
		const int buflen;
		char* buffer;
		char* searchstr;
		int read;
		long pos;
		char* fileName;
		ifstream f;
		CList<long, long> poslist;
		
		int index(char c) { return c - 48; }
		void initskip(const char* s);
		int mischarsearch(const char* p, const char* a, int startpos);
		int readbuffer(int mitnehmen);

	public:
	void FillCompares(CStatic& st); 
		Searcher();
		~Searcher();

		BOOL onlyDigits(const char* str);

		BOOL SetFile(const char* file);
		//TRUE = File wurde gefunden 
		//FALSE= File nicht gefunden
		
		void SetSearchString(const char* str);
		
		BOOL DoSearchStep();
		void Stop(); 
		int GetNumberofResults() { return poslist.GetCount(); }

		void FillListBox(CListBox& lb);
};

#endif
