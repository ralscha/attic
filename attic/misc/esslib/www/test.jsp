<%@ page language="java" import="java.awt.*,ch.ess.util.spreadsheet.*" %>

<%@ taglib uri="/ess-spreadsheet"  prefix="spread" %>
<html>
<head>
	<title>Leistungserfassung bearbeiten</title>
	
<script language="JavaScript">
<!--
  function setNavigation() {
	window.parent.navigation.setNext();
	window.parent.navigation.setBack('zl/zlliste.jsp');
	window.parent.navigation.setBackEnd('zl/zlliste.jsp');
	window.parent.navigation.setStop('zl/descrzl.jsp');  
  }
//-->
</script>		
<script language="JavaScript" src="spreadsheet.js" type="text/javascript">
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" link="#3A3D57" vlink="#3A3D57" alink="#3A3D57" topmargin="5" marginwidth="15" marginheight="5" leftmargin="15" onload="setEnv();">

<%
			/*HtmlSpreadsheetModel m = new HtmlSpreadsheetModel("s");
			SimpleHtmlSpreadsheetCell c;
			String f1 = "#?,0 #?,1 + #?,2 + #?,3 * #?,4 / #?,5 -";
			String f2 = "#?,0 #?,1 +0 #?,2 +0 #?,3 *0 #?,4 /0 #?,5 -0";
			c = new SimpleHtmlSpreadsheetCell(m,0,0,"t1",true,"10",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,1,"t2",true,"0",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,2,"t3",true,"0",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,3,"t4",false,null,"#0,0 #0,1 +0 #0,2 +0",null);
			request.setAttribute("s", m);
			*/
			/*
			c = new SimpleHtmlSpreadsheetCell(m,0,0,null,false,"Const",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,1,null,false,"+",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,2,null,false,"+",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,3,null,false,"*",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,4,null,false,"/",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,5,null,false,"-",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,0,6,null,false,"=",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,0,null,false,"1.0",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,1,null,true,"1.1",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,2,null,true,"1.2",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,3,null,true,"1.3",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,4,null,true,"1.4",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,5,null,true,"1.5",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,1,6,null,false,null,f2,null);
			c = new SimpleHtmlSpreadsheetCell(m,2,0,null,false,null,"#1,0",null);
			c = new SimpleHtmlSpreadsheetCell(m,2,1,null,true,null,"#2,0 #1,1 +","#this #2,0 - #1,? =>");
			c = new SimpleHtmlSpreadsheetCell(m,2,2,null,true,null,"#2,1 #1,2 +","#this #2,1 - #1,? =>");
			c = new SimpleHtmlSpreadsheetCell(m,2,3,null,true,null,"#2,2 #1,3 +","#this #2,2 - #1,? =>");
			c = new SimpleHtmlSpreadsheetCell(m,2,4,null,true,null,"#2,3 #1,4 +","#this #2,3 - #1,? =>");
			c = new SimpleHtmlSpreadsheetCell(m,2,5,null,true,null,"#2,4 #1,5 +","#this #2,4 - #1,? =>");
			c = new SimpleHtmlSpreadsheetCell(m,2,6,null,false,null,f1,null);
			c = new SimpleHtmlSpreadsheetCell(m,3,0,null,false,null,"#2,? DtoHM",null);
			c = new SimpleHtmlSpreadsheetCell(m,3,1,null,true,null,"#2,? DtoHM","#this HMtoD #2,? =>!");
			c = new SimpleHtmlSpreadsheetCell(m,3,2,null,true,null,"#2,? DtoHM","#this HMtoD #2,? =>!");
			c = new SimpleHtmlSpreadsheetCell(m,3,3,null,true,null,"#2,? DtoHM","#this HMtoD #2,? =>!");
			c = new SimpleHtmlSpreadsheetCell(m,3,4,null,true,null,"#2,? DtoHM","#this HMtoD #2,? =>!");
			c = new SimpleHtmlSpreadsheetCell(m,3,5,null,true,null,"#2,? DtoHM","#this HMtoD #2,? =>!");
			c = new SimpleHtmlSpreadsheetCell(m,3,6,null,false,null,f1,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,0,null,false,"4.0",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,1,null,true,"4.1",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,2,null,true,"4.2",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,3,null,true,"4.3",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,4,null,true,"4.4",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,5,null,true,"4.5",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,4,6,null,false,null,f1,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,0,null,false,"5.0",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,1,null,true,"5.1",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,2,null,true,"5.2",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,3,null,true,"5.3",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,4,null,true,"5.4",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,5,null,true,"5.5",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,5,6,null,false,null,f1,null);
			c = new SimpleHtmlSpreadsheetCell(m,6,5,null,false,"Total",null,null);
			c = new SimpleHtmlSpreadsheetCell(m,6,6,null,false,null,"#1,? #2,? +0 #3,? +0 #4,? +0 #5,? +0",null);
			*/

%>

<p>


<form action="/pbroker/zl/saveZl.do" method="post" name="zlBearbeiten" scope="request" onSubmit="chkPendCellUpdate();">
<spread:spreadsheet name="s" scope="request">


  <spread:cell row="0" column="0" name="mwst" editable="true" value="1.076"/>
  <spread:cell row="1" column="0" name="zeithmm" value="" editable="true"/>
  <spread:cell row="2" column="0" name="zeithhh" editable="true" formula="#1,0 DtoHM0" action="#this HMtoD0 #1,0 =>!" format="0:00" precision="100"/>

  <spread:celloutput name="zeithmm" size="8"/><br>
  <spread:celloutput name="zeithhh" size="8"/><br>

</spread:spreadsheet>


<input type="submit" value="submit">
</form>

</body>
</html>
