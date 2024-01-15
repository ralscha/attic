// PrimTdlg.cpp : implementation file
//

#include "stdafx.h"
#include "PrimTeiler.h"
#include "PrimTdlg.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CPrimTeilerDlg dialog

CPrimTeilerDlg::CPrimTeilerDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CPrimTeilerDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPrimTeilerDlg)
	m_zahl = 0;
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CPrimTeilerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPrimTeilerDlg)
	DDX_Control(pDX, IDC_LISTTEILER, m_listteiler);
	DDX_Text(pDX, IDC_EDITZAHL, m_zahl);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CPrimTeilerDlg, CDialog)
	//{{AFX_MSG_MAP(CPrimTeilerDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDSHOW, OnShow)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPrimTeilerDlg message handlers

BOOL CPrimTeilerDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	CenterWindow();
	
	// TODO: Add extra initialization here
	GetDlgItem(IDC_EDITZAHL)->SetFocus();
	((CEdit*)GetDlgItem(IDC_EDITZAHL))->SetSel(0, -1);
	return FALSE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CPrimTeilerDlg::OnPaint() 
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
HCURSOR CPrimTeilerDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

long int CPrimTeilerDlg::NextPrim(long int k)
{
	do
	{
		k++;
	} while (!Primzahl(k));
	return k;
}

BOOL CPrimTeilerDlg::Primzahl(long int n)
{
	long int k = 2;
	for(;;) 
	{
		if (n == 1) return FALSE;
		if (k*k > n) return TRUE;
		if (n % k == 0) return FALSE;
		k = NextPrim(k);
	}
		
}

void CPrimTeilerDlg::OnShow() 
{
	long int prim = 2;
	long int start;
	char buf[30];

	UpdateData(TRUE);
	m_listteiler.ResetContent();
	start = m_zahl;

	while (!Primzahl(start))
	{
		while (!(start % prim == 0))
			prim = NextPrim(prim);
		
		wsprintf(buf, "%ld", prim);
		m_listteiler.AddString(buf);	
		start /= prim;
	}
	wsprintf(buf, "%ld", start);
	m_listteiler.AddString(buf);


	GetDlgItem(IDC_EDITZAHL)->SetFocus();
	((CEdit*)GetDlgItem(IDC_EDITZAHL))->SetSel(0, -1);
}
