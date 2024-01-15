<%@ page language="java" session="false"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>HGVerwaltung: Durchschnitt</title>
		<link rel="stylesheet" type="text/css" href="default.css">
	</head>

	<body>
		<h3>Durchschnitt&nbsp;${jahr}</h3>

		<form:form action="durchschnittList.do" id="searchForm">
			<form:select path="jahr" items="${jahrOptions}" onchange="searchForm.submit()" />
			<form:hidden path="webLogin" />
			<input type="submit" name="Suchen" value="Suchen">
		</form:form>

		<display:table export="false" defaultsort="6" requestURI="durchschnittListDL.do" defaultorder="descending" class="list" id="row" name="durchschnitte">
			<display:column title="Nachname" property="nachname" sortable="true" width="95" />
			<display:column title="Vorname" property="vorname" sortable="true" width="80" />
			<display:column title="Jahrgang" property="jahrgang" sortable="true" width="65" />
			<display:column title="Punkte" property="punkte" sortable="true" width="50" align="right" />
			<display:column title="Striche" property="striche" sortable="true" width="50" align="right" />
			<display:column title="Schnitt" property="schnitt" sortable="true" width="50" align="right" />
			<display:setProperty name="basic.msg.empty_list">Keine Spiele gefunden</display:setProperty>
		</display:table>

	</body>
</html>