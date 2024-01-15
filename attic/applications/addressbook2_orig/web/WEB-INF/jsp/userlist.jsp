<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<html:xhtml />

<div class="title"><bean:message key="Users"/></div>
<br />
<misc:confirm key="DeleteUser"/>
<html:form action="/listUser.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr><td colspan="2"><bean:message key="SearchText"/>:</td></tr>
<tr><td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td><input type="submit" name="show" value="<bean:message key="Show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/users.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" sort="list" defaultsort="1" class="list" decorator="ch.ess.addressbook.web.user.UserDecorator">

  <display:column width="170" property="userName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="UserName"/></misc:title>
  </display:column>

  <display:column width="170" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="LastName"/></misc:title>
  </display:column>

  <display:column width="170" property="firstName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="FirstName"/></misc:title>
  </display:column>
  
  <display:column width="110" property="role" align="left" maxLength="13" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="Roles"/></misc:title>
  </display:column>
  	
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="User"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="Users"/></misc:setProperty>
  <misc:setProperty name="paging.banner.one_item_found"><bean:message key="paging.banner.one_items_found" arg0="{0}" /></misc:setProperty>
  <misc:setProperty name="paging.banner.all_items_found"><bean:message key="paging.banner.all_items_found" arg0="{0}" arg1="{1}" arg2="{2}" /></misc:setProperty>
  <misc:setProperty name="paging.banner.some_items_found"><bean:message key="paging.banner.some_items_found" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" /></misc:setProperty>

  <misc:setProperty name="paging.banner.full"><bean:message key="paging.banner.full" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}"/></misc:setProperty>
  <misc:setProperty name="paging.banner.first"><bean:message key="paging.banner.first" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}"/></misc:setProperty>
  <misc:setProperty name="paging.banner.last"><bean:message key="paging.banner.last" arg0="{0}" arg1="{1}" arg2="{2}" arg3="{3}" arg4="{4}"/></misc:setProperty>
  
</display:table>
<script type="text/javascript">

    var previousClass = null;
    // simple script for highlighting rows
    var table = document.getElementsByTagName("table")[3];
    var rows = table.getElementsByTagName("tr");

    for (i=1; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className='tableRowOver' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
		    if (!bClick) {					  
              var cell = this.getElementsByTagName("td")[4];
              var elink = cell.getElementsByTagName("a")[0];
              var href = elink.getAttribute("href");                              
              location.href=href;
              this.style.cursor="wait";
            }
			bClick = false;
        }
    }

</script>	
<p>&nbsp;</p>
</c:if>
<html:link page="/editUser.do?action=add" styleClass="action"><bean:message key="New"/></html:link>
<p>&nbsp;</p>

