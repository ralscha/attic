<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">	
<tiles:importAttribute name="center" scope="page"/>
<html:html xhtml="true" lang="true">
<head>  
<title><bean:message key="ApplicationTitle"/></title>
<link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" />
<link href="<c:url value='/styles/menu/tigramenu.css' />" rel="stylesheet" type="text/css" />   
<link href="<c:url value='/styles/global.css' />" rel="stylesheet" type="text/css" />
</head>
<body>
<p>&nbsp;</p>
<table class="title" width="100%">
  <tr>
    <td width="40%" align="left">
    <tiles:insert attribute="menu"/>
    </td>  
 	
    <td align="center">
	
	<c:if test="${not empty sessionScope.static_username}">
    <div class="titlesmall"><bean:message key="LoggedInAs"/>:&nbsp;
    <strong>                
	<c:out value="${sessionScope.static_username.firstName}"/>&nbsp;<c:out value="${sessionScope.static_username.name}"/>&nbsp;
	</strong>
	</div>
	</c:if>

	</td>

    <td width="30%" align="right">
      <table cellspacing="0" cellpadding="0">
        <tr>
	      <td align="right" class="titlebig"><span class="titlebig"><bean:message key="ApplicationTitle"/></span>&nbsp;<span class="titlesmall">0.1</span></td>
        </tr>
      </table>
    </td>
   
  </tr>
</table>
  <hr size="1" noshade="noshade" />
<br />
<c:choose>
<c:when test="${pageScope.center}">
<table align="center"><tr><td align="center">
  <tiles:insert attribute="body"/>
</td></tr>
<tr><td align="center">
  <tiles:insert attribute="error"/>
</td></tr>
</table>
</c:when>
<c:otherwise>
  <tiles:insert attribute="body"/>
  <tiles:insert attribute="error"/>  
</c:otherwise>
</c:choose>
</body>
</html:html>
