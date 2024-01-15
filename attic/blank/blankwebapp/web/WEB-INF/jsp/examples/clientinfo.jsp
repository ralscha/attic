<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<table class="show">

<tr>
 <th align="left"><bean:message key="appName"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.appName}</td>
</tr>
<tr>
 <th align="left"><bean:message key="appVersion"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.appVersion}</td>
</tr>
<tr>
 <th align="left"><bean:message key="initialWindowWidth"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.initialWindowWidth}</td>
</tr>
<tr>
 <th align="left"><bean:message key="initialWindowHeight"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.initialWindowHeight}</td>
</tr>
<tr>
 <th align="left"><bean:message key="screenWidth"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.screenWidth}</td>
</tr>
<tr>
 <th align="left"><bean:message key="screenHeight"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.screenHeight}</td>
</tr>
<tr>
 <th align="left"><bean:message key="chromeWidth"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.chromeWidth}</td>
</tr>
<tr>
 <th align="left"><bean:message key="chromeHeight"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.chromeHeight}</td>
</tr>
<tr>
 <th align="left"><bean:message key="colorDepth"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.colorDepth}</td>
</tr>
<tr>
 <th align="left"><bean:message key="jsVersion"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.jsVersion}</td>
</tr>
<tr>
 <th align="left"><bean:message key="initialWindowHasToolBar"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.initialWindowHasToolBar}</td>
</tr>
<tr>
 <th align="left"><bean:message key="initialWindowHasMenuBar"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.initialWindowHasMenuBar}</td>
</tr>
<tr>
 <th align="left"><bean:message key="initialWindowHasStatusBar"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.initialWindowHasStatusBar}</td>
</tr>
<tr>
 <th align="left"><bean:message key="timeZoneOffset"/>:&nbsp;</th>
 <td>${sessionScope.clientinfo.timeZoneOffset}</td>
</tr>
</table>

