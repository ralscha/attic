// PiSearchDlg.h : header file
//
#include "Searcher.h"

/////////////////////////////////////////////////////////////////////////////
// CPiSearchDlg dialog

class CPiSearchDlg : public CDialog
{
// Construction
public:
	CPiSearchDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CPiSearchDlg)
	enum { IDD = IDD_PISEARCH_DIALOG };
	CStatic	m_comparescontrol;
	CEdit	m_edit;
	CListBox	m_listBox;
	CString	m_searchString;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPiSearchDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	Searcher searcher;
	void EnableControls(BOOL bEnable);
	BOOL running;
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CPiSearchDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnExit();
	afx_msg void OnSearch();
	afx_msg void OnStop();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
