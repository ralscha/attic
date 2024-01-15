<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html:xhtml />

<div class="title"><bean:message key="SystemConfig"/></div>
<br />
<html:form action="/storeConfig.do" method="post" focus="passwordLen" onsubmit="return validateConfigForm(this);">
<table class="inputform">
<tr>
 <td><misc:label property="passwordLen" key="PasswordLen"/>&nbsp;</td>
 <td><html:text property="passwordLen" size="5" maxlength="5"/></td>
</tr>
<tr>
 <td><misc:label property="sessionTimeout" key="SessionTimeout"/>&nbsp;</td>
 <td><html:text property="sessionTimeout" size="5" maxlength="5"/>&nbsp;<bean:message key="minute"/></td>
</tr>
<tr>
 <td><misc:label property="mailSmtp" key="MailSmtp"/>&nbsp;</td>
 <td><html:text property="mailSmtp" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="mailSender" key="DefaultMailSender"/>&nbsp;</td>
 <td><html:text property="mailSender" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="errorMailReceiver" key="ErrorMailReceiver"/>&nbsp;</td>
 <td><html:text property="errorMailReceiver" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="uploadPath" key="UploadPath"/>&nbsp;</td>
 <td><html:text property="uploadPath" size="40" maxlength="255"/></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>" />&nbsp;
<input type="submit" name="test" value="<bean:message key="SaveTestEMail"/>" />
</html:form>
<html:javascript cdata="false" formName="configForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>

