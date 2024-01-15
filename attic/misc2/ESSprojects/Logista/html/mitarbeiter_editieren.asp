<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/selectmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/updatemitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/openmitarbeiterrubriken.inc" --> 

<HTML>
<HEAD>
<TITLE>Mitarbeiter aktualisieren</TITLE>
<script language="JavaScript" src="check.js"></script>
</HEAD>
<BODY>



<TABLE border="0" cellpadding="10" cellspacing="0" width="600" bgcolor="#FFFFFF">
	<TR>
		<TD></TD>
		<TD><B><%= SucheBiete(Request("suchebiete")) %> Mitarbeiter Anzeige editieren</B><BR> <BR></TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>	
		
			<FORM name="mail" method="POST" action="mitarbeiter_editieren.asp?suchebiete=<%= Request("suchebiete")%>&mitarbeiterid=<%= Request("mitarbeiterid")%>" onSubmit="return chkFM(this)">
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
						<TD><input type="text" name="username" value="<%= MitarbeiterRS("Name") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>E-Mail:</TD>
						<TD><input type="text" name="email" value="<%= MitarbeiterRS("Email") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Telefon:</TD>
						<TD><input type="text" name="tel" value="<%= MitarbeiterRS("Telefon") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Passwort:</TD>
						<TD><input type="text" name="pw" value="<%= MitarbeiterRS("Passwort") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Titel:</TD>
						<TD><input type="text" name="titel" value="<%= MitarbeiterRS("Titel") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>g&uuml;ltig bis:</TD>
						<TD><input type="text" name="gueltig" value="<%= MitarbeiterRS("Gueltigbis") %>" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD><textarea cols="43" rows="7" name="com"><%= ConvertBeschreibung(MitarbeiterRS("Beschreibung"), false)  %></textarea></TD>
					</TR>
					<TR>
						<TD valign="top">Rubrik:</TD>
						<TD>
							<SELECT name="rubrik" size="1">
							  <% do until MitarbeiterRubrikRS.eof %>
								<OPTION value="<%=MitarbeiterRubrikRS("ID")%>" <% if MitarbeiterRubrikRS("ID") = MitarbeiterRS("MitarbeiterRubrik") then %>selected<% end if %>><%= MitarbeiterRubrikRS("Beschreibung")%></OPTION>
							  <% 
								 MitarbeiterRubrikRS.moveNext
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
								<OPTION value="7" <% if 7 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>1 Woche</OPTION>
								<OPTION value="14" <% if 14 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>2 Wochen</OPTION>
								<OPTION value="21" <% if 21 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>3 Wochen</OPTION>
								<OPTION value="30" <% if 30 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>1 Monat</OPTION>
								<OPTION value="60" <% if 60 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>2 Monate</OPTION>
								<OPTION value="36500" <% if 36500 = MitarbeiterRS("Loeschennach") then %>selected<% end if %>>unbefristet</OPTION>
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
		<% MitarbeiterRS.close %>	
		<% MitarbeiterRubrikRS.close %>
		<!-- #include file="menubarMitarbeiter.asp" -->
	</td>
	</tr>
		
</table>
</BODY>
</HTML>
<!-- #include file="include/cleanup.inc" -->
<!-- #include file="include/mitarbeiter/mitarbeitersub.inc" -->