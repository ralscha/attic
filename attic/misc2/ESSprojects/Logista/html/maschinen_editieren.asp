<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/maschinen/initmaschinen.inc" -->
<!-- #include file="include/maschinen/selectmaschine.inc" -->
<!-- #include file="include/maschinen/updatemaschine.inc" -->
<!-- #include file="include/maschinen/openmaschinenrubriken.inc" --> 
<HTML>
<HEAD>
<TITLE>Maschinen editieren</TITLE>
<script language="JavaScript" src="check.js"></script>
</HEAD>
<BODY>



<TABLE border="0" cellpadding="10" cellspacing="0" width="600" bgcolor="#FFFFFF">
	<TR>
		<TD></TD>
		<TD><B>Maschinen Pool</B><BR> <BR></TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>	
		
			<FORM name="mail" method="POST" action="maschinen_editieren.asp?maschinenid=<%= Request("maschinenid")%>" onSubmit="return chkFM(this)">
				<TABLE border="0">
					<TR>
						<TD colspan="2">
							<TABLE border="0">
								<TR>
									<TD><IMG src="images/finger.gif" border="0"></TD>
									<TD></TD>
									<TD>Anzeige editieren</TD>
								</TR>
							</TABLE><BR> <BR>
						</TD>
					</TR>
					<TR>
						<TD>Name:</TD>
						<TD><input type="text" name="username" value="<%= MaschinenRS("Name") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>E-Mail:</TD>
						<TD><input type="text" name="email" value="<%= MaschinenRS("Email") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Telefon:</TD>
						<TD><input type="text" name="tel" value="<%= MaschinenRS("Telefon") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Passwort:</TD>
						<TD><input type="text" name="pw" value="<%= MaschinenRS("Passwort") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Titel:</TD>
						<TD><input type="text" name="titel" value="<%= MaschinenRS("Titel") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>g&uuml;ltig bis:</TD>
						<TD><input type="text" name="gueltig" value="<%= MaschinenRS("Gueltigbis") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD><textarea cols="43" rows="7" name="com"><%= ConvertBeschreibung(MaschinenRS("Beschreibung"), false) %></textarea></TD>
					</TR>
					<TR>
						<TD valign="top">Rubrik:</TD>
						<TD>
							<SELECT name="rubrik" size="1">
							  <% do until MaschinenRubrikRS.eof %>
								<OPTION value="<%=MaschinenRubrikRS("ID")%>" <% if MaschinenRubrikRS("ID") = MaschinenRS("MaschinenRubrik") then %>selected<% end if %>><%= MaschinenRubrikRS("Beschreibung")%></OPTION>
							  <% 
								 MaschinenRubrikRS.moveNext
							     loop 
							  %>
							</SELECT>							  
						</TD>
					</TR>
					<TR>
						<TD valign="top">l&ouml;schen nach:
						</TD>
						<TD>
							<SELECT name="dauer" size="1">
								<OPTION value="7" <% if 7 = MaschinenRS("Loeschennach") then %>selected<% end if %>>1 Woche</OPTION>
								<OPTION value="14" <% if 14 = MaschinenRS("Loeschennach") then %>selected<% end if %>>2 Wochen</OPTION>
								<OPTION value="21" <% if 21 = MaschinenRS("Loeschennach") then %>selected<% end if %>>3 Wochen</OPTION>
								<OPTION value="30" <% if 30 = MaschinenRS("Loeschennach") then %>selected<% end if %>>1 Monat</OPTION>
								<OPTION value="60" <% if 60 = MaschinenRS("Loeschennach") then %>selected<% end if %>>2 Monate</OPTION>
								<OPTION value="36500" <% if 36500 = MaschinenRS("Loeschennach") then %>selected<% end if %>>unbefristet</OPTION>
							</SELECT>
						</TD>
					</TR>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<TR>
						<TD></TD>
						<td align="left"><input type="submit" name="aktualisieren" value="Aktualisieren">
						</td>
					</TR>
				</TABLE>
			</FORM>
			</td>
			</tr>
			<tr>
			  <td colspan="3"><b><%= Status %></b></td>
			</tr>
		<tr>
		<td colspan="3">	
		<% MaschinenRS.close %>	
		<% MaschinenRubrikRS.close %>
		<!-- #include file="menubarMaschinen.asp" -->
	</td>
	</tr>
		
</table>
</BODY>
</HTML>


<!-- #include file="include/cleanup.inc" -->
<!-- #include file="include/maschinen/maschinensub.inc" -->