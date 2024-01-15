<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<!-- #include file="include/mitarbeiter/openmitarbeiterrubriken.inc" --> 

<HTML>
<HEAD>
<TITLE>Mitarbeiter Detail</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
<script language="JavaScript" src="check.js"></script>
</HEAD>
<BODY>
<BR>

<TABLE border="0" cellpadding="10" cellspacing="0" width="600" bgcolor="#FFFFFF">
	<TR>
		<TD></TD>
		<TD><B><%= SucheBiete(Request("suchebiete")) %> Mitarbeiter Pool</B></TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>
			<TABLE border="0" width="100%">
				<TR>
					<TD colspan="2"><IMG src="images/finger.gif" border="0"></TD>
					<TD><B><%= rubrikBeschreibung(Request("rubrikid")) %></B></TD>
					<TD align="right">
						<FORM method="POST" action="mitarbeiter_detail.asp">

							<SELECT name="links" size="1" onChange="jump(this.options[this.selectedIndex].value)">
							    <option selected>zur Bereichs&uuml;bersicht</option>
							<% do until MitarbeiterRubrikRS.eof %>
								<OPTION value="mitarbeiter_detail.asp?suchebiete=<%=Request("suchebiete")%>&rubrikid=<%= MitarbeiterRubrikRS("ID") %>"><%= MitarbeiterRubrikRS("Beschreibung") %></OPTION>
							<% 
  								 MitarbeiterRubrikRS.moveNext
							   loop 	
							%>	
							</SELECT>
						</FORM>
					</TD>
					<TD></TD>
				</TR>
			</TABLE><BR> <BR>
			
			
			<TABLE border="0" cellspacing="0" cellpadding="5" width="100%">
			
			<!-- #include file="include/mitarbeiter/mitarbeiterdetailloopinit.inc" -->

			<% if MitarbeiterRS.eof then %>
			   <tr>
			   <td colspan="2" valign="top" bgcolor="#F9F9F9"><B>keinze Anzeigen in dieser Rubrik</B></td>
			   </tr> 
			  
			<% end if %>
			
			<% do until MitarbeiterRS.eof %>
			
				<TR>
					<TD valign="top" bgcolor="#f9f9f9"><B><%= MitarbeiterRS("Titel") %></B></TD>
					<TD valign="top" bgcolor="#f9f9f9" align="right"><%= FormatDateTime(MitarbeiterRS("DatumErstellung"),2) %>&nbsp;<%= FormatDateTime(MitarbeiterRS("DatumErstellung"),4) %>&nbsp;(<A href="mitarbeiter_loeschen.asp?suchebiete=<%= Request("suchebiete") %>&mitarbeiterid=<%= MitarbeiterRS("ID") %>" target="main">l&ouml;schen</A>)
					<% if Session("Profil") = "A" then %>(<A href="mitarbeiter_editieren.asp?suchebiete=<%= Request("suchebiete") %>&mitarbeiterid=<%= MitarbeiterRS("ID") %>" target="main">editieren</A>)<% end if %></TD>
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
				
			    <!-- #include file="include/mitarbeiter/mitarbeiterdetailloopend.inc" -->
				
			</TABLE>
		</TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>
			<!-- #include file="menubarMitarbeiterDetail.asp" -->
		</TD>
		<TD>			 
		</TD>
	</TR>
	
</TABLE>



</BODY>
</HTML>
<% MitarbeiterRubrikRS.close %>
<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/mitarbeiter/mitarbeitersub.inc" -->