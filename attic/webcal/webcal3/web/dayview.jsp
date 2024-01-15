<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
  
	<script language="JavaScript" src="<c:url value='/scripts/overlib.js'/>" type="text/javascript"></script>    
  </head>
  <body>

  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
  
  <table width="100%">
  <tr>
    <td width="50%" align="left">
  	<c:url value="yearView.do" var="backYearUrl">
  	  <c:param name="inputYear" value="${dayViewForm.monthBean[0].year-1}"/>
  	</c:url>
  	<a href="${backYearUrl}">${dayViewForm.monthBean[0].year-1}</a>
    </td>
    <td align="center">
    <b>${dayViewForm.monthBean[0].year}</b>
    </td>    
    <td width="50%" align="right">
  	<c:url value="yearView.do" var="nextYearUrl">
  	  <c:param name="inputYear" value="${dayViewForm.monthBean[0].year+1}"/>
  	</c:url>
  	<a href="${nextYearUrl}">${dayViewForm.monthBean[0].year+1}</a>
    </td>  
  </tr>
  </table>
  
<br>
  
<table width="100%">
  <tr>
    <td valign="top" align"left" width="70%">
      <table>
        <tr><td>${dayViewForm.dayString}</td></tr>
        <c:forEach items="${dayViewForm.allDayEvents}" var="event">
        <tr>
          <td>
          <img src='<c:url value="/pic.do?w=10&h=10&c=${event.colour}" />' alt="" width="10" height="10" border="0">&nbsp;
          <c:if test="${not empty event.time}">
          ${event.time}&nbsp;
          </c:if>          
          ${event.subject}&nbsp;
          <c:if test="${not empty event.location}">
          ${event.location}&nbsp;
          </c:if>
          (${event.category})
          <c:if test="${not empty event.reminder}">      
             <img align='absmiddle' vspace='0' border='0' src="images/reminder.gif" onmouseover="return overlib('${event.reminder}', FGCOLOR, '#EEEEEE', WIDTH, 350, CAPTION, '<bean:message key="event.reminder"/>');" onmouseout="return nd();" alt="" width="20" height="12" border="0">      
          </c:if>
          <c:if test="${not empty event.recurrence}">
            <img align='absmiddle' vspace='0' border='0' src="images/repeat.gif" onmouseover="return overlib('${event.recurrence}', FGCOLOR, '#EEEEEE', WIDTH, 200, CAPTION, '<bean:message key="event.recurrence"/>');" onmouseout="return nd();" alt="" width="13" height="12" border="0">      
          </c:if>
          
          </td>  
        </tr>


        </c:forEach>                        
      </table>
    </td>
    
    <td width="30%">
<table width="100%">
  <c:forEach begin="0" end="2" var="month">
  <tr>
    <td valign="top">
    
<!--  START -->
<table width="100%" class="view">
  <thead>
    <tr>
      <th colspan="${dayViewForm.monthBean[month].weekLength}" align="center" class="month">${dayViewForm.monthBean[month].monthName} ${dayViewForm.monthBean[month].year}</th>
    </tr> 
    <tr>      
      <th>&nbsp;</th>
      <c:forEach items="${dayViewForm.monthBean[month].weekdayNames}" var="weekday">
      <th width="14%">${weekday}</th>
      </c:forEach>
    </tr>
  </thead>            
  
  <c:forEach items="${dayViewForm.monthBean[month].weekList}" var="week" varStatus="stat">
  <tbody>
    <tr>  
      <td class="weekno" valign="middle" align="center">${dayViewForm.monthBean[month].weekNoList[stat.index]}</td>
      <c:forEach items="${week}" var="day">
	   <td valign="top">
		  <table width="100%" cellpadding="0" cellspacing="0">
        <tr>        
         <c:choose>         
         <c:when test="${dayViewForm.monthBean[month].today == day && empty dayViewForm.monthBean[month].events[day]}">
         <td class="daytoday" align="right">
         </c:when>
         <c:when test="${dayViewForm.monthBean[month].today == day && not empty dayViewForm.monthBean[month].events[day]}">
         <td class="daytodayevent" align="right">
         </c:when>         
         <c:when test="${not empty dayViewForm.monthBean[month].holidays[day] && empty dayViewForm.monthBean[month].events[day]}">
         <td class="dayholiday" align="right" onmouseover="return overlib('${dayViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${not empty dayViewForm.monthBean[month].holidays[day] && not empty dayViewForm.monthBean[month].events[day]}">
         <td class="dayholidayevent" align="right" onmouseover="return overlib('${dayViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${dayViewForm.monthBean[month].weekends[day] && empty dayViewForm.monthBean[month].events[day]}">
         <td class="dayweekend" align="right">
         </c:when>
         <c:when test="${dayViewForm.monthBean[month].weekends[day] && not empty dayViewForm.monthBean[month].events[day]}">
         <td class="dayweekendevent" align="right">
         </c:when>                  
         
         <c:otherwise>
           <c:if test="${empty dayViewForm.monthBean[month].events[day]}" var="emptyEvents">
             <td class="day" align="right">           
           </c:if>
           <c:if test="${not emptyEvents}">
             <td class="daydayevent" align="right">           
           </c:if>
         </c:otherwise>
         </c:choose>                
	      <c:if test="${day != 0}">           
           <c:url value="dayView.do" var="dayViewUrl"> 
             <c:param name="inputYear" value="${dayViewForm.monthBean[month].year}"/>     
             <c:param name="inputMonth" value="${dayViewForm.monthBean[month].month}"/>
             <c:param name="inputDate" value="${day}"/>
	        </c:url>
           <a href="${dayViewUrl}">${day}</a>           
         </c:if>
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
  </tr>
  <tr><td>&nbsp;</td></tr>
  </c:forEach>
</table>  
    
    </td>
    
  </tr>
</table>  
  
  


</body>
</html>
