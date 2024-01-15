<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<logic:messagesPresent>
<div id="messageDiv" class="errormessage">
<table>
  <tr><th align="left"><bean:message key="common.errormessages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

<html:messages id="error">
  <tr><td>&nbsp;<c:out value="${error}" />&nbsp;&nbsp;</td></tr>    
</html:messages>

  <tr><td>&nbsp;</td></tr>
</table>
</div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
<div id="messageDiv" class="message">
<table>
  <tr><th align="left"><bean:message key="common.messages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

<html:messages id="msg" message="true">
  <tr><td>&nbsp;<c:out value="${msg}" />&nbsp;&nbsp;</td></tr>    
</html:messages>

  <tr><td>&nbsp;</td></tr>
</table>
</div>
</logic:messagesPresent>



