<%@ page isErrorPage="true" %>
<html>
The ERROR page: <%= exception.getMessage() %>
<hr>
<% exception.printStackTrace(); %>
<a href="faqtool">Main Menu</a>
</html>