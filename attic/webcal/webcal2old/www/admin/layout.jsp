<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<tiles:importAttribute name="subtitle" scope="request"/>
<html>
<head>  
<title><tiles:getAsString name="title"/></title>
<html:base/>
<link rel=stylesheet type="text/css" href="<c:url value='/site.css'/>">
</head>
<body background="<c:url value='/images/background.gif'/>">
<table width="970" border="0" cellspacing="0" cellpadding="0">
  <tr><td colspan="3"><p>&nbsp;</p></td></tr>
  <tiles:insert attribute="menutitle"/>
  <tr><td colspan="3"><p>&nbsp;</p></td></tr>  
</table>

<table width="970" border="0" cellspacing="0" cellpadding="0">
<TR>
  <td width="150" valign="top">
    <tiles:insert attribute="menu"/>
  </td>
  <td width="30" valign="top">&nbsp;</td>
  <td valign="top">
    <tiles:insert attribute="body"/>
    <p>
    <tiles:insert attribute="error"/>
  </td>
</tr>
</table>

</body></html>
