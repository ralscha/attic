<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/userGroups.do" styleClass="title"><bean:message key="userGroup.userGroups"/></html:link> / <bean:message key="userGroup.edit"/>: ${userGroupForm.groupName}</div>
<br />

<misc:confirm key="userGroup.delete"/>
<html:form focus="groupName" action="/storeUserGroup.do" method="post" onsubmit="return validateUserGroupForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="groupName" key="userGroup.groupName"/>&nbsp;</td>
 <td><html:text property="groupName" size="40" maxlength="255"/></td>
</tr>

<misc:initSelectOptions id="permissionOptions" type="ch.ess.blank.web.usergroup.PermissionOptions" scope="session" />


<tr>
 <td valign="top"><bean:message key="userGroup.permissions"/>:&nbsp;</td>
 <td> 
	<table>
	<logic:iterate id="po" name="permissionOptions" property="labelValue" type="org.apache.struts.util.LabelValueBean"> 
	<tr><td><html:multibox property="permissionIds" value="${po.value}" /></td><td>${po.label}</td></tr>
	</logic:iterate>
	</table>
 </td>
</tr>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty userGroupForm.id}">
  <c:if test="${userGroupForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${userGroupForm.groupName}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="userGroupForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

