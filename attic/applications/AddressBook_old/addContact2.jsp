<%@ page language="java" info="AddContact"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<HTML>
<HEAD>
<TITLE>Kontakt hinzuf&uuml;gen</TITLE>
</HEAD>
<BODY alink="#0000ff" vlink="#0000ff" bgcolor="#ffffff">
<TABLE width="550" border="0" cellspacing="0" cellpadding="0">
<TR>
<TD><P>&nbsp;</P></TD>
</TR>
<TR bordercolor="#FFFFFF" bgcolor="#F0F0F0">
<TD valign="middle">
<P><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1"><B>Adressbuch f&uuml;r: Ralph</B></FONT></P></TD>
</TR>
<TR>
<TD><P>&nbsp;</P></TD>
</TR>
<TR>
<TD valign="top">
<struts:form action="addContact.do" name="contactForm" type="ch.ess.addressbook.form.ContactForm">
<TABLE cellpadding="0" cellspacing="0" border="0" align="center" width="100%">
<TR>
<TD valign="top">
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<TR bgcolor="#999999">
<TD>
<TABLE cellpadding="3" cellspacing="1" border="0" width="100%">
<TR>
<TD align="left" bgcolor="#DCDCDC">


<table width="100%">
<tr>
	<td><FONT face="Verdana, Arial, Helvetica, sans-serif"><B>Kontakt hinzuf&uuml;gen</B></FONT></td>
	<td align="right"><FONT face="Verdana, Arial, Helvetica, sans-serif"><a href="addContact1.html">1. Seite</a>&nbsp;/&nbsp;<b>2. Seite</b></FONT></td>
</tr>
</table>



</TD>
</TR>
<TR>
<TD bgcolor="#EEEEEE">
<TABLE border="0" cellpadding="3" cellspacing="0" width="100%">
	
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1"><struts:text name="key1" size="20"/></TD>
		<TD><struts:text name="value1" size="20"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1"><struts:text name="key2" size="20"/></TD>
		<TD><struts:text name="value2" size="20"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1"><struts:text name="key3" size="20"/></TD>
		<TD><struts:text name="value3" size="20"/></TD>
	</TR>
	<tr><td colspan="2">&nbsp;</td></tr>
	<TR>
		<TD valign="top"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Notiz</FONT></TD>
		<TD><struts:textarea name="notes" cols="35" rows="10"/></TD>
	</TR>
</TABLE>
</TD>
</TR>
</TABLE>
</TD>
</TR>
</TABLE>
<P>
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<TR bgcolor="#999999">
<TD>
<TABLE cellpadding="3" cellspacing="1" border="0" width="100%">
<TR bgcolor="#F4F5E1">
<TD><FONT face="Verdana, Arial, Helvetica, sans-serif"><FONT size="-1">
<INPUT name="add" type="submit" value="Abspeichern"></FONT></FONT><FONT face="Verdana, Arial, Helvetica, sans-serif"><FONT size="-1">
<INPUT name="cancel" type="submit" value="Abbrechen"></FONT></FONT>
</TD>
</TR>
</TABLE>
</TD>
</TR>
</TABLE>
</TD>
</TR>
</TABLE>
</FORM>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>
