<%@ page language="java" import="java.util.*, java.text.*, java.sql.*"%>
<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>
<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>
<%!
	private Calendar today;	
	int firstDayOfWeek;
	int requestDay;
	int requestMonth, nextMonth, prevMonth;
	int requestYear, nextYear, prevYear;
	int nextDay, prevDay;
	String requestMonthString;
	String requestYearString;
	String requestDayString;
	String userid;
	String link;
%>

<%
	today = Calendar.getInstance();	
	firstDayOfWeek = today.getFirstDayOfWeek();
	
	userid = request.getParameter("userid");
	requestMonthString = request.getParameter("month");
	requestYearString = request.getParameter("year");
	requestDayString = request.getParameter("day");

	link = request.getParameter("link");
	if (link == null)
		return;
		
	if (link.indexOf("?") == -1)
		link += "?";
	else
		link += "&";
		
	if (requestMonthString != null) {
		requestMonth = Integer.parseInt(requestMonthString);
	} else {
		requestMonth = today.get(Calendar.MONTH);		
	}
	
	if (requestYearString != null) {
		requestYear = Integer.parseInt(requestYearString);
	} else {
		requestYear = today.get(Calendar.YEAR);
		requestYearString = String.valueOf(requestYear);
	}
	
	if (requestDayString != null) {
		requestDay = Integer.parseInt(requestDayString);
	} else {
		requestDay = today.get(Calendar.DATE);		
	}
		
	prevMonth = nextMonth = requestMonth;
	prevYear  = nextYear  = requestYear;
	
	if (requestMonth > 0) {
		prevMonth--;
	} else {
		prevMonth = 11;
		prevYear--;
	}
	
	if (requestMonth < 11) {		
		nextMonth++;
	} else {
		nextMonth = 0;
		nextYear++;
	}
	
	nextDay = requestDay;
	prevDay = requestDay;
	
	Calendar test = new GregorianCalendar();
	test.set(Calendar.MONTH, nextMonth);
	test.set(Calendar.YEAR, nextYear);
	int max = test.getActualMaximum(Calendar.DAY_OF_MONTH);
	if (nextDay > max)
		nextDay = max;

	test.set(Calendar.MONTH, prevMonth);
	test.set(Calendar.YEAR, prevYear);
	max = test.getActualMaximum(Calendar.DAY_OF_MONTH);
	if (prevDay > max)
		prevDay = max;
	
%>


