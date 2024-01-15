<%
	if (login.isLogoutMode()) {
		session.invalidate();
%>
	    <jsp:forward page="summary.jsp"/>
<%
	} 
%>
<table width="100%">
<tr>
<td align="left" valign="top">
<table cellspacing="0" cellpadding="0">
<tr>
	<td class="title">ESS Web Calendar
	<% if (ch.ess.calendar.util.CheckLicense.isDemo()) { %>
	   <font color="Red"><b>DEMO</b></font>
	<% } %>
	</td>
</tr>
<tr>
	<td class="textsmall">Version: <%= ch.ess.calendar.util.Version.getVersion()%>&nbsp;(c) 2000 <a href="http://www.ess.ch">ESS Development AG</a></td>
</tr>
</table>

</td>

<% if (login.isLogonOK()) { %>

<td>&nbsp;</td>
<td align="center" class="text">
  Logged in: <%= login.getFirstname() + " " + login.getName() %>&nbsp;
</td>
<td align="right" class="text">

<% if ( (request.getParameter("month") != null) && (request.getParameter("year") != null) ) { %>
  <a href="summary.jsp<%= "?month="+request.getParameter("month")+"&year="+request.getParameter("year")%>" class="text">Overview</a>&nbsp;
<% } else { %>
  <a href="summary.jsp" class="text">Overview</a>&nbsp;
<% } %>  

<% if ( (request.getParameter("month") != null) && (request.getParameter("year") != null) ) { %>
  <a href="appointmentslist.jsp<%= "?month="+request.getParameter("month")+"&year="+request.getParameter("year")%>" class="text">Appointments</a>&nbsp;
<% } else { %>
  <a href="appointmentslist.jsp" class="text">Appointments</a>&nbsp;
<% } %>  
  <% if (login.isAdmin()) { %>
	  <a href="manage.jsp" class="text">Manage</a>&nbsp;
  <% } %>
  
  <a href="summary.jsp?logout=true" class="text">Logout</a>

<% } else {%>

<td align="right" valign="bottom" class="text">

<form action="summary.jsp" method="post">
<% if ( (request.getParameter("month") != null) && (request.getParameter("year") != null) ) { %>
  <a href="summary.jsp<%= "?month="+request.getParameter("month")+"&year="+request.getParameter("year")%>" class="text">Overview</a>&nbsp;&nbsp;&nbsp;
<% } else { %>
  <a href="summary.jsp" class="text">Overview</a>&nbsp;&nbsp;&nbsp;
<% } %>  

User ID:&nbsp;<input type="text" name="userid" size="10" maxlength="30">&nbsp;&nbsp;
Password:&nbsp;<input type="password" name="password" size="10" maxlength="30" id="password">&nbsp;
<INPUT type="submit" value="Login" name="Login">
</form>

<% } %>

</td>
</tr>
</table>
<hr>