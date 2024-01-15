<%@ page language="java" import="java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*,ch.ess.calendar.session.*,cmp.holidays.*" info="Summary" errorPage="error.jsp"%>
<HTML><HEAD><meta http-equiv="pragma" content="no-cache"><title>ESS Web Calendar</title>
<link rel="STYLESHEET" type="text/css" href="planner.css"></HEAD>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<jsp:useBean id="login" scope="session" class="ch.ess.calendar.session.Login"/>
<jsp:setProperty name="login" property="*"/>
<%@ include file="menu.jsp"%>
<p>

<jsp:useBean id="summary" scope="request" class="ch.ess.calendar.Summary">
<jsp:setProperty name="summary" property="*"/>
<% summary.init(); %>
</jsp:useBean>

<%
   session.setAttribute("frompath", request.getServletPath()+"?month="+summary.getMonth()+"&year="+summary.getYear());
%>

<jsp:useBean id="categoriesMap" scope="application" class="ch.ess.calendar.CategoriesMap">
<% categoriesMap.init(); %>
</jsp:useBean>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>
<% appcache.init(categoriesMap); %>


<table border="1" cellspacing="0" cellpadding="1">
<tr>
<td rowspan="2" bgcolor="#F0F0F0"><p class="summarytitle"><%= summary.getRequestMonthName() %>&nbsp;<%= summary.getYear() %></p></td>
<%
  Map holidayMap = ch.ess.calendar.util.Holidays.getHolidayMap(summary.getYear());
  
  for (int i = 1; i <= summary.getLastDay(); i++) {
  	Calendar tmp = summary.getCalendar(i);
  	if (summary.isToday(tmp)) { %>          	  
      <td align="center" bgcolor="Lime" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>
 <% } else if ( holidayMap.get(tmp) != null) { %>
	  <td align="center" bgcolor="#FCDC5F" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td> 
 <% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
	         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
	  <td align="center" bgcolor="#CFE7E7" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>
<%  } else {%>
	  <td align="center" class="textsmall"><%= ch.ess.calendar.util.Constants.SHORT_WEEKDAYS[tmp.get(Calendar.DAY_OF_WEEK)] %></td>    
<%  } 
  }
%>

</tr>
<tr>


<% if (login.isLogonOK()) {
	  for (int i = 1; i <= summary.getLastDay(); i++) {
  		Calendar tmp = summary.getCalendar(i);
	  	if (summary.isToday(tmp)) { %>          	  
    	  <td align="center" bgcolor="Lime"><a href="d.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></td>
	 <% } else if ( holidayMap.get(tmp) != null) { %>
		  <td align="center" bgcolor="#FCDC5F"><a href="d.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></td>	
	 <% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
		         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
		  <td align="center" bgcolor="#CFE7E7"><a href="d.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></td>	
	 <% } else {%>
		  <td align="center"><a href="d.jsp?onedate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&startdate=<%= ch.ess.calendar.util.Constants.dateFormat.format(tmp.getTime()) %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><%= i %></a></td>    
	 <% }
     }
   } else {
		for (int i = 1; i <= summary.getLastDay(); i++) {
  		Calendar tmp = summary.getCalendar(i);
	  	if (summary.isToday(tmp)) { %>          	  
    	  <th bgcolor="Lime"><%= i %></th>
	    <% } else if ( holidayMap.get(tmp) != null) { %>
		  <td align="center" bgcolor="#FCDC5F"><%= i %></td>		
		<% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
		         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
		  <th bgcolor="#CFE7E7"><%= i %></th>
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
   <% if (login.isLogonOK() && user.getUserid().equals(login.getUserid())) { %>
   <th align="left"><%= user.getFirstname() %>&nbsp;<%= user.getName() %></th>
   <% } else { %>
   <td><%= user.getFirstname() %>&nbsp;<%= user.getName() %></td>
   <% } %>
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
				<td><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="w.gif" width="20" height="20" border="0"></a></td>
	<%		} else if ( (appList.size() == 1) && ((Appointments)appList.get(0)).isAlldayevent() ) {
	            Appointments app = (Appointments)appList.get(0);
				descriptiontext = app.getSubject();
				gif = "images/full/" + categoriesMap.getColor(app.getCategoryid()) + ".gif";
	%>
			   <td><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= gif %>" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	<%  
	        } else {
			    boolean hasAllday = false;
				Iterator ita = appList.iterator();
				while(ita.hasNext()) {
					Appointments app = (Appointments)ita.next();
					if (app.isAlldayevent()) {
						hasAllday = true;
						break;
					}
				}
				
				if (hasAllday) {				
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
			     <td><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= gif %>" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	 <%		   } else { /* if hasAllday */
	             Multi[] multi = appcache.compute(appList, categoriesMap);
	 %>        <td>
			   <table border="0" cellspacing="1" cellpadding="0">
			        <% if (multi[0].gif.indexOf("000000") == -1) { %>
					<tr>
						<td valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= multi[0].gif %>" width="9" height="9" border="0" alt="<%= multi[0].description %>"></a></td>
						<td valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= multi[1].gif %>" width="9" height="9" border="0" alt="<%= multi[1].description %>"></a></td>
					</tr>
					<% } else { %>
					<tr>
						<td colspan="2" valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="images/small/half.gif" width="19" height="9" border="0" alt="<%=multi[0].description %>"></a></td>
					</tr>					
					<% } %>
					<% if (multi[2].gif.indexOf("000000") == -1) { %>
					<tr>
						<td valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= multi[2].gif %>" width="9" height="9" border="0" alt="<%= multi[2].description %>"></a></td>
						<td valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="<%= multi[3].gif %>" width="9" height="9" border="0" alt="<%= multi[3].description %>"></a></td>
					</tr>
					<% } else { %>
					<tr>
						<td colspan="2" valign="middle"><A HREF="dv.jsp?userid=<%= user.getUserid()%>&day=<%= i %>&month=<%=summary.getMonth()%>&year=<%= summary.getYear()%>"><img src="images/small/half.gif" width="19" height="9" border="0" alt="<%=multi[2].description %>"></a></td>
					</tr>															
					<% } %>
				</table>	 
				</td>
	 <%
	 
	           } /* if hasAllday */
			} /* size of appList */
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
<hr size="1">
<table>

<% 
Integer[] keys = categoriesMap.getKeys();
int count = keys.length;
for (int num = 0; num < count; num += 7) { %>
  <tr>      
  <% 
   for (int num2 = 0; num2 <= 6; num2++) {
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
<hr size="1">
<% Map holidayMonthMap = ch.ess.calendar.util.Holidays.getHolidayMap(summary.getYear(), summary.getMonth()); %>
<% if (!holidayMonthMap.isEmpty()) { %>

<% 
    StringBuffer sb = new StringBuffer();
    Iterator itm = holidayMonthMap.keySet().iterator();
    while(itm.hasNext()) {
	   String cal = (String)itm.next();
	   sb.append(cal);
	   sb.append("&nbsp;");
	   sb.append(((HolInfo)holidayMonthMap.get(cal)).getName());
	   if (itm.hasNext())
	   	sb.append("; ");
    }
%>
<p class="text"><%= sb.toString() %></p>
<% } %>

</body>

</HTML>