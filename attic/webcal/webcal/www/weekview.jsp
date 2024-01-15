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

<jsp:useBean id="weekrequest" scope="request" class="ch.ess.calendar.WeekViewRequest">
<jsp:setProperty name="weekrequest" property="*"/>
<% weekrequest.setAppCache(appcache, login.isLogonOK(), login.getUserid()); %>
<% weekrequest.init(); %>
</jsp:useBean>


<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<title>ESS Web Calendar: Week View</title>	
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

<p class="titlesmall">Week <%= weekrequest.getWeekno() %>:&nbsp;<%= weekrequest.getFormatedDate(0) %>&nbsp;-&nbsp;<%= weekrequest.getFormatedDate(6) %>&nbsp;(<%= weekrequest.getNameString() %>)</p>


<table><tr><td align="left" valign="top">
<table width="500" cellspacing="2" cellpadding="0">


<% for (int i = 0; i < 7; i++) { %>

<tr>
	    <% if (weekrequest.showDelete()) { %>
   		  <td align="left" class="textb"><a href="d.jsp?onedate=<%= weekrequest.getFormatedDate(i) %>" class="textb"><%= weekrequest.getFormatedDate(i) %></a>&nbsp;<%= weekrequest.getWeekday(i) %></td>	
		<% } else { %>
          <td align="left" class="textb"><%= weekrequest.getFormatedDate(i) %>&nbsp;<%= weekrequest.getWeekday(i) %></td>	
		<% } %>
</tr>
<tr>
	   <%
	   List allDayList = weekrequest.getAlldayAppointments(i);
	   List timedList  = weekrequest.getTimedAppointments(i); %>
    
		<% if ( ((allDayList != null) && !allDayList.isEmpty()) || 
		        ((timedList != null) && !timedList.isEmpty())) { %>
	   <td align="left" valign="top" bgcolor="#E6E6E6">
	    <table>
			<% if ((allDayList != null) && (!allDayList.isEmpty())) { 
			    for (int j = 0; j < allDayList.size(); j++) { 
				   Appointments app = (Appointments)allDayList.get(j);
				%>
		       <tr><td>			
				   <% if (weekrequest.showDelete() || login.isAdmin()) { %>				   
			       <a href="d.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject() %></a>
				   <% } else { %>
				   <%= app.getSubject() %>
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
	              <% if (weekrequest.showDelete() || login.isAdmin()) { %>
		            &nbsp;<a href="weekview.jsp?userid=<%= request.getParameter("userid") %>&day=<%= request.getParameter("day") %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
	              <% } %>
			</td></tr>	
			 <% } %> 
			<% } %>

			
			<% if ((timedList != null) && (!timedList.isEmpty())) { 
			    for (int j = 0; j < timedList.size(); j++) {
				   Appointments app = (Appointments)timedList.get(j);
				%>
				<tr><td>
				   <% if (weekrequest.showDelete() || login.isAdmin()) { %>
			       <%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %> <a href="d.jsp?appid=<%= app.getAppointmentid() %>"><%= app.getSubject()%></a>
				   <% } else { %>
				   <%= app.getFormatedStartTime() %>&nbsp;-&nbsp;<%= app.getFormatedEndTime() %> <%= app.getSubject()%>
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
	               <% if (weekrequest.showDelete() || login.isAdmin()) { %>
		             &nbsp;<a href="weekview.jsp?userid=<%= request.getParameter("userid") %>&day=<%= request.getParameter("day") %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>&delete=<%= app.getAppointmentid() %>"><img src="images/del.gif" width="12" height="12" border="0" alt="delete"></a>
	               <% } %>
				   </td></tr>
			 <% } %> 
			<% } %>
		</table>
	</td>
	<% } else { %>
	  <td align="left" valign="top" bgcolor="#FBFBFB">&nbsp;</td>
	 <% } %>

</tr>
<tr>
<td align="left" bgcolor="White">&nbsp;</td>
</tr>

<% } %>
</table>
</td>
<td>&nbsp;</td>
<td align="left" valign="top">
<jsp:include page="cal.jsp" flush="true">
  <jsp:param name="link" value="weekview.jsp"/>
</jsp:include>
<p>
<table>
<% 
   String day = request.getParameter("day");
   if (day == null)
     day = weekrequest.getDay();
%>
<tr><td><a href="dv.jsp?userid=<%= request.getParameter("userid") %>&day=<%= day %>&month=<%=request.getParameter("month")%>&year=<%=request.getParameter("year")%>">Day View</a></td></tr>
<tr><td bgcolor="#D0D0D0">Week View</td></tr>
</table>
</td>

</tr></table>

</body>
</html>
