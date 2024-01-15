<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc" prefix="misc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<html:xhtml />

<misc:confirm key="DeleteContact"/>
<html:form method="post" focus="searchfield" action="/searchContact.do">
<input type="hidden" name="search" value="search">
<table class="filter" width="100%" cellspacing="1" cellpadding="1">
<tr> 
<td>
<html:text size="20" property="searchfield" />   
<html:submit property="searchButton"><bean:message key="Search"/></html:submit>
</td>
<td bgcolor="#dcdcdc"> 
  <table border="0" cellpadding="4" cellspacing="1" width="100%">
    <tr bgcolor="#ffffff"> 
	  <c:forEach items="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z" var="cat">
	    <th<c:if test="${searchContactForm.category == cat}"> bgcolor="#CCCCCC"</c:if>>
		
		<c:choose>
		<c:when test="${categoryCount.categoryCountMap[cat] gt 0}">
		<a href="<c:url value='searchContact.do?search=1&category=${cat}'/>"><c:out value="${cat}"/></a>
		</c:when>
		<c:otherwise>
		<c:out value="${cat}"/>
		</c:otherwise>
		</c:choose>
		
		</th>
	  </c:forEach>
      <th<c:if test="${not empty searchContactForm.all}"> bgcolor="#CCCCCC"</c:if>>&nbsp;<html:link href="searchContact.do?search=1&all=1"><bean:message key="All"/></html:link>&nbsp;</th>
    </tr>
  </table>
</td>
</tr>
</table>
</html:form>

  
<c:if test="${not empty sessionScope.resultab}">
<script type="text/javascript">var bClick = false</script>
<p>&nbsp;</p>
<c:url value="/default.do" var="requestURI"/>
<display:table name="resultab" export="false" scope="session" pagesize="<%= (String)session.getAttribute("static_noRows") %>" requestURI="<%= (String)pageContext.getAttribute("requestURI") %>" sort="list" defaultsort="1" class="list"  decorator="ch.ess.addressbook.web.contact.ContactDecorator">

  <display:column width="170" property="firstName" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="firstName"/></misc:title>
  </display:column>

  <display:column width="127" property="lastName" align="left" maxLength="15" nowrap="nowrap" headerClass="sortable" sortable="true">
    <misc:title><bean:message key="lastName"/></misc:title>
  </display:column>

  <display:column width="200" property="company" align="left" maxLength="23" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="companyName"/></misc:title>
  </display:column>
  
  <display:column width="136" property="phone" align="left" maxLength="16" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="phone"/></misc:title>
  </display:column>

  <display:column width="213" property="email" maxLength="25" autolink="true" align="left" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="email"/></misc:title>
  </display:column>
  
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  

  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="Contact"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="Contacts"/></misc:setProperty>
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
    var table = document.getElementsByTagName("table")[4];
    var rows = table.getElementsByTagName("tr");

    for (i=1; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className='tableRowOver' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
		    if (!bClick) {					  
              var cell = this.getElementsByTagName("td")[5];
              var elink = cell.getElementsByTagName("a")[0];
              var href = elink.getAttribute("href");                              
              location.href=href;
              this.style.cursor="wait";
            }
			bClick = false;
        }		
    }

</script>	

</c:if>
<p>&nbsp;</p>

<html:link page="/editContact.do?action=add" styleClass="action"><bean:message key="NewContact"/></html:link>
<c:if test="${not empty sessionScope.resultab}">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:link page="/export.donogz" styleClass="action"><bean:message key="Exportxls"/></html:link>
</c:if>
<p>&nbsp;</p>



