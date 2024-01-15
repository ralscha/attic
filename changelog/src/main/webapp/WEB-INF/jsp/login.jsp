<!DOCTYPE html> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>CT ChangeLog</title>
  <link rel="icon" type="image/png" href="resources/images/favicon16.png" sizes="16x16">
  <link rel="icon" type="image/png" href="resources/images/favicon32.png" sizes="32x32">
  <link rel="icon" type="image/png" href="resources/images/favicon48.png" sizes="48x48"> 
  <style>
    <%@ include file="loader.css"%>
  </style> 
  ${applicationScope.login_css} 
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
	
  <script>
    var app_context_path = '<%= request.getContextPath() %>';
  </script>		
	
  <% Locale locale = RequestContextUtils.getLocale(request); %>
  <spring:eval expression="@environment.acceptsProfiles('development')" var="isDevelopment" />    
  <% if ((Boolean)pageContext.getAttribute("isDevelopment")) { %>
    <script src="i18n.js"></script>
  <% } else { %>
  <script src="i18n-<%= locale %>_<spring:eval expression='@environment["application.version"]'/>.js"></script>
  <% } %>
		
  ${applicationScope.login_js}
  
  <% if (locale != null && locale.getLanguage().toLowerCase().equals("de")) { %>
    <script src="<%= request.getContextPath() %>/resources/extjs/<spring:eval expression='@environment["extjs.version"]'/>/locale/ext-lang-de.js"></script>
  <% } %>	
    
  <script>
  Ext.onReady(function() {
    Ext.fly('circularG').destroy();
	<% if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) { /**/ %> 
	  Changelog.ux.window.Notification.error('Fehler', 'Anmeldung fehlgeschlagen');
	  <% session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION"); %>
	<% } %>
  });
  </script>

</body>
</html>