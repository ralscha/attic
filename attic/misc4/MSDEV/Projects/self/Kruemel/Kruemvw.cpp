// Kruemvw.cpp : implementation of the CKruemelView class
//

#include "stdafx.h"
#include "Kruemel.h"

#include "Kruemdoc.h"
#include "Kruemvw.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif


 //Nicht ganz fein
const int zustaende = 15;
const int aix = 100;
const int bix = 100;
int alt[100][100];
int neu[100][100];

/////////////////////////////////////////////////////////////////////////////
// CKruemelView

IMPLEMENT_DYNCREATE(CKruemelView, CView)

BEGIN_MESSAGE_MAP(CKruemelView, CView)
	//{{AFX_MSG_MAP(CKruemelView)
	ON_COMMAND(ID_START, OnStart)
	ON_COMMAND(ID_STOP, OnStop)
	ON_UPDATE_COMMAND_UI(ID_START, OnUpdateStart)
	ON_UPDATE_COMMAND_UI(ID_STOP, OnUpdateStop)
	ON_UPDATE_COMMAND_UI(ID_APP_ABOUT, OnUpdateAppAbout)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CKruemelView construction/destruction

CKruemelView::CKruemelView()
{
	// TODO: add construction code here
	running = FALSE;
}

CKruemelView::~CKruemelView()
{
}

/////////////////////////////////////////////////////////////////////////////
// CKruemelView drawing

void CKruemelView::OnDraw(CDC* pDC)
{
	CKruemelDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	for (int i = 0; i < aix; i++)
		for (int j = 0; j < bix; j++)
			pDC->SetPixel(j, i, RGB(neu[i][j]*20, 
							  neu[i][j]*30%256, neu[i][j]*50%256));			
}

/////////////////////////////////////////////////////////////////////////////
// CKruemelView diagnostics

#ifdef _DEBUG
void CKruemelView::AssertValid() const
{
	CView::AssertValid();
}

void CKruemelView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CKruemelDoc* CKruemelView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CKruemelDoc)));
	return (CKruemelDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CKruemelView message handlers

void CKruemelView::Step()
{
	CClientDC dc(this);
	int x, y, i, j, x1, y1;

	
	for(i = 0; i < aix; i++)
	{
		for (j = 0; j < bix; j++)
		{
			x = i - 1;
			if (x < 0)
				x = aix-1;     
			y = j - 1;
			if (y < 0)
				y = bix-1;
		
			x1 = (i + 1) % aix;
     		y1 = (j + 1) % bix;
			
			if (alt[x][j] == (alt[i][j] + 1) % zustaende) 
      	{
  	   		neu[i][j] = alt[x][j];
     			dc.SetPixel(j, i, RGB(neu[i][j]*20,neu[i][j]*30%256,neu[i][j]*50%256));	
     		}
	    	else if (alt[i][y] == (alt[i][j] + 1) % zustaende) 
  		  	{
				neu[i][j] = alt[i][y];	
     			dc.SetPixel(j, i, RGB(neu[i][j]*20,neu[i][j]*30%256,neu[i][j]*50%256));	
     		}
			else if (alt[x1][j] == (alt[i][j] + 1) % zustaende) 
	  		{
	     		neu[i][j] = alt[x1][j];
	     		dc.SetPixel(j, i, RGB(neu[i][j]*20,neu[i][j]*30%256,neu[i][j]*50%256));	
	     	}
			else if (alt[i][y1] == (alt[i][j] + 1) % zustaende) 
      	{
     			neu[i][j] = alt[i][y1];	
     			dc.SetPixel(j, i, RGB(neu[i][j]*20,neu[i][j]*30%256,neu[i][j]*50%256));	
     		}
  		}
	}			
	for(i = 0; i < aix; i++)
		for (j = 0; j < bix; j++)
			alt[i][j] = neu[i][j];
	
}


void CKruemelView::OnStart() 
{
	MSG msg;
	running = TRUE;

	while (running)
   {
      while (::PeekMessage(&msg, NULL, 0, 0, PM_REMOVE))
      {
         if (msg.message == WM_QUIT)
         {
            running = FALSE;
            ::PostQuitMessage(0);
            break;
         }
         if (!AfxGetApp()->PreTranslateMessage(&msg))
         {
            ::TranslateMessage(&msg);
            ::DispatchMessage(&msg);
         }
      }
      AfxGetApp()->OnIdle(0);   // updates user interface
      AfxGetApp()->OnIdle(1);   // frees temporary objects
 
     Step();
   }
	    	
}

void CKruemelView::OnStop() 
{
	running = FALSE;			
}

void CKruemelView::OnInitialUpdate() 
{
	
	srand((unsigned)time(NULL));
		
	for (int i = 0; i < aix; i++)
		for (int j = 0; j < bix; j++)
		{
			alt[i][j] = rand() % zustaende;
			neu[i][j] = alt[i][j];
		}
			
	CView::OnInitialUpdate();
}




void CKruemelView::OnUpdateStart(CCmdUI* pCmdUI) 
{
	// TODO: Add your command update UI handler code here
	pCmdUI->Enable(!running);
	
}

void CKruemelView::OnUpdateStop(CCmdUI* pCmdUI) 
{
	// TODO: Add your command update UI handler code here
	pCmdUI->Enable(running);
}

void CKruemelView::OnUpdateAppAbout(CCmdUI* pCmdUI) 
{
	// TODO: Add your command update UI handler code here
	pCmdUI->Enable(!running);
}
