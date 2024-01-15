#ifndef __ssdialog_h
#define __ssdialog_h

// ssdialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CSSDialog dialog

class CSSDialog : public CDialog
{

private:
	int ssmensch;
	int sscomputer;
	
// Construction
public:
	CSSDialog(CWnd* pParent = NULL);	// standard constructor
   void SetSpielstand(int m, int c);
// Dialog Data
	//{{AFX_DATA(CSSDialog)
	enum { IDD = IDD_SPIELSTAND };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CSSDialog)
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#endif
