<TABLE border="0" bgcolor="#E6E6E6" cellspacing="0" cellpadding="0" width="100%">
				<TR>
					<TD></TD>
					<TD align="center">Total <%= totalMitarbeiterRubrikAnzeigen(Request("rubrikid"), Request("suchebiete")) %> Anzeige(n) in dieser Rubriken  |  <A href="mitarbeiter_aufgeben.asp?suchebiete=<%= Request("suchebiete")%>">aufgeben</A> | <A href="mitarbeiter_ueberblick.asp?suchebiete=<%= Request("suchebiete")%>" target="main">&Uuml;bersicht</A>
					</TD>
					<TD></TD>
				</TR>
</table>
