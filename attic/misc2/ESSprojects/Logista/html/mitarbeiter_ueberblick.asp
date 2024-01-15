<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/mitarbeiter/initmitarbeiter.inc" -->
<HTML>
<HEAD>
<TITLE>Mitarbeiter Ueberblick</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
</HEAD>
<BODY>
<h3 align="center"><%= SucheBiete(Request("suchebiete")) %> Mitarbeiter Ueberblick </h3>
<table width="160" border="0" cellpadding="0" align="center">
<TR>
  
<TD valign="top" rowspan="<%= totalRubriken() %>">Hier k&ouml;nnen Sie Ihre Mitarbeiter anderen Firmen
anbieten.
<P><B>Wichtig:</B>Vergewissern Sie sich bitte, dass Sie die richtige Rubrik gew&auml;hlt haben, bevor Sie eine neue Anzeige
ver&ouml;ffentlichen.</P>
   
   </TD>
   <td width="10">
   <td>
   
    <!-- #include file="include/mitarbeiter/openmitarbeiterrubriken.inc" -->  
   <% dim totalRubrik 
      dim totalNeuRubrik
      if not MitarbeiterRubrikRS.eof then 
        totalRubrik = totalMitarbeiterRubrikAnzeigen(MitarbeiterRubrikRS("ID"), Request("suchebiete"))
		if totalRubrik = 0 then %>
     		<IMG src="images/demo1.gif" border="0"><B><%= MitarbeiterRubrikRS("Beschreibung") %></B> (0)
        <% else %>		
        <%totalNeuRubrik = totalNeueAnzeigenRubrik(MitarbeiterRubrikRS("ID"), Request("suchebiete"))%>
	    	<A href="mitarbeiter_detail.asp?suchebiete=<%=Request("suchebiete")%>&rubrikid=<%= MitarbeiterRubrikRS("ID") %>"><IMG src="images/demo.gif" border="0"></A><B><%= MitarbeiterRubrikRS("Beschreibung") %></B> (<% = totalRubrik %><% if totalNeuRubrik > 0 then %>&nbsp;<font color="Red">|&nbsp;<%=totalNeuRubrik%>&nbsp;neu</font><% end if %>)
	    <% end if 
        MitarbeiterRubrikRS.moveNext %>
		</td>
     <% else %>
   
   <TD nowrap></TD>
   
   <% end if %>
   
</TR>

<% do until MitarbeiterRubrikRS.eof %>
        <tr>
        <td width="10"></td>
		<td nowrap>
		<%
        totalRubrik = totalMitarbeiterRubrikAnzeigen(MitarbeiterRubrikRS("ID"), Request("suchebiete"))
		if totalRubrik = 0 then %>
     		<IMG src="images/demo1.gif" border="0"><B><%= MitarbeiterRubrikRS("Beschreibung") %></B> (0)
        <% else %>	
        <%totalNeuRubrik = totalNeueAnzeigenRubrik(MitarbeiterRubrikRS("ID"), Request("suchebiete"))%>	
	    	<A href="mitarbeiter_detail.asp?suchebiete=<%=Request("suchebiete")%>&rubrikid=<%= MitarbeiterRubrikRS("ID") %>"><IMG src="images/demo.gif" border="0"></A><B><%= MitarbeiterRubrikRS("Beschreibung") %></B> (<% = totalRubrik %><% if totalNeuRubrik > 0 then %>&nbsp;<font color="Red">|&nbsp;<%=totalNeuRubrik%>&nbsp;neu</font><% end if %>)
	    <% end if %>
		</td>
        </tr>
<% 
   MitarbeiterRubrikRS.moveNext
   loop 
   MitarbeiterRubrikRS.close
%>

</TABLE>
<BR><BR>
<!-- #include file="menubarMitarbeiter.asp" -->

</BODY>
</HTML>

<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/mitarbeiter/mitarbeitersub.inc" -->
   

