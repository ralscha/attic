<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Summary" errorPage="error.jsp"%>

<% 
    SQLManager manager = SQLManager.getInstance();
    Connection conn = manager.requestConnection();
%>

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>
<jsp:setProperty name="login" property="*"/>

<jsp:useBean id="summary" scope="request" class="ch.ess.calendar.Summary">
<jsp:setProperty name="summary" property="*"/>
<% summary.setConnection(conn); %>
</jsp:useBean>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>
<% appcache.setConnection(conn); %>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.setConnection(conn); %>
</jsp:useBean>

<%
   session.setAttribute("frompath", request.getServletPath()+"?month="+summary.getMonth()+"&year="+summary.getYear());
%>

<HTML>
<HEAD>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="REFRESH" content="120; url=summary.jsp">
<title>ESS Web Calendar</title>
<link rel="STYLESHEET" type="text/css" href="planner.css">
</HEAD>

<BODY>


<%@ include file="menu.jsp"%>


<%
  try {
%>

<p class="titlesmall"><%= summary.getRequestMonthName() %>&nbsp;<%= summary.getYear() %></p>

<br>

<table border="1">
<tr>
<td>&nbsp;</td>
<%
  for (int i = 1; i <= summary.getLastDay(); i++) {
  	Calendar tmp = summary.getCalendar(i);
  	if (summary.isToday(tmp)) { %>          	  
      <td bgcolor="Lime" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>
	<% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
	         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
	  <td bgcolor="Silver" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>
	<% } else {%>
	  <td class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>    
	<%} 
  }
%>

</tr>
<tr>
<th>&nbsp;</th>

<% if (login.isLogonOK()) {
	  for (int i = 1; i <= summary.getLastDay(); i++) {
  		Calendar tmp = summary.getCalendar(i);
	  	if (summary.isToday(tmp)) { %>          	  
    	  <th bgcolor="Lime"><a href="appointmentdetail.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></th>
		<% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
		         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
		  <th bgcolor="Silver"><a href="appointmentdetail.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></th>
		<% } else {%>
		  <th><a href="appointmentdetail.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></th>    
		<% }
     }
   } else {
	  for (int i = 1; i <= summary.getLastDay(); i++) {
  		Calendar tmp = summary.getCalendar(i);
	  	if (summary.isToday(tmp)) { %>          	  
    	  <th bgcolor="Lime"><%= i %></th>
		<% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
		         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
		  <th bgcolor="Silver"><%= i %></th>
		<% } else {%>
		  <th><%= i %></th>    
		<% }
     }   
   }
%>

</tr>

<% 
	Iterator it = summary.getUserList().iterator();  
		while (it.hasNext()) {
          Users user = (Users)it.next();
 %>
 <tr>
   <td><%= user.getFirstname() %>&nbsp;<%= user.getName() %></td>
   <% 
          for (int i = 1; i <= summary.getLastDay(); i++) {
   			List appList;
			if (login.isLogonOK()) {
				appList = appcache.getUserAppointments(user.getUserid(), summary.getCalendar(i), login.getUserid());
			} else {
			    appList = appcache.getUserAppointments(user.getUserid(), summary.getCalendar(i));
			}
			String descriptiontext = "";
			String gif = "";
			if ((appList == null) || appList.isEmpty()) { 
	%>
				<td align="center" valign="middle"><img src="images/full/FFFFFF.gif" width="20" height="20" border="0" alt=""></td>
	<%
	        } else if (appList.size() == 1) {
			   Appointments app = (Appointments)appList.get(0);
			   if (app.isAlldayevent()) {
				  descriptiontext = app.getSubject();
				  gif = "images/full/" + categoriesMap.getColor(app.getCategoryid()) + ".gif";
			   } else {
			   	  descriptiontext = app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();				  
				  gif = "images/part/" + categoriesMap.getColor(app.getCategoryid()) + ".gif";
			   }
	%>
			   <td align="center" valign="middle"><A HREF="dayview.jsp?userid=<%= user.getUserid()%>&date=<%= summary.getCalendar(i).getTime().getTime() %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= gif %>" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	<%  
	        } else {
				gif = "images/full/000000.gif";
				for (int n = 0; n < appList.size(); n++) {
					Appointments app = (Appointments)appList.get(n);
					if (app.isAlldayevent()) {
						descriptiontext += app.getSubject();	
					} else {
						descriptiontext += app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();
					}
					if (n+1 < appList.size()) {
						descriptiontext += "; ";
					}
				}
	 %>
			   <td align="center" valign="middle"><A HREF="dayview.jsp?userid=<%= user.getUserid()%>&date=<%= summary.getCalendar(i).getTime().getTime() %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= gif %>" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	 <%			
			}
		  } /* for getLastDay */
   %>   
 </tr>
 

<% } /* while users */ %>
</table>

<p>
<form action="summary.jsp" method="post">
<table>
<tr>

<td><a href="summary.jsp?month=<%= summary.getPrevMonth() %>&year=<%= summary.getPrevYear() %>" class="text">&lt; Previous Month</a></td>
<td>&nbsp;</td>
<td><a href="summary.jsp?month=<%= summary.getNextMonth() %>&year=<%= summary.getNextYear() %>" class="text">Next Month &gt;</a></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td class="text">Month: <select name="month" size="1">
    <% for (int i = 0; i < 12; i++) {
       if (i == summary.getMonth()) { %>
      <option value="<%= i %>" selected><%= summary.getMonthName(i) %></option>
    <% } else { %>
      <option value="<%= i %>"><%= summary.getMonthName(i) %></option>
    <% } } %>   	
	</select></td>
<td class="text">Year: <select name="year">
    <% int cyear = summary.getTodayYear();
	   for (int i = 0; i < 5; i++) {
       if (summary.getYear() == (cyear + i)) { %>
	   <option value="<%= cyear + i %>" selected><%= cyear + i %></option>
	<% } else { %>
	   <option value="<%= cyear + i %>"><%= cyear + i %></option>
	<% } } %>
	
	</select></td>	
<td><input class="text" type="submit" value="Show"></td>	
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><a href="summary.jsp">Current Month</a></td>
</tr>
</table>
</form>
<hr>

<table>

<% 
Integer[] keys = categoriesMap.getKeys();
int count = keys.length;
for (int num = 0; num < count; num += 6) { %>
  <tr>      
  <% 
   for (int num2 = 0; num2 <= 5; num2++) {
     if (num+num2 < count) {%>
    <td><img src="images/full/<%= categoriesMap.getColor(keys[num+num2]) %>.gif" width="20" height="20" border="0" alt="<%= categoriesMap.getDescription(keys[num+num2]) %>"></td>
    <td><%= categoriesMap.getDescription(keys[num+num2]) %></td>
    <td>&nbsp;</td>
  <% } else { %>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  <% }
   } %>  
  </tr>  
<% } %>
</table>

</body>

<% 
} finally {
	manager.returnConnection(conn);	
} 
%>
</HTML>