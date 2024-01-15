// betrzset.h : interface of the CBetrzlgSet class
//
/////////////////////////////////////////////////////////////////////////////

class CBetrzlgSet : public CRecordset
{
DECLARE_DYNAMIC(CBetrzlgSet)

public:
	CBetrzlgSet(CDatabase* pDatabase = NULL);

// Field/Param Data
	//{{AFX_FIELD(CBetrzlgSet, CRecordset)
	CString	m_PCNr;
	CString	m_Bank;
	CString	m_Inhaber;
	//}}AFX_FIELD  
	CString  m_strSuchen;	

// Implementation
protected:
	virtual CString GetDefaultConnect();	// Default connection string
	virtual CString GetDefaultSQL(); 	// default SQL for Recordset
	virtual void DoFieldExchange(CFieldExchange* pFX);	// RFX support
};
