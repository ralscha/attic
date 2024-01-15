<%@ Language=VBScript %>
<% option explicit 
   on error resume next %>

<!-- #include file="adovbs.inc" -->

<html>
<head>
<title>Date Planner: Add Dates</title>

<link rel="STYLESHEET" type="text/css" href="planner.css">
<script language="JavaScript">
<!--
  {
  self.name="Main"; 
  } 
  function calpopup(lnk) { 
  window.open(lnk, "calendar","height=220,width=150,scrollbars=no") 
  }
  
  function IsValidTime(timeStr) {
     // Checks if time is in HH:MM format.

     var timePat = /^(\d{1,2}):(\d{2})$/;

	 if (timeStr == "HH:MM") { return true; }
	 
     var matchArray = timeStr.match(timePat);
     if (matchArray == null) {
        alert("Time is not in a valid format. HH:MM");
        return false;
     }
	 hour = matchArray[1];
	 minute = matchArray[2];


     if (hour < 0  || hour > 23) {
        alert("Hour must be between 00 and 23.");
        return false;
     }
     if (minute<0 || minute > 59) {
        alert ("Minute must be between 0 and 59.");
        return false;
     }
	 
	 return true;
 }
	
 function IsValidDate(dateStr) {

	var datePat = /^(\d{2})(.)(\d{1,2})\2(\d{4})$/;


	var matchArray = dateStr.match(datePat); // is the format ok?
	if (matchArray == null) {
		alert("Date is not in a valid format. DD.MM.YYYY")
		return false;
	}
	
	day = matchArray[1]; // parse date into variables
	month = matchArray[3];
	year = matchArray[4];
	
	if (month < 1 || month > 12) { // check month range
		alert("Month must be between 1 and 12.");
		return false;
	}
	if (day < 1 || day > 31) {
		alert("Day must be between 1 and 31.");
		return false;
	}
	if ((month==4 || month==6 || month==9 || month==11) && day==31) {
		alert("Month "+month+" doesn't have 31 days!")
		return false
	}
	
	if (month == 2) { // check for february 29th
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day>29 || (day==29 && !isleap)) {
			alert("February " + year + " doesn't have " + day + " days!");
			return false;
	   }	
	}
	return true;  // date is valid
}

function CompareDates(fromstr, tostr) {

    var datePat = /^(\d{2})(.)(\d{1,2})(.)(\d{4})$/;
	var matchfromArray = fromstr.match(datePat);
	var matchtoArray = tostr.match(datePat);
  
    if (matchfromArray[5] > matchtoArray[5]) {
		alert("FROM date greater than TO date");
		return false;
	}
	
    if (matchfromArray[5] == matchtoArray[5]) {	   
		if (matchfromArray[3] > matchtoArray[3]) {
		  alert("FROM date greater than TO date");
	      return false;
		}
		
		if (matchfromArray[3] == matchtoArray[3]) {
		  if (matchfromArray[1] > matchtoArray[1]) {
  		   alert("FROM date greater than TO date");
	       return false;
		  }
		}
	}
	
    return true;
  
  
}

  
//-->
</script>

<% 


  dim nologon
  nologon = false
    
  if Session("UserID") = "" then 
     nologon = true
  %>
  
  <script language="JavaScript">
  <!--
   function twoFrames(URL1, F1, URL2,F2)
   {  
    parent.frames[F1].location.href=URL1;
    parent.frames[F2].location.href=URL2;
   }
  //-->
  </script> 
   
<% end if %>

</head>
<body>


<% if nologon then %>

  <p class="text">
  Your no longer logged on<br>
  Pleas click on the refresh link<br>
  
  <a class="text" href="javascript:twoFrames('menu.asp',0,'summary.asp',1)">REFRESH</a>
  </p>
  </body>
  </html>
  
