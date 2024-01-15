<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ch.ess.ntlmtest.*"%>

<html>
<body>

<h1><%= request.getRemoteUser() %></h1>

<% 
  DemoBean db = (DemoBean)session.getAttribute("counter");
  if (db == null) {
    db = new DemoBean();
    db.inc();
    session.setAttribute("counter", db);
  } else {
    db.inc();
  }
%>

<h2>Hello World!</h2>
<p>You are Visitory No: <%= db.getCounter() %>
</body>
</html>
