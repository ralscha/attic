<%@ include file="include/taglibs.inc"%>


<div class="title"><bean:message key="config.systemConfig"/></div>
<br />
<html:form action="/storeConfig.do" method="post" focus="passwordLen" onsubmit="return validateConfigForm(this);">
<table class="inputform">
<tr>
 <td><misc:label property="passwordLen" key="config.passwordLen"/>&nbsp;</td>
 <td><html:text property="passwordLen" size="5" maxlength="5"/></td>
</tr>
<tr>
 <td><misc:label property="sessionTimeout" key="config.sessionTimeout"/>&nbsp;</td>
 <td><html:text property="sessionTimeout" size="5" maxlength="5"/>&nbsp;<bean:message key="time.minute"/></td>
</tr>
<tr>
 <td><misc:label property="mailSmtp" key="config.mailSmtp"/>&nbsp;</td>
 <td><html:text property="mailSmtp" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="mailSmtpPort" key="config.mailSmtpPort"/>&nbsp;</td>
 <td><html:text property="mailSmtpPort" size="5" maxlength="5"/></td>
</tr>
<tr>
 <td><misc:label property="mailSmtpUser" key="config.mailSmtpUser"/>&nbsp;</td>
 <td><html:text property="mailSmtpUser" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="mailSmtpPassword" key="config.mailSmtpPassword"/>&nbsp;</td>
 <td><html:text property="mailSmtpPassword" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="mailSender" key="config.defaultMailSender"/>&nbsp;</td>
 <td><html:text property="mailSender" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="errorMailReceiver" key="config.errorMailReceiver"/>&nbsp;</td>
 <td><html:text property="errorMailReceiver" size="40" maxlength="255"/></td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="test" value="<bean:message key="config.saveTestEMail"/>" />
</html:form>
<html:javascript cdata="false" formName="configForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>

