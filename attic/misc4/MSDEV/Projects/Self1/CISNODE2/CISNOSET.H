#ifndef __CISNOSET_H
#define __CISNOSET_H

// cisnoset.h : interface of the CCisnodesSet class
//
/////////////////////////////////////////////////////////////////////////////

class CCisnodesSet : public CRecordset
{
DECLARE_DYNAMIC(CCisnodesSet)

public:
	CCisnodesSet(CDatabase* pDatabase = NULL);

// Field/Param Data
	//{{AFX_FIELD(CCisnodesSet, CRecordset)
	CString	m_ABK;
	CString	m_ORT;
	//}}AFX_FIELD
   CString  m_strSuchen;
// Implementation
protected:
	virtual CString GetDefaultConnect();	// Default connection string
	virtual CString GetDefaultSQL(); 	// default SQL for Recordset
	virtual void DoFieldExchange(CFieldExchange* pFX);	// RFX support
};

#endif
