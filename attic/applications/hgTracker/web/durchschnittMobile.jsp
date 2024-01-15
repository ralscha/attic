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
<%@ taglib prefix="app" uri="/WEB-INF/app" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title><bean:message key="titel" /></title>
	<link rel="STYLESHEET" type="text/css" href="mobile.css">
</head>

<body>
	<h3><bean:message key="durchschnitt"/></h3>

    <table>
    <tr>
        <td>Nachname</td>
        <td>Vorname</td>
        <td>Jahrgang</td>
        <td>Punkte</td>
        <td>Striche</td>
        <td>Schnitt</td>
    </tr>
    <c:forEach var="spieler" items="${sessionScope.durchschnitte}">
    <tr>
        <td><app:convert>${spieler.nachname}</app:convert></td>
        <td><app:convert>${spieler.vorname}</app:convert></td>
        <td>${spieler.jahrgang}</td>
        <td>${spieler.punkte}</td>
        <td>${spieler.striche}</td>
        <td>${spieler.schnitt}</td>
    </tr>
    </c:forEach>
    </table>

</body>
</html>