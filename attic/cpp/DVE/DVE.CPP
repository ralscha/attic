
/////////////////////////////////////////////////////////////////////////////
//
// DvE.cpp : Class behavior for the application and the main window.
//
/////////////////////////////////////////////////////////////////////////////


#include "stdafx.h"
#include <ctl3d.h>				// Required for 3D controls
#include "dve.h"
#include "time.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// The one and only CDialogApp object
/////////////////////////////////////////////////////////////////////////////
CDialogApp NEAR theApp;

/////////////////////////////////////////////////////////////////////////////
// CDialogApp initialization
/////////////////////////////////////////////////////////////////////////////
BOOL CDialogApp::InitInstance()
{

	SetDialogBkColor();        			// Set dialog and messagebox colors to gray
	Ctl3dRegister(m_hInstance);			// Register the application with CTL3D
	Ctl3dAutoSubclass(m_hInstance);		// Auto subclass all dialogs

	m_pMainWnd = new CMainDlgWindow();	// Create the main application window
	ASSERT(m_pMainWnd);					// Check for failure here
   
	return TRUE;
}



BEGIN_MESSAGE_MAP(CMainDlgWindow, CDialog)
	//{{AFX_MSG_MAP(CMainDlgWindow)
	ON_COMMAND(ID_ABOUT, OnAbout)
	ON_WM_CTLCOLOR()
	ON_WM_ERASEBKGND()
	ON_BN_CLICKED(IDC_RUNDEBEENDEN, OnRundebeenden)
	ON_BN_CLICKED(IDC_WUERFELN, OnWuerfeln)
	ON_COMMAND(ID_MELDUNG, OnMeldung)
	ON_COMMAND(ID_SPIELSTAND, OnSpielstand)
	ON_COMMAND(ID_STRATEGIE, OnStrategie)
	ON_COMMAND(ID_ZIEL, OnZiel)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


//-----------------------------------------------------------------------
// CMainDlgWindow constructor:
// Create the dialog box using the definition in the dialog template
//-----------------------------------------------------------------------
CMainDlgWindow::CMainDlgWindow()
{
	computer = AfxGetApp()->GetProfileInt("Spielstand", "Computer", 0);
	mensch   = AfxGetApp()->GetProfileInt("Spielstand", "Mensch", 0);
	meldung1 = AfxGetApp()->GetProfileInt("Optionen", "Meldung1", 0); // Aus
	ziel     = AfxGetApp()->GetProfileInt("Optionen", "Ziel", 100);
	strategie[ 0] = AfxGetApp()->GetProfileInt("Strategie", "A", 100);
	strategie[ 1] = AfxGetApp()->GetProfileInt("Strategie", "B", 100);
	strategie[ 2] = AfxGetApp()->GetProfileInt("Strategie", "C", 100);
	strategie[ 3] = AfxGetApp()->GetProfileInt("Strategie", "D", 100);
	strategie[ 4] = AfxGetApp()->GetProfileInt("Strategie", "E", 100);
	strategie[ 5] = AfxGetApp()->GetProfileInt("Strategie", "F", 100);
	strategie[ 6] = AfxGetApp()->GetProfileInt("Strategie", "G", 100);
	strategie[ 7] = AfxGetApp()->GetProfileInt("Strategie", "H", 100);
	strategie[ 8] = AfxGetApp()->GetProfileInt("Strategie", "I", 100);
	strategie[ 9] = AfxGetApp()->GetProfileInt("Strategie", "J", 100);
	strategie[10] = AfxGetApp()->GetProfileInt("Strategie", "K", 100);
	strategie[11] = AfxGetApp()->GetProfileInt("Strategie", "L", 100);
	strategie[12] = AfxGetApp()->GetProfileInt("Strategie", "M", 100);
	strategie[13] = AfxGetApp()->GetProfileInt("Strategie", "N", 100);
	strategie[14] = AfxGetApp()->GetProfileInt("Strategie", "O", 100);
	strategie[15] = AfxGetApp()->GetProfileInt("Strategie", "P", 100);
 
   srand( (unsigned)time( NULL ) );
	human = TRUE;
	ende = FALSE;
	aktuelleStrategie = StrategieAuswaehlen();
	totalHuma = totalComp = rpteComp = rpteHuma = 0;
	
	Create(IDD);		// Create the Main Dialog Window (Dialog ID = IDD)
}


//-----------------------------------------------------------------------
// OnInitdialog() is called after the dialog has been created but before
// it is displayed. Perform initialization of the dialog here.
//-----------------------------------------------------------------------
BOOL CMainDlgWindow::OnInitDialog()
{
	CDialog::OnInitDialog();
	
	wsprintf(buffer, "%4d", ziel);
	SetDlgItemText(IDC_ZIEL, buffer);
	
	CMenu* pMenu = GetMenu();
	if (meldung1)
		pMenu->CheckMenuItem(ID_MELDUNG, MF_BYCOMMAND | MF_CHECKED);
	else
		pMenu->CheckMenuItem(ID_MELDUNG, MF_BYCOMMAND | MF_UNCHECKED);
		
	return TRUE;
}

