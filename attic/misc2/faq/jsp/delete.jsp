<%@ page import="com.taglib.wdjsp.faqtool.*" errorPage="error.jsp" %>
<jsp:useBean id="faq" scope="request" class="com.taglib.wdjsp.faqtool.FaqBean"/>
<html>
<head><title>Delete FAQ</title></head>
<form name="menu" action="faqtool" method="post">
<table border="1" align="center"><tr><td>
<table bgcolor="tan" border="0" align="center" cellpadding="10" cellspacing="0">
<tr><th colspan="2">FAQ Administration: Delete FAQ</th></tr>
<tr><td><b>ID:</b></td>
<td><jsp:getProperty name="faq" property="ID"/></td>
</tr>
<tr><td><b>Question:</b></td>
<td><jsp:getProperty name="faq" property="question"/></td>
</tr>
<tr><td><b>Answer:</b></td>
<td><jsp:getProperty name="faq" property="answer"/></td>
</tr>
<tr>
<td colspan="2" align="center">
<input type="submit" value="Abort Deletion">
<input type="submit" value="Delete This FAQ"
onClick="document.menu.cmd.value='do-delete'">
</td></tr>
</table>
</td></tr></table>
<input type="hidden" name="token"
value="<%= request.getAttribute("token") %>">
<input type="hidden" name="id"
value="<jsp:getProperty name="faq" property="id"/>">
<input type="hidden" name="cmd" value="abort">
</form>
</html>