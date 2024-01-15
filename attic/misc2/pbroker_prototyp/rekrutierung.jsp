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
<script language="JavaScript" type="text/javascript">
<!--
  function calpopup(lnk) { 
  window.open(lnk, "calendar","height=250,width=250,scrollbars=no") 
  }

  function submitForm() {
		document.forms[0].submit();
   }
//-->
</script>		<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=4'">

<p class="title">Rekrutierungsangaben</p>
<p class="text">Angaben f&uuml;r die Offert-Anfrage. Bitte m&ouml;glichst vollst&auml;ndig ausf&uuml;llen!
</p>
<struts:form action="rekrutierung.do" method="post" name="rekrutierung" scope="session" type="ch.ess.pbroker.session.Rekrutierung">

<table>
<tr>
<td colspan="2" class="textb">Anforderungen an Mitarbeiter</td>
</tr>
<tr>
	<td align="left" class="text">T&auml;tigkeitsgebiete:</td>
	<td class="text"><struts:text property="taetigkeitsgebiete" size="30" maxlength="50"/></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Skills:</td>
	<td class="text"><struts:textarea property="skills" cols="30" rows="4"></struts:textarea></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Pensum:</td>
	<td class="text"><struts:text property="pensum" size="5" onFocus="if (pensum.value == '0') pensum.value = ''" onBlur="if (pensum.value == '') pensum.value = '0'"/>%</td>
</tr>

</table>
<p>
<table>
<tr>
<td colspan="2" align="left" class="textb">Einsatz</td>
</tr>
<tr>
	<td align="left" class="text">Projekt/Bereich:</td>
	<td class="text"><struts:text property="projekt" size="30"/></td>
</tr>
<tr>
	<td align="left" class="text">Hauptaufgaben:</td>
	<td class="text"><struts:text property="aufgaben" size="30"/></td>
</tr>
<tr>
	<td align="left" class="text">Beginn:</td>
	<td class="text"><struts:text property="vontermin" size="10"/>
	<a href="javascript:calpopup('calendar.jsp?elem=vontermin')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt="" align="bottom"></a></td>
</tr>
<tr>
	<td align="left" class="text">Ende:</td>
	<td class="text"><struts:text property="bistermin" size="10"/>
	<a href="javascript:calpopup('calendar.jsp?elem=bistermin')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt="" align="bottom"></a></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Bemerkung:</td>
	<td class="text"><struts:textarea property="bemerkung" cols="30" rows="3"></struts:textarea></td>
</tr>
<tr>
	<td align="left" class="text">Ansprechpartner:</td>
	<td class="text"><struts:text property="ansprech" size="30" maxlength="50"/></td>
</tr>
<tr>
	<td align="left" class="text">Telefon:</td>
	<td class="text"><struts:text property="ansprechtel" size="20"/></td>
</tr>
<tr>
	<td align="left" class="text">E-Mail:</td>
	<td class="text"><struts:text property="ansprechemail" size="20"/></td>
</tr>
<tr>
	<td align="left" class="text">OE:</td>
	<td class="text"><struts:text property="oe" size="20"/></td>
</tr>
<tr>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
	<td align="left" class="text">Offerteingabe bis:</td>
	<td class="text"><struts:text property="offertebis" size="10"/>
	<a href="javascript:calpopup('calendar.jsp?elem=offertebis')"><img src="images/popupcal.gif" border="0" width="21" height="18" alt="" align="bottom"></a></td>
</tr>

</table>
<p>

<a href="end.do"><img src="images/but_stop.gif" width="25" height="23" border="0" alt="Abbrechen"></a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="basket.jsp"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
&nbsp;&nbsp;<a href="javascript:submitForm()"><img src="images/but_next.gif" width="25" height="23" border="0" alt=""></a>

</struts:form>

</body>
</html>
