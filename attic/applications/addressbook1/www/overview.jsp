<%@ page language="java" import="ch.ess.addressbook.*,ch.ess.addressbook.db.*,java.util.*,ch.ess.addressbook.action.*" errorPage="/error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.0.1" prefix="str" %>
<%@ taglib uri="/ess-table" prefix="t" %>
<%@ taglib uri="/ess-misc"  prefix="misc" %>
<html:html locale="true">
<misc:suppress>
<head>
<title><bean:message key="Adressbuch"/></title><link rel="STYLESHEET" type="text/css" href="css.css">
<misc:confirm key="Kontaktloeschen"/>
</head>
<body bgcolor="#FFFFFF" vlink="#0000FF" alink="#0000FF">
<p>
<table border="0" cellpadding="0" cellspacing="0" width="100%">

<tr bordercolor="#FFFFFF" bgcolor="#F0F0F0">
<td valign="middle">
	<p><b><bean:message key="Adressbuch"/> ESS Development</b></p>
</td>
</tr>

</table>
<html:form method="post" action="/searchContact.do">
<input type="hidden" name="search" value="search">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr> 
<td align="right" bgcolor="#dcdcdc" width="98%">  
  <html:text size="20" property="searchfield" />
   
  <html:submit property="searchButton"><bean:message key="Suchen"/></html:submit>
   </td>
</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0">
<tr> 

<td align="center" valign="middle" bgcolor="#dcdcdc"> 
  <table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr bgcolor="#ffffff"> 
	  <c:forEach items="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z" var="cat">
	    <th<c:if test="${searchContactForm.category == cat}"> bgcolor="#ffffcc"</c:if>>
		
		<c:choose>
		<c:when test="${categoryCount.categoryCountMap[cat] gt 0}">
		<a href="<c:url value='searchContact.do?search=1&category=${cat}'/>"><str:upperCase><c:out value="${cat}"/></str:upperCase></a>
		</c:when>
		<c:otherwise>
		<str:upperCase><c:out value="${cat}"/></str:upperCase>
		</c:otherwise>
		</c:choose>
		
		</th>
	  </c:forEach>
      <th bgcolor="#CCFFFF">&nbsp;<html:link href="searchContact.do?search=1">Alle</html:link>&nbsp;</th>
    </tr>
  </table>
</td>
</tr>
<tr bgcolor="#dcdcdc"> 
<td> 
<t:table name="listmodel" url="/overview.do" scope="session" maxPageItems="20">
  <table border="0" cellspacing="1" cellpadding="2" width="100%">
  <tr>
  <td colspan="3">
  <table border="0">
    <tr> 
      <td valign="middle">Anzeige <c:out value="${model.currentFirstRow}"/>-<c:out value="${model.currentLastRow}"/> von <c:out value="${model.totalRowCount}"/></td>
	  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:link href="export.do">Export</html:link></td>
    </tr>
  </table>  
  </td>
  <td colspan="3" align="right">  
    <t:index>
   <table border="0"><TR>
   <t:prev>
    <c:choose>
	 <c:when test="${empty prevPageURL}">
     <td><img src="images/firstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
     <td><img src="images/noPreviousPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
	 </c:when>
    <c:otherwise>
     <td><html:link href="<%= firstPageURL %>"><img src="images/notFirstPage.gif" hspace="2" width="13" height="16" border="0" alt=""></html:link></td>
     <td><html:link href="<%= prevPageURL %>"><img src="images/previousPage.gif" border="0" hspace="2" width="13" height="16" alt=""></html:link></td>
    </c:otherwise>
	</c:choose>
   </t:prev>
   <t:pages>
    <c:choose>
	 <c:when test="${pageNo == model.currentPage}">      
     <td><b><c:out value="${pageNo}"/></b></td>
	 </c:when>
    <c:otherwise>
     <td><html:link href="<%=pageURL%>"><c:out value="${pageNo}"/></html:link></td>
    </c:otherwise>
	</c:choose>
   </t:pages>
   <t:next>
    <c:choose>
	 <c:when test="${empty nextPageURL}">   
     <td><img src="images/noNextPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
      <td><img src="images/lastPage.gif" hspace="2" width="13" height="16" border="0" alt=""></td>
	 </c:when>
    <c:otherwise>
      <td><html:link href="<%= nextPageURL %>"><img src="images/nextPage.gif" border="0" hspace="2" width="13" height="16" alt=""></html:link></td>
      <td><html:link href="<%= lastPageURL %>"><img src="images/notLastPage.gif" border="0" hspace="2" width="13" height="16" alt=""></html:link></td>	  
    </c:otherwise>
	</c:choose>
   </t:next>
   </tr>
   
   
   </table>
  </t:index>
