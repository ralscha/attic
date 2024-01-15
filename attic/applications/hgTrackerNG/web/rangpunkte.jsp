<%@ page language="java" session="false"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>HGVerwaltung: Rangpunkte</title>
		<link rel="stylesheet" type="text/css" href="default.css">
	</head>

	<body>
		<h3>Rangpunkte&nbsp;${jahr}</h3>
		
    <form:form action="rangpunkteList.do" id="searchForm">
      <form:select path="jahr" items="${jahrOptions}" onchange="searchForm.submit()" />
      <form:hidden path="webLogin" />
      <input type="submit" name="Suchen" value="Suchen">
    </form:form>		
		
		<display:table export="false" defaultsort="8" requestURI="rangpunkteListDL.do" 
		 defaultorder="descending" class="list" id="row" 
		 name="rangpunkte">
			<display:column title="Nachname" property="nachname" sortable="true" width="95" />
			<display:column title="Vorname" property="vorname" sortable="true" width="80" />
			<display:column title="Jahrgang" property="jahrgang" sortable="true" width="65" />
			<display:column title="Punkte" property="punkte" sortable="true" width="50" align="right" />
			<display:column title="Striche" property="striche" sortable="true" width="50" align="right" />
			<display:column title="Schnitt" property="schnitt" sortable="true" width="50" align="right" />
			<display:column title="Punkte pro Spiel" property="punkteProSpiel" sortable="true" width="100" align="right" />
			<display:column title="Rangpunkte" property="rangpunkte" sortable="true" width="80" align="right" />
			<display:setProperty name="basic.msg.empty_list">Keine Spiele gefunden</display:setProperty>
		</display:table>
		<br />
		<br />
		<br />
		<h5>Legende</h5>
		<table cellspacing="2" cellpadding="2" border="0">
			<tr>
				<td><strong>Punkte</strong></td>
				<td>Total der erreichten Punkte in der Meisterschaft</td>
			</tr>
			<tr>
				<td><strong>Striche</strong></td>
				<td>Total geschlagene Striche in der Meisterschaft</td>
			</tr>
			<tr>
				<td><strong>Schnitt</strong></td>
				<td>Bisheriger Durchschnitt der Stricher in der Meisterschaft</td>
			</tr>
			<tr>
				<td><strong>Punkte pro Spiel</strong></td>
				<td>Zukünftige Punkte der noch anstehenden Meisterschaftsspienlen um den Kranz zu erreichen</td>
			</tr>
			<tr>
				<td><strong>Rangpunkte</strong></td>
				<td>Total der Rangpunkte in der Meisterschaft</td>
			</tr>
		</table>


	</body>
</html>