<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>webapp</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
</head>

<body>

<jsp:useBean id="counter" scope="session" class="ch.rasc.webapp.DemoBean" />
<% counter.inc(); %>

<h2>Hello World!</h2>
<p>You are Visitory No: ${sessionScope.counter.counter}</p>
</body>
</html>
