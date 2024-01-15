<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>


<html:form action="/sendMail.do" method="post" onsubmit="return validateSendMailForm(this);">
<table class="inputform">


<tr>
 <td><misc:label property="recipient" key="Recipient"/>&nbsp;</td>
 <td><html:text property="recipient" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="subject" key="Subject"/>&nbsp;</td>
 <td><html:text property="subject" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td valign="top"><misc:label property="text" key="Text"/>&nbsp;</td>
 <td><html:textarea property="text" rows="4" cols="30"/></td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="send" value="<bean:message key="Send"/>">&nbsp;
</html:form>
<html:javascript formName="sendMailForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
