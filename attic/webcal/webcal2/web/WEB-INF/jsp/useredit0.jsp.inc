
<input class="pageselected" type="submit" name="change.page.0" value="<bean:message key="user.general"/>" /> | <input class="page" type="submit" name="change.page.1" value="<bean:message key="department.departments"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="resourcegroup.resourcegroups"/>" />
<br />
<table class="inputform">

<tr>
 <td><misc:label property="userName" key="login.userName"/>&nbsp;</td>
 <td><html:text property="userName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="name" key="user.lastName"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="firstName" key="user.firstName"/>&nbsp;</td>
 <td><html:text property="firstName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="shortName" key="user.shortName"/>&nbsp;</td>
 <td><html:text property="shortName" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="password" key="login.password"/>&nbsp;</td>
 <td><html:password property="password" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="retypePassword" key="user.retypePassword"/>&nbsp;</td>
 <td><html:password property="retypePassword" size="40" maxlength="255"/></td>
</tr>
<misc:initSelectOptions id="roleOption" type="ch.ess.cal.web.RoleOptions" scope="session" />
<tr>
 <td valign="top"><misc:label property="roleIds" key="user.roles"/>&nbsp;</td>
 <td>
   <html:select multiple="true" size="5" property="roleIds">
     <html:option value="-1">&nbsp;</html:option>
     <html:optionsCollection name="roleOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 
<misc:initSelectOptions id="localeOption" type="ch.ess.cal.web.LocaleOptions" scope="session" />
<tr>
 <td><misc:label property="localeStr" key="user.language"/>&nbsp;</td>
 <td>
   <html:select property="localeStr">
     <html:optionsCollection name="localeOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<tr>
 <td valign="top"><label for="emails" class="required"><bean:message key="user.eMail"/> *:</label>&nbsp;</td>
 <td>
<c:if test="${not empty sessionScope.userForm.emails}">
<display:table name="sessionScope.userForm.emails" class="list" decorator="ch.ess.cal.web.EmailDecorator">
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