//-----------------------------------------------------------------------
// CWnd::PostNcDestroy() is the last member function which will be called.
// We will use this to shut down our application.
//-----------------------------------------------------------------------
void CMainDlgWindow::PostNcDestroy()
{
	Ctl3dUnregister(AfxGetInstanceHandle());

	PostQuitMessage(0);				// End the application
	delete this;					// Delete 'this' dialog box object
}


//
//------------------ Procedure to create and display the About Box -------------------
//
void CMainDlgWindow::OnAbout()
{
	CModalDialog aboutdlg(IDD_ABOUTBOX, this);
	aboutdlg.DoModal();
}

void CMainDlgWindow::OnCancel()
{                        
	AfxGetApp()->WriteProfileInt("Spielstand", "Computer", computer);
	AfxGetApp()->WriteProfileInt("Spielstand", "Mensch", mensch);
	AfxGetApp()->WriteProfileInt("Optionen", "Meldung1", meldung1);
	AfxGetApp()->WriteProfileInt("Optionen", "Ziel", ziel);
	AfxGetApp()->WriteProfileInt("Strategie", "A", strategie[ 0]);
	AfxGetApp()->WriteProfileInt("Strategie", "B", strategie[ 1]);
	AfxGetApp()->WriteProfileInt("Strategie", "C", strategie[ 2]);
	AfxGetApp()->WriteProfileInt("Strategie", "D", strategie[ 3]);
	AfxGetApp()->WriteProfileInt("Strategie", "E", strategie[ 4]);
	AfxGetApp()->WriteProfileInt("Strategie", "F", strategie[ 5]);
	AfxGetApp()->WriteProfileInt("Strategie", "G", strategie[ 6]);
	AfxGetApp()->WriteProfileInt("Strategie", "H", strategie[ 7]);
	AfxGetApp()->WriteProfileInt("Strategie", "I", strategie[ 8]);
	AfxGetApp()->WriteProfileInt("Strategie", "J", strategie[ 9]);
	AfxGetApp()->WriteProfileInt("Strategie", "K", strategie[10]);
	AfxGetApp()->WriteProfileInt("Strategie", "L", strategie[11]);
	AfxGetApp()->WriteProfileInt("Strategie", "M", strategie[12]);
	AfxGetApp()->WriteProfileInt("Strategie", "N", strategie[13]);
	AfxGetApp()->WriteProfileInt("Strategie", "O", strategie[14]);
	AfxGetApp()->WriteProfileInt("Strategie", "P", strategie[15]);

	DestroyWindow();
}





HBRUSH CMainDlgWindow::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
const MSG	*msg=GetCurrentMessage();							// Get the current message

	CModalDialog::OnCtlColor(pDC, pWnd, nCtlColor);				// Call base class first
return Ctl3dCtlColorEx(msg->message, msg->wParam, msg->lParam); // ...now call 3D routine.
}

BOOL CMainDlgWindow::OnEraseBkgnd(CDC* pDC)
{
	if(IsIconic())									// Are we an icon
	{
		HICON hIcon=theApp.LoadIcon(IDR_MAIN_ICON);	// Load the icon.
		ASSERT(hIcon);								// Make sure it loaded!

		SendMessage(WM_ICONERASEBKGND, (WPARAM)pDC->m_hDC, 0L);	// Paint the background
		pDC->DrawIcon(0,0, hIcon);					// Draw the icon.
		::DestroyIcon(hIcon);						// Destroy (release) the icon
		return TRUE;								// All done... return success.
	}

	return CDialog::OnEraseBkgnd(pDC);				// Not an icon. Let windows handle it.
}


