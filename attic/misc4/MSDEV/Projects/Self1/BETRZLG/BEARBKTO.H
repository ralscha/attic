#ifndef __bearbkto_h
#define __bearbkto_h

// BearbKonto.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CBearbKonto dialog

class CBearbKonto : public CDialog
{
// Construction
public:
	CBearbKonto(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CBearbKonto)
	enum { IDD = IDD_BEARBEITENKONTO };
	CString	m_bank;
	CString	m_inhaber;
	CString	m_pcnr;
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CBearbKonto)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
#endif
