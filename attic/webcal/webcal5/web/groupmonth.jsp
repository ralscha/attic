<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
  
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  <misc:popupCalendarJs />   
  </head>
  <body>

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />                        
<misc:initSelectOptions id="groupOption" name="groupUserAccessOptions" scope="session" />    

<html:form action="/groupMonth" focus="inputMonth">

  <forms:form type="search" formid="frmGroupMonthSearch">
    <forms:row join="true"> 
      <c:if test="${groupOption.size > 1}">
      <forms:select property="groupId" onchange="submit()">
        <base:options name="groupOption"/>
      </forms:select>
      </c:if>     
      
      <forms:html>
      
      <input id='btnDecreaseMonthHidden' name='btnDecreaseMonth' type='HIDDEN' />
      <img alt="" style="margin-left: 25px;"onClick="document.getElementById('btnDecreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	
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
        		<img alt="" style="margin-left: 35px;"onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
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

<table width="100%" cellspacing="0" cellpadding="0" class="border">
<tr>
   <td rowspan="3" align="center" class="rowheader">
   ${GroupMonthForm.monthName}
   <br/>
   ${GroupMonthForm.year}
   </td>
<c:forEach items="${GroupMonthForm.weekNos}" var="weekNo" varStatus="stat">
   <td align="center" class="week" colspan="${GroupMonthForm.weekColspan[stat.index]}">${weekNo}</td>
</c:forEach>
</tr>
<tr>
<c:forEach begin="1" end="${GroupMonthForm.length}" var="day">
   <c:choose>
   <c:when test="${GroupMonthForm.today == day}">
   <td align="center" class="smalltoday">
   </c:when>
   <c:when test="${not empty GroupMonthForm.holidays[day]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${GroupMonthForm.holidays[day]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallholiday">
   </c:when>
   <c:when test="${not empty GroupMonthForm.weekends[day]}">
   <td align="center" class="smallweekend">
   </c:when>   
   <c:otherwise>
   <td align="center" class="small">
   </c:otherwise>
   </c:choose>
   
   <c:if test="${GroupMonthForm.readOnly}">
     ${day}
   </c:if>
   <c:if test="${not GroupMonthForm.readOnly}">
     <c:url value="eventEdit.do" var="createNewEvent"> 
       <c:param name="from" value="group"/>
       <c:param name="inputYear" value="${GroupMonthForm.year}"/>     
       <c:param name="inputMonth" value="${GroupMonthForm.month}"/>
       <c:param name="inputDay" value="${day}"/>
	   </c:url>   
     <a href="${createNewEvent}">${day}</a>
   </c:if>
   </td>
</c:forEach>
</tr>

<tr>
<c:forEach begin="1" end="${GroupMonthForm.length}" var="day">
   <c:choose>
   <c:when test="${GroupMonthForm.today == day}">
   <td align="center" class="smallertoday">
   </c:when>
   <c:when test="${not empty GroupMonthForm.holidays[day]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${GroupMonthForm.holidays[day]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallerholiday">
   </c:when>
   <c:when test="${not empty GroupMonthForm.weekends[day]}">
   <td align="center" class="smallerweekend">
   </c:when>
   <c:otherwise>
   <td align="center" class="smaller">
   </c:otherwise>
   </c:choose>
   ${GroupMonthForm.weekdayNames[day]}
   </td>
</c:forEach>
</tr>

