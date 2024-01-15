<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml />

<div class="title"><html:link page="/online.do" styleClass="title"><bean:message key="online"/></html:link> / <bean:message key="online.detail"/></div>
<br />



<table class="show">

<tr>
 <th align="left"><bean:message key="online.hostName"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.hostName}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="login.userName"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.userName}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.initialReferrer"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.referrer}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.session"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.sid}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.bot"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.bot}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.start"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.start}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.last"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.last}"/></td>
</tr>
<tr>
 <th align="left"><bean:message key="online.sessionLength"/>:&nbsp;</th>
 <td><c:out value="${sessionScope.online.map.length}"/>&nbsp;<bean:message key="time.minute"/></td>
</tr>

</table>


<c:if test="${not empty sessionScope.onlineshow}">
<p>&nbsp;</p>
<p>&nbsp;</p>
<c:url value="/onlineview.do" var="requestURI"/>

<display:table name="onlineshow" export="false" scope="session" pagesize="10" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" class="list">

  <display:column title="" property="count" align="right" nowrap="nowrap">    
  </display:column>

  <display:column width="510" property="request" align="left" maxLength="60" nowrap="nowrap">    
    <misc:title><bean:message key="online.request"/></misc:title>
  </display:column>
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="online.request"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="online.requests"/></misc:setProperty>

  <%@ include file="tableproperties.inc"%>    
</display:table>
</c:if>

