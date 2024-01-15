<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>


<logic:present parameter="error">

<div id="messageDiv" class="errormessage">
<table>
  <tr><th align="left"><bean:message key="common.errormessages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

  <tr><td>&nbsp;<bean:message key="login.loginFailed"/>&nbsp;&nbsp;</td></tr>    

  <tr><td>&nbsp;</td></tr>
</table>
</div>

</logic:present>
