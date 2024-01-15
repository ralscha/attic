
<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Manage Appointments" errorPage="error.jsp"%>

<% 
    SQLManager manager = SQLManager.getInstance();
    Connection conn = manager.requestConnection();
%>

<%
  session.removeAttribute("frompath");
%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.setConnection(conn); %>
</jsp:useBean>

<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<title>ESS Web Calendar: Appointments</title>
<link rel="STYLESHEET" type="text/css" href="planner.css">
<script language="JavaScript" type="text/javascript">
<!--
  function calpopup(lnk) { 
  window.open(lnk, "calendar","height=250,width=250,scrollbars=no") 
  }

//-->
</script>	
</head>
<body>
<%@ include file="menu.jsp"%>
<p>
<%
  AppointmentsTable at = new AppointmentsTable(conn);
  
  try {
    if (login.isLogonOK()) {
	  	String deleteappid = request.getParameter("delete");
 		if (deleteappid != null) {
	      at.delete("appointmentid = "+deleteappid);
  		}
		
		String deleteButton = request.getParameter("deleteButton");
		if (deleteButton != null) {
		    String untilDate = request.getParameter("deletedate");
			at.deleteAllUntil(untilDate, login.getUserid());
		}
		
	}
%>

<p class="titlesmall">Appointments</p>
<b class="textb">Normal Appointments</b>
<table border="1">
<tr>
<th align="left">Start</th>
<th align="left">End</th>
<th align="left">Title</th>
<th align="left">Category</th>
<th>Private</th>
<th>Reminder</th>
<th>&nbsp;</th>
<th>&nbsp;</th>
</tr>

<%  
    if (login.isLogonOK()) {

		
	Iterator it = at.select("userid = '" + login.getUserid() + "' OR userid = 'group'", "enddate");
	while(it.hasNext()) {
	  Appointments apps = (Appointments)it.next();
	  if (apps.isGroup() && !login.isAdmin()) continue;

	  if (!apps.hasRepeaters()) {
%>
 <tr>
 <td><%= apps.getFormatedStartDate() %>
 <% if (!apps.isAlldayevent()) { %>
   &nbsp;<%= apps.getFormatedStartTime() %>
<% } %>   
</td>
  
 <td><%= apps.getFormatedEndDate() %>
 <% if (!apps.isAlldayevent()) { %>
 &nbsp;<%= apps.getFormatedEndTime() %>
 <% } %>
 </td>
 
 <td><%= apps.getSubject() %></td>   
 <td><%= categoriesMap.getDescription(apps.getCategoryid()) %></td> 
 <% if (apps.isPrivate()) { %>
 	<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
 <% } else { %>
    <td>&nbsp;</td>
 <% } %>
   <% if ((apps.getReminders() != null) && !apps.getReminders().isEmpty()) { %>
 	<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
 <% } else { %>
    <td>&nbsp;</td>
 <% } %>
 <td align="center"><a href="appointmentslist.jsp?delete=<%= apps.getAppointmentid() %>">Delete</a></td>
 <td align="center"><a href="appointmentdetail.jsp?appid=<%= apps.getAppointmentid() %>">Edit</a></td>
   </tr>
<% } /* if hasRepeaters */%>
<% } /* while it.next */%>
<% } /* if logonOK */ %>
</table>
<p>
<b class="textb">Repeating Appointments</b>
<table border="1">
<tr>
<th align="left">Start</th>
<th align="left">Repeating</th>
<th align="left">Until</th>
<th align="left">Title</th>
<th align="left">Category</th>
<th>Private</th>
<th>Reminder</th>
<th>&nbsp;</th>
<th>&nbsp;</th>
</tr>

<%  
    if (login.isLogonOK()) {

		
	Iterator it = at.select("userid = '" + login.getUserid() + "'", "enddate");
	while(it.hasNext()) {
	  Appointments apps = (Appointments)it.next();
	  if (apps.isGroup() && !login.isAdmin()) continue;

	  if (apps.hasRepeaters()) {
	    Repeaters repeater = (Repeaters)apps.getRepeaters().get(0);
%>
 <tr>
 <td><%= apps.getFormatedStartDate() %>
 <% if (!apps.isAlldayevent()) { %>
   &nbsp;<%= apps.getFormatedStartTime() %>
<% } %>   
</td>
  
 <td><%= repeater.getDescription() %></td>
 <td>
   <% if (repeater.isAlways()) { %>
        always
   <% } else { %>
        <%= ch.ess.calendar.util.Constants.dateFormat.format(repeater.getUntil()) %>
   <% } %>
 </td>
 
 <td><%= apps.getSubject() %></td>   
 <td><%= categoriesMap.getDescription(apps.getCategoryid()) %></td> 
 <% if (apps.isPrivate()) { %>
 	<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
 <% } else { %>
    <td>&nbsp;</td>
 <% } %>
   <% if ((apps.getReminders() != null) && !apps.getReminders().isEmpty()) { %>
 	<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
 <% } else { %>
    <td>&nbsp;</td>
 <% } %>
 <td align="center"><a href="appointmentslist.jsp?delete=<%= apps.getAppointmentid() %>">Delete</a></td>
 <td align="center"><a href="appointmentdetail.jsp?appid=<%= apps.getAppointmentid() %>">Edit</a></td>
   </tr>
<% } /* if hasRepeaters */ %>
<% } /* while it.hasNext */ %>
<% } /* if logonOK */ %>
</table>
<p>
<hr>
<a href="appointmentdetail.jsp" class="text">Add new appointment</a><br>

<p class="text">
<form action="appointmentslist.jsp" method="post" name="deleteform" id="deleteform" class="text">

<% Calendar yesterday = new GregorianCalendar();
   yesterday.add(Calendar.DATE, -1);
%>

<input type="submit" name="deleteButton" value="Delete"> all appointments until <input type="text" name="deletedate" value="<%= ch.ess.calendar.util.Constants.dateFormat.format(yesterday.getTime()) %>" size="10" maxlength="10">
<a href="javascript:calpopup('calendar.jsp?form=deleteform&elem=deletedate')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a>
</form>
</p>
</body>
</html>
<% 
} finally {
	manager.returnConnection(conn);	
} 
%>



