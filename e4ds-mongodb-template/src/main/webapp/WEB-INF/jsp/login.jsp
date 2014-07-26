<!doctype html> 
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Locale"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
	<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>" /> 
    <title>e4ds-template-mongodb</title>

        <style type="text/css">
        <%@ include file="loader.css"%>
	</style>

    <link rel="stylesheet" type="text/css" href="http://cdn.sencha.com/ext-4.1.1-gpl/resources/css/ext-all.css">
    <!-- 
    <link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css?v=<spring:eval expression='@environment["extjs.version"]'/>">    
     -->
    
    <spring:eval expression="@environment.acceptsProfiles('development')" var="isDevelopment" />    
    <c:if test="${isDevelopment}">
        <link rel="stylesheet" type="text/css" href="resources/css/app-sprite.css">
		<link rel="stylesheet" type="text/css" href="ux/css/Notification.css">
    </c:if>
    <c:if test="${not isDevelopment}">
      <link rel="stylesheet" type="text/css" href="wro/login-<spring:eval expression='@environment["application.version"]'/>.css" />
    </c:if>
		
</head>
<body>
    <!--[if lt IE 8]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
	<div id="appLoadingIndicator">
		<span></span>
		<span></span>
		<span></span>
	</div>
	
    <spring:eval expression="@environment.acceptsProfiles('development')" var="isDevelopment" />    
    <c:if test="${isDevelopment}">	    
	    <script src="http://cdn.sencha.com/ext-4.1.1-gpl/ext-all-debug.js"></script>
		<!-- 
		<script src="extjs/ext-all-debug.js?v=<spring:eval expression='@environment["extjs.version"]'/>"></script>
		 -->
		
		<script src="i18n.js"></script>
		<script src="ux/window/Notification.js"></script>
		<script src="login.js"></script>
    </c:if>
    <c:if test="${not isDevelopment}">
        <script src="i18n-<spring:eval expression='@environment["application.version"]'/>.js"></script>
		<script src="http://cdn.sencha.com/ext-4.1.1-gpl/ext-all.js"></script>
		<!-- 
		<script src="extjs/ext-all.js?v=<spring:eval expression='@environment["extjs.version"]'/>"></script> 
		-->         
        <script src="wro/login-<spring:eval expression='@environment["application.version"]'/>.js"></script>        
    </c:if>
	    
	<% Locale locale = RequestContextUtils.getLocale(request); %>
    <% if (locale != null && locale.getLanguage().toLowerCase().equals("de")) { %>
      <script src="http://cdn.sencha.com/ext-4.1.1-gpl/locale/ext-lang-de.js"></script>
      <!-- 
      <script src="extjs/locale/ext-lang-de.js?v=<spring:eval expression='@environment["extjs.version"]'/>"></script>
       -->
    <% } %>	
    
	   <script type="text/javascript">
	   Ext.onReady(function() {
		Ext.fly('appLoadingIndicator').destroy();
		<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}"> 
	     Ext.ux.window.Notification.error(i18n.error, i18n.login_failed);
		</c:if> 	     
	   });
	   </script>
	 
</body>
</html>