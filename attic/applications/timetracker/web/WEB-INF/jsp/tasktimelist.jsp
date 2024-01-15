<%@ include file="include/taglibs.inc"%>




<div class="title"><bean:message key="tasktime.tasktimes"/></div>
<br />
<misc:confirm key="tasktime.delete"/>

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerWithProjectsAndTasksOptions" scope="session" />
<misc:initSelectOptions id="projectOption" type="ch.ess.timetracker.web.ProjectWithTasksOptions" scope="session" />
<misc:initSelectOptions id="taskOption" type="ch.ess.timetracker.web.TaskOptions" scope="session" />

<misc:initSelectOptions id="weekOption" type="ch.ess.timetracker.web.WeekOptions" scope="session" />
<misc:initSelectOptions id="monthOption" type="ch.ess.timetracker.web.MonthOptions" scope="session" />
<misc:initSelectOptions id="yearOption" type="ch.ess.timetracker.web.YearOptions" scope="session" />

<html:form action="/listTaskTime.do" method="post">
<input type="hidden" name="search" value="search" />
<input type="hidden" name="changeCustomer" value=" " />
<input type="hidden" name="changeProject" value=" " />

<table class="filter">
<tr>
<td><bean:message key="tasktime.week"/>:</td>
<td><bean:message key="tasktime.month"/>:</td>
<td><bean:message key="tasktime.year"/>:</td>
<td><bean:message key="tasktime.from"/>:</td>
<td><bean:message key="tasktime.to"/>:</td>
<td><bean:message key="customer"/>:</td>
<td><bean:message key="project"/>:</td>
<td><bean:message key="task"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>

<td>
   <html:select property="value(week)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="weekOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(month)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="monthOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(year)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="yearOption" property="labelValue"/>   
   </html:select>
</td>
<td><html:text property="value(from)" size="10" maxlength="10"/><misc:popupCalendar form="mapForm" elementIndex="6" past="true" /></td>
<td><html:text property="value(to)" size="10" maxlength="10"/><misc:popupCalendar form="mapForm" elementIndex="7" past="true" /></td>

<td>
   <html:select property="value(customerId)" onchange="document.forms['mapForm'].elements['changeCustomer'].value='1';document.forms['mapForm'].submit();">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(projectId)" onchange="document.forms['mapForm'].elements['changeProject'].value='1';document.forms['mapForm'].submit();">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(taskId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="taskOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="reset" value="<bean:message key="common.reset"/>">&nbsp;<input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/tasktimes.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="${sessionScope.static_noRows}" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.timetracker.web.tasktime.TaskTimeDecorator">

  <display:column width="75" property="date" align="left" maxLength="10" nowrap="nowrap" headerClass="sortable" sortable="true" decorator="ch.ess.timetracker.web.tasktime.DateWrapper">    
    <misc:title><bean:message key="tasktime.date"/></misc:title>
  </display:column>

    <display:column width="38" property="shortName" align="left" maxLength="5" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="tasktime.shortName"/></misc:title>
  </display:column>
  
  <display:column width="75" property="hours" align="right" maxLength="10" nowrap="nowrap" headerClass="sortable" sortable="true" decorator="ch.ess.timetracker.web.tasktime.DecimalWrapper">    
    <misc:title><bean:message key="tasktime.hours"/></misc:title>
  </display:column>

  <display:column width="90" property="hourRate" align="right" maxLength="10" nowrap="nowrap" headerClass="sortable" sortable="true" decorator="ch.ess.timetracker.web.tasktime.DecimalWrapper">    
    <misc:title><bean:message key="tasktime.hourRate"/></misc:title>
  </display:column>
  
  <display:column width="75" property="total" align="right" maxLength="10" nowrap="nowrap" headerClass="sortable" sortable="true" decorator="ch.ess.timetracker.web.tasktime.DecimalWrapper">    
    <misc:title><bean:message key="tasktime.cost"/></misc:title>
  </display:column>  
  
  <display:column width="150" property="description" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="tasktime.description"/></misc:title>
  </display:column>
  
  <display:column width="90" property="customer" group="1" align="left" maxLength="12" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="customer"/></misc:title>
  </display:column>
  
  <display:column width="90" property="project" group="2" align="left" maxLength="12" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="project"/></misc:title>
  </display:column>
  
    <display:column width="90" property="task" group="3" align="left" maxLength="12" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="task"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="tasktime"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="tasktime.tasktimes"/></misc:setProperty>

  <%@ include file="include/tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="9" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newTaskTime.do" />
<input type="submit" name="go" value="<bean:message key="tasktime.new"/>" />
</html:form>

<misc:popupCalendarJs showWeekNumber="true" showToday="true" />