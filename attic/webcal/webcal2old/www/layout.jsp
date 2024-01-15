<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<tiles:importAttribute name="center" scope="page"/>
<html>
<head>  
<title>WebCalendar</title>
<html:base/>
<link rel=stylesheet type="text/css" href="<c:url value='/site.css'/>">
</head>
<body>

<table class="title" width="100%">
  <tr>
    <td width="40%" align="left">
    <tiles:insert attribute="menu"/>
    </td>  
 	
    <td align="center" class="header">
	<c:if test="${not empty sessionScope['ch.ess.cal.USER']}">
	<c:out value="${sessionScope['ch.ess.cal.USER'].firstName}"/>&nbsp;
	<c:out value="${sessionScope['ch.ess.cal.USER'].name}"/>&nbsp;
	</c:if>
	</td>

    <td width="40%" align="right" valign="top">
      <table cellspacing="0" cellpadding="0">
        <tr>
	      <td align="right" class="titlebig"><span class="titlebig">WebCalendar</span>&nbsp;&nbsp;<span class="titlesmall">2.0.0</span></td>
        </tr>
        <tr>
	      <td align="right" class="titlesmall">(c) 2003 <a class="nodecoration" href="http://www.ess.ch">ESS Development AG</a></td>
        </tr>
      </table>
    </td>
   
  </tr>
</table>
<hr size="1">
<p>&nbsp;</p>
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
  <p>
  <tiles:insert attribute="error"/>  
</c:otherwise>
</c:choose>
</body>
</html>
