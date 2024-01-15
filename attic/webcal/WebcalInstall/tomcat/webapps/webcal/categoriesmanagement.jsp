
<%@ page language="java" import="com.codestudio.util.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Categories Management" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% 
  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  try {
    CategoriesTable catTable = new CategoriesTable(conn);
%>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.setConnection(conn); %>
</jsp:useBean>


<% if (login.isAdmin()) { %> 
<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<TITLE>ESS Web Calendar: Categories</TITLE>
<link rel="STYLESHEET" type="text/css" href="planner.css">

<script language="JavaScript" type="text/javascript">
<!--
  function calcolorchooser(lnk) { 
     window.open(lnk, "colorchooser","height=130,width=640,scrollbars=yes") 
  }
//-->
</script>	

</head>
<BODY>
<%@ include file="menu.jsp"%>
<p class="titlesmall">Categories</p>
<% 

  
  String deleteCatid = request.getParameter("delete");
  if (deleteCatid != null) {
      if (catTable.canDelete(Integer.parseInt(deleteCatid))) {
	  	  catTable.delete("categoryid = " + deleteCatid);
		  categoriesMap.setConnection(conn);  
	  }
  }
  
  String update = request.getParameter("update");
  if (update != null) {
  	Integer[] keys = categoriesMap.getKeys();
	for (int i = 0; i < keys.length; i++) { 
	   String newDescription = request.getParameter("cat"+keys[i]);
	   String newColor = request.getParameter("hi"+keys[i]);
	   if ((newDescription != null) && (newColor != null)) {
		   newColor = newColor.substring(0, newColor.indexOf("."));
		   if (!categoriesMap.getDescription(keys[i]).equals(newDescription) || 
	    	   !categoriesMap.getColor(keys[i]).equals(newColor) ) { 
		    	 Categories cat = categoriesMap.getCategory(keys[i]);
				 cat.setDescription(newDescription);
				 cat.setColor(newColor);
				 catTable.update(cat);
				 categoriesMap.setConnection(conn);
		   }
	   }
	}
  }
  
  String add = request.getParameter("add");
  if (add != null) {
	   String newDescription = request.getParameter("catnew");
	   String newColor = request.getParameter("hinew");
	   if ((newDescription != null) && (newColor != null)) {
	      newDescription = newDescription.trim();
		  newColor = newColor.trim();
		  if (!newDescription.equals("") && !newColor.equals("")) {
		     newColor = newColor.substring(0, newColor.indexOf("."));
		     Categories newCat = new Categories(0, newDescription, newColor);
			 catTable.insert(newCat);
			 categoriesMap.setConnection(conn);
		  }
	   }
  }
  
  int imgcount = 0;  
  int formno = 1;
  if (login.isLogonOK()) { 
    formno = 0;
  } 
%>

<form action="categoriesmanagement.jsp" method="post">   
<table border="1">
<tr>
<th align="left">Description</th>
<th>Colour</th>
<th></th>
</tr>

<%
Iterator it = catTable.select(null, "description");


if (it.hasNext()) {
	Categories cat = (Categories)it.next();
%>

<tr>
<td><input type="text" name="cat<%= cat.getCategoryid() %>" value="<%= cat.getDescription()%>" size="20" maxlength="50"></td>
<td align="center"><img src="images/full/<%= cat.getColor() %>.gif" border="0" alt="">&nbsp;<a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hi<%= cat.getCategoryid() %>')"><img src="images/colour.gif" width="20" height="20" border="0" alt=""></a></td>
<td align="center"><a href="categoriesmanagement.jsp?delete=<%= cat.getCategoryid() %>">Delete</a></td>
<td rowspan="<%= categoriesMap.getSize() %>" align="center"><input type="submit" name="update" value="Update"></td>
<input type="hidden" name="hi<%= cat.getCategoryid() %>" value="<%= cat.getColor() %>.gif">
</tr>
<% 	imgcount += 2;
 } 

while (it.hasNext()) {
	Categories cat = (Categories)it.next();
%>

<tr>
<td><input type="text" name="cat<%= cat.getCategoryid() %>" value="<%= cat.getDescription()%>" size="20" maxlength="50"></td>
<td align="center"><img src="images/full/<%= cat.getColor() %>.gif" border="0" alt="">&nbsp;<a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hi<%= cat.getCategoryid() %>')"><img src="images/colour.gif" width="20" height="20" border="0" alt=""></a></td>
<td align="center"><a href="categoriesmanagement.jsp?delete=<%= cat.getCategoryid() %>">Delete</a></td>
<input type="hidden" name="hi<%= cat.getCategoryid() %>" value="<%= cat.getColor() %>.gif">
</tr>
<% 	imgcount += 2;
   } %>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td><input type="text" name="catnew" size="20" maxlength="50"></td>
<td align="center"><img src="images/full/000000.gif" width="20" height="20" border="0" alt="">&nbsp;<a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hinew')"><img src="images/colour.gif" width="20" height="20" border="0" alt=""></a></td>
<td colspan="2" align="center"><input type="submit" name="add" value="Add"></td>
<input type="hidden" name="hinew" value="">
</tr>
</table>

</form>
<% 
 } // if logonok 
} finally {
   manager.returnConnection(conn);
} %>
</body>


</HTML>
 