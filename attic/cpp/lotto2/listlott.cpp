// listlott.cpp : implementation file
//

#include "stdafx.h"
#include "lotto2.h"
#include "listlott.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// ListLotto dialog


ListLotto::ListLotto(CWnd* pParent /*=NULL*/)
	: CDialog(ListLotto::IDD, pParent)
{
	//{{AFX_DATA_INIT(ListLotto)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void ListLotto::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(ListLotto)
	DDX_Control(pDX, IDC_LISTLZ, m_listlz);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(ListLotto, CDialog)
	//{{AFX_MSG_MAP(ListLotto)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// ListLotto message handlers

BOOL ListLotto::OnInitDialog() 
{
	CDialog::OnInitDialog();
	 
		
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}
