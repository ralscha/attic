<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/tasks.do" styleClass="title"><bean:message key="task.tasks"/></html:link> / <bean:message key="task.edit"/>: ${taskForm.name}</div>
<br />

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerWithProjectsOptions" scope="session" />
<misc:initSelectOptions id="projectOption" type="ch.ess.timetracker.web.ProjectOptions" scope="request" />


<misc:confirm key="task.delete"/>
<html:form focus="name" action="/storeTask.do" method="post" onsubmit="return validateTaskForm(this);">
<html:hidden property="id" />
<input type="hidden" name="change" value=" " />

<table class="inputform">

<tr>
 <td><misc:label property="name" key="task.name"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>

<tr>
 <td><misc:label property="description" key="task.description"/>&nbsp;</td>
 <td><html:textarea property="description" rows="5" cols="40"/></td>
</tr>

<tr>
 <td><misc:label property="customerId" key="customer"/>&nbsp;</td>
 <td>
   <html:select property="customerId" onchange="bCancel=true;document.forms['taskForm'].elements['change'].value='1';document.forms['taskForm'].submit();">
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<tr>
 <td><misc:label property="projectId" key="project"/>&nbsp;</td>
 <td>
   <html:select property="projectId">
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty taskForm.id}">
  <c:if test="${taskForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${taskForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="taskForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

