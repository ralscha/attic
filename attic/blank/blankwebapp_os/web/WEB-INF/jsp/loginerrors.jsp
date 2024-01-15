<%@ include file="include/taglibs.inc"%>



<logic:present parameter="error">

<div id="messageDiv" class="errormessage">
<table>
  <tr><th align="left"><bean:message key="common.errormessages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

  <tr><td>&nbsp;<bean:message key="login.loginFailed"/>&nbsp;&nbsp;</td></tr>    

  <tr><td>&nbsp;</td></tr>
</table>
</div>

</logic:present>
