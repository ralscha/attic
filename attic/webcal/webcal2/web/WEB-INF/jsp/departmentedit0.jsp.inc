<input class="pageselected" type="submit" name="change.page.0" value="<bean:message key="department.general"/>" /> | <input class="page" type="submit" name="change.page.1" value="<bean:message key="user.users"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="resourcegroup.resourcegroups"/>" />
<br />

<table class="inputform">

<logic:iterate id="entry" indexId="ix" name="departmentForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[${ix}].name" class="required"><bean:message key="department.name" /> (${entry.language}) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
 <td valign="top"><label for="emails" class="required"><bean:message key="user.eMail"/> *:</label>&nbsp;</td>
 <td>
<c:if test="${not empty sessionScope.departmentForm.emails}">
<display:table name="sessionScope.departmentForm.emails" class="list" decorator="ch.ess.cal.web.EmailDecorator">
  <display:column property="email" maxLength="25" width="213" title="" align="left"></display:column>
  <display:column property="default" title="" align="left"></display:column>
  <display:column property="delete" title="" align="left"></display:column>
  <display:setProperty name="basic.show.header" value="false"/>
</display:table>
</c:if>
<br />
<html:text property="email" size="30" maxlength="255"/><input type="submit" name="change.addemail" value="<bean:message key="common.add"/>" />
 </td>
</tr>

</table>