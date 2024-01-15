#ifndef __zieldial_h
#define __zieldial_h

// zieldial.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CZielDialog dialog

class CZielDialog : public CDialog
{
private:
	int ziel;                        
	enum {nMin = 100};
	enum {nMax = 1000};
	
// Construction
public:
	CZielDialog(CWnd* pParent = NULL);	// standard constructor
   void SetZiel(int z) { ziel = z; }
   int GetZiel() { return ziel; }
// Dialog Data
	//{{AFX_DATA(CZielDialog)
	enum { IDD = IDD_ZIELDIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

// Implementation
protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support

	// Generated message map functions
	//{{AFX_MSG(CZielDialog)
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#endif
