<%@ include file="include/taglibs.inc"%>

<div class="title"><bean:message key="userGroup.userGroups"/></div>
<br />
<misc:confirm key="userGroup.delete"/>


<html:form action="/listUserGroup.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="common.searchText"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/userGroups.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.blank.web.usergroup.UserGroupDecorator">
  
  <display:column width="150" property="groupName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="userGroup.groupName"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="userGroup"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="userGroup.userGroups"/></misc:setProperty>

  <%@ include file="include/tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="1" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newUserGroup.do" />
<input type="submit" name="go" value="<bean:message key="userGroup.new"/>" />
</html:form>
