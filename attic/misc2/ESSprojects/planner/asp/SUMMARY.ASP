<%@ Language=VBScript %>
<% option explicit 
   on error resume next %>

<!-- #include file="adovbs.inc" -->

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
  dim cnn, userrs, daters, kindrs, rmonth, cyear, ryear, rmonthstr, daysofmonth, monthdays(12), kinddict, i, query
  dim prevmonth, prevyear, nextmonth, nextyear, descriptiontext, ext
  monthdays(1) = 31
  monthdays(2) = 28
  monthdays(3) = 31
  monthdays(4) = 30
  monthdays(5) = 31
  monthdays(6) = 30
  monthdays(7) = 31
  monthdays(8) = 31
  monthdays(9) = 30
  monthdays(10) = 31
  monthdays(11) = 30
  monthdays(12) = 31       
        
  set cnn = Server.CreateObject("ADODB.Connection")
  cnn.open("DSN=PlannerDb2")
  
  set userrs = Server.CreateObject("ADODB.RecordSet")
  set daters = Server.CreateObject("ADODB.RecordSet")
  set kindrs = Server.CreateObject("ADODB.RecordSet")
  
  if trim(Request.QueryString("month")) <> "" then
    rmonth = CInt(Request.QueryString("month"))
    ryear = CInt(Request.QueryString("year"))
  else
    rmonth = month(date)
    ryear = year(date)
  end if
    
  cyear = year(date)  
    
  rmonthstr = MonthName(rmonth)
     
  daysofmonth = monthdays(rmonth)
  if rmonth = 2 and ryear mod 4 = 0 then
    daysofmonth = daysofmonth + 1
  end if
 
  dim curdate(31)
  dim sqlcurdate(31)
 
  kindrs.open "select KindID, Description from DateKind", cnn, adOpenDynamic, adLockOptimistic     
  set kinddict = CreateObject("Scripting.Dictionary")
      
  do until kindrs.eof 
     kinddict.Add cstr(kindrs("KindID")), cstr(kindrs("Description"))        
     kindrs.MoveNext
  loop
  kindrs.Close
        
%>

<p class="titlesmall">Summary: <%= rmonthstr %>&nbsp;<%= ryear %></p>

<br>

<table border="1">
<tr>
<th align="left">User / Day</th>

<%
  for i = 1 to daysofmonth 
    curdate(i) = CDate(i & "/" & rmonth & "/" & ryear)
    sqlcurdate(i) = rmonth & "/" & i & "/" & ryear

    if (day(date) = i) AND (rmonth = month(date)) then %>          	  
      <th bgcolor="Lime"><%= i %></th>
	<% elseif weekday(curdate(i)) = 1 OR weekday(curdate(i)) = 7 then %>
	  <th bgcolor="Silver"><%= i %></th>
	<% else %>
	  <th><%= i %></th>
    <% 
    end if
  next
%>

</tr>

<%
query = "SELECT UserID, firstname, name FROM Users ORDER by firstname"
userrs.open query, cnn, adOpenStatic, adLockOptimistic

