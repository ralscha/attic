<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/ess-misc"  prefix="misc" %>
<misc:confirm key="DeleteCategory"/>
<html:form action="/listCategory.do" method="post">
<input type="hidden" name="search" value="search">
<table class="filter">
<tr>
 <td>Name:<br><html:text property="value(name)" size="20" maxlength="255"/></td>
 <td valign="bottom"><input type="submit" name="show" value="Show"></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<display:table width="760" name="result" scope="session" pagesize="10" requestURI="category.do" decorator="ch.ess.cal.admin.web.category.CategoryDecorator">
  <display:column width="200" property="name" align="left" title="Name" maxLength="20" nowrap="true" sort="true"/>
  <display:column width="100" property="colour" align="center" title="Colour" nowrap="true"/>

  <display:column width="80" property="default" align="center" title="Default" nowrap="true" sort="true"/>  

  <display:column width="290" property="description" align="left" title="Description" maxLength="53" nowrap="true"/>
  <display:column width="90" property="action" align="left" title="Actions"/>  
	
  <display:setProperty name="sort.behavior" value="list" />
  <display.setProperty name="paging.banner.include_first_last" value="true"/>
  <display:setProperty name="paging.banner.item_name" value="category" />
  <display:setProperty name="paging.banner.items_name" value="categories" />
</display:table>
<p>&nbsp;</p>
</c:if>
<html:link page="/editCategory.do?action=add" styleClass="action"><bean:message key="New"/></html:link>
<p>&nbsp;</p>
