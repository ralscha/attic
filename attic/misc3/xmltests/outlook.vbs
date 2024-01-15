   
   
Set oOutApp = WScript.CreateObject("Outlook.Application")
set xObj = WScript.CreateObject("Microsoft.XMLDOM")


Set root = xObj.createElement("outlook")
xObj.appendChild(root)



Set oNS = oOutApp.GetNamespace("MAPI")

' Use the default calendar folder
Set CalFolder = oNS.GetDefaultFolder(9)

' Get all of the appointments in the folder
Set CalItems = CalFolder.Items

root.setAttribute "userid", "sr"

'Loop through the items in the collection. This will not loop infinitely.
For Each itm In CalItems   


   set appointment = xObj.createElement("appointment")
   If (itm.alldayevent) Then
   	appointment.setAttribute "allday", "true"
   Else
   	appointment.setAttribute "allday", "false"
   End If
   xObj.documentElement.appendChild(appointment)
   
   
   Set startdate = xObj.createElement("start")
   startdate.text = itm.start
   appointment.appendChild(startdate)

   Set enddate = xObj.createElement("end")
   enddate.text = itm.end
   appointment.appendChild(enddate)
   
   Set subject = xObj.createElement("subject")
   subject.text = itm.subject
   appointment.appendChild(subject)

   Set body = xObj.createElement("body")
   body.text = itm.body
   appointment.appendChild(body)

   Set location = xObj.createElement("location")
   location.text = itm.location
   appointment.appendChild(location)

   Set importance = xObj.createElement("importance")
   importance.text = itm.importance
   appointment.appendChild(importance)

   Set categories = xObj.createElement("categories")
   categories.text = itm.categories
   appointment.appendChild(categories)


Next


xmlstr = "<?xml version=""1.0""?><!DOCTYPE outlook SYSTEM ""outlook.dtd"">" & xObj.xml

Const ForWriting = 2
Set fso = CreateObject("Scripting.FileSystemObject")
Set ts = fso.OpenTextFile("outlook.xml", ForWriting, True)
ts.write(xmlstr)
Set ts = Nothing

Set itm = Nothing
Set ResItems = Nothing
Set CalItems = Nothing
Set CalFolder = Nothing
Set oNS = Nothing
Set oOutApp = Nothing
   