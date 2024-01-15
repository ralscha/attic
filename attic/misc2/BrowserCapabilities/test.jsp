<%@ page language="java" info="BrowserCapabilities Test" errorPage="error.jsp"%>
<% Capability cap = BrowserCapabilities.getBrowserCapability(request); %>
<html>
<head>
<title>BrowserCapabilities Test</title>
</head>
<body>


<table border="1" bgcolor="Silver">
<tr>
	<th align="left">Key</th>
	<th align="left">Value</th>
</tr>

<tr>
	<td>Browser</td>
	<td><%= cap.getUseragent() %></td>
</tr>

<tr>
	<td>Version</td>
	<td><%= cap.getVersion() %></td>
</tr>

<tr>
	<td>Major Version</td>
	<td><%= cap.getMajorver() %></td>
</tr>

<tr>
	<td>Minor Version</td>
	<td><%= cap.getMinorver() %></td>
</tr>
</table>
</body>
</html>
