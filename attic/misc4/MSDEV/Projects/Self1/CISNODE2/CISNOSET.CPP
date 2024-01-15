// cisnoset.cpp : implementation of the CCisnodesSet class
//

#include "stdafx.h"
#include "cisnodes.h"
#include "cisnoset.h"

/////////////////////////////////////////////////////////////////////////////
// CCisnodesSet implementation

IMPLEMENT_DYNAMIC(CCisnodesSet, CRecordset)

CCisnodesSet::CCisnodesSet(CDatabase* pdb)
	: CRecordset(pdb)
{
	//{{AFX_FIELD_INIT(CCisnodesSet)
	m_ABK = "";
	m_ORT = "";
	m_nFields = 2;
	//}}AFX_FIELD_INIT  
	m_nParams = 1;
	m_strSuchen = "";
}

CString CCisnodesSet::GetDefaultConnect()
{
	return "ODBC;DSN=CIS NODES;";
}

CString CCisnodesSet::GetDefaultSQL()
{
	return "CISNODES";
}

void CCisnodesSet::DoFieldExchange(CFieldExchange* pFX)
{
	//{{AFX_FIELD_MAP(CCisnodesSet)
	pFX->SetFieldType(CFieldExchange::outputColumn);
	RFX_Text(pFX, "ABK", m_ABK);
	RFX_Text(pFX, "ORT", m_ORT);
	//}}AFX_FIELD_MAP
	pFX->SetFieldType(CFieldExchange::param);
	RFX_Text(pFX, "Suchen", m_strSuchen);
}
