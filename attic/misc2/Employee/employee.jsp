<jsp:useBean id="employee" class="EmployeeBean" scope="request" />
<html>
<head><title>employee record</title></head>
<body>
<table border="1" align="center">
<tr bgcolor="tan"><td colspan=2><font size=+3 face=arial><b>
<jsp:getProperty name="employee" property="lastName"/>,
<jsp:getProperty name="employee" property="firstName"/>
</b></font></td></tr>
<tr><td align=left valign=top>
<img height="150"
src="<jsp:getProperty name="employee" property="image"/>"></td>
<td align=left valign=top>
<table border=0>
<tr><td><b>full name:</b></td><td>
<jsp:getProperty name="employee" property="firstName"/>
<jsp:getProperty name="employee" property="lastName"/>
</td></tr>
<tr><td><b>employee id:</b></td><td>
<jsp:getProperty name="employee" property="id"/>
</td></tr>
<tr><td><b>department:</b></td><td>
<jsp:getProperty name="employee" property="department"/>
</td></tr>
<tr><td><b>e-mail:</b></td><td>
<jsp:getProperty name="employee" property="email"/>
</td></tr>
</table>
</td>
</tr>
</table>
</body>
</html>
