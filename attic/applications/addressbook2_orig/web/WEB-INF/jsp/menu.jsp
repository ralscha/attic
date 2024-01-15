<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<%@ taglib uri="/tags/struts-menu" prefix="menu"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<script type="text/javascript" src="<c:url value='/scripts/menu/tigramenu.js' />"></script>
<menu:useMenuDisplayer name="TigraMenu" permissions="rolesAdapter"
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE"
	config="ch.ess.common.web.TigraMenu">
    <menu:displayMenu name="m_application"/>
    <menu:displayMenu name="m_administration"/>			
    <menu:displayMenu name="m_logout"/>			
</menu:useMenuDisplayer>
<script type="text/javascript" src="<c:url value='/scripts/menu/tigramenu_tpl.js' />"></script>
<script language="JavaScript" type="text/javascript">
	new menu (MENU_ITEMS, MENU_POS);
</script>