<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-error.tld" prefix="error" %>
<%@ taglib uri="/WEB-INF/taglib-misc.tld"  prefix="misc" %>
<html>
<head>
<title>Anmeldung LottoWin</title>
<link rel="STYLESHEET" type="text/css" href="global.css">

</head>

<body>

<h3>Anmeldung LottoWin</h3>
<struts:form action="addUser.do" method="post" name="userForm" focus="userID" type="lottowin.form.UserForm">
  <misc:trxToken/>
  <p></p>
  <blockquote>
    <table>
      <tr>
        <td ALIGN="right" colspan="2">
          <p align="left">Bitte Formular ausf&uuml;llen</td>
      </tr>
      <tr>
        <td ALIGN="right" colspan="2" height="10"></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Benutzer ID</em></td>
        <td bgcolor="#CCCCCC"><struts:text property="userID" size="20" maxlength="30"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Vorname</em></td>
        <td bgcolor="#CCCCCC"><struts:text property="firstName" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Nachname</em></td>
        <td bgcolor="#CCCCCC"><struts:text property="lastName" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>E-Mail</em></td>
        <td bgcolor="#CCCCCC"><struts:text property="email" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td colspan="2" height="10"></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Passwort</em></td>
        <td bgcolor="#CCCCCC"><struts:password property="password" size="16" maxlength="16"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Passwort best&auml;tigen</em></td>
        <td bgcolor="#CCCCCC"><struts:password property="passwordConfirm" size="16" maxlength="16"/></td>
      </tr>
      <tr>
        <td ALIGN="right" colspan="2" height="10"></td>
      </tr>
      <tr>
        <td colspan="2" align="left"><struts:submit value="Abschicken"/></td>
      </tr>
    </table>
<p></p>
  <error:errorsPresent>
<table>
  <error:listErrors>
    <tr><td bgcolor="Red"><b><error:errorMessage /></b></td></tr>
  </error:listErrors>
</table>
</error:errorsPresent>
  &nbsp;
</struts:form>
  </blockquote>

</body>

</html>
