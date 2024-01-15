<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	
  
  <c:if test="${sessionScope.useragent_version == 'msie'}">
    <style type="text/css">
      div.yearscroll {
        overflow:auto;
        width:100%;
        overflow-y:hidden;
        overflow-x:scroll;  
      }
      
      div.fixeddiv {
        float: left;
        width: 150px;
        padding-right: 1px;  
      }    
    </style>
  </c:if>
  <c:if test="${sessionScope.useragent_version == 'firefox'}">
    <style type="text/css">
      div.yearscroll {
        overflow:auto;
        width:85%;
        overflow-y:hidden;
        overflow-x:scroll;  
      }
      
      div.fixeddiv {
        float: left;
        width: 150px;
        padding-right: 1px;  
      }    
    </style>  
  </c:if>
  
  
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  <misc:popupCalendarJs />   
  </head>
  <body>

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<misc:initSelectOptions id="groupOption" name="groupUserAccessOptions" scope="session" />    

<html:form action="/groupYear" focus="inputYear">

  <forms:form type="search" formid="frmGroupYearSearch">
    <forms:row join="true"> 
      <c:if test="${groupOption.size > 1}">
      <forms:select property="groupId" onchange="submit()">
        <base:options name="groupOption"/>
      </forms:select>
      </c:if>    
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



<div class="fixeddiv">
  <table width="100%" cellspacing="0" cellpadding="0" class="border">
    <tr>
      <td class="rowheadersmall" align="center">${GroupYearForm.year}</td>
    </tr>
    <tr>
      <td class="small">&nbsp;</td>
    </tr>
    <tr>
      <td class="small">&nbsp;</td>
    </tr>
    <c:forEach items="${GroupYearForm.users}" var="user">
    <tr>
      <td nowrap class="small">&nbsp;${user.name}&nbsp;</td>
    </tr>                        
    </c:forEach>
  </table>
</div>
<div class="yearscroll">
<table width="100%" cellspacing="0" cellpadding="0" class="border">
<tr>
  <c:forEach items="${GroupYearForm.monthNames}" var="monthName" varStatus="stat">
     <td align="center" class="month" colspan="${GroupYearForm.monthColspan[stat.index]}">${monthName}</td>
  </c:forEach>   
</tr>
<tr>
  <c:forEach items="${GroupYearForm.weekNos}" var="weekNo" varStatus="stat">
     <td align="center" class="week" colspan="${GroupYearForm.weekColspan[stat.index]}">${weekNo}</td>
  </c:forEach>
</tr>

<tr>
<c:forEach begin="1" end="${GroupYearForm.length}" var="day">
   <c:choose>
   <c:when test="${GroupYearForm.today == day}">
   <td align="center" class="smalltoday">
   </c:when>
   <c:when test="${not empty GroupYearForm.holidays[day]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${GroupYearForm.holidays[day]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallholiday">
   </c:when>
   <c:when test="${not empty GroupYearForm.weekends[day]}">
   <td align="center" class="smallweekend">
   </c:when>   
   <c:otherwise>
   <td align="center" class="small">
   </c:otherwise>
   </c:choose>
   
   <c:if test="${GroupYearForm.readOnly}">
     ${GroupYearForm.monthDays[day]}
   </c:if>
   <c:if test="${not GroupYearForm.readOnly}">
     <c:url value="eventEdit.do" var="createNewEvent"> 
       <c:param name="from" value="groupyear"/>
       <c:param name="inputYear" value="${GroupYearForm.year}"/> 
       <c:param name="inputMonth" value="${GroupYearForm.month[day]}"/>    
       <c:param name="inputDay" value="${GroupYearForm.monthDays[day]}"/>
	   </c:url>   
     <a href="${createNewEvent}">${GroupYearForm.monthDays[day]}</a>
   </c:if>
   </td>
</c:forEach>
</tr>


<c:forEach items="${GroupYearForm.users}" var="user">
<tr>
  <c:forEach begin="1" end="${GroupYearForm.length}" var="day">
         
   <c:if test="${not empty user.tooltip[day]}" var="hasEvents">
    <c:if test="${not empty user.eventId[day]}" var="hasEventId">
      <c:url value="eventEdit.do" var="editEvent"> 
        <c:param name="from" value="groupyear"/>
        <c:param name="inputYear" value="${GroupYearForm.year}"/>     
        <c:param name="inputMonth" value="${GroupYearForm.month[day]}"/>        
        <c:param name="inputDay" value="${GroupYearForm.monthDays[day]}"/>
        <c:param name="id" value="${user.eventId[day]}"/>
	   </c:url>      
    </c:if>
    <c:if test="${user.multipleEvents[day]}" var="hasMultipleEvents">
      <c:url value="eventList.do" var="listEvent"> 
        <c:param name="value(year)" value="${GroupYearForm.year}"/>     
        <c:param name="value(day)" value="${day}"/>
	   </c:url>      
    </c:if>    
    <c:if test="${empty user.picFileName[day]}">
    <td class="b" align="center"><c:if test="${hasEventId}"><a href="${editEvent}"></c:if><c:if test="${hasMultipleEvents}"><a href="${listEvent}"></c:if><img src='<c:url value="/ep.do?d=${day}&u=${user.id}&w=${width}&h=${height}" />' alt="" width="${width}" height="${height}" border="0" onmouseover="return overlib('${user.tooltip[day]}', <c:if test="${day < 15}">RIGHT</c:if><c:if test="${day >= 15}">LEFT</c:if>, FGCOLOR, '#EEEEEE', WIDTH, ${user.tooltipWidth[day]}, CAPTION, '${GroupYearForm.daytitle[day]}');" onmouseout="return nd();"><c:if test="${hasEventId or hasMultipleEvents}"></a></c:if></td>
    </c:if>    
    <c:if test="${not empty user.picFileName[day]}">
    <td class="b" align="center"><c:if test="${hasEventId}"><a href="${editEvent}"></c:if><c:if test="${hasMultipleEvents}"><a href="${listEvent}"></c:if><img src='<c:url value="/cp/${user.picFileName[day]}" />' alt="" width="${width}" height="${height}" border="0" onmouseover="return overlib('${user.tooltip[day]}', <c:if test="${day < 15}">RIGHT</c:if><c:if test="${day >= 15}">LEFT</c:if>, FGCOLOR, '#EEEEEE', WIDTH, ${user.tooltipWidth[day]}, CAPTION, '${GroupYearForm.daytitle[day]}');" onmouseout="return nd();"><c:if test="${hasEventId or hasMultipleEvents}"></a></c:if></td>
    </c:if>    
        
   </c:if>   
   <c:if test="${not hasEvents}">
   <td class="b" align="center"><img src="<c:url value="/images/x.gif" />" width="${width}" height="${height}" alt="" border="0"></td>
   </c:if>
   
  </c:forEach>
</tr>
</c:forEach>

</table>
<p>
</div>


<c:if test="${GroupYearForm.showQuickAdd}">
<p>
<misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="request" />
<html:form action="/eventEdit" focus="subject">
<input type="hidden" name="quick" value="true">
<input type="hidden" name="from" value="groupyear">
<input type="hidden" name="importance" value="N">
<input type="hidden" name="sensitivity" value="P">
<input type="hidden" name="inputYear" value="${GroupYearForm.year}">
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
