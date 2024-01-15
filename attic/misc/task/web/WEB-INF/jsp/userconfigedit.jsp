<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc" prefix="misc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html:xhtml />
<div class="title"><bean:message key="userconfig.personalConfig"/></div>
<br />
<html:form action="/storeUserConfig.do" styleId="userConfigForm" focus="firstName" method="post" onsubmit="return validateUserConfigForm(this);">
<table class="inputform">

<tr>
 <td><misc:label property="firstName" key="user.firstName"/>&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="name" key="user.lastName"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="email" key="user.eMail"/>&nbsp;</td>
 <td><html:text property="email" size="40" maxlength="255"/></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<misc:initSelectOptions id="timeOption" type="ch.ess.task.web.userconfig.TimeOptions" scope="session" />
<tr>
 <td><misc:label property="time" key="userconfig.loginRemember"/>&nbsp;</td>
 <td><html:text property="time" size="5" maxlength="5"/>&nbsp;
	   <html:select property="timeUnit">
	     <html:optionsCollection name="timeOption" property="labelValue"/>   
	   </html:select> 
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<misc:initSelectOptions id="localeOption" type="ch.ess.task.web.LocaleOptions" scope="session" />
<tr>
 <td><misc:label property="locale" key="user.language"/>&nbsp;</td>
 <td>
   <html:select property="locale">
     <html:optionsCollection name="localeOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<misc:initSelectOptions id="weekStartOption" type="ch.ess.task.web.userconfig.WeekStartOptions" scope="session" />
<tr>
 <td><misc:label property="firstDayOfWeek" key="userconfig.firstDayOfWeek"/>&nbsp;</td>
 <td>
   <html:select property="firstDayOfWeek">
     <html:optionsCollection name="weekStartOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<tr><td colspan="2">&nbsp;</td></tr>
<tr><td colspan="2"><bean:message key="userconfig.changePassword"/></td></tr>
<tr>
 <td><misc:label property="oldPassword" key="userconfig.oldPassword"/>&nbsp;</td>
 <td><html:password property="oldPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="newPassword" key="userconfig.newPassword"/>&nbsp;</td>
 <td><html:password property="newPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="retypeNewPassword" key="user.retypePassword"/>&nbsp;</td>
 <td><html:password property="retypeNewPassword" size="40" maxlength="255"/></td>
</tr>


</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
</html:form>
<html:javascript cdata="false" formName="userConfigForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>
<c:if test="${not empty sessionScope.rememberMeValidUntil}">
<p>&nbsp;</p>
<p>&nbsp;</p>
<bean:message key="userconfig.rememberMeCookieValid"/>: <c:out value="${sessionScope.rememberMeValidUntil}"/>
<html:form action="/deleteCookie.do">
  <input type="submit" name="remove" value="<bean:message key="userconfig.deleteCookie"/>">
</html:form>
</c:if>























