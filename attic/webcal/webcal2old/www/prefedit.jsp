<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

  

<html:form action="/storePreferences.do" method="post" onsubmit="return validatePreferencesForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="StartOfWeek"/>:&nbsp;</td>
 <td><html:radio property="startOfWeek" value="2"/><bean:message key="Monday"/>&nbsp;&nbsp;<html:radio property="startOfWeek" value="1"/><bean:message key="Sunday"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="MinDayInFirstWeek"/>:&nbsp;</td>
 <td><html:radio property="minimalDays" value="1"/>1&nbsp;&nbsp;<html:radio property="minimalDays" value="4"/>4</td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="Timezone"/>:&nbsp;</td>
 <td>
	   <html:select property="timezone">
	     <html:optionsCollection property="timezoneOptions"/>   
	   </html:select>  
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td class="mandatoryInput"><bean:message key="LogonRemember"/>:&nbsp;</td>
 <td><html:text property="time" size="5" maxlength="5"/>&nbsp;
	   <html:select property="timeUnit">
	     <html:optionsCollection property="timeOptions"/>   
	   </html:select> 
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr><td colspan="2"><bean:message key="ChangePassword"/></td></tr>
<tr>
 <td class="optionalInput"><bean:message key="OldPassword"/>:&nbsp;</td>
 <td><html:password property="oldPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="optionalInput"><bean:message key="NewPassword"/>:&nbsp;</td>
 <td><html:password property="newPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="optionalInput"><bean:message key="RetypePassword"/>:&nbsp;</td>
 <td><html:password property="retypeNewPassword" size="40" maxlength="255"/></td>
</tr>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>">&nbsp;
</html:form>
<html:javascript formName="preferencesForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>
