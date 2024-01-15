<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
  
	<script language="JavaScript" src="<c:url value='/scripts/overlib.js'/>" type="text/javascript"></script>    
  </head>
  <body>

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<table class="header" width="100%">
<tr>
  <td width="8%" align="left">
	<c:url value="groupMonth.do" var="backYearUrl">
	  <c:param name="inputYear" value="${groupMonthForm.year-1}"/>
	  <c:param name="inputMonth" value="${groupMonthForm.month}"/>	  
	</c:url>
	<a href="${backYearUrl}">${groupMonthForm.year-1}</a>
  </td>
  <td align="center">
  <c:forEach items="${groupMonthForm.monthNames}" var="month" varStatus="stat">

    <c:choose>
	 <c:when test="${month == groupMonthForm.monthName}">
	    <span class="headerselected">${month}</span>&nbsp;&nbsp;
	 </c:when>
	 <c:otherwise>
		<c:url value="groupMonth.do" var="monthUrl">
		  <c:param name="inputYear" value="${groupMonthForm.year}"/>
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
	  <c:param name="inputYear" value="${groupMonthForm.year+1}"/>
	  <c:param name="inputMonth" value="${groupMonthForm.month}"/>	  
	</c:url>
	<a href="${nextYearUrl}">${groupMonthForm.year+1}</a>
  </td>  
</tr>
</table>
<br>

<table width="100%" cellspacing="0" cellpadding="0" class="border">
<tr>
   <td rowspan="3" align="center" class="rowheader">
   ${groupMonthForm.monthName}
   <br/>
   ${groupMonthForm.year}
   </td>
<c:forEach items="${groupMonthForm.weekNos}" var="weekNo" varStatus="stat">
   <td align="center" class="week" colspan="${groupMonthForm.weekColspan[stat.index]}">${weekNo}</td>
</c:forEach>
</tr>
<tr>
<c:forEach begin="1" end="${groupMonthForm.length}" var="day">
   <c:choose>
   <c:when test="${groupMonthForm.today == day}">
   <td align="center" class="smalltoday">
   </c:when>
   <c:when test="${not empty groupMonthForm.holidays[day]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${groupMonthForm.holidays[day]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallholiday">
   </c:when>
   <c:when test="${not empty groupMonthForm.weekends[day]}">
   <td align="center" class="smallweekend">
   </c:when>   
   <c:otherwise>
   <td align="center" class="small">
   </c:otherwise>
   </c:choose>
   
   <c:url value="editEvent.do" var="createNewEvent"> 
     <c:param name="from" value="group"/>
     <c:param name="inputYear" value="${groupMonthForm.year}"/>     
     <c:param name="inputMonth" value="${groupMonthForm.month}"/>
     <c:param name="inputDay" value="${day}"/>
	</c:url>
   
   <a href="${createNewEvent}">${day}</a>
   
   </td>
</c:forEach>
</tr>

<tr>
<c:forEach begin="1" end="${groupMonthForm.length}" var="day">
   <c:choose>
   <c:when test="${groupMonthForm.today == day}">
   <td align="center" class="smallertoday">
   </c:when>
   <c:when test="${not empty groupMonthForm.holidays[day]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${groupMonthForm.holidays[day]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallerholiday">
   </c:when>
   <c:when test="${not empty groupMonthForm.weekends[day]}">
   <td align="center" class="smallerweekend">
   </c:when>
   <c:otherwise>
   <td align="center" class="smaller">
   </c:otherwise>
   </c:choose>
   ${groupMonthForm.weekdayNames[day]}
   </td>
</c:forEach>
</tr>

<c:forEach items="${groupMonthForm.users}" var="user">
<tr>
  <td nowrap class="rowheaderu">&nbsp;${user.name}&nbsp;</td>
  <c:forEach begin="1" end="${groupMonthForm.length}" var="day">
   
   <c:if test="${not empty user.tooltip[day]}" var="hasEvents">
    <c:if test="${not empty user.eventId[day]}" var="hasEventId">
      <c:url value="editEvent.do" var="editEvent"> 
        <c:param name="from" value="group"/>
     <c:param name="inputYear" value="${groupMonthForm.year}"/>     
     <c:param name="inputMonth" value="${groupMonthForm.month}"/>
     <c:param name="inputDay" value="${day}"/>
        <c:param name="id" value="${user.eventId[day]}"/>
	</c:url>   
    </c:if>
    <td class="b" align="center"><c:if test="${hasEventId}"><a href="${editEvent}"></c:if><img src='<c:url value="/eventPic.do?d=${day}&u=${user.id}&w=${width}&h=${height}" />' alt="" width="${width}" height="${height}" border="0" onmouseover="return overlib('${user.tooltip[day]}', LEFT, FGCOLOR, '#EEEEEE', WIDTH, 80, CAPTION, '${groupMonthForm.daytitle[day]}');" onmouseout="return nd();"><c:if test="${hasEventId}"></a></c:if></td>    
   </c:if>
   <c:if test="${not hasEvents}">
   <td class="b" align="center"><img src="<c:url value="/images/x.gif" />" width="${width}" height="${height}" alt="" border="0"></td>
   </c:if>
   
  </c:forEach>
</tr>
</c:forEach>

</table>
<p>&nbsp;</p>


<misc:initSelectOptions id="combinedGroupOptions" name="combinedGroupOptions" scope="session" />
<html:form action="/groupMonth.do" method="post">
<input type="hidden" name="inputMonth" value="${groupMonthForm.month}">
<input type="hidden" name="inputYear" value="${groupMonthForm.year}">
<bean:message key="group"/>:&nbsp;
   <html:select property="groupId">
     <html:optionsCollection name="combinedGroupOptions" property="labelValue"/>   
   </html:select>
   <input type="submit" name="show" value="<bean:message key="common.show"/>" />
</html:form> 

</body>
</html>
