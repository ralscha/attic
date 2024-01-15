// lotto2.h : main header file for the LOTTO2 application
//

//#include "stdafx.h" 
#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CLotto2App:
// See lotto2.cpp for the implementation of this class
//

class CLotto2App : public CWinApp
{
public:
	CLotto2App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLotto2App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CLotto2App)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////
