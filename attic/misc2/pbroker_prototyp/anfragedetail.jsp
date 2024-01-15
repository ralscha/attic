<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,ch.ess.pbroker.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>
<%! 
   String[] status = {"Offerte eingegangen", "offen", "wird nicht offeriert", "abgelehnt"};
   Random rand = new Random();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Anfrage Detail</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_offerten.jsp?a=2'">

<% String id = request.getParameter("id");

   
   if (id != null) {
   	  Anfragen anfrage = Db.getAnfragenTable().selectOne("AnfrageId = " + id);
	  if (anfrage != null) {
%>

<p class="title">Detail: Anfragen Nr. <%= anfrage.getAnfrageid() %></p>
<p>

<p class="text">
Die Anfrage wurde am <%= Constants.odateFormat.format(anfrage.getAnfragedate()) %> um <%= Constants.timeFormat.format(anfrage.getAnfragedate()) %> verschickt. 
</p>
<p>
<table cellspacing="2" cellpadding="2">
<tr>
	<th align="left" class="textb" bgcolor="#FOFOFO">Kandidat</th>
	<th align="left" class="textb" bgcolor="#FOFOFO">Lieferant</th>
	<th align="left" class="textb" bgcolor="#FOFOFO">Status</th>
	<th>&nbsp;</th>
</tr>
<% 
	List akList = Db.getAnfragekandidatenTable().select("AnfrageId = " + id);
	for (int i = 0; i < akList.size(); i++) {
		Anfragekandidaten ak = (Anfragekandidaten)akList.get(i);
		Kandidaten kandidat = ak.getKandidaten();
%>
<tr>
	<td class="text" bgcolor="#FOFOFO"><%= (kandidat.getSwisscomerfahrung()) ? (kandidat.getName() + " " + kandidat.getVorname()) : String.valueOf(kandidat.getKandidatid()) %></td>
	<td class="text" bgcolor="#FOFOFO"><%= kandidat.getLieferanten().getLieferant() %></td>
	<td class="text" bgcolor="#FOFOFO"><%= (ak.getAblehung()) ? status[3] : status[ak.getStatus()] %></td>
	<td class="text" bgcolor="#FOFOFO"><a href="anfragedetail2.jsp?id=<%=kandidat.getKandidatid()%>&anfrage=<%=anfrage.getAnfrageid()%>">Detail</a></td>
	<% if (ak.getStatus() == 0) { %>
	<td class="text" bgcolor="#BCDEFA"><a href="offerte.jsp?id=<%=kandidat.getKandidatid()%>&anfrage=<%=anfrage.getAnfrageid()%>">Offerte ansehen</a></td>
	<% } %>
</tr>

<%		
	}
%>
</table>

     <% } %>
<% } // if id != null %>
<p>
<a href="anfragen.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>
