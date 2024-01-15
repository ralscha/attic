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
	<title></title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
</head>

<body>
<table cellspacing="2" cellpadding="2" border="0" class="noBorder">
<tr>
<td>
    <table cellspacing="2" cellpadding="2" border="0" class="border">
    	<tr>
    	<td colspan="2"><h3>${spielForm.spielArt}</h3></td>
    </tr>
    <tr>
    	<th align="left">Kampfrichter:&nbsp;</th>
    	<td>${spielForm.kampfrichter}</td>
    </tr>
    <tr>
    	<th align="left">Beschreibung:&nbsp;</th>
    	<td>${spielForm.kampfrichterGegner}</td>
    </tr>
    <tr>
    	<th align="left">Gegner:&nbsp;</th>
    	<td>${spielForm.gegner}</td>
    </tr>
    <tr>
    	<th align="left">Datum:&nbsp;</th>
    	<td>${spielForm.datum}</td>
    </tr>
    </table>
</td>
<td valign="bottom">
    <table cellspacing="2" cellpadding="2" border="0" class="border">
        <tr>
        	<th align="center" width="50" valign="top">Total<br>Nr.</th>
        	<th align="center" width="50" valign="top">Schlag<br>Punkte</th>
        	<th align="center" width="50" >Total<br>Nr.<br>Gegner</th>
            <th align="center" width="50" >Schlag<br>Punkte<br>Gegner</th>
        </tr>
        <tr>
        	<td align="center">${spielForm.totalNrHeim}</td>
        	<td align="center">${total.total}</td>
        	<td align="center">${spielForm.totalNr}</td>
            <td align="center">${spielForm.schlagPunkteGegner}</td>
        </tr>
       </table>
</td>
</tr>
</table>
<p>
<display:table export="false" class="list" id="row" name="sessionScope.punkteAnzeigeList">
	<display:column title="" property="reihenfolge"/>
	<display:column property="nachname"  width="95"/>
	<display:column property="vorname"  width="80"/>
	<display:column property="jahrgang"/>	
	<display:column titleKey="ries1" align="right"><span <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries1}</span></display:column>
	<display:column titleKey="ries2" align="right"><span <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries2}</span></display:column>
	<display:column titleKey="ries3" align="right"><span <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries3}</span></display:column>
	<display:column titleKey="ries4" align="right"><span <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries4}</span></display:column>
	<display:column titleKey="ries5" align="right"><span <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries5}</span></display:column>
	<display:column titleKey="ries6" align="right"><span <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries6}</span></display:column>
	<display:column property="total" align="right" class="total"/>
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
  	<tr>
  </display:footer>
</display:table><br><br><br>
<h5><bean:message key="ueberzaehligeSpieler"/></h5>
<display:table export="false" class="list" id="row" name="sessionScope.punkteUeberzaehligAnzeigeList">
	<display:column title="" property="reihenfolge"/>
	<display:column property="nachname"  width="95"/>
	<display:column property="vorname"  width="80"/>
	<display:column property="jahrgang"/>	
	<display:column titleKey="ries1" align="right"><span <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries1}</span></display:column>
	<display:column titleKey="ries2" align="right"><span <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries2}</span></display:column>
	<display:column titleKey="ries3" align="right"><span <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries3}</span></display:column>
	<display:column titleKey="ries4" align="right"><span <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries4}</span></display:column>
	<display:column titleKey="ries5" align="right"><span <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries5}</span></display:column>
	<display:column titleKey="ries6" align="right"><span <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries6}</span></display:column>
	<display:column property="total"  align="right" class="total"/>
</display:table><br><br>

</body>
</html>
