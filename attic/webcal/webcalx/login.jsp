<%@ page language="java" import="java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*,ch.ess.calendar.session.*" info="Summary" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>
<jsp:setProperty name="login" property="*"/>

<%
	if (login.isLogonOK()) {
%>
	    <jsp:forward page="summary.jsp"/>
<%
	} else {
%>
<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<title>ESS Web Calendar</title>
	<% ch.ess.calendar.util.BrowserDetector bd = new ch.ess.calendar.util.BrowserDetector(request);
	   if (bd.getBrowserName().equals(ch.ess.calendar.util.BrowserDetector.MOZILLA)) {
	%>	
	<link rel="STYLESHEET" type="text/css" href="plannernn.css">
	<% } else { %>
	<link rel="STYLESHEET" type="text/css" href="planner.css">
	<% } %>
</HEAD>

<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<p align="center" class="titlesmall">ESS Web Calendar Login</p>

<br>

<form name="logonForm" action="login.jsp" method="post">
<table align="center">
<tr bgcolor="#CFE7E7">
<td class="text">User ID:</td>
<td><input type="text" name="userid" value="<%= (login.getUserid() != null) ? login.getUserid() : ""%>" size="20" maxlength="30"></td>
</tr>
<tr bgcolor="#CFE7E7">
<td class="text">Password:</td>
<td><input type="password" name="password" size="20" maxlength="30" id="password"></td>
</tr>
<tr bgcolor="#CFE7E7">
<td colspan="2" align="center"><INPUT type="submit" value="Login" name="Login"></td>
</tr>

<% if (login.getUserid() != null) { %>
<tr><td colspan="2">&nbsp;</td></tr>
<tr bgcolor="Red"><td  colspan="2" align="center" class="textb">Login failed</td></tr>
<% } %>

</table>
</form>
<script language="JavaScript">
  <!--
    document.logonForm.userid.focus()
  // -->
</script>
</body>
</html>
<% } %>
