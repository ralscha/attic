// betrzlg.h : main header file for the BETRZLG application
//

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgApp:
// See betrzlg.cpp for the implementation of this class
//

class CBetrzlgApp : public CWinApp
{
public:
	CBetrzlgApp();

// Overrides
	virtual BOOL InitInstance();
   virtual int ExitInstance();
   
// Implementation

	//{{AFX_MSG(CBetrzlgApp)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////
