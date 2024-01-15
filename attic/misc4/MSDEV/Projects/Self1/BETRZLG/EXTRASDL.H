// extrasdl.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CExtrasDlg dialog

class CExtrasDlg : public CDialog
{
// Construction
public:
	CExtrasDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CExtrasDlg)
	enum { IDD = IDD_OPTIONSDIALOG };
	CString	m_z1;
	CString	m_z2;
	CString	m_z3;
	CString	m_z4;
	CString	m_histname;
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CExtrasDlg)
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
