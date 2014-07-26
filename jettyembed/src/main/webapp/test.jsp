<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Simple JSP</title>
</head>
<body>
<p>

<c:if test="${not empty param.test}">
request contains test parameter
</c:if>

<c:if test="${empty param.test}">
no request parameter
</c:if>
</p>
<p>
TEST=${param.test}
</p>
</body>
</html>