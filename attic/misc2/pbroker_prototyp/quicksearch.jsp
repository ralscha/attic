<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*" info="PBroker Login" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>

<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Quicksuche</title>
	
	<script language="JavaScript" type="text/javascript">
		function go(topicid) {
			document.searchform.topic.value = topicid;
			document.searchform.submit();
		}

</script>	<link rel="STYLESHEET" type="text/css" href="style.css">
</head>

<body onLoad="javascript:parent.frames[0].location.href='frames/top_step_rekrutierung.jsp?no=6&a=1'">

<% List kandidaten = Db.getKandidatenTable().select(); %>
<table><tr><td bgcolor="#CFE7E7">
<p class="texts">Es befinden sich <b><%= kandidaten.size() %></b> Kandidaten im Pool</p>
</td></tr></table>

<p class="title">Quicksearch</p>

<form action="<%= response.encodeURL("quicksearch.do") %>" method="post">
<input type="hidden" name="searchfrom" value="search.jsp">
<table cellspacing="0" cellpadding="0">
<tr>
	<td class="text">Stichwort:&nbsp;</td>
	<td class="text"><input type="text" name="searchstring" size="30">&nbsp;&nbsp;</td>
	<td class="text"><input type="submit" name="Suchen" value="Suchen"></td>
</tr>
</table>
</form>
<p class="text">Nur ein Stichwort erlaubt z.B. Java, Unix. Suche ohne Stichwort zeigt alle Kandidaten an.</p>
<p>
<struts:ifAttributeExists name="pbroker.notfound" scope="request">
<font face="Arial,Helvetica,sans-serif" size="2"><b><%= request.getAttribute("pbroker.notfound") %></b></font>
</struts:ifAttributeExists>
<hr align="left" width="600">
<p>
<p class="title">Detailsuche</p>
<p>

<form name="searchform" action="<%= response.encodeURL("detailsearch.do") %>" method="post">
<input type="hidden" name="topic" value="">
<input type="hidden" name="searchfrom" value="detailsearch.jsp">
<p>

<table>

<tr>
<td><font face="Arial,Helvetica,sans-serif" size="1"><select name="kosten" size="1">
	<option value="1" selected class="text">Maximaler Stundenansatz:</option>
	<option value="2" class="text">Maximaler Tagesansatz:</option>
</select></font></td>
<td class="text">SFr.:&nbsp;<input type="text" name="kostensatz" value="0.00" size="10" onFocus="if (kostensatz.value == '0.00') kostensatz.value = ''" onBlur="if (kostensatz.value == '') kostensatz.value = '0.00'"></td>
<td>&nbsp;</td>
<td class="text">Verf&uuml;gbar ab:</td>
<td class="text">
 <select name="verfuegbar" size="1">
	<option value="0" SELECTED>sofort</option>
	<option value="1">Januar</option>
	<option value="2">Februar</option>
	<option value="3">M&auml;rz</option>
	<option value="4">April</option>
	<option value="5">Mai</option>
	<option value="6">Juni</option>
	<option value="7">Juli</option>
	<option value="8">August</option>
	<option value="9">September</option>
	<option value="10">Oktober</option>
	<option value="11">November</option>								
	<option value="12">Dezember</option>									
</select>
</td>
</tr>
</table>
<p>
<img src="images/muss.gif" width="7" height="16" border="0" alt="Muss">&nbsp;Muss&nbsp;&nbsp;
<img src="images/kann.gif" width="12" height="15" border="0" alt="Kann">&nbsp;Kann&nbsp;&nbsp;
<img src="images/nr.gif" width="11" height="9" border="0" alt="Nicht relevant">&nbsp;Nicht relevant<p>
<table border="1" cellspacing="0" cellpadding="0">
<%
	SearchCriterion sc = (SearchCriterion)session.getAttribute("pbroker.searchcriterion");
	
	String topicid = request.getParameter("topic");
	if ("null".equals(topicid))
		topicid = null;
	
	List kategorien = Db.getKategorienTable().select();
	
	for (int i = 0; i < kategorien.size(); i++) {
		Kategorien kategorie = (Kategorien)kategorien.get(i);
		if (kategorie.getIsonqueryform()) {
			List topics = kategorie.getTopics();
			if (!kategorie.getHastopics()) {				
				if (topics.size() > 0) {
%>
<tr><td><table cellspacing="0" cellpadding="0">
<tr>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1"><b><%= kategorie.getKategorie() %></b></font></th>
		<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/muss.gif" width="7" height="16" border="0" alt="muss"></font></th>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/kann.gif" width="12" height="15" border="0" alt="kann"></font></th>
	<th align="center"><img src="images/nr.gif" width="12" height="12" border="0" alt="nicht relevant"></th>
	<th>&nbsp;</th>
	<th>&nbsp;</th>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/muss.gif" width="7" height="16" border="0" alt=""></font></th>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/kann.gif" width="12" height="15" border="0" alt=""></font></th>
	<th align="center"><img src="images/nr.gif" width="12" height="12" border="0" alt="nicht relevant"></th>
	<th>&nbsp;</th>
</tr>

<%				
				
				
	List skills = ((Topics)topics.get(0)).getSkills();
	for (int j = 0; j < skills.size(); j += 2) {
	 
%>
<tr>
<%
     for (int n = 0; n < 2; n++) {  
	  if (j+n < skills.size()) {
  	  Skills skill = (Skills)skills.get(j+n);
%>					

	<% if (sc != null) { 
	    boolean muss = sc.containsMussSkill(skill.getSkillid());
		boolean kann = sc.containsKannSkill(skill.getSkillid());
	%>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><%= skill.getSkill() %></font></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="muss<%= skill.getSkillid() %>" <%= muss ? "checked" : "" %>></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="kann<%= skill.getSkillid() %>" <%= kann ? "checked" : "" %>></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="na<%= skill.getSkillid() %>"  <%= (!muss && !kann) ? "checked" : "" %>></td>
	<td>&nbsp;&nbsp;</td>
	<% } else { %>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><%= skill.getSkill() %></font></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="muss<%= skill.getSkillid() %>"></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="kann<%= skill.getSkillid() %>"></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="na<%= skill.getSkillid() %>" checked></td>
	 <td>&nbsp;&nbsp;</td>
	<% } %>
	<% } else { %>
	     <td>&nbsp;</td>
		 <td>&nbsp;</td>
		 <td>&nbsp;</td>
	<% } %>
	<%  } %>
</tr>

<%					}
%>
</table></td></tr>
<%
				} // if topics.size() 
			} // if not gethastopics()
%>
<%
		} // if isonqueryform()
	}
