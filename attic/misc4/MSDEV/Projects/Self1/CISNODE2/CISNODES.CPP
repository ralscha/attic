
/////////////////////////////////////////////////////////////////////////////
//
// cisnodes.cpp : Class behavior for the application and the main window.
//
/////////////////////////////////////////////////////////////////////////////


#include "stdafx.h"
#include <ctl3d.h>
#include "cisnodes.h"


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

	if (m_lpCmdLine[0] != '\0')
	{
		// Add command line processing here
	}

	return TRUE;
}



BEGIN_MESSAGE_MAP(CMainDlgWindow, CDialog)
	//{{AFX_MSG_MAP(CMainDlgWindow)
	ON_WM_CTLCOLOR()
	ON_WM_ERASEBKGND()
	ON_BN_CLICKED(IDC_ABOUT, OnAbout)
	ON_BN_CLICKED(IDC_EXIT, OnExit)
	ON_BN_CLICKED(IDC_SUCHEN, OnSuchen)
	ON_BN_CLICKED(IDC_TOPMOST, OnTopmost)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

void CMainDlgWindow::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);			// Call the base class first

	//{{AFX_DATA_MAP(CMainDlgWindow)    
	DDX_Check(pDX, IDC_TOPMOST, m_topmost);
	//}}AFX_DATA_MAP
	DDX_Text(pDX, IDC_EDIT, m_Set.m_strSuchen);
	DDX_FieldText(pDX, IDC_NODE, m_Set.m_ORT, &m_Set);
}

//-----------------------------------------------------------------------
// CMainDlgWindow constructor:
// Create the dialog box using the definition in the dialog template
//-----------------------------------------------------------------------
CMainDlgWindow::CMainDlgWindow()
{
	//{{AFX_DATA_INIT(CMainDlgWindow)
	m_topmost = FALSE;
	//}}AFX_DATA_INIT

	Create(IDD);		// Create the Main Dialog Window (Dialog ID = IDD)
}

//-----------------------------------------------------------------------
// OnInitdialog() is called after the dialog has been created but before
// it is displayed. Perform initialization of the dialog here.
//-----------------------------------------------------------------------
BOOL CMainDlgWindow::OnInitDialog()
{
	m_Set.m_strFilter = "ABK = ?"; 
	m_Set.Open();
	CDialog::OnInitDialog();       
	GetDlgItem(IDC_EDIT)->SetFocus();
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
	CDialog aboutdlg(IDD_ABOUTBOX, this);
	aboutdlg.DoModal();
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


void CMainDlgWindow::OnExit()
{
	if (m_Set.IsOpen())
		m_Set.Close();
		
	DestroyWindow();
}

void CMainDlgWindow::OnSuchen()
{
	if (!m_Set.IsOpen())   
		return;     
		                      
	UpdateData(TRUE);	
	m_Set.Requery();
	if (m_Set.IsEOF())
		m_Set.SetFieldNull(&(m_Set.m_ORT), FALSE);
	UpdateData(FALSE);	
	((CEdit*)GetDlgItem(IDC_EDIT))->SetSel(0, -1);	
}

void CMainDlgWindow::OnTopmost()
{
	UpdateData(TRUE);
	if (m_topmost)
   	SetWindowPos(&wndTopMost, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
   else
      SetWindowPos(&wndNoTopMost, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
}
