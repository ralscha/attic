#ifndef __stdialog_h
#define __stdialog_h

// stdialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CSTDialog dialog

class CSTDialog : public CDialog
{
private:
	int stat[16];

// Construction
public:
	CSTDialog(CWnd* pParent = NULL);	// standard constructor
   void SetStrategie(int* statistik);
   
// Dialog Data
	//{{AFX_DATA(CSTDialog)
	enum { IDD = IDD_STRATEGIE };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CSTDialog)
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#endif
