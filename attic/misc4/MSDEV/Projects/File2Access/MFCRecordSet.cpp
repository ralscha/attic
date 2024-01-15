// MFCRecordSet.cpp : implementation file
//

#include "stdafx.h"
#include "File2Access.h"
#include "MFCRecordSet.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CMFCRecordSet

IMPLEMENT_DYNAMIC(CMFCRecordSet, CDaoRecordset)

CMFCRecordSet::CMFCRecordSet(CDaoDatabase* pdb)
	: CDaoRecordset(pdb)
{
	//{{AFX_FIELD_INIT(CMFCRecordSet)
	m_ID = 0;
	m_From = _T("");
	m_Subject = _T("");
	m_Mail = _T("");
	m_nFields = 5;
	//}}AFX_FIELD_INIT
	m_nDefaultType = dbOpenDynaset;
}


CString CMFCRecordSet::GetDefaultDBName()
{
	return _T("D:\\C++\\mfc.mdb");
}

CString CMFCRecordSet::GetDefaultSQL()
{
	return _T("[MFC]");
}

void CMFCRecordSet::DoFieldExchange(CDaoFieldExchange* pFX)
{
	//{{AFX_FIELD_MAP(CMFCRecordSet)
	pFX->SetFieldType(CDaoFieldExchange::outputColumn);
	DFX_Long(pFX, _T("[ID]"), m_ID);
	DFX_DateTime(pFX, _T("[Date]"), m_Date);
	DFX_Text(pFX, _T("[From]"), m_From);
	DFX_Text(pFX, _T("[Subject]"), m_Subject);
	DFX_Text(pFX, _T("[Mail]"), m_Mail);
	//}}AFX_FIELD_MAP
}

/////////////////////////////////////////////////////////////////////////////
// CMFCRecordSet diagnostics

#ifdef _DEBUG
void CMFCRecordSet::AssertValid() const
{
	CDaoRecordset::AssertValid();
}

void CMFCRecordSet::Dump(CDumpContext& dc) const
{
	CDaoRecordset::Dump(dc);
}
#endif //_DEBUG
