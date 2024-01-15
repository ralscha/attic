<%@ page language="java" isErrorPage="true" %>
<%@ page import="java.util.*, java.io.*" %>
<%@ page import="javax.mail.*, javax.mail.internet.*" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html><head><title>Webcal Error</title>
<html:base/>
<link rel=stylesheet type="text/css" href="<c:url value='/styles/global.css'/>">
</head>
<body>
<pre>
<%@ include file="WEB-INF/jsp/include/error.inc"%>
</pre>
</body>
</html>