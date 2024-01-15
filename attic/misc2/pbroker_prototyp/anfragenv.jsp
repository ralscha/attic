<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<% User user = (User)session.getAttribute("pbroker.user"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Anfragen Uebersicht</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_vertrag.jsp?a=1'">
<p class="title">&Uuml;bersicht: Offene Anfragen</p>
<p>

<table cellspacing="2" cellpadding="2" bgcolor="#F0F0F0">
<tr>
	<th align="left" class="textb">Anfrage Nr.</th>
	<th align="left" class="textb">Datum</th>
	<th align="left" class="textb">T&auml;tigkeitsgebiet</th>
</tr>
<% 

     List anfragen = Db.getAnfragenTable().select("Anfragerid = " + user.getBenutzer().getBenutzerid());
	 for (int i = 0; i < anfragen.size(); i++) {
       Anfragen anfrage = (Anfragen)anfragen.get(i);
	   List rekList = anfrage.getRekrutierungsangaben();
	   Rekrutierungsangaben rek = new Rekrutierungsangaben();
	   if (rekList.size() > 0)
	   	rek = (Rekrutierungsangaben)rekList.get(0);
		String tg = rek.getTaetigkeitsgebiet();
		String skill = rek.getSkills();
		if ((tg == null) || (tg.equals("")))
			tg = "&nbsp;";
		if ((skill == null) || (skill.equals("")))
			skill = "&nbsp;";
%>
	
<tr>
	<td class="text"><a href="anfragedetailv.jsp?id=<%= anfrage.getAnfrageid() %>"><%= anfrage.getAnfrageid() %></a></td>	
	<td class="text"><%= ch.ess.pbroker.Constants.dateFormat.format(anfrage.getAnfragedate()) %></td>
	<td class="text"><%= tg %></td>
</tr>

   <% } %>
</table>
<p>
<a href="descriptionvertraege.html"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>
