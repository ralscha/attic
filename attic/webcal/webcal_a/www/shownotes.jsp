<%@ page language="java" import="java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Summary" errorPage="error.jsp"%>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>

<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
	<title>ESS Web Calendar: Show Notes</title><link rel="STYLESHEET" type="text/css" href="planner.css">
</head>

<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<% String idstr = request.getParameter("appid");
   if (idstr != null) {
	 Appointments app = appcache.getAppointment(Integer.parseInt(idstr));
%>
<p class="textb">
     <%= app.getBody() %>
</p>
<%	  
   }
%>

</body>
</html>
