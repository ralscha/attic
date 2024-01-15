
<%@ page language="java" errorPage="../error.jsp"%>

<%! String[] alt = {"Kandidatensuche", "Suchergebnis", "Ausgw&auml;hlte Kandidaten", "Rekrutierungsangaben", "Offertanfragen", "Abschluss"} ; %>

<html><head><title>Rekrutierung</title></head>
<body background="images/setp_bg.gif">
<table border="0" cellpadding="0" cellspacing="0">
<tr><td>&nbsp;</td><td><b><font color="#FFFFFF" face="Verdana,Arial,Helvetica,sans-serif" size="2">
STEP NAVIGATOR: Rekrutierung</font></b></td></tr><tr><td><img border="0" src="images/x.gif" width="439" height="39"></td>
<td nowrap>

<% String astr = request.getParameter("a"); %>
<% int a; %>
<% if (astr == null) a = 0; else a = Integer.parseInt(astr); %>

<% for (int i = 1; i <= 6; i++) { %>
     <% if (a == i) { %>
		<img border="0" src="images/setp_<%=i%>_on.gif" width="36" height="39" alt="<%= alt[i-1] %>">
	 <% } else { %>
  	    <img border="0" src="images/setp_<%=i%>_off.gif" width="36" height="39" alt="<%= alt[i-1] %>">
	 <% } %>
<% } %>
</td></tr></table></body></html>