<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>


<logic:present parameter="error">
<table>
  <tr><td class="error"><bean:message key="LoginFailed"/></td></tr>
</table>
</logic:present>
