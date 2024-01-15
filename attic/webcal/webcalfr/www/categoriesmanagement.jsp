<%@ page language="java" import="java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Categories Management" errorPage="error.jsp"%>
<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<% 
    CategoriesTable catTable = new CategoriesTable();
%>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.init(); %>
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
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<%@ include file="menu.jsp"%>
<p class="titlesmall">Catégories</p>
<% 

  
  String deleteCatid = request.getParameter("delete");
  if (deleteCatid != null) {
      if (catTable.canDelete(Integer.parseInt(deleteCatid))) {
	  	  catTable.delete("categoryid = " + deleteCatid);
		  categoriesMap.init();  
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
				 categoriesMap.init();
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
			 newCat.setCategoryid(catTable.getMaxid());			 
			 catTable.insert(newCat);
			 categoriesMap.init();
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
<table cellspacing="2" cellpadding="0">
<tr>
<th align="left" bgcolor="#D0D0D0">Description</th>
<th bgcolor="#D0D0D0">Couleur</th>
<th></th>
</tr>

<%
Iterator it = catTable.select(null, "description");


if (it.hasNext()) {
	Categories cat = (Categories)it.next();
%>

<tr bgcolor="#E6E6E6">
<td><input type="text" name="cat<%= cat.getCategoryid() %>" value="<%= cat.getDescription()%>" size="20" maxlength="50"></td>
<td align="center"><a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hi<%= cat.getCategoryid() %>')"><img src="images/full/<%= cat.getColor() %>.gif" border="0" alt=""></a></td>
<td align="center"><a href="categoriesmanagement.jsp?delete=<%= cat.getCategoryid() %>">Supprimer</a></td>
<td rowspan="<%= categoriesMap.getSize() %>" align="center"><input type="submit" name="update" value="Mettre à jour"><input type="hidden" name="hi<%= cat.getCategoryid() %>" value="<%= cat.getColor() %>.gif"></td>

</tr>
<% 	imgcount++;
 } 

while (it.hasNext()) {
	Categories cat = (Categories)it.next();
%>

<tr bgcolor="#E6E6E6">
<td><input type="text" name="cat<%= cat.getCategoryid() %>" value="<%= cat.getDescription()%>" size="20" maxlength="50"></td>
<td align="center"><a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hi<%= cat.getCategoryid() %>')"><img src="images/full/<%= cat.getColor() %>.gif" border="0" alt=""></a></td>
<td align="center"><a href="categoriesmanagement.jsp?delete=<%= cat.getCategoryid() %>">Supprimer</a><input type="hidden" name="hi<%= cat.getCategoryid() %>" value="<%= cat.getColor() %>.gif"></td>

</tr>
<% 	imgcount++;
   } %>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr bgcolor="#E6E6E6">
<td><input type="text" name="catnew" size="20" maxlength="50"></td>
<td align="center"><a href="javascript:calcolorchooser('colorchooser.jsp?img=<%= imgcount %>&form=<%= formno %>&hi=hinew')"><img src="images/full/000000.gif" width="20" height="20" border="0" alt=""></a></td>
<td colspan="2" align="center"><input type="submit" name="add" value="Ajouter"><input type="hidden" name="hinew" value=""></td>

</tr>
</table>

</form>
<%  } // if logonok  %>
</body>


</HTML>
 