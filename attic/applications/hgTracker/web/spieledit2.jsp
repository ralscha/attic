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
	<title></title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3><bean:message key="spieledit2" /></h3>

<form action="aufstellung.do" method="post">
<display:table export="false" class="list" id="row" name="sessionScope.aufgestellteSpielerList">
	<display:column titleKey="reihenfolge"><input type="text" size="5" maxlength="5" value="${row.reihenfolge}" name="reihenfolge${row.id}"/></display:column>
	<display:column property="nachname" sortable="true" width="95"/>
	<display:column property="vorname" sortable="true" width="80"/>
	<display:column property="jahrgang" sortable="true" width="65"/>	
	<display:column titleKey="ueberzaehligeSpieler"><input type="checkbox" name="uez${row.id}" value="1"></display:column>
</display:table><br><br>
	<html:cancel><bean:message key="zurueck" /></html:cancel>
	<input type="submit" name="save" value="<bean:message key="weiter" />">
</form>

<br><br><br>
<logic:messagesPresent message="true">

<html:messages message="true" id="msg">
  <div class="infomsg">
  	<bean:write name="msg"/><br>
  </div>
</html:messages>

</logic:messagesPresent>

</body>
</html>
