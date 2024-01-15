<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

  

<html:form focus="userName" action="/logon.do" method="post" onsubmit="return validateLogonForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="UserName"/>:&nbsp;</td>
 <td><html:text property="userName" size="20" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="Password"/>:&nbsp;</td>
 <td><html:password property="password" size="20" maxlength="255"/></td>
</tr>
<tr>
 <td colspan="2"></td>
</tr>
<tr>
 <td colspan="2"><html:checkbox property="rememberLogon"/>&nbsp;&nbsp;<bean:message key="RememberLogon"/></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" value="<bean:message key="Logon"/>">&nbsp;
</html:form>
<html:javascript formName="logonForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>
