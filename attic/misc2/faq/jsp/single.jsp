<%@ page import="com.taglib.wdjsp.faqtool.*"
errorPage="error.jsp" %>
<jsp:useBean id="faq" class="FaqBean" scope="request"/>
<html>
<head>
<title>FAQ <jsp:getProperty name="faq" property="ID"/></title>
</head>
<body bgcolor="white">
<b>Question:</b> <jsp:getProperty name="faq" property="question"/>
<br>
<b>Answer:</b> <jsp:getProperty name="faq" property="answer"/>
<p>
<font size=-1>Last Modified:
<i><jsp:getProperty name="faq" property="lastModified"/></i>
</font>
</body>
</html>