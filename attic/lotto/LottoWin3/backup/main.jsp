<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<app:checkLogon/>
<html>
<head>
<title>logon</title>
</head>
<body>
<struts:link href="showTip.do">Lottozettel eingeben</struts:link><br>
<struts:link href="showJoker.do">Joker eingeben</struts:link>
<struts:link href="logoff.do">Abmelden</struts:link><br>
</body>
</html>
