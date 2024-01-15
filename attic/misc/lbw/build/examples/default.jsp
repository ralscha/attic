<%@ include file="include/taglibs.jspf"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %> 





<html>
  <head><title></title></head>
  <body>
    <my:copyright/> 

  <my:toString var="meinInhalt">
    Dies ist der Body
  </my:toString>
    
    BODY:${meinInhalt}:END
    
  </body>
</html>
