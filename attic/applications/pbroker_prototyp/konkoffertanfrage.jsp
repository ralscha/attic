<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<% OffertAnfrage oa = (OffertAnfrage)session.getAttribute(ch.ess.pbroker.Constants.OFFERTANFRAGE_KEY); 
   if (oa == null) 
      oa = new OffertAnfrage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Allgemeine Offertanfragen absenden</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body>

<p class="title">Personenspezifische Anfrage</p>
<p>

<p class="text">Soll f&uuml;r folgende Kandidaten eine Anfrage verschickt werden?</p>

<form action="konkreteoa.do" method="post">
<input type="submit" name="yes" value="JA">&nbsp;<input type="submit" name="no" value="NEIN">
</form>
<p>

<%@ include file="include/oalieferantenlist.jsp"%>
<hr align="left" width="600">
<%@ include file="include/rekrutierungsangaben.jsp"%>
</body>
</html>
