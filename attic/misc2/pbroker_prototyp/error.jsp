
<%@ page language="java" info="ErrorPage" isErrorPage="true"%>

<HTML>
<head>
<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
<title>ESS Web Calendar: ERROR</title>
</head>
<BODY>
<BR>
<H1>Error page: Planner</H1>

<BR>An error occured. Error Message is: <%=
exception.getMessage() %><BR>
Stack Trace is : <FONT COLOR="RED"><PRE><%
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
 exception.printStackTrace(pw);
  out.println(cw.toString());
 %></PRE></FONT>

</HTML>