<%@ page language="java" import="ch.ess.taglib.table.*" info="JSP Table Test"%>
<html>
<head><title>Office Phone Directory</title>
<link rel="STYLESHEET" type="text/css" href="table/global.css">
</head>
<body>
<center>
<h1>Office Phone Directory</h1>

<!--- Insert jsp:useBean tag (section 1.4, step b) here --->
<jsp:useBean id="model" scope="session" class="JSPTableModel"/>


<!--- Insert request processing (section 2.5, step c) here --->
<% model.processRequest(request); %>


<!--- Insert message (section 2.4, step d) here --->
<p>Now showing employees <%= model.getCurrentFirstRow() %> to <%= model.getCurrentLastRow() %> of <%= model.getTotalRowCount() %>.</p>

<table border="0"><TR>


<!--- Insert previous control (section 2.6, step b) here --->
<% int currentPage = model.getCurrentPage(); %>
<% if (model.isFirstPage()) { %>
   <td><img src="table/images/firstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
   <td><img src="table/images/noPreviousPage.gif" hspace=2></td>
<% } else { %>
   <td><a href="<%= request.getRequestURI() %>?page=1">
   <img src="table/images/notFirstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></a></td>
   <td><a href="<%= request.getRequestURI() %>?page=<%= (currentPage - 1) %>">
   <img src="table/images/previousPage.gif" border=0 hspace=2></a></td>
<% } %>


<!--- Insert page controls (section 2.6, step c) here --->


<% for (int i = 1; i <= model.getTotalPages(); i++) { %>
   <% if (i == currentPage) { %>
      <td><b><%= i %></b></td>
   <% } else { %>
      <td><a href="<%= request.getRequestURI() %>?page=<%= i %>"><%= i %></a></td>
   <% } %>
<% } %>



<!--- Insert next control (section 2.6, step d) here --->
<% if (model.isLastPage()) { %>
   <td><img src="table/images/noNextPage.gif" hspace=2></td>
   <td><img src="table/images/lastPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
<% } else { %>
   <td><a href="<%= request.getRequestURI() %>?page=<%= (currentPage + 1) %>">
      <img src="table/images/nextPage.gif" border=0 hspace=2></a></td>
   <td><a href="<%= request.getRequestURI() %>?page=<%= model.getTotalPages() %>">
      <img src="table/images/notLastPage.gif" border="0" hspace="2" width="13" height="16" alt=""></a></td>	  
<% } %>
</tr></table>

<!--- Insert column headers (section 1.4, step c) here --->
<table cellspacing=0 cellpadding=2 border=0>
   <% int cols = model.getColumnCount();
      // loop over each column in the table
      for (int c = 0; c < cols; c++) { %>
      <th width="250" align="left" bgcolor="#CECEFF">
         <a href="<%= request.getRequestURI() %>?page=<%= currentPage%>&sort=<%= c %>"><%= model.getColumnName(c) %></a>
		 <% if (model.getSortColumn() == c) { 
		       if (model.getSortOrder()) {
		 %>		  
		 <img src="table/images/up.gif" width="9" height="9" border="0" alt="">
		 <%    } else { %>
		 <img src="table/images/down.gif" width="9" height="9" border="0" alt="">
		 <%	   }
		    } %>
      </th>
      <th width=5>&nbsp;</th>
   <% } %>


<!--- Insert table data (section 1.4, step d) here --->

   <% int rows = model.getRowCount();
      // loop over each row in the table
      for (int r = 0; r < rows; r++) { %>
      <tr <% if (r % 2 != 0) { %>bgcolor=F0F0FF<% } %>>
         <% // and loop over each column
            for (int c = 0; c < cols; c++) { %>
            <td><%= model.getValueAt(r, c) %></td>
            <td width=5>&nbsp;</td>
         <% } %>
      </tr>
   <% } %>
</table>


</center>
</body>
</html>
