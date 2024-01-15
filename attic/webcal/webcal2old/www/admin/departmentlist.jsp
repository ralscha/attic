<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/ess-misc"  prefix="misc" %>
<misc:confirm key="DeleteDepartment"/>
<html:form action="/listDepartment.do" method="post">
<input type="hidden" name="search" value="search">
<table class="filter">
<tr>
 <td>Name:<br><html:text property="value(name)" size="20" maxlength="255"/></td>
 <td valign="bottom"><input type="submit" name="show" value="Show"></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<display:table width="760" name="result" scope="session" pagesize="10" requestURI="department.do" decorator="ch.ess.cal.admin.web.department.DepartmentDecorator">
  <display:column width="200" property="name" align="left" title="Name" maxLength="20" nowrap="true" sort="true"/>
  <display:column width="470" property="description" align="left" title="Description" maxLength="70" nowrap="true"/>
  <display:column width="90" property="action" align="left" title="Actions"/>  
	
  <display:setProperty name="sort.behavior" value="list" />
  <display.setProperty name="paging.banner.include_first_last" value="true"/>
  <display:setProperty name="paging.banner.item_name" value="department" />
  <display:setProperty name="paging.banner.items_name" value="departments" />
</display:table>
<p>&nbsp;</p>
</c:if>
<html:link page="/editDepartment.do?action=add" styleClass="action"><bean:message key="New"/></html:link>
<p>&nbsp;</p>
