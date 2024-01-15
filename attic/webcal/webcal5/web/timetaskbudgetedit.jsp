<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title>
    <misc:popupCalendarJs />
  </head>
  <body>
	
	<html:form action="/timeTaskBudgetEdit" focus="timeTaskId">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="timetaskbudgetedit.title" formid="frmTimeTaskBudget">
    	  
    <forms:description label="time.timeTask" valign="top" join="false">
      <misc:initSelectOptions id="timeCustomerProjectTaskOptions" name="timeCustomerProjectTaskOptions" scope="request" exposeCollection="true" />
      <prv:comboselect name="timeCustomerProjectTaskOptions" 
        metadata="idCustomer,customer,idProject,project,idTask,task">
        <prv:select property="timeCustomerId" doMatches="true" />
        <prv:select property="timeProjectId" doMatches="true" />
        <prv:select property="timeTaskId" doMatches="true" />            
      </prv:comboselect>
      <img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>
    </forms:description>  
	  
    <forms:html label="timeTaskBudget.date">
		  <html:text property="date" styleId="date" size="10" maxlength="10"/><img src="images/cal.gif" alt="" name="selectDate" id="selectDate" width="16" height="16" border="0"><img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>
	  </forms:html>	
	  
	  
	  <forms:text label="timeTaskBudget.comment" property="comment" required="false" size="60" maxlength="255"/>
	  <forms:text label="timeTaskBudget.amount" property="amount" required="true" size="10" maxlength="10"/>			  
                    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${TimeTaskBudgetForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:popupCalendar element="date" trigger="selectDate" showOthers="true"/>
  
  
  
  <misc:confirm key="timeTaskBudget.delete"/>
</body>
</html>
