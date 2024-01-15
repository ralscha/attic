<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

  

<html:form focus="passwordLen" action="/storeSystem.do" method="post" onsubmit="return validateSystemForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="PasswordLen"/>:&nbsp;</td>
 <td><html:text property="passwordLen" size="5" maxlength="5"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="SMTPServer"/>:&nbsp;</td>
 <td><html:text property="mailSmtp" size="40" maxlength="255"/></td>
</tr>

<tr>
 <td class="mandatoryInput"><bean:message key="MailSender"/>:&nbsp;</td>
 <td><html:text property="mailSender" size="40" maxlength="255"/></td>
</tr>

<tr>
 <td class="mandatoryInput"><bean:message key="ErrorMailReceiver"/>:&nbsp;</td>
 <td><html:text property="errorMailReceiver" size="40" maxlength="255"/></td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>">&nbsp;
<html:submit property="test"><bean:message key="TestEMail" /></html:submit>
</html:form>
<html:javascript formName="systemForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>
