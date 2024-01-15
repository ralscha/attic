<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>


<jsp:include page="head.html" flush="true"/>
<jsp:include page="menu.jsp" flush="true"/>

<h3>Neuer Benutzer</h3>
<html:form action="addUser.do" method="post" focus="userID">

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
        <td bgcolor="#CCCCCC"><html:text property="userID" size="20" maxlength="30"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Vorname</em></td>
        <td bgcolor="#CCCCCC"><html:text property="firstName" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Nachname</em></td>
        <td bgcolor="#CCCCCC"><html:text property="lastName" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>E-Mail</em></td>
        <td bgcolor="#CCCCCC"><html:text property="email" size="25" maxlength="50"/></td>
      </tr>
      <tr>
        <td colspan="2" height="10"></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Passwort</em></td>
        <td bgcolor="#CCCCCC"><html:password property="password" size="16" maxlength="16"/></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Passwort best&auml;tigen</em></td>
        <td bgcolor="#CCCCCC"><html:password property="passwordConfirm" size="16" maxlength="16"/></td>
      </tr>
      <tr>
        <td ALIGN="right" colspan="2" height="10"></td>
      </tr>
      <tr>
        <td colspan="2" align="left"><html:submit value="Abschicken"/></td>
      </tr>
    </table>
<p>&nbsp;</p>


	    <logic:messagesPresent>
			<table>
			<html:messages id="error">
			<tr><td bgcolor="Red"><b><%= error %></b></td></tr>
			</html:messages>
			 </table>
        </logic:messagesPresent>


  &nbsp;

  </blockquote>
  </html:form>
<%@ include file="tail.html"%>
