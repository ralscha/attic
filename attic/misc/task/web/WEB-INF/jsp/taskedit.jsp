<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml /> 

<misc:popupCalendarJs />

<div class="title"><html:link page="/tasks.do" styleClass="title"><bean:message key="task.tasks"/></html:link> / <bean:message key="task.edit"/>: <c:out value="${taskForm.name}" /></div>
<br />


<misc:confirm key="task.deleteTask"/>
<html:form focus="name" action="/storeTask.do" method="post" onsubmit="return validateTaskForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="name" key="task.name"/>&nbsp;</td>
 <td><html:text property="name" size="60" maxlength="255"/></td>
</tr>
<tr>
 <td valign="top"><misc:label property="description" key="task.description"/>&nbsp;</td>
 <td><html:textarea cols="60" rows="5" property="description"/></td>
</tr>

<misc:initSelectOptions id="projectOption" type="ch.ess.task.web.task.ProjectOptions" scope="session" />
<tr>
 <td><misc:label property="projectId" key="task.project"/>&nbsp;</td>
 <td>
   <html:select property="projectId">
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
 </td>
</tr>
<misc:initSelectOptions id="priorityOption" type="ch.ess.task.web.task.PriorityOptions" scope="session" />
<tr>
 <td><misc:label property="priorityId" key="task.priority"/>&nbsp;</td>
 <td>
   <html:select property="priorityId">
     <html:optionsCollection name="priorityOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<tr>
  <td><bean:message key="task.createdBy"/>:&nbsp;</td>
  <td><c:out value="${taskForm.createdBy}"/></td>
</tr>
<tr>
  <td><bean:message key="task.dateCreated"/>:&nbsp;</td>
  <td><c:out value="${taskForm.created}"/></td>
</tr>
<misc:initSelectOptions id="userOption" type="ch.ess.task.web.task.UserOptions" scope="session" />
<tr>
 <td><misc:label property="toUserId" key="task.assignedTo"/>&nbsp;</td>
 <td>
   <html:select property="toUserId">
     <html:option value="-1">&nbsp;</html:option>
     <html:optionsCollection name="userOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<misc:initSelectOptions id="statusOption" type="ch.ess.task.web.task.StatusOptions" scope="session" />
<tr>
 <td><misc:label property="statusId" key="task.status"/>&nbsp;</td>
 <td>
   <html:select property="statusId">
     <html:optionsCollection name="statusOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<tr>
  <td><misc:label property="complete" key="task.complete"/>&nbsp;</td>
  <td><html:text property="complete" size="5" maxlength="5"/></td>
</tr>
<tr>
  <td><misc:label property="resolved" key="task.dateResolved"/>&nbsp;</td>
  <td><html:text property="resolved" size="10" maxlength="20"/><misc:popupCalendar form="taskForm" element="resolved" /></td>
</tr>
<tr>
 <td valign="top"><misc:label property="resolution" key="task.resolution"/>&nbsp;</td>
 <td><html:textarea cols="60" rows="5" property="resolution"/></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty taskForm.id}">
<input type="submit" onclick="return confirmRequest('<bean:write name="taskForm" property="name"/>')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="taskForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

