<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,java.math.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<%! private final static Random rand = new Random(); %>

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

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_offerten.jsp?a=1'">
<p class="title">&Uuml;bersicht: Offene Anfragen</p>
<p>

<table cellspacing="2" cellpadding="2">
<tr>
	<th align="right" valign="top" bgcolor="#F0F0F0" class="textb">Anfrage Nr.</th>
	<th align="left" valign="top" bgcolor="#F0F0F0" class="textb">Datum</th>
	<th align="left" valign="top" bgcolor="#F0F0F0" class="textb">T&auml;tigkeitsgebiet</th>
	<th align="left" valign="top" bgcolor="#BCDEFA" class="textb">Eingegangene<br>Offerten</th>
</tr>
<% 

     List anfragen = Db.getAnfragenTable().select("Anfragerid = " + user.getBenutzer().getBenutzerid(), "AnfrageId");
	 int offerte;
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
			
		List akList = Db.getAnfragekandidatenTable().select("AnfrageId = " + anfrage.getAnfrageid());	
		offerte = 0;
		for (int n = 0; n < akList.size(); n++) {
			Anfragekandidaten ak = (Anfragekandidaten)akList.get(n);
			if (ak.getStatus() == 0) offerte++;
		}
%>
	
<tr>
	<td align="right" bgcolor="#F0F0F0" class="text"><a href="anfragedetail.jsp?id=<%= anfrage.getAnfrageid() %>"><%= anfrage.getAnfrageid() %></a></td>	
	<td class="text" bgcolor="#F0F0F0"><%= ch.ess.pbroker.Constants.dateFormat.format(anfrage.getAnfragedate()) %></td>
	<td class="text" bgcolor="#F0F0F0"><%= tg %></td>
	<td align="center" bgcolor="#BCDEFA" class="text"><%= offerte %></td>	
</tr>

   <% } %>
</table>
<p>
<a href="descriptionofferten.html"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>
