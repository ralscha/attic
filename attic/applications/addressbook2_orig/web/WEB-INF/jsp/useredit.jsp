<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml /> 

<div class="title"><bean:message key="Users"/></div>
<br />

<misc:confirm key="DeleteUser"/>
<html:form focus="userName" action="/storeUser.do" method="post" onsubmit="return validateUserForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="userName" key="UserName"/>&nbsp;</td>
 <td><html:text property="userName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="name" key="LastName"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="firstName" key="FirstName"/>&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="password" key="Password"/>&nbsp;</td>
 <td><html:password property="password" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="retypePassword" key="RetypePassword"/>&nbsp;</td>
 <td><html:password property="retypePassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="email" key="EMail"/>&nbsp;</td>
 <td><html:text property="email" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td valign="top"><misc:label property="roleIds" key="Roles"/>&nbsp;</td>
 <td>
   <html:select multiple="true" size="5" property="roleIds">
     <html:option value="-1">&nbsp;</html:option>
     <html:optionsCollection property="roleOptions"/>   
   </html:select>
 </td>
</tr> 
<tr>
 <td><misc:label property="locale" key="Language"/>&nbsp;</td>
 <td>
   <html:select property="locale">
     <html:optionsCollection property="localeOptions"/>   
   </html:select>
 </td>
</tr> 
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="SaveAndNew"/>" />&nbsp;
<c:if test="${not empty userForm.id}">
<input type="submit" onclick="return confirmRequest('<bean:write name="userForm" property="userName"/>')" name="delete" value="<bean:message key="Delete"/>" />&nbsp;
</c:if>
<html:cancel><bean:message key="Cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="userForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

