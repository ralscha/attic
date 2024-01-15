
<%@ page language="java" import="java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Manage Overview" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<HTML>
<HEAD>
<TITLE>ESS Web Calendar: Categories</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">

</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p class="titlesmall">Gestion</p>
<% if (login.isLogonOK()) { %>
<ul>
	<li><a href="userlist.jsp" class="text">Utilisateurs</a></li>
	<li><a href="categoriesmanagement.jsp" class="text">Catégories</a></li>
</ul>
<% } %>
</body>
</HTML>
