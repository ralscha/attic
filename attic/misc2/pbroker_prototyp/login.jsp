<%@ page info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<html>

<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
<title>Login PBroker</title>
<script language="JavaScript" type="text/javascript">
	<!-- Hide script from older browsers


	function changePage() {
		if (self.parent.frames.length != 0)
			self.parent.location=document.location;
		}

	// end hiding contents -->
</script>
</head>

<body topmargin="0" leftmargin="0" onLoad="changePage()">

<struts:form action="login.do" method="post" name="loginForm" type="ch.ess.pbroker.form.LoginForm" scope="request">

<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="2" background="images/login_top.gif"><font face="Arial,Helvetica,sans-serif"><img border="0" src="images/x.gif" width="404" height="230" alt="Klick to force enter..."></font></td>
        </tr>
        <tr>
          <td width="104" background="images/login_down.gif"><font face="Arial,Helvetica,sans-serif"><img border="0" src="images/x.gif" width="132" height="175" alt="Klick to force enter..."></font></td>
          
          <td align="center" valign="top">
              <p><font face="Arial,Helvetica,sans-serif"><font size="2">Username:</font><br>
              <font size="2">
              <struts:text property="user" size="20"/></font></font></p>
              <p><font face="Arial,Helvetica,sans-serif"><font size="2">Passwort:</font><br>
              <font size="2">
              <struts:password property="password" size="20"/></font></font><br>
              <input border="0" src="images/go.gif" name="go" alt="GO for it!" width="29" height="29" type="image"></p>              
           </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</struts:form>

<struts:ifAttributeExists name="pbroker.error" scope="request">
<p>
<div align="center">
<b><font face="Arial,Helvetica,sans-serif" size="2"><%= (String)request.getAttribute("pbroker.error") %></font></b>
<% request.removeAttribute("pbroker.error"); %>
</div>
</struts:ifAttributeExists>

</body>

</html>
