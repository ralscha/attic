<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<logic:messagesPresent>
<html:messages id="error">
<table>
  <tr><td class="error"><bean:write name="error" filter="false"/></td></tr>
</table>
</html:messages>
</logic:messagesPresent>

<logic:messagesPresent message="true">
<html:messages id="msg" message="true">
<table>
  <tr><td class="message"><bean:write name="msg" filter="false"/></td></tr>
</table>
</html:messages>
</logic:messagesPresent>

