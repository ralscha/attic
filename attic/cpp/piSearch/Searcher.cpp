#include "stdafx.h"
#include <fstream.h>
#include <ctype.h>
#include <string.h>
#include "Searcher.h"

void Searcher::initskip(const char* s)
{
	int i;
	int sl = strlen(s);
	for (i = 0; i < 10; i++)
		skip[i] = sl;
	for (i = 0; i < sl; i++)
		skip[index(s[i])] = sl-i-1;
}


int Searcher::mischarsearch(const char* p, const char* a, int startpos)
{
	int i, j, t, M, N;
	M = strlen(p);
	N = strlen(a);
	
	for (i = startpos + (M-1), j = M-1; j >= 0; i--, j--)
	{
		compares++;
		while(a[i] != p[j])
		{
			t = skip[index(a[i])];
			i += (M-j > t) ? M-j : t;
			if (i >= N) 
				return N;
			j = M-1;			
		}
	}
	return i+1;
}

int Searcher::readbuffer(int mitnehmen)
{
	char ch;
	int p = 0;
	int r = 0;
	for (int i = 0; i < mitnehmen; i++)
		buffer[i] = buffer[buflen-mitnehmen+i];
	p = mitnehmen;
	
	while (!f.eof() && p < buflen)
	{
		ch = f.get();
		if (isdigit(ch))
		{
			buffer[p] = ch;
			p++;
			r++;
		}
	}
	if (f.eof())
		buffer[p] = '\0';

	return r;
}

BOOL Searcher::onlyDigits(const char* str)
{
	for (int i = 0; i < strlen(str); i++)
	{
		if (!iswdigit(str[i]))
			return FALSE;
	}
	return TRUE;
}

Searcher::Searcher() : f(), buflen(10000)
{
	fileName = NULL;
	searchstr = NULL;
	buffer = new char[buflen];
}

Searcher::~Searcher()
{
	delete [] buffer;
	delete [] fileName;
	delete [] searchstr;
}

BOOL Searcher::SetFile(const char* file)
{
	delete [] fileName;
	fileName = new char[strlen(file)+1];
	strcpy(fileName, file);

	if (f.is_open())
		f.close();

	f.open(fileName);
	if (f)
		return TRUE;
	else
		return FALSE;
}

void Searcher::SetSearchString(const char* str)
{
	if (!f.is_open())
		f.open(fileName);
	
	if (searchstr == NULL)
	{
		searchstr = new char[strlen(str)+1];
		strcpy(searchstr, str);
	}
	else if (strcmp(searchstr, str) != 0)
	{
		delete [] searchstr;
		searchstr = new char[strlen(str)+1];
		strcpy(searchstr, str);
	}
   poslist.RemoveAll();
	initskip(searchstr);
	read = readbuffer(0);
	pos = 0;
	compares = 0;
}

void Searcher::Stop()
{
	f.close();
}

//TRUE  = Fertig
//FALSE = Noch nicht fertig
BOOL Searcher::DoSearchStep()
{
	int lenss = strlen(searchstr);
	int res;
	
	if (read != 0)
	{
		res = mischarsearch(searchstr, buffer, 0);
		while (res < strlen(buffer))
		{
			poslist.AddTail(pos+res);		
			res = mischarsearch(searchstr, buffer, res+1);	
		}
		pos += strlen(buffer) - lenss + 1;
		read = readbuffer(lenss-1);
		return FALSE;
	}	
	else
	{
		f.close();
		return TRUE;
	}
}

void Searcher::FillListBox(CListBox& lb)
{
	lb.ResetContent();
	char buf[10];
	POSITION pos = poslist.GetHeadPosition();
	while (pos != NULL)
	{
		long l = poslist.GetNext(pos);
		wsprintf(buf, "%10lu", l);
		lb.AddString(buf);
	}
	int i = 1 + 1;
}

void Searcher::FillCompares(CStatic& st)
{
	char buf[8];	
	wsprintf(buf, "%7lu", compares);
	st.SetWindowText(buf);	
}
