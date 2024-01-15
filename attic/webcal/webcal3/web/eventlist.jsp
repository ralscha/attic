<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
	<script language="JavaScript" src="<c:url value='/scripts/overlib.js'/>" type="text/javascript"></script>    
  </head>
  <body>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	     
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="event.events"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="event.headline"/>
    </menu:crumbs>    
  <br>
  
  <misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="session" />
  <misc:initSelectOptions id="yearOption" name="yearOptions" scope="session" />		  	  	  	  	  		
  <misc:initSelectOptions id="monthOption" type="ch.ess.cal.web.holiday.MonthOptions" scope="session" />                        
                        
	<html:form action="/listEvent.do"  focus="value(subject)">
		<forms:form type="search" formid="frmListEvent">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>
			  
			  <tr>		  		    
		      <td class='searchfl'><bean:message key="event.subject"/>:</td>			    
 			    <td class='searchfl'><bean:message key="category"/>:</td>
		      <td class='searchfl'><bean:message key="time.month"/>:</td>
		      <td class='searchfl'><bean:message key="time.year"/>:</td>
 				  <td class='fb'></td>
	      </tr>      
			  <tr>
 				  <td class='fd'><html:text property="value(subject)" size="20" maxlength="255"/></td>
 				  <td class='fd'>
				   <html:select property="value(categoryId)">
             <html:option value=" ">&nbsp;</html:option>
             <html:optionsCollection name="categoryOption" property="labelValue"/>   
           </html:select>
				  </td>		    
				  <td class='fd'>
				   <html:select property="value(month)">
             <html:option value=" ">&nbsp;</html:option>
             <html:optionsCollection name="monthOption" property="labelValue"/>   
           </html:select>          
          </td>		   
				  <td class='fd'>
				   <html:select property="value(year)">
             <html:option value=" ">&nbsp;</html:option>
             <html:optionsCollection name="yearOption" property="labelValue"/>   
           </html:select>          
          </td>		                     
			 <td class='fb'>
			 <ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
          </td>
			</tr>
			  
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="eventlist" 
		   action="listEvent.do" 
			 name="events" 
			 scope="session"
			 title="event.events" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="event.subject" property="subject" width="225" maxlength="50" sortable="true"/>
	  <ctrl:columntext title="category" property="category" width="100" maxlength="30" sortable="true"/>    
    <ctrl:columntext title="event.start" property="start" width="100" sortable="true"/>    
    <ctrl:columntext title="event.end" property="end" width="100" sortable="true"/>    
    
    <ctrl:columnhtml title="" id="event">
      &nbsp;&nbsp;
      <c:if test="${event.map.reminder}">      
      <img align='absmiddle' vspace='0' border='0' src="images/reminder.gif" onmouseover="return overlib('${event.map.reminderTooltip}', FGCOLOR, '#EEEEEE', WIDTH, 350, CAPTION, '<bean:message key="event.reminder"/>');" onmouseout="return nd();" alt="" width="20" height="12" border="0">      
      </c:if>
      <c:if test="${not event.map.reminder}">
      <img src="images/x.gif" alt="" width="20" height="12" border="0">
      </c:if>
      &nbsp;&nbsp;
  	</ctrl:columnhtml>
    
  	<ctrl:columnhtml title="" id="event">
      &nbsp;&nbsp;&nbsp;
      <c:if test="${event.map.recurrence}">
      <img align='absmiddle' vspace='0' border='0' src="images/repeat.gif" onmouseover="return overlib('${event.map.recurrenceTooltip}', FGCOLOR, '#EEEEEE', WIDTH, 200, CAPTION, '<bean:message key="event.recurrence"/>');" onmouseout="return nd();" alt="" width="13" height="12" border="0">      
      </c:if>
      <c:if test="${not event.map.recurrence}">
      <img src="images/x.gif" alt="" width="13" height="12" border="0">
      </c:if>
      &nbsp;&nbsp;&nbsp;      
  	</ctrl:columnhtml>
        
  	<ctrl:columnedit tooltip="common.edit"/>    
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.subject}');"/>		
	</ctrl:list>
	
  <misc:confirm key="event.delete"/>
</body>
</html>