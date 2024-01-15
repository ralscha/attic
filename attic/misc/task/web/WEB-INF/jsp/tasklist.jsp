<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml />

<div class="title"><bean:message key="task.tasks"/></div>
<br />
<misc:confirm key="task.deleteTask"/>

<misc:initSelectOptions id="statusOption" type="ch.ess.task.web.task.StatusOptions" scope="session" />
<misc:initSelectOptions id="userOption" type="ch.ess.task.web.task.UserOptions" scope="session" />
<misc:initSelectOptions id="priorityOption" type="ch.ess.task.web.task.PriorityOptions" scope="session" />
<misc:initSelectOptions id="projectOption" type="ch.ess.task.web.task.ProjectOptions" scope="session" />

<html:form action="/listTask.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="common.searchText"/>:</td>
<td><bean:message key="task.project"/>:</td>
<td><bean:message key="task.priority"/>:</td>
<td><bean:message key="task.assignedTo"/>:</td>
<td><bean:message key="task.status"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td>
   <html:select property="value(projectId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="projectOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(priorityId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="priorityOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(userId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="userOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(statusId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="statusOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/tasks.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" sort="list" defaultsort="1" class="list" decorator="ch.ess.task.web.task.TaskDecorator">


  <display:column width="130" property="name" align="left" maxLength="17" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.name"/></misc:title>
  </display:column>
  <display:column width="83" property="project" align="left" maxLength="11" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.project"/></misc:title>
  </display:column>
  <display:column width="83" property="priority" align="left" maxLength="11" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.priority"/></misc:title>
  </display:column>
  <display:column width="143" property="assignedTo" align="left" maxLength="19" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.assignedTo"/></misc:title>
  </display:column>
  <display:column width="83" property="status" align="left" maxLength="11" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.status"/></misc:title>
  </display:column>    

  <display:column width="120" property="complete" align="right" maxLength="3" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="task.complete"/></misc:title>
  </display:column>    
  <display:column title="" width="55" property="completePic" align="center" nowrap="nowrap" sortable="false" />

  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="task"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="task.tasks"/></misc:setProperty>

  <%@ include file="tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="7" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="value(path)" value="/newTask.do" />
<input type="submit" name="go" value="<bean:message key="task.new"/>" />
</html:form>


