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
</head>

<body>
	<h3><bean:message key="rangpunkte"/>&nbsp;${jahr}</h3>

    <% java.util.List jahrList = ch.ess.hgtracker.web.durchschnitt.Util.getJahrList(); %>
    <% session.setAttribute("jahrOption",jahrList); %>
    <html:form action="/rangpunkteList" focus="jahr" method="post">
      <html:select property="jahr">
      <html:optionsCollection name="jahrOption"/>
      </html:select>
    <html:hidden property="printable"/>
    <html:hidden property="webLogin"/>
    <input type="submit" name="Suchen" value="Suchen">
    </html:form>

	<display:table export="false" defaultsort="8" defaultorder="descending" class="list" id="row" name="sessionScope.rangpunkte">
		<display:column property="nachname" sortable="true" width="95"/>
		<display:column property="vorname" sortable="true" width="80"/>
		<display:column property="jahrgang" sortable="true" width="65"/>
		<display:column property="punkte" sortable="true" width="50" align="right"/>
		<display:column property="striche" sortable="true" width="50" align="right"/>
		<display:column property="schnitt" sortable="true" width="50" align="right"/>
        <display:column property="punkteProSpiel" sortable="true" width="100" align="right"/>
        <display:column property="rangpunkte" sortable="true" width="80" align="right"/>
	</display:table><br><br><br>	
<h5><bean:message key="legende"/></h5>
    <table cellspacing="2" cellpadding="2" border="0">
         <tr>
            <td><strong><bean:message key="punkte"/></strong></td>
            <td><bean:message key="punkteText"/></td>
        </tr>
        <tr>
            <td><strong><bean:message key="striche"/></strong></td>
            <td><bean:message key="stricheText"/></td>
        </tr>
        <tr>
            <td><strong><bean:message key="schnitt"/></strong></td>
            <td><bean:message key="schnittText"/></td>
        </tr>
        <tr>
            <td><strong><bean:message key="punkteProSpiel"/></strong></td>
            <td><bean:message key="punkteProSpielText"/></td>
        </tr>
        <tr>
            <td><strong><bean:message key="rangpunkte"/></strong></td>
            <td><bean:message key="rangpunkteText"/></td>
        </tr>
    </table>

    
</body>
</html>