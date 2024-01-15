<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-error.tld" prefix="error" %>
<%@ taglib uri="/WEB-INF/taglib-misc.tld"  prefix="misc" %>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodynew.html" flush="true"/>

<h3>Neuer Benutzer</h3>
<struts:form action="addUser.do" scope="request" method="post" name="userForm" focus="userID" type="lottowin.form.UserForm">
  <misc:trxToken/>
  <p></p>
  <blockquote>
    <table>
      <tr>
        <td ALIGN="right" colspan="2">
          <p align="left">Bitte alle Felder ausf&uuml;llen</td>
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

  </blockquote>
  </struts:form>
<%@ include file="tail.html"%>
