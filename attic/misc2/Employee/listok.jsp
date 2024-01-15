<%@ page import="java.util.*" %>
<html>
<body>
<b>Current Employees</b>
<ul>
<%
  Vector v = (Vector)request.getAttribute("list");
  Iterator i= v.iterator();
  while (i.hasNext()) {
     EmployeeBean employee = (EmployeeBean)i.next();
%>
<li>
<a href="FetchEmployeeServlet?cmd=get&id=<%= employee.getId() %>"><%= employee.getLastName() %>,<%= employee.getFirstName() %></a>
<% } %>
</ul>
</body>
</html>

