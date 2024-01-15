<HTML>

<HEAD>
<TITLE> Error Page </TITLE>
</HEAD>

<%@ page isErrorPage="true" %>

<BODY BGCOLOR=Black TEXT="#FFFFFF" LINK="#FFFFFF" VLINK="#FFFFFF">

<H1> A fatal error has occurred... </H1>

<BR><BR><BR>
Details: <BR>

<%= exception.toString() %> <BR>


</BODY>
</HTML>
