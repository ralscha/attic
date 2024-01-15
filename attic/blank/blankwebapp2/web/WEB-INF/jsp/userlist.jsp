<%@ include file="include/taglibs.inc"%>

<div class="title"><bean:message key="user.users"/></div>
<br />
<misc:confirm key="user.delete"/>

<misc:initSelectOptions id="usergroupOption" type="ch.ess.blank.web.UserGroupOptions" scope="session" />


<html:form action="/listUser.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="common.searchText"/>:</td>
<td><bean:message key="user.userGroup"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td>
   <html:select property="value(userGroupId)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="usergroupOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/users.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.blank.web.user.UserDecorator">

  <display:column width="150" property="userName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="login.userName"/></misc:title>
  </display:column>

  <display:column width="150" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="user.lastName"/></misc:title>
  </display:column>

  <display:column width="150" property="firstName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="user.firstName"/></misc:title>
  </display:column>
  
  <display:column width="225" property="userGroup" align="left" maxLength="30" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="user.userGroups"/></misc:title>
  </display:column>

  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="user"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="user.users"/></misc:setProperty>

  <%@ include file="include/tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="4" />

</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newUser.do" />
<input type="submit" name="go" value="<bean:message key="user.new"/>" />
</html:form>
