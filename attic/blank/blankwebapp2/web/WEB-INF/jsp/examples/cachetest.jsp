<%@ page import="java.util.*" %>
<%@ taglib uri="/tags/oscache" prefix="cache" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%
String scope = "application";
if (request.getParameter("scope") != null)
{
	scope = request.getParameter("scope");
}
%>


<p>&nbsp;</p>
<form action="<c:url value='/cachetest.do'/>">
	<input type="checkbox" name="refresh" value="now"> Refresh&nbsp;&nbsp;&nbsp;&nbsp;
	Scope : <select name="scope">
		<option value="application" <%= scope.equals("application") ? "selected" : "" %>>Application
		<option value="session" <%= scope.equals("session") ? "selected" : "" %>>Session
	</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="Refresh">
</form>

<% Date start = new Date(); %> <b>Start Time</b>: <%= start %><p>
<hr>
 <%-- Note that we have to supply a cache key otherwise the 'refresh' parameter
         causes the refreshed page to end up with a different cache key! --%>
<cache:cache key="test"
	refresh='<%= request.getParameter("refresh") == null ? false : true %>'
	scope="<%= scope %>">
	<b>Cache Time</b>: <%= new Date() %><br>
	<% try { %>
	Inside try block. <br>
	<%
	Thread.sleep(1000L); // Kill some time
	if ((new Date()).getTime() % 5 == 0)
	{
		System.out.println("THROWING EXCEPTION....");
		throw new Exception("ack!");
	}
	%>
	<p>

	<% }
	catch (Exception e)
	{
	%>
		Using cached content: <cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
<b>End Time</b>: <%= new Date() %><p>

<b>Running Time</b>: <%= (new Date()).getTime() - start.getTime() %> ms.<p>
