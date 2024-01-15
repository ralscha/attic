<%@ page language="java" import="java.util.*, java.text.*" session="false"%>
<%!
	private final static Locale local = new Locale("fr", "CH");
	//private final static Locale local = Locale.UK;
	private final static DateFormatSymbols symbols = new DateFormatSymbols(local); 
	private final static String[] MONTHNAMES = symbols.getMonths();
	private final static String[] WEEKDAYS   = symbols.getShortWeekdays();	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static final DateFormat parseDateFormat = new ch.ess.util.SimpleDateFormat2k("dd.MM.yy");

    

	
	int firstDayOfWeek;
	int requestMonth, nextMonth, prevMonth;
	int requestYear, nextYear, prevYear;
	String requestMonthString;
	String requestYearString;

	static {	
    for (int i = 0; i < WEEKDAYS.length; i++) {
		if (WEEKDAYS[i].length() > 2)
			WEEKDAYS[i] = WEEKDAYS[i].substring(0,2);
	}
	}
%>

<%
    parseDateFormat.setLenient(false);
	Calendar today = Calendar.getInstance(local);	
	firstDayOfWeek = today.getFirstDayOfWeek();

	String requestDateString = request.getParameter("date");
	
	requestMonthString = request.getParameter("m");
	requestYearString = request.getParameter("y");
	
	if (requestDateString != null) {
	   try {
	     Date temp = parseDateFormat.parse(requestDateString);
		 Calendar tempCal = new GregorianCalendar();
		 tempCal.setTime(temp);
	     requestMonth = tempCal.get(Calendar.MONTH);		
		 requestYear = tempCal.get(Calendar.YEAR);
	   } catch (ParseException pe) {
	     requestMonth = today.get(Calendar.MONTH);		
		 requestYear = today.get(Calendar.YEAR);
	   }
	} else {
	if (requestMonthString != null) {
		requestMonth = Integer.parseInt(requestMonthString);
	} else {
		requestMonth = today.get(Calendar.MONTH);		
	}
	
	if (requestYearString != null) {
		requestYear = Integer.parseInt(requestYearString);
	} else {
		requestYear = today.get(Calendar.YEAR);
		}
	}
	
		requestYearString = String.valueOf(requestYear);
	
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
<head><title>Date Selector</title><meta http-equiv="pragma" content="no-cache"></head>
<body vlink=blue link=blue bgcolor=#FFFFFF>
<form action="calendar.jsp" method="post" name="dateselector" id="dateselector">
<script language="JavaScript" type="text/javascript">
<!-- 
function setOpenerDate(d, m, y)
{
	var i;
	var len;

	var dte = "";
	if (d < 10) {
		dte = "0";
	}
	dte = dte + d + ".";
	m = m + 1;
	if (m < 10) {
		dte = dte + "0";
	}

	dte = dte + m + ".";
	dte = dte + y;
	
	window.opener.document.<%=request.getParameter("form")%>.<%=request.getParameter("elem")%>.value = dte;
	
	window.close();
}

function reopenDateSelector ()
{
	var yri = document.dateselector.year.selectedIndex;
	window.document.location.href = 'calendar.jsp?form=<%=request.getParameter("form")%>&elem=<%=request.getParameter("elem")%>&m=' + document.dateselector.month.selectedIndex + '&y=' + 
		document.dateselector.year.options[yri].value ;
}
// -->
</script>
<table cellpadding=0 cellspacing=0 border=0 width="100%">
<tr>
	<td align=center>
		<table cellpadding=0 cellspacing=0 border=0 width=165>
		<tr bgcolor=#CCCCCC>
			<td align=center>
				<table cellpadding=3 cellspacing=1 border=0 width=170>				
				<tr bgcolor=#A0B8C8>
					<td align=center nowrap colspan=7>
						<a href="calendar.jsp?form=<%=request.getParameter("form")%>&elem=<%=request.getParameter("elem")%>&m=<%= prevMonth %>&y=<%= prevYear %>"><img src="images/left.gif" width="11" height="11" border="0" alt=""></a>
						<font size=-1 face="Arial,Helvetica">
						<b><%= MONTHNAMES[requestMonth] %> <%= requestYearString %></b>
						</font>
						<a href="calendar.jsp?form=<%=request.getParameter("form")%>&elem=<%=request.getParameter("elem")%>&m=<%= nextMonth %>&y=<%= nextYear %>"><img src="images/right.gif" width="11" height="11" border="0" alt=""></A>
					</td>
				</tr>
				<tr bgcolor=#EEEEEE>					
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
				<tr>
				    <% for (int i = 0; i < firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { %>
					<% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                      (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										  (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#FFFFFF"; %> 
					<td align="right" bgcolor="<%= bgcolor %>"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-2" color="#999999"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					<% } 
					   for (int i = 0; i < 7-firstDay; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { %>
					<% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                      (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										  (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#F4F4F4"; %> 

					<td align="right" bgcolor="<%= bgcolor %>"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-1"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					<% } %>
				</tr>

				<% for (int j = 0; j < 5; j++) { %>
				<% if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
				<tr>
					<% for (int i = 0; i < 7; i++, tmpCalendar.add(Calendar.DAY_OF_MONTH, 1)) { 
					      if (requestMonth == tmpCalendar.get(Calendar.MONTH)) { %>
					     <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                           (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										       (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#F4F4F4"; %> 						  
						     <td align="right" bgcolor="<%= bgcolor %>"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-1"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>
					   <% } else { %>
   					     <% String bgcolor = ( (today.get(Calendar.DATE) == tmpCalendar.get(Calendar.DATE)) &&
					                           (today.get(Calendar.MONTH) == tmpCalendar.get(Calendar.MONTH)) &&
										       (today.get(Calendar.YEAR) == tmpCalendar.get(Calendar.YEAR)) ) ? "Lime" : "#FFFFFF"; %> 						  
							 <td align="right" bgcolor="<%= bgcolor %>"><a href="#" onClick="setOpenerDate(<%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%>,<%= tmpCalendar.get(Calendar.MONTH)%>,<%= tmpCalendar.get(Calendar.YEAR)%>)"><font face="Arial,Helvetica,sans-serif" size="-2" color="#999999"><%= tmpCalendar.get(Calendar.DAY_OF_MONTH)%></font></a></td>						  
						  <% } %>  

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
<select name="month" onchange="reopenDateSelector()">
<% for (int i = 0; i < 12; i++) { %>
<option value="<%= i %>" <% if (i == requestMonth) {%>selected<% } %>><%= MONTHNAMES[i] %></option>
<% } %>
</select>

<select name="year" onchange="reopenDateSelector()">
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
<tr>
<td align="center"><font face="Arial,Helvetica,sans-serif" size="-1"><a href="#" onClick="setOpenerDate(<%= today.get(Calendar.DAY_OF_MONTH)%>,<%= today.get(Calendar.MONTH)%>,<%= today.get(Calendar.YEAR)%>)">Today</a>: <%= dateFormat.format(today.getTime()) %></font></td>
</tr>
</table>

</form>
</body>
</html>
