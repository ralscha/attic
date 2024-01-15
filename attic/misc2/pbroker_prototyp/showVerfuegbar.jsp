<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,java.text.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%! 
	private final static Locale local = new Locale("de", "CH");
	private final static DateFormatSymbols symbols = new DateFormatSymbols(local); 
	private final static String[] MONTHNAMES = symbols.getShortMonths(); 
%>	
<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Verfuegbarkeit</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<% String id = request.getParameter("id"); %>

<% if (id != null) { %>
<%
   Kandidaten kandidat = Db.getKandidatenTable().selectOne("KandidatId = " + id);
   if (kandidat != null) {
%>

<body>
<p class="title">Verf&uuml;gbarkeit Kandidat Nr. <%= kandidat.getKandidatid() %><br><%= kandidat.getSwisscomerfahrung() ? "("+kandidat.getVorname() + " " + kandidat.getName() + ")" : "" %></p>
<p>
	<% String ver = kandidat.getVerfuegbar();
	   int n;
	   if (ver == null)
	     n = 0;
	   else
	     n = Integer.parseInt(ver); %>

	<table>
	    <tr>
		 <td colspan="2" class="text"><b>2000</b></td>
		</tr>
		<tr>
	<% for (int i = 0; i < 12; i++) { %>
		<td class="text"><%= MONTHNAMES[i] %></td>
	<% } %>
    </tr>

		<%
		for (int x = 0; x < 12; x++) {
	    if ((n & 1) > 0) { %>
		    <td align="center"><img src="c.gif" width="20" height="9" border="0"></td>
		<% } else { %>
		    <td align="center"><img src="f.gif" width="20" height="9" border="0"></td>
	    <% } 
		    n = n >> 1; 		
		 } %>
	<tr>
	<td colspan="2">&nbsp;</td>
	</tr>
 <tr>
		 <td colspan="2" class="text"><b>2001</b></td>
		</tr>
		<tr>
	<% for (int i = 0; i < 12; i++) { %>
		<td class="text"><%= MONTHNAMES[i] %></td>
	<% } %>
    </tr>

		<%
		for (int x = 0; x < 12; x++) {
	    if ((n & 1) > 0) { %>
		    <td align="center"><img src="c.gif" width="20" height="9" border="0"></td>
		<% } else { %>
		    <td align="center"><img src="f.gif" width="20" height="9" border="0"></td>
	    <% } 
		    n = n >> 1; 		
		 } %>
	<tr>	
	</table>
<p class="text"><img src="c.gif" width="20" height="9" border="0" alt="">&nbsp;F&uuml;r diesen Zeitraum besteht ein Vertrag<br>
<img src="f.gif" width="20" height="9" border="0" alt="">&nbsp;Der Kandidat steht zur Verf&uuml;gung</p>
<% } %>
<% } %>

<p class="text"><a href="javascript:window.close()">Schliessen</a></p>

</body>
</html>

