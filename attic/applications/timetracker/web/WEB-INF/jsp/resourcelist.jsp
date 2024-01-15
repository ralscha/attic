<%@ include file="include/taglibs.inc"%>

<div class="title"><bean:message key="resource.resources"/></div>
<br />
<misc:confirm key="resource.delete"/>
<html:form action="/listResource.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr><td colspan="2"><bean:message key="resource.name"/>:</td></tr>
<tr><td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/resources.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="${sessionScope.static_noRows}" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.timetracker.web.resource.ResourceDecorator">

  <display:column width="340" property="name" align="left" maxLength="40" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="resource.name"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column> 
    	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="resource"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="resource.resources"/></misc:setProperty>
  
  <%@ include file="include/tableproperties.inc"%>  
    
</display:table>
<misc:highlight tableNo="3" colNo="1" />
<p>&nbsp;</p>
</c:if>


