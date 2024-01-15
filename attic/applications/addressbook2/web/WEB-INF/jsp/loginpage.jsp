<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>


<%@ include file="clientdetect.inc"%>

<form action="login" method="post" id="loginForm" onSubmit="javascript:clientDetect();">
<%@ include file="clientdetecth.inc"%>
	  
<table class="inputform">
<tr>
 <td align="left"><label for="userName" class="required"><bean:message key="login.userName"/>:&nbsp;</label></td>
 <td><input type="text" tabindex="1" name="userName" id="userName" size="20" maxlength="255" /></td>
</tr>
<tr>
 <td align="left"><label for="password" class="required"><bean:message key="login.password"/>:&nbsp;</label></td>
 <td><input type="password" tabindex="2" name="password" id="password" size="20" maxlength="255" /></td>
</tr>
<tr>
 <td align="left"><label for="remember" class="optional"><bean:message key="login.rememberMe"/>:&nbsp;</label></td>
 <td><input type="checkbox" tabindex="3" name="remember" id="remember" value="on" /></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" value="<bean:message key="login"/>" />&nbsp;
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>
<bean:message key="login.newPasswordDescription"/>
</p>
<p>&nbsp;</p>
<input type="submit" name="newPassword" value="<bean:message key="userconfig.newPassword"/>" />
</form>

<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["loginForm"].elements["userName"];

  if (focusControl.type != "hidden") {
     focusControl.focus();
  }
  // -->
</script>

<p>&nbsp;</p>
