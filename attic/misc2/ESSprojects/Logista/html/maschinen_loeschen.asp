<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/maschinen/initmaschinen.inc" -->
<!-- #include file="include/maschinen/selectmaschine.inc" -->
<!-- #include file="include/maschinen/loeschenmaschine.inc" -->
<HTML>
<HEAD>
<TITLE>Maschinen l&ouml;schen</TITLE>

</HEAD>
<BODY>
<BR>
<form action="maschinen_loeschen.asp?maschinenid=<%= Request("maschinenid") %>" method="post">
<% if not loeschenOK then %>

<table border="0" cellspacing="0" cellpadding="5" width="50%">
			<% if not MaschinenRS.eof then %>
			   			
				<TR>
					<TD valign="top" bgcolor="#f9f9f9"><B><%= MaschinenRS("Titel") %></B></TD>
					<TD valign="top" bgcolor="#f9f9f9" align="right"><%= FormatDateTime(MaschinenRS("DatumErstellung"),2) %>&nbsp;<%= FormatDateTime(MaschinenRS("DatumErstellung"),4) %></TD>
				</TR>
				<TR><TD bgcolor="#f9f9f9" colspan="2" valign="top"><%= MaschinenRS("Beschreibung") %></TD>
				</TR>
				<TR>
					<TD valign="top" bgcolor="#f9f9f9" colspan="2"><%= MaschinenRS("Gueltigbis") %></TD>
					<TD></TD>
				</TR>
				<TR>
					<TD valign="top" bgcolor="#f9f9f9" colspan="2"><B><A href="mailto:<%= MaschinenRS("Email") %>?subject=<%= MaschinenRS("Titel") %>"><%= MaschinenRS("Name") %></A></B>&nbsp;(Tel. <%= MaschinenRS("Telefon") %>)</TD>
				</TR>
				<TR>
					<TD colspan="2">&nbsp;</TD>
				</TR>
				<% if not Admin then %>
				<tr>
				  <td>Passwort: </td>
				  <td><input type="text" name="passwort" size="20" maxlength="30"></td>
				</tr>
				<% end if %>
			    <tr>
				   <td colspan="2"><input type="submit" name="loeschen" value="Löschen"></td>
				</tr>
				<tr><td colspan="2"><b><%= Status %></b></td></tr>
			<% end if %>

</TABLE>
<% else %>
<b><%= Status %></b>
<% end if 
   MaschinenRS.close
%>	
</form>
<!-- #include file="menubarMaschinenLoeschen.asp" -->


</BODY>
</HTML>

<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/maschinen/maschinensub.inc" -->