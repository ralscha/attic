<input class="page" type="submit" name="change.page.0" value="<bean:message key="resourcegroup.general"/>" /> | <input class="pageselected" type="submit" name="change.page.1" value="<bean:message key="department.departments"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="user.users"/>" />
<br />

<table class="inputform">

<tr>
<td valign="top"><bean:message key="department.departments"/>:&nbsp;</td>
<td>
<table><tr>
	<td class="titlesmall"><bean:message key="common.available"/>:</td>
	<td>&nbsp;</td>
	<td class="titlesmall"><bean:message key="common.selected"/>:</td>
	</tr>
	<tr>
	<td>
   <html:select style="width:140px" multiple="true" size="10" property="availableDepartmentsId">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection property="availableDepartments" />   
   </html:select>
   </td>
   <td>
   <input type="image" name="change.depoadd" height="16" width="16" border="0" src="<c:url value="/images/add.gif"/>" />
   <br />
   <input type="image" name="change.depoalladd" height="16" width="16" border="0" src="<c:url value="/images/addall.gif"/>" />
   <br />
   <input type="image" name="change.deporemove" height="16" width="16" border="0" src="<c:url value="/images/remove.gif"/>" />
   <br />
   <input type="image" name="change.depoallremove" height="16" width="16" border="0" src="<c:url value="/images/removeall.gif"/>" />         
   </td>
   <td>
   <html:select style="width:140px" multiple="true" size="10" property="departmentsId">
     <html:option value=" ">&nbsp;</html:option>
     <html:optionsCollection property="departments" />   
   </html:select>
   </td>   
   </tr>
</table>
</td>
</tr>

</table>
