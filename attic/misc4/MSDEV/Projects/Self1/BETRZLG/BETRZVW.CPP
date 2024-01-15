// betrzvw.cpp : implementation of the CBetrzlgView class
//

#include "stdafx.h"
#include "betrzlg.h"
#include "zahlung.h"
#include "betrzset.h"
#include "betrzdoc.h"
#include "betrzvw.h"
#include "bearbkto.h" 
#include "bearbzlg.h" 
#include <ctl3d.h>

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif


//für Ausdruck
const char* ktoinh = "KONTOINHABER";
const	char* liste  = "Liste zum Zahlungsauftrag Nr. %d";
const	char* ueberschrift1 = "Konto\tDer Betrag ist gutzuschreiben auf - anzuweisen an\t";
const char* ueberschrift2 = "Fr. Rp";
const char* uebertrag = "\tÜbertrag";
const char* total = "\tTOTAL";

const char *MonatsNamen[12] =
	 { "Januar","Februar","März","April","Mai","Juni",
		"Juli","August","September","Oktober","November","Dezember" };

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView

IMPLEMENT_DYNCREATE(CBetrzlgView, CFormView)

BEGIN_MESSAGE_MAP(CBetrzlgView, CFormView)
	//{{AFX_MSG_MAP(CBetrzlgView)
	ON_EN_KILLFOCUS(IDC_PCNR, OnKillfocusPcnr)
	ON_BN_CLICKED(IDC_ADD, OnAdd)
	ON_EN_KILLFOCUS(IDC_AUFTRAGSNR, OnKillfocusAuftragsnr)
	ON_COMMAND(ID_BEARBEITEN_LSCHEN, OnBearbeitenLschen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_LSCHEN, OnUpdateBearbeitenLschen)
	ON_LBN_DBLCLK(IDC_LISTBOX, OnDblclkListbox)
	ON_COMMAND(ID_BEARBEITEN_WIEDERHOLEN, OnBearbeitenWiederholen)
	ON_UPDATE_COMMAND_UI(ID_BEARBEITEN_WIEDERHOLEN, OnUpdateBearbeitenWiederholen)
	ON_COMMAND(ID_DATEI_DRUCKEN_EXTERN, OnDateiDruckenExtern)
	ON_COMMAND(ID_DATEI_DRUCKEN_INTERN, OnDateiDruckenIntern)
	ON_COMMAND(ID_DATEI_SEITENANSICHT_EXTERN, OnDateiSeitenansichtExtern)
	ON_COMMAND(ID_DATEI_SEITENANSICHT_INTERN, OnDateiSeitenansichtIntern)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView construction/destruction

CBetrzlgView::CBetrzlgView()
	: CFormView(CBetrzlgView::IDD)
{
	//{{AFX_DATA_INIT(CBetrzlgView)
	m_pSet = NULL;
	m_unr1 = "";
	m_unr2 = "";
	m_unr3 = "";
	m_unr4 = "";
	m_unr5 = "";
	m_unr6 = "";
	m_auftragsnr = 0;
	m_anzahl = 0;
	m_inhaber = "";
	m_betrag = 0;
	m_totalstr = "";
	//}}AFX_DATA_INIT 
	m_total = 0.0;
	ptt = FALSE;
}

CBetrzlgView::~CBetrzlgView()
{
}

