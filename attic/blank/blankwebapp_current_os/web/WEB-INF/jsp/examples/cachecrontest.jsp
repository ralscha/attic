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

boolean refresh = false;
if (request.getParameter("refresh") != null)
{
	refresh = true;
}

boolean forceCacheUse = false;
if (request.getParameter("forceCacheUse") != null)
{
	forceCacheUse = true ;
}
%>

<p>&nbsp;</p>
<form action="<c:url value='/cachecrontest.do'/>">
	<input type="checkbox" name="forceCacheUse" value="now"> Force cache use&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" name="refresh" value="now"> Refresh&nbsp;&nbsp;&nbsp;&nbsp;
	Scope : <select name="scope">
		<option value="application" <%= scope.equals("application") ? "selected" : "" %>>Application
		<option value="session" <%= scope.equals("session") ? "selected" : "" %>>Session
	</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="Refresh">
</form>

<em>The cached content for the current day of the week should expire every minute.
Try setting your system clock to a couple of minutes before midnight and watch what
happens when you refresh the page as the day rolls over.</em>
<hr>
<b>Time this page was last refreshed: </b>: <%= new Date() %><br>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest1"
	cron="* * * * Sunday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Sunday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest2"
	cron="* * * * Monday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Monday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest3"
	cron="* * * * Tuesday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Tuesday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest4"
	cron="* * * * Wednesday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Wednesday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest5"
	cron="* * * * Thursday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Thursday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest6"
	cron="* * * * Friday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Friday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>
 <cache:cache
	refresh='<%= refresh %>'
	scope="<%= scope %>"
	key="cronTest7"
	cron="* * * * Saturday">

	<b>Cache Time</b>: <%= new Date() %><br>

	<b>This is some cache content (expires according to the cron expression &quot;* * * * Saturday&quot;)</b><br><br>
	<%
	if (forceCacheUse)
	{
	%>
		<cache:usecached />
	<%
	}
	%>
</cache:cache>
<hr>

