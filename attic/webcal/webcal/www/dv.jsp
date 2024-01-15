<%@ page language="java" import="java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Dayview" errorPage="error.jsp"%>
<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>
<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>


<%
  session.setAttribute("frompath", request.getServletPath()+"?userid="+request.getParameter("userid")+"&day="+request.getParameter("day")+"&month="+request.getParameter("month")+"&year="+request.getParameter("year")+"&update=true");
%>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.init(); %>
</jsp:useBean>

<%	
    if (login.isLogonOK()) {
	    AppointmentsTable at = new AppointmentsTable();
	  	String deleteappid = request.getParameter("delete");
 		if (deleteappid != null) {
	      at.delete("appointmentid = "+deleteappid);
		  appcache.refresh(request.getParameter("userid"), request);
  		}
		String update = request.getParameter("update");
		if (update != null) {
			appcache.refresh(request.getParameter("userid"), request);
		}
		
	}
	
%>

<jsp:useBean id="dayrequest" scope="request" class="ch.ess.calendar.DayViewRequest">
<jsp:setProperty name="dayrequest" property="*"/>
<% dayrequest.setAppCache(appcache, login.isLogonOK(), login.getUserid()); %>
<% dayrequest.init(); %>
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
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<%@ include file="menu.jsp"%>

<p class="titlesmall"><%= dayrequest.getWeekday() %>, <%= dayrequest.getFormatedDate() %>
&nbsp;(<%= dayrequest.getNameString() %>)</p>
<table><tr><td align="left" valign="top">

<table width="500" cellspacing="2" cellpadding="0">

<% if (dayrequest.hasAlldayappointments()) { %>
<tr bgcolor="#E6E6E6">
<% } else { %>
<tr bgcolor="#FBFBFB">
<% } %>
	<% if (dayrequest.showDelete()) { %>
    <td width="55" align="center" valign="top" bgcolor="#D0D0D0"><a href="d.jsp?onedate=<%= dayrequest.getFormatedDate() %>" class="textb">All Day</a></td>	
    <% } else { %>
	<td width="55" align="center" valign="top" bgcolor="#D0D0D0" class="textb">All Day</td>
	<% } %>
	
	<td width="443" align="left" valign="top" <% if (dayrequest.getMaxCol() > 1) { %>colspan="<%= dayrequest.getMaxCol()%>"<% } %>>

<table>
<% if (dayrequest.hasAlldayappointments()) { %>
<% Iterator it = dayrequest.getAlldayappointments().iterator();
   while (it.hasNext()) {  
	   Appointments app = (Appointments)it.next();
%>    
<tr bgcolor="#E6E6E6">
  <td>
       <% if (dayrequest.showDelete() || login.isAdmin()) { %>
  	    <% if (!app.getFormatedStartDate().equals(app.getFormatedEndDate())) { %>
    	 <a href="d.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject() + " ("+app.getFormatedStartDate()+"-"+app.getFormatedEndDate()+")" %></a>
	    <% } else { %>
 	     <a href="d.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject() %></a>
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
		&nbsp;<a href="dv.jsp?userid=<%= request.getParameter("userid") %>&day=<%= request.getParameter("day") %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
	   <% } %>
  </td>
</tr>
<% } %>
<% } else { %>
<tr bgcolor="#FBFBFB"><td>&nbsp;</td></tr>
<% } %>
</table>
	</td>
</tr>
<tr>
    <% if (dayrequest.getMaxCol() > 1) { %>
	<td colspan="<%= 1+dayrequest.getMaxCol()%>">&nbsp;</td>
	<% } else { %>
	<td colspan="2">&nbsp;</td>
	<% } %>
</tr>
<% for (int i = 0; i < dayrequest.getAppArraySize(); i++) {
      List appList = dayrequest.getAppArray(i);
%>
<tr bgcolor="#FBFBFB">
	<% String timestr;
	   if (i < 10) 
	      timestr = "0" + i + ":00";
	   else
	      timestr = i + ":00";
	%>
	<% if (dayrequest.showDelete()) { %>
	<td width="55" align="center" bgcolor="#D0D0D0"><a href="d.jsp?onedate=<%= dayrequest.getFormatedDate() %>&starttime=<%= timestr %>&event=<%= ch.ess.calendar.AppointmentRequest.TIMED_EVENT%>" class="textb"><%= timestr %></a></td>
	<% } else { %>
	<td width="55" align="center" bgcolor="#D0D0D0" class="textb"><%= timestr %></td>
	<% } %>
	<% if (dayrequest.hasTimedappointments()) { %>
	<% if (appList.isEmpty()) { %>
	      <% if (dayrequest.getMaxCol()-dayrequest.getAffected(i) > 0) { %>
	      	  <td <% if (dayrequest.getMaxCol()-dayrequest.getAffected(i) > 1) { %>colspan="<%= dayrequest.getMaxCol()-dayrequest.getAffected(i) %>"<%}%>>&nbsp;</td>
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
				<td bgcolor="#E6E6E6" rowspan="<%= endhour-starthour %>" align="left"><%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %>&nbsp;<a href="d.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject()%></a>
				<% } else { %>
                <td bgcolor="#E6E6E6" rowspan="<%= endhour-starthour %>" align="left"><%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %>&nbsp;<%= app.getSubject()%>				
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
						&nbsp;<a href="dv.jsp?userid=<%= request.getParameter("userid") %>&day=<%= request.getParameter("day") %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
					   <% } %>	
				</td>
				
		  <% } // for
		     if (appList.size() < dayrequest.getMaxCol()) {
			    if ((dayrequest.getMaxCol()-appList.size()-dayrequest.getAffected(i)) > 0) {
		  %>
		        <td <% if (dayrequest.getMaxCol()-appList.size()-dayrequest.getAffected(i) > 1) { %>colspan="<%= dayrequest.getMaxCol()-appList.size()-dayrequest.getAffected(i) %>"<%}%>>&nbsp;</td>
		  <%	} 	
			 } 
		  %>	
		  
	<% } %>
	<% } else { %>
	  <td>&nbsp;</td>
	<% } %>
	
</tr>

<% } /* for */%>

</table>

</td>
<td>&nbsp;</td>
<td align="left" valign="top">
<jsp:include page="cal.jsp" flush="true">
  <jsp:param name="link" value="dv.jsp"/>
</jsp:include>
<p>
<table>
<tr><td bgcolor="#D0D0D0">Day View</td></tr>
<% 
   String day = request.getParameter("day");
   if (day == null)
     day = dayrequest.getDay();
%>
<tr><td><a href="weekview.jsp?userid=<%= request.getParameter("userid") %>&day=<%= day %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>">Week View</a></td></tr>
</table>
</td>

</tr></table>

</body>
</html>
