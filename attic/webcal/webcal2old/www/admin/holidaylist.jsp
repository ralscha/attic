<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/ess-misc"  prefix="misc" %>

<html:form action="/listHoliday.do" method="post">
<input type="hidden" name="search" value="search">
<table class="filter">
<tr>
 <td>Name:<br><html:text property="value(name)" size="20" maxlength="255"/></td>
 <td valign="bottom"><input type="submit" name="show" value="Show"></td>
</tr>
</table>
</html:form>

<c:if test="${not empty sessionScope.result}">
<html:form action="/updateHoliday.do" method="post">
<display:table width="760" name="result" scope="session" pagesize="10" requestURI="holiday.do" decorator="ch.ess.cal.admin.web.holiday.HolidayDecorator">
  <display:column width="500" property="name" align="left" title="Name" maxLength="100" nowrap="true" sort="true"/>
  <display:column width="100" property="month" align="left" title="Month" maxLength="20" nowrap="true" sort="true"/>
  <display:column width="100" property="country" align="left" title="Country" maxLength="10" nowrap="true" sort="true"/>
  <display:column width="60" property="check" align="center" title="Show" nowrap="true"/>
 	
  <display:setProperty name="sort.behavior" value="list" />
  <display.setProperty name="paging.banner.include_first_last" value="true"/>
  <display:setProperty name="paging.banner.item_name" value="holiday" />
  <display:setProperty name="paging.banner.items_name" value="holidays" />
</display:table>
<p>&nbsp;</p>
<html:submit property="hide"><bean:message key="HideAll" /></html:submit>
<html:submit property="show"><bean:message key="ShowAll" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="save"><bean:message key="Save" /></html:submit>
</html:form>
</c:if>
<p>&nbsp;</p>