void CBetrzlgView::DoDataExchange(CDataExchange* pDX)
{
	CFormView::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBetrzlgView)
	DDX_Control(pDX, IDC_INHABER, m_ctrlInhaber);
	DDX_Control(pDX, IDC_LISTBOX, m_listBox);
	DDX_Text(pDX, IDC_UNR1, m_unr1);
	DDV_MaxChars(pDX, m_unr1, 7);
	DDX_Text(pDX, IDC_UNR2, m_unr2);
	DDV_MaxChars(pDX, m_unr2, 7);
	DDX_Text(pDX, IDC_UNR3, m_unr3);
	DDV_MaxChars(pDX, m_unr3, 7);
	DDX_Text(pDX, IDC_UNR4, m_unr4);
	DDV_MaxChars(pDX, m_unr4, 7);
	DDX_Text(pDX, IDC_UNR5, m_unr5);
	DDV_MaxChars(pDX, m_unr5, 7);
	DDX_Text(pDX, IDC_UNR6, m_unr6);
	DDV_MaxChars(pDX, m_unr6, 7);
	DDX_Text(pDX, IDC_AUFTRAGSNR, m_auftragsnr);
	DDV_MinMaxInt(pDX, m_auftragsnr, 0, 999);
	DDX_Text(pDX, IDC_ANZAHL, m_anzahl);
	DDX_LBString(pDX, IDC_INHABER, m_inhaber);
	DDX_Text(pDX, IDC_BETRAG, m_betrag);
	DDX_FieldText(pDX, IDC_BANK, m_pSet->m_Bank, m_pSet);
	DDX_Text(pDX, IDC_TOTAL, m_totalstr);
	//}}AFX_DATA_MAP
	DDX_Text(pDX, IDC_PCNR, m_pSet->m_strSuchen);
}

void CBetrzlgView::OnInitialUpdate()
{
	m_pSet = &GetDocument()->m_betrzlgSet;  
	m_pSet->m_strFilter = "PCNr = ?";
	
	ASSERT(m_pSet != NULL);
	if (!m_pSet->IsOpen())
   {
   	BeginWaitCursor();
      TRY
      {
      	m_pSet->Open();
      }
      CATCH(CDBException, e)
      {
      	AfxMessageBox(e->m_strError, MB_ICONEXCLAMATION);
         THROW_LAST();
      }
      AND_CATCH_ALL(e)
      {
      	THROW_LAST();
      }
      END_CATCH_ALL
      EndWaitCursor();
   }
    
	CFormView::OnInitialUpdate();
	Ctl3dSubclassDlg(m_hWnd, CTL3D_ALL);
	GetParentFrame()->RecalcLayout();
	ResizeParentToFit(FALSE);
	  
	int tab[6];        
	tab[0] = 60; tab[1] = 96; tab[2] = 132;
	tab[3] = 168; tab[4] = 204; tab[5] = 240;
	m_listBox.SetTabStops(6, tab);      
}
      
void CBetrzlgView::OnUpdate(CView* pSender, LPARAM lHint, CObject* pHint)
{
	Array2ListBox();
	m_auftragsnr = GetDocument()->GetAuftragsNr();
	UpdateData(FALSE);
}


/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView printing

