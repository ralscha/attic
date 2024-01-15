<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,ch.ess.pbroker.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Anfrage Detail</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_vertrag.jsp?a=2'">

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
<table cellspacing="2" cellpadding="2" bgcolor="#FOFOFO">
<tr>
	<th align="left" class="textb">Kandidat</th>
	<th align="left" class="textb">Lieferant</th>
	<th>&nbsp;</th>
</tr>
<% 
	List akList = Db.getAnfragekandidatenTable().select("AnfrageId = " + id);
	for (int i = 0; i < akList.size(); i++) {
		Anfragekandidaten ak = (Anfragekandidaten)akList.get(i);
		Kandidaten kandidat = ak.getKandidaten();
		if (!ak.getAblehung()) {
%>
<tr>
	<td class="text"><%= (kandidat.getSwisscomerfahrung()) ? (kandidat.getName() + " " + kandidat.getVorname()) : String.valueOf(kandidat.getKandidatid()) %></td>
	<td class="text"><%= kandidat.getLieferanten().getLieferant() %></td>
	<td class="text"><a href="vertrag.jsp?id=<%=kandidat.getKandidatid()%>&anfrage=<%=anfrage.getAnfrageid()%>">Vertrag</a></td>
</tr>

<%	}	
	}
%>
</table>

     <% } %>
<% } // if id != null %>
<p>
<a href="anfragenv.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>