void CMainDlgWindow::OnRundebeenden()
{
	if (human)
	{
		if (zufall == 1)
			rpteHuma = 0;
		totalHuma += rpteHuma;
		rpteHuma = 0;
		wsprintf(buffer, "%3d", rpteHuma);
		SetDlgItemText(IDC_RPTEHUMA, buffer);
		wsprintf(buffer, "%3d", totalHuma);
		SetDlgItemText(IDC_TOTALHUMA, buffer);
		if (totalHuma >= ziel)
		{
			MessageBox("Du hast gewonnen :-(", "Spielende", MB_OK);
			mensch++;
			strategie[aktuelleStrategie] -= 10;
			if (strategie[aktuelleStrategie] <= 0)
				strategie[aktuelleStrategie] = 1;
			ende = TRUE;
		}
		else
		{
			GetDlgItem(IDC_WUERFELN)->EnableWindow(FALSE);
			GetDlgItem(IDC_RUNDEBEENDEN)->EnableWindow(FALSE);
			human = FALSE;
			ComputerSpiel();
		}
	}
	else
	{
		if (zufall == 1)
			rpteComp = 0;
		totalComp += rpteComp;
		rpteComp = 0;
		wsprintf(buffer, "%3d", rpteComp);
		SetDlgItemText(IDC_RPTECOMP, buffer);
		wsprintf(buffer, "%3d", totalComp);
		SetDlgItemText(IDC_TOTALCOMP, buffer);
		
		if (totalComp >= ziel)
		{
			MessageBox("Ich habe gewonnen :-)", "Spielende", MB_OK);
			computer++;
			strategie[aktuelleStrategie] += 10;
			if (strategie[aktuelleStrategie] == 11)
				strategie[aktuelleStrategie] = 10;
			ende = TRUE;
		}
		else
		{
			GetDlgItem(IDC_WUERFELN)->EnableWindow(TRUE);
			GetDlgItem(IDC_RUNDEBEENDEN)->EnableWindow(TRUE);
			human = TRUE;
		}
	}
	
	if (ende)
	{
		if (MessageBox("Noch ein Spiel?", "DvE", MB_ICONQUESTION | MB_YESNO) == IDYES)
		{
			totalComp = totalHuma = 0;
			wsprintf(buffer, "%3d", totalComp);
			SetDlgItemText(IDC_TOTALCOMP, buffer);
			SetDlgItemText(IDC_TOTALHUMA, buffer);
			ende = FALSE;
			GetDlgItem(IDC_WUERFELN)->EnableWindow(TRUE);
			GetDlgItem(IDC_RUNDEBEENDEN)->EnableWindow(TRUE);
			human = TRUE;
			aktuelleStrategie = StrategieAuswaehlen();
		}
		else
			OnCancel();
	}
}

void CMainDlgWindow::OnWuerfeln()
{
	for (int i = 0; i < 70; i++)
	{
		zufall = rand() % 6 + 1;
		for (int n = 0; n < 110; n++);
		wsprintf(buffer, "%d", zufall);
		SetDlgItemText(IDC_WURF, buffer);
	}
	if (human)
	{
		if (zufall == 1)
		{
			if (meldung1)
				MessageBox("Du hast eine 1 gewürfelt und\nverlierst damit alle Punkte\naus dieser Runde",
							  "DvE", MB_OK);
			OnRundebeenden();
		}
		else
		{
			rpteHuma += zufall;
			wsprintf(buffer, "%3d", rpteHuma);
			SetDlgItemText(IDC_RPTEHUMA, buffer);
		}
	}
	else
	{
		rpteComp += zufall;
		wsprintf(buffer, "%3d", rpteComp);
		SetDlgItemText(IDC_RPTECOMP, buffer);
	}
}

void CMainDlgWindow::ComputerSpiel()
{
	int wuerfe = 0;
	
	switch (aktuelleStrategie)
	{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:  do
					{
					 	OnWuerfeln();
					  	wuerfe++;
					}
					while ((wuerfe < aktuelleStrategie+1) && (zufall != 1)
					 		&& ((rpteComp + totalComp) < ziel));
					break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11: do
					{
						OnWuerfeln();
					}
					while ((zufall != 1)	&& ((rpteComp + totalComp) < ziel)
					          && (rpteComp < (aktuelleStrategie-5)*4-1));
					break;

		case 12:
		case 13:
		case 14:
		case 15: do
					{
						OnWuerfeln();
						wuerfe++;
					}
					while ((wuerfe < aktuelleStrategie-9) && (zufall != 1)
							&& (rpteComp < (aktuelleStrategie-9)*4-1)
							&& ((rpteComp + totalComp) < ziel));
					break;
	}

	OnRundebeenden();
}                 

int CMainDlgWindow::StrategieAuswaehlen()
{
	int total = 0;
	for (int i = 0; i < 16; i++)
		total += strategie[i];
		
	zufall = rand() % total + 1;
	
	for (i = 0; i < 16; i++)
	{
		zufall -= strategie[i];
		if (zufall <= 0)
			return i;
	} 
	return 0;
}
		 
void CMainDlgWindow::OnMeldung()
{
	CMenu* pMenu = GetMenu();
	int state = pMenu->GetMenuState(ID_MELDUNG, MF_BYCOMMAND);
	
	if ((state & MF_CHECKED) == MF_CHECKED)
	{
		pMenu->CheckMenuItem(ID_MELDUNG, MF_BYCOMMAND | MF_UNCHECKED);
		meldung1 = FALSE;
	}
	else
	{
		pMenu->CheckMenuItem(ID_MELDUNG, MF_BYCOMMAND | MF_CHECKED);
		meldung1 = TRUE;
	}	
}
                  

void CMainDlgWindow::OnSpielstand()
{
	CSSDialog dlg(this);
	dlg.SetSpielstand(mensch, computer);
	dlg.DoModal();
}

void CMainDlgWindow::OnStrategie()
{
	CSTDialog dlg(this);
	dlg.SetStrategie(&strategie[0]);
	dlg.DoModal();
}

void CMainDlgWindow::OnZiel()
{
	CZielDialog dlg(this);
	dlg.SetZiel(ziel);
	if (dlg.DoModal() == IDOK)   
	{
		ziel = dlg.GetZiel();	
		wsprintf(buffer, "%4d", ziel);
		SetDlgItemText(IDC_ZIEL, buffer);	
	}
}
