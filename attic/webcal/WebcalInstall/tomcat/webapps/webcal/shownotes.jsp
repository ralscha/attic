<%@ page language="java" import="com.codestudio.util.*, java.text.*, java.util.*, java.sql.*, ch.ess.calendar.db.*" info="Summary" errorPage="error.jsp"%>

<jsp:useBean id="appcache" scope="application" class="ch.ess.calendar.session.AppointmentsCache"/>

<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
	<title>ESS Web Calendar: Show Notes</title>
</head>

<body>

<% String idstr = request.getParameter("appid");
   if (idstr != null) {
	 Appointments app = appcache.getAppointment(Integer.parseInt(idstr));
%>
     <%= app.getBody() %>
<%	  
   }
%>

</body>
</html>
