// betrzset.cpp : implementation of the CBetrzlgSet class
//

#include "stdafx.h"
#include "betrzlg.h"
#include "betrzset.h"

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgSet implementation

IMPLEMENT_DYNAMIC(CBetrzlgSet, CRecordset)

CBetrzlgSet::CBetrzlgSet(CDatabase* pdb)
	: CRecordset(pdb)
{
	//{{AFX_FIELD_INIT(CBetrzlgSet)
	m_PCNr = "";
	m_Bank = "";
	m_Inhaber = "";
	m_nFields = 3;
	//}}AFX_FIELD_INIT 
	m_nParams = 1;  
	m_strSuchen = "";
}

CString CBetrzlgSet::GetDefaultConnect()
{
	return "ODBC;DSN=PC Kontos;";
}

CString CBetrzlgSet::GetDefaultSQL()
{
	return "[PC Konto]";
}

void CBetrzlgSet::DoFieldExchange(CFieldExchange* pFX)
{
	//{{AFX_FIELD_MAP(CBetrzlgSet)
	pFX->SetFieldType(CFieldExchange::outputColumn);
	RFX_Text(pFX, "PCNr", m_PCNr);
	RFX_Text(pFX, "Bank", m_Bank);
	RFX_Text(pFX, "Inhaber", m_Inhaber);
	//}}AFX_FIELD_MAP
	pFX->SetFieldType(CFieldExchange::param);
	RFX_Text(pFX, "Suchstring", m_strSuchen);
}
