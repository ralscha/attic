<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
      
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  </head>
  <body>

  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
  <misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />                        
  
<html:form action="/monthView" focus="inputMonth">

  <forms:form type="search" formid="frmGroupMonthSearch">
    <forms:row join="true">
   <forms:html>
      <input id='btnDecreaseMonthHidden' name='btnDecreaseMonth' type='HIDDEN' />
      <img onClick="document.getElementById('btnDecreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	
      </forms:html>    
	<forms:select property="inputMonth" onchange="submit()">
        <base:options name="monthOption"/>
      </forms:select>   
      <forms:html>
      <input id='btnIncreaseMonthHidden' name='btnIncreaseMonth' type='HIDDEN' />
     <img alt="" onClick="document.getElementById('btnIncreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	   		 </forms:html>
	   		 <forms:html>	
      <input id='btnDecreaseYearHidden' name='btnDecreaseYear' type='HIDDEN' />
        		<img style="margin-left: 35px;" onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
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


  <table width="100%" class="view">

  <thead>
  <tr>
    <th colspan="${MonthViewForm.monthBean.weekLength}" align="center" class="month">${MonthViewForm.monthBean.monthName} ${MonthViewForm.monthBean.year}</th>
  </tr> 
  <tr>      
    <th>&nbsp;</th>
    <c:forEach items="${MonthViewForm.monthBean.weekdayNames}" var="weekday">
      <th width="14%">${weekday}</th>
    </c:forEach>

  </tr>
 </thead>      
      
  <c:forEach items="${MonthViewForm.monthBean.weekList}" var="week" varStatus="stat">
  <tbody>
  <tr>  
    <td class="weekno" valign="middle" align="center">${MonthViewForm.monthBean.weekNoList[stat.index]}</td>
    <c:forEach items="${week}" var="day">
	   <td height="80" valign="top">
		  <table width="100%" cellpadding="0" cellspacing="0">
        <tr>        
         <c:choose>         
         <c:when test="${MonthViewForm.monthBean.today == day && empty MonthViewForm.monthBean.events[day]}">
         <td class="daytoday">
         </c:when>
         <c:when test="${MonthViewForm.monthBean.today == day && not empty MonthViewForm.monthBean.events[day]}">
         <td class="daytodayevent">
         </c:when>                 
         <c:when test="${not empty MonthViewForm.monthBean.holidays[day] && empty MonthViewForm.monthBean.events[day]}">
         <td class="dayholiday">
         </c:when>
         <c:when test="${not empty MonthViewForm.monthBean.holidays[day] && not empty MonthViewForm.monthBean.events[day]}">
         <td class="dayholidayevent">
         </c:when>
         <c:when test="${MonthViewForm.monthBean.weekends[day] && empty MonthViewForm.monthBean.events[day]}">
         <td class="dayweekend">
         </c:when>
         <c:when test="${MonthViewForm.monthBean.weekends[day] && not empty MonthViewForm.monthBean.events[day]}">
         <td class="dayweekendevent">
         </c:when>                  
         
         <c:otherwise>
           <c:if test="${empty MonthViewForm.monthBean.events[day]}" var="emptyEvents">
             <td class="day">           
           </c:if>
           <c:if test="${not emptyEvents}">
             <td class="daydayevent">           
           </c:if>
         </c:otherwise>
         </c:choose>                
	      <c:if test="${day != 0}">
           <c:url value="eventEdit.do" var="createNewEvent"> 
             <c:param name="from" value="month"/>
             <c:param name="inputYear" value="${MonthViewForm.monthBean.year}"/>     
             <c:param name="inputMonth" value="${MonthViewForm.monthBean.month}"/>
             <c:param name="inputDay" value="${day}"/>
	        </c:url>
           <a href="${createNewEvent}">${day}</a>
         </c:if>
           <c:if test="${not empty MonthViewForm.monthBean.holidays[day]}">
           ${MonthViewForm.monthBean.holidays[day]}
           </c:if>         
		   </td>
		  </tr>
        <tr><td class="dayevent">&nbsp;</td></tr>
        <c:forEach items="${MonthViewForm.monthBean.events[day]}" var="event">
        <tr>
          <c:url value="eventEdit.do" var="editEvent"> 
            <c:param name="from" value="month"/>
             <c:param name="inputYear" value="${MonthViewForm.monthBean.year}"/>     
             <c:param name="inputMonth" value="${MonthViewForm.monthBean.month}"/>
             <c:param name="inputDay" value="${day}"/>
            <c:param name="id" value="${event.eventId}"/>     
	       </c:url>        
          <td class="dayevent" onmouseover="return overlib('${event.tooltip}', LEFT, FGCOLOR, '#EEEEEE', WIDTH, 80, CAPTION, '${MonthViewForm.monthBean.daytitle[day]}');" onmouseout="return nd();">&nbsp;<a href="${editEvent}"><img src='<c:url value="/pic.do?w=10&h=10&c=${event.colour}" />' alt="" width="10" height="10" border="0"></a>&nbsp;${event.event}</td>
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
