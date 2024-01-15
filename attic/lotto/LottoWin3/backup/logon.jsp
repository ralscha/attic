<%@ page language="java" import="org.apache.struts.action.*"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-error.tld" prefix="error" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>

<html>
<head>
<title>Lottowin Anmeldung</title>
<link rel="STYLESHEET" type="text/css" href="global.css">
</head>
<body bgcolor="white">
<div align="center">

<h3>Bitte anmelden</h3>
<struts:form action="logon.do" name="logonForm" focus="username" type="lottowin.form.LogonForm">
<table border="0">

  <tr>
    <td align="right">Benutzer ID</td>
    <td align="left"><struts:text property="username" size="20" maxlength="50"/>
    </td>
  </tr>

  <tr>
    <td align="right">Passwort</td>
    <td align="left"><struts:password property="password" size="20" maxlength="50"/></td>
  </tr>

  <tr>
    <td colspan="2" align="center"><struts:submit property="submit">Anmelden</struts:submit></td>
  </tr>

</table>

</struts:form>
<error:errorsPresent>
<table>
  <error:listErrors>
    <tr><td bgcolor="Red"><b><errorerrorMessage /></b></td></tr>
  </error:listErrors>
</table>
</error:errorsPresent>
</div>
</body>
</html>
