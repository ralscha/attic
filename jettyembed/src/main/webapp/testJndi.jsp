<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@page import="javax.naming.InitialContext"%>
<html>
<head>
</head>
<body>
	<p>
		<%
			InitialContext ic = new InitialContext();
		
		%>
	
	<p>
		<%=ic.lookup("java:comp/env/aSimpleEntry")%>
	</p>

	<p>
		<%=ic.lookup("java:comp/env/jdbc/ds")%>
	</p>
	
	
</body>
</html>