<%@ include file="include/taglibs.inc"%>
<html:xhtml />

<div class="title"><bean:message key="event.events"/></div>
<br />
<misc:confirm key="event.delete"/>

<misc:initSelectOptions id="categoryOption" type="ch.ess.cal.web.CategoryOptions" scope="session" />
<misc:initSelectOptions id="yearOption" type="ch.ess.cal.web.YearOptions" scope="session" />
<misc:initSelectOptions id="monthOption" type="ch.ess.cal.web.MonthOptions" scope="session" />

<html:form action="/listEvent.do" focus="subject" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="event.subject"/>:</td>
<td><bean:message key="category"/>:</td>
<td><bean:message key="time.Month"/>:</td>
<td><bean:message key="time.Year"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="subject" size="20" maxlength="255"/></td>
<td>
   <html:select property="categoryId">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="categoryOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="month">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="monthOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="year">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="yearOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>


<c:if test="${not empty sessionScope.result}">
<script type="text/javascript">var bClick = false</script>

<c:url value="/events.do" var="requestURI"/>

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.cal.web.event.EventDecorator">

  <display:column width="170" property="subject" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="event.subject"/></misc:title>
  </display:column>
  <display:column width="160" property="category" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="category"/></misc:title>
  </display:column>
  <display:column width="128" property="start" align="left" maxLength="16" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="event.start"/></misc:title>
  </display:column>
  <display:column width="128" property="end" align="left" maxLength="16" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="event.end"/></misc:title>
  </display:column>
  
  <display:column property="reminder" title="" align="center">  
  </display:column>  
  <display:column property="recurrence" title="" align="center">  
  </display:column>   
  <display:column property="edit" title="" align="center">  
  </display:column>
  <display:column property="delete" title="" align="center">  
  </display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="event"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="event.events"/></misc:setProperty>

  <%@ include file="tableproperties.inc"%>  
</display:table>
<misc:highlight tableNo="3" colNo="6" />
</c:if>
<br />
<html:form action="/forward.do" method="post">
<input type="hidden" name="path" value="/newEvent.do" />
<input type="submit" name="go" value="<bean:message key="event.new"/>" />
</html:form>

