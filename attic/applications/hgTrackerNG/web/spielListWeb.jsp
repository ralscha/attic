<%@ page language="java" session="false"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>HGVerwaltung: Spiel Liste</title>
	<link rel="stylesheet" type="text/css" href="default.css">
</head>

<body>
    <h3>Resultate&nbsp;${jahr}</h3>
        
    <form:form action="spielList.do" id="searchForm">
      <form:select path="jahr" onchange="searchForm.submit()" >
        <form:option value="0" label=""/>
        <form:options items="${jahrOptions}"/>
      </form:select>
      <form:select path="art" onchange="searchForm.submit()" >
        <form:option value="0" label=""/>
        <form:options items="${artOptions}" itemValue="id" itemLabel="spielArt"/>
      </form:select>
      <form:hidden path="webLogin" />
      <input type="submit" name="Suchen" value="Suchen">
    </form:form>        
        
    <display:table export="false" class="list" pagesize="10" 
      id="row" name="spielList" defaultsort="2" sort="list" defaultorder="descending" requestURI="spielList.do">
    	<display:column><a href="punkteEdit.do?spielId=${row.id}" target="_blank"><img src="images/add.gif" alt="Resultate" width="16" height="15" border="0"></a></display:column>
    	<display:column title="Datum" property="datum" sortable="true" width="101" 
    	 decorator="ch.ess.hgtracker.web.DateColumnDecorator"/>
    	<display:column title="Art" property="art" sortable="true" width="130"/>
      <display:column title="Ort" property="ort" sortable="true" width="60"/>
    	<display:column title="Gegner" property="gegner" sortable="true" width="180"/>
    	<display:column title="Total Nr." property="totalNr" sortable="true" width="55" align="right"/>
    	<display:column title="Schlag Punkte" property="schlagPunkte" titleKey="schlagPunkteBr" sortable="true" width="55" align="right"/>	
    	<display:column title="Total Nr. Gegner" property="totalNrGegner" titleKey="totalNrGegnerBr" sortable="true" width="55" align="right"/>
      <display:column title="Schlag Punkte Gegner" property="schlagPunkteGegner" titleKey="schlagPunkteGegnerBr" sortable="true" width="55" align="right"/>	
    	<display:setProperty name="paging.banner.item_name">Spiel</display:setProperty>
    	<display:setProperty name="paging.banner.items_name">Spiele</display:setProperty>
    	<display:setProperty name="basic.msg.empty_list">Keine Spiele gefunden</display:setProperty>
			<display:setProperty name="paging.banner.one_item_found"><span class="pagebanner">1 {0} gefunden.</span></display:setProperty>
			<display:setProperty name="paging.banner.all_items_found"><span class="pagebanner">{0} {1} gefunden, zeige alle {2}.</span></display:setProperty>
			<display:setProperty name="paging.banner.some_items_found"><span class="pagebanner">{0} {1} gefunden, zeige {2} bis {3}.</span></display:setProperty>
			<display:setProperty name="paging.banner.full"><span class="pagelinks">[<a href="{1}">Anfang</a>/<a href="{2}">Zurück</a>] {0} [<a href="{3}">Vorwärts</a>/<a href="{4}">Ende</a>]</span></display:setProperty>
			<display:setProperty name="paging.banner.first"><span class="pagelinks">[Anfang/Zurück] {0} [<a href="{3}">Vorwärts</a>/<a href="{4}">Ende</a>]</span></display:setProperty>
			<display:setProperty name="paging.banner.last"><span class="pagelinks">[<a href="{1}">Anfang</a>/<a href="{2}">Zurück</a>] {0} [Vorwärts/Ende]</span></display:setProperty>
      <display:setProperty name="paging.banner.no_items_found"><span class="pagebanner">Kein {0} gefunden.</span></display:setProperty>    	
    </display:table>    
</body>
</html>
