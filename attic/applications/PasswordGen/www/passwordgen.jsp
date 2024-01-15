<%@ page language="java" import="java.util.*,ch.sr.pwgen.*" session="false" info="Password Generator"%>
<jsp:useBean id="passwordgen" scope="application" class="ch.sr.pwgen.PasswordGen"/>
<jsp:useBean id="pwrequest" scope="page" class="ch.sr.pwgen.PasswordGenRequest"/>
<jsp:setProperty name="pwrequest" property="*"/>

<html>
<head>
<title>Password Generator</title>
</head>
<body>

<h3><font face="Verdana,Geneva,Arial,Helvetica,sans-serif">Password Generator</font></h3>
<font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="-1"><form action="passwordgen.jsp" method="POST">
Number of Passwords:
<select name="nopw">
	   	<option value="10" <% if (pwrequest.getNopw() == 10) { %>selected<%}%>>10</option>
	   	<option value="20" <% if (pwrequest.getNopw() == 20) { %>selected<%}%>>20</option>
	   	<option value="30" <% if (pwrequest.getNopw() == 30) { %>selected<%}%>>30</option>
	   	<option value="40" <% if (pwrequest.getNopw() == 40) { %>selected<%}%>>40</option>
	   	<option value="50" <% if (pwrequest.getNopw() == 50) { %>selected<%}%>>50</option>
</select>
&nbsp;&nbsp;
Length of Password:
<select name="pwlen">
		<option value="5" <% if (pwrequest.getPwlen() == 5) { %>selected<%}%>>5</option>
	   	<option value="6" <% if (pwrequest.getPwlen() == 6) { %>selected<%}%>>6</option>
	   	<option value="7" <% if (pwrequest.getPwlen() == 7) { %>selected<%}%>>7</option>
	   	<option value="8" <% if (pwrequest.getPwlen() == 8) { %>selected<%}%>>8</option>
	   	<option value="9" <% if (pwrequest.getPwlen() == 9) { %>selected<%}%>>9</option>
	   	<option value="10" <% if (pwrequest.getPwlen() == 10) { %>selected<%}%>>10</option>
	   	<option value="11" <% if (pwrequest.getPwlen() == 11) { %>selected<%}%>>11</option>
	   	<option value="12" <% if (pwrequest.getPwlen() == 12) { %>selected<%}%>>12</option>
	   	<option value="13" <% if (pwrequest.getPwlen() == 13) { %>selected<%}%>>13</option>
	   	<option value="14" <% if (pwrequest.getPwlen() == 14) { %>selected<%}%>>14</option>
</select>
&nbsp;&nbsp;
Language:
<select name="language">
	   	<option value="GERMAN" <% if (pwrequest.isRequestedLanguage("GERMAN")) { %>selected<%}%>>German</option>
		<option value="ENGLISH" <% if (pwrequest.isRequestedLanguage("ENGLISH")) { %>selected<%}%>>English</option>
</select>
&nbsp;&nbsp;
<input type="Submit" name="Submit" value="Generate">
<p>
<input type="checkbox" name="withNumberStr" value="true" <% if (pwrequest.isWithNumber()) { %>checked<% } %>>with number&nbsp;&nbsp;
<input type="checkbox" name="withSpecialStr" value="true" <% if (pwrequest.isWithSpecial()) { %>checked<% } %>>with special character
</form></font>
<hr>

<% if (pwrequest.isValuesSet()) { 
   List pwList = passwordgen.generate(pwrequest.getNopw(), pwrequest.getPwlen(), pwrequest.getLanguage(), pwrequest.getMode());
   Iterator it = pwList.iterator();
%>
<table width="500">

<% while (it.hasNext()) { %>
<tr>
	<td><font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="-1"><%= it.next() %></font></td>
	
	<% for (int i = 0; i < 2; i++) { %>
		<% if (it.hasNext()) { %>
		<td><font face="Verdana,Geneva,Arial,Helvetica,sans-serif" size="-1"><%= it.next() %></font></td>
		<% } else {%>
		<td>&nbsp;</td>
		<% } %>
	<% } %>	
</tr>
<% } %>

</table>

<% } %>

</body></html>
