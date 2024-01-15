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
    <td width="50%" align="left">
  	<c:url value="yearView.do" var="backYearUrl">
  	  <c:param name="inputYear" value="${yearViewForm.monthBean[0].year-1}"/>
  	</c:url>
  	<a href="${backYearUrl}">${yearViewForm.monthBean[0].year-1}</a>
    </td>
    <td align="center">
    <span class="headerselected">${yearViewForm.monthBean[0].year}</span>
    </td>    
    <td width="50%" align="right">
  	<c:url value="yearView.do" var="nextYearUrl">
  	  <c:param name="inputYear" value="${yearViewForm.monthBean[0].year+1}"/>
  	</c:url>
  	<a href="${nextYearUrl}">${yearViewForm.monthBean[0].year+1}</a>
    </td>  
  </tr>
  </table>
  
<br>
  
<table width="100%">
  <c:forEach items="0,3,6,9" var="rowstat">
  <tr>
    <c:forEach begin="0" end="2" var="colstat">
    <c:set value="${rowstat + colstat}" var="month"/>
    <td valign="top" width="33%">
    
<!--  START -->
<table width="100%" class="view">
  <thead>
    <tr>
      <th colspan="${yearViewForm.monthBean[month].weekLength}" align="center" class="month">${yearViewForm.monthBean[month].monthName} ${yearViewForm.monthBean[month].year}</th>
    </tr> 
    <tr>      
      <th>&nbsp;</th>
      <c:forEach items="${yearViewForm.monthBean[month].weekdayNames}" var="weekday">
      <th width="14%">${weekday}</th>
      </c:forEach>
    </tr>
  </thead>            
  
  <c:forEach items="${yearViewForm.monthBean[month].weekList}" var="week" varStatus="stat">
  <tbody>
    <tr>  
      <td class="weekno" valign="middle" align="center">${yearViewForm.monthBean[month].weekNoList[stat.index]}</td>
      <c:forEach items="${week}" var="day">
	   <td height="40" valign="top">
		  <table width="100%" cellpadding="0" cellspacing="0">
        <tr>        
         <c:choose>         
         <c:when test="${yearViewForm.monthBean[month].today == day && empty yearViewForm.monthBean[month].events[day]}">
         <td class="daytoday">
         </c:when>
         <c:when test="${yearViewForm.monthBean[month].today == day && not empty yearViewForm.monthBean[month].events[day]}">
         <td class="daytodayevent">
         </c:when>         
         <c:when test="${not empty yearViewForm.monthBean[month].holidays[day] && empty yearViewForm.monthBean[month].events[day]}">
         <td class="dayholiday" onmouseover="return overlib('${yearViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${not empty yearViewForm.monthBean[month].holidays[day] && not empty yearViewForm.monthBean[month].events[day]}">
         <td class="dayholidayevent" onmouseover="return overlib('${yearViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${yearViewForm.monthBean[month].weekends[day] && empty yearViewForm.monthBean[month].events[day]}">
         <td class="dayweekend">
         </c:when>
         <c:when test="${yearViewForm.monthBean[month].weekends[day] && not empty yearViewForm.monthBean[month].events[day]}">
         <td class="dayweekendevent">
         </c:when>                  
         
         <c:otherwise>
           <c:if test="${empty yearViewForm.monthBean[month].events[day]}" var="emptyEvents">
             <td class="day">           
           </c:if>
           <c:if test="${not emptyEvents}">
             <td class="daydayevent">           
           </c:if>
         </c:otherwise>
         </c:choose>                
	      <c:if test="${day != 0}">           
           <c:url value="editEvent.do" var="createNewEvent"> 
             <c:param name="from" value="year"/>
             <c:param name="inputYear" value="${yearViewForm.monthBean[month].year}"/>     
             <c:param name="inputMonth" value="${yearViewForm.monthBean[month].month}"/>
             <c:param name="inputDay" value="${day}"/>
	        </c:url>
           <a href="${createNewEvent}">${day}</a>
         </c:if>
		   </td>
		  </tr>
        <tr>
        <td class="dayevent" align="center">
        <c:forEach items="${yearViewForm.monthBean[month].events[day]}" var="event">
          <c:url value="editEvent.do" var="editEvent"> 
             <c:param name="from" value="year"/>
             <c:param name="inputYear" value="${yearViewForm.monthBean[month].year}"/>     
             <c:param name="inputMonth" value="${yearViewForm.monthBean[month].month}"/>
             <c:param name="inputDay" value="${day}"/>
             <c:param name="id" value="${event.eventId}"/>     
	       </c:url>        
          <a href="${editEvent}"><img onmouseover="return overlib('${event.tooltip}', LEFT, FGCOLOR, '#EEEEEE', WIDTH, 80, CAPTION, '${yearViewForm.monthBean[month].daytitle[day]}');" onmouseout="return nd();" src='<c:url value="/pic.do?w=10&h=10&c=${event.colour}" />' alt="" width="10" height="10" border="0"></a>&nbsp; 
        </c:forEach>
        </td>
        </tr>
        </table>
		</td>
    </c:forEach>
  </tr>
  </tbody>
  </c:forEach>
  
  
</table>

<!-- END -->    
    
    
    
    </td>
    </c:forEach>
  </tr>
  </c:forEach>
</table>  
  


</body>
</html>
