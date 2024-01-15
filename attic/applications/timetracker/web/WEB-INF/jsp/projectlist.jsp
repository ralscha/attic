<%@ include file="include/taglibs.inc"%>

<div class="title"><bean:message key="project.projects"/></div>
<br />
<misc:confirm key="project.delete"/>

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerOptions" scope="session" />

<html:form action="/listProject.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="common.searchText"/>:</td>
<td><bean:message key="customer"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td>
   <html:select property="value(customerId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/projects.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="${sessionScope.static_noRows}" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.timetracker.web.project.ProjectDecorator">

  
  <display:column width="150" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="project.name"/></misc:title>
  </display:column>
  
  <display:column width="150" property="customer" group="1" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="customer"/></misc:title>
  </display:column>

  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="project"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="project.projects"/></misc:setProperty>

  <%@ include file="include/tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="2" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newProject.do" />
<input type="submit" name="go" value="<bean:message key="project.new"/>" />
</html:form>
