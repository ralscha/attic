<%@ page import="java.util.*" %>
<%@ taglib uri="/tags/oscache" prefix="cache" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>


<p>&nbsp;</p>
<hr>Flushing 'group2'...<hr>
<cache:flush group='group2' scope='application'/>
<hr>
<cache:cache key='test1' groups='group1,group2' duration='5s'>
    <b>Cache Time</b>: <%= (new Date()).getTime() %><br>
    This is some cache content that is in 'group1' and 'group2'. Normally it would refresh if it
    was more than 5 seconds old, however the &lt;cache:flush group='group2' scope='application'&gt;
    tag causes this entry to be flushed on every page refresh.<br>
</cache:cache>
<hr>
<cache:cache key='test2' groups='group1' duration='5s'>
    <b>Cache Time</b>: <%= (new Date()).getTime() %><br>
    This is some cache content that is in 'group1' (refreshes if more than 5 seconds old)<br>
</cache:cache>
<hr>
