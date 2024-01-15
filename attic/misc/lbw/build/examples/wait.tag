<%@ tag body-content="empty" %> 
<%@ attribute name="top" rtexprvalue="true" required="false" %> 
<%@ attribute name="left" rtexprvalue="true" required="false" %> 
<c:if test="${empty top}">
  <c:set var="top" value="200"/>
</c:if>
<c:if test="${empty left}">
  <c:set var="left" value="200"/>
</c:if>

<script type="text/javascript" language="JavaScript">
ns4 = (document.layers)? true:false
ie = (document.styleSheets&&document.all)? true:false
ns6 = document.getElementById&&!document.all;
opera = (document.all&& !document.styleSheets)? true:false;


function hide_wait() {
  if (ns6||opera) document.getElementById("stickyad").style.visibility = "hidden";
  if (ns4) document.stickyad.visibility = "hidden";
  if (ie) document.all.stickyad.style.visibility = "hidden"; 
}

function show_wait() {
  if (ns6||opera) document.getElementById("stickyad").style.visibility = "";
  if (ns4) document.stickyad.visibility = "";
  if (ie) document.all.stickyad.style.visibility = ""; 
}

if (! ns4) {
  document.write('<div id="stickyad" style="Position:absolute; top: ${top}px; left: ${left}px; visibility: hidden;"><table border="1" cellspacing="0" cellpadding="10" bordercolor="#000000"><tr><td align="center" valign="middle" bgcolor="#CFD0DA"><div class="titlebig">${waittext}</div></td></tr><tr><td align="center" bgcolor="#CFD0DA"><img src="/images/wait.gif" width="300" height="4" border="0" alt=""></td></tr></table>');
  document.write('</div>');
}

if (ns4) {
  document.write('<layer name="stickyad" left="${left}" top="${top}" visibility="hide"><table border="1" cellspacing="0" cellpadding="10"><tr><td align="center" valign="middle" bgcolor="#CFD0DA"><h3>${waittext}</h3></td></tr><tr><td align="center" bgcolor="#CFD0DA"><img src="/images/wait.gif" width="398" height="4" border="0" alt=""></td></tr></table>');
  document.write('</layer>');
}
</script>