<%@ page errorPage="error.jsp"%>
<html><head>
<META HTTP-EQUIV="Refresh" CONTENT="0; URL=<%= request.getAttribute("referer") %>?N"> 
<title></title></head><body>
<form action="" method="post">
<input type="hidden" name="height" value="">
<input type="hidden" name="width" value="">
</form>
<script language="JavaScript">
<!--
document.forms[0].action = "<%= request.getAttribute("referer") %>"
document.forms[0].height.value = screen.height;
document.forms[0].width.value = screen.width;
document.forms[0].submit();
//-->
</script>
<noscript>NO</noscript>
</body></html>
