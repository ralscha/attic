<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*,ch.ess.pbroker.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Hardcopy</title>	
	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>
<script language="JavaScript" type="text/javascript">
<!--
  function calpopup(lnk) { 
  window.open(lnk, "calendar","height=250,width=250,scrollbars=no") 
  }

//-->
</script>	
<body>

<p class="title">Hardcopy</p>

<% String id = request.getParameter("anfrage"); %>
<% String kandid = request.getParameter("id"); %>

<% Kandidaten kandidat = Db.getKandidatenTable().selectOne("KandidatId = " + kandid); %>
<p class="text">
Kandidat Nr.&nbsp;<%= kandidat.getKandidatid() %>&nbsp;<%= (kandidat.getSwisscomerfahrung()) ? "("+kandidat.getName() + " " + kandidat.getVorname()+")" : "" %><br>
Personal-Lieferant: <%= kandidat.getLieferanten().getLieferant() %>
</p>

<% String next = "/anfragedetail2.jsp?anfrage="+ id +"&id="+kandid; %>
<form action="hardcopy.do" method="post">
<table>
<p class="text">Bitte um weitere Unterlagen f&uuml;r obenerw&auml;hnten Kandidaten</p>
<tr><td class="text">Bemerkung</td></tr>
<tr></tr><td class="text"><textarea cols="30" rows="4" name="bemerkung"></textarea></td></tr>
<tr><td><input type="submit" name="hardcopy" value="Absenden" class="text"></td></tr>
</table>


<input type="hidden" name="next" value="<%= java.net.URLEncoder.encode(next)%>">
<input type="hidden" name="anfrage" value="<%= id %>">
<input type="hidden" name="kandidat" value="<%= kandid %>">
</form>
</p>
<a class="text" href="<%= next.substring(1) %>"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
</body>
</html>



