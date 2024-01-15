<%@ page import="com.taglib.wdjsp.faqtool.*"
errorPage="error.jsp" %>
<html>
<head><title>Add FAQ</title></head>

<body bgcolor="white">
<form name="menu" action="faqtool" method="post">
<table border="1" align="center"><tr><td>
<table bgcolor="tan" border="0" align="center" cellpadding="10" cellspacing="0">
<tr><th colspan="2">FAQ Administration: Add FAQ</th></tr>
<tr><td><b>Question:</b></td>
<td><input type="text" name="question" size="41" value="">
</td></tr>
<tr><td><b>Answer:</b></td>
<td>
<textarea name="answer" cols="35" rows="5">
</textarea>
</td></tr>
<tr><td colspan="2" align="center">
<input type="submit" value="Abort Addition">
<input type="submit" value="Add This FAQ" onClick="document.menu.cmd.value='do-add'">
</td></tr>
</table>
</td></tr></table>
<input type="hidden" name="token" value="<%= request.getAttribute("token") %>">
<input type="hidden" name="cmd" value="abort">
</form>
</body>
</html>
