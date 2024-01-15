<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-misc.tld"  prefix="misc" %>

<jsp:useBean id="newUser" scope="request" class="lottowin.form.UserForm"/>
<html>
<head>
<title>Anmeldung LottoWin</title>
<link rel="STYLESHEET" type="text/css" href="global.css">

</head>

<body>

<h3>Anmeldung LottoWin </h3>

  <blockquote>
    <table>
      <tr>
        <td ALIGN="right" colspan="2">
          <p align="left"><b>Anmeldung erfolgreich abgeschlossen</b></td>
      </tr>
      <tr>
        <td ALIGN="right" colspan="2" height="10"></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Benutzer ID</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="newUser" property="userID"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Vorname</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="newUser" property="firstName"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Nachname</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="newUser" property="lastName"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>E-Mail</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="newUser" property="email"/></b></td>
      </tr>
      <tr>
        <td colspan="2" height="10"></td>
      </tr>
    </table>
  </blockquote>
  <misc:trxTokenRemove/>
</body>

</html>
