<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->
<HTML>
<HEAD>

<TITLE>Mitglieder Menu</TITLE>
</HEAD>
<BODY>
<table>
<tr>
	<td><a href="maschinen_ueberblick.asp" target="main"><img src="images/Maschinen_r.jpg" width="92" height="18" border="0" alt="Maschinen"></a></td>
</tr>
<tr>
	<td><p>&nbsp;</p></td>
</tr>
<tr>
	<td><img src="images/Mitarbeiter.jpg" width="95" height="17" border="0" alt="Mitarbeiter">
	<table>
<tr>
	<td><a href="mitarbeiter_ueberblick.asp?suchebiete=B" target="main"><img src="images/biete_r.jpg" width="34" height="14" border="0" alt="Mitarbeiter biete"></a></td>
	<td><a href="mitarbeiter_ueberblick.asp?suchebiete=S" target="main"><img src="images/suche_r.jpg" width="37" height="14" border="0" alt="Mitarbeiter suche"></a></td>
</tr>
</table>
</td>
</tr>

<% if Session("Profil") = "A" then %>
<tr>
	<td><a href="mitarbeiter_rubriken.asp" target="main">Mitarbeiter Rubriken bearbeiten</a></td>
</tr>
<tr>
	<td><a href="maschinen_rubriken.asp" target="main">Maschinen Rubriken bearbeiten</a></td>
</tr>
<% end if %>
</table>

</BODY>
</HTML>
