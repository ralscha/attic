<%@ page session="false" import="java.util.*, gtf.ss.common.*" %>

<html>
<head>
<title>Gtf Swiss Search</title>
<link rel=stylesheet type="text/css" href="/ss/ss.css">
<script language="JavaScript" type="text/javascript">
<!--
// original code by Bill Trefzger 12/12/96
function go1(){
if (document.searchform.requesttype.options[document.searchform.requesttype.selectedIndex].value == "http://159.122.248.165:8080/ss/servlet/SwissSearch?jsp=SwissSearchAmount.jsp") {
location = document.searchform.requesttype.options[document.searchform.requesttype.selectedIndex].value
		}
	}
//-->
</script>
</head>
<body>

<a href="http://159.122.248.165/gtf" target="_top">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;<i>Gtf Swiss Search</i>
<%
 String lastUpdate = (String)request.getAttribute("ss.last.update");
 if (lastUpdate != null) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;Last Update:&nbsp;<%= lastUpdate %>

 <%
 }
 %>
<hr>
<p>

<form action="/ss/servlet/SwissSearch" method="POST" name="searchform">
<input type="hidden" name="jsp.page" value="/SwissSearch.jsp">
<table cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><select name="requesttype" size="1" onchange="go1()">
	<option value="bankref">Bank Reference</option>
	<option value="bankname">Bank Name</option>
	<option value="http://159.122.248.165:8080/ss/servlet/SwissSearch?jsp=SwissSearchAmount.jsp">Dossier Amount</option></select></td>
	
    <td><input type="text" name="requeststring" size="25"></td>
	<td><input type="hidden" name="action" value="SEARCH">
<input type="Submit" name="Submit" value="Search"></td>
</tr>
</table>
</form>

<%
 List resultList = (List)request.getAttribute("ss.search.result");
 if (resultList != null) {
%>

	<hr>
		
	<!-- Search Request -->
	
	<%
	   String ref = (String)request.getAttribute("ss.search.request.ref");
       String name = (String)request.getAttribute("ss.search.request.name");
		String amount = (String)request.getAttribute("ss.search.request.amount"); 
		if ((ref != null) && (ref.length() > 0)) {
	%>
	Bank Reference: <%= ref  %>
	<% }
	else if ((name != null) && (name.length() > 0)) {
	%>
	Bank Name: <%= name %>
	<% } 
	else if ((amount != null) && (amount.length() > 0)) {
	%>
	Dossier Amount: <%= amount %>
	<% } %>
	
	
	<!-- Search Result -->
	&nbsp;(Found: <%= resultList.size() %>)
	
	<%
		if (resultList.size() > 0) {
	%>
	
	<p>
	<table border="1" cellspacing="2" cellpadding="2">
	<tr>
		<th class="textb">&nbsp;</th>
	    <th class="textb">Dossier Number</th>
	    <th class="textb">Bank Name</th>
	    <th class="textb">Bank Location</th>
	    <th class="textb">Bank Ref No</th>
		<th class="textb">ISO</th>
		<th align="right" class="textb">Amount</th>
	</tr>
	
	<% 
	for (int i = 0; i < resultList.size(); i++) { 
   		SS_BANK_REF row = (SS_BANK_REF)resultList.get(i);
   %>
		<tr>
			<td class="text"><%= row.getSERVICECENTER() %> </td>		
		    <td class="text"><%= row.getFormattedDossierNumber() %> </td>
		    <td class="text"><%= row.getBANK_NAME().substring(0,35) %></td>
		    <td class="text"><% String location = row.getBANK_LOCATION(); if (location.trim().equals("")) out.print("&nbsp;"); else out.print(location); %></td>
		    <td class="text"><% String refno = row.getBANK_REF_NO(); if (refno.trim().equals("")) out.print("&nbsp;"); else out.print(refno); %></td>
			<td class="text"><% String iso = row.getISO_CODE(); if (iso.trim().equals("")) out.print("&nbsp;"); else out.print(iso); %></td>
			<td align="right" class="text"><% amount = row.getFormattedAmount(); if (amount.trim().equals("")) out.print("&nbsp;"); else out.print(amount); %></td>
		</tr>
	<% 
		} 
	%>
	
	</table>
	
	<!-- End Search Result -->
<%
	}
 }
%>


</body></html>
