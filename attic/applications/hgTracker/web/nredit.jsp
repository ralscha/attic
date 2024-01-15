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
<html:form action="/nrSave.do" focus="ries1nr" method="post">
<h5>${spielForm.name}</h5>
<table cellspacing="2" cellpadding="2" border="0">
<c:if test="${spielForm.showries1nr}">
<tr>
	<td><bean:message key="ries1"/></td>
	<td><html:checkbox property="ries1nr"/></td>
</tr>
</c:if>
<c:if test="${spielForm.showries2nr}">
<tr>
	<td><bean:message key="ries2"/></td>
	<td><html:checkbox property="ries2nr"/></td>
</tr>
</c:if>
<c:if test="${spielForm.showries3nr}">
<tr>
	<td><bean:message key="ries3"/></td>
	<td><html:checkbox property="ries3nr"/></td>
</tr>
</c:if>
<c:if test="${spielForm.showries4nr}">
<tr>
	<td><bean:message key="ries4"/></td>
	<td><html:checkbox property="ries4nr"/></td>
</tr>
</c:if>
<c:if test="${spielForm.showries5nr}">
<tr>
	<td><bean:message key="ries5"/></td>
	<td><html:checkbox property="ries5nr"/></td>
</tr>
</c:if>
<c:if test="${spielForm.showries6nr}">
<tr>
	<td><bean:message key="ries6"/></td>
	<td><html:checkbox property="ries6nr"/></td>
</tr>
</c:if>
</table>


<input type="submit" name="save" value="<bean:message key="save" />">&nbsp;&nbsp;&nbsp;
<html:cancel><bean:message key="zurueck" /></html:cancel>

</html:form>
</body>
</html>
