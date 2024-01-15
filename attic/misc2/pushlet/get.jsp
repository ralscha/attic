<html><head><title></title>

<% String kto = request.getParameter("kto"); 
   if (kto != null) { 
   
   Thread.sleep(5000);
   %>
   
<script language="JavaScript" type="text/javascript">
<!--
parent.push('<%= kto %>---<%= new java.util.Date() %>', '<%= System.currentTimeMillis() %>');
//-->
</script>
<% } %>
</head><body>

<form action="get.jsp" method="post" name="getform">
<input type="hidden" name="kto" value="">
<input type="submit" name="dummy" value="dummy">
</form>
</body>
</html>



