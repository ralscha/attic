
<%@ page language="java" import="com.codestudio.util.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="UserManagement" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% if (login.isAdmin()) { %>
<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<TITLE>ESS Web Calendar: User Management</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</head>
<BODY>
<%@ include file="menu.jsp"%>
<p class="titlesmall">User Management</p>
<% 
  
  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  UsersTable usersTable = new UsersTable(conn);
  AppointmentsTable appTable = new AppointmentsTable(conn);
  
  String deleteUserid = request.getParameter("delete");
  if (deleteUserid != null) {
  	  appTable.deleteAllfromUser(deleteUserid);
      usersTable.delete("userid = '" + deleteUserid + "'");
  }
    
  try {
%>  
   
<table width="50%" border="1">
<tr>
<th align="left">ID</th>
<th align="left">Firstname</th>
<th align="left">Name</th>
<th>Administrator</th>
<th></th>
<th></th>
</tr>

<%
Iterator it = usersTable.select(null, "firstname");
while (it.hasNext()) {
	Users user = (Users)it.next();
%>

<tr>
<td><%= user.getUserid() %></td>
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
<% 
} finally {
   manager.returnConnection(conn);
} %>
</body>

</HTML>
<% } %>