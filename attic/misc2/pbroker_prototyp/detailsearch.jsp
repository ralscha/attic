<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:ifAttributeMissing name="pbroker.user" scope="session">
  <jsp:forward page="login.jsp"/>
</struts:ifAttributeMissing>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
	<title>Detailsuche</title>	
	<script language="JavaScript" type="text/javascript">
		function go(topicid) {
			document.searchform.topic.value = topicid;
			document.searchform.submit();
		}

</script>
</head>

<body>
<font face="Arial,Helvetica,sans-serif" size="4">Detailsuche</font>
<p>

<form name="searchform" action="<%= response.encodeURL("detailsearch.do") %>" method="post">
<input type="hidden" name="topic" value="">
<input type="hidden" name="searchfrom" value="detailsearch.jsp">
<p>

<table>
<tr>
<td>&nbsp;</td>
<td colspan="3">&nbsp;</td>
<td><font face="Arial,Helvetica,sans-serif" size="1"><input type="submit" name="search" value="Suchen"></font></td>
</tr>
<tr>
<td><font face="Arial,Helvetica,sans-serif" size="1"><select name="kosten" size="1">
	<option value="1" SELECTED>Maximaler Stundenansatz:</option>
	<option value="2">Maximaler Tagesansatz:</option>
</select></font></td>
<td><font face="Arial,Helvetica,sans-serif" size="1"><input type="text" name="kostensatz" size="10"></font></td>
<td>&nbsp;</td>
<td><font face="Arial,Helvetica,sans-serif" size="1">Verf&uuml;gbar ab:</font></td>
<td>
<font face="Arial,Helvetica,sans-serif" size="1">
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
</font>
</td>
</tr>
</table>
<p>
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
<font face="Arial,Helvetica,sans-serif" size="1"><input type="submit" name="search" value="Suchen"></font>
</form>
</body>
</html>
