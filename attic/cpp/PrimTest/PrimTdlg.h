// PrimTdlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CPrimTestDlg dialog

class CPrimTestDlg : public CDialog
{
// Construction
public:
	CPrimTestDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CPrimTestDlg)
	enum { IDD = IDD_PRIMTEST_DIALOG };
	long	m_zahl;
	CString	m_primtext;
	CString	m_teiltext;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPrimTestDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
private:
	long int isPrim(long int z);

protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CPrimTestDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnTest();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
