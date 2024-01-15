<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
 
<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<%! int color; 
    boolean showScore = false;
    boolean swisscom;
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
 
<html>

<head>

	<title>Suchergebnis</title>
	<script language="JavaScript" type="text/javascript">
<!--
		function submitForm(url1) {
			document.addform.tourl.value = url1;
			document.addform.submit();
		}
		function submitFormWO() {
		    document.addform.submit();
		}
  		
		function calpopup(lnk) { 
			  window.open(lnk, "calendar","height=320,width=450") 
		}		
//-->
</script>		
		<link rel="STYLESHEET" type="text/css" href="style.css"><link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=2'">

<% String requesturi = (String)request.getParameter("searchfrom"); %>
<font face="Arial,Helvetica,sans-serif" size="1">

</font><p>
<p class="title">Suchergebnis</p>
<p>
<% List kandidaten = (List)session.getAttribute("pbroker.kandidaten"); 
   KandidatenBasket basket = (KandidatenBasket)session.getAttribute("pbroker.basket"); 
   if (basket == null)
   	basket = new KandidatenBasket();
	
   if ((kandidaten != null) && (kandidaten.size() > 0)) {
%>
<p class="text">Suche nach:

<%
   SearchCriterion sc = (SearchCriterion)session.getAttribute("pbroker.searchcriterion");
	if (sc != null)
		showScore = sc.isShowScore();
		
   if (sc.getQuicksearch() != null) { %>
   <i><%= sc.getQuicksearch() %></i>
  <% } else { %>
   T&auml;tigkeitsgebiet:&nbsp;<i><%= sc.getTaetigkeitsgebiete()%></i><br>
   Kann Kriterien:&nbsp;<i><%= sc.getKannDescription() %></i><br>
   Muss Kriterien:&nbsp;<i><%= sc.getMussDescription() %></i></p>
<% } %>

<p>

<%
   String hasSwisscomStr = request.getParameter("has");
   String hasNonSwisscomStr = request.getParameter("hasnon");
   String show = request.getParameter("show");
   if (show == null)
   	show = "mit";
   
   boolean hasSwisscom = false;
   boolean hasNonSwisscom = false;
   int nomit = 0;
   int noohne = 0;

   for (int i = 0; i < kandidaten.size(); i++) {
   	Kandidaten kandidat = (Kandidaten)kandidaten.get(i);
 	if (kandidat.getSwisscomerfahrung()) {
		hasSwisscom = true;
		nomit++;
	} else {
		hasNonSwisscom = true;
		noohne++;
	}	
   }
%>
<% String urlitem; %>

<% if ("mit".equals(show)) { %> 

	<% String url = "/treffer.jsp?show=ohne&has=" + hasSwisscom + "&hasnon="+  hasNonSwisscom;  %>
	<% urlitem = "treffer.jsp?show=mit";  %>
	<font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="2"><b>mit Swisscom Erfahrung (<%= nomit %>)</b></font>&nbsp;&nbsp;
	<font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="2"><a href="javascript:submitForm('<%= url %>')">Kandidaten ohne Swisscom Erfahrung (<%= noohne %>)</a></font>
<% } else { %>
	<font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="2"><b>ohne Swisscom Erfahrung (<%= noohne %>)</b></font>&nbsp;&nbsp;
	<% String url = "/treffer.jsp?show=mit&has=" + hasSwisscom + "&hasnon="+  hasNonSwisscom;  %>
	<% urlitem = "treffer.jsp?show=ohne";  %>
	<font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="2"><a href="javascript:submitForm('<%= url %>')">Kandidaten mit Swisscom Erfahrung (<%= nomit %>)</a></font>
<% } %>

<p>
<form action="addKandidat.do" method="post" name="addform" id="addform">
<input type="hidden" name="tourl" value="">
<% if (hasSwisscom && ("mit".equals(show))) { %>

<table cellspacing="2" cellpadding="0">

<jsp:include page="include/header.jsp" flush="true">
  <jsp:param name="header.showScore" value="<%= showScore %>"/>
  <jsp:param name="header.swisscom" value="true"/>
  <jsp:param name="header.showChoice" value="true"/>
</jsp:include>

<%  color = 0;
    for (int i = 0; i < kandidaten.size(); i++) { 
	 Kandidaten kandidat = (Kandidaten)kandidaten.get(i); 
	 if (kandidat.getSwisscomerfahrung()) {
	 %>
    <tr bgcolor="<%= (color++ % 2 == 0) ? "Silver" : "#F0F0F0" %>">
	
	<% request.setAttribute("item.kandidat", kandidat); %>
	<jsp:include page="include/item.jsp" flush="true">
      <jsp:param name="item.showScore" value="<%= showScore %>"/>
      <jsp:param name="item.swisscom" value="true"/>
      <jsp:param name="item.showChoice" value="true"/>
	  <jsp:param name="item.from" value="<%= urlitem %>"/>
      <jsp:param name="item.js" value="true"/>	  
	</jsp:include>	
    </tr>
 <% } // if swisscomerfahrung %>
 <% } // for %>
</table>
<p>
<% } // if hasswisscom %>
<% if (hasNonSwisscom && ("ohne".equals(show))) { %>
<table cellspacing="2" cellpadding="0">


<jsp:include page="include/header.jsp" flush="true">
  <jsp:param name="header.showScore" value="<%= showScore %>"/>
  <jsp:param name="header.swisscom" value="false"/>
  <jsp:param name="header.showChoice" value="true"/>
</jsp:include>


<%  color = 0;
    for (int i = 0; i < kandidaten.size(); i++) { 
	 Kandidaten kandidat = (Kandidaten)kandidaten.get(i); 
	 if (!kandidat.getSwisscomerfahrung()) {
	 %>
	 
    <tr bgcolor="<%= (color++ % 2 == 0) ? "Silver" : "#F0F0F0" %>">
	
		<% request.setAttribute("item.kandidat", kandidat); %>
		<jsp:include page="include/item.jsp" flush="true">
         <jsp:param name="item.showScore" value="<%= showScore %>"/>
         <jsp:param name="item.swisscom" value="false"/>
         <jsp:param name="item.showChoice" value="true"/>
	     <jsp:param name="item.from" value="<%= urlitem %>"/>	
         <jsp:param name="item.js" value="true"/>		 	 
		</jsp:include>	
    </tr>
 <% } // if swisscomerfahrung %>
 <% } // for %>
</table>

<% } //if hasNonSwisscom %>
<p>
<a href="end.do"><img src="images/but_stop.gif" width="25" height="23" border="0" alt="Abbrechen"></a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="quicksearch.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
&nbsp;&nbsp;<a href="javascript:submitFormWO()"><img src="images/but_next.gif" width="25" height="23" border="0" alt=""></a>

</form>
<% } else { // if hasKandidaten %>
    <p class="text"><b>keine Kandidatenprofile gefunden</b></p>
	<a href="quicksearch.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
<% } %>



</body>
</html>
