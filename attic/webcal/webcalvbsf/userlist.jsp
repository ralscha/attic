<%@ page language="java" import="java.util.*, java.sql.*, ch.ess.calendar.db.*" info="UserManagement" errorPage="error.jsp"%>
<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% if (login.isAdmin()) { %>
<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<TITLE>ESS Web Calendar: User Management</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p class="titlesmall">User Management</p>
<% 
  
  UsersTable usersTable = new UsersTable();
  AppointmentsTable appTable = new AppointmentsTable();
  
  String deleteUserid = request.getParameter("delete");
  if (deleteUserid != null) {
  	  appTable.deleteAllfromUser(deleteUserid);
      usersTable.delete("userid = '" + deleteUserid + "'");
  }    
%>  
   
<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">ID</th>
<th align="left" bgcolor="#D0D0D0">Firstname</th>
<th align="left" bgcolor="#D0D0D0">Name</th>
<th bgcolor="#D0D0D0">Administrator</th>
<th></th>
<th></th>
</tr>

<%
Iterator it = usersTable.select(null, "firstname");
while (it.hasNext()) {
	Users user = (Users)it.next();
%>

<tr bgcolor="#E6E6E6">
<th align="left"><%= user.getUserid() %></th>
<td><%= user.getFirstname() %></td>
<td><%= user.getName() %></td>
<% if (user.isAdmin()) { %>
<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>
<td align="center"><a href="userlist.jsp?delete=<%= user.getUserid() %>">Delete</a></td>
<td align="center"><a href="userdetail.jsp?edituserid=<%= user.getUserid() %>">Edit</a></td>
</tr>
<% } %>
</table>
<p>
<a href="userdetail.jsp" class="text">Add new user</a>
<% if (ch.ess.calendar.util.CheckLicense.isDemo()) { %>
<p class="text"><i>Delete user not supported in demo version</i></p>

<% } %>
 
</body>

</HTML>
<% } %>