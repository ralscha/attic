<%@ include file="include/taglibs.inc"%>
<html><head>
<META HTTP-EQUIV="Refresh" CONTENT="0; URL=<c:url value="/loginDirect.do"/>"> 
<title></title>
</head>

<body onLoad="javascript:clientDetect();document.forms[0].submit();">
<%@ include file="include/clientdetect.inc"%>
	<html:form action="/loginDirect.do" method="post">
<%@ include file="include/clientdetecth.inc"%>
</html:form>

</body></html>

