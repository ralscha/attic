<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri='http://cewolf.sourceforge.net/taglib/cewolf.tld' prefix='cewolf' %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<jsp:useBean id="pageViews" class="ch.ess.examples.PageViewCountData"/>



<cewolf:chart 
    id="line" 
    title="Page View Statistics" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Views">
    <cewolf:data>
        <cewolf:producer id="pageViews"/>
    </cewolf:data>
</cewolf:chart>
<p>
<cewolf:img chartid="line" renderer="/cewolf" width="400" height="300">
    <cewolf:map/>
</cewolf:img>



