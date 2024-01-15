<html>
<head>
<title>Excel Test</title>
</head>
<body>


<form action="test.jsp">
<table>
<tr>
<td>Kapital:</td>
<td><input type="text" name="kapital" size="10" maxlength="10"></td>
</tr>
<tr>
<td>Zins:</td>
<td><input type="text" name="zins" size="5" maxlength="5"></td>
</tr>
<tr><td colspan="2">
<input type="submit" name="Berechnen" value="Berechnen">
</td>
</tr>
</table>
</form>
<hr>
<% String kapital = request.getParameter("kapital");
   String zins = request.getParameter("zins");
   if (kapital != null) {
%>   
<img src="/f1/servlet/DisplayMyJPGServlet?kapital=<%= kapital %>&zins=<%= zins %>" border="0" alt="">
<% } %>

</body>
</html>
