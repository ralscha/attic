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

<p>&nbsp;</p>

<form action="login.jsp" method="post" name="logonForm">


<table border="0" align="center">

 <tr><td valign="top" bgcolor="#000000">
  <table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr><td bgcolor="#FFCC99" class="titlesmall">ESS Web Calendar Logon</td></tr>
  </table>
 </td></tr>

 <tr><td>&nbsp;</td></tr>

 <tr><td valign="top" bgcolor="#000000">
  <table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr bgcolor="#CFE7E7">
   <td class="text"><b>User ID:</b></td>
   <td><input type="text" name="userid" value="<%= (login.getUserid() != null) ? login.getUserid() : ""%>" size="20" maxlength="30"></td>
  </tr>
  <tr bgcolor="#CFE7E7">
   <td class="text"><b>Password:</b></td>
   <td><input type="password" name="password" size="20" maxlength="30" id="password"></td>
  </tr>
  <tr bgcolor="#CFE7E7">
   <td colspan="2" align="center"><INPUT type="submit" value="Logon" name="Logon"></td>
  </tr>
  </table>
 </td></tr>

<% if (login.getUserid() != null) { %>
 <tr><td>&nbsp;</td></tr>
 <tr><td valign="top" bgcolor="#000000">
  <table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr bgcolor="Red"><td align="center" class="textb">Logon failed</td></tr>
  </table>
 </td></tr>
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
