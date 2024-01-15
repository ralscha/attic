<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml />

<div class="title"><bean:message key="online"/></div>
<br />
<html:form action="/listOnline.do" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>
<c:url value="/online.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" sort="list" defaultsort="1" class="list" decorator="ch.ess.addressbook.web.online.OnlineDecorator">

  <display:column title="" property="count" align="right" nowrap="nowrap" headerClass="sortable" sortable="true">    
  </display:column>

  <display:column property="sid" align="left"  nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="online.session"/></misc:title>
  </display:column>

  <display:column width="150" property="userName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="login.userName"/></misc:title>
  </display:column>
  
  <display:column property="start" align="left" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="online.start"/></misc:title>
  </display:column>  

  <display:column property="last" align="left" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="online.last"/></misc:title>
  </display:column>  

  
  <display:column width="40" property="size" align="right" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="online.size"/></misc:title>
  </display:column>

  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="online.session"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="online.sessions"/></misc:setProperty>

  <%@ include file="tableproperties.inc"%>  
  
</display:table>
<misc:highlight tableNo="3" colNo="1" />
<p>&nbsp;</p>
</c:if>


