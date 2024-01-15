<%@ page language="java" info="ServerGif"%>
<jsp:useBean id="input" scope="session" class="InputData"/>
<jsp:setProperty name="input" property="*"/>


<html>
<head>
<title>ServerGifs</title>
<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<h1 style="font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;">GIF server creation</h1>
<p>

<img src="servlet/GifServlet" border="0" alt="" width="<%= input.getWidth() %>" height="<%= input.getHeight() %>">

<form action="index.jsp" method="post">
<table>
<tr><td>Type: </td><td><select name="type" size="1">
			<option value="bar" <%= input.getType().equals(InputData.BAR) ? "SELECTED" : ""%>>Bar Chart</option>
			<option value="line" <%= input.getType().equals(InputData.LINE) ? "SELECTED" : ""%>>Line Chart</option>
			<option value="pie" <%= input.getType().equals(InputData.PIE) ? "SELECTED" : ""%>>Pie Chart</option>
</select></td></tr>
<tr><td>Width: </td><td><input type="text" name="width" value="<%= input.getWidth() %>" size="5"></td></tr>
<tr><td>Height: </td><td><input type="text" name="height" value="<%= input.getHeight() %>" size="5"></td></tr>
<tr><td>Values: </td><td><input type="text" name="commaSeparatedValues" value="<%= input.getCommaSeparatedValues() %>" size="50"></td></tr>
<tr><td>3D Mode: </td><td><input type="checkbox" name="mode3d" value="1" <% if (input.is3D()) { %>checked><% } %></td></tr>
<tr><td colspan="2"><input type="submit" name="Submit" value="Update"></td></tr>
</table>
</form>
</body>
</html>

