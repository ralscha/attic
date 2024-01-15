// File2AccessDlg.cpp : implementation file
//

#include "stdafx.h"
#include "MFCRecordSet.h"
#include "Entry.h"
#include "MFCFile.h"
#include "File2Access.h"
#include "File2AccessDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFile2AccessDlg dialog

CFile2AccessDlg::CFile2AccessDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CFile2AccessDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CFile2AccessDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CFile2AccessDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CFile2AccessDlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CFile2AccessDlg, CDialog)
	//{{AFX_MSG_MAP(CFile2AccessDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_START, OnStart)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFile2AccessDlg message handlers

BOOL CFile2AccessDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CFile2AccessDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CFile2AccessDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CFile2AccessDlg::OnStart() 
{
	CWaitCursor wait;
	CEntry entry;
	CMFCFile file("D:\\C++\\MFC.TXT");
	if (file.ReadAll(elist))	
	{
		try
		{
			set.Open();
			
			POSITION	pos = elist.GetHeadPosition();
			while (pos != NULL)
			{
				entry = elist.GetNext(pos);
				set.AddNew();
				entry.FillRecordSet(set);
				set.Update();
		
			}
		}
		catch(CDaoException* e)
		{
			AfxMessageBox(e->m_pErrorInfo->m_strDescription);
			e->Delete();
			return;
		}
	}
}