<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/projects.do" styleClass="title"><bean:message key="project.projects"/></html:link> / <bean:message key="project.edit"/>: ${projectForm.name}</div>
<br />

<misc:confirm key="project.delete"/>
<html:form focus="name" action="/storeProject.do" method="post" onsubmit="return validateProjectForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="name" key="project.name"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="description" key="customer.description"/>&nbsp;</td>
 <td><html:textarea property="description" rows="5" cols="40"/></td>
</tr>
<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerOptions" scope="session" />
<tr>
 <td valign="top"><misc:label property="customerId" key="customer"/>&nbsp;</td>
 <td>
   <html:select property="customerId">
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
 </td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty projectForm.id}">
  <c:if test="${projectForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${projectForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="projectForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

