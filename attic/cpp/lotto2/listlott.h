// listlott.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// ListLotto dialog

class ListLotto : public CDialog
{
// Construction
public:
	ListLotto(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(ListLotto)
	enum { IDD = IDD_DIALOG1 };
	CListBox	m_listlz;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(ListLotto)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(ListLotto)
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