<c:forEach items="${GroupMonthForm.users}" var="user">
<tr>
  <td nowrap class="rowheaderu">&nbsp;${user.name}&nbsp;</td>
  <c:forEach begin="1" end="${GroupMonthForm.length}" var="day">
         
   <c:if test="${not empty user.tooltip[day]}" var="hasEvents">
    <c:if test="${not empty user.eventId[day]}" var="hasEventId">
      <c:url value="eventEdit.do" var="editEvent"> 
        <c:param name="from" value="group"/>
        <c:param name="inputYear" value="${GroupMonthForm.year}"/>     
        <c:param name="inputMonth" value="${GroupMonthForm.month}"/>
        <c:param name="inputDay" value="${day}"/>
        <c:param name="id" value="${user.eventId[day]}"/>
	   </c:url>      
    </c:if>
    <c:if test="${user.multipleEvents[day]}" var="hasMultipleEvents">
      <c:url value="eventList.do" var="listEvent"> 
        <c:param name="value(year)" value="${GroupMonthForm.year}"/>     
        <c:param name="value(month)" value="${GroupMonthForm.month}"/>
        <c:param name="value(day)" value="${day}"/>
	   </c:url>      
    </c:if> 
    <c:if test="${empty user.picFileName[day]}">   
    <td class="b" align="center"><c:if test="${hasEventId}"><a href="${editEvent}"></c:if><c:if test="${hasMultipleEvents}"><a href="${listEvent}"></c:if><img src='<c:url value="/eventPic.do?d=${day}&u=${user.id}&w=${width}&h=${height}" />' alt="" width="${width}" height="${height}" border="0" onmouseover="return overlib('${user.tooltip[day]}', <c:if test="${day < 15}">RIGHT</c:if><c:if test="${day >= 15}">LEFT</c:if>, FGCOLOR, '#EEEEEE', WIDTH, ${user.tooltipWidth[day]}, CAPTION, '${GroupMonthForm.daytitle[day]}');" onmouseout="return nd();"><c:if test="${hasEventId or hasMultipleEvents}"></a></c:if></td>
    </c:if>
    <c:if test="${not empty user.picFileName[day]}">
    <td class="b" align="center"><c:if test="${hasEventId}"><a href="${editEvent}"></c:if><c:if test="${hasMultipleEvents}"><a href="${listEvent}"></c:if><img src='<c:url value="/cp/${user.picFileName[day]}" />' alt="" width="${width}" height="${height}" border="0" onmouseover="return overlib('${user.tooltip[day]}', <c:if test="${day < 15}">RIGHT</c:if><c:if test="${day >= 15}">LEFT</c:if>, FGCOLOR, '#EEEEEE', WIDTH, ${user.tooltipWidth[day]}, CAPTION, '${GroupMonthForm.daytitle[day]}');" onmouseout="return nd();"><c:if test="${hasEventId or hasMultipleEvents}"></a></c:if></td>
    </c:if>
   </c:if>   
   <c:if test="${not hasEvents}">
   <td class="b" align="center"><img src="<c:url value="/images/x.gif" />" width="${width}" height="${height}" alt="" border="0"></td>
   </c:if>
   
  </c:forEach>
</tr>
</c:forEach>

</table>
<c:if test="${GroupMonthForm.showQuickAdd}">
<p>
<misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="request" />
<html:form action="/eventEdit" focus="subject">
<input type="hidden" name="quick" value="true">
<input type="hidden" name="from" value="group">
<input type="hidden" name="importance" value="N">
<input type="hidden" name="sensitivity" value="P">
<input type="hidden" name="inputYear" value="${GroupMonthForm.year}">
<input type="hidden" name="inputMonth" value="${GroupMonthForm.month}">
<forms:form type="edit" caption="event.quickadd" formid="frmEvent" noframe="false">
   <forms:html>
     <table><tr><td>
     <span class="formtitle"><bean:message key="event.subject"/>:</span>&nbsp;<ctrl:text property="subject" size="20" maxlength="255"/>&nbsp;&nbsp;&nbsp;&nbsp;
     <span class="formtitle"><bean:message key="event.start"/>:</span>&nbsp;<ctrl:text styleId="start" property="start" size="16" maxlength="16"/>
     <img src="images/cal.gif" alt="" name="selectStart" id="selectStart" width="16" height="16" border="0">&nbsp;&nbsp;&nbsp;&nbsp;
     <span class="formtitle"><bean:message key="event.end"/>:</span>&nbsp;<ctrl:text styleId="end" property="end" size="16" maxlength="16"/>		   
     <img src="images/cal.gif" alt="" name="selectEnd" id="selectEnd" width="16" height="16" border="0">&nbsp;&nbsp;&nbsp;&nbsp;
     <span class="formtitle"><bean:message key="category"/>:</span>&nbsp; 
     <ctrl:select property="categoryId">		    
       <base:options empty="empty" name="categoryOption"/>
     </ctrl:select>
     &nbsp;&nbsp;&nbsp;&nbsp; 
     </td><td>      
     <ctrl:button base="buttons.src" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>		
     </td></tr></table>
   </forms:html>
</forms:form>
</html:form>
<misc:popupCalendar element="start" trigger="selectStart" showOthers="true" showsTime="true"/>
<misc:popupCalendar element="end" trigger="selectEnd" showOthers="true" showsTime="true"/>
</c:if>
</body>
</html>
