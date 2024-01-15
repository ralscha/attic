// stdialog.cpp : implementation file
//

#include "stdafx.h"
#include "DvE.h"
#include "stdialog.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CSTDialog dialog


CSTDialog::CSTDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CSTDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CSTDialog)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}

void CSTDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CSTDialog)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CSTDialog, CDialog)
	//{{AFX_MSG_MAP(CSTDialog)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CSTDialog message handlers

BOOL CSTDialog::OnInitDialog()
{
	CDialog::OnInitDialog();
	
	char buffer[6];
	
	for (int i = 0; i < 16; i++)
	{
		wsprintf(buffer, "%3d", stat[i]);
		SetDlgItemText(S + i, buffer);
	}
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CSTDialog::SetStrategie(int* statistik)
{
	for (int i = 0; i < 16; i++)
		stat[i] = *statistik++;
	
}