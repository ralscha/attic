
<%@ page language="java" info="ErrorPage" isErrorPage="true"%>

<HTML>
<head>
<meta http-equiv="pragma" content="no-cache">
<title>ESS Web Calendar: ERROR</title>
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<BR>
<H1>Error page: ESS Web Calendar</H1>

<BR>An error occured. Error Message is: <%=
exception.getMessage() %><BR>
Stack Trace is : <FONT COLOR="RED"><PRE><%
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
 exception.printStackTrace(pw);
  out.println(cw.toString());
 %></PRE></FONT>
 <p>&nbsp;
 <p>&nbsp;
Please send an email to: <a href="mailto:webcal-bugs@ess.ch">webcal-bugs@ess.ch</a></BODY>
</HTML>