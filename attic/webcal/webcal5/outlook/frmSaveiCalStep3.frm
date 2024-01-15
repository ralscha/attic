VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} frmSaveiCalStep3 
   Caption         =   "Save iCal - Step 3/3"
   ClientHeight    =   7485
   ClientLeft      =   45
   ClientTop       =   495
   ClientWidth     =   8070
   OleObjectBlob   =   "frmSaveiCalStep3.frx":0000
   StartUpPosition =   1  'CenterOwner
End
Attribute VB_Name = "frmSaveiCalStep3"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False


' Copyright 2004 - Norman L. Jones, Provo Utah (nlj61@comcast.net)
' You are free to use this software for your personal use
' You may NOT use it for commercial purposes without written permission

Option Explicit

Const scratchpath2 As String = "C:\Temp\exporticalconfig102f2.txt" 'Path where scratch file is saved

Private Sub chkSavePass_Click()
If chkSavePass Then

    If vbCancel = MsgBox("This will save your password in a text file in your C:\temp\ directory.", vbInformation + vbOKCancel, "Warning!") Then
        chkSavePass = False
    End If

End If

End Sub

Private Sub cmdCancel_Click()

    frmSaveiCalStep3.Hide

End Sub

Private Sub cmdSave_Click()

    frmSaveiCalStep3.Hide
       
    Dim configfile As Integer
    configfile = FreeFile

    On Error GoTo Hell 'In case we can't open the file
    Open scratchpath2 For Output As #configfile
    Write #configfile, optSaveLocal.Value
    Write #configfile, optFTP.Value
    Write #configfile, optWebservice.Value
    Write #configfile, txtFilename.Text
    Write #configfile, txtHostName.Text
    Write #configfile, txtUserName.Text
    Write #configfile, chkSavePass.Value
    
    If chkSavePass Then
        Write #configfile, txtPassword.Text
    Else
        Write #configfile, ""
    End If
    
    Write #configfile, txtRemoteFileName.Text
    Write #configfile, txtRemoteSub.Text
        
    Write #configfile, txtServiceUserName.Text
    
    Write #configfile, chkServiceSavePass.Value
    
    If chkServiceSavePass Then
        Write #configfile, txtServicePassword.Text
    Else
        Write #configfile, ""
    End If
    
    Close #configfile
    
Hell:

    Dim tempPath As String
    If optSaveLocal Then
        tempPath = txtFilename.Text
    Else
        tempPath = "C:\temp\ical.ics"
    End If
    
    export_ical tempPath

    'Upload to FTP if necessary
    
    If optFTP Then
    
        'I found the source for the FTP class at:
        'http://www.vbusers.com/downloads/download.asp
        'It uses the clsFTP class in the Class Modules (must be added to project)
    
        Dim oFTP As New clsFTP, asFiles() As String, vThisFile As Variant
        
        oFTP.LoginName = txtUserName.Text
        oFTP.Password = txtPassword.Text
        oFTP.ServerName = txtHostName.Text
    
        If oFTP.Connect Then
        
            'You can change to subdirectory, but you must use forward slashes
            oFTP.CurDir = txtRemoteSub.Text
        
            If oFTP.UploadFile(tempPath) Then
    
                MsgBox "File uploaded successfully!", vbExclamation, "Finished"
            Else
                MsgBox "Error uploading file: " & oFTP.LastError
            End If
            
            oFTP.Disconnect
        Else
            MsgBox "Error connecting to FTP server: " & oFTP.LastError
        End If
     ElseIf optWebservice Then
        
        Dim ical As String
        ical = createICal()
        
        Dim xmlhttp As MSXML2.xmlhttp
        Set xmlhttp = CreateObject("msxml2.xmlhttp")
    
        'Open a connection and send a request to the server in the form of an XML fragment
        Call xmlhttp.Open("POST", "http://localhost:8081/cal5/test.do", False)
    
        Call xmlhttp.Send("username" & Chr(10) & "password" & Chr(10) & ical)
    
        'Create an xml document object, and load the server's response
        Dim xmldoc As DOMDocument
        Set xmldoc = CreateObject("Microsoft.XMLDOM")
        xmldoc.async = False


        'Note: the ResponseXml property parses the server's response, responsetext doesn't
        xmldoc.LoadXml (xmlhttp.responseText)
    
        Dim noOfInserted As String
        Dim noOfUpdated As String
        Dim code As String
        code = xmldoc.childNodes.item(0).childNodes.item(0).Text
        noOfInserted = xmldoc.childNodes.item(0).childNodes.item(1).Text
        noOfUpdated = xmldoc.childNodes.item(0).childNodes.item(2).Text
        MsgBox code
        MsgBox noOfInserted & " Events inserted and " & noOfUpdated & " Events updated"
    
   
        
     End If
    
End Sub





Private Sub optSaveLocal_Click()

    txtHostName.Enabled = False
    txtUserName.Enabled = False
    txtPassword.Enabled = False
    txtServicePassword.Enabled = False
    txtServiceUserName.Enabled = False
    
    chkSavePass.Enabled = False
    txtRemoteFileName.Enabled = False
    txtRemoteSub.Enabled = False
    
    txtFilename.Enabled = True

End Sub

Private Sub optFTP_Click()
    
    txtHostName.Enabled = True
    txtUserName.Enabled = True
    txtPassword.Enabled = True
    chkSavePass.Enabled = True
    txtRemoteFileName.Enabled = True
    txtRemoteSub.Enabled = True
    
    txtServicePassword.Enabled = False
    txtServiceUserName.Enabled = False

    
    txtFilename.Enabled = False

End Sub

Private Sub optWebservice_Click()
    txtHostName.Enabled = False
    txtUserName.Enabled = False
    txtPassword.Enabled = False
    
    txtServicePassword.Enabled = True
    txtServiceUserName.Enabled = True
    
    chkSavePass.Enabled = False
    txtRemoteFileName.Enabled = False
    txtRemoteSub.Enabled = False
    
    txtFilename.Enabled = False
End Sub


Private Sub UserForm_Activate()
        
    'Read the settings from the last session
    Dim configfile As Integer
    Dim str As String
    Dim bool As Boolean
    
    configfile = FreeFile

    On Error GoTo Hell
    Open scratchpath2 For Input As #configfile
    
    Input #configfile, bool
    optSaveLocal.Value = bool
    Input #configfile, bool
    optFTP.Value = bool
    Input #configfile, bool
    optWebservice.Value = bool
    Input #configfile, str
    txtFilename.Text = str
    Input #configfile, str
    txtHostName.Text = str
    Input #configfile, str
    txtUserName.Text = str
    Input #configfile, bool
    chkSavePass.Value = bool
    Input #configfile, str
    txtPassword.Text = str
    Input #configfile, str
    txtRemoteFileName.Text = str
    Input #configfile, str
    txtRemoteSub.Text = str
    Input #configfile, str
    txtServiceUserName.Text = str
    Input #configfile, bool
    chkServiceSavePass = bool
    Input #configfile, str
    txtServicePassword.Text = str
    
    
    Close #configfile
      
Hell:

    'Initialize the controls' enabled property
    If optSaveLocal Then
        optSaveLocal_Click
    ElseIf optFTP Then
        optFTP_Click
    Else
        optWebservice_Click
    End If


End Sub


