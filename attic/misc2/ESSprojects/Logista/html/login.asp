<%@ Language=VBScript %>
<% option explicit %>

<!-- #include file="include/adovbs.inc" -->
<!-- #include file="include/init.inc" -->
<!-- #include file="include/benutzercheck.inc" -->


<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY onLoad="document.forms[0].benutzer.focus()">

<CENTER>
<B>Bitte geben Sie &nbsp;Ihren Benutzernamen und Ihr Passwort ein.</B>
</CENTER>

<P>
<FORM action="login.asp" method="POST">
</P>

<CENTER>
<P>	<TABLE cellspacing="1" width="170" border="0">
		<TR>
			<TD>Benutzername:</TD>
			<TD><input type="text" name="benutzer" size="20" maxlength="30"></TD>
		</TR>
		<TR>
			<TD>Passwort:</TD>
			<TD><input type="password" name="passwort" size="20" maxlength="30"></TD>
		</TR>
		<TR>
			<td colspan="2" align="center" valign="middle">
			<INPUT type="submit" name ="Login" value="Login">
			</td>
		</TR>
	</TABLE>
	</P>
<P>

<b><%= Status %></b>

</CENTER>
</FORM>
</BODY>
</HTML>
<!-- #include file="include/cleanup.inc" -->