BOOL CBetrzlgView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CBetrzlgView::OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo)
{   
	
	pen3 = new CPen(PS_SOLID, 3, RGB(0, 0, 0));
	pen2 = new CPen(PS_SOLID, 2, RGB(0, 0, 0));
	
	int cxmm = pDC->GetDeviceCaps(HORZSIZE); // millimeter
	int cxpx = pDC->GetDeviceCaps(HORZRES); // pixel    
	int cypx = pDC->GetDeviceCaps(VERTRES);
	
	pixelpermm = cxpx / cxmm;

	CString buffer = AfxGetApp()->GetProfileString("Seitenraender", "Oben", "0.00");
	orand = atof(buffer) * 10.0 * pixelpermm;
	buffer = AfxGetApp()->GetProfileString("Seitenraender", "Unten", "0.00");
	urand = atof(buffer) * 10.0 * pixelpermm;
	buffer = AfxGetApp()->GetProfileString("Seitenraender", "Links", "0.00");
	lrand = atof(buffer) * 10.0 * pixelpermm;
	buffer = AfxGetApp()->GetProfileString("Seitenraender", "Rechts", "0.00");
	rrand = atof(buffer) * 10.0 * pixelpermm;
	
	lrand2 = lrand + (2 * pixelpermm);
	rrand2 = rrand + (2 * pixelpermm);

	// PC-Nr, Inhaber, Betrag und Adresse
	int h = -MulDiv(pDC->GetDeviceCaps(LOGPIXELSY), abs(-11), 72);
	font1 = new CFont();
	font1->CreateFont(h, 0, 0, 0, FW_NORMAL, FALSE, FALSE, FALSE, DEFAULT_CHARSET,
							OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY,
							DEFAULT_PITCH|FF_DONTCARE, "Arial");

	// U-Nummern
	h = -MulDiv(pDC->GetDeviceCaps(LOGPIXELSY), abs(-10), 72);
	font2 = new CFont();
	font2->CreateFont(h, 0, 0, 0, FW_NORMAL, FALSE, FALSE, FALSE, DEFAULT_CHARSET,
							OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY,
							DEFAULT_PITCH|FF_DONTCARE, "Arial");

	// Text 'KONTOINHABER' und Überschriften
	h = -MulDiv(pDC->GetDeviceCaps(LOGPIXELSY), abs(-8), 72);
	font3 = new CFont();
	font3->CreateFont(h, 0, 0, 0, FW_NORMAL, FALSE, FALSE, FALSE, DEFAULT_CHARSET,
							OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY,
							DEFAULT_PITCH|FF_DONTCARE, "Arial");

	// Übertrag, Total und Datum
	h = -MulDiv(pDC->GetDeviceCaps(LOGPIXELSY), abs(-12), 72);
	font4 = new CFont();
	font4->CreateFont(h, 0, 0, 0, FW_BOLD, FALSE, FALSE, FALSE, DEFAULT_CHARSET,
							OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY,
							DEFAULT_PITCH|FF_DONTCARE, "Arial");

	// ZaNr und SeitenNr
	h = -MulDiv(pDC->GetDeviceCaps(LOGPIXELSY), abs(-12), 72);
	font5 = new CFont();
	font5->CreateFont(h, 0, 0, 0, FW_BOLD, FALSE, FALSE, FALSE, DEFAULT_CHARSET,
							OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY,
							DEFAULT_PITCH|FF_DONTCARE, "Arial");

	TEXTMETRIC tex;
	pDC->SelectObject(font1); pDC->GetTextMetrics(&tex);	hoehe1 = tex.tmHeight;
	pDC->SelectObject(font2); pDC->GetTextMetrics(&tex);	hoehe2 = tex.tmHeight;
	pDC->SelectObject(font3); pDC->GetTextMetrics(&tex);	hoehe3 = tex.tmHeight;
	pDC->SelectObject(font4); pDC->GetTextMetrics(&tex);	hoehe4 = tex.tmHeight;
	pDC->SelectObject(font5); pDC->GetTextMetrics(&tex);	hoehe5 = tex.tmHeight;

	int abzug1 = orand + urand + hoehe5 + hoehe3 + 4*hoehe1 + 3*hoehe5;
	abzug1 += pixelpermm + hoehe3 + 2*hoehe4 + hoehe5 + hoehe1;

	int abzug2 = orand + urand + 2*hoehe5 + pixelpermm + hoehe3 + 2*hoehe4 + 2*hoehe4;
	abzug2 += hoehe5 + hoehe1;

	if (ptt)
	{             
		zeilen1 = (cypx - abzug1) / (2 * pixelpermm + hoehe1);
		zeilen2 = (cypx - abzug2) / (2 * pixelpermm + hoehe1);
	}
	else
	{
		zeilen1 = (cypx - abzug1) / (2 * pixelpermm + hoehe1 + hoehe2);
		zeilen2 = (cypx - abzug2) / (2 * pixelpermm + hoehe1 + hoehe2);
	}

	
	CObArray* array = GetDocument()->GetArray();
	int totalItems = array->GetSize();
	totalItems -= zeilen1;
	int anzahlSeiten = 1;
	while (totalItems > 0)
	{
		totalItems -= zeilen2;
		anzahlSeiten++;
	}
   pInfo->SetMaxPage(anzahlSeiten);
   
	//Tabulatoren berechnen
	CSize extent;      
	pDC->SelectObject(font1);
	extent = pDC->GetTextExtent("00000000000000", 14);
	aTabs[0] = extent.cx;

	//Zwischenraum U-Nr berechnen
	pDC->SelectObject(font2);
	extent = pDC->GetTextExtent("00", 2);
	zr = extent.cx;
}

