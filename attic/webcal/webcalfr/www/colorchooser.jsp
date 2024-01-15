<%@ page language="java" import="java.util.*, java.io.*" info="ColorChooser" errorPage="error.jsp"%>

<%! private static File[] files = null; %>

<%
    if (files == null) {
      String filename = pageContext.getServletContext().getRealPath("/images");
      File dirFull = new File(filename+"/full");
      files = dirFull.listFiles();
    }
%>

<html>
<head>
	<title>Selection de couleur</title>
	<meta http-equiv="pragma" content="no-cache">
<script language="JavaScript" type="text/javascript">
<!-- 
function setOpenerImage(file)
{	
	window.opener.document.images[<%=request.getParameter("img")%>].src = 'images/full/' + file;	
	window.opener.document.forms[<%=request.getParameter("form")%>].<%=request.getParameter("hi")%>.value = file;	
	window.close();
}

// -->
</script>
</head>

<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<% 
   for (int i = 0; i < files.length; i += 36) {
     for (int j = 0; j < 36; j++) {
	   if (i+j < files.length) {
%>
   <a href="#" onClick="setOpenerImage('<%= files[i+j].getName() %>')"><img src="images/full/<%=files[i+j].getName() %>" border="0" alt="" width="12" height="12"></a>
<%   
       } 
	 }   %>
   <br>
<% } %>

</body>
</html>
