<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<form method="post" id="loginForm" action="login">
	  
<table class="inputform">
<tr>
 <td align="left"><label for="userName" class="required"><bean:message key="UserName"/>:&nbsp;</label></td>
 <td><input type="text" tabindex="1" name="userName" id="userName" size="20" maxlength="255" /></td>
</tr>
<tr>
 <td align="left"><label for="password" class="required"><bean:message key="Password"/>:&nbsp;</label></td>
 <td><input type="password" tabindex="2" name="password" id="password" size="20" maxlength="255" /></td>
</tr>
<tr>
 <td align="left"><label for="remember" class="optional"><bean:message key="RememberMe"/>:&nbsp;</label></td>
 <td><input type="checkbox" tabindex="3" name="remember" id="remember" value="on" /></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" value="<bean:message key="Login"/>" />&nbsp;
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>
<bean:message key="NewPasswordDescription"/>
</p>
<p>&nbsp;</p>
<input type="submit" name="newPassword" value="<bean:message key="NewPassword"/>" />
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