void CBetrzlgView::OnPrint(CDC* pDC, CPrintInfo* pInfo)
{
	CObArray* array = GetDocument()->GetArray();
	CZahlung* hz;
	CSize extent;
	int akty = orand;
	int startlinieny = 0;
	char buffer[255];
	
	//Text 'KONTOINHABER' und SeitenNr
	pDC->SelectObject(font5);
	wsprintf(buffer, "Seite %3d", pInfo->m_nCurPage);
	extent = pDC->GetTextExtent(buffer, strlen(buffer));
	pDC->TextOut(pInfo->m_rectDraw.Width() - (extent.cx + rrand), akty, buffer, strlen(buffer));
   
   
	if (pInfo->m_nCurPage == 1)
	{
		pDC->SelectObject(font3);
		pDC->TextOut(lrand, akty, ktoinh, strlen(ktoinh));
		akty += hoehe5;

		//Inhaber    
		CString z1 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile1", "30-4541-9");
		CString z2 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile2", "Eidg. Steuerverwaltung");
	   CString z3 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile3", "Betreibungskostenvorschüsse");
	   CString z4 = AfxGetApp()->GetProfileString("Kontoinhaber", "Zeile4", "Bern");

		pDC->SelectObject(font1);
		pDC->TextOut(lrand, akty, z1, z1.GetLength()); akty += hoehe1;
		pDC->TextOut(lrand, akty, z2, z2.GetLength()); akty += hoehe1;
		pDC->TextOut(lrand, akty, z3, z3.GetLength()); akty += hoehe1;
		pDC->TextOut(lrand, akty, z4, z4.GetLength()); akty += hoehe1;

		//ZaNr
		pDC->SelectObject(font5);
		akty += hoehe5;        
		
		wsprintf(buffer, liste, m_auftragsnr);
		pDC->TextOut(lrand, akty, buffer, strlen(buffer));
		akty += 2*hoehe5; // inkl. 1 Leerzeile
	}
	else
	{
		//ZaNr
		wsprintf(buffer, liste, m_auftragsnr);
		pDC->TextOut(lrand, akty, buffer, strlen(buffer));
		akty += 2*hoehe5; // inkl. 1 Leerzeile
	}

	//Start
	startlinieny = akty;

	// Ueberschrift
	pDC->SelectObject(font3); 
	pDC->SelectObject(pen3);
	pDC->MoveTo(lrand, akty);	pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);	
	pDC->SelectObject(pen2);
	akty += pixelpermm / 2;
	pDC->TabbedTextOut(lrand2, akty, ueberschrift1, strlen(ueberschrift1), sizeof(aTabs) / sizeof(int), aTabs, lrand2);
	extent = pDC->GetTextExtent(ueberschrift2, strlen(ueberschrift2));
	pDC->TextOut(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx), akty, ueberschrift2);
	akty += hoehe3 + pixelpermm / 2;
	pDC->MoveTo(lrand, akty);	pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);

	
	//Summe berechnen
	float summe;  
	CZahlung* temp;
	if (pInfo->m_nCurPage == 1)
	{
		summe = 0.0;
	}		
	else
	{
		for (int i = 0; i < zeilen1; i++)
		{
			temp = (CZahlung*)array->GetAt(i);
			summe += temp->GetBetrag();
		}
		
		for (i = zeilen1; i < zeilen1+((pInfo->m_nCurPage-2) * zeilen2); i++)
		{
			temp = (CZahlung*)array->GetAt(i);
			summe += temp->GetBetrag();		
		}
	}
	
	
	// Uebertrag wenn vorhanden
	if (pInfo->m_nCurPage > 1)
	{
		pDC->SelectObject(font4);
		akty += hoehe4 / 2;
		pDC->TabbedTextOut(lrand2, akty, uebertrag, strlen(uebertrag), sizeof(aTabs) / sizeof(int), aTabs, lrand2);
		sprintf(buffer, "%9.2f", summe / 100.0);
		extent = pDC->GetTextExtent(buffer, strlen(buffer));
		pDC->TextOut(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx), akty, buffer);

		akty += hoehe4 + hoehe4 / 2;
		pDC->MoveTo(lrand, akty);	pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);
	}

	//Haupttext
	int i = 0;   
	int index; 
	int zeilen;
	if (pInfo->m_nCurPage == 1)
	{
		index = 0;
		zeilen = zeilen1;
	}	
	else
	{
		index = zeilen1;
		index += (pInfo->m_nCurPage-2) * zeilen2;
		zeilen = zeilen2;
	}

	
	while ((i < zeilen) && (index < array->GetSize()))
	{
		pDC->SelectObject(font1);
		akty += pixelpermm;
      
      hz = (CZahlung*)array->GetAt(index);
		
		CString buf(hz->GetKtoNr());
		buf += '\t';
		
		if (hz->GetBank().IsEmpty())  
			buf += hz->GetInhaber();
		else
		{
			buf += hz->GetBank();
			buf += ", Z. v. ";
			buf += hz->GetInhaber();
		}
	
		pDC->TabbedTextOut(lrand2, akty, buf, buf.GetLength(), sizeof(aTabs) / sizeof(int), aTabs, lrand2);
		sprintf(buffer, "%9.2f", hz->GetBetrag() / 100.0);
		extent = pDC->GetTextExtent(buffer, strlen(buffer));
		pDC->TextOut(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx), akty, buffer);

		summe += hz->GetBetrag();
		akty += hoehe1;

		if (!ptt)                    
		{
			pDC->SelectObject(font2);
			pDC->TextOut(lrand2 + aTabs[0], akty, hz->GetUNr(0), hz->GetUNr(0).GetLength());
			
			if (!hz->GetUNr(1).IsEmpty())
			{
				extent = pDC->GetTextExtent(hz->GetUNr(0), hz->GetUNr(0).GetLength());
				int x = extent.cx + zr;
				for (int n = 1; n < 6; n++)
				{
					if (!hz->GetUNr(n).IsEmpty())
					{
						extent = pDC->GetTextExtent(hz->GetUNr(n), hz->GetUNr(n).GetLength());
						pDC->TextOut(lrand2 + aTabs[0] + x, akty, hz->GetUNr(n), hz->GetUNr(n).GetLength());
						x += extent.cx + zr;
					}
				}
			}
			akty += hoehe2;
		}
		akty += pixelpermm;
		pDC->MoveTo(lrand, akty); pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);
		i++;
		index++;
	}


	// Total oder Übertrag
	pDC->SelectObject(font4);
	akty += hoehe4 / 2;
               
	if (pInfo->m_nCurPage != pInfo->GetMaxPage())
		pDC->TabbedTextOut(lrand2, akty, uebertrag, strlen(uebertrag), sizeof(aTabs) / sizeof(int), aTabs, lrand2);
	else
		pDC->TabbedTextOut(lrand2, akty, total, strlen(total),
						  sizeof(aTabs) / sizeof(int), aTabs, lrand2);
	sprintf(buffer, "%9.2f", summe / 100.0);
	extent = pDC->GetTextExtent(buffer, strlen(buffer));
	pDC->TextOut(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx), akty, buffer);

	akty += hoehe4 + hoehe4 / 2;
	pDC->SelectObject(pen3);
	pDC->MoveTo(lrand, akty); pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);

	//Die 4 senkrechten Linien Zeichnen
	pDC->MoveTo(lrand, startlinieny);
	pDC->LineTo(lrand, akty);

	pDC->MoveTo(pInfo->m_rectDraw.Width() - rrand, startlinieny);
	pDC->LineTo(pInfo->m_rectDraw.Width() - rrand, akty);

	pDC->SelectObject(pen2);
	pDC->MoveTo(lrand2 + (aTabs[0] - 2 * pixelpermm), startlinieny);
	pDC->LineTo(lrand2 + (aTabs[0] - 2 * pixelpermm), akty);

	extent = pDC->GetTextExtent("000000000", 9);
	pDC->MoveTo(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx + 2 * pixelpermm), startlinieny);
	pDC->LineTo(pInfo->m_rectDraw.Width() - (rrand2 + extent.cx + 2 * pixelpermm), akty);

	//Datum
	akty += hoehe5;
	pDC->SelectObject(font1);
	
	CTime t = CTime::GetCurrentTime();
	wsprintf(buffer, "Bern, %d. %s %d", t.GetDay(),
				MonatsNamen[t.GetMonth()-1],	t.GetYear());
	pDC->TextOut(lrand, akty, buffer);
}


