<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<logic:messagesPresent message="true" property="org.apache.struts.action.GLOBAL_ERROR">
<html:messages id="error" message="true" property="org.apache.struts.action.GLOBAL_ERROR">
<table class="error">
  <tr><td><%= error %></td></tr>
</table>
</html:messages>
</logic:messagesPresent>

<logic:messagesPresent>
<html:messages id="error">
<table class="error">
  <tr><td><%= error %></td></tr>
</table>
</html:messages>
</logic:messagesPresent>

<logic:messagesPresent message="true" property="org.apache.struts.action.GLOBAL_MESSAGE">
<html:messages id="msg" message="true" property="org.apache.struts.action.GLOBAL_MESSAGE">
<table class="ok">
  <tr><td><%= msg %></td></tr>
</table>
</html:messages>
</logic:messagesPresent>

