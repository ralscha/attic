<%@ tag body-content="scriptless" %> 
<%@ attribute name="var" rtexprvalue="false" required="true" %> 
<%@ variable name-from-attribute="var" alias="body" variable-class="java.lang.String" scope="AT_END" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<jsp:doBody var="body" />