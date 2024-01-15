<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Dayview" errorPage="error.jsp"%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>


<%
  session.setAttribute("frompath", request.getServletPath()+"?userid="+request.getParameter("userid")+"&date="+request.getParameter("date")+"&update=true");
%>

<%
    SQLManager manager = SQLManager.getInstance();
    Connection conn = manager.requestConnection();
	try {
    if (login.isLogonOK()) {
	    AppointmentsTable at = new AppointmentsTable(conn);
	  	String deleteappid = request.getParameter("delete");
 		if (deleteappid != null) {
	      at.delete("appointmentid = "+deleteappid);
		  appcache.setConnection(conn); 
  		}
		String update = request.getParameter("update");
		if (update != null) {
			appcache.setConnection(conn);
		}
		
	}
	} finally {
		manager.returnConnection(conn);	
	} 
%>

<jsp:useBean id="dayrequest" scope="request" class="ch.ess.calendar.DayViewRequest">
<jsp:setProperty name="dayrequest" property="*"/>
<% dayrequest.setAppCache(appcache, login.isLogonOK(), login.getUserid()); %>
<% dayrequest.setConnection(conn); %>
</jsp:useBean>


<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<title>ESS Web Calendar: Day View</title>	
	<link rel="STYLESHEET" type="text/css" href="planner.css">
<script language="JavaScript" type="text/javascript">
 <!--
   function popup(lnk){
   newwin = window.open(lnk,'shownotes','width=300,height=100,scrollbar=yes,toolbar=no');
 } 
 //-->
 </script>
</head>
<body>

<%@ include file="menu.jsp"%>
<p class="titlesmall"><%= dayrequest.getWeekday() %>, <%= dayrequest.getFormatedDate() %>
&nbsp;(<%= dayrequest.getNameString() %>)</p>
<% if (dayrequest.hasAlldayappointments()) { %>
<table>
<tr>
	<th align="left">All Day</th>		
</tr>
</table>
<table border="1">
<% Iterator it = dayrequest.getAlldayappointments().iterator();
   while (it.hasNext()) {  
	   Appointments app = (Appointments)it.next();
%>    
<tr>
  <td>
       <% if (dayrequest.showDelete() || login.isAdmin()) { %>
  	    <% if (!app.getFormatedStartDate().equals(app.getFormatedEndDate())) { %>
    	 <a href="appointmentdetail.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject() + " ("+app.getFormatedStartDate()+"-"+app.getFormatedEndDate()+")" %></a>
	    <% } else { %>
 	     <a href="appointmentdetail.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject() %></a>
	    <% } %>
       <% } else { %>
	    <% if (!app.getFormatedStartDate().equals(app.getFormatedEndDate())) { %>
    	 <%= app.getSubject() + " ("+app.getFormatedStartDate()+"-"+app.getFormatedEndDate()+")" %>
	    <% } else { %>
 	     <%= app.getSubject() %>
	    <% } %>
	   <% } %>
	   
	   <% if (app.isPrivate()) { %>
	     &nbsp;<img src="images/private.gif" width="11" height="10" border="0" alt="private">
	   <% } %>
	   <% if (app.hasReminders()) { %>
	    &nbsp;<img src="images/reminder.gif" width="20" height="12" border="0" alt="reminder">
	   <% } %>
	   <% if (app.getBody() != null) { %>
		&nbsp;<a href="javascript:popup('shownotes.jsp?appid=<%= app.getAppointmentid() %>')"><img src="images/edit.gif" width="12" height="12" border="0" alt="notes"></a>
	   <% } %>
	   <% if (app.hasRepeaters()) { %>
		&nbsp;<img src="images/repeat.gif" width="13" height="12" border="0" alt="<%= ((Repeaters)app.getRepeaters().get(0)).getDescription()%>">
	   <% } %>
	   <% if (dayrequest.showDelete() || login.isAdmin()) { %>
		&nbsp;<a href="dayview.jsp?userid=<%= request.getParameter("userid") %>&date=<%= request.getParameter("date") %>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
	   <% } %>
  </td>
</tr>
<% } %>
</table>
<% } // if hasAlldayappointments %>
<p>
<table width="500" border="1">

