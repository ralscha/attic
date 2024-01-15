<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/openmitarbeiterrubriken.inc" --> 
<!-- #include file="include/mitarbeiter/insertmitarbeiter.inc" -->

<HTML>
<HEAD>
<TITLE>Mitarbeiter aufgeben</TITLE>
<script language="JavaScript" src="check.js"></script>
</HEAD>
<BODY>



<TABLE border="0" cellpadding="10" cellspacing="0" width="600" bgcolor="#FFFFFF">
	<TR>
		<TD></TD>
		<TD><B><%= SucheBiete(Request("suchebiete")) %> Mitarbeiter aufgeben</B><BR> <BR></TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>	
		
			<FORM name="mail" method="POST" action="mitarbeiter_aufgeben.asp?suchebiete=<%= Request("suchebiete")%>" onSubmit="return chkFM(this)">
				<TABLE border="0">
					<TR>
						<TD colspan="2">
							<TABLE border="0">
								<TR>
									<TD><IMG src="images/finger.gif" border="0"></TD>
									<TD></TD>
									<TD>Anzeige aufgeben</TD>
								</TR>
							</TABLE><BR> <BR>
						</TD>
					</TR>				
					<TR>
						<TD>Name:</TD>
						<TD><INPUT type="text" name="username" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>E-Mail:</TD>
						<TD><INPUT type="text" name="email" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Telefon:</TD>
						<TD><INPUT type="text" name="tel" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Passwort:</TD>
						<TD><INPUT type="text" name="pw" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>Titel:</TD>
						<TD><INPUT type="text" name="titel" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD>g&uuml;ltig bis:</TD>
						<TD><INPUT type="text" name="gueltig" size="45" maxlength="50"></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD><textarea cols="43" rows="7" name="com" wrap="soft"></textarea></TD>
					</TR>
					<TR>
						<TD valign="top">Rubrik:</TD>
						<TD>
							<SELECT name="rubrik" size="1">
							  <% do until MitarbeiterRubrikRS.eof %>
								<OPTION value="<%=MitarbeiterRubrikRS("ID")%>"><%= MitarbeiterRubrikRS("Beschreibung")%></OPTION>
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
								<OPTION value="7">1 Woche</OPTION>
								<OPTION value="14">2 Wochen</OPTION>
								<OPTION value="21">3 Wochen</OPTION>
								<OPTION value="30">1 Monat</OPTION>
								<OPTION value="60">2 Monate</OPTION>
								<OPTION value="36500">unbefristet</OPTION>
							</SELECT>
						</TD>
					</TR>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<TR>
						<TD></TD>
						<td align="left"><input type="submit" name="eintragen" value="Ver&ouml;ffentlichen ...">
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
		<% MitarbeiterRubrikRS.close %>	
		<!-- #include file="menubarMitarbeiter.asp" -->
	</td>
	</tr>
		
</table>
</BODY>
</HTML>
<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/mitarbeiter/mitarbeitersub.inc" -->