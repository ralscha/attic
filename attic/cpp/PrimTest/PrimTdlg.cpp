// PrimTdlg.cpp : implementation file
//

#include "stdafx.h"
#include "PrimTest.h"
#include "PrimTdlg.h"
#include <math.h>

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CPrimTestDlg dialog

CPrimTestDlg::CPrimTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CPrimTestDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPrimTestDlg)
	m_zahl = 0;
	m_primtext = _T("");
	m_teiltext = _T("");
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CPrimTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPrimTestDlg)
	DDX_Text(pDX, IDC_EDITZAHL, m_zahl);
	DDX_Text(pDX, IDC_STATICPRIM, m_primtext);
	DDX_Text(pDX, IDC_STATICTEIL, m_teiltext);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CPrimTestDlg, CDialog)
	//{{AFX_MSG_MAP(CPrimTestDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDTEST, OnTest)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPrimTestDlg message handlers

BOOL CPrimTestDlg::OnInitDialog()
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

void CPrimTestDlg::OnPaint() 
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
HCURSOR CPrimTestDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

long int CPrimTestDlg::isPrim(long int z)
{
	long int i, wur;

	wur = sqrt(z) + 1;
	for (i = 3; i <= wur; i += 2)
	{
		if (z % i == 0)
			return i;
	}
	return 0;
}

void CPrimTestDlg::OnTest() 
{
	long int erg;
	char buf[30];

	UpdateData(TRUE);
	m_primtext = "";
	m_teiltext = "";

	if (m_zahl == 1)
		m_primtext = "Keine Primzahl";
	else if (m_zahl == 2)
		m_primtext = "2 ist Primzahl";
	else if (m_zahl % 2 == 0)
	{
		m_primtext = "Keine Primzahl";
		m_teiltext = "Teilbar durch:  2";
	}
	else
	{
		erg = isPrim(m_zahl);
		if (erg != 0)
		{
			m_primtext = "Keine Primzahl";
			wsprintf(buf, "Teilbar durch: %ld", erg);
			m_teiltext = buf;
		}
		else
		{
			wsprintf(buf, "%ld ist Primzahl", m_zahl);
			m_primtext = buf;
		}
	}
	UpdateData(FALSE);
	GetDlgItem(IDC_EDITZAHL)->SetFocus();
	((CEdit*)GetDlgItem(IDC_EDITZAHL))->SetSel(0, -1);
}
