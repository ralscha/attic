<%@ page language="java" isErrorPage="true" %>

<% String errorMsg = ch.ess.cal.web.ErrorPrinter.logError(pageContext, exception); %>

<html>
  <head>
  <title></title>
</head>
<body>
<pre>
   <%= errorMsg %>
</pre>
</body>
</html>