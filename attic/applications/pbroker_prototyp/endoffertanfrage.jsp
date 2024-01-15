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
	<title>Abschluss Offertanfragen</title>
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=6'">

<p class="title">Abschluss Ofertanfragen</p>
<p>

<p class="text">Der Rekrutierungsprozess ist jetzt abgeschlossen</p>
<% if (oa.getAnfrage() != null) { %>
<p class="textb">Folgende Anfragen wurden mit der Nr. <%= oa.getAnfrage() %> verschickt</p>
<p>&nbsp;
<p>
<% if (basket.getSize() > 0) { %>
<p class="textb">Personenspezifische Anfragen</p>
<p>
<% if (oa.isSendKonkrete()) { %>
<p class="text">Es wurden Anfragen f&uuml;r folgende Kandidaten verschickt</p>
<%@ include file="include/oalieferantenlist.jsp"%>
<% } else { %>
<p class="text">Es wurden keine personenspezifischen Anfragen verschickt</p>
<% } %>
<p>&nbsp;</p>
<p>
<% } // if basket.getSize() > 0 %>

<p class="textb">Allgemeine Anfrage</p>
<% if (oa.isSendAllgemeine()) { %>
<p class="text">Es wurde eine allgemeine Anfrage mit folgenden <br>Rekrutierugnsangaben an alle Personallieferanten verschickt</p>
<% } else { %>
<p class="text">Es wurde keine allgemeine Anfrage verschickt</p>
<% } %>
<hr align="left" width="600">
<%@ include file="include/rekrutierungsangaben.jsp"%>

<% } else { %>
<p class="text">Es wurden keine Anfragen verschickt.</p>
<% } %>
<p>

<%
    session.removeAttribute(ch.ess.pbroker.Constants.ERROR_KEY);
    session.removeAttribute(ch.ess.pbroker.Constants.BASKET_KEY);
    session.removeAttribute(ch.ess.pbroker.Constants.OFFERTANFRAGE_KEY);
    session.removeAttribute(ch.ess.pbroker.Constants.KANDIDATEN_KEY);
    session.removeAttribute(ch.ess.pbroker.Constants.REKRUTIERUNGS_KEY);
    session.removeAttribute(ch.ess.pbroker.Constants.SEARCH_CRITERION_KEY);
%>
<p>
<a href="description.html" class="text">Beenden</a>

</body>
</html>
