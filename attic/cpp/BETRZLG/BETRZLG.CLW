; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CBetrzlgView
LastTemplate=CRecordset
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "betrzlg.h"
VbxHeaderFile=betrzlg.h
VbxImplementationFile=betrzlg.cpp
LastPage=1

ClassCount=11
Class1=CBetrzlgApp
Class2=CBetrzlgDoc
Class3=CBetrzlgView
Class4=CMainFrame
Class6=CAboutDlg

ResourceCount=8
Resource1=IDR_MAINFRAME
Class5=CBetrzlgSet
Class7=CBearbKonto
Class8=CBearbZlg
Class9=CSeiteDlg
Class10=CExtrasDlg
Class11=CDatumDlg
Resource2=IDD_ABOUTBOX
Resource3=IDD_BETRZLG_FORM
Resource4=IDD_BEARBEITENKONTO
Resource5=IDD_BEARBEITENZLG
Resource6=IDD_SEITEEINRICHTEN
Resource7=IDD_OPTIONSDIALOG
Resource8=IDD_DATUMDIALOG

[CLS:CBetrzlgApp]
Type=0
HeaderFile=betrzlg.h
ImplementationFile=betrzlg.cpp
Filter=N
LastObject=CBetrzlgApp

[CLS:CBetrzlgDoc]
Type=0
HeaderFile=betrzdoc.h
ImplementationFile=betrzdoc.cpp
Filter=N
LastObject=ID_EXTRAS_HISTORY

[CLS:CBetrzlgView]
Type=0
HeaderFile=betrzvw.h
ImplementationFile=betrzvw.cpp
Filter=D
LastObject=CBetrzlgView

[CLS:CBetrzlgSet]
Type=0
HeaderFile=betrzset.h
ImplementationFile=betrzset.cpp
Filter=D

[DB:CBetrzlgSet]
DB=1
ColumnCount=3
Column1=PCNr,12,50,1
Column2=Bank,12,50,1
Column3=Inhaber,12,100,1


[CLS:CMainFrame]
Type=0
HeaderFile=mainfrm.h
ImplementationFile=mainfrm.cpp
Filter=T
LastObject=CMainFrame

[CLS:CAboutDlg]
Type=0
HeaderFile=betrzlg.cpp
ImplementationFile=betrzlg.cpp
Filter=D

