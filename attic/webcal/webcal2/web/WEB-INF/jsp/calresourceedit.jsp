<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 

<div class="title"><html:link page="/calresources.do" styleClass="title"><bean:message key="calresource.resources"/></html:link> / <bean:message key="calresource.edit"/>: ${calResourceForm.name}</div>
<br />

<misc:initSelectOptions id="resourceGroupOption" type="ch.ess.cal.web.calresource.ResourceGroupOptions" scope="session" />

<misc:confirm key="calresource.delete"/>
<html:form action="/storeCalResource.do" method="post" onsubmit="return validateCalResourceForm(this);">
<html:hidden property="id" />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="calResourceForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[${ix}].name" class="required"><bean:message key="calresource.name" /> (${entry.language}) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>
<tr>
<td><misc:label property="resourceGroupId" key="resourcegroup"/>&nbsp;</td>
<td>
   <html:select property="resourceGroupId">
     <html:optionsCollection name="resourceGroupOption" property="labelValue"/>   
   </html:select>
</td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty calResourceForm.id}">
  <c:if test="${calResourceForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${calResourceForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>


<html:javascript cdata="false" formName="calResourceForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

