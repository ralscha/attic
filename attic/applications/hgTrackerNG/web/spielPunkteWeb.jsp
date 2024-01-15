<%@ page language="java" session="false"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>HGVerwaltung: Punkte</title>
		<link rel="stylesheet" type="text/css" href="default.css">
	</head>

	<body>
		<table cellspacing="2" cellpadding="2" border="0" class="noBorder">
			<tr>
				<td>
					<table cellspacing="2" cellpadding="2" border="0" class="border">
						<tr>
							<td colspan="2">
								<h3>${spielForm.spielArt}</h3>
							</td>
						</tr>
						<tr>
							<th align="left">Kampfrichter:&nbsp;</th>
							<td>${spielForm.kampfrichter}</td>
						</tr>
						<tr>
							<th align="left">Beschreibung:&nbsp;</th>
							<td>${spielForm.kampfrichterGegner}</td>
						</tr>
						<tr>
							<th align="left">Gegner:&nbsp;</th>
							<td>${spielForm.gegner}</td>
						</tr>
						<tr>
							<th align="left">Datum:&nbsp;</th>
							<td>${spielForm.datum}</td>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<table cellspacing="2" cellpadding="2" border="0" class="border">
						<tr>
							<th align="center" width="50" valign="top">
								Total<br />Nr.
							</th>
							<th align="center" width="50" valign="top">
								Schlag<br />Punkte
							</th>
							<th align="center" width="50">
								Total<br />Nr.<br />Gegner
							</th>
							<th align="center" width="50">
								Schlag<br />Punkte<br />Gegner
							</th>
						</tr>
						<tr>
							<td align="center">
								${spielForm.totalNrHeim}
							</td>
							<td align="center">
								${result.total}
							</td>
							<td align="center">
								${spielForm.totalNr}
							</td>
							<td align="center">
								${spielForm.schlagPunkteGegner}
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p>
			<display:table export="false" class="list" id="row" name="result.punkteAnzeige">
				<display:column title="" property="reihenfolge" />
				<display:column title="Nachname" property="nachname" width="95" />
				<display:column title="Vorname" property="vorname" width="80" />
				<display:column title="Jahrgang" property="jahrgang" />
				<display:column title="1.Ries" align="right">
					<span <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries1}</span>
				</display:column>
				<display:column title="2.Ries" align="right">
					<span <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries2}</span>
				</display:column>
				<display:column title="3.Ries" align="right">
					<span <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries3}</span>
				</display:column>
				<display:column title="4.Ries" align="right">
					<span <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries4}</span>
				</display:column>
				<display:column title="5.Ries" align="right">
					<span <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries5}</span>
				</display:column>
				<display:column title="6.Ries" align="right">
					<span <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries6}</span>
				</display:column>
				<display:column title="Total" property="total" align="right" class="total" />
				<display:footer>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<th>Total</th>
						<th align="right">${result.total1}</th>
						<th align="right">${result.total2}</th>
						<th align="right">${result.total3}</th>
						<th align="right">${result.total4}</th>
						<th align="right">${result.total5}</th>
						<th align="right">${result.total6}</th>
						<th align="right">${result.total}</th>
					</tr>
				</display:footer>
			</display:table>
			<br />
			<br />
			<br />
		<h5>
		  Überzählige Spieler
		</h5>
		<display:table export="false" class="list" id="row" name="result.ueberzaehligePunkteAnzeige">
			<display:column title="" property="reihenfolge" />
			<display:column title="Nachname" property="nachname" width="95" />
			<display:column title="Vorname" property="vorname" width="80" />
			<display:column title="Jahrgang" property="jahrgang" />
			<display:column title="1.Ries" align="right">
				<span <c:if test="${row.nr1}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries1}</span>
			</display:column>
			<display:column title="2.Ries" align="right">
				<span <c:if test="${row.nr2}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries2}</span>
			</display:column>
			<display:column title="3.Ries" align="right">
				<span <c:if test="${row.nr3}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries3}</span>
			</display:column>
			<display:column title="4.Ries" align="right">
				<span <c:if test="${row.nr4}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries4}</span>
			</display:column>
			<display:column title="5.Ries" align="right">
				<span <c:if test="${row.nr5}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries5}</span>
			</display:column>
			<display:column title="6.Ries" align="right">
				<span <c:if test="${row.nr6}">style="background-color: #F85327; border: 1px solid Gray; border-collapse: collapse;"</c:if>> ${row.ries6}</span>
			</display:column>
			<display:column title="Total" property="total" align="right" class="total" />
			<display:setProperty name="basic.msg.empty_list">Keine überzähligen Spieler</display:setProperty>
		</display:table>

	</body>
</html>
