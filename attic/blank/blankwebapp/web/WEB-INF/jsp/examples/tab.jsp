<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri='http://cewolf.sourceforge.net/taglib/cewolf.tld' prefix='cewolf' %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<script language="JavaScript" src="<c:url value='/scripts/tab.js' />" type="text/javascript"></script>



	<ilayer id="panelLocator" width="450" height="200"></ilayer>

	<div id="p1" style="background-color: transparent; position: relative; width: 450px; height: 200px">
		<div id="p1panel0" class="panel" style="z-index:5; width: 450px; height: 200px">	
		<p>&nbsp;</p>
		This is tab 1

		</div>
		<div onclick="selectTab(0)" id="p1tab0" class="tab" style="left:0px; top:0px; z-index:5; clip:rect(0 auto 25 0)">
Tab 1
		</div>
		<div id="p1panel1" class="panel" style="z-index:4; width: 450px; height: 200px">
		<p>&nbsp;</p>
		This is tab 2		

		</div>
		<div onclick="selectTab(1)" id="p1tab1" class="tab" style="left:90px; top:0px; z-index:4; clip:rect(0 auto 25 0)">
Tab 2
		</div>
		<div id="p1panel2" class="panel" style="z-index:3; width: 450px; height: 200px">
		<p>&nbsp;</p>
		This is tab 3		
		
		</div>
		<div onclick="selectTab(2)" id="p1tab2" class="tab" style="left:180px; top:0px; z-index:3; clip:rect(0 auto 25 0)">
Tab 3
		</div>
		<div id="p1panel3" class="panel" style="z-index:2; width: 450px; height: 200px">
		<p>&nbsp;</p>
		This is tab 4		
		
		</div>
		<div onclick="selectTab(3)" id="p1tab3" class="tab" style="left:270px; top:0px; z-index:2; clip:rect(0 auto 25 0)">
Tab 4
		</div>
	
		<div id="p1panel4" class="panel" style="z-index:1; width: 450px; height: 200px">
		<p>&nbsp;</p>
		This is tab 5

		</div>
		<div onclick="selectTab(4)" id="p1tab4" class="tab" style="left:360px; top:0px; z-index:1; clip:rect(0 auto 25 0)">
Tab 5
		</div>
	</div>	
