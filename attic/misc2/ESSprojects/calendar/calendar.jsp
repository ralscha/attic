<%@ page language="java" import="java.util.*, java.text.*" session="false"%>
<%!
	private final static Locale local = new Locale("de", "CH");
	//private final static Locale local = Locale.UK;
	private final static DateFormatSymbols symbols = new DateFormatSymbols(local); 
	private final static String[] MONTHNAMES = symbols.getMonths();
	private final static String[] WEEKDAYS   = symbols.getShortWeekdays();	
	private final Calendar today = Calendar.getInstance(local);
	
	int firstDayOfWeek = today.getFirstDayOfWeek();
	int requestMonth, nextMonth, prevMonth;
	int requestYear, nextYear, prevYear;
	String requestMonthString;
	String requestYearString;
%>

<%
	requestMonthString = request.getParameter("m");
	requestYearString = request.getParameter("y");
	
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
%>

<html>
<head><title>Date Selector</title></head>
<body vlink=blue link=blue bgcolor=ffffff>
<form name=dateselector>
<script language=javascript>
<!-- 
function setOpenerDate(d, m, y)
{
	var i;
	var len;
	window.opener.document.dateform.month.selectedIndex = m;
	window.opener.document.dateform.day.selectedIndex = d-1;
	len = window.opener.document.dateform.year.options.length;
	for (i = 0; i < len; i++)
	{
		if (window.opener.document.dateform.year.options[i].value == y)
		{
			window.opener.document.dateform.year.selectedIndex = i;
			break;
		}
	}
	window.close();
}
function reopenDateSelector ()
{
	var yri = document.dateselector.year.selectedIndex;
	window.document.location.href = 'calendar.jsp?m=' + document.dateselector.month.selectedIndex + '&y=' + 
		document.dateselector.year.options[yri].value ;
}
// -->
</script>
<table cellpadding=0 cellspacing=0 border=0 width="100%">
<tr>
	<td align=center>
		<table cellpadding=0 cellspacing=0 border=0 width=165>
		<tr bgcolor=cccccc>
			<td align=center>
				<table cellpadding=3 cellspacing=1 border=0 width=170>				
				<tr bgcolor=a0b8c8>
					<td align=center nowrap colspan=7>
						<a href="calendar.jsp?m=<%= prevMonth %>&y=<%= prevYear %>"><img src="left.gif" width="11" height="11" border="0" alt=""></a>
						<font size=-1 face="Arial,Helvetica">
						<b><%= MONTHNAMES[requestMonth] %> <%= requestYearString %></b>
						</font>
						<a href="calendar.jsp?m=<%= nextMonth %>&y=<%= nextYear %>"><img src="right.gif" width="11" height="11" border="0" alt=""></A>
					</td>
				</tr>
				<tr bgcolor=eeeeee>					
					<% int n = 0;
					   int pos = firstDayOfWeek;
					   while (n < 7) {
					%>
							<td align=right><font face="monospace" size="-1"><%= WEEKDAYS[pos] %></font></td>
					<%  	n++;
							pos++;
							if (pos > Calendar.SATURDAY)
								pos = Calendar.SUNDAY;
						} 
					%>
				</tr>
				
				<% Calendar tmpCalendar = (Calendar)today.clone();
				   tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
				   tmpCalendar.set(Calendar.MONTH, requestMonth);
				   tmpCalendar.set(Calendar.YEAR, requestYear);
				   int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
				   if (firstDay < 0) 
				   	 firstDay += 7;
					 
				   tmpCalendar.add(Calendar.DAY_OF_MONTH, -firstDay);
				%>				
				<tr bgcolor=ffffff>
				    <% for (int i = 0; i < firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { %>
					<td align="right"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-2" color="#999999"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					<% } 
					   for (int i = 0; i < 7-firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) {
					%>
					<td align="right"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-1"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					<% } %>
				</tr>

				<% for (int j = 0; j < 5; j++) { %>
				<% if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
				<tr bgcolor=ffffff>
					<% for (int i = 0; i < 7; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { 
					      if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
						     <td align="right"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-1"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					      <% } else { %>
							 <td align="right"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-2" color="#999999"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>						  
						  <% } %>  
<!-- <td align=right bgcolor=ffffcc><a href="#" onClick="setOpenerDate(3,28,2000)"><font face="Arial,Helvetica,sans-serif" size="-1" color="Black">28</font></a></td> -->
					<% } %>
				</tr>
				<% } %>
				<% } %>				
				</table>
			</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td align="center" nowrap>
		<font face="Arial,Helvetica,sans-serif" size="-1">
<select name="month" onChange="reopenDateSelector()">
<% for (int i = 0; i < 12; i++) { %>
<option value="<%= i %>" <% if (i == requestMonth) {%>selected<% } %>><%= MONTHNAMES[i] %></option>
<% } %>
</select>

<select name="year" onChange="reopenDateSelector()">
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
</font>
</td>
</tr>
</table>
</form>
</body>
</html>
