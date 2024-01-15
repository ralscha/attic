
<%@ page language="java" import="com.codestudio.util.*, java.util.*, java.sql.*" info="UserManagement" errorPage="error.jsp"%>

<jsp:useBean id="newuser" scope="page" class="Users" />
<jsp:setProperty name="newuser" property="*"/>

<HTML>
<HEAD>
<TITLE>DatePlanner: User Management</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</head>
<BODY>
<p class="titlesmall">User Management</p>
<% 
  String statustext = "";
  boolean inputerror = false;
  
  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  UsersTable usersTable = new UsersTable(conn);

  try {
  if (request.getParameter("DeleteUser") != null) {
	String check[] = request.getParameterValues("check");
	if (check != null) {
	    statustext = "<b>User(s): ";
		

		for (int i = 0; i < check.length; i++) {		
			usersTable.delete("userid = '" + check[i]+ "'");
			statustext = statustext + check[i];
			if (i < check.length-1) {
				statustext = statustext + ", ";
			} else {
				statustext = statustext + " ";
			}	
		}
		statustext = statustext + "deleted</b>";
	}   	
  }

  if (request.getParameter("AddUser") != null) {

	
    if ( (newuser.getUserID() != null) && (newuser.getUserID().trim() != "") && 
	     (newuser.getPassword() != null) && (newuser.getPassword().trim() != "") ) {

		Iterator it = usersTable.select("userid = '" + newuser.getUserID() + "'");
		if (!it.hasNext()) {
			usersTable.insert(newuser);
		} else {
			statustext = "<b>ID exists already</b><p>";
			inputerror = true;
		}		
	} else {
		statustext = "<b>Wrong Input</b><p>";
    	inputerror = true;
	}
  }

%>  
   
<form action="usermanagement.jsp" method="post">
<table border="1">
<tr>
<th>ID</th>
<th>Firstname</th>
<th>Name</th>
</tr>

<%
Iterator it = usersTable.select(null, "firstname");
while (it.hasNext()) {
	Users user = (Users)it.next();
%>

<tr>
<td><%= user.getUserID() %></td>
<td><%= user.getFirstname() %></td>
<td><%= user.getName() %></td>
<td><input type="checkbox" name="check" value="<%= user.getUserID() %>"></td>
</tr>


<% }
} finally {
   manager.returnConnection(conn);
} %>
</table>
<p>
<input name="DeleteUser" type="submit" value="Delete User" class="text">



<p></p>
<p>&nbsp;</p>

<table border="1">
<tr>
<td>ID:</td>
<td><input type="text" name="userID" size="5" maxlength="5" <% if (inputerror) { %> value="<%= newuser.getUserID() %>" <% } %>></td>
</tr>
<tr>
<td>Firstname:</td>
<td><input type="text" name="firstname" size="30" maxlength="100" <% if (inputerror) { %> value="<%= newuser.getFirstname() %>" <% } %>></td>
</tr>
<tr>
<td>Name:</td>
<td><input type="text" name="name" size="30" maxlength="100" <% if (inputerror) { %> value="<%= newuser.getName() %>" <% } %>></td>
</tr>
<tr>
<td>Password:</td>
<td><input type="password" name="password" size="20" maxlength="30"></td>
</tr>
</table>

<p>
<input name="AddUser" type="submit" value="Add User" class="text">



</form>
<br>
<% if (statustext != "") { %>
<p class="text">Status: <%= statustext %></p><br>
<% } %>

</body>

</HTML>
