// seitedlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CSeiteDlg dialog

class CSeiteDlg : public CDialog
{
// Construction
public:
	CSeiteDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CSeiteDlg)
	enum { IDD = IDD_SEITEEINRICHTEN };
	CString	m_links;
	CString	m_oben;
	CString	m_rechts;
	CString	m_unten;
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CSeiteDlg)
	//}}AFX_MSG        
	virtual BOOL OnInitDialog();  
		
	DECLARE_MESSAGE_MAP()
};
