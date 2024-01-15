// CreateHTMLDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CCreateHTMLDialog dialog

class CCreateHTMLDialog : public CDialog
{
// Construction
public:
	CCreateHTMLDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CCreateHTMLDialog)
	enum { IDD = IDD_CREATEHTML };
	int		m_day;
	int		m_month;
	int		m_year;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCreateHTMLDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CCreateHTMLDialog)
	afx_msg void OnCreateHTML();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
