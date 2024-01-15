
<input class="page" type="submit" name="change.page.0" value="<bean:message key="event.general"/>" /> | <input class="page" type="submit" name="change.page.1" value="<bean:message key="event.reminder"/>" /> | <input class="pageselected" type="submit" name="change.page.2" value="<bean:message key="event.recurrence"/>" />
<br />

<misc:initSelectOptions id="posOption" type="ch.ess.cal.web.event.PosOptions" scope="session" />
<misc:initSelectOptions id="weekdayOption" type="ch.ess.cal.web.event.WeekdayOptions" scope="session" />
<misc:initSelectOptions id="monthOption" type="ch.ess.cal.web.MonthOptions" scope="session" />

<table class="inputform">

<tr>
 <td colspan="2">&nbsp;<html:checkbox property="recurrenceActive"/>&nbsp;<bean:message key="event.recurrence.active"/></td>
</tr>
<tr><td colspan="2">
<br />&nbsp;
<bean:message key="event.recurrence.pattern" />
<br />&nbsp;
</td></tr>

<tr>
 <td valign="top">
 <html:radio property="recurrenceType" value="0" />&nbsp;<bean:message key="event.recurrence.daily"/>&nbsp;
 </td>
 <td>
 <table cellpadding="5">
 <tr>
 <td><bean:message key="event.recurrence.daily.every"/> <html:text property="dailyEveryDay" size="4" maxlength="6"/>&nbsp;<bean:message key="event.recurrence.days"/></td>
 </tr>
 </table>
 </td>
</tr>

<tr>
 <td valign="top">
 <html:radio property="recurrenceType" value="1" />&nbsp;<bean:message key="event.recurrence.weekly"/>&nbsp;
 </td>
 <td>
 <table cellpadding="5">
 <tr><td>
 <bean:message key="event.recurrence.recurevery"/> <html:text property="weeklyEveryWeek" size="4" maxlength="6"/>&nbsp;<bean:message key="event.recurrence.weeks"/>:
 </td></tr>
  <tr><td>
  <table>
  <tr>

  <c:forEach items="${sessionScope.weekdayOption.labelValue}" var="labelValue" varStatus="stat">

	<td><html:multibox property="weeklyWeekday" value="${labelValue.value}"/>${labelValue.label}"</td>
	
	<c:if test="${stat.index == 3}">
      </tr><tr>	  
	</c:if>
  </c:forEach>
 
  <td>&nbsp;</td>
  </tr>
  </table>
  </td></tr>
</table>
 </td>
</tr>
<tr>
 <td valign="top">
 <html:radio property="recurrenceType" value="2" />&nbsp;<bean:message key="event.recurrence.monthly"/>&nbsp;
 </td>
 <td>
 <table cellpadding="5">
 <tr>
 <td><html:radio property="monthlyType" value="2" /></td><td><bean:message key="event.recurrence.day"/></td><td><html:text property="monthlyDay" size="4" maxlength="6"/> <bean:message key="event.recurrence.ofevery"/> <html:text property="monthlyEveryMonth" size="4" maxlength="6"/> <bean:message key="event.recurrence.months"/></td>
 </tr>
 <tr>
 <td><html:radio property="monthlyType" value="3" /></td><td><bean:message key="event.recurrence.the"/></td>
 <td>
 <html:select property="monthlyPos">
  <html:optionsCollection name="posOption" property="labelValue"/>   
 </html:select>
   <html:select property="monthlyWeekdayPos">
     <html:optionsCollection name="weekdayOption" property="labelValue"/>   
   </html:select>
&nbsp;<bean:message key="event.recurrence.ofevery"/> <html:text property="monthlyEveryMonthPos" size="4" maxlength="6"/> <bean:message key="event.recurrence.months"/>
</td>
</tr>
</table>
 </td>
</tr>
<tr>
 <td valign="top">
 <html:radio property="recurrenceType" value="4" />&nbsp;<bean:message key="event.recurrence.yearly"/>&nbsp;
 </td>
 <td>
  <table cellpadding="5">
  <tr>
 <td><html:radio property="yearlyType" value="4" /></td><td><bean:message key="event.recurrence.yearly.every"/></td>
  <td><html:text property="yearlyEveryDay" size="4" maxlength="6"/>&nbsp;
  <html:select property="yearlyEveryMonth">
  <html:optionsCollection name="monthOption" property="labelValue"/>   
 </html:select>
  </td></tr>
  <tr>
 <td><html:radio property="yearlyType" value="5" /></td><td><bean:message key="event.recurrence.the"/></td>
  <td>
 <html:select property="yearlyPos">
  <html:optionsCollection name="posOption" property="labelValue"/>   
 </html:select>
 <html:select property="yearlyWeekdayPos">
  <html:optionsCollection name="weekdayOption" property="labelValue"/>   
 </html:select>
 &nbsp;<bean:message key="event.recurrence.of"/>&nbsp;
 <html:select property="yearlyMonthPos">
  <html:optionsCollection name="monthOption" property="labelValue"/>   
 </html:select>
 </td>
 </tr>
</table>
 </td>
</tr>
<tr><td colspan="2">
<br />&nbsp;
<bean:message key="event.recurrence.range"/>
<br />&nbsp;
</td></tr>
<tr>
<td colspan="2">

<table cellpadding="5">
<tr>
<td><html:radio property="rangeType" value="0" /></td><td colspan="2"><bean:message key="event.recurrence.range.noend"/></td>
</tr>
<tr>
<td><html:radio property="rangeType" value="1" /></td><td><bean:message key="event.recurrence.range.endafter"/>: </td><td><html:text property="rangeOccurrences" size="4" maxlength="6"/> <bean:message key="event.recurrence.range.occurrences"/></td>
</tr>
<tr>
<td><html:radio property="rangeType" value="2" /></td><td><bean:message key="event.recurrence.range.endby"/>: </td><td><html:text property="rangeEnd" size="10" maxlength="10"/><misc:popupCalendar form="eventForm" element="rangeEnd" past="true" /></td>
</tr>
</table>

</td>
</tr>

</table>
