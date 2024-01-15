#include "StdAfx.h"
#include "MFCRecordSet.h"
#include "Entry.h"
#include "MFCFile.h"


void CMFCFile::ParseDate(const CString& str, COleDateTime& d)
{
	int year = 0;
	int month = 0;
	int day = 0;
	int pos = 0;
	CString help;

	CString monthn[12] = { "Jan", "Feb", "Mar", "Apr", "May",
                         "Jun", "Jul", "Aug", "Sep", "Oct",
								 "Nov", "Dec" };

	//Tag oder Wochenname
	pos = 0;
	while(!isdigit(str[pos]))
		pos++;
	
	if (str[pos+1] == ' ')
	{
		day = atoi(str.Mid(pos, 1));
		pos += 2;
	}
	else if (str[pos+2] == ' ')
	{
		day = atoi(str.Mid(pos, 2));
		pos += 3;
	}

	for (int i = 0; i < 12; i++)
	{
		if (str.Mid(pos, 3) == monthn[i])
		{
			month = i + 1;
			pos += 3;
			i = 12;
		}
	}
	
	while (str[pos] != ' ')
		pos++;
	
	pos++;
   if (!isdigit(str[pos+4]))
		year = atoi(str.Mid(pos, 4));
	else if (!isdigit(str[pos+2]))
		year = atoi(str.Mid(pos, 2));

	if (year != 0 && month != 0 && day != 0)
		d.SetDate(year, month, day);
	else
		d.SetStatus(COleDateTime::invalid);

}



BOOL CMFCFile::ReadAll(CList<CEntry, CEntry&>& list)
{
	CEntry* entry;

	BOOL gotfrom, gotsubject, gotdate, end;
	CString instr, help, help2, body;
	COleDateTime date;
	int poss, posf, posd, line, posre1, posre2;
	char buf[100];
	
	in.getline(buf, 100);
	instr = buf;
	while (!in.eof())
	{
		gotfrom = gotsubject = gotdate = end = FALSE;
		line = 0;
		entry = new CEntry;

		while (!(gotfrom && gotsubject && gotdate) && (line < 20) && !in.eof())
		{
			line++;
			posf = instr.Find("From: ");
			poss = instr.Find("Subject: ");
			posd = instr.Find("Date: ");
			if (posf == 0)
			{
				help = instr.Right(instr.GetLength()-6); 
				help.TrimLeft();
				entry->SetFrom(help);
				gotfrom = TRUE;
			}
			else if (poss == 0)
			{
				help = instr.Right(instr.GetLength()-9); 
				help.TrimLeft();
				posre1 = help.Find("Re:");
				posre2 = help.Find("RE:");
				if (posre1 == 0 | posre2 == 0)
				{
					help2 = help.Right(help.GetLength()-3);
					help2.TrimLeft();
					entry->SetSubject(help2);
				}
				else
					entry->SetSubject(help);
				gotsubject = TRUE;
			}
			else if (posd == 0)
			{
				help = instr.Right(instr.GetLength()-5);
				help.TrimLeft();
				ParseDate(help, date);
				entry->SetDate(date);
				gotdate = TRUE;
			}

			in.getline(buf, 100);
			instr = buf;
		}

		if ((line >= 20) || in.eof())
				return FALSE;
		
		//Body einlesen
		while (instr.GetLength() != 0)
		{
			in.getline(buf, 100);
			instr = buf;
		}
		in.getline(buf, 100);
		instr = buf;
		body = "";
		while (!in.eof() && !end)
		{ 
			if (instr.Left(13) == "+**//***+--//")
				end = TRUE;
			else
			{
				body += instr;
				body += "\r\n";
			}
			in.getline(buf, 100);
			instr = buf;
		}
		entry->SetBody(body);
		list.AddTail(*entry);

	}
	return TRUE;
}



