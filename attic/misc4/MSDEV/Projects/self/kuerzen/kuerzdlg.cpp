// kuerzdlg.cpp : implementation file
//

#include "stdafx.h"
#include "kuerzen.h"
#include "kuerzdlg.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CKuerzenDlg dialog

CKuerzenDlg::CKuerzenDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CKuerzenDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CKuerzenDlg)
	m_nenner = 0;
	m_zaehler = 0;
	m_nennergk = _T("");
	m_zaehlergk = _T("");
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CKuerzenDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CKuerzenDlg)
	DDX_Text(pDX, IDC_EDITNENNER, m_nenner);
	DDX_Text(pDX, IDC_EDITZAEHLER, m_zaehler);
	DDX_Text(pDX, IDC_STATICNENNERGK, m_nennergk);
	DDX_Text(pDX, IDC_STATICZAEHLERGK, m_zaehlergk);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CKuerzenDlg, CDialog)
	//{{AFX_MSG_MAP(CKuerzenDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDRECHNEN, OnRechnen)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CKuerzenDlg message handlers

BOOL CKuerzenDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	CenterWindow();
	
	// TODO: Add extra initialization here
	
	GetDlgItem(IDC_EDITZAEHLER)->SetFocus();	
	((CEdit*)GetDlgItem(IDC_EDITZAEHLER))->SetSel(0, -1);

	return FALSE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CKuerzenDlg::OnPaint() 
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
HCURSOR CKuerzenDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

long int CKuerzenDlg::ggT(long int a, long int b)
{
	long int help, rest;

	if (a < b)
	{ 
		help = a; a = b; b = help; 
	}

	do
	{
		rest = a % b;
		if (rest > 0)
		{ 
			a = b; b = rest;
		}
	} while (rest != 0);

	return b;
}

void CKuerzenDlg::OnRechnen() 
{
	char buffer[10];
	long int ggt;
	
	UpdateData(TRUE);

	ggt = ggT(m_zaehler, m_nenner);

	wsprintf(buffer, "%ld", m_zaehler / ggt);
	m_zaehlergk = buffer;
	
	wsprintf(buffer, "%ld", m_nenner / ggt);
	m_nennergk = buffer; 

	UpdateData(FALSE);
}
