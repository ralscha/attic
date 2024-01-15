<%@ Language=VBScript %>
<% option explicit 
   on error resume next %>
<!-- #include file="adovbs.inc" -->

<html>
<head>
<title>Date Planner: Show Detail</title>

<link rel="STYLESHEET" type="text/css" href="planner.css">

</head>
<body>


<%

  dim query, cnn, userrs, daters, kindrs, i, length, nextid, fromh, fromm, toh, tom
  
  set cnn = Server.CreateObject("ADODB.Connection")
  cnn.open("DSN=PlannerDb2")
  set daters = Server.CreateObject("ADODB.RecordSet")
  set userrs = Server.CreateObject("ADODB.RecordSet")

  query = "select FirstName, Name from users where userid = '" & Request.QueryString("UserID") & "'"
  userrs.Open query, cnn, adOpenStatic, adLockOptimistic    

%>
   <p class="textb"><%= userrs("Firstname") & " " & userrs("Name") %><br>
   <%= formatDate(Request.QueryString("date")) %></p>
   <p>   

<%
  userrs.Close 
%>


<table border="1">
<tr>
<th>From</th>
<th>To</th>
<th colspan="2">Description</th>
</tr>

<%

  query = "select StartDate, StartTime, EndDate, EndTime, KindID, UserID, DateID, Description from dates where UserID = '" & Request.QueryString("UserID") & "' and startdate <= #" & Request.QueryString("date") & "# and enddate >= #" & Request.QueryString("date") & "# order by StartDate"
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

<% daters.MoveNext 
   loop 
   
   daters.close
   cnn.Close
%>
</tr>
<tr>
</tr>
</table>
</body>
</html>

<!-- #include file="common.asp" -->