<% else %>  
<%

  dim query, queryid, cnn, daters, kindrs, i, length, nextid
  
  set cnn = Server.CreateObject("ADODB.Connection")
  cnn.open("DSN=PlannerDb2")
  set daters = Server.CreateObject("ADODB.RecordSet")
  set kindrs = Server.CreateObject("ADODB.RecordSet")
  
  query = "select StartDate, EndDate, StartTime, EndTime, Description, KindID, DateID, UserID from dates where UserID = '" & Session("UserID") & "' order by DateID"
  queryid = "select max(DateID) as maxid from dates where UserID = '" & Session("UserID") & "'"

  if Request("Delete") <> "" then
	length = Request.Form("DateID").Count
	if (length >= 1) then 
	    
		for i = 1 to length  
			cnn.Execute("delete from dates where DateID = " & Request.Form("DateID").Item(i) & " AND UserID = '" & Session("UserID") & "'")			
		next
	end if
  end if
  
  if Request("Add") <> "" then     
     daters.Open queryid, cnn, adOpenKeyset, adLockOptimistic 

     nextid = 1                             
     if daters.RecordCount = 1 then     
         if isNull(daters("maxid")) then
           nextid = 1
         else
           nextid = daters("maxid") + 1
         end if
     end if
     daters.Close
     
     daters.Open query, cnn, adOpenKeyset, adLockOptimistic    
     daters.AddNew     
     daters("UserID") = Session("UserID")
     daters("StartDate") = Request("fromdate")
     daters("EndDate") = Request("todate")
     
     
     if trim(Request("description") = "") then
        daters("Description") = null
     else
        daters("Description") = Request("description")
     end if
     
     daters("KindID") = Request("kind")
     daters("DateID") = nextid
     
     
     if trim(Request("fromtime") <> "") AND (Request("fromtime") <> "HH:MM") AND trim(Request("totime") <> "") AND (Request("totime") <> "HH:MM") then
       daters("StartTime") = CDate(Request("fromtime"))
       daters("EndTime") = CDate(Request("totime"))
     else     
       daters("StartTime") = #00:00#
       daters("EndTime") = #23:59#
     end if
     
     
     daters.Update
     daters.close            
  end if
  
%>

<p class="titlesmall">Manage Dates</p>

<form action="adddate.asp" method="post" name="dateform" id="dateform" 
   onSubmit="return IsValidTime(document.dateform.fromtime.value) && IsValidTime(document.dateform.totime.value) && IsValidDate(document.dateform.fromdate.value) && IsValidDate(document.dateform.todate.value) && CompareDates(document.dateform.fromdate.value, document.dateform.todate.value);">
<table border="1">
<tr>
<th>From</th>
<th>To</th>
<th colspan="2">Description</th>
<th>Delete</th>
</tr>

<%
  daters.open query, cnn, adOpenStatic, adLockOptimistic
  do until daters.eof 
%>
 <tr>
 <td><%= formatDate(daters("StartDate")) %>
 <% if daters("StartTime") <> #00:00# then %>
   &nbsp;<%= FormatDateTime(daters("StartTime"),4) %>
<% end if %>   
</td>
  
 <td><%= formatDate(daters("EndDate")) %>
 <% if daters("EndTime") <> #23:59# then %>
 &nbsp;<%= FormatDateTime(daters("EndTime"),4) %></td>
 <% end if %>
 
 <% if not isNull(daters("Description")) then %> 
   <td><%= showKind(daters("KindID")) %></td> 
   <td><%= daters("Description") %></td>   
 <% else %>
   <td colspan="2"><%= showKind(daters("KindID")) %></td> 
 <% end if %>
 <td><input type="checkbox" name="dateid" value="<%= daters("DateId") %>"></td>

<% daters.MoveNext %>
   </tr>
<% loop 
%>

<tr>
<td colspan="5"><input class="text" type="submit" value="Delete" name="Delete"></td>
</tr>
</table>
<p>
<table border="1">
<tr>
<th align="left">From</th>
<th align="left">To</th>
<th align="left">Description</th>
<th></th>
</tr>
<tr>
<td><input type="text" name="fromdate" value="<%= formatdate(date) %>" size="10" maxlength="10">
    <a href="javascript:calpopup('DateSelect.asp?Page=Main&Form=dateform&Element=fromdate')"><img src="calendar.gif" border=0></a>
   </td>	
<td><input type="text" name="todate" value="<%= formatdate(date) %>" size="10" maxlength="10">
    <a href="javascript:calpopup('DateSelect.asp?Page=Main&Form=dateform&Element=todate')"><img src="calendar.gif" border=0></a>
</td>		
<td><select name="kind">
    <% 
       kindrs.open "select KindID, Description from datekind", cnn, adOpenStatic, adLockOptimistic
        do until kindrs.eof 
    %>     
        <option value="<%= kindrs("KindID") %>"><%= kindrs("Description") %></option>    
    <%     
         kindrs.movenext
        loop
        kindrs.close
    %>
	
</select></td>	
<td rowspan="2"><input type="submit" value="Add" name="Add"></td>
</tr>
<tr>
<td><input type="text" name="fromtime" value="HH:MM" size="6" maxlength="5" onFocus="if (fromtime.value == 'HH:MM') fromtime.value = ''">
<td><input type="text" name="totime" value="HH:MM" size="6" maxlength="5" onFocus="if (totime.value == 'HH:MM') totime.value = ''">
   
<td>
<input type="text" name="description" size="30" maxlength="50">
</td>

</tr>

</table>

</form>

</body>
<% cnn.Close %>
</html>
<% end if 'if nologn %>


<!-- #include file="common.asp" -->

