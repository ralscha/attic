<%@ page language="java" import="java.io.*,java.util.*" isErrorPage="true" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html><head><title>ContractTracker Error</title>
<link rel="STYLESHEET" type="text/css" href="../css.css">
</head>

<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">
<p>
Ein Fehler ist aufgetreten<br>
</p>

</body>
</html>

<pre>
Error
<%  //report exception 
    if (exception == null) {
	  exception = (Exception)request.getAttribute("org.apache.struts.action.EXCEPTION");
	}
    if (exception == null) {
	  exception = (Exception)request.getAttribute("javax.servlet.error.exception");
	}
	

  	Throwable rootCause = null;
	if (exception != null) {
  	if (exception instanceof ServletException) 
		rootCause = ((ServletException)exception).getRootCause();
    
    StringWriter writer = new StringWriter();

	if (rootCause != null) {
		rootCause.printStackTrace(new PrintWriter(writer));		
	} else {	
	    if (exception != null) {
	      exception.printStackTrace(new PrintWriter(writer));
		}
	}
%>
<%= writer.getBuffer() %>
<%	
	}
%>

--------------------------------------
<% //report request %>
Request:
URL: <%= request.getMethod() %> <%= request.getRequestURL() %><% if (request.getQueryString() != null) { %>?<%= request.getQueryString() %><% } %>
Attributes:
<%
 Enumeration names = request.getAttributeNames();
 while(names.hasMoreElements()) {
%><%= (String)names.nextElement() %>
<% 
 }
%>

--------------------------------------
Sesssion:
<% 
StringBuffer sb = new StringBuffer();
String sessionId = request.getRequestedSessionId();
if (sessionId == null) {
  sb.append("none");
} else if (request.isRequestedSessionIdValid()) {
  sb.append(sessionId);
  sb.append(" (from ");
  if (request.isRequestedSessionIdFromCookie())
    sb.append("cookie)\n");
  else if (request.isRequestedSessionIdFromURL())
    sb.append("url)\n");
  else
    sb.append("unknown)\n");
} else {
    sb.append("invalid\n");
}
%>
ID: <%= sb.toString() %>
Attributes:
<%
 names = session.getAttributeNames();
 while(names.hasMoreElements()) {
%><%= (String)names.nextElement() %>
<% 
 }
%>

--------------------------------------
<% //report parameters %>
Parameters:
<%
names = request.getParameterNames();
if (names.hasMoreElements()) {
  while (names.hasMoreElements()) {
    String name = (String) names.nextElement();
    String[] values = request.getParameterValues(name);
    for (int i = 0; i < values.length; ++i) {
%><%= name %>  =  <%= values[i] %>
<%	
    }
  }
} 
%>
--------------------------------------
<% //report headers %>
Headers:
<%
names = request.getHeaderNames();
if (names.hasMoreElements()) {
  while (names.hasMoreElements()) {
    String name = (String) names.nextElement();
    String value = (String) request.getHeader(name);
%><%= name %>  =  <%= value %>
<%
  }
}
%>
--------------------------------------
<% // report cookies %>
Cookies:
<%
    Cookie[] cookies = request.getCookies();
    int l = cookies.length;
    if (l > 0) {
      for (int i = 0; i < l; ++i) {
        Cookie cookie = cookies[i];
%><%= cookie.getName() %>  =  <%= cookie.getValue() %>
<%    } 
    } %>

	


</pre>
