
<%@ Language=VBScript %>
<% option explicit 
   on error resume next %>

<!-- #include file="adovbs.inc" -->


<HTML>
<HEAD>
<TITLE>DatePlanner: User Management</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</head>
<BODY>
<p class="titlesmall">User Management</p>
<% 

  dim cnn, userrs, length, i, query, inputerror, statustext
  
  inputerror = false  
  statustext = ""
  
  set cnn = Server.CreateObject("ADODB.Connection")
  cnn.open("DSN=PlannerDb2")

  if Request("DeleteUser") <> "" then
	length = Request.Form("check").Count
	if (length >= 1) then 
	    statustext = "<b>User(s): "
		for i = 1 to length  
			cnn.Execute("delete from users where userid = '" & Request.Form("check").Item(i) & "'")
			statustext = statustext & Request.Form("check").Item(i) 
			if (i < length) then
			  statustext = statustext & ", "
			else
			  statustext = statustext & " "
			end if
		next
		statustext = statustext & "deleted</b>"
	end if
  end if


  set userrs = Server.CreateObject("ADODB.RecordSet")

  if Request("AddUser") <> "" then
    

    if trim(Request("ID")) <> "" AND trim(Request("Password")) <> "" then
  
        query = "select userid,firstname,name,password from users where userid = '" & Request("ID") & "'"
  
        userrs.open query, cnn, adOpenKeyset,adLockOptimistic        
  
		if userrs.RecordCount = 0 then
  
			userrs.addnew
			userrs("userid") = Request("ID")
			userrs("firstname") = Request("firstname")
			userrs("name") = Request("name")
			userrs("password") = Request("Password")
					
			userrs.update       
			statustext = "<b>Add User: " & Request("ID") & " OK</b><p>"
		
		else
			statustext = "<b>ID exists already</b><p>"
			inputerror = true
		end if
		userrs.Close		
    else 
        statustext = "<b>Wrong Input</b><p>"
        inputerror = true
    end	if
    
  end if
%>  
   
<form action="usermanagement.asp" method="post">
<table border="1">
<tr>
<th>ID</th>
<th>Firstname</th>
<th>Name</th>
</tr>

<%

query = "select userid, firstname, name from users order by firstname"
userrs.open query, cnn, adOpenStatic, adLockOptimistic
do until userrs.eof %>

<tr>
<td><%= userrs("userid") %></td>
<td><%= userrs("firstname") %></td>
<td><%= userrs("name") %></td>
<td><input type="checkbox" name="check" value="<%= userrs("userid") %>"></td>
</tr>


<% userrs.movenext
  loop

userrs.close
%>
</table>
<p>
<input name="DeleteUser" type="submit" value="Delete User" class="text">



<p></p>
<p>&nbsp;</p>

<table border="1">
<tr>
<td>ID:</td>
<td><input type="text" name="ID" size="5" maxlength="5" <% if (inputerror) then %> value="<%= Request("ID") %>" <%end if%>></td>
</tr>
<tr>
<td>Firstname:</td>
<td><input type="text" name="firstname" size="30" maxlength="100" <% if (inputerror) then %> value="<%= Request("firstname") %>" <%end if%>></td>
</tr>
<tr>
<td>Name:</td>
<td><input type="text" name="name" size="30" maxlength="100" <% if (inputerror) then %> value="<%= Request("name") %>" <%end if%>></td>
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
<% if statustext <> "" then %>
<p class="text">Status: <%= statustext %></p><br>
<% end if 
  cnn.Close
%>

</body>

</HTML>
