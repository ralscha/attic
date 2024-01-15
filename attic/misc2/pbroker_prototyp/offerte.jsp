<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,java.math.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<%! private final static Random rand = new Random(); %>
<% String id = request.getParameter("anfrage");
   String kid = request.getParameter("id");
   
   Anfragen anfrage = null;
   Kandidaten kandidat = null;
   	 
   if (id != null)
   	  anfrage = Db.getAnfragenTable().selectOne("AnfrageId = " + id);

   if (kid != null)	  
      kandidat = Db.getKandidatenTable().selectOne("KandidatId = " + kid);
	  
%>

<!doctype html public "-//W3C//DTD HTML 4.0 //EN">
<html>
<head>
<title>Offerte</title><link rel="STYLESHEET" type="text/css" href="style.css">
</head>
<body>
<a href="anfragedetail.jsp?id=<%= id %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
<table width="80%">
<tr>
 <th width="60%"></th>
 <th width="40%"></th>
</tr>
<tr>
 <td class="textb"><%= kandidat.getLieferanten().getLieferant() %></td>
 <td class="textb"><%= kandidat.getLieferanten().getOrt() %>, 20. August 2000</td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr><td colspan="2">

<h1 align="center" class="title" div="">Offerte Nr: <%= rand.nextInt(1000) %></h1>
<% List l = anfrage.getRekrutierungsangaben(); %>
<% Rekrutierungsangaben rek = null;
   if (l.size() > 0) { 
    rek = (Rekrutierungsangaben)l.get(0);
	%>
	<h1 align="center" class="title" div=""><%= rek.getTaetigkeitsgebiet() %></h1>
<% } %>



<br>
<div align="left" class="text">Sehr geehrte Damen und Herren</div>
<br>
<div align="left" class="text">Wir erlauben uns, Ihnen die Offerte f&uuml;r den <%= rek.getTaetigkeitsgebiet() %>,
<%= kandidat.getAnrede() %> <%= kandidat.getVorname() %> <%= kandidat.getName() %>
 zuzusenden. Der externe Mitarbeiter kann in den Monaten Oktober bis Dezember 2000 f&uuml;r das Projekt eingesetzt werden:</div>
<p>
<table width="100%">
<tr>
 <th width="50"> </th>
 <th> </th>
</tr>
<tr>
 <td valign="top"><h2 class="title">1.</h2></td>
 <td><h2 class="title"><u>Person</u></h2>
<div align="left" class="textb">
<%= kandidat.getName() %>&nbsp;<%= kandidat.getVorname() %>,&nbsp;<%= kandidat.getOrt() %><br>
<%= kandidat.getEmail() %></div><br>
<div align="left" class="texts">
<%= kandidat.getSkills() %><br>
</div>
</td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td valign="top"><h2 class="title">2.</h2></td>
 <td><h2 class="title"><u>Einsatz Funktion</u></h2>
<div align="left" class="textb">
<%= rek.getOe() %><br>&nbsp;
<table>
<tr>
 <td valign="top" class="text">Stellenbezeichnung:&nbsp;&nbsp;</td>
 <td class="textb"><%= rek.getAufgaben() %></td>
</tr>
<tr>
 <td valign="top" class="text">Vorgesetzter / Kontaktpweson:&nbsp;&nbsp;</td>
 <td class="textb"><%= rek.getAnsprechspartner() %></td>
</tr>
<tr>
 <td valign="top" class="text">Arbeitsbeginn:&nbsp;&nbsp;</td>
 <td class="textb"><%= rek.getVon() %></td>
</tr>
<tr>
 <td valign="top" class="text">Projektphasen, Meilensteine:&nbsp;&nbsp;</td>
 <td class="textb"><%= rek.getProjekt() %></td>
</tr>
<tr>
 <td valign="top" class="text">Besonderes:&nbsp;&nbsp;</td>
 <td class="text"></td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
</table>
</div>
</td>
</tr>
<tr>
 <td valign="top"><h2 class="title">3.</h2></td>
 <td><h2 class="title"><u>Zeitrahmen</u></h2>
<div align="left">
<table>
<tr>
 <td valign="top" class="text">Zurverf&uuml;gungstellung:&nbsp;&nbsp;</td>
 <td class="textb"><%= rek.getVon() %></td>
</tr>
<tr>
 <td valign="top" class="text">Weiterverpflichtung m&ouml;glich:&nbsp;&nbsp;</td>
 <td class="textb">Ja</td>
</tr>
<% if (rand.nextBoolean()) { %>
<tr>
 <td valign="top" class="text">Stundenzahl total:&nbsp;&nbsp;</td>
 <td class="textb">1000</td>
</tr>
<% } else { %>
<tr>
 <td valign="top" class="text">Tage total:&nbsp;&nbsp;</td>
 <td class="textb">100<br>&nbsp;</td>
</tr>
<% } %>
</table>
</div>
</td>
</tr>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td valign="top"><h2 class="title">4.</h2></td>
 <td><h2 class="title"><u>Arbeitsort und Hilfsmittel</u></h2>
<div align="left" class="text">
Die Arbeitsapl&auml;tze sowie die im Rahmen der Auftragserledigung geeigneten Einrichtungen werden vom Kunden zur Verf&uuml;gung gestellt.<br>
&nbsp;<br>
<table>
<tr>
 <td valign="top" class="text">Arbeitsort:&nbsp;&nbsp;</td>
 <td class="textb">Worblaufen<br>&nbsp;</td>
</tr>
</table>
</div>
</td>
</tr>
<tr>
 <td valign="top"><h2 class="title">5.</h2></td>
 <td><h2 class="title"><u>Kosten, Abrechnung</u></h2>
<div align="left">
<table>
<tr>

 <td valign="top" class="text">Stundensatz je geleistete Stunde:&nbsp;&nbsp;</td>
 <td class="text">Fr. &nbsp;</td>
 <% int std = rand.nextInt(200); %>
 <td align="right" class="textb"><%= std %></td>
</tr>
<tr>
 <td valign="top" class="text">plus MwSt von 7.5%:</i>&nbsp;&nbsp;</td>
 <td class="text">Fr. &nbsp;</td>
 <td align="right" class="textb"><%= (int)(std * 1.075) %></td>
</tr>
<tr>
 <td valign="top" class="text">Stundensatz je geleisteten Tag:&nbsp;&nbsp;</td>
 <td class="text">Fr. &nbsp;</td>
 <td align="right" class="textb"><%= std * 8 %></td>
</tr>
<tr>
 <td valign="top" class="text">plus MwSt von 7.5%:&nbsp;&nbsp;</td>
 <td class="text">Fr. &nbsp;</td>
 <td align="right" class="textb"><%= (int)(std * 8 * 1.075) %></td>
</tr>
</table>
<p class="text">
Die Arbeitszeit wird mittels Zeiterfassungskarte festgehalten und Monatlich vom Kunden kontrolliert. Die weiteren Bedingungen ergeben sich aus den allgemeinen Gesch&auml;ftsbedingungen.
</p>
</div>
</td>
</tr>
</table>
<p class="text">
Wir w&uuml;rden uns freuen, dieses Projekt erfolgreich f&uuml;r Sie durchzuf&uuml;hren. F&uuml;r Ihr Vertrauen danken wir bestens und w&uuml;nschen Ihnen alles Gute.
</p>

<p class="text">Mit freundlichen Gr&uuml;ssen</p>
<p class="textb"><%= kandidat.getLieferanten().getLieferant() %></p>
</td></tr>
</table>
<p>
<a href="anfragedetail.jsp?id=<%= id %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
</body>
</html>