<script language="JavaScript" type="text/javascript">
<!-- 
function reopenDateSelector ()
{
	var yri = document.dateselector.yearselect.selectedIndex;
	window.document.location.href = '<%= link %>userid=<%= userid %>&month=' + document.dateselector.monthselect.selectedIndex + '&year=' + 
		document.dateselector.yearselect.options[yri].value ;
}
// -->
</script>

	<table width="150" border="0" cellspacing="1" cellpadding="3">
		<TR bgcolor="#A0B8C8">
			<td colspan="7" align="center" nowrap class="month">
  			 <a href="<%= link %>userid=<%= userid %>&day=<%= nextDay %>&month=<%= prevMonth %>&year=<%= prevYear %>"><img src="images/left.gif" width="11" height="11" border="0" alt=""></a>			 
			 <%= ch.ess.calendar.util.Constants.MONTHNAMES[requestMonth] %> <%= requestYearString %>
			 <a href="<%= link %>userid=<%= userid %>&day=<%= nextDay %>&month=<%= nextMonth %>&year=<%= nextYear %>"><img src="images/right.gif" width="11" height="11" border="0" alt=""></A>
            </td>			
		</TR>
		<TR bgcolor="#EEEEEE">
			<% int n = 0;
			   int pos = firstDayOfWeek;
			   while (n < 7) {
			%>
			<td align="right" class="week"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[pos] %></td>
			<%  	n++;
					pos++;
					if (pos > Calendar.SATURDAY)
						pos = Calendar.SUNDAY;
					} 
			%>
		</TR>
		<% Calendar tmpCalendar = (Calendar)today.clone();
		   tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		   tmpCalendar.set(Calendar.MONTH, requestMonth);
		   tmpCalendar.set(Calendar.YEAR, requestYear);
		   int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
		   if (firstDay < 0) 
		      firstDay += 7;
					 
		   tmpCalendar.add(Calendar.DAY_OF_MONTH, -firstDay);
		%>		
		<TR>
		<% for (int i = 0; i < firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { 
  		    List appList;
			if (login.isLogonOK()) {
				appList = appcache.getUserAppointments(userid, tmpCalendar, login.getUserid());
			} else {
			    appList = appcache.getUserAppointments(userid, tmpCalendar);
			}
		  %>	
		  <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					            (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
								(today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#FEFEFE"; %> 
			<td align="right" bgcolor="<%= bgcolor %>"><a href="<%= link%>userid=<%= userid %>&day=<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>&month=<%= tmpCalendar.get(Calendar.MONTH)%>&year=<%= tmpCalendar.get(Calendar.YEAR)%>" class="daysmall<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></a></td>
		<% } 
		for (int i = 0; i < 7-firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) {
		    List appList;
			if (login.isLogonOK()) {
				appList = appcache.getUserAppointments(userid, tmpCalendar, login.getUserid());
			} else {
			    appList = appcache.getUserAppointments(userid, tmpCalendar);
			}
		  %>	
		  <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					            (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
							    (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#F4F4F4"; %> 
		  <% boolean showa = ( (requestDay == tmpCalendar.get(Calendar.DATE)) &&
					           (requestMonth == tmpCalendar.get(Calendar.MONTH)) &&
							   (requestYear == tmpCalendar.get(Calendar.YEAR)) ) ? false : true; %> 
          <% if (showa) {%>
			<td align="right" bgcolor="<%= bgcolor %>"><a href="<%= link %>userid=<%= userid %>&day=<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>&month=<%= tmpCalendar.get(Calendar.MONTH)%>&year=<%= tmpCalendar.get(Calendar.YEAR)%>" class="day<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></a></td>
		  <% } else { %>
			<td align="right" bgcolor="<%= bgcolor.equals("Lime") ? bgcolor : "#E0E0E0"%>" class="day<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></td>
		  <% } %>
		<% } %>
		</TR>
		
		<% for (int j = 0; j < 5; j++) { %>
			<% if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
			<tr>
				<% for (int i = 0; i < 7; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { 
				      List appList;
			          if (login.isLogonOK()) {
				        appList = appcache.getUserAppointments(userid, tmpCalendar, login.getUserid());
			          } else {
			            appList = appcache.getUserAppointments(userid, tmpCalendar);
			          }
		  
					  if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
					   <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                         (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										     (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#F4F4F4"; %> 						  						  
					  <% boolean showa = ( (requestDay == tmpCalendar.get(Calendar.DATE)) &&
					                       (requestMonth == tmpCalendar.get(Calendar.MONTH)) &&
							               (requestYear == tmpCalendar.get(Calendar.YEAR)) ) ? false : true; %> 
			          <% if (showa) {%>											 
                		  <td align="right" bgcolor="<%= bgcolor %>"><a href="<%= link %>userid=<%= userid %>&day=<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>&month=<%= tmpCalendar.get(Calendar.MONTH)%>&year=<%= tmpCalendar.get(Calendar.YEAR)%>" class="day<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></a></td>
                      <% } else { %>
					      <td align="right" bgcolor="<%= bgcolor.equals("Lime") ? bgcolor : "#E0E0E0"%>" class="day<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></td>
					  <% } %>
				   <% } else { %>
   					   <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                         (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										     (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#FEFEFE"; %> 						  
						  <td align="right" bgcolor="<%= bgcolor %>"><a href="<%= link %>userid=<%= userid %>&day=<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>&month=<%= tmpCalendar.get(Calendar.MONTH)%>&year=<%= tmpCalendar.get(Calendar.YEAR)%>" class="daysmall<%= ((appList != null) && !appList.isEmpty()) ? "b": ""%>"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></a></td>
				   <% } %>  

			    <% } %>
			</tr>
			<% } %>
			<% } %>						
		
	</TABLE><BR>
	<FORM action="#" method="post" name="dateselector" id="dateselector">
	<select name="monthselect" class="day" onChange="reopenDateSelector()">
	<% for (int i = 0; i < 12; i++) { %>
	<option value="<%= i %>" <% if (i == requestMonth) {%>selected<% } %>><%= ch.ess.calendar.util.Constants.MONTHNAMES[i] %></option>
	<% } %>
	</select>

	<select name="yearselect" class="day" onChange="reopenDateSelector()">
	<% int currentYear = today.get(Calendar.YEAR);
	   int startYear = currentYear;
	   int endYear = currentYear;
   
	   if (startYear > requestYear)
   		  startYear = requestYear;
	   if (endYear < requestYear) 
    	  endYear = requestYear;	
	  
	   for (int i = startYear-1; i <= endYear+2; i++) { %>
	<option value="<%= i %>" <% if (i == requestYear) {%>selected<% } %>><%= i %></option>
	<% } %>

    </select>	
	</form>
	<a href="<%= link %>userid=<%= userid %>&day=<%= today.get(Calendar.DAY_OF_MONTH)%>&month=<%= today.get(Calendar.MONTH)%>&year=<%= today.get(Calendar.YEAR)%>" class="day">Aujourd'hui: <%= ch.ess.calendar.util.Constants.dateFormat.format(today.getTime()) %></a>