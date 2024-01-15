
<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*" info="Show Detail" errorPage="error.jsp"%>

<html>
<head>
<title>Date Planner: Show Detail</title>

<link rel="STYLESHEET" type="text/css" href="planner.css">

</head>
<body>
<%! private final static SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy"); %>

<%

  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  UsersTable usersTable = new UsersTable(conn);

  try {

  	Iterator it = usersTable.select("UserID = '" + request.getParameter("userid") + "'");
	Users user = new Users();
	if (it.hasNext()) {
		user = (Users)it.next();
	}
%>
   <p class="textb"><%= user.getFirstname() + " " + user.getName() %><br>
   <% String d = request.getParameter("date");
      String dd = d.substring(3,5) + "." + d.substring(0,2) + "." + d.substring(6);
	  
	  %><%= dd %></p>
   <p>   


<table border="1">
<tr>
<th>From</th>
<th>To</th>
<th colspan="2">Description</th>
</tr>

<%
  DatesTable dt = new DatesTable(conn);
    
  Iterator itd = dt.selectDates(request.getParameter("userid"), dateformatter.parse(request.getParameter("date")));
  
  while (itd.hasNext()) {
    Dates date = (Dates)itd.next();
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

<% } %>
</tr>
<tr>
</tr>
</table>
</body>
</html>


<% } finally {
     manager.returnConnection(conn);
   }
%>

<%@ include file="common.jsp" %>
