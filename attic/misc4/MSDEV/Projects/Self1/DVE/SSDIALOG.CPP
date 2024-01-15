// ssdialog.cpp : implementation file
//

#include "stdafx.h"
#include "DvE.h"
#include "ssdialog.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSSDialog dialog


CSSDialog::CSSDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CSSDialog::IDD, pParent)
{
	ssmensch = 0;
	sscomputer = 0;
	//{{AFX_DATA_INIT(CSSDialog)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}

void CSSDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CSSDialog)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CSSDialog, CDialog)
	//{{AFX_MSG_MAP(CSSDialog)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CSSDialog message handlers

BOOL CSSDialog::OnInitDialog()
{
	CDialog::OnInitDialog();
	char buffer[6];

	wsprintf(buffer, "%4d", ssmensch);	
	SetDlgItemText(IDC_SSHUMA, buffer);
	wsprintf(buffer, "%4d", sscomputer);
	SetDlgItemText(IDC_SSCOMP, buffer);
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CSSDialog::SetSpielstand(int m, int c)
{
	ssmensch = m;
	sscomputer = c;
}