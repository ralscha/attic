<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
  
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  </head>
  <body>

  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
  
  <html:form action="yearView" focus="inputYear">

  <forms:form type="search" formid="frmYearSearch">
    <forms:row join="true">      
       <forms:html>
      <input id='btnDecreaseYearHidden' name='btnDecreaseYear' type='HIDDEN' />
      <img onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
</forms:html>
       <forms:text size="4" maxlength="4" property="inputYear" onchange="submit()"/>   
<forms:html>
  		  <input id='btnIncreaseYearHidden' name='btnIncreaseYear' type='HIDDEN' />
        <img alt="" onClick="document.getElementById('btnIncreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">		
        </forms:html>
        <forms:html>
      <ctrl:button base="buttons.src" name="btnShow"  src="btnShow1.gif"  title="button.title.show"/>       
    </forms:html>
    </forms:row>    
      
  </forms:form>

</html:form>
  
  
  
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
      <th colspan="${YearViewForm.monthBean[month].weekLength}" align="center" class="month">${YearViewForm.monthBean[month].monthName} ${YearViewForm.monthBean[month].year}</th>
    </tr> 
    <tr>      
      <th>&nbsp;</th>
      <c:forEach items="${YearViewForm.monthBean[month].weekdayNames}" var="weekday">
      <th width="14%">${weekday}</th>
      </c:forEach>
    </tr>
  </thead>            
  
  <c:forEach items="${YearViewForm.monthBean[month].weekList}" var="week" varStatus="stat">
  <tbody>
    <tr>  
      <td class="weekno" valign="middle" align="center">${YearViewForm.monthBean[month].weekNoList[stat.index]}</td>
      <c:forEach items="${week}" var="day">
	   <td height="40" valign="top">
		  <table width="100%" cellpadding="0" cellspacing="0">
        <tr>        
         <c:choose>         
         <c:when test="${YearViewForm.monthBean[month].today == day && empty YearViewForm.monthBean[month].events[day]}">
         <td class="daytoday">
         </c:when>
         <c:when test="${YearViewForm.monthBean[month].today == day && not empty YearViewForm.monthBean[month].events[day]}">
         <td class="daytodayevent">
         </c:when>         
         <c:when test="${not empty YearViewForm.monthBean[month].holidays[day] && empty YearViewForm.monthBean[month].events[day]}">
         <td class="dayholiday" onmouseover="return overlib('${YearViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${not empty YearViewForm.monthBean[month].holidays[day] && not empty YearViewForm.monthBean[month].events[day]}">
         <td class="dayholidayevent" onmouseover="return overlib('${YearViewForm.monthBean[month].holidays[day]}', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();">
         </c:when>
         <c:when test="${YearViewForm.monthBean[month].weekends[day] && empty YearViewForm.monthBean[month].events[day]}">
         <td class="dayweekend">
         </c:when>
         <c:when test="${YearViewForm.monthBean[month].weekends[day] && not empty YearViewForm.monthBean[month].events[day]}">
         <td class="dayweekendevent">
         </c:when>                  
         
         <c:otherwise>
           <c:if test="${empty YearViewForm.monthBean[month].events[day]}" var="emptyEvents">
             <td class="day">           
           </c:if>
           <c:if test="${not emptyEvents}">
             <td class="daydayevent">           
           </c:if>
         </c:otherwise>
         </c:choose>                
	      <c:if test="${day != 0}">                      
           <c:url value="eventEdit.do" var="createNewEvent"> 
             <c:param name="from" value="year"/>
             <c:param name="inputYear" value="${YearViewForm.monthBean[month].year}"/>     
             <c:param name="inputMonth" value="${YearViewForm.monthBean[month].month}"/>
             <c:param name="inputDay" value="${day}"/>
	        </c:url>   
           <a href="${createNewEvent}">${day}</a>           
         </c:if>
		   </td>
		  </tr>
        <tr>
        <td class="dayevent" align="center">
        <c:forEach items="${YearViewForm.monthBean[month].events[day]}" var="event">
          <c:url value="eventEdit.do" var="editEvent"> 
             <c:param name="from" value="year"/>
             <c:param name="inputYear" value="${YearViewForm.monthBean[month].year}"/>     
             <c:param name="inputMonth" value="${YearViewForm.monthBean[month].month}"/>
             <c:param name="inputDay" value="${day}"/>
             <c:param name="id" value="${event.eventId}"/>     
	       </c:url>        
          <a href="${editEvent}"><img onmouseover="return overlib('${event.tooltip}', LEFT, FGCOLOR, '#EEEEEE', WIDTH, 80, CAPTION, '${YearViewForm.monthBean[month].daytitle[day]}');" onmouseout="return nd();" src='<c:url value="/pic.do?w=10&h=10&c=${event.colour}" />' alt="" width="10" height="10" border="0"></a>&nbsp; 
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
