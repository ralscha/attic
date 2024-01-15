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
  	<c:url value="monthView.do" var="backYearUrl">
  	  <c:param name="inputYear" value="${monthViewForm.monthBean.year-1}"/>
  	  <c:param name="inputMonth" value="${monthViewForm.monthBean.month}"/>	  
  	</c:url>
  	<a href="${backYearUrl}">${monthViewForm.monthBean.year-1}</a>
    </td>
    <td align="center">
    <c:forEach items="${monthViewForm.monthNames}" var="month" varStatus="stat">
  
      <c:choose>
  	 <c:when test="${stat.index == monthViewForm.monthBean.month}">
  	    <span class="headerselected">${month}</span>&nbsp;&nbsp;
  	 </c:when>
  	 <c:otherwise>
  		<c:url value="monthView.do" var="monthUrl">
  		  <c:param name="inputYear" value="${monthViewForm.monthBean.year}"/>
  		  <c:param name="inputMonth" value="${stat.index}"/>	  
  		</c:url>
  	    <a href="${monthUrl}">${month}</a>
  	   &nbsp;&nbsp;
  	 </c:otherwise>
  
  	</c:choose>
    </c:forEach>
    </td>
    <td width="8%" align="right">
  	<c:url value="monthView.do" var="nextYearUrl">
  	  <c:param name="inputYear" value="${monthViewForm.monthBean.year+1}"/>
  	  <c:param name="inputMonth" value="${monthViewForm.monthBean.month}"/>	  
  	</c:url>
  	<a href="${nextYearUrl}">${monthViewForm.monthBean.year+1}</a>
    </td>  
  </tr>
  </table>
  
<br>
  <table width="100%" class="view">

  <thead>
  <tr>
    <th colspan="${monthViewForm.monthBean.weekLength}" align="center" class="month">${monthViewForm.monthBean.monthName} ${monthViewForm.monthBean.year}</th>
  </tr> 
  <tr>      
    <th>&nbsp;</th>
    <c:forEach items="${monthViewForm.monthBean.weekdayNames}" var="weekday">
      <th width="14%">${weekday}</th>
    </c:forEach>

  </tr>
 </thead>      
      
  <c:forEach items="${monthViewForm.monthBean.weekList}" var="week" varStatus="stat">
  <tbody>
  <tr>  
    <td class="weekno" valign="middle" align="center">${monthViewForm.monthBean.weekNoList[stat.index]}</td>
    <c:forEach items="${week}" var="day">
	   <td height="80" valign="top">
		  <table width="100%" cellpadding="0" cellspacing="0">
        <tr>        
         <c:choose>         
         <c:when test="${monthViewForm.monthBean.today == day && empty monthViewForm.monthBean.events[day]}">
         <td class="daytoday">
         </c:when>
         <c:when test="${monthViewForm.monthBean.today == day && not empty monthViewForm.monthBean.events[day]}">
         <td class="daytodayevent">
         </c:when>                 
         <c:when test="${not empty monthViewForm.monthBean.holidays[day] && empty monthViewForm.monthBean.events[day]}">
         <td class="dayholiday">
         </c:when>
         <c:when test="${not empty monthViewForm.monthBean.holidays[day] && not empty monthViewForm.monthBean.events[day]}">
         <td class="dayholidayevent">
         </c:when>
         <c:when test="${monthViewForm.monthBean.weekends[day] && empty monthViewForm.monthBean.events[day]}">
         <td class="dayweekend">
         </c:when>
         <c:when test="${monthViewForm.monthBean.weekends[day] && not empty monthViewForm.monthBean.events[day]}">
         <td class="dayweekendevent">
         </c:when>                  
         
         <c:otherwise>
           <c:if test="${empty monthViewForm.monthBean.events[day]}" var="emptyEvents">
             <td class="day">           
           </c:if>
           <c:if test="${not emptyEvents}">
             <td class="daydayevent">           
           </c:if>
         </c:otherwise>
         </c:choose>                
	      <c:if test="${day != 0}">
           <c:url value="editEvent.do" var="createNewEvent"> 
             <c:param name="from" value="month"/>
             <c:param name="inputYear" value="${monthViewForm.monthBean.year}"/>     
             <c:param name="inputMonth" value="${monthViewForm.monthBean.month}"/>
             <c:param name="inputDay" value="${day}"/>
	        </c:url>
           <a href="${createNewEvent}">${day}</a>
         </c:if>
           <c:if test="${not empty monthViewForm.monthBean.holidays[day]}">
           ${monthViewForm.monthBean.holidays[day]}
           </c:if>         
		   </td>
		  </tr>
        <tr><td class="dayevent">&nbsp;</td></tr>
        <c:forEach items="${monthViewForm.monthBean.events[day]}" var="event">
        <tr>
          <c:url value="editEvent.do" var="editEvent"> 
            <c:param name="from" value="month"/>
             <c:param name="inputYear" value="${monthViewForm.monthBean.year}"/>     
             <c:param name="inputMonth" value="${monthViewForm.monthBean.month}"/>
             <c:param name="inputDay" value="${day}"/>
            <c:param name="id" value="${event.eventId}"/>     
	       </c:url>        
          <td class="dayevent" onmouseover="return overlib('${event.tooltip}', LEFT, FGCOLOR, '#EEEEEE', WIDTH, 80, CAPTION, '${monthViewForm.monthBean.daytitle[day]}');" onmouseout="return nd();">&nbsp;<a href="${editEvent}"><img src='<c:url value="/pic.do?w=10&h=10&c=${event.colour}" />' alt="" width="10" height="10" border="0"></a>&nbsp;${event.event}</td>
        </tr>
        </c:forEach>
        
        </table>
		</td>
    </c:forEach>
  </tr>
  </tbody>
  </c:forEach>
  
  
  </table>


</body>
</html>
