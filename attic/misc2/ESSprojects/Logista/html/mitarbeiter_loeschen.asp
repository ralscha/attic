<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/selectmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/loeschenmitarbeiter.inc" -->

<HTML>
<HEAD>
<TITLE>Mitarbeiter l&ouml;schen</TITLE>

</HEAD>
<BODY>
<BR>
<form action="mitarbeiter_loeschen.asp?suchebiete=<%= Request("suchebiete")%>&mitarbeiterid=<%= Request("mitarbeiterid") %>" method="post">
<% if not loeschenOK then %>

<table border="0" cellspacing="0" cellpadding="5" width="50%">
			<% if not MitarbeiterRS.eof then %>
			   			
				<TR>
					<TD valign="top" bgcolor="#f9f9f9"><B><%= MitarbeiterRS("Titel") %></B></TD>
					<TD valign="top" bgcolor="#f9f9f9" align="right"><%= FormatDateTime(MitarbeiterRS("DatumErstellung"),2) %>&nbsp;<%= FormatDateTime(MitarbeiterRS("DatumErstellung"),4) %></TD>
				</TR>
				<TR><TD bgcolor="#f9f9f9" colspan="2" valign="top"><%= MitarbeiterRS("Beschreibung") %></TD>
				</TR>
				<TR>
					<TD valign="top" bgcolor="#f9f9f9" colspan="2"><%= MitarbeiterRS("Gueltigbis") %></TD>
					<TD></TD>
				</TR>
				<TR>
					<TD valign="top" bgcolor="#f9f9f9" colspan="2"><B><A href="mailto:<%= MitarbeiterRS("Email") %>?subject=<%= MitarbeiterRS("Titel") %>"><%= MitarbeiterRS("Name") %></A></B>&nbsp;(Tel. <%= MitarbeiterRS("Telefon") %>)</TD>
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
<% end if %>
  <%
    MitarbeiterRS.close
 %>	
</form>
<!-- #include file="menubarMitarbeiterLoeschen.asp" -->


</BODY>
</HTML>

<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/mitarbeiter/mitarbeitersub.inc" -->