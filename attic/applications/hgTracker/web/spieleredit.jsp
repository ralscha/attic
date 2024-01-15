<%@ page language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.club}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>Neuer Spieler erfassen</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3>Spieler bearbeiten</h3>

<html:form action="/spielerSave" focus="nachname" method="post">
<html:hidden property="id" />

<table cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><bean:message key="nachname" />*</td>
	<td><html:text property="nachname" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="vorname" />*</td>
	<td><html:text property="vorname" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="strasse" />*</td>
	<td><html:text property="strasse" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="plz" />*</td>
	<td><html:text property="plz" size="4" maxlength="4" /></td>
</tr>
<tr>
	<td><bean:message key="ort" />*</td>
	<td><html:text property="ort" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="jahrgang" />*</td>
	<td><html:text property="jahrgang" size="4" maxlength="4" /></td>
</tr>
<tr>
	<td><bean:message key="aufgestellt" /></td>
	<td><html:checkbox property="aufgestellt" /></td>
</tr>
<tr>
	<td><bean:message key="reihenfolge" /></td>
	<td><html:text property="reihenfolge" size="4" maxlength="4" /></td>
</tr>
<tr>
	<td><bean:message key="email" /></td>
	<td><html:text property="email" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="telNr" /></td>
	<td><html:text property="telNr" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="mobileNr" /></td>
	<td><html:text property="mobileNr" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="aktiv" /></td>
	<td><html:checkbox property="aktiv"/></td>
</tr>
</table>

<input type="submit" name="save" value="<bean:message key="save" />">&nbsp;&nbsp;&nbsp;
<html:cancel><bean:message key="zurueck" /></html:cancel>

</html:form>

<logic:messagesPresent>

<html:messages id="error">
  <div class="errormsg">
  	<bean:write name="error"/><br>
  </div>
</html:messages>

</logic:messagesPresent>

<logic:messagesPresent message="true">

<html:messages message="true" id="msg">
  <div class="infomsg">
  	<bean:write name="msg"/><br>
  </div>
</html:messages>

</logic:messagesPresent>

</body>
</html>
