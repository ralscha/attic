<%@ page language="java" session="false"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>HGVerwaltung: Spiel Liste</title>
	<link rel="stylesheet" type="text/css" href="default.css">
</head>

<body>
<h3>Spielplan&nbsp;${jahr}</h3>

<display:table export="false" class="list" id="row" name="spielList" defaultsort="2" requestURI="spielList.do">
	<display:column>	 
    <c:if test="${row.totalNr != null}">
      <a href="punkteEdit.do?spielId=${row.id}" target="_blank" ><img src="images/add.gif" alt="Resultate" width="16" height="15" border="0"></a>
    </c:if>
  </display:column>
  <display:column title="Datum" property="datum" sortable="true" width="101" 
                  decorator="ch.ess.hgtracker.web.DateColumnDecorator"/>
  <display:column title="Wochentag" property="wochentag" sortable="true" width="90"/>
	<display:column title="Art" property="art.spielArt" sortable="true" width="130"/>
  <display:column title="Ort" property="ort" sortable="true" width="60"/>
	<display:column title="Gegner" property="gegner" sortable="true" width="180"/>	
	<display:setProperty name="basic.msg.empty_list">Keine Spiele gefunden</display:setProperty>
</display:table>

</body>
</html>
