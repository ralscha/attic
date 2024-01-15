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

<h2>${spielForm.spielArt}</h2>
<h4>${spielForm.gegner}</h4>
<form action="punkteSave.do" method="post">
<display:table export="false" class="list" id="row" name="sessionScope.punkteAnzeigeList">
	<display:column title="" property="reihenfolge"/>
	<display:column property="nachname"  width="95"/>
	<display:column property="vorname"  width="80"/>
	<display:column property="jahrgang"/>	
	<display:column titleKey="ries1"><input type="text" size="2" maxlength="2" value="${row.ries1}" name="ries1${row.id}" <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries2"><input type="text" size="2" maxlength="2" value="${row.ries2}" name="ries2${row.id}" <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries3"><input type="text" size="2" maxlength="2" value="${row.ries3}" name="ries3${row.id}" <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries4"><input type="text" size="2" maxlength="2" value="${row.ries4}" name="ries4${row.id}" <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries5"><input type="text" size="2" maxlength="2" value="${row.ries5}" name="ries5${row.id}" <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries6"><input type="text" size="2" maxlength="2" value="${row.ries6}" name="ries6${row.id}" <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
    <display:column property="total" align="right"/>
    <c:if test="${meisterschaft}"><display:column titleKey="rangpunkte" align="center"><input type="text" size="2" maxlength="2" value="${row.rangpunkte}" name="rangpunkte${row.id}"/></display:column></c:if>
	<display:column align="right"><input type="image" name="punkteId${row.id}" src="images/nr.gif"></display:column>
	<display:footer>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<th>Total</th>
		<th align="center">${total.total1}</th>
		<th align="center">${total.total2}</th>
		<th align="center">${total.total3}</th>
		<th align="center">${total.total4}</th>
		<th align="center">${total.total5}</th>
		<th align="center">${total.total6}</th>
		<th align="center">${total.total}</th>
  		<td></td>
  	<tr>
  </display:footer>
</display:table><br><br><br>
<h5><bean:message key="ueberzaehligeSpieler"/></h5>
<display:table export="false" class="list" id="row" name="sessionScope.punkteUeberzaehligAnzeigeList">
	<display:column title="" property="reihenfolge"/>
	<display:column property="nachname"  width="95"/>
	<display:column property="vorname"  width="80"/>
	<display:column property="jahrgang"/>	
	<display:column titleKey="ries1"><input type="text" size="2" maxlength="2" value="${row.ries1}" name="ries1${row.id}" <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries2"><input type="text" size="2" maxlength="2" value="${row.ries2}" name="ries2${row.id}" <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries3"><input type="text" size="2" maxlength="2" value="${row.ries3}" name="ries3${row.id}" <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries4"><input type="text" size="2" maxlength="2" value="${row.ries4}" name="ries4${row.id}" <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries5"><input type="text" size="2" maxlength="2" value="${row.ries5}" name="ries5${row.id}" <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column titleKey="ries6"><input type="text" size="2" maxlength="2" value="${row.ries6}" name="ries6${row.id}" <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>/></display:column>
	<display:column property="total"  align="right"/>
    <c:if test="${meisterschaft}"><display:column titleKey="rangpunkte" align="center"><input type="text" size="2" maxlength="2" value="${row.rangpunkte}" name="rangpunkte${row.id}"/></display:column></c:if>
</display:table><br><br>


	<input type="submit" name="save" value="<bean:message key="save" />">
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
