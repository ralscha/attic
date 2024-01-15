<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<%! int i = 0; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Ausgew&auml;lte Kandidaten</title>
<script language="JavaScript" type="text/javascript">
<!--
		function calpopup(lnk) { 
			  window.open(lnk, "calendar","height=320,width=450") 
		}	
//-->
</script>	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=3'">

<p class="title">Ausgew&auml;hlte Kandidaten</p>
<p>
<% KandidatenBasket basket = (KandidatenBasket)session.getAttribute("pbroker.basket"); %>

<% if ((basket != null) && (basket.getSize() > 0)) { 

   boolean hasSwisscom = false;
   boolean hasNonSwisscom = false;
	
	Iterator kit = basket.getKandidatenIterator(); 
	while (kit.hasNext()) {
   	 Kandidaten kandidat = (Kandidaten)kit.next();
 	if (kandidat.getSwisscomerfahrung())
		hasSwisscom = true;
	 else
		hasNonSwisscom = true;
		
	 if (hasSwisscom && hasNonSwisscom)
		break;
    }
%>
<% if ((hasSwisscom) || (hasNonSwisscom)) { %>
<table cellspacing="2" cellpadding="2">
<% } %>
<% if (hasSwisscom) { %>
<tr>
  <td colspan="7"><font face="Arial,Helvetica,sans-serif" size="2"><b>mit Swisscom Erfahrung</b></font></td>
</tr>

<jsp:include page="include/header.jsp" flush="true">
  <jsp:param name="header.showScore" value="false"/>
  <jsp:param name="header.swisscom" value="true"/>
  <jsp:param name="header.showChoice" value="false"/>
  <jsp:param name="header.showDelete" value="true"/>  
</jsp:include>

<% kit = basket.getKandidatenIterator(); 
   i = 0;
%>

<%	while (kit.hasNext()) {
	 Kandidaten kandidat = (Kandidaten)kit.next(); 
	 if (kandidat.getSwisscomerfahrung()) {
%>
    <tr bgcolor="<%= (i++ % 2 == 0) ? "Silver" : "#F0F0F0" %>">
	<% request.setAttribute("item.kandidat", kandidat); %>
	<jsp:include page="include/item.jsp" flush="true">
      <jsp:param name="item.showScore" value="false"/>
      <jsp:param name="item.swisscom" value="true"/>
      <jsp:param name="item.showChoice" value="false"/>
	  <jsp:param name="item.from" value="basket.jsp"/>	  
	  <jsp:param name="item.showDelete" value="true"/>	  
	</jsp:include>		    
	</tr>
 <% } // if swisscomerfahrung %>
 <% } // while hasNext %>

<% } // hasswisscom %>
<% if (hasNonSwisscom) { %>
<% if (hasSwisscom) { %>
<tr>
<td colspan="7">&nbsp;</td>
</tr>
<% } %>
<tr>
<td colspan="7"><font face="Arial,Helvetica,sans-serif" size="2"><b>ohne Swisscom Erfahrung</b></font></td>
</tr>
<jsp:include page="include/header.jsp" flush="true">
  <jsp:param name="header.showScore" value="false"/>
  <jsp:param name="header.swisscom" value="false"/>
  <jsp:param name="header.showChoice" value="false"/>
  <jsp:param name="header.showDelete" value="true"/>
</jsp:include>

<% kit = basket.getKandidatenIterator(); 
   i = 0;
%>

<%	while (kit.hasNext()) {
	 Kandidaten kandidat = (Kandidaten)kit.next(); 
	 if (!kandidat.getSwisscomerfahrung()) {
%>
    <tr bgcolor="<%= (i++ % 2 == 0) ? "Silver" : "#F0F0F0" %>">
	<% request.setAttribute("item.kandidat", kandidat); %>
	<jsp:include page="include/item.jsp" flush="true">
      <jsp:param name="item.showScore" value="false"/>
      <jsp:param name="item.swisscom" value="false"/>
      <jsp:param name="item.showChoice" value="false"/>
	  <jsp:param name="item.from" value="basket.jsp"/>
	  <jsp:param name="item.showDelete" value="true"/>
	</jsp:include>		
    </tr>
 <% } // if swisscomerfahrung %>
 <% } // while hasNext %>


<% } // if hasnonswisscom %>
<% if ((hasSwisscom) || (hasNonSwisscom)) { %>
</table>
<% } %>
<% } else { //if basket != null %>
<p class="text"><b>keine Kandidaten ausgew&auml;hlt</b></p>
<% } %>
<p>
<a href="end.do"><img src="images/but_stop.gif" width="25" height="23" border="0" alt="Abbrechen"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="treffer.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
&nbsp;&nbsp;<a href="rekrutierung.jsp"><img src="images/but_next.gif" width="25" height="23" border="0" alt=""></a>

</body>
</html>
