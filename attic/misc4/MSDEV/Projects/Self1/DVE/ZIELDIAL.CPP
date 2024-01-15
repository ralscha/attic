// zieldial.cpp : implementation file
//

#include "stdafx.h"
#include "DvE.h"
#include "zieldial.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CZielDialog dialog


CZielDialog::CZielDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CZielDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZielDialog)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}

void CZielDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZielDialog)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CZielDialog, CDialog)
	//{{AFX_MSG_MAP(CZielDialog)
	ON_WM_HSCROLL()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// CZielDialog message handlers

BOOL CZielDialog::OnInitDialog()
{
	CDialog::OnInitDialog();
	
	char buffer[6];
	wsprintf(buffer, "%4d", ziel);
	SetDlgItemText(IDC_ZIELPUNKTE, buffer);
		
	CScrollBar* pSB = (CScrollBar*)GetDlgItem(IDC_SCROLLZIEL);
	pSB->SetScrollRange(nMin, nMax);
	pSB->SetScrollPos(ziel);
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CZielDialog::OnOK()
{
	CScrollBar* pSB = (CScrollBar*)GetDlgItem(IDC_SCROLLZIEL);
	ziel = pSB->GetScrollPos();
	
	CDialog::OnOK();
}

void CZielDialog::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	char buffer[6];
	static int hPos = nMin;
		
	switch(nSBCode)
	{
		case SB_THUMBPOSITION:
			hPos = nPos; 
			if (hPos % 10 <= 4)
				hPos -= (hPos % 10);
			else
				hPos += (10-(hPos % 10)); 
			break;                            
		case SB_LINELEFT:
			hPos -= 10;
			hPos = hPos < nMin ? nMin : hPos;
			break;
		case SB_LINERIGHT:
			hPos += 10;
			hPos = hPos > nMax ? nMax : hPos;				
			break;
		case SB_PAGELEFT:
			hPos -= 100;
			hPos = hPos < nMin ? nMin : hPos;
			break;
		case SB_PAGERIGHT:
			hPos += 100;
			hPos = hPos > nMax ? nMax : hPos;				
			break;		
		case SB_LEFT:
			hPos = nMin;
			break;
		case SB_RIGHT:
			hPos = nMax;
			break;
		case SB_THUMBTRACK:
			hPos = nPos; 
			if (hPos % 10 <= 4)
				hPos -= (hPos % 10);
			else
				hPos += (10-(hPos % 10)); 
			break;            
	}     
	pScrollBar->SetScrollPos(hPos);
	wsprintf(buffer, "%4d", hPos);
	SetDlgItemText(IDC_ZIELPUNKTE, buffer);
}
