VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} frmSaveiCalStep2 
   Caption         =   "Save iCal - Step 2/3"
   ClientHeight    =   6135
   ClientLeft      =   45
   ClientTop       =   495
   ClientWidth     =   8070
   OleObjectBlob   =   "frmSaveiCalStep2.frx":0000
   StartUpPosition =   1  'CenterOwner
End
Attribute VB_Name = "frmSaveiCalStep2"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False


' Copyright 2004 - Norman L. Jones, Provo Utah (nlj61@comcast.net)
' You are free to use this software for your personal use
' You may NOT use it for commercial purposes without written permission


Option Explicit

Const scratchpath1 As String = "C:\Temp\exporticalconfig102f1.txt" 'Path where scratch file is saved

Private Sub chkExportAll_Click()
'If True, highlight all items in list and dim list
If chkExportAll Then
    Dim i As Integer
    For i = 0 To lstCategories.ListCount - 1
        lstCategories.Selected(i) = True
    Next i
    lstCategories.Enabled = False
Else
'Otherwise, undim the list
    lstCategories.Enabled = True
End If

End Sub

Private Sub cmdCancel_Click()

    frmSaveiCalStep2.Hide

End Sub

Private Sub cmdNext_Click()

    frmSaveiCalStep2.Hide
    
    'Create a list of selected categories
    Dim allcat As Boolean
    ReDim selcatlist(1 To 1) As String
    Dim ncat As Integer
    Dim i As Integer
    
    If chkExportAll Then
        allcat = True
    Else
        allcat = False
        
        'Search through the list
        
        lstCategories.List(0) = ""
        
        For i = 0 To lstCategories.ListCount - 1
            If lstCategories.Selected(i) Then
                ncat = ncat + 1
                ReDim Preserve selcatlist(1 To ncat) As String
                selcatlist(ncat) = lstCategories.List(i)
            End If
        Next i
                
    End If
    
    Dim configfile As Integer
    configfile = FreeFile

    On Error GoTo Hell 'In case we can't open the file
    Open scratchpath1 For Output As #configfile
    Write #configfile, chkExportAll.Value
    If Not allcat Then
        Write #configfile, ncat
        For i = 1 To ncat
            Write #configfile, selcatlist(i)
        Next i
    End If
    Close #configfile
    
Hell:

    save_catlist allcat, selcatlist
    frmSaveiCalStep3.Show
End Sub


Private Sub UserForm_Activate()
    
    Dim myNameSpace As Variant
    Dim myCalendar As Variant
    Dim myItems As Variant
    Dim item As AppointmentItem
    
    'Unfortunately, it appears there isn't a way to get a list of categories.
    'Search through the appt. items and generate a list of categories.
            
    Set myNameSpace = GetNamespace("MAPI")
    Set myCalendar = get_calendar
    Set myItems = myCalendar.Items
    myItems.IncludeRecurrences = True  'This ensures that recurring appts are fully included
    
    ReDim catlist(0 To 0) As String
    Dim numcat As Integer
    
    catlist(0) = ""           'This is for the case where no category has been selected
    numcat = 1
    
    For Each item In myItems
        If Not category_found(catlist, item.Categories) Then
            
            'Add the category to the list
            ReDim Preserve catlist(0 To numcat) As String
            catlist(numcat) = item.Categories
            numcat = numcat + 1
            
       End If
    Next item
    
    'Load the categories into the list box
    lstCategories.ColumnCount = numcat
    catlist(0) = "<no specified category>"
    lstCategories.List() = catlist
    catlist(0) = ""
    
    'Read the settings from the last session
    Dim configfile As Integer
    Dim prevncat As Integer
    Dim currcat As String
    Dim i As Integer, j As Integer
    Dim str As String
    Dim bool As Boolean
    
    configfile = FreeFile

    On Error GoTo Hell  'Just a little geek humor
    Open scratchpath1 For Input As #configfile
    Input #configfile, bool
    chkExportAll.Value = bool
    If Not chkExportAll Then
        Input #configfile, prevncat
        'Set the toggles in the list based on what was selected before
        For i = 1 To prevncat
            Input #configfile, currcat
            For j = 0 To numcat - 1
                If currcat = catlist(j) Then
                    lstCategories.Selected(j) = True
                    Exit For
                End If
            Next j
        Next i
    End If
    Close #configfile
      
    
Hell:


End Sub


