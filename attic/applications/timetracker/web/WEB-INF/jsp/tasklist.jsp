<%@ include file="include/taglibs.inc"%>

<div class="title"><bean:message key="task.tasks"/></div>
<br />
<misc:confirm key="task.delete"/>

<misc:initSelectOptions id="customerOption" type="ch.ess.timetracker.web.CustomerWithProjectsOptions" scope="session" />
<misc:initSelectOptions id="projectOption" type="ch.ess.timetracker.web.ProjectOptions" scope="session" />


<html:form action="/listTask.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<input type="hidden" name="change" value=" " />
<table class="filter">
<tr>
<td><bean:message key="common.searchText"/>:</td>
<td><bean:message key="customer"/>:</td>
<td><bean:message key="project"/>:</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td>
   <html:select property="value(customerId)" onchange="document.forms['mapForm'].elements['change'].value='1';document.forms['mapForm'].submit();">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="customerOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(projectId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/tasks.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="${sessionScope.static_noRows}" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.timetracker.web.task.TaskDecorator">

  
  <display:column width="150" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="task.name"/></misc:title>
  </display:column>

  <display:column width="150" property="customer" group="1" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="customer"/></misc:title>
  </display:column>
  
  <display:column width="150" property="project" group="2" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="project"/></misc:title>
  </display:column>
  

  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="task"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="task.tasks"/></misc:setProperty>

  <%@ include file="include/tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="3" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newTask.do" />
<input type="submit" name="go" value="<bean:message key="task.new"/>" />
</html:form>

