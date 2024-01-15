<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



<html:form focus="userName" action="/storeUser.do" method="post" onsubmit="return validateUserForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="UserName"/>:&nbsp;</td>
 <td><html:text property="userName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="FirstName"/>:&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="LastName"/>:&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="Password"/>:&nbsp;</td>
 <td><html:password property="password" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="RetypePassword"/>:&nbsp;</td>
 <td><html:password property="retypePassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td valign="top" class="mandatoryInput"><bean:message key="EMail"/>:&nbsp;</td>
 <td><html:text property="email1" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td>&nbsp;</td>
 <td><html:text property="email2" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td>&nbsp;</td>
 <td><html:text property="email3" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td valign="top" class="optionalInput"><bean:message key="Administrator"/>:&nbsp;</td>
 <td><html:checkbox property="admin"/></td>
</tr>
<tr>
  <td colspan="2">&nbsp;&nbsp;</td>
</tr>
<tr>
 <td valign="top" class="optionalInput"><bean:message key="Departments"/>:&nbsp;</td>
 <td>
   <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="mandatoryInput">Belongs to</td>
	  <td>&nbsp;&nbsp;&nbsp;</td>
      <td class="optionalInput">Access to</td>
    </tr>
    <tr>
	 <td align="left">
	   <html:select multiple="true" size="5" property="departmentIds">
	     <html:optionsCollection property="departmentOptions"/>   
	   </html:select>
	 </td>   
	 <td>&nbsp;&nbsp;&nbsp;</td>   
	 <td align="left">
	   <html:select multiple="true" size="5" property="accessDepartmentIds">
	     <html:option value="-1">&nbsp;</html:option>
	     <html:optionsCollection property="departmentOptions"/>   
	   </html:select>
	 </td>      
    </tr>
   </table>
 </td>
</tr>
<tr>
 <td valign="top" class="optionalInput"><bean:message key="ResourceGroups"/>:&nbsp;</td>
 <td>
   <html:select multiple="true" size="5" property="resourceGroupIds">
     <html:option value="-1">&nbsp;</html:option>
     <html:optionsCollection property="resourceGroupOptions"/>   
   </html:select>
 </td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>">&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="SaveAndNew"/>">&nbsp;
<html:cancel><bean:message key="Cancel"/></html:cancel>
</html:form>
<html:javascript formName="userForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>
