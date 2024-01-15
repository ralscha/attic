<%@ include file="include/taglibs.jspf"%>



<html>
<head>
<title></title>
<misc:popupCalendarJs />
</head>
<body>

	<html:form action="/timeTaskBudgetList" focus="value(date)">
		<forms:form type="search" formid="frmListTimeTaskBudget">
			<forms:html>
			
			<table>
        <tr height="100" style="line-height: 25px;">
        	<td  valign="top"style="border-right: 1px solid grey; padding: 5px; ">
        		<bean:message key="time.from"/>
        		<br />
        		<ctrl:text styleId="from"
							property="value(from)" size="10" maxlength="10" /><img
						src="images/cal.gif" alt="" name="selectFrom" id="selectFrom"
						width="16" height="16" border="0">
					<br />	
			<bean:message key="time.to"/><br />
			<ctrl:text styleId="to" property="value(to)"
							size="10" maxlength="10" /><img src="images/cal.gif" alt=""
						name="selectTo" id="selectTo" width="16" height="16" border="0">
						</td>
        
        	<td  valign="top"style="border-right: 1px solid grey; padding: 5px;">
	        	<bean:message key="time.customer"/>&nbsp;/&nbsp;<bean:message key="time.project"/>&nbsp;/&nbsp;<bean:message key="time.timeTask"/> 
    	    	<br />
   				<misc:initSelectOptions
							id="timeCustomerProjectTaskOptions"
							name="timeCustomerProjectTaskOptions" scope="request"
							exposeCollection="true" /> <prv:comboselect
							name="timeCustomerProjectTaskOptions"
							metadata="idCustomer,customer,idProject,project,idTask,task">
							<prv:select style="width: 200px; margin-bottom: 5px;height:20px;" property="value(timeCustomerId)" doMatches="true" />
							<br />
							<prv:select  style="width: 200px; margin-bottom: 5px;height:20px;" property="value(timeProjectId)" doMatches="true" />
							<br />
							<prv:select  style="width: 200px; margin-bottom: 5px;height:20px;" property="value(timeTaskId)" doMatches="true" />
						</prv:comboselect>
        	</td>

        	
        	<td  valign="top" align="center" style=" padding: 5px;">
				<ctrl:checkbox property="searchWithInactive" /><bean:message key="time.searchWithInactive" /><br/>
        		<ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" style="margin-top: 25px;" title="button.title.search" /><br />
        		<ctrl:button base="buttons.src"
							name="btnClearSearch" src="btnClearSearch1.gif" style="margin-top: 10px;"
							title="button.title.clearsearch" />
        	</td>
        </tr>
   		</table>
			
			</forms:html>
		</forms:form>
	</html:form>
	<misc:popupCalendar element="from" trigger="selectFrom"
		showOthers="true" />
	<misc:popupCalendar element="to" trigger="selectTo" showOthers="true" />

	<p></p>
	<c:if test="${not empty listControl}">
		<ctrl:list id="timeTaskBudgetList" action="/timeTaskBudgetList"
			name="listControl" scope="session" 
			title="timeTaskBudget.timeTaskBudgets" rows="${sessionScope.noRows}"
			createButton="true" refreshButton="true">

			<ctrl:columntext title="time.customer" property="timeCustomer"
				width="180" maxlength="30" sortable="true" />
			<ctrl:columntext title="time.project" property="timeProject"
				width="180" maxlength="30" sortable="true" />
			<ctrl:columntext title="time.timeTask" property="timeTask"
				width="180" maxlength="30" sortable="true" />

			<ctrl:columntext title="timeTaskBudget.date" property="date"
				width="60" sortable="true" converter="ch.ess.base.web.DateConverter" />
			<ctrl:columntext title="timeTaskBudget.amount" property="amount"
				width="60" align="right" sortable="true"
				converter="ch.ess.base.web.BigDecimalConverter" />

			<ctrl:columngroup title="common.action" align="center">
				<ctrl:columnedit tooltip="common.edit" />
				<misc:columncopy tooltip="common.copy" />
				<ctrl:columndelete tooltip="common.delete" property="deletable"
					onclick="return confirmRequest('@{bean.map.dateStr}');" />
			</ctrl:columngroup>
		</ctrl:list>
		<misc:confirm key="timeTaskBudget.delete" />
	</c:if>
</body>
</html>
