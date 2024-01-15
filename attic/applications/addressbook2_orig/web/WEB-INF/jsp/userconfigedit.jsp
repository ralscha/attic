<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc" prefix="misc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html:xhtml />
<div class="title"><bean:message key="PersonalConfig"/></div>
<br />
<html:form action="/storeUserConfig.do" styleId="userConfigForm" focus="firstName" method="post" onsubmit="return validateUserConfigForm(this);">
<table class="inputform">

<tr>
 <td><misc:label property="firstName" key="FirstName"/>&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="name" key="LastName"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="email" key="EMail"/>&nbsp;</td>
 <td><html:text property="email" size="40" maxlength="255"/></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td><misc:label property="noRows" key="NoRows"/>&nbsp;</td>
 <td><html:text property="noRows" size="5" maxlength="5"/></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td><misc:label property="time" key="LoginRemember"/>&nbsp;</td>
 <td><html:text property="time" size="5" maxlength="5"/>&nbsp;
	   <html:select property="timeUnit">
	     <html:optionsCollection property="timeOptions"/>   
	   </html:select> 
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td><misc:label property="locale" key="Language"/>&nbsp;</td>
 <td>
   <html:select property="locale">
     <html:optionsCollection property="localeOptions"/>   
   </html:select>
 </td>
</tr> 
<tr><td colspan="2">&nbsp;</td></tr>
<tr><td colspan="2"><bean:message key="ChangePassword"/></td></tr>
<tr>
 <td><misc:label property="oldPassword" key="OldPassword"/>&nbsp;</td>
 <td><html:password property="oldPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="newPassword" key="NewPassword"/>&nbsp;</td>
 <td><html:password property="newPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="retypeNewPassword" key="RetypePassword"/>&nbsp;</td>
 <td><html:password property="retypeNewPassword" size="40" maxlength="255"/></td>
</tr>


</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>" />&nbsp;
</html:form>
<html:javascript cdata="false" formName="userConfigForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>
<c:if test="${not empty sessionScope.rememberMeValidUntil}">
<p>&nbsp;</p>
<p>&nbsp;</p>
<bean:message key="RememberMeCookieValid"/>: <c:out value="${sessionScope.rememberMeValidUntil}"/>
<html:form action="/deleteCookie.do">
  <input type="submit" name="remove" value="<bean:message key="DeleteCookie"/>">
</html:form>
</c:if>