do until userrs.eof 
   

 %>
 <tr>
   <td><%= userrs("firstname") %>&nbsp;<%= userrs("name") %></td>
   <%
	   for i = 1 to daysofmonth 
	     
	     query = "select StartDate, StartTime, EndDate, EndTime, KindID, UserID, DateID, Description from dates where UserID = '" & userrs("UserID") & "' and startdate <= #" & sqlcurdate(i) & "# and enddate >= #" & sqlcurdate(i) & "# order by StartDate"
	     daters.open query, cnn, adOpenKeyset, adLockOptimistic
	     
	     if daters.RecordCount <= 0 then %>
	       <td align="center" valign="middle"><img src="white.gif" width="20" height="20" border="0" alt=""></td>     
	  <% elseif daters.RecordCount = 1 then 	 
	        descriptiontext = ""
	        ext = ""
	        if daters("StartTime") <> #00:00# then
	          ext = "_part"
	          descriptiontext = formatdatetime(daters("StartTime"), 4) & "-" & formatdatetime(daters("EndTime"), 4)
	        end if
	           	        
	        if (not isNull(daters("Description"))) then
	          descriptiontext = descriptiontext & " " & daters("Description")
	        else
	          descriptiontext = descriptiontext & " " & kinddict.Item(cstr(daters("KindID"))) 
	        end if
	       %>
	       <td align="center" valign="middle"><A HREF="showdetail.asp?userid=<%= userrs("UserID") %>&date=<%= curdate(i) %>" target="popup" onclick="popup();"><img src="<%= daters("KindID") & ext %>.gif" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>     
	  <% else 
	         descriptiontext = "" 
	         while not daters.eof
	          	         
	          if daters("StartTime") <> #00:00# then
	            descriptiontext = descriptiontext & formatdatetime(daters("StartTime"), 4) & "-" & formatdatetime(daters("EndTime"), 4)
	          end if
	              
	          if not isNull(daters("Description")) then
	            descriptiontext = descriptiontext & " " & daters("Description")
	          else
	            descriptiontext = descriptiontext & " " & kinddict.Item(cstr(daters("KindID"))) 
	          end if
	                       
              daters.MoveNext
              descriptiontext = descriptiontext & vbCRLF
             wend
            	     
	     
	     %>
	       <td align="center" valign="middle"><A HREF="showdetail.asp?userid=<%= userrs("UserID") %>&date=<%= curdate(i) %>" target="popup" onclick="popup();"><img src="multi.gif" width="20" height="20" border="0" alt="<%= descriptiontext %>"></a></td>
         <% end if 
         daters.close   
      next   
   %>   
 </tr>
 

<% userrs.movenext
loop
userrs.close
%>
</table>

<p>
<form method="get" target="Main" action="summary.asp">
<table>
<tr>
<% 
   if rmonth = 1 then
     prevmonth = 12
     prevyear = ryear - 1
   else
     prevmonth = rmonth - 1
     prevyear = ryear
   end if 
   
   if rmonth = 12 then
     nextmonth = 1
     nextyear = ryear + 1
   else
     nextmonth = rmonth + 1
     nextyear = ryear
   end if
%>
<td><a href="summary.asp?month=<%= prevmonth %>&year=<%= prevyear %>" class="text" target="Main">&lt; Previous Month</a></td>
<td>&nbsp;</td>
<td><a href="summary.asp?month=<%= nextmonth %>&year=<%= nextyear %>" class="text" target="Main">Next Month &gt;</a></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td class="text">Month: <select name="month" size="1">
    <% for i = 1 to 12  
       if i = rmonth then %>
      <option value="<%= i %>" selected><%= MonthName(i) %></option>
    <% else %>
      <option value="<%= i %>"><%= MonthName(i) %></option>
    <% end if
       next %>   	
	</select></td>
<td class="text">Year: <select name="year">
    <% for i = 0 to 5
       if ryear = cyear + i then %>
	   <option value="<%= cyear + i %>" selected><%= cyear + i %></option>
	<% else %>
	   <option value="<%= cyear + i %>"><%= cyear + i %></option>
	<% end if
	   next %>
	
	</select></td>	
<td><input class="text" type="submit" value="Show"></td>	
</tr>
</table>
</form>
<hr>

<table>

<% 

dim keys, count, num, num2
keys = kinddict.Keys
count = kinddict.Count - 1


for num = 0 to count step 5 %>

  <tr>
      
  <% 
   for num2 = 0 to 4     
     if num+num2 <= count then %>
    <td><img src="<%= keys(num+num2) %>.gif" width="20" height="20" border="0" alt="<%= kinddict.Item(keys(num+num2)) %>"></td>
    <td><%= kinddict.Item(keys(num+num2)) %></td>
    <td>&nbsp;</td>
  <% else %>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  <% end if 
   next %>
  
  </tr>
  
<% next

%>
</table>

</body>

<% cnn.Close %>

</HTML>
