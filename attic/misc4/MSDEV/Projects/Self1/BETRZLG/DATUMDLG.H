// datumdlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CDatumDlg dialog

class CDatumDlg : public CDialog
{
// Construction
public:
	CDatumDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CDatumDlg)
	enum { IDD = IDD_DATUMDIALOG };
	int		m_jahr;
	int		m_monat;
	int		m_tag;
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CDatumDlg)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
