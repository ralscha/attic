<jsp:useBean id="rekrutierung" scope="session" class="ch.ess.pbroker.session.Rekrutierung"/>

<table>
<tr>
<td colspan="2" align="left" class="texti">Anforderungen an Mitarbeiter</td>
</tr>
<tr>
	<td align="left" class="text">T&auml;tigkeitsgebiete:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="taetigkeitsgebiete" /></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Skills:</td>
	<td class="textb">
	<b><struts:htmlProperty name="rekrutierung" property="skills" /></td>
</tr>
<tr>
	<td align="left" class="text">Pensum:</td>
	<td class="textb"><b><struts:htmlProperty name="rekrutierung" property="pensum" /></b></td>
</tr>

<tr>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td colspan="2" align="left"><i>Einsatz</i></td>
</tr>
<tr>
	<td align="left" class="text">Projekt/Bereich:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="projekt"/></td>
</tr>
<tr>
	<td align="left" class="text">Hauptaufgaben:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="aufgaben"/></td>
</tr>
<tr>
	<td align="left"  class="text">Termin Von - Bis:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="vontermin"/>&nbsp;-&nbsp;<struts:htmlProperty name="rekrutierung" property="vontermin"/></td>
</tr>
<tr>
	<td align="left" valign="top" class="text">Bemerkung:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="bemerkung"/></td>
</tr>
<tr>
	<td align="left" class="text">Ansprechpartner:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprech"/></td>
</tr>
<tr>
	<td align="left" class="text">Telefon:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprechtel"/></td>
</tr>
<tr>
	<td align="left" class="text">E-Mail:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="ansprechemail"/></td>
</tr>
<tr>
	<td align="left" class="text">OE:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="oe"/></td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<tr>
	<td align="left" class="text">Offerte bis:</td>
	<td class="textb"><struts:htmlProperty name="rekrutierung" property="offertebis"/></td>
</tr>
</table>

