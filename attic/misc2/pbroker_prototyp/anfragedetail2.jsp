<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,ch.ess.pbroker.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>
<% User user = (User)session.getAttribute("pbroker.user"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Anfragen Detail</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_offerten.jsp?a=2'">

<% String id = request.getParameter("anfrage");
   String kandid = request.getParameter("id");
   if ((kandid != null) && (id != null)) {
   	  Anfragen anfrage = Db.getAnfragenTable().selectOne("AnfrageId = " + id);
	  Kandidaten kandidat = Db.getKandidatenTable().selectOne("KandidatId = " + kandid);
	  
	  if ((anfrage != null) && (kandidat != null)) {
%>

<p class="title">Anfragen Nr. <%= anfrage.getAnfrageid() %><br>
Kandidat Nr.&nbsp;<%= kandidat.getKandidatid() %>&nbsp;<%= (kandidat.getSwisscomerfahrung()) ? "("+kandidat.getName() + " " + kandidat.getVorname()+")" : "" %>
<p>
<a class="text" href="<%= response.encodeURL("kandidatdetail.do?id="+ kandid + "&from="+java.net.URLEncoder.encode("anfragedetail2.jsp?anfrage="+id+"&id="+kandid)+"&nocancel=1")%>">Kandidatendetail</a>
<p>

   <%
      Anfragekandidaten ak = Db.getAnfragekandidatenTable().selectOne("AnfrageId = " + id + " AND KandidatId = " + kandid);
	  if (ak != null) {
   %>
   <% if (ak.getAblehung()) { %>
    <p class="text">Der Kandidat wurde abgelehnt</p>
   <% } else { %>
     <% if (ak.getHardcopy() != null) { %>
	 Hardcopy verlangt am: <%= ch.ess.pbroker.Constants.dateFormat.format(ak.getHardcopy()) %>
	 <% } %>
   <% } %>   
   
<% if (!ak.getAblehung() && !(ak.getStatus() == 2)) { %>      
  <a href="hardcopy.jsp?anfrage=<%= id %>&id=<%=kandid%>" class="text">Hardcopy verlangen</a><br>
  <a href="vorstellung.jsp?anfrage=<%= id %>&id=<%=kandid%>" class="text">Vorstellungsgespr&auml;ch</a><br>
  <a href="ablehnung.jsp?anfrage=<%= id %>&id=<%=kandid%>" class="text">Ablehung</a><br>
<% } %>
<a href="info.jsp?anfrage=<%= id %>&id=<%=kandid%>" class="text">Info</a><br>
<% } %>

<% } %>
<% } %>
<p>
<a href="anfragedetail.jsp?id=<%= id %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>

