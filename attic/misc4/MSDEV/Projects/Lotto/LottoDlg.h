// LottoDlg.h : header file
//
#include "lottocla.h"
/////////////////////////////////////////////////////////////////////////////
// CLottoDlg dialog


class CLottoDlg : public CDialog
{
// Construction
public:
	CLottoDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CLottoDlg)
	enum { IDD = IDD_LOTTO_EINGABE_DLG };
	int		m_nr;
	int		m_jahr;
	int		m_zahl1;
	int		m_zahl2;
	int		m_zahl3;
	int		m_zahl4;
	int		m_zahl5;
	int		m_zahl6;
	int		m_zz;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLottoDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	CLottozahlen lottozahlen;
	BOOL changed;
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CLottoDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnAdd();
	afx_msg void OnSaveexit();
	afx_msg void OnCreatehtml();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
