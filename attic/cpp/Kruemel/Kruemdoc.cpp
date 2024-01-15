// Kruemdoc.cpp : implementation of the CKruemelDoc class
//

#include "stdafx.h"
#include "Kruemel.h"

#include "Kruemdoc.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CKruemelDoc

IMPLEMENT_DYNCREATE(CKruemelDoc, CDocument)

BEGIN_MESSAGE_MAP(CKruemelDoc, CDocument)
	//{{AFX_MSG_MAP(CKruemelDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CKruemelDoc construction/destruction

CKruemelDoc::CKruemelDoc()
{
	// TODO: add one-time construction code here

}

CKruemelDoc::~CKruemelDoc()
{
}

BOOL CKruemelDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)
	SetTitle("Kruemel");
	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CKruemelDoc serialization

void CKruemelDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

/////////////////////////////////////////////////////////////////////////////
// CKruemelDoc diagnostics

#ifdef _DEBUG
void CKruemelDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CKruemelDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CKruemelDoc commands
