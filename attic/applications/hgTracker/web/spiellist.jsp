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
<h3><bean:message key="spiel" /></h3>
<% java.util.List jahrList = ch.ess.hgtracker.web.spiel.Util.getJahrList(); %>
<% session.setAttribute("jahrOption",jahrList); %>
<% java.util.List artList = ch.ess.hgtracker.web.spiel.Util.getArtList(); %>
<% session.setAttribute("artOption",artList); %>
<html:form action="/spielList" focus="jahr" method="post">
  <html:select property="jahr">
	<html:option value="">&nbsp;</html:option>
	<html:optionsCollection name="jahrOption"/>
  </html:select>
  <html:select property="artIdSuche">
	<html:option value="">&nbsp;</html:option>
	<html:optionsCollection name="artOption"/>
  </html:select>
<html:hidden property="printable"/>
<html:hidden property="webLogin"/>
<input type="submit" name="Suchen" value="Suchen">
</html:form>
<p>
<display:table export="false" class="list" pagesize="10" id="row" name="sessionScope.spielList" defaultsort="1">
	<display:column property="datum" sortable="true" width="101" decorator="ch.ess.hgtracker.web.DateColumnDecorator" />
	<display:column property="art" sortable="true" width="130"/>
    <display:column property="ort" sortable="true" width="60"/>
	<display:column property="gegner" sortable="true" width="180"/>
	<display:column property="totalNr" sortable="true" width="55" align="right"/>
	<display:column property="schlagPunkte" titleKey="schlagPunkteBr" sortable="true" width="55" align="right"/>	
    <display:column property="totalNrGegner" titleKey="totalNrGegnerBr" sortable="true" width="55" align="right"/>
	<display:column property="schlagPunkteGegner" titleKey="schlagPunkteGegnerBr" sortable="true" width="55" align="right"/>	
	<display:column><a href="spielEdit.do?id=${row.id}" ><img src="images/edit.gif" alt="<bean:message key="edit" />" width="16" height="15" border="0"></a></display:column>
	<display:column><a onclick="return confirm('Wollen Sie dieses Spiel wirklich löschen?')" href="spielDelete.do?id=${row.id}" ><img src="images/delete.gif" alt="<bean:message key="delete" />" width="16" height="15" border="0"></a></display:column>
	<display:setProperty name="paging.banner.item_name"><bean:message key="spiel"/></display:setProperty>
	<display:setProperty name="paging.banner.items_name"><bean:message key="spiele"/></display:setProperty>
</display:table><br><br>

<form action="spielNew.do" method="post">
	<input type="submit" name="save" value="<bean:message key="neuesSpiel" />">
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
