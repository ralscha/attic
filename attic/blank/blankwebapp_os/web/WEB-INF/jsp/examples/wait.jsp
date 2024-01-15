<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri='http://cewolf.sourceforge.net/taglib/cewolf.tld' prefix='cewolf' %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<misc:wait top="120" left="208" />
<script language="JavaScript" type="text/javascript">
  function wait(thisform) {
  ret = validateWaitActionForm(thisform);
  if (ret) {
    setTimeout("show_wait()", 2000);
  }   
  return ret;
  }
</script>

<html:form action="/waitAction.do" method="post" onsubmit="return wait(this);">
<table class="inputform">
<tr>
 <td><misc:label property="seconds" key="Seconds"/>&nbsp;</td>
 <td><html:text property="seconds" size="4" maxlength="10"/></td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="send" value="<bean:message key="Wait"/>">&nbsp;
</html:form>
<html:javascript formName="waitActionForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
