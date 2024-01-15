<%@ page import="java.util.*, java.text.*"%>
  <% 
  String pagename;
  String formname;
  String elementname;

  Calendar today = new GregorianCalendar();
  
  if (request.getParameter("Page") != null) { 
     session.setAttribute("PageName", request.getParameter("Page"));
  } 
  pagename = (String)session.getAttribute("PageName");
  
  
  if (request.getParameter("Form") != null) {
     session.setAttribute("FormName", request.getParameter("Form"));
  } 
  
  formname = (String)session.getAttribute("FormName");
  
  if (request.getParameter("Element") != null) {
     session.setAttribute("ElementName", request.getParameter("Element"));
  }
  
  elementname = (String)session.getAttribute("ElementName");

  %> 
  <HTML> 
  <HEAD>  
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=iso-8859-1"> 
  <TITLE>Select Date</TITLE> 
  <SCRIPT LANGUAGE="javascript"> 
 function calpopulate(d, m, y) { 
         var dte = "";
         if (d < 10) {
           dte = "0";
         }
         dte = dte + d + ".";
         if (m < 10) {
           dte = dte + "0";
         }
         dte = dte + m + ".";
         dte = dte + y;
	 window.opener.document.<%=formname + "." + elementname%>.value = dte; 
     self.close() 
        } 
  </SCRIPT> 
  </HEAD> 
  <BODY onBlur="javascript:self.focus();"> 
  <% 
  	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	SimpleDateFormat monthformatter = new SimpleDateFormat("MMMM", Locale.US);
	
	GregorianCalendar buildDate = null;
	
	if (request.getParameter("Date") != null) {
	  	Date tmpDate = formatter.parse(request.getParameter("Date"));
		buildDate = new GregorianCalendar();
		buildDate.setTime(tmpDate);
	} 
	
	int bMonth;
	int bYear;
	
	if (buildDate == null) {
		if (request.getParameter("BMonth") == null) {
			bMonth = today.get(Calendar.MONTH);		
		} else {
			bMonth = Integer.parseInt(request.getParameter("BMonth"));
		}
		
		if (request.getParameter("BYear") != null) {
			bYear = Integer.parseInt(request.getParameter("BYear"));	
		} else {
			bYear = today.get(Calendar.YEAR);
		}
		buildDate = new GregorianCalendar(bYear, bMonth, 1);
	}
 	session.setAttribute("CurrentDate", buildDate);

  	int buildDayValue = buildDate.get(Calendar.DAY_OF_WEEK);
	int currentMonth = buildDate.get(Calendar.MONTH);
  %> 
  <center> 
  <table> 
  <tr> 
  <td colspan="7" align="center"> 
  <hr> 
  <font size=2><b><%= monthformatter.format(buildDate.getTime()) %>&nbsp;<%= buildDate.get(Calendar.YEAR) %></b></font>
  <br> 

  <% 
  String nextMonth;
  if (currentMonth < 11) {
	  nextMonth = (currentMonth+1) + "&BYear=" + buildDate.get(Calendar.YEAR);
  } else { 
  	  nextMonth = "0&BYear=" + (buildDate.get(Calendar.YEAR) + 1);
  }

  String previousMonth;
  if (currentMonth > 0) {
  	previousMonth = (currentMonth-1) + "&BYear=" + buildDate.get(Calendar.YEAR);   
  } else {
  	previousMonth = "11&BYear=" + (buildDate.get(Calendar.YEAR) - 1); 
  }
  %> 
  <a href="DateSelect.jsp?BMonth=<%= previousMonth %>"><font size=-2>Previous</font></a> 
  &nbsp;&nbsp;&nbsp; 
  <a href="DateSelect.jsp?BMonth=<%= nextMonth %>"><font size=-2>Next</font></a> 

  
  <hr></td> 
  </tr> 
  
  <tr> 
  <td><font size="-3">Su</font></td><td><font size="-3">Mo</font></td><td><font 
  size="-3">Tu</font></td><td><font size="-3">We</font></td><td><font 
  size="-3">Th</font></td><td><font size="-3">Fr</font></td><td><font size="-3">Sa</font></td> 
  </tr> 
  <tr> 

  <% 
  int dayPosition = 1;
  for (int i = 0; i < buildDayValue -1 ; i++) {

  %> 
  <td><font size="-3">&nbsp;</font></td> 
  <% 
  	dayPosition++;
  } 

  while (currentMonth == buildDate.get(Calendar.MONTH)) {
  %> 

  <% 
   while (dayPosition != 8) { 
  %> 
  <td align="center" <%if (buildDate.get(Calendar.DATE) == today.get(Calendar.DATE)) { %> bgcolor=\"Lime\"") <%}%>>
  <font size="-3"> 
  <a href="javascript:calpopulate(<%=buildDate.get(Calendar.DATE)%>, <%=(buildDate.get(Calendar.MONTH)+1)%>,<%=buildDate.get(Calendar.YEAR)%> )"><%= buildDate.get(Calendar.DATE) %></a> 
  </font>
  </td> 
  <% 
  	dayPosition = dayPosition + 1; 
 	 buildDate.add(Calendar.DATE, 1);
  	if (currentMonth != buildDate.get(Calendar.MONTH)) { 
  		dayPosition = 8; 
  	}
  }
  dayPosition = 1; 
  %> 

  </tr><tr> 
  <% 
  }
  %> 
  </tr> 
  </table> 
  </center> 
  </BODY> 
  </HTML> 