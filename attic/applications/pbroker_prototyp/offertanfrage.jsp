<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<% KandidatenBasket basket = (KandidatenBasket)session.getAttribute(ch.ess.pbroker.Constants.BASKET_KEY); 
   if (basket == null)
     basket = new KandidatenBasket();
%>
<% OffertAnfrage oa = (OffertAnfrage)session.getAttribute(ch.ess.pbroker.Constants.OFFERTANFRAGE_KEY); 
   if (oa == null) 
      oa = new OffertAnfrage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Offertanfragen</title>
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=5'">

<p class="title">Offertanfragen</p>
<p></p>


<p>
<% if (!oa.isSendAllgemeine()) { %>
<a href="alloffertanfrage.jsp" class="text">Allgemeine Anfrage an alle Personallieferanten</a>
<% } else { %>
<table>
<tr>
	<td colspan="2" align="left" class="text">Allgemeine Anfrage an alle Personallieferanten</td>
</tr>
<tr>
    <td>&nbsp;&nbsp;</td>
	<td align="left" class="textb">Es wurde eine Anfrage an alle Lieferanten verschickt</td>
</tr>
</table>

<p>
<% } %>
<p>
<% if (basket.getSize() > 0) { %>
  <% if (!oa.isSendKonkrete()) { %>
    <a href="konkoffertanfrage.jsp" class="text">Personenspezifische Anfrage f&uuml;r die ausgew&auml;hlte Kandidaten</a>
  <% } else { %>

<table>
<tr>
	<td colspan="2" align="left" class="text">Personenspezifische Anfrage f&uuml;r die ausgew&auml;hlte Kandidaten</td>
</tr>
<tr>
    <td>&nbsp;&nbsp;</td>
	<td align="left" class="textb">Es wurden Anfragen an die betroffenen Lieferaten verschickt</td>
</tr>
</table>	
  <% } %>
<% } %>
<p>&nbsp;
<p>
<% if ((!oa.isSendKonkrete()) && (!oa.isSendAllgemeine())) { %>
<a href="end.do"><img src="images/but_stop.gif" width="25" height="23" border="0" alt="Abbrechen"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="rekrutierung.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
&nbsp;&nbsp;&nbsp;<% } %><a href="endoffertanfrage.jsp" class="text">Abschliessen</a>
</body>
</html>