<t:noIndex><table border="0"><TR><td><img src="images/x.gif" border="0" hspace="2" width="13" height="16" alt=""></td></tr></table></t:noIndex>	 
  
  </td></tr>
    <tr bgcolor="#ffffcc"> 
      <th align="left" width="25%"> 
        <table cellpadding="0" cellspacing="0" border="0">						
          <tr> 
		  	<t:columnHeader col="<%=SearchContactAction.COL_LASTNAME%>">			
            <th><html:link href="<%= sortURL %>">Name</html:link><% if (isSortColumn.booleanValue()) { %><img src="images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %>&#160;|&#160;</th>
            </t:columnHeader>
		  	<t:columnHeader col="<%=SearchContactAction.COL_FIRSTNAME%>">			
			<th valign="bottom"><html:link href="<%= sortURL %>">Vorname</html:link><% if (isSortColumn.booleanValue()) { %><img src="images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></th>
			</t:columnHeader>
          </tr>
        </table>
      </th>
   	  <t:columnHeader col="<%=SearchContactAction.COL_COMPANY %>">			
        <th width="25%" align="left" valign="bottom"><html:link href="<%= sortURL %>">Firma</html:link><% if (isSortColumn.booleanValue()) { %><img src="images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></th>
	  </t:columnHeader>
   	  <t:columnHeader col="<%=SearchContactAction.COL_PHONE %>">				  
        <th width="15%" align="left" valign="bottom"><html:link href="<%= sortURL %>">Telefon</html:link><% if (isSortColumn.booleanValue()) { %><img src="images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></th>
	  </t:columnHeader>
   	  <t:columnHeader col="<%=SearchContactAction.COL_EMAIL %>">				  	  
        <th width="25%" align="left" valign="bottom"><html:link href="<%= sortURL %>">E-Mail</html:link><% if (isSortColumn.booleanValue()) { %><img src="images/<%= isAscending.booleanValue() ? "up.gif" : "down.gif" %>" width="9" height="9" border="0" alt=""><% } %></th>
	  </t:columnHeader>

      <td width="5%">&nbsp; </td>
      <td width="5%">&nbsp; </td>
    </tr>
    <% int rows = model.getRowCount();		   						  
       for (int r = 0; r < rows; r++) { 	%>
	   <% long id = ((Long)model.getValueAt(r, SearchContactAction.COL_ID)).longValue(); %>
    <tr bgcolor="#ffffff"> 
	
	  <% String name = (String)model.getValueAt(r, SearchContactAction.COL_LASTNAME);
	     String vorname = (String)model.getValueAt(r, SearchContactAction.COL_FIRSTNAME); 
		 String combined = name;
		 if ((vorname != null) && (!vorname.trim().equals(""))) {
		   combined += ", " + vorname;
		 }
	  %>
      <td><misc:truncate lower="34" upper="34"><%= combined %></misc:truncate></td>
      <td><misc:truncate lower="34" upper="34"><%= model.getValueAt(r, SearchContactAction.COL_COMPANY)%></misc:truncate></td>
      <td><%= model.getValueAt(r, SearchContactAction.COL_PHONE)%></td>
      <td><a href="mailto:<%= model.getValueAt(r, SearchContactAction.COL_EMAIL)%>"><%= model.getValueAt(r, SearchContactAction.COL_EMAIL)%></a></td>
	  <% String editURL = "editContact.do?action=edit&row="+r; %> 
      <td align="center"><html:link href="<%= editURL %>"><img src="images/editicon.gif" width="12" height="12" alt="Edit" border="0" /></html:link></td>
      <td align="center"><a onclick="return confirmRequest()" href="deleteContact.do?row=<%= r %>"><img src="images/del.gif" width="12" height="12" alt="Delete" border="0" /></html:link></td>
    </tr>	
	<% } %>
  </table>
  
   
</td>
</tr>

</t:table> 
<tr>
<td>
  <hr size="1" noshade="noshade" />
</td>
</tr>
</table>  
<table width="100%" cellpadding="2" cellspacing="0" border="0">
<tr bgcolor="#dcdcdc"> 
<td align="left" nowrap="nowrap" width="74%">
<html:messages id="error">
<b class="rot"><%= error %></b><br>
</html:messages>

<html:messages id="msg" message="true">
<b class="lime"><%= msg %></b><br>
</html:messages> 
</td>
<td align="right" nowrap="nowrap" width="26%">
<html:submit property="addcontact"><bean:message key="KontaktHinzufuegen"/></html:submit>
   </td>
</tr>

</table>
</html:form>


</body>
</misc:suppress>
</html:html>



