<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<bean:message key="LoggedOut"/>
<p>&nbsp;</p>
<html:link page="/default.do"><bean:message key="BackToApplication"/></html:link>
