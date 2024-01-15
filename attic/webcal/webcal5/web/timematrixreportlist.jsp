<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title>
  <misc:popupCalendarJs />
  </head>
  <body>
  
  <misc:initSelectOptions id="timeGroupOption" name="groupOptions" scope="session" timeGroup="true"/>
  <misc:initSelectOptions id="timeYearOptions" name="timeYearOptions" scope="session"/>  
  <misc:initSelectOptions id="monthOptions" name="monthOptions" scope="session"/>    
  <misc:initSelectOptions id="weekOptions" name="weekOptions" scope="application"/>    
  <misc:initSelectOptions id="timeRangeOptions" name="timeRangeOptions" scope="session"/>      
    
  <html:form action="/timeMatrixReportList" focus="week">
    <input type="hidden" value="timeList" name="listType" id="listType"/>
	  <forms:form type="search" formid="frmListTime">
      <forms:html>
      
      
      
      
      
       <table>
        <tr height="100" style="line-height: 25px;">
        	<td  valign="top"style="border-right: 1px solid grey; padding: 5px; ">
        		<bean:message key="time.fromTo"/>
        		<br />
        		<ctrl:text styleId="from" property="from" size="7" maxlength="10"/><img src="images/cal.gif" alt="" name="selectFrom" id="selectFrom" width="16" height="16" border="0">	
			    <ctrl:text styleId="to" property="to" size="7" maxlength="10"/><img src="images/cal.gif" alt="" name="selectTo" id="selectTo" width="16" height="16" border="0">
		   	 	<br />
		   	 	<div style="float: left; ">
			   	<div style="width: 45px; float:left;"><bean:message key="header.week"/></div>
    	      	<div style="width: 80px; float:left;"><bean:message key="calendar.month"/></div>
        		<div style="float:left;"><bean:message key="calendar.year"/></div>
        		</div>
        		<br />
			  	<ctrl:select styleId="week" property="week" onclick="document.getElementById('month').value=''">
          			<base:options empty="empty" name="weekOptions"/>
        		</ctrl:select> 				
				<ctrl:select styleId="month" property="month" onchange="document.getElementById('week').value=''">
          			<base:options empty="empty" name="monthOptions"/>
        		</ctrl:select> 				
				<ctrl:select property="year">
          			<base:options empty="empty" name="timeYearOptions"/>
        		</ctrl:select> 		
        		<br />
        			   			<input id='btnDecreaseWeekHidden' name='btnDecreaseWeek' type='HIDDEN' />
        		<img alt="" style="margin-left: 3px;"onClick="document.getElementById('btnDecreaseWeekHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
        		<input id='btnIncreaseWeekHidden' name='btnIncreaseWeek' type='HIDDEN' />
        		<img alt=""  onClick="document.getElementById('btnIncreaseWeekHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	   	
	   		
	   			<input id='btnDecreaseMonthHidden' name='btnDecreaseMonth' type='HIDDEN' />
        		<img alt="" style="margin-left: 25px;"onClick="document.getElementById('btnDecreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
	   			<input id='btnIncreaseMonthHidden' name='btnIncreaseMonth' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnIncreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	   		
	   			<input id='btnDecreaseYearHidden' name='btnDecreaseYear' type='HIDDEN' />
        		<img alt="" style="margin-left: 35px;"onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
	   			<input id='btnIncreaseYearHidden' name='btnIncreaseYear' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnIncreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">		
        	</td>
        
        	<td  valign="top"style="border-right: 1px solid grey; padding: 5px;">
	        	<bean:message key="time.customer"/>&nbsp;/&nbsp;<bean:message key="time.project"/>&nbsp;/&nbsp;<bean:message key="time.timeTask"/> 
    	    	<br />
   				<sec:granted permission="$timeadmin">
 		    			<misc:initSelectOptions id="timeCustomerProjectTaskOptions" name="timeCustomerProjectTaskOptions" scope="request" exposeCollection="true" />
					<prv:comboselect name="timeCustomerProjectTaskOptions" 
					  metadata="idCustomer,customer,idProject,project,idTask,task">
						<prv:select value=" " style="width: 200px; margin-bottom: 5px;height:20px;" size="1" property="timeCustomerId" doMatches="true" /><br/>
						<prv:select value=" " style="width: 200px; margin-bottom: 5px;height:20px;" size="1" property="timeProjectId" doMatches="true" /><br/>
						<prv:select value=" " style="width: 200px; margin-bottom: 5px;height:20px;" size="1" property="timeTaskId" doMatches="true" />						
					</prv:comboselect>
				</sec:granted>
				
				<sec:notGranted permission="$timeadmin">          
 		    			<misc:initSelectOptions id="timeCustomerProjectTaskOptions" name="timeCustomerProjectTaskOptions" scope="request" userCustomer="true" exposeCollection="true" />
					<prv:comboselect name="timeCustomerProjectTaskOptions" 
					  metadata="idCustomer,customer,idProject,project,idTask,task">
						<prv:select style="width: 200px;margin-bottom: 5px;height:20px;" property="timeCustomerId" doMatches="true"/>
						<prv:select style="width: 200px;margin-bottom: 5px;height:20px;" property="timeProjectId" doMatches="true"/>
						<prv:select style="width: 200px;margin-bottom: 5px;height:20px;" property="timeTaskId" doMatches="true"/>						
					</prv:comboselect>					
				</sec:notGranted>  
        	</td>
        	

        	<td valign="top" style="border-right: 1px solid grey; padding: 5px;">
	        	 <sec:granted permission="$timeadmin">	         		          
		         	<bean:message key="time.user"/>
		         	<br />
          			<misc:initSelectOptions id="timeUserOption" name="userOptions" scope="session" permission="time"/>
					<ctrl:select style="width: 150px" property="userId">
	          			<base:options empty="empty" name="timeUserOption"/>
	        		</ctrl:select> 
	        		<br />
	        		<c:if test="${timeGroupOption.size > 0}">	
		          		<bean:message key="time.group"/>
		          		<br />
		          		<ctrl:select style="width: 150px" property="groupId">
	          				<base:options empty="empty" name="timeGroupOption"/>
	        			</ctrl:select> 	
	        			<br />
		          </c:if>	
		    	</sec:granted>
		    	
        	</td>
        	
        	
        	<td valign="top" style="border-right: 1px solid grey; padding: 5px;">
        		
						<bean:message key="time.scale"/><br />
						<ctrl:select property="scale">
	          				<base:options name="timeRangeOptions"/>
	          			 </ctrl:select> 	<br /><br />
	          			 <ctrl:checkbox property="searchWithInactive" />
						<bean:message key="time.searchWithInactive" />
						<br />

        	</td>
     	       	
        	
        	<td  valign="bottom" align="center" style=" padding: 5px;">
        		
        		<ctrl:button style="margin-top:25px; margin-bottom:25px;" base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search" /><br/>
          		<ctrl:button style="margin-bottom:5px;" base="buttons.src" name="btnClearSearch" src="btnClearSearch1.gif" title="button.title.clearsearch" />
        	</td>
        </tr>
   		</table>

      </forms:html>				
		</forms:form>	  	  
  </html:form>		
  <misc:popupCalendar element="from" trigger="selectFrom" showOthers="true"/>
  <misc:popupCalendar element="to" trigger="selectTo" showOthers="true"/>	
	
	<p></p>

	  
	<c:if test="${not empty listControl}">
		<ctrl:treelist 
			  action="/timeMatrixReportList.do" 
			  name="listControl" 
			  scope="session">
	   	</ctrl:treelist>
   	</c:if>
</body>
</html>
