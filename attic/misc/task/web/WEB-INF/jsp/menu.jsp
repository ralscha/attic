<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/tags/oscache" prefix="cache" %>

<cache:cache scope="session" key="menu">
<c:choose>
<c:when test="${not empty sessionScope.clientinfo.jsVersion}">
  
<script type="text/javascript" src="<c:url value='/scripts/menubar.js' />"></script>
<menu:useMenuDisplayer name="MenuBar" permissions="rolesAdapter"
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE"
	config="ch.ess.common.web.TigraMenu">
    <menu:displayMenu name="m_mainmenu"/>
</menu:useMenuDisplayer>

</c:when>
<c:otherwise>
<table width="170" cellpadding="5" class="simplemenu">
<tr><td>
<menu:useMenuDisplayer name="Simple" permissions="rolesAdapter"
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE">
    <menu:displayMenu name="m_mainmenu"/>
</menu:useMenuDisplayer>
</td></tr>
</table>
</c:otherwise>
</c:choose>
</cache:cache>