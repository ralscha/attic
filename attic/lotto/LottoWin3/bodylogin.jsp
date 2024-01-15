<%@ page language="java" errorPage="error.jsp"%>


<body background="Background.gif">
<table border="0" cellspacing="0" cellpadding="0">
<TR>
<td width="150" valign="TOP">
<h3>LottoWin</h3>
<br>

<p class="contentTitle">Eingabe</p>
<a href="<%= response.encodeURL("showTip.do")%>" class="contentLink">Tips</a><br>
<a href="<%= response.encodeURL("showJoker.do")%>" class="contentLink">Joker</a><br>
<br>
<p class="contentTitle">Auswertung</p>
<a href="<%= response.encodeURL("goCheckTip.do")%>" class="contentLink">Tips</a><br>
<a href="<%= response.encodeURL("goCheckJoker.do")%>" class="contentLink">Joker</a><br>
<br>
<br>
<a href="<%= response.encodeURL("logoff.do")%>" class="contentLink">Abmelden</a><br>
<br>
<hr>
<p class="ohne">Alle Angaben ohne Gew&auml;hr</p>
<hr>
<a href="mailto:rschaer@datacomm.ch" class="mail">rschaer@datacomm.ch</a>
</TD>
<td width="30" valign="TOP"> </TD>
<td valign="TOP">
<br>