%>
</table>
<p>
<table border="1"  cellspacing="0" cellpadding="0">

<%
	for (int i = 0; i < kategorien.size(); i++) {
		Kategorien kategorie = (Kategorien)kategorien.get(i);
		if (kategorie.getIsonqueryform()) {
			List topics = kategorie.getTopics();
			if (kategorie.getHastopics()) {
					
%>					
<tr>
<td colspan="2"><font face="Arial,Helvetica,sans-serif" size="1"><b><%= kategorie.getKategorie() %></b></font></td>
</tr>
<tr><td align="left" valign="top">
	<table  cellspacing="1" cellpadding="1">
		
    <% Iterator it = topics.iterator(); 
	   while (it.hasNext()) {
	      Topics topic = (Topics)it.next(); 
		  if (topicid == null)
			topicid = String.valueOf(topic.getTopicid());

						
		  if (topicid.equals(String.valueOf(topic.getTopicid()))) {
 	%>
	<tr><td bgcolor="Silver"><font face="Arial,Helvetica,sans-serif" size="1"><%= topic.getTopic() %></font></td></tr>
	     <% } else { %>
	<tr><td><a href="javascript:go('<%= topic.getTopicid() %>')"><font face="Arial,Helvetica,sans-serif" size="1"><%= topic.getTopic() %></font></a></td></tr>
		 <% } %>
	<% } %>
	
	</table>
	</td>
<td align="left" valign="top">
	  <table  cellspacing="0" cellpadding="0">
<tr>
	<th align="left"><font face="Arial,Helvetica,sans-serif" size="1">Kenntnis</font></th>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/muss.gif" width="7" height="16" border="0" alt=""></font></th>
	<th align="center"><font face="Arial,Helvetica,sans-serif" size="1"><img src="images/kann.gif" width="12" height="15" border="0" alt=""></font></th>
	<th align="center"><img src="images/nr.gif" width="12" height="12" border="0" alt="nicht relevant"></th>
</tr>

<% 
	List skills = Db.getSkillsTable().select("TopicId = " + topicid);
	for (int n = 0; n < skills.size(); n++) {
	  Skills skill = (Skills)skills.get(n);
%>
<tr>
	<td><font face="Arial,Helvetica,sans-serif" size="1"><%= skill.getSkill() %></font></td>
	<% if (sc != null) { 
	    boolean muss = sc.containsMussSkill(skill.getSkillid());
		boolean kann = sc.containsKannSkill(skill.getSkillid());
	%>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="muss<%= skill.getSkillid() %>" <%= muss ? "checked" : "" %>></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="kann<%= skill.getSkillid() %>" <%= kann ? "checked" : "" %>></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="na<%= skill.getSkillid() %>"  <%= (!muss && !kann) ? "checked" : "" %>></td>
	<% } else { %>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="muss<%= skill.getSkillid() %>"></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="kann<%= skill.getSkillid() %>"></td>
	 <td><input type="radio" name="skill<%= skill.getSkillid() %>" value="na<%= skill.getSkillid() %>" checked></td>
	<% } %>	
</tr>
<% } %>

</table>

</td>	
</tr>
					
<%	
			}
		}		
	}
%>


</table>


<p>
<input type="submit" name="search" value="Suchen" class="text">
</form>
<p>
<a href="description.html"><img src="images/but_back.gif" width="25" height="23" border="0" alt=""></a>
</body>
</html>
