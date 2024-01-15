<%@ include file="include/taglibs.inc"%>

<script language="JavaScript" src="<c:url value='/scripts/picker.js'/>" type="text/javascript"></script>  
<misc:popupCalendarJs showWeekNumber="true" showToday="true" />

<div class="title"><html:link page="/tasktimes.do" styleClass="title"><bean:message key="tasktime.tasktimes"/></html:link> / <bean:message key="tasktime.edit"/>: ${taskTimeForm.description}</div>
<br />

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerWithProjectsAndTasksOptions" scope="session" />
<misc:initSelectOptions id="projectOption" type="ch.ess.timetracker.web.ProjectWithTasksOptions" scope="request" />
<misc:initSelectOptions id="taskOption" type="ch.ess.timetracker.web.TaskOptions" scope="request" />

<misc:initSelectOptions id="userOption" type="ch.ess.timetracker.web.UserOptions" scope="session" />

<misc:confirm key="tasktime.delete"/>
<html:form focus="taskTimeDate" action="/storeTaskTime.do" method="post" onsubmit="return validateTaskTimeForm(this);">
<html:hidden property="id" />
<input type="hidden" name="changeCustomer" value=" " />
<input type="hidden" name="changeProject" value=" " />

<table class="inputform">

<misc:isUserInRole role="admin">
<tr>
 <td><misc:label property="userId" key="user"/>&nbsp;</td>
 <td>
   <html:select property="userId">
     <html:optionsCollection name="userOption" property="labelValue"/>   
   </html:select>
 </td>
</tr>
</misc:isUserInRole>

<tr>
 <td><misc:label property="taskTimeDate" key="tasktime.date"/>&nbsp;</td>
 <td><html:text property="taskTimeDate" size="10" maxlength="10"/><misc:popupCalendar form="taskTimeForm" element="taskTimeDate" past="true" /></td>
</tr>
<tr>
 <td><misc:label property="workInHour" key="tasktime.hours"/>&nbsp;</td>
 <td><html:text property="workInHour" size="10" maxlength="10"/></td>
</tr>
<tr>
 <td><misc:label property="hourRate" key="tasktime.hourRate"/>&nbsp;</td>
 <td><html:text property="hourRate" size="10" maxlength="10"/></td>
</tr>
<tr>
 <td><misc:label property="description" key="tasktime.description"/>&nbsp;</td>
 <td><html:text property="description" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="comment" key="tasktime.comment"/>&nbsp;</td>
 <td><html:textarea property="comment" rows="5" cols="40"/></td>
</tr>


<tr>
 <td><misc:label property="customerId" key="customer"/>&nbsp;</td>
 <td>
   <html:select property="customerId" onchange="bCancel=true;document.forms['taskTimeForm'].elements['changeCustomer'].value='1';document.forms['taskTimeForm'].submit();">
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
 </td>
</tr>
<tr>
 <td><misc:label property="projectId" key="project"/>&nbsp;</td>
 <td>
   <html:select property="projectId" onchange="bCancel=true;document.forms['taskTimeForm'].elements['changeProject'].value='1';document.forms['taskTimeForm'].submit();">
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
 </td>
</tr>
<tr>
 <td><misc:label property="taskId" key="task"/>&nbsp;</td>
 <td>
   <html:select property="taskId">
     <html:optionsCollection name="taskOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 


</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty taskTimeForm.id}">
  <c:if test="${taskTimeForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${taskTimeForm.description}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="taskTimeForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

