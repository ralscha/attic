<%@ page import="java.util.*, ch.ess.pbroker.db.*,ch.ess.pbroker.rekrutierung.*,com.objectmatter.bsf.*" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<app:checkLogon/>

<html>
<head>
<title>Personalien</title>
<link rel="STYLESHEET" type="text/css" href="../pbroker.css">
</head>

<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<p class="title">Personalien</p>

<form name="personalienForm" action="rekrutierung/savePersonalien.do" method="post">
<table border="0">
<tr>
<td bgcolor="#000000">
<table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr>
          <td ALIGN="left" bgcolor="White">Anrede</td>
          <td bgcolor="White">
		    <select size="1" name="Anrede">
			<option value="Herr" selected>Herr</option>
			<option value="Frau" selected>Frau</option>
            </select></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Titel</td>
          <td bgcolor="White"><input type="text" name="titel" size="39"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Name</td>
          <td bgcolor="White"><input type="text" name="name" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Vorname</td>
          <td bgcolor="White"><input type="text" name="vorname" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Strasse</td>
          <td bgcolor="White"><input type="text" name="strasse" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">PLZ</td>
          <td bgcolor="White"><input type="text" name="plz" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Ort</td>
          <td bgcolor="White"><input type="text" name="ort" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Land</td>
          <td bgcolor="White"><input type="text" name="land" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Telefon</td>
          <td bgcolor="White"><input type="text" name="telefon" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Telefon Mobil</td>
          <td bgcolor="White"><input type="text" name="telefonmobil" size="40"></td>
        </tr>		
        <tr>
          <td ALIGN="left" bgcolor="White">Fax</td>
          <td bgcolor="White"><input type="text" name="fax" size="40"></td>
        </tr>		
        <tr>
          <td ALIGN="left" bgcolor="White">E-Mail</td>
          <td bgcolor="White"><input type="text" name="email" size="40"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Geburtsdatum</td>
          <td bgcolor="White"><input type="text" name="geburts" size="8"></td>
        </tr>
        <tr>
          <td ALIGN="left" bgcolor="White">Sozialversicherungsnr.</td>
          <td bgcolor="White"><input type="text" name="sozial" size="20"></td>
        </tr>
      </table>
</td></tr></table>
<p>
  <input TYPE="submit" VALUE="Abspeichern">&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="#"><img src="../images/but_back.gif" width="25" height="23" border="0" alt=""></a>   
</form>

</body>
</html>
