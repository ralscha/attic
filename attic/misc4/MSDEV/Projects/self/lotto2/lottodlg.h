
// lottodlg.h : header file
//
#include "..\lottocommon\lottocla.h"

/////////////////////////////////////////////////////////////////////////////
// CLotto2Dlg dialog

class CLotto2Dlg : public CDialog
{
// Construction
public:
	CLotto2Dlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CLotto2Dlg)
	enum { IDD = IDD_LOTTO2_DIALOG };
	int		m_nr;
	int		m_jahr;
	int		m_zz;
	int 	m_zahl1;
	int		m_zahl2;
	int		m_zahl3;
	int		m_zahl4;
	int		m_zahl5;
	int		m_zahl6;
	//}}AFX_DATA
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLotto2Dlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;
	BOOL changed;
	CLottozahlen lottozahlen;

	//Generated message map functions
	//{{AFX_MSG(CLotto2Dlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnExit();
	afx_msg void OnAdd();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
