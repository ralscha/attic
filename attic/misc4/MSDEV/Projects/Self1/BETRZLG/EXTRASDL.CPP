// extrasdl.cpp : implementation file
//

#include "stdafx.h"
#include "betrzlg.h"
#include "extrasdl.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CExtrasDlg dialog


CExtrasDlg::CExtrasDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CExtrasDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CExtrasDlg)
	m_z1 = "";
	m_z2 = "";
	m_z3 = "";
	m_z4 = "";
	m_histname = "";
	//}}AFX_DATA_INIT
}

void CExtrasDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CExtrasDlg)
	DDX_Text(pDX, IDC_ZEILE1, m_z1);
	DDX_Text(pDX, IDC_ZEILE2, m_z2);
	DDX_Text(pDX, IDC_ZEILE3, m_z3);
	DDX_Text(pDX, IDC_ZEILE4, m_z4);
	DDX_Text(pDX, IDC_HISTNAME, m_histname);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CExtrasDlg, CDialog)
	//{{AFX_MSG_MAP(CExtrasDlg)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CExtrasDlg message handlers

BOOL CExtrasDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	
	m_z1 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile1", "30-4541-9");
   m_z2 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile2", "Eidg. Steuerverwaltung");
   m_z3 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile3", "Betreibungskostenvorschüsse");
   m_z4 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile4", "Bern");  
   m_histname = AfxGetApp()->GetProfileString("Settings", "History", "C:\HISTORY.TXT");
   UpdateData(FALSE);
	return TRUE;  // return TRUE  unless you set the focus to a control
}
