// betrzdoc.h : interface of the CBetrzlgDoc class
//
/////////////////////////////////////////////////////////////////////////////

class CBetrzlgDoc : public CDocument
{
protected: // create from serialization only
	CBetrzlgDoc();
	DECLARE_DYNCREATE(CBetrzlgDoc)

// Attributes    
public:
	CBetrzlgSet m_betrzlgSet;
   CObArray m_zahlungArray;

private:
	WORD m_auftragsNr;
	
// Operations
public:
   CObArray* GetArray() { return &m_zahlungArray; }
   int GetAuftragsNr() { return m_auftragsNr; }
   void SetAuftragsNr(int nr) { m_auftragsNr = nr; }
   
// Implementation
public:
	virtual ~CBetrzlgDoc();
	virtual void Serialize(CArchive& ar);   // overridden for document i/o
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	virtual BOOL OnNewDocument();
   virtual void DeleteContents();
   
// Generated message map functions
protected:
	//{{AFX_MSG(CBetrzlgDoc)
	afx_msg void OnUpdateFileSave(CCmdUI* pCmdUI);
	afx_msg void OnUpdateFileSaveAs(CCmdUI* pCmdUI);
	afx_msg void OnBearbeitenAlleslschen();
	afx_msg void OnUpdateBearbeitenAlleslschen(CCmdUI* pCmdUI);
	afx_msg void OnUpdateDateiDruckenExtern(CCmdUI* pCmdUI);
	afx_msg void OnUpdateDateiDruckenIntern(CCmdUI* pCmdUI);
	afx_msg void OnUpdateDateiSeitenansichtExtern(CCmdUI* pCmdUI);
	afx_msg void OnUpdateDateiSeitenansichtIntern(CCmdUI* pCmdUI);
	afx_msg void OnDateiSeiteeinrichten();
	afx_msg void OnExtrasOptionen();
	afx_msg void OnExtrasHistory();
	afx_msg void OnUpdateExtrasHistory(CCmdUI* pCmdUI);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////
