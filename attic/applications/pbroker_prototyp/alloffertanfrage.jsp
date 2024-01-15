<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>Allgemeine Offertanfragen absenden</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body>

<p class="title">Allgemeine Offertanfragen</p>
<p>

<p class="text">Soll eine allgemeine Anfrage mit untenstehenden Angaben<br>
an alle Personallieferanten verschickt werden?</p>

<form action="allgemeineoa.do" method="post">
<input type="submit" name="yes" value="JA">&nbsp;<input type="submit" name="no" value="NEIN">
</form>
<p>
<hr align="left" width="600">
<%@ include file="include/rekrutierungsangaben.jsp"%>

</body>
</html>
