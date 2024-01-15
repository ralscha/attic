<%@ include file="include/taglibs.jspf" %>

<html>
  <head><title></title>
    <misc:popupCalendarJs />
    
    <script type='text/javascript' src='dwr/interface/timeHourUpdater.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>   
    
    <script type="text/javascript">
		  function updateTimeHour(values) {
		    DWRUtil.setValues(values);		    
		  }
		  function updateBudget(values) {
		    DWRUtil.setValues(values);
		  }
		  function clearBudget() {
		    DWRUtil.setValue('budget', '');
		    DWRUtil.setValue('budgetOpen', '');
		  }
		  
    </script>   
  </head>
  <body onload="javascript:setVisibility();">
	
    
    
	<html:form action="/timeEdit" focus="taskTimeDate">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  <sec:notGranted permission="$timeadmin">
  <html:hidden property="userId"/>
  </sec:notGranted>

	<forms:form type="edit" caption="timeedit.title" formid="frmTime">
    
    <sec:granted permission="$timeadmin">
    <misc:initSelectOptions id="timeUserOption" name="userOptions" scope="session" permission="time"/>
	  <forms:select label="time.user" property="userId" required="true">
	  	<base:options empty="empty" name="timeUserOption" />
	  </forms:select> 
	  </sec:granted>
	  
    <forms:html label="time.taskTimeDate">
		  <html:text property="taskTimeDate" styleId="taskTimeDate" size="10" maxlength="10"/><img src="images/cal.gif" alt="" name="selectTaskTimeDate" id="selectTaskTimeDate" width="16" height="16" border="0">
	  </forms:html>	
	  
	  <forms:row>
		<forms:description label="time.start" join="false">
      <html:text property="startHour" maxlength="2" size="2"/>&nbsp;:&nbsp;<html:text property="startMin" maxlength="2" size="2"/>
    </forms:description>
		<forms:description label="time.end" join="true">
      <html:text property="endHour" maxlength="2" size="2"/>&nbsp;:&nbsp;<html:text property="endMin" maxlength="2" size="2"/>
    </forms:description>
	  </forms:row> 	
	  	  
	  <forms:text label="time.workInHour" join="true" property="workInHour" required="true" size="12" maxlength="10"/>			  
	  
	  <forms:row>

    <forms:plaintext  label="time.hourRate"  property="hourRate"/>
<forms:plaintext  label="time.cost"  property="cost"/>
	 
	  </forms:row>
	  
	  <forms:text label="time.activity" property="activity" required="false" size="78" maxlength="255"/>			  
	  <forms:textarea valign="top" label="time.comment" property="comment" required="false" rows="2" cols="75" maxlength="1000"/>			  
	  
	  <forms:row>  
			<forms:row styleId="timeEditDescription">
			  <forms:description label="time.timeDescription"  valign="top" join="false">
					<misc:initSelectOptions id="timeCustomerProjectTaskOptions" name="timeCustomerProjectTaskOptions" scope="request" userCustomer="true" exposeCollection="true" />
					<prv:comboselect name="timeCustomerProjectTaskOptions" 
						metadata="idCustomer,customer,idProject,project,idTask,task">
						<prv:select property="timeCustomerId" style="width: 206px;height:19px;" doMatches="true" onchange="timeHourUpdater.updateTimeCustomerHour(this.value, updateTimeHour);clearBudget();"/>
						<img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>
						<br />
						<prv:select style="margin-top:5px;width: 206px;height:19px;" property="timeProjectId" doMatches="true" onchange="timeHourUpdater.updateTimeProjectHour(this.value, updateTimeHour);clearBudget();timeHourUpdater.updateBudgetProject(this.value, updateBudget);"/>
						<img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>
						<br />
						<prv:select style="margin-top:5px;width: 206px;height:19px;" property="timeTaskId" doMatches="true" onchange="timeHourUpdater.updateTimeTaskHour(this.value, updateTimeHour);timeHourUpdater.updateBudgetTask(this.value, updateBudget);"/>
					</prv:comboselect>
				</forms:description>
				<forms:group orientation="vertical">
					<c:if test="${sessionScope.newProject==true}">
						<forms:text property="newProject" maxlength="255" size="30" join="true" style="margin-top:22px;" />
					</c:if>
					<c:if test="${sessionScope.newProject==false && sessionScope.newTask==true}">
						<forms:text property="newTask" maxlength="255" size="30" join="true" style="margin-top:46px;" />
					</c:if>
					<c:if test="${sessionScope.newProject==true && sessionScope.newTask==true}">
						<forms:text property="newTask" maxlength="255" size="30" join="true" style="margin-top:-6px;" />
					</c:if>
				</forms:group>
			</forms:row>
	  </forms:row>
	  
	  <forms:row>
	  <forms:text label="time.charges" property="chargesStyle" required="false" size="35" maxlength="35"/>			  
	  <forms:text label="time.amount" property="amount" required="false" size="10" maxlength="10"/>			  
	  </forms:row>      
    
    <forms:description label="time.budget" valign="top" join="false">
      <div id="budget">${TimeForm.budget}</div>  
    </forms:description>      

    <forms:description label="time.budget.open" valign="top" join="true">
      <div id="budgetOpen">${TimeForm.budgetOpen}</div>  
    </forms:description>      
                    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${TimeForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:popupCalendar element="taskTimeDate" trigger="selectTaskTimeDate" showOthers="true"/>
  <misc:confirm key="time.delete"/>
</body>
</html>