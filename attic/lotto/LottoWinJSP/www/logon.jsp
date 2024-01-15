<%@ page language="java" import="org.apache.struts.action.*" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>


<jsp:include page="head.html"/>
<jsp:include page="menu.jsp"/>


<h3>Anmeldung</h3>
<html:form action="logon.do" focus="username">
<blockquote>
<table border="0">

  <tr>
    <td align="right">Benutzer ID</td>
    <td align="left"><html:text property="username" size="20" maxlength="50"/>
    </td>
  </tr>

  <tr>
    <td align="right">Passwort</td>
    <td align="left"><html:password property="password" size="20" maxlength="50"/></td>
  </tr>
 
  <tr>
    <td colspan="2" align="center"><html:submit property="submit">Anmelden</html:submit></td>
  </tr>

</table>
<br>


	    <logic:messagesPresent>
			<table>
			<html:messages id="error">
			<tr><td bgcolor="Red"><b><%= error %></b></td></tr>
			</html:messages>
			 </table>
        </logic:messagesPresent>


</blockquote>

</html:form>
<p>&nbsp;</p>
<p>&nbsp;</p>
Noch kein Benutzerkonto? <br>
Unter dem Menupunkt "Neuer Benutzer" kannst du dich eintragen.
<p>&nbsp;</p>
Nur mal ausprobieren?<br>
Mit Benutzer Id <strong>demo</strong> und Passwort <strong>demo</strong> kannst du herumspielen.
<%@ include file="tail.html"%>
