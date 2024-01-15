
//======================================================================
// cisnodes.h : main header file for the cisnodes application
//======================================================================

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // Main application symbols
#include "cisnoset.h"

//----------------------------------------------------------------------
// CDialogApp : Class definition for the entire application.
//----------------------------------------------------------------------
class CDialogApp : public CWinApp
{
public:
	virtual BOOL InitInstance();
};


//----------------------------------------------------------------------
// CMainDlgWindow : The main window for the cisnodes application.
//----------------------------------------------------------------------
class CMainDlgWindow : public CDialog
{
DECLARE_MESSAGE_MAP()

private:
	CCisnodesSet m_Set;   
	
public:
	CMainDlgWindow();					// Constructor.
	//{{AFX_DATA(CMainDlgWindow)
	enum { IDD = MAIN_DLG_WINDOW };
	BOOL	m_topmost;
	//}}AFX_DATA

protected:
	//{{AFX_MSG(CMainDlgWindow)
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);// Used to paint the icon
	afx_msg void OnAbout();
	afx_msg void OnExit();
	afx_msg void OnSuchen();
	afx_msg void OnTopmost();
	//}}AFX_MSG

	BOOL OnInitDialog();				// Dialog initialization
	void PostNcDestroy();				// Called after window is destroyed
	void DoDataExchange(CDataExchange*);	// DDX/DDV support
};


