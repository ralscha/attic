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
	<style type="text/css">@import url(calendar-green.css);</style>
   	<script src="calendar.js" type="text/javascript"></script>
   	<script src="calendar-de.js" type="text/javascript"></script>
   	<script src="calendar-setup.js" type="text/javascript"></script>
	<title>Neues Spiel erfassen</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3><bean:message key="spieledit1"/></h3>

<html:form action="/spielSave" focus="datum" method="post">
<html:hidden property="id" />

<table cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><bean:message key="datum" />*</td>
	<td><html:text property="datum" styleId="datum" size="10" maxlength="10" /><img src="images/cal.gif" alt="" name="trigger" id="trigger" width="16" height="16" border="0"></td>
</tr>
<tr>
	<td><bean:message key="zeit" /></td>
	<td><html:text property="stunden" size="2" maxlength="2" />:<html:text property="minuten" size="2" maxlength="2" /></td>
</tr>
<tr>
	<td><bean:message key="art" />*</td>
	<td><html:select property="artId">
			<html:option value="">&nbsp;</html:option>
			<html:optionsCollection property="art"/>
		</html:select>
	</td>
</tr>
<tr>
	<td><bean:message key="ort" />*</td>
	<td><html:select property="ort"> 
			<html:option value="">&nbsp;</html:option>
			<html:option key="Heim" value="Heim"/>
			<html:option key="Auswaerts" value="Auswärts"/>
		</html:select>
	</td>
</tr>
<tr>
	<td><bean:message key="gegner" />*</td>
	<td><html:text property="gegner" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="kampfrichter" /></td>
	<td><html:text property="kampfrichter" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="kampfrichterGegner" /></td>
	<td><html:text property="kampfrichterGegner" size="50" maxlength="255" /></td>
</tr>
<tr>
	<td><bean:message key="totalNr" /></td>
	<td><html:text property="totalNr" size="4" maxlength="4" /></td>
</tr>
<tr>
	<td><bean:message key="schlagPunkteGegner" /></td>
	<td><html:text property="schlagPunkteGegner" size="4" maxlength="4" /></td>
</tr>
</table>

<input type="submit" name="save" value="<bean:message key="save" />">&nbsp;&nbsp;&nbsp;
<html:cancel><bean:message key="zurueck" /></html:cancel>
<input type="submit" name="weiter" value="<bean:message key="weiter" />">

</html:form>

<script type="text/javascript">
  Calendar.setup(
    {
      inputField  : "datum",       // ID of the input field
      ifFormat    : "%d.%m.%Y",    // the date format
      button      : "trigger"      // ID of the button
    }
  );
</script>

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
