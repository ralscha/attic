#ifndef __CMFCTEXT_H
#define __CMFCTEXT_H

#include <fstream.h>

class CMFCFile : public CObject
{
	private:
		ifstream in;
		
	public:
		CMFCFile(const CString& path) { in.open(path); }
		~CMFCFile() { in.close();}
		BOOL ReadAll(CList<CEntry, CEntry&>& list);
	
	private:
		void ParseDate(const CString& str, COleDateTime& d);
};

#endif

