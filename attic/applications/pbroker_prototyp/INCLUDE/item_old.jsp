<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<% ch.ess.pbroker.util.BrowserDetector bd = new ch.ess.pbroker.util.BrowserDetector(request); %>

<% Kandidaten kandidat = (Kandidaten)request.getAttribute("item.kandidat");
   String showChoice = request.getParameter("item.showChoice");
   String swisscom = request.getParameter("item.swisscom");
   String showScore = request.getParameter("item.showScore");
   String from = request.getParameter("item.from");	 
   String showDelete = request.getParameter("item.showDelete");   
   String js = request.getParameter("item.js");
   
   if (kandidat != null) { 
%>
    <% if (!"true".equals(js)) { %>
	<% if ("true".equals(swisscom)) { %>
	  <td><font face="Arial,Helvetica,sans-serif" size="1"><a href="<%= response.encodeURL("kandidatdetail.do?id="+ kandidat.getKandidatid() + "&from="+from+"&showadd=1")%>"><%= kandidat.getVorname() %>&nbsp;<%= kandidat.getName() %></a></font></td>
	<% } else { %> 
      <td width="50" align="center"><font face="Arial,Helvetica,sans-serif" size="1"><a href="<%= response.encodeURL("kandidatdetail.do?id="+ kandidat.getKandidatid() + "&from="+from+"&showadd=1")%>"><%= kandidat.getKandidatid() %></a></font></td>
	<% } %>
	<% } else { %>
	<% if ("true".equals(swisscom)) { %>
	  <td><font face="Arial,Helvetica,sans-serif" size="1"><a href="javascript:submitForm('/<%= response.encodeURL("kandidatdetail.do?id="+ kandidat.getKandidatid() + "&from="+from+"&showadd=1")%>')"><%= kandidat.getVorname() %>&nbsp;<%= kandidat.getName() %></a></font></td>
	<% } else { %> 
      <td width="50" align="center"><font face="Arial,Helvetica,sans-serif" size="1"><a href="javascript:submitForm('/<%= response.encodeURL("kandidatdetail.do?id="+ kandidat.getKandidatid() + "&from="+from+"&showadd=1")%>')"><%= kandidat.getKandidatid() %></a></font></td>
	<% } %>
	<% } %>
	<% String jg = kandidat.getFormattedGeburtsJahr(); %>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><%= jg != null ? jg : "&nbsp;" %></font></td>
	<td align="right"><font face="Arial,Helvetica,sans-serif" size="1">100 %</font></td>		
	<% Lieferanten lieferant = kandidat.getLieferanten(); %>
	<% if (lieferant != null) { %>
		<td><font face="Arial,Helvetica,sans-serif" size="1"><%= lieferant.getLieferant() %></font></td>
	<% } else { %>
		<td>&nbsp;</td>
	<% } %>
	<% String stdsatz = kandidat.getFormattedMinStdsatz(); %>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><%= stdsatz != null ? stdsatz + " - " + kandidat.getFormattedMaxStdsatz()  : "&nbsp;" %></font></td>

	
	<% String ver = kandidat.getVerfuegbar(); 
	   
	
	%>

	    <%int n = Integer.parseInt(ver); %>
		<td>
		
		<% if (bd.getBrowserName().equals(ch.ess.pbroker.util.BrowserDetector.MOZILLA)) { %>
			<table cellspacing="0" cellpadding="0" onClick="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')">
		<% } else { %>
			<table cellspacing="1" cellpadding="0" onClick="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')">
		<% } %>	
			<tr>
		<%
		for (int x = 0; x < 24; x++) {
	    if ((n & 1) > 0) { %>
		    <td><img src="c.gif"></td>
		<% } else { %>
		    <td><img src="f.gif"></td>
	    <% } 
	    n = n >> 1; 		
  	    } %>
		<% if (bd.getBrowserName().equals(ch.ess.pbroker.util.BrowserDetector.MOZILLA)) { %>
		<td><a href="javascript:calpopup('showVerfuegbar.jsp?id=<%=kandidat.getKandidatid()%>')"><img src="images/pls.gif" width="11" height="13" border="0" alt=""></a></td>
		<% } %>
		</tr></table></td>
	<% if ("true".equals(showScore)) { %>
	<td align="center"><font face="Arial,Helvetica,sans-serif" size="1"><%= kandidat.getScore() %></font></td>
	<% } %>
	
	<% if ("true".equals(showChoice)) { 
	   KandidatenBasket basket = (KandidatenBasket)session.getAttribute("pbroker.basket"); 
       if (basket == null)
   	     basket = new KandidatenBasket();
	
	    if (basket.containsKandidat(kandidat.getKandidatid())) { %>
	 <td align="center">
	 <a href="javascript:submitForm('/<%= response.encodeURL("deleteBasketKandidat.do?id="+ kandidat.getKandidatid() + "&from="+java.net.URLEncoder.encode("/"+from))%>')"><img src="images/check.gif" width="12" height="12" border="0"></a>
	 </td>
	 <% } else { %>	    
	 <td align="center"><input type="checkbox" name="kandidatid" value="<%= kandidat.getKandidatid() %>"></td>
	 <% } %>
	<% } %>
	
    <% if ("true".equals(showDelete)) { %>
	<% String deletelink = "deleteBasketKandidat.do?id=" + kandidat.getKandidatid(); %>
	<td width="40" align="center"><struts:link href="<%= deletelink %>"><img src="images/del.gif" width="12" height="12" border="0" alt=""></struts:link></td>
	<% } %>

	
<% } // kandidat != null %>
<% request.removeAttribute("item.kandidat"); %>
