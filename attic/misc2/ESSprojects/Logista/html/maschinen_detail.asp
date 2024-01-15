<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/maschinen/initmaschinen.inc" -->
<!-- #include file="include/maschinen/openmaschinenrubriken.inc" --> 

<HTML>
<HEAD>
<TITLE>Maschinen Detail</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
<script language="JavaScript" src="check.js"></script>
</HEAD>
<BODY>
<BR>

<TABLE border="0" cellpadding="10" cellspacing="0" width="600" bgcolor="#FFFFFF">
	<TR>
		<TD></TD>
		<TD><B>Maschinen Pool</B></TD>
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
						<FORM method="POST" action="maschinen_detail.asp">

							<SELECT name="links" size="1" onChange="jump(this.options[this.selectedIndex].value)">
							    <option selected>zur Bereichs&uuml;bersicht</option>
							<% do until MaschinenRubrikRS.eof %>
								<OPTION value="/logista/maschinen_detail.asp?rubrikid=<%= MaschinenRubrikRS("ID") %>"><%= MaschinenRubrikRS("Beschreibung") %></OPTION>
							<% 
  								 MaschinenRubrikRS.moveNext
							   loop 	
							%>	
							</SELECT>
						</FORM>
					</TD>
					<TD></TD>
				</TR>
			</TABLE><BR> <BR>
			
			
			<TABLE border="0" cellspacing="0" cellpadding="5" width="100%">
			
			<!-- #include file="include/maschinen/maschinendetailloopinit.inc" -->

			<% if MaschinenRS.eof then %>
			   <tr>
			   <td colspan="2" valign="top" bgcolor="#F9F9F9"><B>keinze Anzeigen in dieser Rubrik</B></td>
			   </tr> 
			  
			<% end if %>
			
			<% do until MaschinenRS.eof %>
			
				<TR>
					<TD valign="top" bgcolor="#f9f9f9"><B><%= MaschinenRS("Titel") %></B></TD>
					<TD valign="top" bgcolor="#f9f9f9" align="right"><%= FormatDateTime(MaschinenRS("DatumErstellung"),2) %>&nbsp;<%= FormatDateTime(MaschinenRS("DatumErstellung"),4) %>&nbsp;(<A href="maschinen_loeschen.asp?maschinenid=<%= MaschinenRS("ID") %>" target="main">l&ouml;schen</A>) 
					<% if Session("Profil") = "A" then %>(<A href="maschinen_editieren.asp?maschinenid=<%= MaschinenRS("ID") %>" target="main">editieren</A>)<% end if %></TD>
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
				
			    <!-- #include file="include/maschinen/maschinendetailloopend.inc" -->
				
			</TABLE>
		</TD>
		<TD></TD>
	</TR>
	<TR>
		<TD></TD>
		<TD>
			<!-- #include file="menubarMaschinenDetail.asp" -->
		</TD>
		<TD>			 
		</TD>
	</TR>
	
</TABLE>



</BODY>
</HTML>
<% MaschinenRubrikRS.close %>
<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/maschinen/maschinensub.inc" -->