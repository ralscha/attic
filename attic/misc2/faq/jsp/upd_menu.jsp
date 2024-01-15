<%@ page import="com.taglib.wdjsp.faqtool.*"
errorPage="error.jsp" %>

<%
  FaqBean[] faqs = (FaqBean[])request.getAttribute("faqs");
%>
<html>
<head><title>Update Menu</title></head>
<form name="menu" action="faqtool" method="post">
<table border="1" align="center"><tr><td>
<table bgcolor="tan" border="1" align="center" cellpadding="10" cellspacing="0">
<tr><th colspan="2">FAQ Administration: Update Menu</th></tr>
<%
for (int i=0; i < faqs.length; i++) {
  FaqBean faq = faqs[i];
%>
<tr>
<td><input type="radio" name="id"
value="<%= faq.getID() %>">
<%= faq.getID() %></td>
<td><%= faq.getQuestion() %></td>
</tr>
<% } %>
<tr><td colspan=2 align="center">
<input type="submit" value="Abort Updating">
<input type="submit" value="Update Selected FAQ" onClick="document.menu.cmd.value='update'">
</td></tr>
</table>
</td></tr></table>
<input type="hidden" name="cmd" value="abort">
</form>
</html>