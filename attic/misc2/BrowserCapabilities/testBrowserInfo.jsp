<%@ page language="java" info="BrowserCapabilities Test" errorPage="error.jsp"%>
<% BrowserInfo info = BrowserInfoFactory.getBrowserInfo(request, response); %>
<html>
<head>
<title>BrowserInfo Test</title>
</head>
<body>


<table border="1" bgcolor="Silver">
<tr>
	<th align="left">Key</th>
	<th align="left">Value</th>
</tr>

<tr>
	<td>Useragent</td>
	<td><%= info.getUserAgent() %></td>
</tr>

<% if (info.isJavascriptOn()) { %>
   <tr>
		<td>JavaScript</td>
		<td>Enabled</td>   
   </tr>
	<tr>
		<td>Screen Width</td>
		<td><%= info.getScreenWidth() %></td>
	</tr>
	<tr>
		<td>Screen Height</td>
		<td><%= info.getScreenHeight() %></td>
	</tr>

<% } else { %>
	<tr>
		<td>JavaScript</td>
		<td>Disabled</td>   
	</tr>	
<% } %>
</table>

</body>
</html>