<% for (int i = 0; i < dayrequest.getAppArraySize(); i++) {
      List appList = dayrequest.getAppArray(i);
%>
<tr>
    <% String timestr;
	   if (i < 10) 
	      timestr = "0" + i + ":00";
	   else
	      timestr = i + ":00";
	%>
	<% if (dayrequest.showDelete()) { %>
	<td width="50" align="center"><a href="appointmentdetail.jsp?onedate=<%= dayrequest.getFormatedDate() %>&starttime=<%= timestr %>&event=<%= ch.ess.calendar.AppointmentRequest.TIMED_EVENT%>"><%= timestr %></a></td>
	<% } else { %>
	<td width="50" align="center"><%= timestr %></td>
	<% } %>
	<% if (dayrequest.hasTimedappointments()) { %>
	<% if (appList.isEmpty()) { %>
	      <% if (dayrequest.getMaxCol()-dayrequest.getAffected(i) > 0) { %>
	      	  <td colspan="<%= dayrequest.getMaxCol()-dayrequest.getAffected(i) %>">&nbsp;</td>
		  <% } %>
	<% } else { %>
	      <% 
		     for (int j = 0; j < appList.size(); j++) { 
		        Appointments app = (Appointments)appList.get(j); 
				int starthour = app.getStartCalendar().get(Calendar.HOUR_OF_DAY);
		  
		 		Calendar endCal = app.getEndCalendar();
				int endhour = endCal.get(Calendar.HOUR_OF_DAY);
		        int endminute = endCal.get(Calendar.MINUTE);
		        if (endminute > 0) 
		  	      endhour++;
				  
				   
		  %>
		        <% if (dayrequest.showDelete() || login.isAdmin()) { %>
				<td rowspan="<%= endhour-starthour %>" align="left"><%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %>&nbsp;<a href="appointmentdetail.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject()%></a>
				<% } else { %>
                <td rowspan="<%= endhour-starthour %>" align="left"><%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %>&nbsp;<%= app.getSubject()%>				
				<% } %>
					   <% if (app.isPrivate()) { %>
					     &nbsp;<img src="images/private.gif" width="11" height="10" border="0" alt="private">
	   				   <% } %>
					   <% if (app.hasReminders()) { %>
					    &nbsp;<img src="images/reminder.gif" width="20" height="12" border="0" alt="reminder">
					   <% } %>
					   <% if (app.getBody() != null) { %>
						&nbsp;<a href="javascript:popup('shownotes.jsp?appid=<%= app.getAppointmentid() %>')"><img src="images/edit.gif" width="12" height="12" border="0" alt="notes"></a>
					   <% } %>
					   <% if (app.hasRepeaters()) { %>
						&nbsp;<img src="images/repeat.gif" width="13" height="12" border="0" alt="<%= ((Repeaters)app.getRepeaters().get(0)).getDescription()%>">
					   <% } %>
					   <% if (dayrequest.showDelete() || login.isAdmin()) { %>
						&nbsp;<a href="dayview.jsp?userid=<%= request.getParameter("userid") %>&date=<%= request.getParameter("date") %>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
					   <% } %>	
				</td>
				
		  <% } // for
		     if (appList.size() < dayrequest.getMaxCol()) {
			    if ((dayrequest.getMaxCol()-appList.size()-dayrequest.getAffected(i)) > 0) {
		  %>
		        <td colspan="<%= dayrequest.getMaxCol()-appList.size()-dayrequest.getAffected(i) %>">&nbsp;</td>
		  <%	} 	
			 } 
		  %>	
		  
	<% } %>
	<% } else { %>
	  <td width="450">&nbsp;</td>
	<% } %>
	
</tr>

<% } /* for */%>

</table>


</body>
</html>
