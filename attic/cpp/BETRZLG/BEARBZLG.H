// bearbzlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CBearbZlg dialog

class CBearbZlg : public CDialog
{
// Construction
public:
	CBearbZlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CBearbZlg)
	enum { IDD = IDD_BEARBEITENZLG };
	CString	m_bank;
	double	m_betrag;
	CString	m_inhaber;
	CString	m_pcnr;
	CString	m_unr1;
	CString	m_unr2;
	CString	m_unr3;
	CString	m_unr4;
	CString	m_unr5;
	CString	m_unr6;
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CBearbZlg)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
