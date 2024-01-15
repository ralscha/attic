<%@ include file="include/taglibs.inc"%>


<div class="title"><bean:message key="tasktime.report"/></div>
<br />

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerWithProjectsAndTasksOptions" scope="session" />

<misc:initSelectOptions id="weekOption" type="ch.ess.timetracker.web.WeekOptions" scope="session" />
<misc:initSelectOptions id="monthOption" type="ch.ess.timetracker.web.MonthOptions" scope="session" />
<misc:initSelectOptions id="yearOption" type="ch.ess.timetracker.web.YearOptions" scope="session" />

<html:form action="/reportTaskTime.do" method="post">
<input type="hidden" name="search" value="search" />

<table class="filter">
<tr>
<td><bean:message key="tasktime.week"/>:</td>
<td><bean:message key="tasktime.month"/>:</td>
<td><bean:message key="tasktime.year"/>:</td>
<td><bean:message key="tasktime.from"/>:</td>
<td><bean:message key="tasktime.to"/>:</td>
<td><bean:message key="customer"/>:</td>
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
<td><html:text property="value(from)" size="10" maxlength="10"/><misc:popupCalendar form="mapForm" elementIndex="1" past="true" /></td>
<td><html:text property="value(to)" size="10" maxlength="10"/><misc:popupCalendar form="mapForm" elementIndex="2" past="true" /></td>
<td>
   <html:select property="value(customerId)">
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="reset" value="<bean:message key="common.reset"/>">&nbsp;<input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">


<display:table name="result" export="false" scope="session" sort="list" class="list" decorator="ch.ess.timetracker.web.tasktime.TaskTimeReportDecorator">

  
  <display:column width="150" property="customer" group="1" align="left" maxLength="20" nowrap="nowrap">    
    <misc:title><bean:message key="customer"/></misc:title>
  </display:column>
  
  <display:column width="150" property="project" group="2" align="left" maxLength="20" nowrap="nowrap">    
    <misc:title><bean:message key="project"/></misc:title>
  </display:column>
  
  <display:column width="150" property="task" group="3" align="left" maxLength="20" nowrap="nowrap">    
    <misc:title><bean:message key="task"/></misc:title>
  </display:column>
    
  <display:column width="120" property="totalHours" align="right" nowrap="nowrap" decorator="ch.ess.timetracker.web.tasktime.DecimalWrapper">    
    <misc:title><bean:message key="tasktime.totalHour"/></misc:title>
  </display:column>	
		
  <display:column width="120" property="totalCost" align="right" nowrap="nowrap" decorator="ch.ess.timetracker.web.tasktime.DecimalWrapper">    
    <misc:title><bean:message key="tasktime.totalCost"/></misc:title>
  </display:column>			
		
  <%@ include file="include/tableproperties.inc"%>  
  
  <display:setProperty name="css.tr.even" value="even"/>
  <display:setProperty name="css.tr.odd" value="even"/>
</display:table>

</c:if>
<br />
<html:link page="/testReport.do">Report</html:link>
<misc:popupCalendarJs showWeekNumber="true" showToday="true" />