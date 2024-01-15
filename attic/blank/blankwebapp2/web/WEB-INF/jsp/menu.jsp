<%@ include file="include/taglibs.inc"%>


<%
  request.setAttribute("userPermissionsAdapter", new ch.ess.common.web.UserPermissionsAdapter(request)); 
%>


<c:choose>
<c:when test="${not empty sessionScope.clientinfo.jsVersion}">
  
<script type="text/javascript" src="<c:url value='/scripts/menubar.js' />"></script>
<menu:useMenuDisplayer name="MenuBar" permissions="userPermissionsAdapter"
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE"
	config="ch.ess.common.web.TigraMenu">
    <menu:displayMenu name="m_mainmenu"/>
</menu:useMenuDisplayer>

</c:when>
<c:otherwise>
<table width="170" cellpadding="5" class="simplemenu">
<tr><td>
<menu:useMenuDisplayer name="Simple" permissions="userPermissionsAdapter"
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE">
    <menu:displayMenu name="m_mainmenu"/>
</menu:useMenuDisplayer>
</td></tr>
</table>
</c:otherwise>
</c:choose>
