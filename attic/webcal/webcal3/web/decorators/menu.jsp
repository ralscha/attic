      
<%@ include file="../include/taglibs.jspf"%>

<%
  request.setAttribute("userPermissionsAdapter", new ch.ess.cal.web.PrincipalAdapter(request)); 
%>
<html>
<head>
  <!-- HTTP 1.1 -->
  <meta http-equiv="Cache-Control" content="no-store" />
  <!-- HTTP 1.0 -->
  <meta http-equiv="Pragma" content="no-cache" />
  <!-- Prevents caching at the Proxy Server -->
  <meta http-equiv="Expires" content="0" />
  
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
  <link rel="icon" href="<c:url value="/images/favicon.ico"/>" />  
    
	<title><bean:message key="application.title"/></title>
	<html:base/>
  
	<util:jsp directive="includes"/>	
  <link href="<c:url value='/styles/default.css' />" media="all" rel="stylesheet" type="text/css" />	
  <link href="<c:url value='/styles/menubar.css' />" media="all" rel="stylesheet" type="text/css" />		
  <script language="JavaScript" src="<c:url value='/scripts/menubar.js'/>" type="text/javascript"></script>
  
  <decorator:head/>
<style type="text/css">
<!--
body {
  margin-bottom: 5px;
  margin-left: 10px;
  margin-right: 5px;
  margin-top: 5px;
}
table.c1 { width: 100%; }
td.c2 { width: 280px; }
-->
</style>

</head>
<body marginwidth="0" onload="init();">



<table border="0" cellspacing="0" cellpadding="0" class="c1">
  <tr class="decoratorheader" height="25">
    <td class="c2">
      <c:if test="${not empty sessionScope.header_variables}">
      <dropmenu:useMenuDisplayer name="MenuBar" 
       bundle="org.apache.struts.action.MESSAGE"
       locale="org.apache.struts.action.LOCALE"
       permissions="userPermissionsAdapter">
        <dropmenu:displayMenu name="m_mainmenu"/>
      </dropmenu:useMenuDisplayer>
      </c:if>
    </td>
    <td class="title" align="left">ESS WebCal 0.1</td>
    <c:if test="${not empty sessionScope.header_variables}">
    <td class="smalltext" align="center"><bean:message key="header.date"/>: ${sessionScope.header_variables.date}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="header.day"/>: ${sessionScope.header_variables.day}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="header.week"/>: ${sessionScope.header_variables.weekno}</td>    
	  <td class="smalltext" align="right">
	    <bean:message key="user"/>: ${sessionScope.header_variables.firstName}&nbsp;${sessionScope.header_variables.name}&nbsp;&nbsp;	  
	  </td>
    </c:if>
  </tr>
</table>
<br />
<decorator:body/>

</body>
</html>

<div class="message"><forms:message caption="dlg.error" severity="error" formid="err"/></div>
<div class="message"><forms:message caption="dlg.message" severity="information" formid="info"/></div>

<util:jsp directive="endofpage"/>
