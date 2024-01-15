VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} frmSaveiCalStep1 
   Caption         =   "Save iCal - Step 1/3"
   ClientHeight    =   6135
   ClientLeft      =   45
   ClientTop       =   495
   ClientWidth     =   8070
   OleObjectBlob   =   "frmSaveiCalStep1.frx":0000
   StartUpPosition =   1  'CenterOwner
End
Attribute VB_Name = "frmSaveiCalStep1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False


' Copyright 2004 - Norman L. Jones, Provo Utah (nlj61@comcast.net)
' You are free to use this software for your personal use
' You may NOT use it for commercial purposes without written permission

Option Explicit


Private Sub cmdCancel_Click()

    frmSaveiCalStep1.Hide

End Sub


Private Sub cmdNext_Click()

If optDefault Then

    Dim myNameSpace As Outlook.NameSpace
            
    Set myNameSpace = GetNamespace("MAPI")
    save_calendar myNameSpace.GetDefaultFolder(olFolderCalendar)
    
    lblOtherCalendarName = "<none selected>"  'So we can force a selection next time

    frmSaveiCalStep1.Hide
    frmSaveiCalStep2.Show
    
Else
    If lblOtherCalendarName = "<none selected>" Then
        MsgBox "You must select a calendar first", vbCritical + vbOKOnly, "Error!"
    Else
        frmSaveiCalStep1.Hide
        frmSaveiCalStep2.Show
    End If
    
End If

End Sub


Private Sub cmdPickCalendar_Click()
    Dim myNameSpace As Outlook.NameSpace
    Dim myCalendar As Outlook.mapiFolder
            
    Set myNameSpace = GetNamespace("MAPI")
    Set myCalendar = myNameSpace.PickFolder
    
    If Not (myCalendar Is Nothing) Then 'Make sure something was selected
    
        
        If myCalendar.DefaultItemType <> olAppointmentItem Then
            MsgBox "You must select a calendar item", vbCritical + vbOKOnly, "Error!"
        Else
        
            save_calendar myCalendar
            
            lblOtherCalendarName = myCalendar.Name
            
        End If
    
    End If
    

End Sub

Private Sub optDefault_Click()

cmdPickCalendar.Enabled = False
lblCal.Enabled = False
lblOtherCalendarName.Enabled = False

End Sub


Private Sub optOther_Click()

cmdPickCalendar.Enabled = True
lblCal.Enabled = True
lblOtherCalendarName.Enabled = True

End Sub



Private Sub UserForm_Initialize()

optDefault_Click

End Sub
