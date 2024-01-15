
<%@ page language="java" info="ErrorPage" isErrorPage="true"%>

<HTML>
<BODY>
<BR>
<H1>Error page: Planner</H1>

<BR>An error occured. Error Message is: <%=
exception.getMessage() %><BR>
Stack Trace is : <PRE><FONT COLOR="RED"><%
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
 exception.printStackTrace(pw);
  out.println(cw.toString());
 %></FONT></PRE>
<BR></BODY>
</HTML>