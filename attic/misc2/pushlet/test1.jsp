<HTML>
<HEAD>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
   <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY BGCOLOR="blue" TEXT="white">
<%
  int i = 1;

  try {
    while (true) {
       out.print("<h1>"+(i++)+"</h1>");
       out.flush();

       try {
            Thread.sleep(3000);
       } catch (InterruptedException e) {
       out.print("<h1>"+e+"</h1>");
        }
     }
   } catch (Exception e) {
       out.print("<h1>"+e+"</h1>");
   }
%>
</BODY>
</HTML>