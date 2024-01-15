<%@ page language="java" errorPage="error.jsp"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>

<body background="Background.gif">
<table border="0" cellspacing="0" cellpadding="0">
<TR>
<td width="150" valign="TOP">
<h3>LottoWin</h3>
<br>

<p class="contentTitle">Eingabe</p>
<html:link href="showTip.do?clean=1" styleClass="contentLink">Tips</html:link><br>
<html:link href="showJoker.do?clean=1" styleClass="contentLink">Joker</html:link><br>
<br>
<p class="contentTitle">Auswertung</p>
<html:link href="goCheckTip.do?clean=1" styleClass="contentLink">Tips</html:link><br>
<html:link href="goCheckJoker.do?clean=1" styleClass="contentLink">Joker</html:link><br>
<br>
<br>
<html:link href="logoff.do" styleClass="contentLink">Abmelden</html:link><br>
<br>
<hr>
<p class="ohne">Alle Angaben ohne Gew&auml;hr</p>
<hr>
<a href="mailto:rschaer@datacomm.ch" class="mail">rschaer@datacomm.ch</a>
</TD>
<td width="30" valign="TOP"> </TD>
<td valign="TOP">
<br>