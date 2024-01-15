#ifndef __ZAHLUNG_H
#define __ZAHLUNG_H
      

struct ZStruct
{
	CString* ktonr;
	CString* bank;
	CString* inhaber;
	LONG betrag;
	CString* unr[6];
};


class CZahlung : public CObject
{ 
	DECLARE_SERIAL(CZahlung)

	private:
		CString* ktonr;
		CString* bank;
		CString* inhaber;
		LONG betrag;
		CString* unr[6];
	
	public:
		CZahlung();  
		CZahlung(const ZStruct& zs);    
		~CZahlung();
		
		CString& GetKtoNr() const { return *ktonr; }
		CString& GetUNr(int index) const { return *unr[index]; }
		CString& GetBank() const { return *bank; }
		CString& GetInhaber() const { return *inhaber; }
		LONG GetBetrag() const { return betrag; }
		void SetBetrag(LONG be) { betrag = be; }
		void SetUNr(int index, CString& str) 
			{ delete unr[index]; unr[index] = new CString(str); } 
		
		
		virtual void Serialize(CArchive& ar);
};      
      
#endif      
