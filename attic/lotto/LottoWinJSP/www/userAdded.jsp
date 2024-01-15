<%@ page language="java" errorPage="error.jsp"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>

<jsp:useBean id="userForm" scope="request" class="ch.sr.lottowin.form.UserForm"/>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="menu.jsp" flush="true"/>


 
<h3>Neuer Benutzer</h3>

  <blockquote>
    <table>
      <tr>
        <td ALIGN="right" colspan="2">
          <p align="left"><b>Neuen Benutzer erfolgreich eingetragen</b></td>
      </tr>
      <tr>
        <td ALIGN="right" colspan="2" height="10"></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Benutzer ID</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="userForm" property="userID"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Vorname</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="userForm" property="firstName"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>Nachname</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="userForm" property="lastName"/></b></td>
      </tr>
      <tr>
        <td ALIGN="right" bgcolor="#CCCCCC"><em>E-Mail</em></td>
        <td bgcolor="#CCCCCC"><b><jsp:getProperty name="userForm" property="email"/></b></td>
      </tr>
      <tr>
        <td colspan="2" height="10"></td>
      </tr>
    </table>
 	<p>&nbsp;</p>
	<html:link href="logon.jsp">Weiter</html:link>
 
  </blockquote>

  
 
<%@ include file="tail.html"%>
