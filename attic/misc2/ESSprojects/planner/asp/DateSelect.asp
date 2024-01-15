
<%@ LANGUAGE="VBSCRIPT" %> 
  <% 
  'Code Written by D. Scott Hand 
  'If any errors are found, please 
  'e-mail scott_hand@pobox.com with 
  'the error and the way the error 
  'was caused 
  '***Purpose:************ 
  '* This is a page built to show calendar functionality. 
  '* Description: 
  '* This is the instantiated file toto 
  '* allow the user to select a date. 
  '*********************** 
  If Request.Querystring("Page") <> "" Then 
     PageName = Request.Querystring("Page") 
     Session("PageName") = PageName 
  Else 
     PageName = Session("PageName") 
  End If 
  If Request.Querystring("Form") <> "" Then 
     FormName = Request.Querystring("Form") 
     Session("FormName") = FormName 
  Else 
     FormName = Session("FormName") 
  End If 
  If Request.Querystring("Element") <> "" Then 
     ElementName = Request.Querystring("Element") 
     Session("ElementName") = ElementName 
  Else 
     ElementName = Session("ElementName") 
  End If 
  %> 
  <HTML> 
  <HEAD>  
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=iso-8859-1"> 
  <TITLE>Select Date</TITLE> 
  <SCRIPT LANGUAGE="javascript"> 
     function calpopulate(d, m, y) { 

         var dte = "";
         if (d < 10) {
           dte = "0";
         }
         dte = dte + d + ".";
         if (m < 10) {
           dte = dte + "0";
         }
         dte = dte + m + ".";
         dte = dte + y;

	 window.opener.document.<%=formname & "." & elementname%>.value = dte; 
     self.close() 
        } 
  </SCRIPT> 
  </HEAD> 
  <BODY onBlur="javascript:self.focus();"> 
  <% 
  If IsDate(Request.QueryString("Date")) Then 
  BuildDate=Request.QueryString("Date") 
  Else 

  If Request.Querystring("BMonth") = "" Then 
  BMonth = Month(Now) 
  Else 
  BMonth = Request.Querystring("BMonth") 
  End If 

  If Request.QueryString("BYear") <> "" Then 
  BuildDate = "1" & "/" & BMonth  & "/" & _ 
         Request.QueryString("BYear") 
  Else 
  BuildDate = "1" & "/" & BMonth & "/" & Right(Year(Now), 2) 
  End If 

  End If 
  Session("CurrentDate")=BuildDate 

  'This gives the position of weekday for that date 
  BuildDayValue = Weekday(BuildDate) 


  CurrentMonth = Month(BuildDate) 
  %> 
  <center> 
  <table> 
  <tr> 
  <td colspan="7" align="center"> 
  <hr> 

  <font size=2><b><%=MonthName(CurrentMonth)%>&nbsp;<%=Year(BuildDate)%></b></font>
  <br> 

  <% 
  'BuildDate=DateAdd("d", -1, BuildDate) 
  If CurrentMonth < 12 then 
  NextMonth=CurrentMonth+1 & "&BYear=" & Year(BuildDate) 
  Else 
  NextMonth="1&BYear=" & Year(DateAdd ("yyyy", 1, BuildDate)) 
  End if 

  If CurrentMonth > 1 then 
  PreviousMonth=CurrentMonth-1 & "&BYear=" & Year(BuildDate) 
  Else 
  PreviousMonth= "12&BYear=" & Year(DateAdd ("yyyy", -1, BuildDate)) 
  End If 
  %> 
  <a href="DateSelect.asp?BMonth=<%=PreviousMonth%>"><font size=-2>Previous</font></a> 
  &nbsp;&nbsp;&nbsp; 
  <a href="DateSelect.asp?BMonth=<%=NextMonth%>"><font size=-2>Next</font></a> 

  
  <hr></td> 
  </tr> 
  
  <tr> 
  <td><font size="-3">Su</font></td><td><font size="-3">Mo</font></td><td><font 
  size="-3">Tu</font></td><td><font size="-3">We</font></td><td><font 
  size="-3">Th</font></td><td><font size="-3">Fr</font></td><td><font size="-3">Sa</font></td> 
  </tr> 
  <tr> 

  <% 
  DayPosition=1 
  'Now loop through table build with blanks until first day of month 
  'is in position 
  For I = 1 to BuildDayValue-1 

  %> 
  <td><font size="-3">&nbsp;</font></td> 
  <% 
  DayPosition=DayPosition+1 
  Next 

  Do Until CurrentMonth <> Month(BuildDate) 
  %> 

  <% 
  While DayPosition<>8 
  %> 
  <td align="center" <%If Day(BuildDate)=Day(Now) Then Response.Write "bgcolor=""Lime"""%>>
  <font size="-3"> 
  <a href="javascript:<% Response.Write "calpopulate(" & Day(BuildDate) & "," & Month(BuildDate) & "," &  Year(BuildDate)%>)"><%=Day(BuildDate)%></a> 
  </font>
  </td> 
  <% 
  DayPosition=DayPosition+1 
  BuildDate=DateAdd("d", 1, BuildDate) 
  If CurrentMonth <> Month(BuildDate) then 
  DayPosition=8 
  End If 
  Wend 
  DayPosition=1 
  %> 

  </tr><tr> 
  <% 
  Loop 
  %> 
  </tr> 
  </table> 
  </center> 
  </BODY> 
  </HTML> 