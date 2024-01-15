
<% Map lieferantenMap = oa.getLieferantenMap(); %>
<% if (lieferantenMap.size() > 0) { %>
<table><tr><td>
<table border="1" cellspacing="2" cellpadding="2">
<tr>
	<th align="left" class="texti">Personallieferant</th>
	<th align="left" class="texti">Kandidat</th>
</tr>

<% Iterator keys = lieferantenMap.keySet().iterator(); 
   while(keys.hasNext()) { 
     String lieferant = (String)keys.next();
	 List kandidatenList = (List)lieferantenMap.get(lieferant);
	 for (int i = 0; i < kandidatenList.size(); i++) {
	   Kandidaten kandidat = (Kandidaten)kandidatenList.get(i);
%>
<tr>
    <% if (i == 0) { %>
	<td rowspan="<%=kandidatenList.size()%>" align="left" valign="top" class="textb"><%= lieferant %></td>
	<% } %>
	<td class="text"><%= (kandidat.getSwisscomerfahrung()) ? (kandidat.getName() + " " + kandidat.getVorname()) : String.valueOf(kandidat.getKandidatid()) %></td>
</tr>
<%   } %>
<% } %>
</table></td></tr></table>
<% } %>
