
<%@ page language="java" import="com.codestudio.util.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Manage Overview" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<HTML>
<HEAD>
<TITLE>ESS Web Calendar: Categories</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">

</head>
<BODY>
<%@ include file="menu.jsp"%>
<p class="titlesmall">Manage</p>
<% if (login.isLogonOK()) { %>
<ul>
	<li><a href="userlist.jsp" class="text">Users</a></li>
	<li><a href="categoriesmanagement.jsp" class="text">Categories</a></li>
</ul>
<% } %>
</body>
</HTML>
