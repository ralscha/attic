<%@ page info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>


<% String menu = request.getParameter("m"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>Menu</TITLE>
<script language="JavaScript" type="text/javascript">
<!--
  window.onerror = null;
	browserName=navigator.appName;
	version="";
	browserVer=parseInt(navigator.appVersion);

	if(browserName=="Netscape" && browserVer >=3) version ="n3";
	if(browserName=="Microsoft Internet Explorer" && browserVer >=4) version="n3";
	if (version=="n3")
	{       
	<!-- BUTTON CODES GO HERE -->

        abmelden_on=new Image(111, 22);
        abmelden_on.src="mi/abmelden_down.gif";
        abmelden_off=new Image(111, 22);
        abmelden_off.src="mi/abmelden_up.gif";

        reporting_on=new Image(111, 22);
        reporting_on.src="mi/reporting_down.gif";
        reporting_off=new Image(111, 22);
        reporting_off.src="mi/reporting_up.gif";

        dienste_on=new Image(111, 22);
        dienste_on.src="mi/dienste_down.gif";
        dienste_off=new Image(111, 22);
        dienste_off.src="mi/dienste_up.gif";

        zeitleistung_on=new Image(111, 22);
        zeitleistung_on.src="mi/zeitleistung_down.gif";
        zeitleistung_off=new Image(111, 22);
        zeitleistung_off.src="mi/zeitleistung_up.gif";

        vertraege_on=new Image(111, 22);
        vertraege_on.src="mi/vertraege_down.gif";
        vertraege_off=new Image(111, 22);
        vertraege_off.src="mi/vertraege_up.gif";

        rekrutierung_on=new Image(111, 22);
        rekrutierung_on.src="mi/rekrutierung_down.gif";
        rekrutierung_off=new Image(111, 22);
        rekrutierung_off.src="mi/rekrutierung_up.gif";

	}


  function go(URL1, F1, URL2,F2, URL3, F3)
   {  
    parent.frames[F1].location.href=URL1;
    parent.frames[F2].location.href=URL2;
	parent.frames[F3].location.href=URL3;
   }
   function go2(URL1, F1, URL2,F2)
   {  
    parent.frames[F1].location.href=URL1;
    parent.frames[F2].location.href=URL2;
   }

//-->
</script>
</HEAD>
<BODY background="images/navi_nob.gif" leftMargin=0 
style="MARGIN-LEFT: 0px; MARGIN-TOP: 0px" topMargin=0 marginheight="0" 
marginwidth="0">
<TABLE border=0 cellPadding=0 cellSpacing=0 width=160>
  <TR>
    <TD align=center><IMG border=0 height=50 
      src="images/x.gif" width=120></TD></TR>
  <% if (!"s".equals(menu)) { %>	  
  <TR>    
    <TD align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp?m=s', 1, 'searchdescr.html',2)"><img src="mi/rekrutierung_up.gif" width="111" height="22" border="0" alt="Rekrutierung" name="rekrutierung"></a></TD>
  </TR>
  <% } else { %>
  <TR>
    <TD align=center><A HREF="javascript:go('frames/top_step_nop.html',0,'menu.jsp', 1, 'main.html',2)"><img src="mi/rekrutierung_down.gif" width="111" height="22" border="0" alt="Rekrutierung" name="rekrutierung"></A></TD>
  </TR> 
  <TR>
    <TD align=center><A HREF="javascript:go2('frames/top_step_nop.html',0, 'description.html',2)"><IMG BORDER="0" SRC="mi/sub_suche.gif" ALT="Suche" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="javascript:go2('frames/top_step_nop.html',0, 'descriptionofferten.html',2)"><IMG BORDER="0" SRC="mi/sub_offerten.gif" ALT="Offerte" width="120" height="23"></A></TD></TR>
  <TR><TD align=center><IMG border=0 height=10 src="images/x.gif" width=120></TD></TR>     
  <% } %>
    <TD align=center><IMG border=0 height=5
      src="images/x.gif" width=120></TD></TR>
  <% if (!"v".equals(menu)) { %>    
    <TR>
    <TD align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp?m=v', 1, 'vertragdescr.html',2)"><img src="mi/vertraege_up.gif" width="111" height="22" border="0" alt="Verträge" name="vertraege"></A></TD>
	</TR>
  <% } else { %>
    <TR>
    <TD align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp', 1, 'main.html',2)"><img src="mi/vertraege_down.gif" width="111" height="22" border="0" alt="Verträge" name="vertraege"></A></TD>
   <TR>
     <TD ALIGN="center"><A HREF="#"><IMG BORDER="0" SRC="mi/sub_neu.gif" ALT="Neu" width="120" height="23"></A></TD></TR>
   <TR>
     <TD ALIGN="center"><A HREF="#"><IMG BORDER="0" SRC="mi/sub_verlaengerung.gif" ALT="Verlängerung" width="120" height="23"></A></TD></TR>
   <TR>
     <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_ersatz.gif" ALT="Ersatz" width="120" height="23"></A></TD></TR>
   <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_aufloesung.gif" ALT="Aufl&ouml;sung" width="120" height="23"></A></TD></TR>
   <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_archiv.gif" ALT="Archiv" width="120" height="23"></A></TD></TR>

	<TR><TD align=center><IMG border=0 height=10 src="images/x.gif" width=120></TD></TR>   
  <% } %>
     <TD align=center><IMG border=0 height=5
      src="images/x.gif" width=120></TD></TR>
  <% if (!"z".equals(menu)) { %>    
    <TR>
    <TD align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp?m=z', 1, 'zldescr.html',2)"><img src="mi/zeitleistung_up.gif" width="111" height="22" border="0" alt="Zeit/Leistung" name="Zeit/Leistung"></A></TD>
	</TR>
  <% } else { %>
    <TR>
    <TD align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp', 1, 'main.html',2)"><img src="mi/zeitleistung_down.gif" width="111" height="22" border="0" alt="Zeit/Leistung" name="Zeit/Leistung"></A></TD>
    </tr>
   <% } %>
  
  <TD align=center><IMG border=0 height=5 
      src="images/x.gif" width=120></TD></TR>
  <% if (!"r".equals(menu)) { %>    
  <TR>
    <td align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp?m=r', 1, 'reportingdescr.html',2)"><img src="mi/reporting_up.gif" width="111" height="22" border="0" alt="Dienste" name="dienste"></a></td>
  </TR>
  <% } else { %>
  <TR>
    <td align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp', 1, 'main.html',2)"><img src="mi/reporting_down.gif" width="111" height="22" border="0" alt="Dienste" name="dienste"></a></td>  
   <TR>
    <TD align=center><A HREF="javascript:go2('frames/top_step_nop.html',0, 'personalkennzahlen.html',2)"><IMG BORDER="0" SRC="mi/sub_personal.gif" ALT="Personalkennzahlen" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_finanz.gif" ALT="Finanzkennzahlen" width="120" height="23"></A></TD></TR>
  <TR><TD align=center><IMG border=0 height=10 src="images/x.gif" width=120></TD></TR>     
  <% } %>
  <TR>
  
      <TD align=center><IMG border=0 height=5 
      src="images/x.gif" width=120></TD></TR>
  <% if (!"d".equals(menu)) { %>    
  <TR>
    <td align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp?m=d', 1, 'dienstedescr.html',2)"><img src="mi/dienste_up.gif" width="111" height="22" border="0" alt="Dienste" name="dienste"></a></td>
  </TR>
  <% } else { %>
  <TR>
    <td align=center><a href="javascript:go('frames/top_step_nop.html',0,'menu.jsp', 1, 'main.html',2)"><img src="mi/dienste_down.gif" width="111" height="22" border="0" alt="Dienste" name="dienste"></a></td>  
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_beschwerde.gif" ALT="Beschwerde" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_feedback.gif" ALT="Feedback" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_hotline.gif" ALT="Feedback" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_info.gif" ALT="Information" width="120" height="23"></A></TD></TR>
  <TR>
    <TD align=center><A HREF="#"><IMG BORDER="0" SRC="mi/sub_akkreditierung.gif" ALT="Akkreditierung" width="120" height="23"></A></TD></TR>

	
  <% } %>
  <TR>
    <TD align=center><IMG border=0 height=50 
      src="images/x.gif" width=120></TD></TR>
  <TR>
    <TD align=center><struts:link href="logoff.do" target="_top"><img src="mi/abmelden_up.gif" width="111" height="22" border="0" alt="Abmelden" name="abmelden"></struts:link>

</TD></TR></TABLE></BODY></HTML>

