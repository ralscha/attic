#ifndef __CENTRY_H
#define __CENTRY_H


class CEntry : public CObject
{
	private:
		COleDateTime date;
		CString from;
		CString subject;
		CString body;

	public:
		CEntry()
			{ from = subject = body = ""; }

		const CEntry& operator=(const CEntry& e)		
			{	date = e.date;
				from = e.from;
				subject = e.subject;
				body = e.body;
				return *this;
			}

		CEntry(const CEntry& e)
			{
				date = e.date;
				from = e.from;
				subject = e.subject;
				body = e.body;						
			}


		void FillRecordSet(CMFCRecordSet& set) 
			{ set.m_Date = date;
			  set.m_From = from;
			  set.m_Subject = subject;
			  set.m_Mail = body; }

		void SetDate(const COleDateTime& d) { date = d; }
		void SetFrom(const CString& s) { from = s; }
		void SetSubject(const CString& s) { subject = s; }
		void SetBody(const CString& s) { body = s; }



	};

#endif