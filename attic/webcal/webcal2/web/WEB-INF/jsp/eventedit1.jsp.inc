
<input class="page" type="submit" name="change.page.0" value="<bean:message key="event.general"/>" /> | <input class="pageselected" type="submit" name="change.page.1" value="<bean:message key="event.reminder"/>" /> | <input class="page" type="submit" name="change.page.2" value="<bean:message key="event.recurrence"/>" />
<br />

<misc:initSelectOptions id="emailOption" type="ch.ess.cal.web.event.EmailOptions" scope="session" />
<misc:initSelectOptions id="timeOption" type="ch.ess.cal.web.userconfig.TimeOptions" scope="session" />

<table class="inputform">

<tr>
 <td>
 
 <c:if test="${not empty sessionScope.eventForm.reminders}">
<display:table name="sessionScope.eventForm.reminders" class="list" decorator="ch.ess.cal.web.event.ReminderDecorator">
  <display:column property="email" maxLength="25" width="213" align="left">
     <misc:title><bean:message key="user.eMail"/></misc:title>
  </display:column>
  <display:column property="timeStr" maxLength="14" width="120" align="left">
     <misc:title><bean:message key="event.reminder.time"/></misc:title>
  </display:column>  
  <display:column property="delete" title="" align="left"></display:column>
  <display:setProperty name="basic.show.header" value="true"/>
</display:table>
</c:if>
 <br />
 <table>
 <tr>
 <td><bean:message key="user.eMail"/></td>
 <td><bean:message key="event.reminder.time"/></td> 
 <td>&nbsp;</td>
 </tr>
 <tr>
<td><html:text property="email" size="27" maxlength="100"/></td>
<td><html:text property="time" size="4" maxlength="4"/>&nbsp;
<html:select property="timeUnit">
	     <html:optionsCollection name="timeOption" property="labelValue"/>   
	   </html:select> 
</td>
<td><input type="submit" name="change.addreminder" value="<bean:message key="common.add"/>" /></td>
</tr>
<tr>
<td colspan="3">
   <html:select property="emailSelect">
     <html:option value="">&nbsp;</html:option>
     <html:optionsCollection name="emailOption" property="labelValue"/>   
   </html:select>
</td>   
</tr>
</table>


 </td>
</tr>


</table>
