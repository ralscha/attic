
<%@ page language="java" import="java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Manage Appointments" errorPage="error.jsp"%>

<%
  session.removeAttribute("frompath");
%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.init(); %>
</jsp:useBean>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>

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
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p>
<%
  
    List nonrepapp = null;
	List repapp = null;  
    if (login.isLogonOK()) {
	    AppointmentsTable at = new AppointmentsTable();
	  	String deleteappid = request.getParameter("delete");
 		if (deleteappid != null) {
	      at.delete("appointmentid = "+deleteappid);
		  appcache.init(categoriesMap);
  		}
		
		String deleteButton = request.getParameter("deleteButton");
		if (deleteButton != null) {
		    String untilDate = request.getParameter("deletedate");
			at.deleteAllUntil(untilDate, login.getUserid());
			appcache.init(categoriesMap);
		}
		String update = request.getParameter("update");
		if (update != null) {
			appcache.init(categoriesMap);
		}
   		nonrepapp = appcache.getUserAppointments(login.getUserid(), null, login.getUserid(), appcache.APPOINTMENTS_NOT_REPEATING);
   		repapp    = appcache.getUserAppointments(login.getUserid(), null, login.getUserid(), appcache.APPOINTMENTS_REPEATING);		
	}
%>

<p class="titlesmall">Appointments</p>
<% if ((nonrepapp != null) && !nonrepapp.isEmpty()) { %>
<b class="textb">Normal Appointments</b>
<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">Start</th>
<th align="left" bgcolor="#D0D0D0">End</th>
<th align="left" bgcolor="#D0D0D0">Title</th>
<th align="left" bgcolor="#D0D0D0">Category</th>
<th bgcolor="#D0D0D0">Secret</th>
<th bgcolor="#D0D0D0">Reminder</th>
<th></th>
<th></th>
</tr>

<%  		
	Iterator it = nonrepapp.iterator();
	while(it.hasNext()) {
	  Appointments apps = (Appointments)it.next();
	  if (apps.isGroup() && !login.isAdmin()) continue;
%>
 <tr bgcolor="#E6E6E6">
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
 <td align="center"><a href="d.jsp?appid=<%= apps.getAppointmentid() %>">Edit</a></td>
   </tr>
<% } /* while it.next */%>
</table>
<p>
<% } /* if has nonrepapps */ %>

<% if ((repapp != null) && !repapp.isEmpty()) { %>
<b class="textb">Repeating Appointments</b>
<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">Start</th>
<th align="left" bgcolor="#D0D0D0">Repeating</th>
<th align="left" bgcolor="#D0D0D0">Until</th>
<th align="left" bgcolor="#D0D0D0">Title</th>
<th align="left" bgcolor="#D0D0D0">Category</th>
<th bgcolor="#D0D0D0">Secret</th>
<th bgcolor="#D0D0D0">Reminder</th>
<th>&nbsp;</th>
<th>&nbsp;</th>
</tr>

<%  		
	Iterator it = repapp.iterator();
	while(it.hasNext()) {
	  Appointments apps = (Appointments)it.next();
	  if (apps.isGroup() && !login.isAdmin()) continue;

	    Repeaters repeater = (Repeaters)apps.getRepeaters().get(0);
%>
 <tr bgcolor="#E6E6E6">
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
 <td align="center"><a href="d.jsp?appid=<%= apps.getAppointmentid() %>">Edit</a></td>
   </tr>
<% } /* while it.hasNext */ %>
</table>
<p>
<% } /* has rep apps */ %>
<hr>
<a href="d.jsp" class="text">Add new appointment</a><br>

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



