<%@ include file="WEB-INF/jsp/include/taglibs.inc"%>

<c:choose>
  <c:when test="${not empty cookie.cal_remember}">
	<html>
	<head>
	<META HTTP-EQUIV="Refresh" CONTENT="0; URL=<c:url value="/login"/>"> 
	<title></title>
	</head>
	<body onLoad="javascript:clientDetect();document.forms[0].submit();">
	<%@ include file="WEB-INF/jsp/include/clientdetect.inc"%>
	<form action="login" method="post">
	<%@ include file="WEB-INF/jsp/include/clientdetecth.inc"%>
	</form>
	
	</body>
	</html>
  </c:when>
  <c:otherwise>
   <tiles:insert definition=".login" flush="true"/>
  </c:otherwise>
</c:choose>

