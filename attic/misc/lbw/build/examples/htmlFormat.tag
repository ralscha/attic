<%@ tag body-content="scriptless" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%-- Capture the body evaluation result in a variable --%> 
<jsp:doBody var="bodyRes" /> 
<%-- Convert special characters to character entities --%> 
<c:set var="escapedBody" value="${fn:escapeXml(bodyRes)}" /> 
<%-- Replace "[code]/[/code]" with "<pre>/</pre>" --%> 
<c:set var="convBody" 
value="${fn:replace(escapedBody, '[code]', '<pre>')}" /> 
<c:set var="convBody" 
value="${fn:replace(convBody, '[/code]', '</pre>')}" /> 
<%-- Output the result --%> 
${convBody} 