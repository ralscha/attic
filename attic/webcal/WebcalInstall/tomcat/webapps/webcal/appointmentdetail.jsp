<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Manage Appointments" errorPage="error.jsp"%>

<% 
    SQLManager manager = SQLManager.getInstance();
    Connection conn = manager.requestConnection();
%>

<jsp:useBean id="apprequest" scope="request" class="ch.ess.calendar.AppointmentRequest">
<jsp:setProperty name="apprequest" property="*"/>
</jsp:useBean>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.setConnection(conn); %>
</jsp:useBean>


<% if (apprequest.isCancelClicked()) { 
		if (session.getAttribute("frompath") != null) { 
		    String path = (String)session.getAttribute("frompath"); 
			session.removeAttribute("frompath"); %>
			<jsp:forward page="<%= path %>"/>				  
<%		} else { %>
			<jsp:forward page="appointmentslist.jsp"/>		
<%      }			  
   } else if (apprequest.isSaveClicked()) {
     if (apprequest.commitChange(login.getUserid())) {
	    if (!apprequest.isAddAnotherClicked()) { 
		  if (session.getAttribute("frompath") != null) { 
		    String path = (String)session.getAttribute("frompath"); 
			session.removeAttribute("frompath"); %>
			<jsp:forward page="<%= path %>"/>				  
<%		  } else { %>
			<jsp:forward page="appointmentslist.jsp"/>		
<%        }
		} else {
		    if (!login.isAdmin())
				apprequest.initAfterAddAnother();
		}
	 }
   } else if (apprequest.isResetClicked()) {
   	   apprequest.init();
   }
%>
 
<html>
<head>
	<title>ESS Web Calendar: Add/Update Appointments</title>
	<meta http-equiv="pragma" content="no-cache">
	<% ch.ess.calendar.util.BrowserDetector bd = new ch.ess.calendar.util.BrowserDetector(request);
	   if (bd.getBrowserName().equals(ch.ess.calendar.util.BrowserDetector.MOZILLA)) {
	%>	
	<link rel="STYLESHEET" type="text/css" href="plannernn.css">
	<% } else { %>
	<link rel="STYLESHEET" type="text/css" href="planner.css">
	<% } %>
<script language="JavaScript" type="text/javascript">
<!--
  function calpopup(lnk) { 
  window.open(lnk, "calendar","height=250,width=250,scrollbars=no") 
  }
//-->
</script>	
	
</head>

