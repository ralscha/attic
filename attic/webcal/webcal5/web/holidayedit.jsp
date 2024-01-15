<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
	    
  <misc:initSelectOptions id="weekDayOption" name="weekdayOptions" scope="session" />	
  <misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />	  	  	  	  	
  	
  <html:form action="/holidayEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <html:hidden property="builtin"/>

	<forms:form type="edit" caption="holidayedit.title" formid="frmHoliday" width="450">
     
    <logic:iterate id="entry" indexId="ix" name="HolidayForm" property="entry" type="ch.ess.base.web.NameEntry">
      <c:set var="label">#<bean:message key="holiday.name" /> (${entry.language})</c:set>

      <html:hidden name="entry" indexed="true" property="locale"/>
      <forms:text required="true" label="${label}" size="40" maxlength="255" property="entry[${ix}].name"/>
    </logic:iterate>	    
    
    
    <c:if test="${empty HolidayForm.builtin}">        
    <forms:text label="holiday.dayOfMonth" property="dayOfMonth" required="false" size="5" maxlength="2"/>		    		  						        
    
    <forms:select  label="calendar.month" property="monthNo" required="false">
      <base:options empty="" name="monthOption"/>
    </forms:select>		
        
    <forms:text label="holiday.fromYear" property="fromYear" required="false" size="5" maxlength="4"/>		    		  						        
    <forms:text label="holiday.toYear" property="toYear" required="false" size="5" maxlength="4"/>		    		  						        
    </c:if>
    
    <forms:checkbox label="holiday.active" property="active" required="false" />    
     	  						
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" name="btnSave" default="true" src="btnSave1.gif"  title="button.title.save"/>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	

</body>
</html>