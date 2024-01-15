<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title><bean:message key="application.title"/>: <bean:message key="holiday.headline"/></title></head>
  <body>
	
    <menu:crumbs value="crumb2" labellength="40">
	    <menu:crumb	crumbid="crumb1"	action="/listHoliday.do" title="holiday.holidays"/>
	    <menu:crumb	crumbid="crumb2"	title="holiday.headline"/>
    </menu:crumbs>
    <br>
    
  <misc:initSelectOptions id="weekDayOption" name="weekdayOptions" scope="session" />	
  <misc:initSelectOptions id="monthOption" type="ch.ess.cal.web.holiday.MonthOptions" scope="session" />	  	  	  	  	
  	
	<html:form action="/editHoliday">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <html:hidden property="builtin"/>

  <c:set var="label"><bean:message key="holiday.headline" /></c:set>  
	<forms:form type="edit" caption="${label}" formid="frmHoliday" locale="false" width="450">
     
    <logic:iterate id="entry" indexId="ix" name="holidayForm" property="entry" type="ch.ess.cal.web.NameEntry">
      <c:set var="label"><bean:message key="holiday.name" /> (${entry.language})</c:set>

      <html:hidden name="entry" indexed="true" property="locale"/>
      <forms:text required="true" label="${label}" size="40" maxlength="255" property="entry[${ix}].name"/>
    </logic:iterate>	    
    
    
    <c:if test="${empty holidayForm.builtin}">        
    <c:set var="label"><bean:message key="holiday.dayOfMonth" /></c:set>
    <forms:text label="${label}" property="dayOfMonth" required="false" size="5" maxlength="2"/>		    		  						        
    
    <c:set var="label"><bean:message key="time.month" /></c:set>
    <forms:select  label="${label}" property="monthNo" required="false">
      <base:options empty="" name="monthOption"/>
    </forms:select>		
        
    <c:set var="label"><bean:message key="holiday.fromYear" /></c:set>        
    <forms:text label="${label}" property="fromYear" required="false" size="5" maxlength="4"/>		    		  						        

    <c:set var="label"><bean:message key="holiday.toYear" /></c:set>
    <forms:text label="${label}" property="toYear" required="false" size="5" maxlength="4"/>		    		  						        
    </c:if>
    
    <c:set var="label"><bean:message key="holiday.active" /></c:set>
    <forms:checkbox label="${label}" property="active" required="false" />    
     	  						
		<forms:buttonsection join="false"> 
         <c:set var="buttonsbase"><bean:message key="buttons.src" /></c:set>
			<forms:button   base="${buttonsbase}" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="${buttonsbase}" name="btnSave" default="true" src="btnSave1.gif"  title="button.title.save"/>
			<forms:button   base="${buttonsbase}" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	

</body>
</html>
