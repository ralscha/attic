<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<c:choose>
  <c:when test="${not empty cookie.addressbook_remember}">
	<html>
	<head>
	<META HTTP-EQUIV="Refresh" CONTENT="0; URL=<c:url value="/login"/>"> 
	<title></title>
	</head>
	<body onLoad="javascript:clientDetect();document.forms[0].submit();">
	<%@ include file="WEB-INF/jsp/clientdetect.inc"%>
	<form action="login" method="post">
	<%@ include file="WEB-INF/jsp/clientdetecth.inc"%>
	</form>
	
	</body>
	</html>
  </c:when>
  <c:otherwise>
   <tiles:insert definition=".login" flush="true"/>
  </c:otherwise>
</c:choose>

