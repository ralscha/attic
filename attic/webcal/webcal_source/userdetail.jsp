
<%@ page language="java" import="java.util.*, java.sql.*, ch.ess.calendar.*" info="UserManagement" errorPage="error.jsp"%>

<jsp:useBean id="userrequest" scope="page" class="ch.ess.calendar.UserManagementRequest">
<jsp:setProperty name="userrequest" property="*"/>
</jsp:useBean>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% if (login.isAdmin()) { 
   	if ((userrequest.isSubmitClicked()) && (userrequest.commitChange())) {
%>
 	   <jsp:forward page="userlist.jsp"/>
<% } %>

<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<TITLE>ESS Web Calendar: User Management</TITLE>
<link href="planner.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p>
<% if (userrequest.isUpdateMode()) { %>
	<p class="titlesmall">User Management: Edit User</p>
<% } else { %>
    <p class="titlesmall">User Management: Add New User</p>
<% } %>

<form action="userdetail.jsp" method="post">
<% if (userrequest.isUpdateMode()) { %>
<input type="hidden" name="userid" id="userid" value="<%= userrequest.getUserid() %>">
<input type="hidden" name="update" value="true">
<% } %>

<table>
<tr>
<td>ID:</td>
<% if (userrequest.isUpdateMode()) { %>
<td><%= userrequest.getUserid() %></td>
<% } else { %>
<td><input type="text" name="userid" value="<% if (userrequest.hasError()) {%><%= userrequest.getUserid() %><% } %>" size="5" maxlength="5"></td>
<% } %>

</tr>
<tr>
<td>Firstname:</td>
<% if (userrequest.isUpdateMode() || userrequest.hasError()) { %>
<td><input type="text" name="firstname" value="<%= userrequest.getFirstname() %>" size="30" maxlength="100"></td>
<% } else { %>
<td><input type="text" name="firstname" size="30" maxlength="100"></td>
<% } %>

</tr>
<tr>
<td>Name:</td>
<% if (userrequest.isUpdateMode() || userrequest.hasError()) { %>
<td><input type="text" name="name" value="<%= userrequest.getName() %>" size="30" maxlength="100"></td>
<% } else { %>
<td><input type="text" name="name" size="30" maxlength="100"></td>
<% } %>

</tr>

<% if (!userrequest.isUpdateMode()) { %>
<tr>
<td>Password:</td>
<td><input type="password" name="password" size="20" maxlength="50"></td>
</tr>
<tr>
<td>Retype Password:</td>
<td><input type="password" name="passwordretype" size="20" maxlength="50"></td>
</tr>

<% } %>
<tr>
<td>Administrator:</td>
<% if (userrequest.isUpdateMode() || userrequest.hasError()) { %>
<td><input type="checkbox" name="administrator" value="Y" <% if (userrequest.isAdmin()) { %>checked<% }%>></td>
<% } else { %>
<td><input type="checkbox" name="administrator" value="Y"></td>
<% } %>

</tr>
</table>

<p>
<% if (userrequest.isUpdateMode()) { %>   
   <input name="submit" type="submit" value="Update User" class="text">
<% } else { %>
   <input name="submit" type="submit" value="Add User" class="text">
<% } %>


</form>

<br>
<% if (userrequest.getErrorText() != null) { %>
<p class="text"><font color="Red">Error: <%= userrequest.getErrorText() %></font></p><br>
<% } %>
</body>
</HTML>
<% } %>