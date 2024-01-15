<%@ page info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<html>

<head>
<title>PBroker</title>
</head>

<frameset framespacing="0" border="0" rows="151,*" frameborder="0">
  <frame name="top" scrolling="no" noresize target="_self" src="frames/top_step_nop.html" marginwidth="0" marginheight="0">
  <frameset cols="165,*">
    <frame src="menu.jsp" name="menu" id="menu" scrolling="auto" noresize marginwidth="0" marginheight="0" target="main">
    <frame name="main" src="main.html" marginwidth="0" marginheight="0" scrolling="auto" noresize target="_blank">
  </frameset>
  <noframes>
  <body>
  </body>
  </noframes>
</frameset>

</html>
