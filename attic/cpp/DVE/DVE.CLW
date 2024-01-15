; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CMainDlgWindow
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "DvE.h"
VbxHeaderFile=DvE.h
VbxImplementationFile=DvE.cpp
LastPage=0

ClassCount=4

ResourceCount=6
Resource1=IDD_ZIELDIALOG
Resource2=MAIN_DLG_WINDOW
Class1=CMainDlgWindow
Resource3=IDR_MAIN_MENU
Resource4=IDD_SPIELSTAND
Class2=CSSDialog
Resource5=IDD_STRATEGIE
Class3=CSTDialog
Class4=CZielDialog
Resource6=IDD_ABOUTBOX

[DLG:IDD_ABOUTBOX]
Type=1
Class=?
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308352
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:MAIN_DLG_WINDOW]
Type=1
ControlCount=17
Control1=ID_FRAME1,static,1342308352
Control2=IDC_WURF,static,1342308352
Control3=ID_FRAME1,static,1342308361
Control4=ID_FRAME1,static,1342308352
Control5=ID_FRAME1,static,1342308353
Control6=ID_FRAME1,static,1342308352
Control7=ID_FRAME1,static,1342308352
Control8=IDC_WUERFELN,button,1342242817
Control9=IDC_RUNDEBEENDEN,button,1342242816
Control10=ID_FRAME1,static,1342308359
Control11=IDC_RPTECOMP,static,1342308354
Control12=IDC_TOTALCOMP,static,1342308354
Control13=IDC_RPTEHUMA,static,1342308354
Control14=IDC_TOTALHUMA,static,1342308354
Control15=ID_FRAME1,static,1342308352
Control16=IDC_ZIEL,static,1342308354
Control17=ID_FRAME1,static,1342177287

[MNU:IDR_MAIN_MENU]
Type=1
Command1=IDCANCEL
Command2=ID_SPIELSTAND
Command3=ID_STRATEGIE
Command4=ID_MELDUNG
Command5=ID_ZIEL
Command6=ID_ABOUT
CommandCount=6

[CLS:CMainDlgWindow]
Type=0
HeaderFile=DvE.h
ImplementationFile=DvE.cpp
Filter=D
LastObject=ID_FRAME1

[DLG:IDD_SPIELSTAND]
Type=1
ControlCount=7
Control1=IDOK,button,1342242817
Control2=-1,static,1342308352
Control3=-1,static,1342308353
Control4=IDC_SSCOMP,static,1342308353
Control5=-1,static,1342177287
Control6=IDC_SSHUMA,static,1342308353
Control7=-1,static,1342308352
Class=CSSDialog

[CLS:CSSDialog]
Type=0
HeaderFile=ssdialog.h
ImplementationFile=ssdialog.cpp
Filter=D
LastObject=CSSDialog

[DLG:IDD_STRATEGIE]
Type=1
ControlCount=40
Control1=IDOK,button,1342242817
Control2=-1,static,1342308352
Control3=S,static,1342308354
Control4=1016,static,1342308354
Control5=-1,static,1342308352
Control6=1017,static,1342308354
Control7=-1,static,1342308352
Control8=1018,static,1342308354
Control9=-1,static,1342308352
Control10=1019,static,1342308354
Control11=-1,static,1342308352
Control12=1020,static,1342308354
Control13=-1,static,1342308352
Control14=-1,static,1342177287
Control15=-1,static,1342308352
Control16=1021,static,1342308354
Control17=1022,static,1342308354
Control18=-1,static,1342308352
Control19=1023,static,1342308354
Control20=-1,static,1342308352
Control21=1024,static,1342308354
Control22=-1,static,1342308352
Control23=1025,static,1342308354
Control24=-1,static,1342308352
Control25=1026,static,1342308354
Control26=-1,static,1342308352
Control27=-1,static,1342177287
Control28=-1,static,1342308352
Control29=1027,static,1342308354
Control30=1028,static,1342308354
Control31=1029,static,1342308354
Control32=1030,static,1342308354
Control33=-1,static,1342177287
Control34=-1,static,1342308352
Control35=-1,static,1342308352
Control36=-1,static,1342308352
Control37=-1,static,1342308352
Control38=-1,static,1342308352
Control39=-1,static,1342308352
Control40=-1,static,1342308352
Class=CSTDialog

[CLS:CSTDialog]
Type=0
HeaderFile=stdialog.h
ImplementationFile=stdialog.cpp
Filter=D
LastObject=CSTDialog

[DLG:IDD_ZIELDIALOG]
Type=1
Class=CZielDialog
ControlCount=6
Control1=IDOK,button,1342242817
Control2=IDC_ZIELPUNKTE,static,1342308353
Control3=IDCANCEL,button,1342242816
Control4=-1,static,1342177287
Control5=-1,static,1342308352
Control6=IDC_SCROLLZIEL,scrollbar,1342177280

[CLS:CZielDialog]
Type=0
HeaderFile=zieldial.h
ImplementationFile=zieldial.cpp
Filter=D
LastObject=CZielDialog

