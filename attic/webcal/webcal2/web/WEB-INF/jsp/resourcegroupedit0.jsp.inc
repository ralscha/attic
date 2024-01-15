
<input class="pageselected" type="submit" name="change.page.0" value="<bean:message key="resourcegroup.general"/>" /> | <input class="page" type="submit" name="change.page.1" value="<bean:message key="department.departments"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="user.users"/>" />
<br />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="resourceGroupForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[${ix}].name" class="required"><bean:message key="resourcegroup.name" /> (${entry.language}) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>
<tr>
<td valign="top"><bean:message key="calresource.resources"/>:&nbsp;</td>
<td>
<table><tr>
	<td class="titlesmall"><bean:message key="common.available"/>:</td>
	<td>&nbsp;</td>
	<td class="titlesmall"><bean:message key="common.selected"/>:</td>
	</tr>
	<tr>
	<td>
   <html:select style="width:140px" multiple="true" size="10" property="availableResourcesId">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection property="availableResources" />   
   </html:select>
   </td>
   <td>
   <input type="image" name="change.resoadd" height="16" width="16" border="0" src="<c:url value="/images/add.gif"/>" />
   <br />
   <input type="image" name="change.resoalladd" height="16" width="16" border="0" src="<c:url value="/images/addall.gif"/>" />
   </td>
   <td>
   <html:select style="width:140px" multiple="true" size="10" property="resourcesId">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection property="resources" />   
   </html:select>
   </td>   
   </tr>
</table>
</td>
</tr>

</table>
