<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/logincheck.inc" -->

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->

<!-- #include file="include/maschinen/initmaschinen.inc" -->

<HTML>
<HEAD>
<TITLE>Maschinen Ueberblick</TITLE>
<META HTTP-EQUIV="Expires" CONTENT="Mon, 06 Jan 1990 00:00:01 GMT"> 
</HEAD>
<BODY>
<h3 align="center">Maschinen Ueberblick </h3>
<table width="160" border="0" cellpadding="0" align="center">
<TR>
   <TD valign="top" rowspan="<%= totalRubriken() %>">Hier k&ouml;nnen Sie Spezialwerkzeuge/Maschinen anbieten oder finden.
   <P><B>Wichtig:</B>Vergewissern Sie sich bitte, dass Sie die richtige Rubrik gew&auml;hlt haben, bevor Sie eine neue Anzeige ver&ouml;ffentlichen.</P>
   </TD>
   <td width="10">
   <td>
   
 <!-- #include file="include/maschinen/openmaschinenrubriken.inc" -->  
 
   <% dim totalRubrik
      dim totalNeuRubrik  
      if not MaschinenRubrikRS.eof then 
        totalRubrik = totalMaschinenRubrikAnzeigen(MaschinenRubrikRS("ID"))
		if totalRubrik = 0 then %>
     		<IMG src="images/demo1.gif" border="0"><B><%= MaschinenRubrikRS("Beschreibung") %></B> (0)
        <% else %>		
         <%totalNeuRubrik = totalNeueAnzeigenRubrik(MaschinenRubrikRS("ID"))%>
	    	<A href="maschinen_detail.asp?rubrikid=<%= MaschinenRubrikRS("ID") %>"><IMG src="images/demo.gif" border="0"></A><B><%= MaschinenRubrikRS("Beschreibung") %></B> (<% = totalRubrik %><% if totalNeuRubrik > 0 then %>&nbsp;<font color="Red">|&nbsp;<%=totalNeuRubrik%>&nbsp;neu</font><% end if %>)
	    <% end if 
        MaschinenRubrikRS.moveNext %>
		</td>
     <% else %>
   
   <TD nowrap></TD>
   
   <% end if %>
   
</TR>

<% do until MaschinenRubrikRS.eof %>
        <tr>
        <td width="10"></td>
		
		<td nowrap>
		<% 
        totalRubrik = totalMaschinenRubrikAnzeigen(MaschinenRubrikRS("ID"))
		  if totalRubrik = 0 then %>
     		<IMG src="images/demo1.gif" border="0"><B><%= MaschinenRubrikRS("Beschreibung") %></B> (0)
     <% else %>		
        <%totalNeuRubrik = totalNeueAnzeigenRubrik(MaschinenRubrikRS("ID"))%>
	    	<A href="maschinen_detail.asp?rubrikid=<%= MaschinenRubrikRS("ID") %>"><IMG src="images/demo.gif" border="0"></A><B><%= MaschinenRubrikRS("Beschreibung") %></B> (<% = totalRubrik %><% if totalNeuRubrik > 0 then %>&nbsp;<font color="Red">|&nbsp;<%=totalNeuRubrik%>&nbsp;neu</font><% end if %>)
	  
	  <% end if %>
		</td>
        </tr>
<% 
   MaschinenRubrikRS.moveNext
   loop 
   MaschinenRubrikRS.close 
%>

</TABLE>
<BR><BR>
<!-- #include file="menubarMaschinen.asp" -->

</BODY>
</HTML>
<!-- #include file="include/cleanup.inc" -->

<!-- #include file="include/maschinen/maschinensub.inc" -->
   
