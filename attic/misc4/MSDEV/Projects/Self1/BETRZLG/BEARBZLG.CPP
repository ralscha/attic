// bearbzlg.cpp : implementation file
//

#include "stdafx.h"
#include "betrzlg.h"
#include "bearbzlg.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CBearbZlg dialog


CBearbZlg::CBearbZlg(CWnd* pParent /*=NULL*/)
	: CDialog(CBearbZlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CBearbZlg)
	m_bank = "";
	m_betrag = 0;
	m_inhaber = "";
	m_pcnr = "";
	m_unr1 = "";
	m_unr2 = "";
	m_unr3 = "";
	m_unr4 = "";
	m_unr5 = "";
	m_unr6 = "";
	//}}AFX_DATA_INIT
}

void CBearbZlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBearbZlg)
	DDX_Text(pDX, IDC_BANK, m_bank);
	DDX_Text(pDX, IDC_BETRAG, m_betrag);
	DDV_MinMaxDouble(pDX, m_betrag, 0., 99999.);
	DDX_Text(pDX, IDC_INHABER, m_inhaber);
	DDX_Text(pDX, IDC_PCNR, m_pcnr);
	DDX_Text(pDX, IDC_UNR1, m_unr1);
	DDV_MaxChars(pDX, m_unr1, 7);
	DDX_Text(pDX, IDC_UNR2, m_unr2);
	DDV_MaxChars(pDX, m_unr2, 7);
	DDX_Text(pDX, IDC_UNR3, m_unr3);
	DDV_MaxChars(pDX, m_unr3, 7);
	DDX_Text(pDX, IDC_UNR4, m_unr4);
	DDV_MaxChars(pDX, m_unr4, 7);
	DDX_Text(pDX, IDC_UNR5, m_unr5);
	DDV_MaxChars(pDX, m_unr5, 7);
	DDX_Text(pDX, IDC_UNR6, m_unr6);
	DDV_MaxChars(pDX, m_unr6, 7);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CBearbZlg, CDialog)
	//{{AFX_MSG_MAP(CBearbZlg)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CBearbZlg message handlers
