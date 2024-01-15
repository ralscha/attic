<%@ page language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>Spieler Liste</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3><bean:message key="spieler" /></h3>

<display:table export="false" class="list" id="row" name="sessionScope.spielerList">
	<display:column property="nachname" sortable="true" width="95"/>
	<display:column property="vorname" sortable="true" width="80"/>
	<display:column property="ort" sortable="true"/>
	<display:column property="jahrgang" sortable="true" width="65"/>
    <display:setProperty name="paging.banner.item_name"><bean:message key="spieler"/></display:setProperty>
	<display:setProperty name="paging.banner.items_name"><bean:message key="spieler"/></display:setProperty>
</display:table><br><br>

</body>
</html>
