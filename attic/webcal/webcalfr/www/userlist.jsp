<%@ page language="java" import="java.util.*, java.sql.*, ch.ess.calendar.db.*" info="UserManagement" errorPage="error.jsp"%>
<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% if (login.isAdmin()) { %>
<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<TITLE>ESS Web Calendar: Gestion des utilisateurs</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p class="titlesmall">Gestion des utilisateurs</p>
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
<th align="left" bgcolor="#D0D0D0">Identification</th>
<th align="left" bgcolor="#D0D0D0">Prénom</th>
<th align="left" bgcolor="#D0D0D0">Nom</th>
<th align="left" bgcolor="#D0D0D0">E-Mail</th>
<th bgcolor="#D0D0D0">Administrateur</th>
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
<td><%= user.getEmail() %></td>
<% if (user.isAdmin()) { %>
<td align="center"><img src="images/check.gif" width="20" height="15" border="0" alt="Y"></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>

<td align="center"><a href="userlist.jsp?delete=<%= user.getUserid() %>">Supprimer</a></td>
<td align="center"><a href="userdetail.jsp?edituserid=<%= user.getUserid() %>">Modifier</a></td>
</tr>
<% } %>
</table>
<p>
<a href="userdetail.jsp" class="text">Ajouter un utilisateur</a>
<% if (ch.ess.calendar.util.CheckLicense.isDemo()) { %>
<p class="text"><i>Suppression d'un utilisateur impossible dans le version d&eacute;mo</i></p>

<% } %>
 
</body>

</HTML>
<% } %>