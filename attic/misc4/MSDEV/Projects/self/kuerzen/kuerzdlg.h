// kuerzdlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CKuerzenDlg dialog

class CKuerzenDlg : public CDialog
{
// Construction
public:
	CKuerzenDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CKuerzenDlg)
	enum { IDD = IDD_KUERZEN_DIALOG };
	long	m_nenner;
	long	m_zaehler;
	CString	m_nennergk;
	CString	m_zaehlergk;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CKuerzenDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation

private:
	long int ggT(long int a, long int b);

protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CKuerzenDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnRechnen();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
