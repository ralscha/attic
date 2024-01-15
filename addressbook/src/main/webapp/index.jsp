<!DOCTYPE html> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.Locale"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>ab-template</title>	
  <link rel="icon" type="image/png" href="resources/images/Shape-Cube-16.png" sizes="16x16">
  <link rel="icon" type="image/png" href="resources/images/Shape-Cube-32.png" sizes="32x32">
  <link rel="icon" type="image/png" href="resources/images/Shape-Cube-48.png" sizes="48x48">  
  <style>
    <%@ include file="loader.css"%>
  </style>
  ${applicationScope.app_css}
</head>
<body>
  <div id="circularG">
  <div id="circularG_1" class="circularG">
  </div>
  <div id="circularG_2" class="circularG">
  </div>
  <div id="circularG_3" class="circularG">
  </div>
  <div id="circularG_4" class="circularG">
  </div>
  <div id="circularG_5" class="circularG">
  </div>
  <div id="circularG_6" class="circularG">
  </div>
  <div id="circularG_7" class="circularG">
  </div>
  <div id="circularG_8" class="circularG">
  </div>
  </div>
		     
  <% Locale locale = RequestContextUtils.getLocale(request); %>
  <spring:eval expression="@environment.acceptsProfiles('development')" var="isDevelopment" />
  <% if ((Boolean)pageContext.getAttribute("isDevelopment")) { %>
    <script src="i18n.js"></script>
  <% } else { %>
    <script src="i18n-<%= locale %>_<spring:eval expression='@environment["application.version"]'/>.js"></script>
  <% } %>

  ${applicationScope.app_js}
  
  <% if (locale != null && locale.getLanguage().toLowerCase().equals("de")) { %>
    <script src="<%= request.getContextPath() %>/resources/extjs-gpl/<spring:eval expression='@environment["extjs.version"]'/>/locale/ext-lang-de.js"></script>
  <% } %>	
  
</body>
</html>