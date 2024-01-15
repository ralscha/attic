
//======================================================================
// DvE.h : main header file for the DvE application
//======================================================================

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // Main application symbols
#include "ssdialog.h"
#include "stdialog.h"    
#include "zieldial.h"

//----------------------------------------------------------------------
// CDialogApp : Class definition for the entire application.
//----------------------------------------------------------------------
class CDialogApp : public CWinApp
{
public:
	virtual BOOL InitInstance();
};


//----------------------------------------------------------------------
// CMainDlgWindow : The main window for the DvE application.
//----------------------------------------------------------------------
class CMainDlgWindow : public CDialog
{
private:
	BOOL human, ende;
	int aktuelleStrategie;
	int totalHuma;
	int totalComp;
	int rpteComp;
	int rpteHuma;
	int zufall;
	int strategie[16];
	int computer, mensch;
   BOOL meldung1;
   int ziel;
   char buffer[6];
                
	int StrategieAuswaehlen();
	void ComputerSpiel();

public:
	CMainDlgWindow();					// Constructor.
	//{{AFX_DATA(CMainDlgWindow)
	enum { IDD = MAIN_DLG_WINDOW };
	//}}AFX_DATA

protected:
	//{{AFX_MSG(CMainDlgWindow)
	afx_msg void OnAbout();				// Creates and displays an About box.
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);// Used to paint the icon
	afx_msg void OnRundebeenden();
	afx_msg void OnWuerfeln();
	afx_msg void OnMeldung();
	afx_msg void OnSpielstand();
	afx_msg void OnStrategie();
	afx_msg void OnZiel();
	//}}AFX_MSG

	BOOL OnInitDialog();				// Dialog initialization
	void PostNcDestroy();				// Called after window is destroyed
   virtual void OnCancel();     
   
   DECLARE_MESSAGE_MAP()
};


