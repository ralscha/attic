<%@ page language="java" import="org.apache.struts.action.*" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-error.tld" prefix="error" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodynew.html" flush="true"/>


<h3>Bitte anmelden</h3>
<struts:form action="logon.do" scope="request" name="logonForm" focus="username" type="lottowin.form.LogonForm">
<blockquote>
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
<br>

<error:errorsPresent>
<table>
  <error:listErrors>
    <tr><td bgcolor="Red"><b><error:errorMessage /></b></td></tr>
  </error:listErrors>
</table>
</error:errorsPresent>
</blockquote>

</struts:form>


<%@ include file="tail.html"%>
