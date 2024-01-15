<%@ page language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title><bean:message key="titel" /></title>
	<link rel="STYLESHEET" type="text/css" href="default.css">
	<decorator:head/>
</head>

<body>
	<table class="noBorder" cellspacing="2" cellpadding="2" border="0">
		<tr>
			<td><h2>${club.hgName}</h2>
				<a name="logout" id="logout" href="logout.do"><bean:message key="logout" /></a>&nbsp;&nbsp;
				<a href="spielList.do"><bean:message key="spiel" /></a>&nbsp;&nbsp;
				<a href="spielerList.do"><bean:message key="spieler" /></a>&nbsp;&nbsp;
				<a href="durchschnittList.do"><bean:message key="durchschnitt" /></a>&nbsp;&nbsp;
                <a href="rangpunkteList.do"><bean:message key="rangpunkte" /></a>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><decorator:body/></td>
		</tr>
	</table>



</body>
</html>