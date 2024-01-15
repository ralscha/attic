<%@ page import="com.taglib.wdjsp.faqtool.*"
errorPage="error.jsp" %>

<% FaqBean[] faqs = (FaqBean[])request.getAttribute("faqs"); %>
<html>
<head><title>FAQ List</title></head>
<body bgcolor="white">
<h2>FAQ List</h2>
<%
for (int i=0; i < faqs.length; i++) {
  FaqBean faq = faqs[i];
%>
<b>Question:</b> <%= faq.getQuestion() %>
<br>
<b>Answer:</b> <%= faq.getAnswer() %>
<p>
</tr>
<% } %>
</body>
</html>