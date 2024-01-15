<%@ page language="java" import="java.util.*,ch.sr.pf.*" %>

<html>
<head>
<title>Primfaktoren</title>
</head>
<body>

<form action="index.jsp" method="post">

<table bgcolor="#CFE7E7">
<tr>
<td>1. Zahl:</td>
<td><input type="text" name="z1" size="15" maxlength="12"></td>
</tr>
<tr>
<td>2. Zahl:</td>
<td><input type="text" name="z2" size="15" maxlength="12"></td>
</tr>
<tr>
<td colspan="2">
<input type="submit" name="Berechnen" value="Berechnen">
</td>
</tr>
</table>
<p>
<% 
   String z1 = request.getParameter("z1");
   String z2 = request.getParameter("z2");
%> 

<table width="70%" border="1" cellspacing="0" cellpadding="2" bgcolor="#D4D4D4">
<tr>
<th colspan="2">&nbsp;</th>
<th align="left">Primfaktoren</th>
</tr>
<tr>
<% long start = System.currentTimeMillis(); %>
<td width="10%">1. Zahl:</td>
<td width="20%"><b><%= (z1 != null) ? z1 : "" %></b></td>
<% Map mone = PrimeFactorDecomposition.getPfd(z1); %>
<td width="68%"><%= PrimeFactorDecomposition.toString(mone)%></td>
</tr>
<tr>
<td>2. Zahl:</td>
<% Map mtwo = PrimeFactorDecomposition.getPfd(z2); %>
<td><b><%= (z2 != null) ? z2 : "" %></b></td>
<td><%= PrimeFactorDecomposition.toString(mtwo)%></td>
</tr>
<tr>
<td>ggT:</td>
<td colspan="2"><b><%= GCDUtil.GCD(z1, z2)%></b></td>
</tr>
<tr>
<td>kgV:</td>
<td><b><%= PrimeFactorDecomposition.getKgV(mone, mtwo) %></b></td>
<td><%= PrimeFactorDecomposition.getKgVPrimFactors(mone, mtwo) %></td>
</tr>
</table>
</form>
<p>
<%= (System.currentTimeMillis() - start) %>&nbsp;ms
</body>
</html>
