<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>


<table width="170" cellpadding="5" class="simplemenu">
<tr><td>
<menu:useMenuDisplayer name="Simple" 
    bundle="org.apache.struts.action.MESSAGE"
	locale="org.apache.struts.action.LOCALE">
    <menu:displayMenu name="m_examples"/>
</menu:useMenuDisplayer>
</td></tr>
</table>
