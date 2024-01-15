// Kruemvw.h : interface of the CKruemelView class
//
/////////////////////////////////////////////////////////////////////////////

class CKruemelView : public CView
{
protected: // create from serialization only
	CKruemelView();
	DECLARE_DYNCREATE(CKruemelView)

// Attributes
public:
	CKruemelDoc* GetDocument();
	BOOL running;

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CKruemelView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual void OnInitialUpdate();
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CKruemelView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CKruemelView)
	afx_msg void OnStart();
	afx_msg void OnStop();
	afx_msg void OnUpdateStart(CCmdUI* pCmdUI);
	afx_msg void OnUpdateStop(CCmdUI* pCmdUI);
	afx_msg void OnUpdateAppAbout(CCmdUI* pCmdUI);
	//}}AFX_MSG
	void Step();
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in Kruemvw.cpp
inline CKruemelDoc* CKruemelView::GetDocument()
   { return (CKruemelDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////
