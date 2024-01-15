// Freitdlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CFreitagDlg dialog

class CFreitagDlg : public CDialog
{
// Construction
public:
	CFreitagDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CFreitagDlg)
	enum { IDD = IDD_FREITAG_DIALOG };
	CListBox	m_MonateListBox;
	int		m_jahr;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CFreitagDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CFreitagDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnAnzeigen();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
