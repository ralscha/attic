<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  </head>
  <body>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	       
  <misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="session" />
  <misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />                        
          

  <html:form action="/eventList" focus="value(subject)">
	<forms:form type="search" formid="frmListEvent">

    <forms:html>
    <table>
      <tr>
        <td><bean:message key="event.subject"/></td>
        <td><bean:message key="category"/></td>
        <td><bean:message key="calendar.day"/></td>
        <td><bean:message key="time.month.singular"/></td>
        <td style="padding-left: 35px;"><bean:message key="time.year.singular"/></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><ctrl:text property="value(subject)" size="20" maxlength="255"/></td>
        <td>
    			<ctrl:select property="value(categoryId)">
            <base:options empty="empty" name="categoryOption"/>
          </ctrl:select>  
        </td>        
        <td><ctrl:text property="value(day)" size="4" maxlength="2"/></td>
        <td>
        
         <input id='btnDecreaseMonthHidden' name='btnDecreaseMonth' type='HIDDEN' />
      <img onClick="document.getElementById('btnDecreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	
    			<ctrl:select property="value(month)">
            <base:options empty="empty" name="monthOption"/>
          </ctrl:select>
          
           <input id='btnIncreaseMonthHidden' name='btnIncreaseMonth' type='HIDDEN' />
     <img alt="" onClick="document.getElementById('btnIncreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	 
        </td>
        <td> <input id='btnDecreaseYearHidden' name='btnDecreaseYear' type='HIDDEN' />
      <img alt="" style="margin-left: 35px;"onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
       <ctrl:text size="4" maxlength="4" property="value(year)" onchange="submit()"/>   
  		  <input id='btnIncreaseYearHidden' name='btnIncreaseYear' type='HIDDEN' />
        <img alt="" onClick="document.getElementById('btnIncreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">		
       </td>
	  	
        <td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
      </tr>
    </table>
    </forms:html>       
      
	</forms:form>
	</html:form>          
	
	<p></p>
	
	<ctrl:list id="eventlist" 
		   action="/eventList" 
			 name="listControl" 
			 scope="session"
			 title="event.events" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="event.subject" property="subject" width="225" maxlength="50" sortable="true"/>
	  <ctrl:columntext title="category" property="category" width="100" maxlength="30" sortable="true"/>    
    <ctrl:columntext title="event.start" property="start" width="100" sortable="true" converter="ch.ess.cal.service.EventListDateObjectConverter"/>    
    <ctrl:columntext title="event.end" property="end" width="100" sortable="true" converter="ch.ess.cal.service.EventListDateObjectConverter"/>    
    
    <ctrl:columnhtml width="5" title="" id="event">
      &nbsp;&nbsp;
      <c:if test="${event.map.reminder}">      
      <img align='absmiddle' vspace='0' border='0' src="images/reminder.gif" onmouseover="return overlib('${event.map.reminderTooltip}', FGCOLOR, '#EEEEEE', WIDTH, 350, CAPTION, '<bean:message key="event.reminder"/>');" onmouseout="return nd();" alt="" width="20" height="12" border="0">      
      </c:if>
      <c:if test="${not event.map.reminder}">
      <img src="images/x.gif" alt="" width="20" height="12" border="0">
      </c:if>
      &nbsp;&nbsp;
  	</ctrl:columnhtml>
    
  	<ctrl:columnhtml width="5" title="" id="event">
      &nbsp;&nbsp;&nbsp;
      <c:if test="${event.map.recurrence}">
      <img align='absmiddle' vspace='0' border='0' src="images/repeat.gif" onmouseover="return overlib('${event.map.recurrenceTooltip}', FGCOLOR, '#EEEEEE', WIDTH, 200, CAPTION, '<bean:message key="event.recurrence"/>');" onmouseout="return nd();" alt="" width="13" height="12" border="0">      
      </c:if>
      <c:if test="${not event.map.recurrence}">
      <img src="images/x.gif" alt="" width="13" height="12" border="0">
      </c:if>
      &nbsp;&nbsp;&nbsp;      
  	</ctrl:columnhtml>
    <ctrl:columngroup title="common.action" align="center">    
  	<ctrl:columnedit tooltip="common.edit"/>    
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.deletesubject}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="event.delete"/>
</body>
</html>