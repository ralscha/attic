<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<% session.removeAttribute("static_username"); %>
<tiles:insert definition=".logout" flush="true"/>
<% session.invalidate(); %>