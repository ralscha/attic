<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<jsp:useBean id="rekrutierung" scope="session" class="ch.ess.pbroker.session.Rekrutierung"/>

<% Map lieferantenMap = (Map)session.getAttribute("pbroker.offertanfrage");
   if (lieferantenMap == null)
   	lieferantenMap = new TreeMap();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>

<head>
	<title>Offertanfragen absenden</title>

<script language="JavaScript" type="text/javascript">
<!--
  function popup(lnk) { 
  window.open(lnk, "popup","height=600,width=400") 
  }

//-->
</script>		
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_5.htm'">

<p class="title">Offertanfragen</p>
<p>
<% Integer anfrage = (Integer)request.getAttribute("anfrage"); %>

<% if ( request.getAttribute("finish") == null) { %>

<% if (lieferantenMap.size() > 0) { %>

<hr align="left" width="600">
<p>
<b>Konkrete Anfrage f&uuml;r ausgew&auml;hlte Kandidaten</b>
<p>
<table>
<tr>
<td>
<table cellspacing="2" cellpadding="2" bgcolor="Silver">
<tr>
	<th align="left">Personallieferant</th>
	<th align="left">Kandidat</th>
</tr>

<% Iterator keys = lieferantenMap.keySet().iterator(); 
   while(keys.hasNext()) { 
     String lieferant = (String)keys.next();
	 List kandidatenList = (List)lieferantenMap.get(lieferant);
	 for (int i = 0; i < kandidatenList.size(); i++) {
	   Kandidaten kandidat = (Kandidaten)kandidatenList.get(i);
%>
<tr>
	<td><%= (i==0) ? lieferant : "&nbsp;" %></td>
	<td><%= (kandidat.getSwisscomerfahrung()) ? (kandidat.getName() + " " + kandidat.getVorname()) : String.valueOf(kandidat.getKandidatid()) %></td>
</tr>
<%   } %>
<% } %>

</table>
</td>
<td>&nbsp;</td>

<% if (request.getAttribute("konkreteanfrage") == null) { %>
<td align="center" valign="middle"><a href="offertanfrage.do?mode=k<%= (anfrage==null) ? "" : "&anfrage="+anfrage %>">Offertanfragen absenden</a></td>
<% } else { %>
<td align="center" valign="middle"><b></b>Konkrete Anfragen mit der Nr. <%= anfrage %> wurde abgeschickt</b></td>
<% } %>
</tr>
</table>
<% } %>


<hr align="left" width="600">
<p>
<p class="textb">Allgemeine Anfrage an alle Personallieferanten</p>
<p class="text">
Anfrage mit untenstehenden Rekrutierungsangaben an alle akkreditierten Personallieferanten absenden.
</p>
<% if (request.getAttribute("allanfrage") == null) { %>
<a href="offertanfrage.do?mode=a<%= (anfrage==null) ? "" : "&anfrage="+anfrage %>" class="text">Offertanfragen absenden</a>
<% } else { %>
<p class="text">Allgemeine Anfragen mit der Nr. <%= anfrage %> wurde abgeschickt</p>
<% } %>
<hr align="left" width="600">
<p class="textb">Rekrutierungsangaben</p>
<p>
<table>
<tr>
<td colspan="2" align="left" class="texti">Anforderungen an Mitarbeiter</td>
</tr>
<tr>
	<td align="left" class="text">T&auml;tigkeitsgebiete:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="taetigkeitsgebiete" /></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Skills:</td>
	<td class="textb">
	<b><struts:htmlProperty name="rekrutierung" property="skills" /></td>
</tr>
<tr>
	<td align="left" class="text">Pensum:</td>
	<td class="textb"><b><struts:htmlProperty name="rekrutierung" property="pensum" /></b></td>
</tr>

<tr>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td colspan="2" align="left"><i>Einsatz</i></td>
</tr>
<tr>
	<td align="left" class="text">Projekt/Bereich:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="projekt"/></td>
</tr>
<tr>
	<td align="left" class="text">Hauptaufgaben:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="aufgaben"/></td>
</tr>
<tr>
	<td align="left"  class="text">Termin Von - Bis:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="vontermin"/>&nbsp;-&nbsp;<struts:htmlProperty name="rekrutierung" property="vontermin"/></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Bemerkung:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="bemerkung"/></td>
</tr>
<tr>
	<td align="left" class="text">Ansprechpartner:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprech"/></td>
</tr>
<tr>
	<td align="left" class="text">Telefon:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprechtel"/></td>
</tr>
<tr>
	<td align="left" class="text">E-Mail:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprechemail"/></td>
</tr>
<tr>
	<td align="left" class="text">OE:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="oe"/></td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<tr>
	<td align="left" class="text">Offerte bis:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="offertebis"/></td>
</tr>

</table>
<p>

<a href="end.do"><img src="images/cancel.gif" width="31" height="31" border="0" alt="Abbrechen"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="rekrutierung.jsp"><img src="images/4ARROW3.GIF" width="31" height="31" border="0" alt=""></a>
<a href="offertanfrage.do?mode=s<%= (anfrage==null) ? "" : "&anfrage="+anfrage %>">Abschliessen</a>

 
<% } else { %>

 Der Rekrutierungsprozess ist jetzt abgeschlossen.<br>
 <% if (anfrage != null) { %>
 Die Anfragen wurden mit der Nr. <%= anfrage %> abgeschickt<br>
 <% } else { %>
 Es wurden keine Anfragen abgeschickt<br>  
 <% } %>

 <a href="description.html">Zur&uuml;ck zum Start</a><br>



<% } %>
</body>
</html>
