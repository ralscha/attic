<%@ page language="java" import="ch.ess.tag.table.*" info="JSP Table Test"%>
<%@ taglib uri="/ess-table"  prefix="t" %>
<html>
<head><title>Table Test</title>
<link rel="STYLESHEET" type="text/css" href="/esslib/global.css">
</head>
<body>
<center>
<h1>Office Phone Directory</h1>

<t:table name="model" scope="session" maxPageItems="10">
<t:param name="testparam" value="value"/>
<p>Now showing employees <%= model.getCurrentFirstRow() %> to <%= model.getCurrentLastRow() %> of <%= model.getTotalRowCount() %>.</p>
<p>This is page number <%= model.getCurrentPage() %> / <%= model.getTotalPages() %></p>

 <t:index>
 <table border="0"><TR>
<t:prev>
<% if (prevPageURL == null) { %>
   <td><img src="/esslib/images/firstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
   <td><img src="/esslib/images/noPreviousPage.gif" hspace=2></td>
<% } else { %>
   <td><a href="<%= firstPageURL %>">
   <img src="/esslib/images/notFirstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></a></td>
   <td><a href="<%= prevPageURL %>">
   <img src="/esslib/images/previousPage.gif" border=0 hspace=2></a></td>
<% } %>
 </t:prev>
<t:pages>
   <% if (pageNo.intValue() == model.getCurrentPage()) { %>
      <td><b><%= pageNo %></b></td>
   <% } else { %>
      <td><a href="<%=pageURL%>"><%= pageNo %></a></td>
   <% } %>
</t:pages>
<t:next>
<% if (nextPageURL == null) { %>
   <td><img src="/esslib/images/noNextPage.gif" hspace=2></td>
   <td><img src="/esslib/images/lastPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
<% } else { %>
   <td><a href="<%= nextPageURL %>">
      <img src="/esslib/images/nextPage.gif" border=0 hspace=2></a></td>
   <td><a href="<%= lastPageURL %>">
      <img src="/esslib/images/notLastPage.gif" border="0" hspace="2" width="13" height="16" alt=""></a></td>	  
<% } %>
</t:next>
</tr></table>
 </t:index>
 
<table cellspacing=0 cellpadding=2 border=0><tr>
	      <t:columnHeader col="0">
	      <th width="20" align="left" bgcolor="#CECEFF"><a href="<%= sortURL %>">ID<% if (isSortColumn.booleanValue()) { %><img src="/esslib/images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></a></th>
		  </t:columnHeader>
		<th width=5>&nbsp;</th>
		  <t:columnHeader col="1">
		  <th width="250" align="left" bgcolor="#CECEFF"><a href="<%= sortURL %>">Name<% if (isSortColumn.booleanValue()) { %><img src="/esslib/images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></a></th>
		  </t:columnHeader>
		 <th width=5>&nbsp;</th>
		  <t:columnHeader col="2">
		  <th width="250" align="left" bgcolor="#CECEFF"><a href="<%= sortURL %>">Department<% if (isSortColumn.booleanValue()) { %><img src="/esslib/images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></a></th>
		  </t:columnHeader>
         <th width=5>&nbsp;</th>		  
		  <t:columnHeader col="3">
		  <th width="250" align="left" bgcolor="#CECEFF"><a href="<%= sortURL %>">Phone<% if (isSortColumn.booleanValue()) { %><img src="/esslib/images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></a></th>
		  </t:columnHeader>
         <th width=5>&nbsp;</th>
     
     <th>&nbsp;</th>
</tr>

 <% int rows = model.getRowCount();
      // loop over each row in the table
      for (int r = 0; r < rows; r++) { %>
      <tr <% if (r % 2 != 0) { %>bgcolor="#F0F0FF"<% } %>>
         <% // and loop over each column
            for (int c = 0; c < model.getColumnCount(); c++) { %>
            <td><%= model.getValueAt(r, c) %></td>
            <td width=5>&nbsp;</td>
         <% } %>
		 <td><a href="servlet/JSPSearchServlet?page=<%= model.getCurrentPage() %>&sort=<%= model.getSortColumn() %>&delete=<%= model.getValueAt(r, 0) %>">Delete</a></td>
      </tr>
   <% } %>
</table>   


 
</t:table>
</center>
</body>
</html>
