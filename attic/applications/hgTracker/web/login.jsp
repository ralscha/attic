<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>Login</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h1><bean:message key="login.titel"/></h1>
<html:form action="/login" focus="benutzername" method="post">

<table class="login" cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><bean:message key="benutzername" /></td>
	<td><html:text property="benutzername" size="20" maxlength="20" /></td>
</tr>
<tr>
	<td><bean:message key="passwort" /></td>
	<td><html:password property="passwort" size="20" maxlength="20" /></td>
</tr>
<tr>
	<td colspan="2" align="center"><input type="submit" name="login" value="<bean:message key="login" />"></td>
</tr>
</table>
</html:form>

<logic:messagesPresent>

<html:messages id="error">
  <div class="errormsg">
  	<bean:write name="error"/><br>
  </div>
</html:messages>

</logic:messagesPresent>

</body>
</html>
