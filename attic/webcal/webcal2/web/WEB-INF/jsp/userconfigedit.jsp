<%@ include file="include/taglibs.inc"%>
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
 <td><misc:label property="shortName" key="user.shortName"/>&nbsp;</td>
 <td><html:text property="shortName" size="40" maxlength="255"/></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<misc:initSelectOptions id="timeOption" type="ch.ess.cal.web.userconfig.TimeOptions" scope="session" />
<tr>
 <td><misc:label property="time" key="userconfig.loginRemember"/>&nbsp;</td>
 <td><html:text property="time" size="5" maxlength="5"/>&nbsp;
	   <html:select property="timeUnit">
	     <html:optionsCollection name="timeOption" property="labelValue"/>   
	   </html:select> 
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<misc:initSelectOptions id="localeOption" type="ch.ess.cal.web.LocaleOptions" scope="session" />
<tr>
 <td><misc:label property="locale" key="user.language"/>&nbsp;</td>
 <td>
   <html:select property="locale">
     <html:optionsCollection name="localeOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<misc:initSelectOptions id="weekStartOption" type="ch.ess.cal.web.userconfig.WeekStartOptions" scope="session" />
<tr>
 <td><misc:label property="firstDayOfWeek" key="userconfig.firstDayOfWeek"/>&nbsp;</td>
 <td>
   <html:select property="firstDayOfWeek">
     <html:optionsCollection name="weekStartOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<misc:initSelectOptions id="timezoneOption" type="ch.ess.cal.web.userconfig.TimezoneOptions" scope="session" />
<tr>
 <td><misc:label property="timezone" key="userconfig.timezone"/>&nbsp;</td>
 <td>
   <html:select property="timezone">
     <html:optionsCollection name="timezoneOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td><bean:message key="userconfig.overviewPic"/></td>
 <td><misc:label property="overviewPicWidth" key="userconfig.overviewPicWidth"/>&nbsp;<html:text property="overviewPicWidth" size="4" maxlength="3"/>&nbsp;&nbsp;
     <misc:label property="overviewPicHeight" key="userconfig.overviewPicHeight"/>&nbsp;<html:text property="overviewPicHeight" size="4" maxlength="3"/>
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
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td valign="top"><label for="emails" class="required"><bean:message key="user.eMail"/> *:</label>&nbsp;</td>
 <td>
<c:if test="${not empty sessionScope.userConfigForm.emails}">
<display:table name="sessionScope.userConfigForm.emails" class="list" decorator="ch.ess.cal.web.EmailDecorator">
  <display:column property="email" maxLength="25" width="213" title="" align="left"></display:column>
  <display:column property="default" title="" align="left"></display:column>
  <display:column property="delete" title="" align="left"></display:column>
  <display:setProperty name="basic.show.header" value="false"/>
</display:table>
</c:if>
<br />
<html:text property="email" size="30" maxlength="255"/><input type="submit" name="change.addemail" value="<bean:message key="common.add"/>" />
 </td>
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
<bean:message key="userconfig.rememberMeCookieValid"/>: ${sessionScope.rememberMeValidUntil}
<html:form action="/deleteCookie.do">
  <input type="submit" name="remove" value="<bean:message key="userconfig.deleteCookie"/>">
</html:form>
</c:if>























