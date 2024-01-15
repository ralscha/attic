
<%@ Language=VBScript %>
<% option explicit 
   on error resume next %>

<!-- #include file="adovbs.inc" -->

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


  dim query, cnn, userrs, username, userfirstname

  if Request.QueryString("logout") <> "" then  
    Session("UserID") = ""
    Session.Abandon  
  else
     
	if Request("userid") = "user" and Request("password") = "user" then
		Session("UserID") = "user"               
	else
	       
	  set cnn = Server.CreateObject("ADODB.Connection")
	  cnn.open("DSN=PlannerDb2")
	  set userrs = Server.CreateObject("ADODB.RecordSet")
  
	  query = "select userid, firstname, name from users where userid = '" & Request("userid") & "' AND password = '" & Request("password") & "'"
	  userrs.Open query, cnn, adOpenKeyset, adLockOptimistic	  	      
  
  
      
	  if userrs.RecordCount > 0 then    
	    username = userrs("Name")
	    userfirstname = userrs("Firstname")
	    Session("UserID") = Request("userid")        
	    Session.Timeout = 20
	  else
	    Session("UserID") = ""
	  end if
  
	  userrs.close
	  cnn.Close
	  
	end if
	
  end if 'if logout
%>




<table width="100%">
<tr>
<td align="left" valign="top"><p class="title">Date Planner</p></td>

<% if Session("UserID") = "user" then %>

<td>&nbsp;</td>
<td align="right" class="text">
  <a href="summary.asp" target="Main" class="text">Summary</a>&nbsp;
  <a href="usermanagement.asp" target="Main" class="text">User Management</a>&nbsp;
  <a href="menu.asp?logout=true" target="Menu" class="text" onClick="javascript:twoFrames('Menu.asp',0,'Summary.asp',1)">Logout</a>


<% else if Session("UserID") <> "" then %>

<td>&nbsp;</td>
<td align="center" class="text">
  Logged in: <%= userfirstname & " " & username %>&nbsp;
</td>
<td align="right" class="text">
  <a href="summary.asp" target="Main" class="text">Summary</a>&nbsp;
  <a href="adddate.asp" target="Main" class="text">Manage Dates</a>&nbsp;
  <a href="menu.asp?logout=true" target="Menu" class="text" onClick="javascript:twoFrames('Menu.asp',0,'Summary.asp',1)">Logout</a>

<% else %>

<td align="right" valign="bottom" class="text">
<form action="menu.asp" method="post">
User ID:&nbsp;<input type="text" name="userid" size="10" maxlength="30">&nbsp;&nbsp;
Password:&nbsp;<input type="password" name="password" size="10" maxlength="30" id="password">&nbsp;
<INPUT type="submit" value="Login" name="Login">
</form>

<% end if
   end if %>

</td>
</tr>
</table>

</body>
</html>
