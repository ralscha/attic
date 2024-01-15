// betrzdoc.cpp : implementation of the CBetrzlgDoc class
//

#include "stdafx.h"
#include "betrzlg.h"

#include "betrzset.h"
#include "betrzdoc.h"    
#include "seitedlg.h"
#include "extrasdl.h"
#include "datumdlg.h" 
#include "zahlung.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgDoc

IMPLEMENT_DYNCREATE(CBetrzlgDoc, CDocument)

BEGIN_MESSAGE_MAP(CBetrzlgDoc, CDocument)
	//{{AFX_MSG_MAP(CBetrzlgDoc)
	ON_UPDATE_COMMAND_UI(ID_FILE_SAVE, OnUpdateFileSave)
	ON_UPDATE_COMMAND_UI(ID_FILE_SAVE_AS, OnUpdateFileSaveAs)
	ON_COMMAND(ID_BEARBEITEN_ALLESLSCHEN, OnBearbeitenAlleslschen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_ALLESLSCHEN, OnUpdateBearbeitenAlleslschen)
	ON_UPDATE_COMMAND_UI(ID_DATEI_DRUCKEN_EXTERN, OnUpdateDateiDruckenExtern)
	ON_UPDATE_COMMAND_UI(ID_DATEI_DRUCKEN_INTERN, OnUpdateDateiDruckenIntern)
	ON_UPDATE_COMMAND_UI(ID_DATEI_SEITENANSICHT_EXTERN, OnUpdateDateiSeitenansichtExtern)
	ON_UPDATE_COMMAND_UI(ID_DATEI_SEITENANSICHT_INTERN, OnUpdateDateiSeitenansichtIntern)
	ON_COMMAND(ID_DATEI_SEITEEINRICHTEN, OnDateiSeiteeinrichten)
	ON_COMMAND(ID_EXTRAS_OPTIONEN, OnExtrasOptionen)
	ON_COMMAND(ID_EXTRAS_HISTORY, OnExtrasHistory)
	ON_UPDATE_COMMAND_UI(ID_EXTRAS_HISTORY, OnUpdateExtrasHistory)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgDoc construction/destruction

CBetrzlgDoc::CBetrzlgDoc()
{
	// TODO: add one-time construction code here
}

CBetrzlgDoc::~CBetrzlgDoc()
{
}

BOOL CBetrzlgDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

void CBetrzlgDoc::DeleteContents()
{
	CObject* temp;
	m_auftragsNr = 0;
	
	int upper = m_zahlungArray.GetUpperBound();
	if (upper != -1)
	{
		for (int i = 0; i <= upper; i++)
		{
			temp = m_zahlungArray.GetAt(0);
			m_zahlungArray.RemoveAt(0);
			delete temp;
		}
	}
}
/////////////////////////////////////////////////////////////////////////////
// CBetrzlgDoc serialization

void CBetrzlgDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		ar << m_auftragsNr; // TODO: add storing code here
	}
	else
	{
		ar >> m_auftragsNr; // TODO: add loading code here
	}        
	m_zahlungArray.Serialize(ar);
}

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgDoc diagnostics

#ifdef _DEBUG
void CBetrzlgDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CBetrzlgDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgDoc commands

void CBetrzlgDoc::OnUpdateFileSave(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(IsModified());
}

void CBetrzlgDoc::OnUpdateFileSaveAs(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(IsModified());
}

void CBetrzlgDoc::OnBearbeitenAlleslschen()
{  
	SetModifiedFlag(FALSE);
	OnNewDocument();    
	UpdateAllViews(NULL);
}

void CBetrzlgDoc::OnUpdateBearbeitenAlleslschen(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);		
}

void CBetrzlgDoc::OnUpdateDateiDruckenExtern(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);	
}

void CBetrzlgDoc::OnUpdateDateiDruckenIntern(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);	
}

void CBetrzlgDoc::OnUpdateDateiSeitenansichtExtern(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);	
}

void CBetrzlgDoc::OnUpdateDateiSeitenansichtIntern(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);
}

