<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 

<div class="title"><html:link page="/holidays.do" styleClass="title"><bean:message key="holiday.holidays"/></html:link> / <bean:message key="holiday.edit"/>: ${holidayForm.name}</div>
<br />


<misc:confirm key="holiday.delete"/>
<html:form action="/storeHoliday.do" method="post">
<html:hidden property="id" />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="holidayForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[${ix}].name" class="required"><bean:message key="holiday.name" /> (${entry.language}) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<c:if test="${not empty holidayForm.id}">
  <c:if test="${holidayForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${holidayForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>

