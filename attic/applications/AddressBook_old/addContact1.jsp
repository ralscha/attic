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
	<td align="right"><FONT face="Verdana, Arial, Helvetica, sans-serif"><b>1. Seite</b>&nbsp;/&nbsp;<a href="addContact2.html">2. Seite</a></FONT></td>
</tr>
</table>
</TD>
</TR>
<TR>
<TD bgcolor="#EEEEEE">
<TABLE border="0" cellpadding="3" cellspacing="0" width="100%">
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Vorname</FONT></TD>
		<TD><struts:text name="firstname" size="30"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Nachname</FONT></TD>
		<TD><struts:text name="name" size="30"/></TD>
	</TR>
	<TR>
		<TD height="35"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Firma</FONT></TD>
		<TD height="35"><struts:text name="company" size="35"/></TD>
	</TR>
	<TR>
		<TD valign="top"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Adresse</FONT></TD>
		<TD><struts:textarea name="address" maxlength="500" size="35"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">PLZ&nbsp;/&nbsp;Ort</FONT></TD>
		<TD>
			<struts:text name="plz" size="8"/>
			<struts:text name="city" size="35"/>
		</TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Land</FONT></TD>
		<TD><struts:text name="country" size="25"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Email</FONT></TD>
		<TD><struts:text name="email" size="25"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Mobiltelefon</FONT></TD>
		<TD><struts:text name="mobilephone" size="20"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Telefon&nbsp;Gesch&auml;ft</FONT></TD>
		<TD><struts:text name="workphone" size="20"/></TD>
	</TR>
	<TR>
		<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="-1">Telefon&nbsp;privat</FONT></TD>
		<TD><struts:text name="homephone" size="20"/></TD>
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
<INPUT name="add" type="submit" value="Abspeichern"></FONT></FONT>
<FONT face="Verdana, Arial, Helvetica, sans-serif"><FONT size="-1">
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
