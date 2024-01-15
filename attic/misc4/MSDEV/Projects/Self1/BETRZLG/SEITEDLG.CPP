// seitedlg.cpp : implementation file
//

#include "stdafx.h"
#include "betrzlg.h"
#include "seitedlg.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSeiteDlg dialog


CSeiteDlg::CSeiteDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CSeiteDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CSeiteDlg)
	m_links = "";
	m_oben = "";
	m_rechts = "";
	m_unten = "";
	//}}AFX_DATA_INIT
}

void CSeiteDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CSeiteDlg)
	DDX_Text(pDX, IDC_LRAND, m_links);
	DDX_Text(pDX, IDC_ORAND, m_oben);
	DDX_Text(pDX, IDC_RRAND, m_rechts);
	DDX_Text(pDX, IDC_URAND, m_unten);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CSeiteDlg, CDialog)
	//{{AFX_MSG_MAP(CSeiteDlg)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CSeiteDlg message handlers

BOOL CSeiteDlg::OnInitDialog()
{
	CDialog::OnInitDialog();     
	
	m_oben = AfxGetApp()->GetProfileString("Seitenraender", "Oben", "0.00");
	m_unten = AfxGetApp()->GetProfileString("Seitenraender", "Unten", "0.00");
	m_links = AfxGetApp()->GetProfileString("Seitenraender", "Links", "0.00");
	m_rechts = AfxGetApp()->GetProfileString("Seitenraender", "Rechts", "0.00");
	UpdateData(FALSE);
	return TRUE;
}

