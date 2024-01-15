<%@ include file="WEB-INF/jsp/include/taglibs.inc"%>


<% session.removeAttribute("static_username"); %>
<tiles:insert definition=".logout" flush="true"/>
<% session.invalidate(); %>