<body>
<%@ include file="menu.jsp"%>
<P>
<form action="appointmentdetail.jsp" method="post" name="appform" id="appform">
<% if (apprequest.isUpdate()) { %>
	<input type="hidden" name="updateappid" id="updateappid" value="<%= apprequest.getUpdateappid() %>">
<% } %>
 <table width="70%" cellspacing="3" cellpadding="3">	
		<tr>
		    <% if ((apprequest.getErrorText() != null) && (!apprequest.getErrorText().equals(""))) { %>
			    <td align="left" bgcolor="Red"><font color="Black"><b><%= apprequest.getErrorText()%></b></font></td>
			<% } else { %>
				<td>&nbsp;</td>
			<% } %>
			<td align="right">
			    <% if (apprequest.isUpdate()) { %>
			  	<input type="submit" name="save" value="Update">
				<% } else { %>
			  	<input type="submit" name="save" value="Save">
				<input type="submit" name="saveadd" value="Save and Add Another">
				<% } %>				
				<% if (!apprequest.isUpdate() && login.isAdmin()) { %>
				<input type="submit" name="reset" value="Reset">
				<% } %>
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
  </table>
  <table width="70%" border="1" cellspacing="3" cellpadding="3">
  <TR>
	  <% if (apprequest.isUpdate()) { %>
    	<th colspan="4" align="left" bgcolor="Silver">Edit Appointment<% if (login.isAdmin()) { %> &nbsp;&nbsp;User: <select name="forwhom" size="1">
							<option value="group" <% if (apprequest.forGroup()) { %>SELECTED<% } %>>group</option>
							<%  UsersTable usersTable = new UsersTable(conn);
							    Iterator it = usersTable.select(null, "firstname");
                                while (it.hasNext()) {
 	                                Users user = (Users)it.next();
							%>        
							<option value="<%= user.getUserid() %>" <% if (user.getUserid().equals(apprequest.getForwhom())) { %>SELECTED<% } %>><%= user.getFirstname() %> <%= user.getName() %></option>
							<% } %>
							</select><% } %></th>							
							<% if (!login.isAdmin()) { %>
							<input type="hidden" name="forwhom" value="<%= apprequest.getForwhom() %>">
							<% } %>
	  <% } else { %>
	    <th colspan="4" align="left" bgcolor="Silver">New Appointment <% if (login.isAdmin()) { apprequest.setForwhom(login.getUserid()); %> &nbsp;&nbsp;User: <select name="forwhom" size="1">
							<option value="group" <% if (apprequest.forGroup()) { %>SELECTED<% } %>>group</option>
							<%  UsersTable usersTable = new UsersTable(conn);
							    Iterator it = usersTable.select(null, "firstname");
                                while (it.hasNext()) {
 	                                Users user = (Users)it.next();
							%>        
							<option value="<%= user.getUserid() %>" <% if (user.getUserid().equals(apprequest.getForwhom())) { %>SELECTED<% } %>><%= user.getFirstname() %> <%= user.getName() %></option>
							<% } %>
							</select><% } %></th>
	  <% } %>
  </TR>
  <TR>
    <td colspan="2">Title: <input type="text" name="title" value="<%= apprequest.getTitle() %>" size="40" maxlength="50"></td>
    <td colspan="2">Category: <select name="category" size="1">
	 <% Integer[] catkeys = categoriesMap.getKeys();
	    for (int i = 0; i < catkeys.length; i++) { %>
			<option value="<%= catkeys[i] %>" <% if (catkeys[i].intValue() == apprequest.getCategory()) { %>selected<% } %>><%= categoriesMap.getDescription(catkeys[i]) %></option>
	 <% } %>		
			</select>
			<input type="checkbox" name="secret" value="Y" <% if (apprequest.isPriv()) {%>checked<% } %>>secret
	</td>
  </TR>
  <TR>
    <td width="10" align="left" valign="top"><input type="radio" name="mode" value="<%= apprequest.ONE_MODE %>" <% if (apprequest.getMode() == apprequest.ONE_MODE) { %>checked<% } %>></td>
    <td align="left" valign="top">
	   <table>
		<tr>
			<td>Date: <input type="text" name="onedate" value="<%= apprequest.getOnedate() %>" size="10" maxlength="10">
			<a href="javascript:calpopup('calendar.jsp?form=appform&elem=onedate')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a>
			</td>
		</tr>
		<tr>
			<td><input type="radio" name="event" value="<%= apprequest.ALLDAY_EVENT %>" <% if (apprequest.getEvent() == apprequest.ALLDAY_EVENT) {%>checked<% } %>>All day appointment</td>
		</tr>
		<tr>
			<td><input type="radio" name="event" value="<%= apprequest.TIMED_EVENT %>" <% if (apprequest.getEvent() == apprequest.TIMED_EVENT) {%>checked<% } %>>Timed appointment (HH:MM): <input type="text" name="starttime" value="<%= apprequest.getStarttime() %>" size="6" maxlength="5"> to <input type="text" name="endtime" value="<%= apprequest.getEndtime() %>" size="6" maxlength="5"></td>
		</tr>
		</table>
	
	</td>
    <td colspan="2" rowspan="2">Notes:<br><textarea cols="25" rows="8" name="notes" wrap="soft" id="notes"><%= apprequest.getNotes() %></textarea></td>
  </TR>
  <TR>
    <td width="10" align="left" valign="top"><input type="radio" name="mode" value="<%= apprequest.TWO_MODE %>" <% if (apprequest.getMode() == apprequest.TWO_MODE) {%>checked<% } %>></td>
    <td align="left" valign="top"><table>
		<tr>
			<td>Start Date:</td>
			<td><input type="text" name="startdate" value="<%= apprequest.getStartdate() %>" size="10" maxlength="10">
			<a href="javascript:calpopup('calendar.jsp?form=appform&elem=startdate')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a>
			</td>
		</tr>
		<tr>
			<td>End Date:</td>
			<td><input type="text" name="enddate" value="<%= apprequest.getEnddate() %>" size="10" maxlength="10">
			<a href="javascript:calpopup('calendar.jsp?form=appform&elem=enddate')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a>
			</td>
		</tr>
		</table>

	</td>
   </TR>      
   <tr>
        <td colspan="4">
	    <table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th colspan="4" align="left">Repeating</th>
		</tr>
		</table>
        </td>   	
   </tr>
   <tr>
    <td width="10" align="left" valign="top"><input type="radio" name="repeatingmode" value="<%= apprequest.REPEATING_OFF %>" <% if (apprequest.getRepeatingmode() == apprequest.REPEATING_OFF) { %>checked<% } %>></td>
	<td colspan="4" align="left">Do not repeat this appointment</td>
   </tr>
   <tr>
    <td width="10" align="left" valign="top"><input type="radio" name="repeatingmode" value="<%= apprequest.REPEATING_EVERY %>" <% if (apprequest.getRepeatingmode() == apprequest.REPEATING_EVERY) { %>checked<% } %>></td>
	<td colspan="2" align="left">Repeat <select name="every" size="1">
		<option value="<%= Repeaters.EVERY%>" <% if (apprequest.getEvery() == Repeaters.EVERY) {%>selected<% } %>>Every</option>
			<option value="<%= Repeaters.EVERYSECOND%>" <% if (apprequest.getEvery() == Repeaters.EVERYSECOND) {%>selected<% } %>>Every second</option>
			<option value="<%= Repeaters.EVERYTHIRD%>" <% if (apprequest.getEvery() == Repeaters.EVERYTHIRD) {%>selected<% } %>>Every third</option>
			<option value="<%= Repeaters.EVERYFOURTH%>" <% if (apprequest.getEvery() == Repeaters.EVERYFOURTH) {%>selected<% } %>>Every fourth</option>
		</select>
		<select name="everyperiod" size="1">
			<option value="<%= Repeaters.EVERYPERIOD_DAY%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_DAY) {%>selected<% } %>>Day</option>
			<option value="<%= Repeaters.EVERYPERIOD_WEEK%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_WEEK) {%>selected<% } %>>Week</option>
			<option value="<%= Repeaters.EVERYPERIOD_MONTH%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_MONTH) {%>selected<% } %>>Month</option>
			<option value="<%= Repeaters.EVERYPERIOD_YEAR%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_YEAR) {%>selected<% } %>>Year</option>
			<option value="<%= Repeaters.EVERYPERIOD_MWF%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_MWF) {%>selected<% } %>>Mon, Wed, Fri</option>
			<option value="<%= Repeaters.EVERYPERIOD_TT%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_TT) {%>selected<% } %>>Tue &amp; Thu</option>
			<option value="<%= Repeaters.EVERYPERIOD_WORKWEEK%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_WORKWEEK) {%>selected<% } %>>Mon - Fr</option>
			<option value="<%= Repeaters.EVERYPERIOD_WEEKEND%>" <% if (apprequest.getEveryperiod() == Repeaters.EVERYPERIOD_WEEKEND) {%>selected<% } %>>Sat &amp; Sun</option>
		</select>
	</td>
	<td rowspan="2">
	<table>
	  <tr>
			<td align="left"><input type="radio" name="repeatingvalid" value="<%= apprequest.REPEATINGVALID_ALWAYS%>" <% if (apprequest.getRepeatingvalid() == apprequest.REPEATINGVALID_ALWAYS) { %>checked<% } %>>Always</td>
	  </tr>
	  <tr>
			<td align="left"><input type="radio" name="repeatingvalid" value="<%= apprequest.REPEATINGVALID_UNTIL%>" <% if (apprequest.getRepeatingvalid() == apprequest.REPEATINGVALID_UNTIL) { %>checked<% } %>>Until <input type="text" name="repeatinguntil" value="<%= apprequest.getRepeatinguntil() %>" size="10" maxlength="10">
			<a href="javascript:calpopup('calendar.jsp?form=appform&elem=repeatinguntil')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt=""></a></td>
      </tr>
	</table>
	</td>
   </tr>
   <tr>
    <td width="10" align="left" valign="top"><input type="radio" name="repeatingmode" value="<%= apprequest.REPEATING_ON %>" <% if (apprequest.getRepeatingmode() == apprequest.REPEATING_ON) { %>checked<% } %>></td>
		   <td colspan="2" align="left">Repeat on the <select name="repeaton">
			<option value="<%= Repeaters.REPEATON_FIRST%>" <% if (apprequest.getRepeaton() == Repeaters.REPEATON_FIRST) {%>selected<% } %>>First</option>
			<option value="<%= Repeaters.REPEATON_SECOND%>" <% if (apprequest.getRepeaton() == Repeaters.REPEATON_SECOND) {%>selected<% } %>>Second</option>
			<option value="<%= Repeaters.REPEATON_THIRD%>" <% if (apprequest.getRepeaton() == Repeaters.REPEATON_THIRD) {%>selected<% } %>>Third</option>
			<option value="<%= Repeaters.REPEATON_FOURTH%>" <% if (apprequest.getRepeaton() == Repeaters.REPEATON_FOURTH) {%>selected<% } %>>Fourth</option>
			<option value="<%= Repeaters.REPEATON_LAST%>" <% if (apprequest.getRepeaton() == Repeaters.REPEATON_LAST) {%>selected<% } %>>Last</option>
		    </select>
		    <select name="repeatonweekday">
			<option value="<%= Calendar.MONDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.MONDAY) {%>selected<% } %>>Mon</option>
			<option value="<%= Calendar.TUESDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.TUESDAY) {%>selected<% } %>>Tue</option>
			<option value="<%= Calendar.WEDNESDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.WEDNESDAY) {%>selected<% } %>>Wed</option>
			<option value="<%= Calendar.THURSDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.THURSDAY) {%>selected<% } %>>Thu</option>
			<option value="<%= Calendar.FRIDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.FRIDAY) {%>selected<% } %>>Fri</option>
			<option value="<%= Calendar.SATURDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.SATURDAY) {%>selected<% } %>>Sat</option>
			<option value="<%= Calendar.SUNDAY %>" <% if (apprequest.getRepeatonweekday() == Calendar.SUNDAY) {%>selected<% } %>>Sun</option>
			</select><br>
			of the month every <select name="repeatonperiod">
			<option value="<%= Repeaters.REPEATONPERIOD_MONTH%>" <% if (apprequest.getRepeatonperiod() == Repeaters.REPEATONPERIOD_MONTH) {%>selected<% } %>>month</option>
			<option value="<%= Repeaters.REPEATONPERIOD_2MONTH%>" <% if (apprequest.getRepeatonperiod() == Repeaters.REPEATONPERIOD_2MONTH) {%>selected<% } %>>2 month</option>
			<option value="<%= Repeaters.REPEATONPERIOD_3MONTH%>" <% if (apprequest.getRepeatonperiod() == Repeaters.REPEATONPERIOD_3MONTH) {%>selected<% } %>>3 month</option>
			<option value="<%= Repeaters.REPEATONPERIOD_4MONTH%>" <% if (apprequest.getRepeatonperiod() == Repeaters.REPEATONPERIOD_4MONTH) {%>selected<% } %>>4 month</option>
			<option value="<%= Repeaters.REPEATONPERIOD_6MONTH%>" <% if (apprequest.getRepeatonperiod() == Repeaters.REPEATONPERIOD_6MONTH) {%>selected<% } %>>6 month</option>
			</select>
			</td> 
  </TR>
  <tr>
        <td colspan="4">
	    <table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th colspan="4" align="left">Reminder</th>
		</tr>
		</table>
        </td>   	
   </tr>
   <tr>
    <td width="10" align="left" valign="top"><input type="radio" name="reminderMode" value="<%= apprequest.REMINDER_OFF %>" <% if (apprequest.getReminderMode() == apprequest.REMINDER_OFF) {%>checked<% } %>></td>
	<td colspan="4" align="left">Do not send a reminder</td>
   </tr>
   <tr>
    <td width="10" align="left" valign="top"><input type="radio" name="reminderMode" value="<%= apprequest.REMINDER_ON %>" <% if (apprequest.getReminderMode() == apprequest.REMINDER_ON) {%>checked<% } %>></td>
    <td colspan="3">
  	  <table>
		<tr>
		   <td align="left">Send a reminder&nbsp;<select name="reminderMinutes">
			<option value="5" <% if (apprequest.getReminderMinutes() == 5) {%>selected<% } %>>5 Minutes</option>
			<option value="10" <% if (apprequest.getReminderMinutes() == 10) {%>selected<% } %>>10 Minutes</option>
			<option value="15" <% if (apprequest.getReminderMinutes() == 15) {%>selected<% } %>>15 Minutes</option>
			<option value="30" <% if (apprequest.getReminderMinutes() == 30) {%>selected<% } %>>30 Minutes</option>
			<option value="60" <% if (apprequest.getReminderMinutes() == 60) {%>selected<% } %>>1 hour</option>
			<option value="120" <% if (apprequest.getReminderMinutes() == 120) {%>selected<% } %>>2 hours</option>
			<option value="180" <% if (apprequest.getReminderMinutes() == 180) {%>selected<% } %>>3 hours</option>
			<option value="240" <% if (apprequest.getReminderMinutes() == 240) {%>selected<% } %>>6 hours</option>
			<option value="720" <% if (apprequest.getReminderMinutes() == 720) {%>selected<% } %>>12 hours</option>
			<option value="1440" <% if (apprequest.getReminderMinutes() == 1440) {%>selected<% } %>>1 day</option>
			<option value="2880" <% if (apprequest.getReminderMinutes() == 2880) {%>selected<% } %>>2 days</option>
			<option value="4320" <% if (apprequest.getReminderMinutes() == 4320) {%>selected<% } %>>3 days</option>
			<option value="5760" <% if (apprequest.getReminderMinutes() == 5760) {%>selected<% } %>>4 days</option>
			<option value="7200" <% if (apprequest.getReminderMinutes() == 7200) {%>selected<% } %>>5 days</option>
			<option value="8640" <% if (apprequest.getReminderMinutes() == 8640) {%>selected<% } %>>6 days</option>
			<option value="10080" <% if (apprequest.getReminderMinutes() == 10080) {%>selected<% } %>>7 days</option>
			<option value="11520" <% if (apprequest.getReminderMinutes() == 11520) {%>selected<% } %>>8 days</option>
			<option value="12960" <% if (apprequest.getReminderMinutes() == 12960) {%>selected<% } %>>9 days</option>
			<option value="14400" <% if (apprequest.getReminderMinutes() == 14400) {%>selected<% } %>>10 days</option>
			<option value="15840" <% if (apprequest.getReminderMinutes() == 15840) {%>selected<% } %>>11 days</option>
			<option value="17280" <% if (apprequest.getReminderMinutes() == 17280) {%>selected<% } %>>12 days</option>
			<option value="18720" <% if (apprequest.getReminderMinutes() == 18720) {%>selected<% } %>>13 days</option>
			<option value="20160" <% if (apprequest.getReminderMinutes() == 20160) {%>selected<% } %>>14 days</option>			
		    </select>
			before the appointment via
			</td>
		</tr>
		<tr>
			<td align="left">email: <input type="text" name="reminderEmail" value="<%= apprequest.getReminderEmail() %>" size="30" maxlength="100"></td>
		</tr>		
		</table>
	</td>
 
  </TR>
       
</TABLE>
 <table width="70%" cellspacing="3" cellpadding="3">	
		<tr>
			<td align="right">
			    <% if (apprequest.isUpdate()) { %>
			  	<input type="submit" name="save" value="Update">
				<% } else { %>
			  	<input type="submit" name="save" value="Save">
				<input type="submit" name="saveadd" value="Save and Add Another">
				<% } %>				
				<% if (!apprequest.isUpdate() && login.isAdmin()) { %>
				<input type="submit" name="reset" value="Reset">
				<% } %>
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
  </table>
</form>

</body>
</html>