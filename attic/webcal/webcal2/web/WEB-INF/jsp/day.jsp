<%@ include file="include/taglibs.inc"%>


<html:link page="/groupMonth.do">Overview</html:link>&nbsp;&nbsp;Day&nbsp;&nbsp;Week&nbsp;&nbsp;Month&nbsp;&nbsp;Year
<p></p>
${sessionScope.dayForm.dayString}
<p></p>
<c:forEach items="${sessionScope.dayForm.allDayEvents}" var="event" varStatus="stat">
${event}
</c:forEach>
<p></p>

<table width="50%" class="day">
<c:forEach items="${sessionScope.dayForm.hours}" var="hour" varStatus="stat">

<tr>
  <td width="1">&nbsp;</td>
  <td class="hour" height="60" rowspan="4" align="left" width="10%" valign="top">${hour}</td>
  
  <c:forEach begin="0" end="${sessionScope.dayForm.cols}" varStatus="stati">
  <c:if test="${sessionScope.dayForm.colspan[stat.index*4][stati.index] != 0}">  
  <c:if test="${sessionScope.dayForm.rowspan[stati.index][stat.index*4] != 0}">  
  
  <c:if test="${empty sessionScope.dayForm.distribution[stat.index*4][stati.index]}" var="flag">
    <td rowspan="${sessionScope.dayForm.rowspan[stati.index][stat.index*4]}" colspan="${sessionScope.dayForm.colspan[stat.index*4][stati.index]}" class="dottedlinet">&nbsp;</td>
  </c:if>
  <c:if test="${not flag}">
    <td bgcolor="#C0C0C0" rowspan="${sessionScope.dayForm.rowspan[stati.index][stat.index*4]}" colspan="${sessionScope.dayForm.colspan[stat.index*4][stati.index]}" class="dottedlinet">

	<table width="100%" border="0" cellpadding="1" cellspacing="0">
	  <tr><td>${sessionScope.dayForm.distribution[stat.index*4][stati.index]}</td></tr>
	</table>
	
	</td>
  </c:if>
  

  </c:if>
  </c:if>
  </c:forEach>
  
</tr>
<c:forEach begin="1" end="3" varStatus="ix">
<tr>
  <td width="1">&nbsp;</td>
  <c:forEach begin="0" end="${sessionScope.dayForm.cols}" varStatus="stati">
  <c:if test="${sessionScope.dayForm.colspan[stat.index*4+ix.index][stati.index] != 0}">
  <c:if test="${sessionScope.dayForm.rowspan[stati.index][stat.index*4+ix.index] != 0}">  

  <c:if test="${empty sessionScope.dayForm.distribution[stat.index*4+ix.index][stati.index]}" var="flag">
      <td rowspan="${sessionScope.dayForm.rowspan[stati.index][stat.index*4+ix.index]}" colspan="${sessionScope.dayForm.colspan[stat.index*4+ix.index][stati.index]}" class="dottedline">&nbsp;</td>
  </c:if>
  <c:if test="${not flag}">
    <td bgcolor="#C0C0C0" rowspan="${sessionScope.dayForm.rowspan[stati.index][stat.index*4+ix.index]}" colspan="${sessionScope.dayForm.colspan[stat.index*4+ix.index][stati.index]}" class="dottedline">

	<table width="100%" border="0" cellspacing="0" cellpadding="1">
	  <tr><td>${sessionScope.dayForm.distribution[stat.index*4+ix.index][stati.index]}</td></tr>
	</table>
	
	</td>
  </c:if>
  
    
  </c:if>
  </c:if>
  </c:forEach>
</tr>
</c:forEach>


</c:forEach>
</table>
