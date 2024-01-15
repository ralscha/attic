// betrzvw.h : interface of the CBetrzlgView class
//
/////////////////////////////////////////////////////////////////////////////
#include "zahlung.h"

class CBetrzlgSet;

class CBetrzlgView : public CFormView
{
private:
	void Array2ListBox();
	void Object2ListBox(CZahlung* zlg, int lbindex1 = -1, int lbindex2 = -1,
											int lbindex3 = -1);

protected: // create from serialization only
	CBetrzlgView();
	DECLARE_DYNCREATE(CBetrzlgView)

public:
	//{{AFX_DATA(CBetrzlgView)
	enum { IDD = IDD_BETRZLG_FORM };
	CListBox	m_ctrlInhaber;
	CListBox	m_listBox;
	CBetrzlgSet* m_pSet;
	CString	m_unr1;
	CString	m_unr2;
	CString	m_unr3;
	CString	m_unr4;
	CString	m_unr5;
	CString	m_unr6;
	int		m_auftragsnr;
	int		m_anzahl;  
	CString	m_inhaber;
	float	m_betrag;
	CString	m_totalstr;
	//}}AFX_DATA
	
	double	m_total;
	
	int hoehe1, hoehe2, hoehe3, hoehe4, hoehe5;
	int rrand, rrand2, lrand, lrand2, orand, urand;
	int aTabs[1];
	int zr;     
	int zeilen1;
	int zeilen2;
	float pixelpermm;
	CFont* font1;
   CFont* font2;
   CFont* font3;
   CFont* font4;
   CFont* font5;     
   CPen* pen2;
   CPen* pen3;
   BOOL ptt;
   
// Attributes
public:
	CBetrzlgDoc* GetDocument();

// Operations
public:
	virtual CRecordset* OnGetRecordset();

// Implementation
public:
	virtual ~CBetrzlgView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	virtual void OnInitialUpdate(); // called first time after construct
   virtual void OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint);
		   
	// Printing support
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
   virtual void OnPrint(CDC* pDC, CPrintInfo* pInfo);   
   
// Generated message map functions
protected:
	//{{AFX_MSG(CBetrzlgView)
	afx_msg void OnKillfocusPcnr();
	afx_msg void OnAdd();
	afx_msg void OnKillfocusAuftragsnr();
	afx_msg void OnBearbeitenLschen();
	afx_msg void OnUpdateBearbeitenLschen(CCmdUI* pCmdUI);
	afx_msg void OnDblclkListbox();
	afx_msg void OnBearbeitenWiederholen();
	afx_msg void OnUpdateBearbeitenWiederholen(CCmdUI* pCmdUI);
	afx_msg void OnDateiDruckenExtern();
	afx_msg void OnDateiDruckenIntern();
	afx_msg void OnDateiSeitenansichtExtern();
	afx_msg void OnDateiSeitenansichtIntern();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in betrzvw.cpp
inline CBetrzlgDoc* CBetrzlgView::GetDocument()
   { return (CBetrzlgDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////
