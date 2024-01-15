// File2AccessDlg.h : header file
//
#include "Entry.h"
/////////////////////////////////////////////////////////////////////////////
// CFile2AccessDlg dialog

class CFile2AccessDlg : public CDialog
{
// Construction
public:
	CFile2AccessDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CFile2AccessDlg)
	enum { IDD = IDD_FILE2ACCESS_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CFile2AccessDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;
	CList<CEntry, CEntry&> elist;	
	CMFCRecordSet set;

	// Generated message map functions
	//{{AFX_MSG(CFile2AccessDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnStart();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
