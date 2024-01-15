<%@ page import="com.taglib.wdjsp.faqtool.*"
 errorPage="error.jsp" %>

<% FaqBean[] faqs = (FaqBean[])request.getAttribute("faqs"); %>
<html>
<head><title>FAQ Index</title></head>
<body bgcolor="white">
<h2>FAQ Index</h2>
<%
for (int i=0; i < faqs.length; i++) {
  FaqBean faq = faqs[i];
%>
<b>Q:</b>
<a href="faqs?page=single.jsp&id=<jsp:getProperty name="faq" property="ID"/>">
<%= faq.getQuestion() %></a>
<p>
<% } %>
</body>
</html>