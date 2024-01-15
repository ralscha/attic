// PiSearchDlg.cpp : implementation file
//
 
#include "stdafx.h"
#include "PiSearch.h"
#include "Searcher.h"
#include "PiSearchDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPiSearchDlg dialog

CPiSearchDlg::CPiSearchDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CPiSearchDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPiSearchDlg)
	m_searchString = _T("");
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	running = FALSE;
}

void CPiSearchDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPiSearchDlg)
	DDX_Control(pDX, IDC_COMPARES, m_comparescontrol);
	DDX_Control(pDX, IDC_SEARCHSTRING, m_edit);
	DDX_Control(pDX, IDC_LIST1, m_listBox);
	DDX_Text(pDX, IDC_SEARCHSTRING, m_searchString);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CPiSearchDlg, CDialog)
	//{{AFX_MSG_MAP(CPiSearchDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_EXIT, OnExit)
	ON_BN_CLICKED(IDC_SEARCH, OnSearch)
	ON_BN_CLICKED(IDC_STOP, OnStop)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPiSearchDlg message handlers

BOOL CPiSearchDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	CString strAboutMenu;
	strAboutMenu.LoadString(IDS_ABOUTBOX);
	if (!strAboutMenu.IsEmpty())
	{
		pSysMenu->AppendMenu(MF_SEPARATOR);
		pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	CWnd* edit = GetDlgItem(IDC_SEARCHSTRING);
	edit->SetFocus();
	
	//////////////////////////
	BOOL b = searcher.SetFile("PI.TXT");
   //////////////////////////

	return FALSE;  // return TRUE  unless you set the focus to a control
}

void CPiSearchDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CPiSearchDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CPiSearchDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CPiSearchDlg::OnExit() 
{
	EndDialog(IDOK);
	
}

void CPiSearchDlg::OnSearch() 
{
	MSG msg;
	LONG lIdle;

	UpdateData(TRUE);
	m_listBox.ResetContent();		
	if (!searcher.onlyDigits(m_searchString))
	{
		MessageBox("Nur Zahlen als Eingabe erlaubt");
		m_edit.SetFocus();
		m_edit.SetSel(0, -1);
	}
	else
	{
		EnableControls(FALSE);
		running = TRUE;

		searcher.SetSearchString(m_searchString);

		while (running)
		{
			while (::PeekMessage(&msg, NULL, 0, 0, PM_REMOVE))
			{
				if (msg.message == WM_QUIT)
				{
					running = FALSE;
					::PostQuitMessage(0);
					break;
				}
				if (!AfxGetApp()->PreTranslateMessage(&msg))
				{
					::TranslateMessage(&msg);
				   ::DispatchMessage(&msg);
				}
			}
			// let MFC do its idle processing
			lIdle = 0;
			while (AfxGetApp()->OnIdle(lIdle++))  ;
			if (searcher.DoSearchStep() == TRUE)
			{
				EnableControls(TRUE);	
				running = FALSE;
				if (searcher.GetNumberofResults() == 0)
					m_listBox.AddString("Nichts gefunden");
				else
					searcher.FillListBox(m_listBox);
				
				m_edit.SetFocus();
				m_edit.SetSel(0, -1);
			}
			searcher.FillCompares(m_comparescontrol);
		}   	
	}
}

void CPiSearchDlg::OnStop() 
{
	running = FALSE;
	EnableControls(TRUE);
	searcher.Stop();
}

void CPiSearchDlg::EnableControls(BOOL bEnable)
{
	CButton* button = (CButton*)GetDlgItem(IDC_SEARCH);
	button->EnableWindow(bEnable);
	button = (CButton*)GetDlgItem(IDC_STOP);
	button->EnableWindow(!bEnable);
	m_edit.EnableWindow(bEnable);
}

