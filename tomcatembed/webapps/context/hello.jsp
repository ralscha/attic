<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.Date"%>
<html>
  <head><title>Export Dir</title></head>
  <body>
    <p>
    <% InitialContext ic = new InitialContext(); %>
    <%= (String)ic.lookup("java:comp/env/app/exportDir") %>
    </p>
  </body>
</html>