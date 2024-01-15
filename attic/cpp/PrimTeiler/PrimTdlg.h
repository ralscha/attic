// PrimTdlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CPrimTeilerDlg dialog

class CPrimTeilerDlg : public CDialog
{
// Construction
public:
	CPrimTeilerDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CPrimTeilerDlg)
	enum { IDD = IDD_PRIMTEILER_DIALOG };
	CListBox	m_listteiler;
	long	m_zahl;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPrimTeilerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
private:
	long int NextPrim(long int k);
	BOOL Primzahl(long int n);
	
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CPrimTeilerDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnShow();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
