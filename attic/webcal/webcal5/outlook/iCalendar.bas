Attribute VB_Name = "iCalendar"
' Copyright 2004 - Norman L. Jones, Provo Utah (njones61@gmail.com)
' You are free to use this software for your personal use
' You may NOT use it for commercial purposes without written permission

Option Explicit

'Global variables
Dim myCalendar As Outlook.mapiFolder
Dim allcat As Boolean
Dim catlist() As String

Sub save_catlist(myallcat As Boolean, mycatlist() As String)
'Save these values for future use

allcat = myallcat
catlist = mycatlist

End Sub

Sub save_calendar(theCalendar As Outlook.mapiFolder)

Set myCalendar = theCalendar

End Sub

Function get_calendar() As Outlook.mapiFolder

Set get_calendar = myCalendar

End Function

Sub export_icalendar()
'Sets up data in preparation for prompting users for file name
'and list of categories.


frmSaveiCalStep1.Show


End Sub

Sub test()
    Dim xmlhttp As MSXML2.xmlhttp
    Set xmlhttp = CreateObject("msxml2.xmlhttp")
    
    'Open a connection and send a request to the server in the form of an XML fragment
    Call xmlhttp.Open("POST", "http://localhost:8081/cal5/test.do", False)
    
    Call xmlhttp.Send("username" & Chr(10) & "password\ntext\nICAL")
    
    'Create an xml document object, and load the server's response
    Dim xmldoc
    Set xmldoc = CreateObject("Microsoft.XMLDOM")
    xmldoc.async = False


    'Note: the ResponseXml property parses the server's response, responsetext doesn't
    xmldoc.LoadXml (xmlhttp.responseText)
    
    MsgBox xmldoc.XML
    
   

End Sub


Function createICal()
  Dim fso, fldTemp, tempPath
  Set fso = CreateObject("Scripting.FileSystemObject")
  Set fldTemp = fso.GetSpecialFolder(2) ' TemporaryFolder
  tempPath = fldTemp.Path & "\"
  
  Dim item
  Dim myItems As Items
  
    
  Set myItems = myCalendar.Items

  Dim fileName As String
  Dim inFile As Integer
  Dim ical As String
  Dim exportitem As Boolean

  Dim counter As Integer
  counter = 0
  For Each item In myItems
    If TypeName(item) = "AppointmentItem" Then
            
      exportitem = False
      If (Not ((item.End <= Now) And (DateDiff("d", item.End, Now) > 40))) Or item.IsRecurring Then
        'only Items newer and not older than 40 day will be exported
        'if item is recurring we have to ignore the age
        If allcat Then 'Export all categories
          exportitem = True
        ElseIf category_found(catlist, item.Categories) Then
          exportitem = True
        End If
      End If
        
      If exportitem Then
      
        fileName = tempPath & counter & ".ics"
        item.SaveAs fileName, olICal
            
        inFile = FreeFile
        Open fileName For Input As inFile
        ical = ical & Input(LOF(inFile), inFile)
        Close inFile
      
        fso.DeleteFile fileName
      
        counter = counter + 1
      End If
    End If
  Next item

  Set fldTemp = Nothing
  Set fso = Nothing

  ical = removeDuplicateHeader(ical)
  createICal = ical
  'Dim outFile As Integer
  'outFile = FreeFile
  'Open tempPath & "webcal.ics" For Output As #outFile
  'Print #outFile, ical
  'Close #outFile

End Function



Function removeDuplicateHeader(ical As String)
 Dim findString As String
  findString = "END:VCALENDAR" & vbCrLf
  findString = findString & "BEGIN:VCALENDAR" & vbCrLf
  findString = findString & "PRODID:-//Microsoft Corporation//Outlook 11.0 MIMEDIR//EN" & vbCrLf
  findString = findString & "VERSION:2.0" & vbCrLf
  findString = findString & "METHOD:PUBLISH" & vbCrLf

  removeDuplicateHeader = Replace(ical, findString, "")
  
  findString = "END:VCALENDAR" & vbCrLf
  findString = findString & "BEGIN:VCALENDAR" & vbCrLf
  findString = findString & "PRODID:-//Microsoft Corporation//Outlook 11.0 MIMEDIR//EN" & vbCrLf
  findString = findString & "VERSION:2.0" & vbCrLf
  findString = findString & "METHOD:REQUEST" & vbCrLf

  removeDuplicateHeader = Replace(ical, findString, "")
  
  findString = "END:VCALENDAR" & vbCrLf
  findString = findString & "BEGIN:VCALENDAR" & vbCrLf
  findString = findString & "PRODID:-//Microsoft Corporation//Outlook 10.0 MIMEDIR//EN" & vbCrLf
  findString = findString & "VERSION:2.0" & vbCrLf
  findString = findString & "METHOD:PUBLISH" & vbCrLf

  removeDuplicateHeader = Replace(ical, findString, "")
  
  findString = "END:VCALENDAR" & vbCrLf
  findString = findString & "BEGIN:VCALENDAR" & vbCrLf
  findString = findString & "PRODID:-//Microsoft Corporation//Outlook 10.0 MIMEDIR//EN" & vbCrLf
  findString = findString & "VERSION:2.0" & vbCrLf
  findString = findString & "METHOD:REQUEST" & vbCrLf

  removeDuplicateHeader = Replace(ical, findString, "")
End Function



Sub export_ical(thepath As String)
'Saves the main calendar to an iCalendar file.

If thepath <> "" Then
    Dim outFile As Integer
    outFile = FreeFile
    Open thepath For Output As #outFile
    Print #outFile, createICal
    Close #outFile
End If

End Sub











Public Function category_found(catarray() As String, thecat As String) As Boolean

Dim cat As Variant

category_found = False
For Each cat In catarray
    If cat = thecat Then
        category_found = True
        Exit For
    End If
Next cat
End Function


