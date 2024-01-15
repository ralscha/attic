// CreateHTMLDialog.cpp : implementation file
//

#include "stdafx.h"
#include "Lotto.h"
#include "CreateHTMLDialog.h"
#include "lottocla.h"
#include "LottoHTML.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCreateHTMLDialog dialog


CCreateHTMLDialog::CCreateHTMLDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CCreateHTMLDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CCreateHTMLDialog)
	m_day = 0;
	m_month = 0;
	m_year = 0;
	//}}AFX_DATA_INIT
	CTime t =  CTime::GetCurrentTime();
	m_day = t.GetDay() - (t.GetDayOfWeek() % 7);
	m_year = t.GetYear();
	m_month = t.GetMonth();
}


void CCreateHTMLDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CCreateHTMLDialog)
	DDX_Text(pDX, IDC_DAY, m_day);
	DDV_MinMaxInt(pDX, m_day, 1, 31);
	DDX_Text(pDX, IDC_MONTH, m_month);
	DDV_MinMaxInt(pDX, m_month, 1, 12);
	DDX_Text(pDX, IDC_YEAR, m_year);
	DDV_MinMaxInt(pDX, m_year, 1996, 2100);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CCreateHTMLDialog, CDialog)
	//{{AFX_MSG_MAP(CCreateHTMLDialog)
	ON_BN_CLICKED(IDC_CREATE, OnCreateHTML)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCreateHTMLDialog message handlers

void CCreateHTMLDialog::OnCreateHTML() 
{
	CLottoHTML lottohtml;
	UpdateData(TRUE);
	
	if (m_day != 0 && m_month != 0 && m_year != 0)
	{
		CTime t(m_year, m_month, m_day, 0, 0, 0);
		CWaitCursor wait;
		lottohtml.CreatePages("c:\\temp", t);
		//lottohtml.CreatePages("d:\\internet\\My Pages bei Compuserve", t);	
		wait.Restore();
	}
	OnOK();
}