void CBetrzlgView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	delete font1; delete font2; delete font3; delete font4; delete font5;
	delete pen2; delete pen3;
}

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView diagnostics

#ifdef _DEBUG
void CBetrzlgView::AssertValid() const
{
	CFormView::AssertValid();
}

void CBetrzlgView::Dump(CDumpContext& dc) const
{
	CFormView::Dump(dc);
}

CBetrzlgDoc* CBetrzlgView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CBetrzlgDoc)));
	return (CBetrzlgDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView database support

CRecordset* CBetrzlgView::OnGetRecordset()
{
	return m_pSet;
}


/////////////////////////////////////////////////////////////////////////////
// CBetrzlgView message handlers

void CBetrzlgView::OnKillfocusPcnr()
{
	UpdateData(TRUE);
	
	if (!m_pSet->m_strSuchen.IsEmpty())
	{
		if (!m_pSet->IsOpen())
		return;
		
		m_ctrlInhaber.ResetContent();	  
	   
	   m_pSet->Requery();
		if (m_pSet->IsEOF())
		{                              
			if (MessageBox("Soll ein neues Konto erzeugt werden?", "Neues Konto?", 
					MB_ICONQUESTION | MB_YESNO) == IDYES)
			{
				CBearbKonto bkDlg;
				
				bkDlg.m_pcnr = m_pSet->m_strSuchen;
			
				if (bkDlg.DoModal() == IDOK)
				{
					if (!m_pSet->CanAppend())
						return;
					m_pSet->AddNew();
					m_pSet->m_PCNr = bkDlg.m_pcnr;
					m_pSet->m_Bank = bkDlg.m_bank;
					m_pSet->m_Inhaber = bkDlg.m_inhaber;		
					if(!m_pSet->Update())
					{
						AfxMessageBox("Record not added; no field values were set.");
						return;
					}
					OnKillfocusPcnr();   
					return;
				}  
			}
			else
			{
				m_pSet->SetFieldNull(&(m_pSet->m_PCNr), FALSE);
				m_pSet->SetFieldNull(&(m_pSet->m_Bank), FALSE);
				m_pSet->SetFieldNull(&(m_pSet->m_Inhaber), FALSE);     
				GetDlgItem(IDC_PCNR)->SetFocus();
				((CEdit*)GetDlgItem(IDC_PCNR))->SetSel(0, -1);
			}
		}
		else
		{
			while (!m_pSet->IsEOF())                     
			{
				m_ctrlInhaber.AddString(m_pSet->m_Inhaber);
				m_pSet->MoveNext();  
			}      
			m_ctrlInhaber.SetCurSel(0);
			if (m_ctrlInhaber.GetCount() == 1)  
			{
				GetDlgItem(IDC_BETRAG)->SetFocus();
				((CEdit*)GetDlgItem(IDC_BETRAG))->SetSel(0, -1);
			}
		}			
		UpdateData(FALSE);	
	}
}

void CBetrzlgView::OnAdd()
{                         
	CObArray* array = GetDocument()->GetArray();
	ZStruct zs;
	CString eingabe;
		
	UpdateData(TRUE);           
	if (!m_pSet->m_PCNr.IsEmpty()	&& !m_inhaber.IsEmpty()
			&& m_betrag != 0.00)
	{
		zs.ktonr = new CString(m_pSet->m_PCNr);
		zs.bank = new CString(m_pSet->m_Bank);
		zs.inhaber = new CString(m_inhaber);
		zs.betrag = LONG(m_betrag * 100.00 + 0.5);
		if (m_unr1 == "")
		{
			zs.unr[0] = new CString("gemäss Beilage"); 
			zs.unr[1] = new CString("");
			zs.unr[2] = new CString("");
			zs.unr[3] = new CString("");
			zs.unr[4] = new CString("");
			zs.unr[5] = new CString("");
		}
		else
		{
			zs.unr[0] = new CString(m_unr1);
			zs.unr[1] = new CString(m_unr2);
			zs.unr[2] = new CString(m_unr3);
			zs.unr[3] = new CString(m_unr4);
			zs.unr[4] = new CString(m_unr5);
			zs.unr[5] = new CString(m_unr6);
		}    
		
		CZahlung* zlg = new CZahlung(zs);
		array->Add(zlg);
		GetDocument()->SetModifiedFlag();
		
		Object2ListBox(zlg);
		m_anzahl++;
		
		m_pSet->m_strSuchen = ""; 
		m_pSet->Requery();
		m_inhaber = "";  
		m_ctrlInhaber.ResetContent();
		m_betrag = 0.00;
		m_unr1 = ""; m_unr2 = ""; m_unr3 = ""; 
		m_unr4 = ""; m_unr5 = ""; m_unr6 = "";

      
      if (m_listBox.GetCount() > 15)
      	m_listBox.SetTopIndex(m_listBox.GetCount()-15);
      
      char buf[9];
		sprintf(buf, "%8.2f", m_total);
		m_totalstr = buf;
      UpdateData(FALSE);
      GetDlgItem(IDC_PCNR)->SetFocus();
	}		
}

void CBetrzlgView::Array2ListBox()
{
	m_listBox.ResetContent();
	m_total = 0.0;
	m_anzahl = 0;
	
	CObject* temp;
	CObArray* array = GetDocument()->GetArray();
	int upper = array->GetUpperBound();
	if (upper != -1)	
	{
		for (int i = 0; i <= upper; i++)
		{
			temp = array->GetAt(i);          
			Object2ListBox((CZahlung*)temp);
		}
	}                 
	
	m_anzahl = array->GetSize();  
	char buf[9];
	sprintf(buf, "%8.2f", m_total);
	m_totalstr = buf;
	UpdateData(FALSE);
}

void CBetrzlgView::Object2ListBox(CZahlung* zlg, int lbindex1, int lbindex2, int lbindex3)
{  
	CString eingabe = "";

	eingabe = zlg->GetKtoNr(); 
	eingabe += '\x09';           
	if (!zlg->GetBank().IsEmpty())
	{
		eingabe += zlg->GetBank();
		eingabe += ", Z. v. ";
		eingabe += zlg->GetInhaber();
	}
	else
		eingabe += zlg->GetInhaber();
		
	m_listBox.InsertString(lbindex1, eingabe);
		
	char buffer[9];
	sprintf(buffer, "%-8.2f", zlg->GetBetrag() / 100.0);
	
	m_total += zlg->GetBetrag() / 100.00;
		
	eingabe.Empty();
	eingabe += buffer;
	eingabe += '\x09';
	eingabe += zlg->GetUNr(0);
	for (int i = 1; i < 6; i++)
	{
		if (!zlg->GetUNr(i).IsEmpty())
		{
			eingabe += '\x09';
			eingabe += zlg->GetUNr(i);
		}
	}
		
	m_listBox.InsertString(lbindex2, eingabe);     
	m_listBox.InsertString(lbindex3, "");
}
void CBetrzlgView::OnKillfocusAuftragsnr()
{
	UpdateData(TRUE);
	GetDocument()->SetAuftragsNr(m_auftragsnr);
}



void CBetrzlgView::OnBearbeitenLschen()
{                                        
	CObject* help;
	CObArray* array = GetDocument()->GetArray();
	
	int ix1 = m_listBox.GetCurSel();  
	if ((ix1 - 1) % 3 == 0)
		ix1 --;
	int ix2 = ix1 / 3;
			
	help = array->GetAt(ix2);
	
	m_listBox.DeleteString(ix1);
	m_listBox.DeleteString(ix1);
	m_listBox.DeleteString(ix1);
		
	m_total -= ((CZahlung*)help)->GetBetrag() / 100.0;	
	m_anzahl--;
	array->RemoveAt(ix2);
	GetDocument()->SetModifiedFlag();  
	char buf[9];
	sprintf(buf, "%8.2f", m_total);
	m_totalstr = buf;
	UpdateData(FALSE);
}

void CBetrzlgView::OnUpdateBearbeitenLschen(CCmdUI* pCmdUI)
{
	int ix = m_listBox.GetCurSel();
	ix -= 2;
	if (ix % 3 != 0)
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);
}


