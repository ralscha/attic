
<input class="pageselected" type="submit" name="change.page.0" value="<bean:message key="event.general"/>" /> | <input class="page" type="submit" name="change.page.1" value="<bean:message key="event.reminder"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="event.recurrence"/>" />
<br />

<table class="inputform">


<misc:initSelectOptions id="departmentOption" type="ch.ess.cal.web.DepartmentOptions" scope="session" />
<tr>
 <td><misc:label property="departmentId" key="department"/>&nbsp;</td>
 <td>
   <html:select tabindex="5" property="departmentId">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="departmentOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<tr>
 <td><misc:label property="subject" key="event.subject"/>&nbsp;</td>
 <td><html:text tabindex="10" property="subject" size="60" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="location" key="event.location"/>&nbsp;</td>
 <td><html:text tabindex="20" property="location" size="60" maxlength="255"/></td>
</tr>

<tr>
 <td><misc:label property="start" key="event.start"/>&nbsp;</td>
 <td><html:text tabindex="30" property="start" size="10" maxlength="10"/><misc:popupCalendar form="eventForm" element="start" past="true" />
 &nbsp;&nbsp;<html:text tabindex="40" property="startHour" size="2" maxlength="2"/>:<html:text tabindex="50" property="startMinute" size="2" maxlength="2"/>
 &nbsp;&nbsp;<html:checkbox tabindex="90" property="allday"/>&nbsp;<misc:label property="allday" key="event.allday" prefix=""/>
 </td>
</tr>
<tr>
 <td><misc:label property="end" key="event.end"/>&nbsp;</td>
 <td><html:text tabindex="60" property="end" size="10" maxlength="10"/><misc:popupCalendar form="eventForm" element="end" past="true" />
 &nbsp;&nbsp;<html:text tabindex="70" property="endHour" size="2" maxlength="2"/>:<html:text tabindex="80" property="endMinute" size="2" maxlength="2"/> 
 </td>
</tr>

<misc:initSelectOptions id="categoryOption" type="ch.ess.cal.web.CategoryOptions" scope="session" />
<tr>
 <td><misc:label property="categoryId" key="category"/>&nbsp;</td>
 <td>
   <html:select tabindex="100" property="categoryId">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="categoryOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<misc:initSelectOptions id="importanceOption" type="ch.ess.cal.web.event.ImportanceOptions" scope="session" />
<tr>
 <td><misc:label property="importance" key="event.importance"/>&nbsp;</td>
 <td>
   <html:select tabindex="103" property="importance">
     <html:optionsCollection name="importanceOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<misc:initSelectOptions id="sensitivityOption" type="ch.ess.cal.web.event.SensitivityOptions" scope="session" />
<tr>
 <td><misc:label property="sensitivity" key="event.sensitivity"/>&nbsp;</td>
 <td>
   <html:select tabindex="106" property="sensitivity">
     <html:optionsCollection name="sensitivityOption" property="labelValue"/>   
   </html:select>
 </td>
</tr> 

<tr>
 <td valign="top"><misc:label property="description" key="event.description"/>&nbsp;</td>
 <td><html:textarea tabindex="110" property="description" cols="60" rows="5" /></td>
</tr>

</table>
