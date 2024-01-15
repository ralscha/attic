<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*" info="Summary" errorPage="error.jsp"%>

<HTML>
<HEAD>
<title>Date Planner: Summary</title>

<script type="text/javascript">
 <!--
   function popup(){
   newwin = window.open('','popup','width=500,height=200,scrollbar=yes,toolbar=no');
 } 
 //-->
 </script>
 

<link rel="STYLESHEET" type="text/css" href="planner.css">
</HEAD>

<BODY>

<%
  int[] monthdays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30 ,31};
  
  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  UsersTable usersTable = new UsersTable(conn);
  DatesTable datesTable = new DatesTable(conn);

  
  
  try {
  
  int rmonth;
  int ryear;
  Calendar today = new GregorianCalendar();
  
  if (request.getParameter("month") != null) {
    rmonth = Integer.parseInt(request.getParameter("month"));
    ryear = Integer.parseInt(request.getParameter("year"));
  } else {
    rmonth = today.get(Calendar.MONTH);
    ryear = today.get(Calendar.YEAR);
  }
    
  SimpleDateFormat dateformatter = new SimpleDateFormat("dd.MM.yyyy");  
  SimpleDateFormat monthformatter = new SimpleDateFormat("MMMM", Locale.US);
  
  GregorianCalendar tmp = new GregorianCalendar(ryear, rmonth, 1);
        
  int daysofmonth = monthdays[rmonth];
  if (tmp.isLeapYear(ryear) && (rmonth == Calendar.FEBRUARY)) {
    daysofmonth++;
  }
  
  Map kindmap = new HashMap();
  
  DateKindTable dateKindTable = new DateKindTable(conn);
  Iterator it = dateKindTable.select();
  while (it.hasNext()) {
  	DateKind dk = (DateKind)it.next();
	kindmap.put(dk.getKindID(), dk.getDescription());
  }

  String monthNames[] = new String[12];
  for (int i = 0; i < 12; i++) {
    tmp = new GregorianCalendar(ryear, i, 1);
	monthNames[i] = monthformatter.format(tmp.getTime()); 	 
  }
        
%>

<p class="titlesmall">Summary: <%= monthNames[rmonth] %>&nbsp;<%= ryear %></p>

<br>

<table border="1">
<tr>
<th align="left">User / Day</th>

<%
  for (int i = 1; i <= daysofmonth; i++) {
    tmp = new GregorianCalendar(ryear, rmonth, i);
  
    if ((today.get(Calendar.DATE) == i) && (today.get(Calendar.MONTH) == rmonth)) { %>          	  
      <th bgcolor="Lime"><%= i %></th>
	<% } else if ( (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) 
	         || (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) { %>
	  <th bgcolor="Silver"><%= i %></th>
	<% } else {%>
	  <th><%= i %></th>    
	<%} 
  }
%>

</tr>

<%
  Iterator userit = usersTable.select(null, "firstname");
  while (userit.hasNext()) {
    Users user = (Users)userit.next();
 %>
 <tr>
   <td><%= user.getFirstname() %>&nbsp;<%= user.getName() %></td>
   <%  
       List dateslist = new ArrayList();
	   for (int i = 1; i <= daysofmonth; i++) {
	     dateslist.clear();
		 
	     java.util.Date td = (new GregorianCalendar(ryear, rmonth, i)).getTime();
		 
		 Iterator datesit = datesTable.selectDates(user.getUserID(), td);
		 
		 while(datesit.hasNext()) {
		 	Dates date = (Dates)datesit.next();
			dateslist.add(date);			
		 }					 
				 
		 String descriptiontext;					 
		 String ext;
		 
         if (dateslist.size() == 0) { %>
	       <td align="center" valign="middle"><img src="white.gif" width="20" height="20" border="0" alt=""></td>     
	  <% } else if (dateslist.size() == 1) { 	
	        Dates date = (Dates)dateslist.get(0);
	        descriptiontext = "";
	        ext = "";
	        if (!formatTime(date.getStartTime()).equals("00:00")) {
	          ext = "_part";
	          descriptiontext = formatTime(date.getStartTime()) + "-" + formatTime(date.getEndTime());
	        }
	           	        
	        if (date.getDescription() != null) {
	          descriptiontext = descriptiontext + " " + date.getDescription();
	        } else {
	          descriptiontext = descriptiontext + " " + (String)kindmap.get(date.getKindID());
	        }
	       %>
	       <td align="center" valign="middle"><A HREF="showdetail.jsp?userid=<%= date.getUserID() %>&date=<%= formatDbDate(td) %>" target="popup" onclick="popup();"><img src="<%= date.getKindID() + ext %>.gif" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	  <% } else { 
	         descriptiontext = "";
			 
			 for (int j = 0; j < dateslist.size(); j++) { 
				Dates date = (Dates)dateslist.get(j);
	          	         
	          if (!formatTime(date.getStartTime()).equals("00:00")) {
	            descriptiontext = descriptiontext + formatTime(date.getStartTime()) + "-" + formatTime(date.getEndTime());
	          }
	              
	          if (date.getDescription() != null) {
	            descriptiontext = descriptiontext + " " + date.getDescription();
	          } else {
	            descriptiontext = descriptiontext + " " + (String)kindmap.get(date.getKindID());
	          }
	                       
              descriptiontext = descriptiontext + "; ";
             }
            	     
	     
	     %>
	       <td align="center" valign="middle"><A HREF="showdetail.jsp?userid=<%= user.getUserID() %>&date=<%= formatDbDate(td) %>" target="popup" onclick="popup();"><img src="multi.gif" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>
       <% }
	   }
   %>   
 </tr>
 

<% } %>
</table>

<p>
<form method="get" target="Main" action="summary.jsp">
<table>
<tr>
<% 
   int prevmonth, prevyear, nextmonth, nextyear;
   
   if (rmonth == 1) {
     prevmonth = 12;
     prevyear = ryear - 1;
   } else {
     prevmonth = rmonth - 1;
     prevyear = ryear;
   }
   
   if (rmonth == 12) {
     nextmonth = 1;
     nextyear = ryear + 1;
   } else {
     nextmonth = rmonth + 1;
     nextyear = ryear;
   }
%>
<td><a href="summary.jsp?month=<%= prevmonth %>&year=<%= prevyear %>" class="text" target="Main">&lt; Previous Month</a></td>
<td>&nbsp;</td>
<td><a href="summary.jsp?month=<%= nextmonth %>&year=<%= nextyear %>" class="text" target="Main">Next Month &gt;</a></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td class="text">Month: <select name="month" size="1">
    <% for (int i = 0; i < 12; i++) {
       if (i == rmonth) { %>
      <option value="<%= i %>" selected><%= monthNames[i] %></option>
    <% } else { %>
      <option value="<%= i %>"><%= monthNames[i] %></option>
    <% } } %>   	
	</select></td>
<td class="text">Year: <select name="year">
    <% int cyear = today.get(Calendar.YEAR);
	   for (int i = 0; i < 5; i++) {
       if (ryear == (cyear + i)) { %>
	   <option value="<%= cyear + i %>" selected><%= cyear + i %></option>
	<% } else { %>
	   <option value="<%= cyear + i %>"><%= cyear + i %></option>
	<% } } %>
	
	</select></td>	
<td><input class="text" type="submit" value="Show"></td>	
</tr>
</table>
</form>
<hr>

<table>

<% 


String[] keys = (String[])kindmap.keySet().toArray(new String[kindmap.size()]);
int count = kindmap.size();

for (int num = 0; num < count; num += 5) { %>

  <tr>
      
  <% 
   for (int num2 = 0; num2 <= 4; num2++) {
     if (num+num2 < count) {%>
    <td><img src="<%= keys[num+num2] %>.gif" width="20" height="20" border="0" alt="<%= kindmap.get(keys[num+num2]) %>"></td>
    <td><%= kindmap.get(keys[num+num2]) %></td>
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

<% } finally {
	manager.returnConnection(conn);	
} %>

</HTML>
<%@ include file="common.jsp" %>