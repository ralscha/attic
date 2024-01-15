<%@ page language="java" isErrorPage="true" %>


<html>
  <head>
  <title></title>
</head>
<body>
<pre>
   <%= ch.ess.base.web.ErrorPrinter.logError(pageContext, exception) %>
</pre>
</body>
</html>