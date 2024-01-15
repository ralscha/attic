
<%@ include file="../include/taglibs.jspf"%>
<html>
	<head>    
	<title><bean:message key="application.title"/></title>
	<html:base/>
  
	<util:jsp directive="includes"/>	
  <link href="<c:url value='/styles/default.css' />" media="all" rel="stylesheet" type="text/css" />	
	
  <decorator:head/>
	</head>
	<body>
		<decorator:body />
	</body>
</html>

<util:jsp directive="endofpage"/>
