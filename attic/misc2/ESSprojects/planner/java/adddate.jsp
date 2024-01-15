
<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*" info="Manage Dates" errorPage="error.jsp"%>

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



  boolean nologon = false;
  
  String userid = (String)session.getAttribute("UserID");
  
  if ( (userid == null) || (userid.equals("")) ) {
     nologon = false;
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
   
<% } %>

</head>
<body>


<% if (nologon) { %>

  <p class="text">
  Your no longer logged on<br>
  Pleas click on the refresh link<br>
  
  <a class="text" href="javascript:twoFrames('menu.jsp',0,'summary.jsp',1)">REFRESH</a>
  </p>
  </body>
  </html>
  
<% } else { %>  
<%

  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  UsersTable usersTable = new UsersTable(conn);
  DatesTable datesTable = new DatesTable(conn);

  try {
  
  if (request.getParameter("Delete") != null) {
    String dateid[] = request.getParameterValues("dateID");

	if (dateid != null) {
	    for (int i = 0; i < dateid.length; i++) {
			datesTable.delete("DateID = " + dateid[i] + " AND UserID = '" + userid + "'");
		}
	}
  }
  
  if (request.getParameter("Add") != null) {     
  

    String queryid = "select max(DateID) as maxid from Dates where UserID = '" + userid + "'";

  	Hashtable[] result = SQLUtil.getInstance().executeSql(queryid);
	int nextid = 1;
	if ((result != null) && (result.length > 0 )) {
		Hashtable row = result[0];
		if (row.get("maxid") instanceof Integer) {
			nextid = ((Integer)row.get("maxid")).intValue() + 1;   
		}
	}                         

	Dates newdate = new Dates();
	newdate.setStartDate(request.getParameter("startDate"));
	newdate.setEndDate(request.getParameter("endDate"));
	newdate.setStartTime(request.getParameter("startTime"));
	newdate.setEndTime(request.getParameter("endTime"));
	newdate.setDescription(request.getParameter("description"));
	newdate.setKindID(request.getParameter("kindID"));
	
	newdate.setUserID(userid);
	newdate.setDateID((short)nextid);
	datesTable.insert(newdate);
  }
  
%>

<p class="titlesmall">Manage Dates</p>

<form action="adddate.jsp" method="post" name="dateform" id="dateform" 
   onSubmit="return IsValidTime(document.dateform.startTime.value) && IsValidTime(document.dateform.endTime.value) && IsValidDate(document.dateform.startDate.value) && IsValidDate(document.dateform.endDate.value) && CompareDates(document.dateform.startDate.value, document.dateform.endDate.value);">
<table border="1">
<tr>
<th>From</th>
<th>To</th>
<th colspan="2">Description</th>
<th>Delete</th>
</tr>

<%
	Iterator it = datesTable.select("UserID = '" + userid + "'", "DateID");
	while(it.hasNext()) {
	  Dates date = (Dates)it.next();
%>
 <tr>
 <td><%= formatDate(date.getStartDate()) %>
 <% if (!formatTime(date.getStartTime()).equals("00:00")) { %>
   &nbsp;<%= formatTime(date.getStartTime()) %>
<% } %>   
</td>
  
 <td><%= formatDate(date.getEndDate()) %>
 <% if (!formatTime(date.getEndTime()).equals("23:59")) { %>
 &nbsp;<%= formatTime(date.getEndTime()) %></td>
 <% } %>
 
 <% if (date.getDescription() != null) { %> 
   <td><%= showKind(date.getKindID()) %></td> 
   <td><%= date.getDescription() %></td>   
 <% } else { %>
   <td colspan="2"><%= showKind(date.getKindID()) %></td> 
 <% } %>
 <td><input type="checkbox" name="dateID" value="<%= date.getDateID() %>"></td>

   </tr>
<% } %>

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
<td><input type="text" name="startDate" value="<%= formatDate(new GregorianCalendar().getTime()) %>" size="10" maxlength="10">
    <a href="javascript:calpopup('dateselect.jsp?Page=Main&Form=dateform&Element=startDate')"><img src="calendar.gif" border=0></a>
   </td>	
<td><input type="text" name="endDate" value="<%= formatDate(new GregorianCalendar().getTime()) %>" size="10" maxlength="10">
    <a href="javascript:calpopup('dateselect.jsp?Page=Main&Form=dateform&Element=endDate')"><img src="calendar.gif" border=0></a>
</td>		
<td><select name="kindID">
    <% 
		DateKindTable dkt = new DateKindTable(conn);
		Iterator iter = dkt.select();
		while (iter.hasNext()) {
		  DateKind dk = (DateKind)iter.next();
    %>     
        <option value="<%= dk.getKindID() %>"><%= dk.getDescription() %></option>    
    <% } %>
	
</select></td>	
<td rowspan="2"><input type="submit" value="Add" name="Add"></td>
</tr>
<tr>
<td><input type="text" name="startTime" value="HH:MM" size="6" maxlength="5" onFocus="if (startTime.value == 'HH:MM') startTime.value = ''">
<td><input type="text" name="endTime" value="HH:MM" size="6" maxlength="5" onFocus="if (endTime.value == 'HH:MM') endTime.value = ''">
   
<td>
<input type="text" name="description" size="30" maxlength="50">
</td>

</tr>

</table>

</form>

</body>
</html>
<% 
   } finally {
	manager.returnConnection(conn);	
} 
}%>


<%@ include file="common.jsp" %>

