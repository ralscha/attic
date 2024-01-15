<%@ include file="include/taglibs.inc"%>



<%@ include file="include/clientdetect.inc"%>

<html:form focus="userName" action="/login.do" method="post" onsubmit="clientDetect();return validateLoginForm(this);">
<html:hidden property="to"/>

<%@ include file="include/clientdetecth.inc"%>
	  
<table class="inputform">
<tr>
 <td><misc:label property="userName" key="login.userName"/>&nbsp;</td>
 <td><html:text tabindex="10" property="userName" size="20" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="password" key="login.password"/>&nbsp;</td>
 <td><html:password tabindex="20" property="password" size="20" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="remember" key="login.rememberMe"/>&nbsp;</td>
 <td><html:checkbox tabindex="30" property="remember"/></td>
</tr>
</table>
<p>&nbsp;</p>
<html:submit property="submit"><bean:message key="login"/></html:submit>&nbsp;
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>
<bean:message key="login.newPasswordDescription"/>
</p>
<p>&nbsp;</p>
<html:submit property="newPassword"><bean:message key="userconfig.newPassword"/></html:submit>&nbsp;
</html:form>
<html:javascript cdata="false" formName="loginForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>
<p>&nbsp;</p>
