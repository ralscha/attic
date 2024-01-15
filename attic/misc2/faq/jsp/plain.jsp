<%@ page import="com.taglib.wdjsp.faqtool.*"
contentType="text/plain" errorPage="error.jsp" %>

<% FaqBean[] faqs = (FaqBean[])request.getAttribute("faqs"); %>
FAQs List:
<%
for (int i=0; i < faqs.length; i++) {
  FaqBean faq = faqs[i];
%>
Question: <%= faq.getQuestion() %>
Answer: <%= faq.getAnsert() %>
<% } %>
