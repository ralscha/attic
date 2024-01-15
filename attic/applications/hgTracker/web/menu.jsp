<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<c:if test="${empty sessionScope.club}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>Untitled</title>
</head>

<body>
<h1>MENU</h1><br>

<table cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><bean:message key="spiel" /></td>
	<td><bean:message key="spielText" /></td>
</tr>
<tr>
	<td><bean:message key="spieler" /></td>
	<td><bean:message key="spielerText" /></td>
</tr>
<tr>
	<td><bean:message key="logout" /></td>
	<td><bean:message key="logoutText" /></td>
</tr>
</table>

</body>
</html>