void CBetrzlgDoc::OnDateiSeiteeinrichten()
{
	CSeiteDlg sdlg;
	if (sdlg.DoModal() == IDOK)
	{
		AfxGetApp()->WriteProfileString("Seitenraender", "Oben", sdlg.m_oben);
		AfxGetApp()->WriteProfileString("Seitenraender", "Unten", sdlg.m_unten);
		AfxGetApp()->WriteProfileString("Seitenraender", "Links", sdlg.m_links);
		AfxGetApp()->WriteProfileString("Seitenraender", "Rechts", sdlg.m_rechts);
	}
}

void CBetrzlgDoc::OnExtrasOptionen()
{
	CExtrasDlg xdlg;
	if (xdlg.DoModal() == IDOK)
	{
		AfxGetApp()->WriteProfileString("Kontoinhaber", "Zeile1", xdlg.m_z1);
      AfxGetApp()->WriteProfileString("Kontoinhaber", "Zeile2", xdlg.m_z2);
      AfxGetApp()->WriteProfileString("Kontoinhaber", "Zeile3", xdlg.m_z3);
      AfxGetApp()->WriteProfileString("Kontoinhaber", "Zeile4", xdlg.m_z4); 
      AfxGetApp()->WriteProfileString("Settings", "History", xdlg.m_histname);
	}
}
               
void CBetrzlgDoc::OnExtrasHistory()
{   
	CDatumDlg ddlg;
	CZahlung* zlg;
	int upper = m_zahlungArray.GetUpperBound();
			
	if (upper != -1)
	{
		if (ddlg.DoModal() == IDOK)
		{
			CFile file;
         TRY        
         {
           	CString name = AfxGetApp()->GetProfileString("Settings", "History", "C:\HISTORY.TXT");
           	int ret;
           	if(file.Open(name, CFile::modeReadWrite))
           	{
           		ret = AfxGetApp()->m_pMainWnd->MessageBox("Historydatei existiert bereits\nSoll die Datei überschrieben werden?\n\nJA = Überschreiben / NEIN = Anhängen", "Datei existiert", MB_YESNOCANCEL|MB_ICONQUESTION);
           		if (ret == IDCANCEL)
           			return;
           	
           	  	if (ret == IDYES)
           	   {
           			file.Close();
           			file.Open(name, CFile::modeWrite|CFile::modeCreate);
           		}                                                      
           		else if (ret == IDNO)
           			file.SeekToEnd();
           	}
           	else
           		file.Open(name, CFile::modeWrite|CFile::modeCreate);
           		       
            char buffer[256];
				CString buf;
				for (int i = 0; i <= upper; i++)
				{
					zlg = (CZahlung*)m_zahlungArray[i];
					wsprintf(buffer, ";%d;", m_auftragsNr);
					file.Write(buffer, strlen(buffer));
					wsprintf(buffer, "%d.%d.%d;", ddlg.m_tag, ddlg.m_monat, ddlg.m_jahr);
					file.Write(buffer, strlen(buffer));
					buf.Empty();
					buf += zlg->GetKtoNr() + ";" + zlg->GetBank() + ";" + zlg->GetInhaber() + ";";                      
					file.Write(buf, buf.GetLength());
					sprintf(buffer, "%.2f;", zlg->GetBetrag() / 100.00);
					file.Write(buffer, strlen(buffer));
							
					if (zlg->GetUNr(0) == "gemäss Beilage")
						file.Write("BEILAGE;;;;;\n", 13);
					else
					{                                     
						buf.Empty();
						buf += zlg->GetUNr(0) + ";" + zlg->GetUNr(1) + ";" +
					   	    zlg->GetUNr(2) + ";" + zlg->GetUNr(3) + ";" +
					   		 zlg->GetUNr(4) + ";" + zlg->GetUNr(5) + "\n";
						file.Write(buf, buf.GetLength());
					}
				}
         }
			CATCH(CException, e)
         {
         	AfxMessageBox("Fehler beim Schreiben der Datei");
         }
			END_CATCH
		}
	}	
}            	
					
void CBetrzlgDoc::OnUpdateExtrasHistory(CCmdUI* pCmdUI)
{
	pCmdUI->Enable(m_zahlungArray.GetUpperBound() != -1);	
}
