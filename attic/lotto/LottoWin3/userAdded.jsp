<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/taglib-misc.tld"  prefix="misc" %>

<jsp:useBean id="userForm" scope="request" class="lottowin.form.UserForm"/>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodynew.html" flush="true"/>


 
<h3>Neuer Benutzer</h3>

  <blockquote>
    <table>
      <tr>
        <td ALIGN="right" colspan="2">
          <p align="left"><b>Neuer Benutzer erfolgreich eingetragen</b></td>
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
	<a href="logon.jsp">Weiter</a>
 
  </blockquote>
  <misc:trxTokenRemove/>
  
 
<%@ include file="tail.html"%>
