<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/maschinen/initmaschinen.inc" -->
<!-- #include file="include/maschinen/updatemaschinenrubriken.inc" -->
<!-- #include file="include/maschinen/openmaschinenrubriken.inc" --> 

<HTML>
<HEAD>
<TITLE>Maschinen Ueberblick</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
</HEAD>
<BODY>
<h3 align="center">Maschinen Rubriken bearbeiten</h3>

<form action="maschinen_rubriken.asp" method="post">
<table border="1">
<tr>
<th>Beschreibung</th>
<th>L&ouml;schen?</th>
</tr>
<% do until MaschinenRubrikRS.eof %>

<tr>
	<td><input type="text" name="<%=MaschinenRubrikRS("ID")%>" value="<%=MaschinenRubrikRS("Beschreibung")%>"></td>
	<td align="center"><input type="checkbox" name="loeschen" value="<%=MaschinenRubrikRS("ID")%>"></td>
</tr>
<% 
     MaschinenRubrikRS.moveNext
   loop 
   MaschinenRubrikRS.close 
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


   

