
<%@ page language="java" import="com.codestudio.util.*, java.util.*" info="Menu" errorPage="error.jsp"%>


<html>
<head>
<title>Date Planner</title>
<link rel="STYLESHEET" type="text/css" href="planner.css">

  <script language="JavaScript">
  <!--
   function twoFrames(URL1, F1, URL2,F2)
   {  
    parent.frames[F1].location.href=URL1;
    parent.frames[F2].location.href=URL2;
   }
  //-->
  </script> 
  
</head>
<body>



<%

	String username = null;
	String userfirstname = null;
	String userid = "";
	
	if (request.getParameter("logout") != null) {
		session.invalidate();				
	} else  {
		userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if ((userid != null) && (password != null)) {
			if ( (userid.equals("user"))  && (password.equals("user")) ) {
				session.setAttribute("UserID", "user");
				userid = "user";
			} else {
				String query = 	"select userid, firstname, name from Users where UserID = '" + userid + "' AND Password = '" + password + "'";
				Hashtable[] result = SQLUtil.getInstance().executeSql(query);
				if ((result != null) && (result.length > 0 )) {
					Hashtable row = result[0];
					username = (String)row.get("name");
					userfirstname = (String)row.get("firstname");
					userid = (String)row.get("userid");
					session.setAttribute("UserID", userid);
					
				} else {
					session.setAttribute("UserID", "");
					userid = "";
				}
	
			}
		} else {
			userid = "";
		}
	}
	
%>




<table width="100%">
<tr>
<td align="left" valign="top"><p class="title">Date Planner</p></td>

<% if (userid.equals("user")) { %>

<td>&nbsp;</td>
<td align="right" class="text">
  <a href="summary.jsp" target="Main" class="text">Summary</a>&nbsp;
  <a href="usermanagement.jsp" target="Main" class="text">User Management</a>&nbsp;
  <a href="menu.jsp?logout=true" target="Menu" class="text" onClick="javascript:twoFrames('menu.jsp',0,'summary.jsp',1)">Logout</a>


<% } else if (!userid.equals("")) { %>

<td>&nbsp;</td>
<td align="center" class="text">
  Logged in: <%= userfirstname + " " + username %>&nbsp;
</td>
<td align="right" class="text">
  <a href="summary.jsp" target="Main" class="text">Summary</a>&nbsp;
  <a href="adddate.jsp" target="Main" class="text">Manage Dates</a>&nbsp;
  <a href="menu.jsp?logout=true" target="Menu" class="text" onClick="javascript:twoFrames('menu.jsp',0,'summary.jsp',1)">Logout</a>

<% } else {%>

<td align="right" valign="bottom" class="text">
<form action="menu.jsp" method="post">
User ID:&nbsp;<input type="text" name="userid" size="10" maxlength="30">&nbsp;&nbsp;
Password:&nbsp;<input type="password" name="password" size="10" maxlength="30" id="password">&nbsp;
<INPUT type="submit" value="Login" name="Login">
</form>

<% } %>

</td>
</tr>
</table>
</body>
</html>
