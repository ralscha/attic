#ifndef __LOTTOCLA_H
#define __LOTTOCLA_H
      

typedef int INTARRAY6[6];

typedef int INTARRAY45[45];
typedef int SUMARRAY[235];
typedef int PAARARRAY[45][45];
typedef int VERBUNDARRAY[11];
typedef int GERARRAY[7];


class CZiehung : public CObject
{
	public:
		enum Verbund {Nichts, Zwilling, Drilling, Vierling, Fuenfling,
				Sechsling, Doppelzwilling, Doppeldrilling, DrillingZwilling,
				Dreifachzwilling, VierlingZwilling};

	private:
		INTARRAY6 zahlen;
		int zz;
		int nr;
		int jahr;

		Verbund vb;
		int gerade;
		int summe;

	public:
		CZiehung(INTARRAY6& zahl, int z, int n, int j);
		CZiehung();
		CZiehung(const CZiehung& zie);
		virtual ~CZiehung() {}
		const CZiehung& operator=(const CZiehung& zie);
		int GetJahr() const { return jahr; }
		int GetNr() const { return nr; }
		int GetZZ() const { return zz; }
		void GetZahlen(INTARRAY6& zahl) const;
		int GetGerade() const { return gerade; }
		int GetUngerade() const { return 6-gerade; }
		int GetSumme() const { return summe; }
		Verbund GetVerbund() const { return vb; }

	private:
		Verbund SucheVerbund(INTARRAY6& zahl);
		BOOL Odd(int zahl) { return zahl % 2; } 

	public:
		virtual void Serialize(CArchive& ar);
	
	
	DECLARE_SERIAL(CZiehung)
};

inline void AFXAPI DestructElements(CZiehung* pElements, int nCount)
{

}

class CLottozahlen : public CObject
{
	public:
		enum ab {alle, aus42, aus45, neu};
	
	private: 
		CArray<CZiehung, CZiehung&> ziehungen;
		int totalungerade;
		int totalgerade;
		INTARRAY45 ausstehend;
		INTARRAY45 haeufigkeit;
		SUMARRAY totalsumme;
		PAARARRAY paar;
		VERBUNDARRAY totalverbund;
		GERARRAY haeufger;
		
		static char datfilename[];

	public:
		CLottozahlen();
		void AddZiehung(INTARRAY6& zahl, int z, int n, int ja);
		int GetTotalGerade() const { return totalgerade; }
		int GetTotalUngerade() const { return totalungerade; }
		void GetAusstehend(INTARRAY45& zahl) const;
		void GetHaeufigkeit(INTARRAY45& zahl) const;
		void GetTotalSumme(SUMARRAY& sum) const;
		void GetPaar(PAARARRAY& pa) const;
		void GetTotalVerbund(VERBUNDARRAY& verb) const;
		void GetTotalGer(GERARRAY& ger) const;
		
		unsigned GetAnzahlAusspielungen() const
			{ return ziehungen.GetSize(); }
		
		
		CZiehung& operator [] (int loc);

		virtual void Serialize(CArchive& ar);
		void CalculateDatas();
		void LoadData(int ls);
		void SaveData();
		void RemoveAll() { ziehungen.RemoveAll(); }
	
	DECLARE_SERIAL(CLottozahlen)
};



#endif
