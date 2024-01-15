<%@ include file="include/taglibs.jspf"%>

<sec:granted permission="$event">
<logic:redirect page="/groupMonth.do?clean=1&startCrumb=1&startCrumbPath=/calendar"/>
</sec:granted>
<sec:granted permission="$time">
<logic:redirect page="/preTimeList.do?clean=1&startCrumb=1&startCrumbPath=/time"/>
</sec:granted>
<sec:granted permission="$task">
<logic:redirect page="/taskList.do?clean=1&amp;startCrumb=1"/>
</sec:granted>
<sec:granted permission="$contact">
<logic:redirect page="/preContactList.do?clean=1&amp;startCrumb=1"/>
</sec:granted>
<sec:granted permission="$document">
<logic:redirect page="/directoryList.do?clean=1&amp;startCrumb=1"/>
</sec:granted>

<html>
  <head><title></title></head>
  <body>    
  </body>
</html>
