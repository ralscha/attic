<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/updatemitarbeiterrubriken.inc" -->
<!-- #include file="include/mitarbeiter/openmitarbeiterrubriken.inc" --> 

<HTML>
<HEAD>
<TITLE>Mitarbeiter Ueberblick</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
</HEAD>
<BODY>
<h3 align="center">Mitarbeiter Rubriken bearbeiten</h3>

<form action="mitarbeiter_rubriken.asp" method="post">
<table border="1">
<tr>
<th>Beschreibung</th>
<th>L&ouml;schen?</th>
</tr>
<% do until MitarbeiterRubrikRS.eof %>

<tr>
	<td><input type="text" name="<%=MitarbeiterRubrikRS("ID")%>" value="<%=MitarbeiterRubrikRS("Beschreibung")%>"></td>
	<td align="center"><input type="checkbox" name="loeschen" value="<%=MitarbeiterRubrikRS("ID")%>"></td>
</tr>
<% 
   MitarbeiterRubrikRS.moveNext
   loop 
   MitarbeiterRubrikRS.close
%>
<tr>
	<td><input type="text" name="neuerubrik" value=""></td>
	<td align="center">Neu</td>
</tr>
</table>
<input type="submit" name="aktualisieren" value="Aktualisieren">
</form>


</BODY>
</HTML>
<!-- #include file="include/cleanup.inc" -->


   

