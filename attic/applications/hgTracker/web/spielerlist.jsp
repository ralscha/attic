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
	<title>Spieler Liste</title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<h3><bean:message key="spieler" /></h3>

<display:table export="false" class="list" pagesize="10" id="row" name="sessionScope.spielerList">
	<display:column property="nachname" sortable="true" width="95"/>
	<display:column property="vorname" sortable="true" width="80"/>
	<display:column property="strasse" sortable="true" maxLength="20"/>
	<display:column property="plz" sortable="true"/>
	<display:column property="ort" sortable="true"/>
	<display:column property="jahrgang" sortable="true" width="65"/>	
	<display:column><a href="spielerEdit.do?id=${row.id}" ><img src="images/edit.gif" alt="<bean:message key="edit" />" width="16" height="15" border="0"></a></display:column>
	<display:column><a onclick="return confirm('Wollen Sie diesen Spieler wirklich löschen?')" href="spielerDelete.do?id=${row.id}" ><img src="images/delete.gif" alt="<bean:message key="delete" />" width="16" height="15" border="0"></a></display:column>
	<display:setProperty name="paging.banner.item_name"><bean:message key="spieler"/></display:setProperty>
	<display:setProperty name="paging.banner.items_name"><bean:message key="spieler"/></display:setProperty>
</display:table><br><br>

<table class="noborder">
    <tr>
        <td colspan="2">
          <form action="spielerNew.do" method="post">
	        <input type="submit" name="save" value="<bean:message key="neuerSpieler" />">
          </form>
        </td>
    </tr>
    <tr>    
        <td>
          <form action="spielerExcel.do" method="get">
	        <input type="submit" name="save" value="<bean:message key="excelExport" />">
          </form>
        </td>
        <td>
          <form action="spielerPdf.do" method="get">
	        <input type="submit" name="save" value="<bean:message key="pdfExport" />">
          </form>
        </td>
    </tr>
</table>
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
