<%@ include file="include/taglibs.inc"%>
<html:xhtml />

<div class="title"><bean:message key="department.departments"/></div>
<br />
<misc:confirm key="department.delete"/>
<html:form action="/listDepartment.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr><td colspan="2"><bean:message key="department.name"/>:</td></tr>
<tr><td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/departments.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.cal.web.department.DepartmentDecorator">

  <display:column width="170" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="department.name"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column> 
    	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="department"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="department.departments"/></misc:setProperty>
  
  <%@ include file="tableproperties.inc"%>  
    
</display:table>
<misc:highlight tableNo="3" colNo="1" />
</c:if>

<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newDepartment.do" />
<input type="submit" name="go" value="<bean:message key="department.new"/>" />
</html:form>

