<%@ page import="com.taglib.wdjsp.faqtool.*" errorPage="error.jsp" %>
<jsp:useBean id="faq" scope="request" class="com.taglib.wdjsp.faqtool.FaqBean"/>
<html>
<head><title>Update FAQ</title></head>
<body bgcolor="white">
<form name="menu" action="faqtool" method="post">
<table border="1" align="center"><tr><td>
<table bgcolor="tan" border="0" align="center" cellpadding="10" cellspacing="0">
<tr><th colspan="2">FAQ Administration: Update FAQ</th></tr>
<tr><td><b>Question:</b></td>
<td><input type="text" name="question" size="41"
value="<jsp:getProperty name="faq" property="question"/>">
</td></tr>
<tr><td><b>Answer:</b></td>
<td>
<textarea name="answer" cols="35" rows="5">
<jsp:getProperty name="faq" property="answer"/>
</textarea>
</td></tr>
<tr><td colspan="2" align="center">
<input type="submit" value="Abort Update">
<input type="submit" value="Update This FAQ"
onClick="document.menu.cmd.value='do-update'">
</td></tr>
</table>
</td></tr></table>
<input type="hidden" name="cmd" value="abort">
<input type="hidden" name="token"
value="<%= request.getAttribute("token") %>">
<input type="hidden" name="id"
value="<jsp:getProperty name="faq" property="ID"/>">
</form>
</body>
</html>

