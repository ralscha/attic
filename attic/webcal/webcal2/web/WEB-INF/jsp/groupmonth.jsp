<%@ include file="include/taglibs.inc"%>

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<script language="JavaScript" src="<c:url value='/scripts/overlib.js'/>" type="text/javascript"></script>  

<table width="100%">
<tr>
  <td width="8%" align="left">
	<c:url value="groupMonth.do" var="backYearUrl">
	  <c:param name="inputYear" value="${sessionScope.groupMonthForm.year-1}"/>
	  <c:param name="inputMonth" value="${sessionScope.groupMonthForm.month}"/>	  
	</c:url>
	<a href="${backYearUrl}">${sessionScope.groupMonthForm.year-1}</a>
  </td>
  <td align="center">
  <c:forEach items="${sessionScope.groupMonthForm.monthNames}" var="month" varStatus="stat">

    <c:choose>
	 <c:when test="${month == sessionScope.groupMonthForm.monthName}">
	    <b>${month}</b>&nbsp;&nbsp;
	 </c:when>
	 <c:otherwise>
		<c:url value="groupMonth.do" var="monthUrl">
		  <c:param name="inputYear" value="${sessionScope.groupMonthForm.year}"/>
		  <c:param name="inputMonth" value="${stat.index}"/>	  
		</c:url>
	    <a href="${monthUrl}">${month}</a>
	   &nbsp;&nbsp;
	 </c:otherwise>

	</c:choose>
  </c:forEach>
  </td>
  <td width="8%" align="right">
	<c:url value="groupMonth.do" var="nextYearUrl">
	  <c:param name="inputYear" value="${sessionScope.groupMonthForm.year+1}"/>
	  <c:param name="inputMonth" value="${sessionScope.groupMonthForm.month}"/>	  
	</c:url>
	<a href="${nextYearUrl}">${sessionScope.groupMonthForm.year+1}</a>
  </td>  
</tr>
</table>
<p>&nbsp;</p>
<table width="100%" cellspacing="0" cellpadding="0" class="border">
<tr>
   <td class="b" rowspan="3">
   ${sessionScope.groupMonthForm.monthName}&nbsp;${sessionScope.groupMonthForm.year}
   </td>
<c:forEach items="${sessionScope.groupMonthForm.weekNos}" var="weekNo" varStatus="stat">
   <td align="center" class="small" colspan="${sessionScope.groupMonthForm.weekColspan[stat.index]}">${weekNo}</td>
</c:forEach>
</tr>
<tr>
<c:forEach begin="0" end="${sessionScope.groupMonthForm.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.groupMonthForm.today == stat.index+1}">
   <td align="center" class="smalltoday">
   </c:when>
   <c:when test="${not empty sessionScope.groupMonthForm.holidays[stat.index]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${sessionScope.groupMonthForm.holidays[stat.index]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallholiday">
   </c:when>
   <c:otherwise>
   <td align="center" class="small">
   </c:otherwise>
   </c:choose>
   ${stat.index+1}
   </td>
</c:forEach>
</tr>

<tr>
<c:forEach items="${sessionScope.groupMonthForm.weekdayNames}" var="weekday" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.groupMonthForm.today == stat.index+1}">
   <td align="center" class="smallertoday">
   </c:when>
   <c:when test="${not empty sessionScope.groupMonthForm.holidays[stat.index]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${sessionScope.groupMonthForm.holidays[stat.index]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallerholiday">
   </c:when>
   <c:otherwise>
   <td align="center" class="smaller">
   </c:otherwise>
   </c:choose>
   ${weekday}
   </td>
</c:forEach>
</tr>

<c:forEach items="${sessionScope.groupMonthForm.users}" var="user">
<tr>
  <td nowrap class="b">${user.name}</td>
  <c:forEach begin="0" end="${sessionScope.groupMonthForm.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.groupMonthForm.today == stat.index+1}">  
     <c:set value="bt" var="style"/>
   </c:when>
   <c:when test="${not empty sessionScope.groupMonthForm.holidays[stat.index]}">
     <c:set value="bh" var="style"/>
   </c:when>   
   <c:otherwise>
     <c:set value="b" var="style"/>
   </c:otherwise>
   </c:choose>   
   
   <c:if test="${user.hasEvents[stat.index]}" var="hasEvents">
   <td class="${style}" align="center"><html:link paramId="day" paramName="stat" paramProperty="index" page="/day.do"><img src='<c:url value="/calPicUser.donogz?d=${stat.index+1}&u=${user.id}&w=${sessionScope.width}&h=${sessionScope.height}" />' alt="" width="${sessionScope.width}" height="${sessionScope.height}" border="0"></html:link></td>
   </c:if>
   <c:if test="${not hasEvents}">
   <td class="${style}" align="center"><html:link paramId="day" paramName="stat" paramProperty="index" page="/day.do"><img src="<c:url value="/images/x.gif" />" width="${sessionScope.width}" height="${sessionScope.height}" alt="" border="0"></html:link></td>
   </c:if>
   
  </c:forEach>
</tr>
</c:forEach>

</table>
<p>&nbsp;</p>


<misc:initSelectOptions id="combineDepartmentOption" type="ch.ess.cal.web.CombineDepartmentOptions" scope="session" />
<c:if test="${combineDepartmentOption.size > 1}" var="hasDepartments">
<html:form action="/groupMonth.do" method="post">
<input type="hidden" name="inputMonth" value="${sessionScope.groupMonthForm.month}">
<input type="hidden" name="inputYear" value="${sessionScope.groupMonthForm.year}">
<bean:message key="department"/>:&nbsp;
   <html:select property="departmentId">
     <html:optionsCollection name="combineDepartmentOption" property="labelValue"/>   
   </html:select>
   <input type="submit" name="show" value="<bean:message key="common.show"/>" />
</html:form> 
</c:if>
<c:if test="${not hasDepartments}">
<bean:message key="department"/>:&nbsp;${sessionScope.groupMonthForm.department}
</c:if>

