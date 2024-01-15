<%@ include file="include/taglibs.inc"%>
<html:xhtml />

<div class="title"><bean:message key="holiday.holidays"/></div>
<br />
<misc:confirm key="holiday.delete"/>

<misc:initSelectOptions id="countryOption" type="ch.ess.cal.web.holiday.CountryOptions" scope="session" />
<misc:initSelectOptions id="yesnoOption" type="ch.ess.cal.web.YesNoOptions" scope="session" />

<html:form action="/listHoliday.do" focus="value(searchText)" method="post">
<input type="hidden" name="search" value="search" />
<table class="filter">
<tr>
<td><bean:message key="holiday.name"/>:</td>
<td><bean:message key="holiday.country"/>:</td>
<td><bean:message key="holiday.show"/>:</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><html:text property="value(searchText)" size="20" maxlength="255"/></td>
<td>
   <html:select property="value(country)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="countryOption" property="labelValue"/>   
   </html:select>
</td>
<td>
   <html:select property="value(show)">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection name="yesnoOption" property="labelValue"/>   
   </html:select>
</td>
<td><input type="submit" name="show" value="<bean:message key="common.show"/>" /></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">

<script type="text/javascript">var bClick = false</script>

<c:url value="/holidays.do" var="requestURI"/>
<html:form action="/updateHoliday.do" method="post">

<display:table name="result" export="false" scope="session" pagesize="10" requestURI="${pageScope.requestURI}" sort="list" defaultsort="1" class="list" decorator="ch.ess.cal.web.holiday.HolidayDecorator">

  <display:column width="170" property="name" align="left" maxLength="20" nowrap="nowrap" headerClass="sortable" sortable="true">    
    <misc:title><bean:message key="holiday.name"/></misc:title>
  </display:column>

  <display:column width="85" property="month" align="left" maxLength="10" nowrap="nowrap">
    <misc:title><bean:message key="holiday.month"/></misc:title>
  </display:column>

  <display:column width="60" property="country" align="left" maxLength="6" nowrap="nowrap" headerClass="sortable" sortable="true">  
    <misc:title><bean:message key="holiday.country"/></misc:title>
  </display:column>

  <display:column property="check" title="" align="center">
    <misc:title><bean:message key="holiday.show"/></misc:title>  
  </display:column>
  
  <display:column property="edit" title="" align="center"></display:column>
  <display:column property="delete" title="" align="center"></display:column>  
  	
  <misc:setProperty name="paging.banner.group_size" value="10"/>  
  <misc:setProperty name="paging.banner.item_name"><bean:message key="holiday"/></misc:setProperty>
  <misc:setProperty name="paging.banner.items_name"><bean:message key="holiday.holidays"/></misc:setProperty>

  <%@ include file="tableproperties.inc"%>  
</display:table>
<p>&nbsp;</p>
<html:submit property="hide"><bean:message key="holiday.hideall" /></html:submit>
<html:submit property="show"><bean:message key="holiday.showall" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="save"><bean:message key="common.save" /></html:submit>
</html:form>
<misc:highlight tableNo="3" colNo="4" />
<p>&nbsp;</p>
</c:if>
<p>&nbsp;</p>