[MNU:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_FILE_SAVE_AS
Command5=ID_DATEI_SEITEEINRICHTEN
Command6=ID_DATEI_DRUCKEN_INTERN
Command7=ID_DATEI_DRUCKEN_EXTERN
Command8=ID_DATEI_SEITENANSICHT_INTERN
Command9=ID_DATEI_SEITENANSICHT_EXTERN
Command10=ID_FILE_PRINT_SETUP
Command11=ID_FILE_MRU_FILE1
Command12=ID_APP_EXIT
Command13=ID_BEARBEITEN_WIEDERHOLEN
Command14=ID_BEARBEITEN_LSCHEN
Command15=ID_BEARBEITEN_ALLESLSCHEN
Command16=ID_VIEW_TOOLBAR
Command17=ID_VIEW_STATUS_BAR
Command18=ID_EXTRAS_OPTIONEN
Command19=ID_EXTRAS_HISTORY
Command20=ID_APP_ABOUT
CommandCount=20

[ACL:IDR_MAINFRAME]
Type=1
Class=CMainFrame
Command1=ID_EDIT_COPY
Command2=ID_FILE_NEW
Command3=ID_FILE_OPEN
Command4=ID_FILE_PRINT
Command5=ID_FILE_SAVE
Command6=ID_EDIT_PASTE
Command7=ID_EDIT_UNDO
Command8=ID_EDIT_CUT
Command9=ID_BEARBEITEN_WIEDERHOLEN
Command10=ID_NEXT_PANE
Command11=ID_PREV_PANE
Command12=ID_EDIT_COPY
Command13=ID_EDIT_PASTE
Command14=ID_EDIT_CUT
Command15=ID_EDIT_UNDO
CommandCount=15

[CLS:CBearbKonto]
Type=0
HeaderFile=bearbkto.h
ImplementationFile=bearbkto.cpp
LastObject=IDC_BANK
Filter=D

[DB:CBearbKonto]
DB=1
ColumnCount=-1
LastClass=CBearbKonto
LastPage=0

[CLS:CBearbZlg]
Type=0
HeaderFile=bearbzlg.h
ImplementationFile=bearbzlg.cpp
Filter=D

[CLS:CSeiteDlg]
Type=0
HeaderFile=seitedlg.h
ImplementationFile=seitedlg.cpp
Filter=D
LastObject=IDC_LRAND

[CLS:CExtrasDlg]
Type=0
HeaderFile=extrasdl.h
ImplementationFile=extrasdl.cpp
Filter=D
LastObject=IDC_HISTNAME

[DB:CHistSet]
DB=1
ColumnCount=11
Column1=Zaehler,4,4,1
Column2=Zahlungsauftragsnr,5,2,1
Column3=PCNr,12,11,1
Column4=Bank,12,50,1
Column5=Inhaber,12,60,1
Column6=UNr1,12,9,1
Column7=UNr2,12,9,1
Column8=UNr3,12,9,1
Column9=UNr4,12,9,1
Column10=UNr5,12,9,1
Column11=UNr6,12,9,1
Column12=UNr6,12,9,1
Column13=UNr6,12,9,1

[CLS:CDatumDlg]
Type=0
HeaderFile=datumdlg.h
ImplementationFile=datumdlg.cpp
Filter=D

[DLG:IDD_ABOUTBOX]
Type=1
ControlCount=5
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308352
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889
Control5=IDC_STATIC,static,1342308352

[DLG:IDD_BETRZLG_FORM]
Type=1
ControlCount=23
Control1=IDC_PCNR,edit,1350631552
Control2=IDC_INHABER,listbox,1352728835
Control3=IDC_BETRAG,edit,1350631552
Control4=IDC_UNR1,edit,1350631552
Control5=IDC_UNR2,edit,1350631552
Control6=IDC_UNR3,edit,1350631552
Control7=IDC_UNR4,edit,1350631552
Control8=IDC_UNR5,edit,1350631552
Control9=IDC_UNR6,edit,1350631552
Control10=IDC_AUFTRAGSNR,edit,1350631552
Control11=IDC_ANZAHL,edit,1350633600
Control12=IDC_TOTAL,edit,1350633600
Control13=IDC_STATIC,static,1342308352
Control14=IDC_STATIC,static,1342308352
Control15=IDC_STATIC,static,1342308352
Control16=IDC_STATIC,static,1342308352
Control17=IDC_STATIC,static,1342308352
Control18=IDC_STATIC,static,1342308352
Control19=IDC_STATIC,static,1342308352
Control20=IDC_STATIC,static,1342308352
Control21=IDC_LISTBOX,listbox,1352728961
Control22=IDC_BANK,edit,1350633600
Control23=IDC_ADD,button,1342242817

[DLG:IDD_BEARBEITENKONTO]
Type=1
ControlCount=8
Control1=IDC_BANK,edit,1350631552
Control2=IDC_INHABER,edit,1350631552
Control3=IDC_PCNR,edit,1350631552
Control4=IDOK,button,1342242817
Control5=IDCANCEL,button,1342242816
Control6=IDC_STATIC,static,1342308352
Control7=IDC_STATIC,static,1342308352
Control8=IDC_STATIC,static,1342308352

[DLG:IDD_BEARBEITENZLG]
Type=1
ControlCount=19
Control1=IDC_BETRAG,edit,1350631552
Control2=IDC_UNR1,edit,1350631552
Control3=IDC_UNR2,edit,1350631552
Control4=IDC_UNR3,edit,1350631552
Control5=IDC_UNR4,edit,1350631552
Control6=IDC_UNR5,edit,1350631552
Control7=IDC_UNR6,edit,1350631552
Control8=IDC_PCNR,edit,1350633600
Control9=IDC_BANK,edit,1350633600
Control10=IDC_INHABER,edit,1350633600
Control11=IDOK,button,1342242817
Control12=IDCANCEL,button,1342242816
Control13=IDC_STATIC,static,1342308352
Control14=IDC_STATIC,static,1342308352
Control15=IDC_STATIC,static,1342308352
Control16=IDC_STATIC,static,1342308352
Control17=IDC_STATIC,static,1342308352
Control18=IDC_STATIC,button,1342177287
Control19=IDC_STATIC,button,1342177287

[DLG:IDD_SEITEEINRICHTEN]
Type=1
ControlCount=11
Control1=IDC_ORAND,edit,1350631424
Control2=IDC_LRAND,edit,1350631424
Control3=IDC_RRAND,edit,1350631424
Control4=IDC_URAND,edit,1350631424
Control5=IDOK,button,1342242817
Control6=IDCANCEL,button,1342242816
Control7=IDC_STATIC,button,1342177287
Control8=IDC_STATIC,static,1342308352
Control9=IDC_STATIC,static,1342308352
Control10=IDC_STATIC,static,1342308352
Control11=IDC_STATIC,static,1342308352

[DLG:IDD_OPTIONSDIALOG]
Type=1
ControlCount=10
Control1=IDC_ZEILE1,edit,1350631424
Control2=IDC_ZEILE2,edit,1350631424
Control3=IDC_ZEILE3,edit,1350631424
Control4=IDC_ZEILE4,edit,1350631424
Control5=IDC_HISTNAME,edit,1350631552
Control6=IDOK,button,1342242817
Control7=IDCANCEL,button,1342242816
Control8=IDC_STATIC,button,1342177287
Control9=IDC_STATIC,button,1342177287
Control10=IDC_STATIC,static,1342308352

[DLG:IDD_DATUMDIALOG]
Type=1
ControlCount=7
Control1=IDC_TAG,edit,1350631424
Control2=IDC_MONAT,edit,1350631424
Control3=IDC_JAHR,edit,1350631424
Control4=IDOK,button,1342242817
Control5=IDCANCEL,button,1342242816
Control6=IDC_STATIC,button,1342177287
Control7=IDC_STATIC,static,1342308352

