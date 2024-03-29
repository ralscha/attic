
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
<title>ESS Web Calendar: Evénements</title>
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
		  appcache.refresh(login.getUserid(), request);
  		}
		
		String deleteButton = request.getParameter("deleteButton");
		if (deleteButton != null) {
		    String untilDate = request.getParameter("deletedate");
			at.deleteAllUntil(untilDate, login.getUserid());
			appcache.refresh(login.getUserid(), request);
		}
		String update = request.getParameter("update");
		if (update != null) {
			appcache.refresh(login.getUserid(), request);
		}
   		nonrepapp = appcache.getUserAppointments(login.getUserid(), null, login.getUserid(), appcache.APPOINTMENTS_NOT_REPEATING);
   		repapp    = appcache.getUserAppointments(login.getUserid(), null, login.getUserid(), appcache.APPOINTMENTS_REPEATING);		
	}
%>

<p class="titlesmall">Evénements</p>
		<% if ((nonrepapp != null) && !nonrepapp.isEmpty()) { %><b class="textb">Ev&eacute;nements normaux</b>
		<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">De</th>
<th align="left" bgcolor="#D0D0D0">A</th>
<th align="left" bgcolor="#D0D0D0">Titre</th>
<th align="left" bgcolor="#D0D0D0">Cat&eacute;gorie</th>
<th bgcolor="#D0D0D0">Secret</th>
<th bgcolor="#D0D0D0">Rappel</th>
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
 <td align="center"><a href="appointmentslist.jsp?delete=<%= apps.getAppointmentid() %>">Supprimer</a></td>
 <td align="center"><a href="d.jsp?appid=<%= apps.getAppointmentid() %>">Modifier</a></td>
   </tr>
<% } /* while it.next */%>
</table>
		<p>
<% } /* if has nonrepapps */ %>

<% if ((repapp != null) && !repapp.isEmpty()) { %>
<b class="textb">Ev&eacute;nements redondants</b>
<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">De</th>
<th align="left" bgcolor="#D0D0D0">R&eacute;petition</th>
<th align="left" bgcolor="#D0D0D0">A</th>
<th align="left" bgcolor="#D0D0D0">Titre</th>
<th align="left" bgcolor="#D0D0D0">Cat&eacute;gorie</th>
<th bgcolor="#D0D0D0">Secret</th>
<th bgcolor="#D0D0D0">Rappel</th>
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
 <td align="center"><a href="appointmentslist.jsp?delete=<%= apps.getAppointmentid() %>">Supprimer</a></td>
 <td align="center"><a href="d.jsp?appid=<%= apps.getAppointmentid() %>">Modifier</a></td>
   </tr>
<% } /* while it.hasNext */ %>
</table>
<p>
<% } /* has rep apps */ %>
<hr>
<a href="d.jsp" class="text">Ajouter un &eacute;v&eacute;nement</a><br>

<p class="text">
<form action="appointmentslist.jsp" method="post" name="deleteform" id="deleteform" class="text">

<% Calendar yesterday = new GregorianCalendar();
   yesterday.add(Calendar.DATE, -1);
%>

<input type="submit" name="deleteButton" value="Supprimer"> tous les événements jusqu'au <input type="text" name="deletedate" value="<%= ch.ess.calendar.util.Constants.dateFormat.format(yesterday.getTime()) %>" size="10" maxlength="10">
<a href="javascript:calpopup('calendar.jsp?form=deleteform&elem=deletedate')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a>
</form>
</p>
</body>
</html>



