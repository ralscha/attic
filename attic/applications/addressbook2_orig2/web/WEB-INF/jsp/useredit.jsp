<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml /> 

<div class="title"><html:link page="/users.do" styleClass="title"><bean:message key="user.users"/></html:link> / <bean:message key="user.edit"/>: <c:out value="${userForm.userName}" /></div>
<br />

<misc:confirm key="user.deleteUser"/>
<html:form focus="userName" action="/storeUser.do" method="post" onsubmit="return validateUserForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="userName" key="login.userName"/>&nbsp;</td>
 <td><html:text property="userName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="name" key="user.lastName"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="firstName" key="user.firstName"/>&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="password" key="login.password"/>&nbsp;</td>
 <td><html:password property="password" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="retypePassword" key="user.retypePassword"/>&nbsp;</td>
 <td><html:password property="retypePassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="email" key="user.eMail"/>&nbsp;</td>
 <td><html:text property="email" size="40" maxlength="255"/></td>
</tr>
<misc:initSelectOptions id="roleOption" type="ch.ess.addressbook.web.RoleOptions" scope="session" />
<tr>
 <td valign="top"><misc:label property="roleIds" key="user.roles"/>&nbsp;</td>
 <td>
   <html:select multiple="true" size="5" property="roleIds">
     <html:option value="-1">&nbsp;</html:option>
     <html:optionsCollection name="roleOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<misc:initSelectOptions id="localeOption" type="ch.ess.addressbook.web.LocaleOptions" scope="session" />
<tr>
 <td><misc:label property="localeStr" key="user.language"/>&nbsp;</td>
 <td>
   <html:select property="localeStr">
     <html:optionsCollection name="localeOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty userForm.id}">
  <c:if test="${userForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('<c:out value="${userForm.userName}" />')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="userForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

