// lottodlg.cpp : implementation file
//

#include "stdafx.h"
#include "lotto2.h"
#include "lottodlg.h"
#include "..\lottocommon\lottocla.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
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

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//{{AFX_MSG(CAboutDlg)
	virtual BOOL OnInitDialog();
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
// CAboutDlg message handlers

BOOL CAboutDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	CenterWindow();
	
	// TODO: Add extra about dlg initialization here
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

/////////////////////////////////////////////////////////////////////////////
// CLotto2Dlg dialog

CLotto2Dlg::CLotto2Dlg(CWnd* pParent /*=NULL*/)
	: CDialog(CLotto2Dlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CLotto2Dlg)
	m_nr = 0;
	m_jahr = 0;	
	m_zz = 0;
	m_zahl1 = 0;
	m_zahl2 = 0;
	m_zahl3 = 0;
	m_zahl4 = 0;
	m_zahl5 = 0;
	m_zahl6 = 0;					 
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	changed = FALSE;
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CLotto2Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CLotto2Dlg)
	DDX_Text(pDX, IDC_EDITNR, m_nr);
	DDV_MinMaxInt(pDX, m_nr, 1, 53);
	DDX_Text(pDX, IDC_EDITJAHR, m_jahr);
	DDV_MinMaxInt(pDX, m_jahr, 1995, 2500);
	DDX_Text(pDX, IDC_EDITZ1, m_zahl1);
	DDV_MinMaxInt(pDX, m_zahl1, 1, 45);
	DDX_Text(pDX, IDC_EDITZ2, m_zahl2);
	DDV_MinMaxInt(pDX, m_zahl2, 1, 45);
	DDX_Text(pDX, IDC_EDITZ3, m_zahl3);
	DDV_MinMaxInt(pDX, m_zahl3, 1, 45);
	DDX_Text(pDX, IDC_EDITZ4, m_zahl4);
	DDV_MinMaxInt(pDX, m_zahl4, 1, 45);
	DDX_Text(pDX, IDC_EDITZ5, m_zahl5);
	DDV_MinMaxInt(pDX, m_zahl5, 1, 45);
	DDX_Text(pDX, IDC_EDITZ6, m_zahl6);
	DDV_MinMaxInt(pDX, m_zahl6, 1, 45);
	DDX_Text(pDX, IDC_EDITZZ, m_zz);
	DDV_MinMaxInt(pDX, m_zz, 1, 45);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CLotto2Dlg, CDialog)
	//{{AFX_MSG_MAP(CLotto2Dlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDEXIT, OnExit)
	ON_BN_CLICKED(IDADD, OnAdd)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CLotto2Dlg message handlers

BOOL CLotto2Dlg::OnInitDialog()
{
	lottozahlen.LoadData(CLottozahlen::alle);
	m_nr = lottozahlen[lottozahlen.GetAnzahlAusspielungen()-1].GetNr()+1;
	m_jahr = lottozahlen[lottozahlen.GetAnzahlAusspielungen()-1].GetJahr();
	
	CDialog::OnInitDialog();
	CenterWindow();

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
	
	GetDlgItem(IDC_EDITNR)->SetFocus();	
	((CEdit*)GetDlgItem(IDC_EDITNR))->SetSel(0, -1);
	return FALSE;  // return TRUE  unless you set the focus to a control
}

void CLotto2Dlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CLotto2Dlg::OnPaint() 
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
HCURSOR CLotto2Dlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}




void CLotto2Dlg::OnExit() 
{
	if (changed)
		lottozahlen.SaveData();

	CDialog::OnCancel(); // Dialog beenden
	
}

void CLotto2Dlg::OnAdd() 
{
	UpdateData(TRUE);
	int m_zahlen[6];
	m_zahlen[0] = m_zahl1;
	m_zahlen[1] = m_zahl2;
	m_zahlen[2] = m_zahl3;
	m_zahlen[3] = m_zahl4;
	m_zahlen[4] = m_zahl5;
	m_zahlen[5] = m_zahl6;

	int j,i;
	BOOL doppelt = FALSE;
	BOOL fehlen = FALSE;

	if (m_jahr == 0) fehlen = TRUE;
	if (m_nr == 0) fehlen = TRUE;
	for (i = 0; i < 6; i++)
		if (m_zahlen[i] == 0) fehlen = TRUE;
			
	for (i = 0; i < 6; i++)
		for (j = i+1; j < 6; j++)
			if (m_zahlen[i] == m_zahlen[j])
				doppelt = TRUE; 
	
	if (fehlen || doppelt)
	{
		MessageBox("Fehlerhafte Eingabe");
		GetDlgItem(IDC_EDITNR)->SetFocus();
	}
	else
	{
		changed = TRUE;
		lottozahlen.AddZiehung(m_zahlen, m_zz, m_nr, m_jahr);
		MessageBox("Ziehung wurde hinzugefügt");
		m_zahl1 = 0;
		m_zahl2 = 0;
		m_zahl3 = 0;
		m_zahl4 = 0;
		m_zahl5 = 0;
		m_zahl6 = 0;
		m_zz = 0;
		m_nr++;
		UpdateData(FALSE);
	}
	GetDlgItem(IDC_EDITNR)->SetFocus();	
}
