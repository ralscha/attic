

<%
  String swisscom = request.getParameter("header.swisscom");
  String showChoice = request.getParameter("header.showChoice");
  String showScore = request.getParameter("header.showScore");
  String showDelete = request.getParameter("header.showDelete");
 
%>
<tr>
    <% if ("true".equals(swisscom)) { %>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Name</font></th>
	<% } else { %>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Nr.</font></th>
	<% } %> 
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Jg.</font></th>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Pensum</font></th>	
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Lieferant</font></th>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Stdsatz</font></th>
	<th width="130" align="left"><font face="Arial,Helvetica,sans-serif" size="1">Verf&uuml;gbar</font></th>
	<% if ("true".equals(showScore)) { %>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Score</font></th>
	<% } %>
	<% if ("true".equals(showChoice)) { %>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1">ausw&auml;hlen</font></th>
	<% } %>
	<% if ("true".equals(showDelete)) { %>
	<th align="center">&nbsp;</th>
	<% } %>
	
</tr>