void CBetrzlgView::OnDblclkListbox()
{
	CZahlung* help;
	CObArray* array = GetDocument()->GetArray();
	CBearbZlg bzlg;
				
	int ix = m_listBox.GetCurSel();  
	if (ix % 3 != 2)	
	{
		UpdateData(TRUE);
		float total = m_total;
	
		if ((ix - 1) % 3 == 0)
			ix --;
	
		help = (CZahlung*)array->GetAt(ix / 3);
		bzlg.m_pcnr = help->GetKtoNr();
		bzlg.m_bank = help->GetBank();
		bzlg.m_inhaber = help->GetInhaber();
		bzlg.m_betrag = help->GetBetrag() / 100.0;
		total -= bzlg.m_betrag;
		
		if (help->GetUNr(0) != "gemäss Beilage")
		{
			bzlg.m_unr1 = help->GetUNr(0);
			bzlg.m_unr2 = help->GetUNr(1);
			bzlg.m_unr3 = help->GetUNr(2);
			bzlg.m_unr4 = help->GetUNr(3);
			bzlg.m_unr5 = help->GetUNr(4);
			bzlg.m_unr6 = help->GetUNr(5);			
		}					
				
		if (bzlg.DoModal() == IDOK)
		{
			help->SetBetrag(LONG(bzlg.m_betrag * 100.00 + .5));
			total += help->GetBetrag() / 100.00;
			if (!bzlg.m_unr1.IsEmpty())
			{			
				help->SetUNr(0, bzlg.m_unr1);
				help->SetUNr(1, bzlg.m_unr2);
				help->SetUNr(2, bzlg.m_unr3);
				help->SetUNr(3, bzlg.m_unr4);
				help->SetUNr(4, bzlg.m_unr5);
				help->SetUNr(5, bzlg.m_unr6);
			}
			else
			{
				bzlg.m_unr1 = "gemäss Beilage";
				help->SetUNr(0, bzlg.m_unr1);
			}                      
			m_listBox.DeleteString(ix);
			m_listBox.DeleteString(ix);
			m_listBox.DeleteString(ix);
			Object2ListBox(help, ix, ix+1, ix+2); 
			GetDocument()->SetModifiedFlag();  
			m_total = total;  
			char buf[9];
			sprintf(buf, "%8.2f", m_total);
			m_totalstr = buf;
			UpdateData(FALSE);
		} 
	}
}

