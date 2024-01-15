// datumdlg.cpp : implementation file
//

#include "stdafx.h"
#include "betrzlg.h"
#include "datumdlg.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CDatumDlg dialog


CDatumDlg::CDatumDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CDatumDlg::IDD, pParent)
{
	CTime t = CTime::GetCurrentTime();
	//{{AFX_DATA_INIT(CDatumDlg)
	m_jahr = t.GetYear();
	m_monat = t.GetMonth();
	m_tag = t.GetDay();
	//}}AFX_DATA_INIT
}

void CDatumDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CDatumDlg)
	DDX_Text(pDX, IDC_JAHR, m_jahr);
	DDX_Text(pDX, IDC_MONAT, m_monat);
	DDX_Text(pDX, IDC_TAG, m_tag);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CDatumDlg, CDialog)
	//{{AFX_MSG_MAP(CDatumDlg)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CDatumDlg message handlers
