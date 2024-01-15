<%@ include file="include/taglibs.inc"%>


<logic:messagesPresent>
<div id="messageDiv" class="errormessage">
<table>
  <tr><th align="left"><bean:message key="common.errormessages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

<html:messages id="error">
  <tr><td>&nbsp;${error}&nbsp;&nbsp;</td></tr>    
</html:messages>

  <tr><td>&nbsp;</td></tr>
</table>
</div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
<div id="messageDiv" class="message">
<table>
  <tr><th align="left"><bean:message key="common.messages"/></th></tr>
  <tr><td>&nbsp;</td></tr>

<html:messages id="msg" message="true">
  <tr><td>&nbsp;${msg}&nbsp;&nbsp;</td></tr>    
</html:messages>

  <tr><td>&nbsp;</td></tr>
</table>
</div>
</logic:messagesPresent>



