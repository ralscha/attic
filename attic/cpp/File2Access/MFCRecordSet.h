// MFCRecordSet.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CMFCRecordSet DAO recordset

class CMFCRecordSet : public CDaoRecordset
{
public:
	CMFCRecordSet(CDaoDatabase* pDatabase = NULL);
	DECLARE_DYNAMIC(CMFCRecordSet)

// Field/Param Data
	//{{AFX_FIELD(CMFCRecordSet, CDaoRecordset)
	long	m_ID;
	COleDateTime	m_Date;
	CString	m_From;
	CString	m_Subject;
	CString	m_Mail;
	//}}AFX_FIELD

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMFCRecordSet)
	public:
	virtual CString GetDefaultDBName();		// Default database name
	virtual CString GetDefaultSQL();		// Default SQL for Recordset
	virtual void DoFieldExchange(CDaoFieldExchange* pFX);  // RFX support
	//}}AFX_VIRTUAL

// Implementation
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif
};
