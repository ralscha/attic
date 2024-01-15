<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml />

<div class="title"><bean:message key="priority.priorities"/></div>
<br />
<misc:confirm key="priority.deletePriority"/>
<html:form action="/listPriority.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr><td colspan="2"><bean:message key="common.searchText"/>:</td></tr>
<tr><td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/priorities.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" sort="list" defaultsort="1" class="list" decorator="ch.ess.task.web.priority.PriorityDecorator">

  <display:column width="340" property="name" align="left" maxLength="40" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="priority.name"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column> 
    	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="priority"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="priority.priorities"/></misc:setProperty>
  
  <%@ include file="tableproperties.inc"%>  
    
</display:table>
<misc:highlight tableNo="3" colNo="1" />
</c:if>

<html:form action="/forward.do" method="post">
<input type="hidden" name="value(path)" value="/newPriority.do" />
<input type="submit" name="go" value="<bean:message key="priority.new"/>" />
</html:form>

