<%@ page import="java.util.*, ch.ess.pbroker.db.*, ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>

<head>
    <meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Rekrutierungsangaben</title>
</head>

<body>

<font face="Arial,Helvetica,sans-serif" size="4">Rekrutierungsangaben</font>
<form name="rekrutierung" action="rekrutierungForm" method="post">
<table>
<tr>
<td colspan="2" align="left"><font face="Arial,Helvetica,sans-serif" size="2"><b>Anforderungen an Mitarbeiter</b></font></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">T&auml;tigkeitsgebiete:</font></td>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><textarea cols="30" rows="4" name="taetigkeitsgebiete"></textarea></font></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Skills:</font></td>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><textarea cols="30" rows="4" name="skills"></textarea></font></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Pensum:</font></td>
	<td><input type="text" name="pensum"></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Mit Swisscom Erfahrung:</font></td>
	<td><input type="checkbox" name="swisscomerfahrung" value="se"></td>
</tr>
</table>
<p>
<table>
<tr>
<td colspan="2" align="left"><font face="Arial,Helvetica,sans-serif" size="2"><b>Einsatz</b></font></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Projekt/Bereich:</font></td>
	<td><input type="text" name="projekt"></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Beschreibung:</font></td>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><textarea cols="30" rows="4" name="beschreibung"></textarea></font></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Termin:</font></td>
	<td><input type="text" name="termin"></td>
</tr>
<tr>
	<td align="left" valign="top"><font face="Arial,Helvetica,sans-serif" size="1">Bemerkung:</font></td>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><textarea cols="30" rows="4" name="bemerkung"></textarea></font></td>
</tr>
</table>
<p>
<font face="Arial,Helvetica,sans-serif" size="2">
<input type="submit" name="save" value="Abspeichern">
</font>
</form>

</body>
</html>
