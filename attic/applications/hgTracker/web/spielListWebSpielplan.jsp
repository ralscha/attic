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
	<title>Spiel Liste</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3>Spielplan</h3>

<display:table export="false" class="list" id="row" name="sessionScope.spielList" defaultsort="2">
	<display:column>
        <c:if test="${row.totalNr!=null}">
            <a href="punkteEdit.do?spielId=${row.id}&printable=true" target="_blank" ><img src="images/add.gif" alt="Resultate" width="16" height="15" border="0"></a>
        </c:if>
      </display:column>
    <display:column property="datum" sortable="true" width="101" decorator="ch.ess.hgtracker.web.DateColumnDecorator"/>
    <display:column property="wochentag" sortable="true" width="90"/>
	<display:column property="art" sortable="true" width="130"/>
    <display:column property="ort" sortable="true" width="60"/>
	<display:column property="gegner" sortable="true" width="180"/>	
</display:table><br><br>

</body>
</html>
