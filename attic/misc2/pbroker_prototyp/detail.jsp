<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<html>
<head>

<title>Kandidatendetail</title>
<link rel="STYLESHEET" type="text/css" href="style.css">
<script language="JavaScript" type="text/javascript">
<!--

		function calpopup(lnk) { 
			  window.open(lnk, "calendar","height=320,width=450") 
		}	

//-->
</script>
</head>
<body>

<% String requesturi = (String)request.getParameter("from"); %>
<% if (requesturi != null) 
     requesturi = java.net.URLDecoder.decode(requesturi);
%>

<% ch.ess.pbroker.util.BrowserDetector bd = new ch.ess.pbroker.util.BrowserDetector(request); %>

<p>
<struts:ifAttributeExists name="pbroker.kandidaten" scope="request">

<% Kandidaten kandidat = (Kandidaten)request.getAttribute("pbroker.kandidaten"); 

 KandidatenBasket basket = (KandidatenBasket)session.getAttribute("pbroker.basket"); 
   if (basket == null)
   	basket = new KandidatenBasket();

   if (kandidat != null) { 
%>
<p class="title">Detail Kandidat Nr. <%= kandidat.getKandidatid() %> (<%= kandidat.getSwisscomerfahrung() ? kandidat.getVorname() + " " + kandidat.getName() : "" %>)</p>
<p>
<table width="600" border="1">
<% if (kandidat.getSwisscomerfahrung()) { %>
<tr>
	<th align="left" valign="top" class="text">Name</th>
	<td class="text"><%= kandidat.getVorname() %> <%= kandidat.getName() %></td>
</tr>
<% } %>
<tr>
	<th align="left" valign="top" class="text">Jahrgang</th>

	<% String jg = kandidat.getFormattedGeburtsJahr(); %>
	<td class="text"><%= jg != null ? jg : "&nbsp;" %></td>
</tr>
<tr>
	<th align="left" valign="top" class="text">Pensum</th>
	<td class="text">100 %</td>
</tr>
<tr>
	<th align="left" class="text">Personal Lieferant</th>
	<% Lieferanten lieferant = kandidat.getLieferanten(); %>
	<td class="text"><%= (lieferant == null) ? "&nbsp;" : lieferant.getLieferant() %></td>
</tr>
<tr>
	<th align="left" valign="top" class="text">Stundenansatz</th>

	<% String stdsatz = kandidat.getFormattedMinStdsatz(); %>
	<td class="text"><%= stdsatz != null ? stdsatz + " - " + kandidat.getFormattedMaxStdsatz()  : "&nbsp;" %></td>
</tr>

<tr>
	<th align="left" valign="top" class="text">Verf&uuml;gbarkeit</th>
		<% String ver = kandidat.getVerfuegbar(); %>

	    <% int n = Integer.parseInt(ver); %>
		<td>
		
		<% if (bd.getBrowserName().equals(ch.ess.pbroker.util.BrowserDetector.MOZILLA)) { %>
			<table cellspacing="0" cellpadding="0" onClick="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')">
		<% } else { %>
			<table cellspacing="1" cellpadding="0" onClick="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')">
		<% } %>	
			<tr>
		<%
		for (int x = 0; x < 24; x++) {
	    if ((n & 1) > 0) { %>
		    <td><img src="c.gif" width="4" height="9" border="0"></td>
		<% } else { %>
		    <td><img src="f.gif" width="4" height="9" border="0"></td>
	    <% } 
	    n = n >> 1; 		
  	    } %>
		<% if (bd.getBrowserName().equals(ch.ess.pbroker.util.BrowserDetector.MOZILLA)) { %>
		<td><a href="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')"><img src="images/pls.gif" width="11" height="13" border="0" alt=""></a></td>
		<% } %>
		</tr></table></td>
</tr>

<tr>
	<th align="left" valign="top" class="text">T&auml;tigkeitsgebiet</th>
	<% String tg = kandidat.getTaetigkeitsgebiete(); 
	   if (tg.trim().equals(""))
	     tg = null;
	%>
	<td class="text"><%= (tg != null) ? tg : "&nbsp" %></td>
</tr>

<tr>
	<th align="left" valign="top" class="text">Skills</th>
	<td class="text"><%= kandidat.getSkillsOther() %></td>
</tr>
<tr>
	<th align="left" valign="top" class="text">Swisscom Erfahrung</th>
	<% String se = kandidat.getSwisscomErfahrung(); 
	   if (se.trim().equals(""))
	     se = null;
	%>
	<td class="text"><%= (se != null) ? se : "nein" %></td>
</tr>
<tr>
	<th align="left" valign="top" class="text">Notiz</th>
	<td><pre class="texts"><%= kandidat.getNotiz() %></pre></td>
</tr>

</table>
<% } %>
<struts:ifParameterNotNull name="showadd">
<form action="<%= response.encodeURL("addKandidat.do") %>" method="post">
<input type="hidden" name="kandidatid" value="<%= kandidat.getKandidatid() %>">
<input type="hidden" name="tourl" value="/<%= request.getParameter("from")%>">

<% if (!basket.containsKandidat(kandidat.getKandidatid())) { %>
<input type="submit" name="add" value="Kandidat ausw&auml;hlen">
<% } else { %>
<p class="text"><b>Kandidat ausgew&auml;hlt</b></p>
<% } %>
</form>

</struts:ifParameterNotNull>
<p>
<% if (request.getParameter("nocancel") == null) { %>
<a href="end.do"><img src="images/but_stop.gif" width="25" height="23" border="0" alt="Abbrechen"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<% } %>
<% if (requesturi != null) { %>
<struts:link href="<%= requesturi %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></struts:link>
<% } %>

<p>

</struts:ifAttributeExists>
<struts:ifAttributeMissing name="pbroker.kandidaten" scope="request">
<p class="text"><b>kein Kandidat ausgew&auml;hlt</b></p>
<% if (requesturi != null) { %>
<struts:link href="<%= requesturi %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></struts:link>
<% } %>
</struts:ifAttributeMissing>
</body>
</html>
