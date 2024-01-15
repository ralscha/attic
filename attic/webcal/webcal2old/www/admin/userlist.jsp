<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/ess-misc"  prefix="misc" %>
<misc:confirm key="DeleteUser"/>
<html:form action="/listUser.do" method="post">
<input type="hidden" name="search" value="search">
<table class="filter">
<tr>
 <td>Username:<br><html:text property="value(userName)" size="20" maxlength="255"/></td>
 <td valign="bottom"><input type="submit" name="show" value="Show"></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<display:table width="760" name="result" scope="session" pagesize="10" requestURI="user.do" decorator="ch.ess.cal.admin.web.user.UserDecorator">
  <display:column width="183" property="userName" align="left" title="Username" maxLength="25" nowrap="true" sort="true"/>

  <display:column width="183" property="name" align="left" title="Last Name" maxLength="25" nowrap="true" sort="true"/>
  <display:column width="183" property="firstName" align="left" title="First Name" maxLength="25" nowrap="true" sort="true"/>  
  
  <display:column width="121" property="admin" align="center" title="Administrator" nowrap="true" sort="true"/>  
  <display:column width="90" property="action" align="left" title="Actions"/>  
	
  <display:setProperty name="sort.behavior" value="list" />
  <display.setProperty name="paging.banner.include_first_last" value="true"/>
  <display:setProperty name="paging.banner.item_name" value="user" />
  <display:setProperty name="paging.banner.items_name" value="users" />
</display:table>
<p>&nbsp;</p>
</c:if>
<html:link page="/editUser.do?action=add" styleClass="action"><bean:message key="New"/></html:link>
<p>&nbsp;</p>