void CBetrzlgView::OnBearbeitenWiederholen()
{
	CZahlung* help;
	CObArray* array = GetDocument()->GetArray();
	help = (CZahlung*)array->GetAt(array->GetSize()-1);
	
	m_pSet->m_strSuchen = help->GetKtoNr(); 
	m_pSet->Requery();
	
	m_ctrlInhaber.ResetContent();
   while (!m_pSet->IsEOF())                     
	{
		m_ctrlInhaber.AddString(m_pSet->m_Inhaber);
		m_pSet->MoveNext();  
	} 
   
   m_ctrlInhaber.SelectString(-1, help->GetInhaber());
   
	m_betrag = 0.00;
	m_unr1 = ""; m_unr2 = ""; m_unr3 = ""; 
	m_unr4 = ""; m_unr5 = ""; m_unr6 = "";	
	UpdateData(FALSE);
	GetDlgItem(IDC_BETRAG)->SetFocus(); 
	((CEdit*)GetDlgItem(IDC_BETRAG))->SetSel(0, -1);
}

void CBetrzlgView::OnUpdateBearbeitenWiederholen(CCmdUI* pCmdUI)
{
	CObArray* array = GetDocument()->GetArray();
	if (array->GetSize() > 0)
		pCmdUI->Enable(TRUE);
	else
		pCmdUI->Enable(FALSE);	
}

void CBetrzlgView::OnDateiDruckenExtern()
{
	ptt = TRUE;
	CFormView::OnFilePrint();
}

void CBetrzlgView::OnDateiDruckenIntern()
{
	ptt = FALSE;
	CFormView::OnFilePrint();
}

void CBetrzlgView::OnDateiSeitenansichtExtern()
{
	ptt = TRUE;
	CFormView::OnFilePrintPreview();
	
}

void CBetrzlgView::OnDateiSeitenansichtIntern()
{
	ptt = FALSE;
	CFormView::OnFilePrintPreview();	
}
