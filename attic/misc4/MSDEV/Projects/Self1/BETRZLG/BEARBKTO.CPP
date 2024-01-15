// BearbKonto.cpp : implementation file
//

#include "stdafx.h"
#include "betrzlg.h"
#include "bearbkto.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CBearbKonto dialog


CBearbKonto::CBearbKonto(CWnd* pParent /*=NULL*/)
	: CDialog(CBearbKonto::IDD, pParent)
{
	//{{AFX_DATA_INIT(CBearbKonto)
	m_bank = "";
	m_inhaber = "";
	m_pcnr = "";
	//}}AFX_DATA_INIT
}

void CBearbKonto::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBearbKonto)
	DDX_Text(pDX, IDC_BANK, m_bank);
	DDV_MaxChars(pDX, m_bank, 60);
	DDX_Text(pDX, IDC_INHABER, m_inhaber);
	DDV_MaxChars(pDX, m_inhaber, 60);
	DDX_Text(pDX, IDC_PCNR, m_pcnr);
	DDV_MaxChars(pDX, m_pcnr, 11);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CBearbKonto, CDialog)
	//{{AFX_MSG_MAP(CBearbKonto)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CBearbKonto message handlers
