      
<%@ include file="../include/taglibs.jspf"%>

<%
  request.setAttribute("userPermissionsAdapter", new ch.ess.base.web.PrincipalAdapter(request)); 
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
td.c2 { width: 660px; }
-->
</style>

</head>
<body marginwidth="0" onload="init();">



<table border="0" cellspacing="0" cellpadding="0" class="c1">
  <tr class="decoratorheader" height="25">    
    <c:if test="${not empty sessionScope.header_variables}" var="hasHeader">
      <td class="c2">
        <dropmenu:useMenuDisplayer name="MenuBar" 
         bundle="org.apache.struts.action.MESSAGE"
         locale="org.apache.struts.action.LOCALE"
         permissions="userPermissionsAdapter">
          <dropmenu:displayMenu name="m_mainmenu"/>
        </dropmenu:useMenuDisplayer>
      </td>
      <td class="title" align="left">&nbsp;</td>
	  <td class="smalltext" align="right" style="white-space: nowrap;">
	    <span class="smalltextbold"><bean:message key="header.date"/>:</span> ${sessionScope.header_variables.date}&nbsp;
	    <span class="smalltextbold"><bean:message key="header.day"/>:</span> ${sessionScope.header_variables.day}&nbsp;
	    <span class="smalltextbold"><bean:message key="header.week"/>:</span> ${sessionScope.header_variables.weekno}&nbsp;
	    <span class="smalltextbold"><bean:message key="user"/>:</span> ${sessionScope.header_variables.firstName}&nbsp;${sessionScope.header_variables.name}&nbsp;&nbsp;	  
	  </td>
    </c:if>
    <c:if test="${not hasHeader}">
      <td class="title" align="left">&nbsp;<bean:message key="application.title"/></td>    
    </c:if>
    
  </tr>
</table>
<br />

<c:if test="${not empty sessionScope.crumbs}"><menu:crumbs scope="session" name="crumbs" /><br /></c:if>

<decorator:body/>

</body>
</html>

<div class="message"><forms:message caption="dlg.error" severity="error" formid="err"/></div>
<div class="message"><forms:message caption="dlg.message" severity="information" formid="info"/></div>

<util:jsp directive="endofpage"/>
