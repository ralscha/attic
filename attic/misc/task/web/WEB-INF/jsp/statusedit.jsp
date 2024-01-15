<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags/ess-misc" prefix="misc" %>
<html:xhtml /> 

<div class="title"><html:link page="/status.do" styleClass="title"><bean:message key="status"/></html:link> / <bean:message key="status.edit"/>: <c:out value="${statusForm.name}" /></div>
<br />

<misc:confirm key="status.deleteStatus"/>
<html:form focus="name" action="/storeStatus.do" method="post" onsubmit="return validateStatusForm(this);">
<html:hidden property="id" />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="statusForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[<c:out value='${ix}'/>].name" class="required"><bean:message key="status.name" /> (<c:out value="${entry.language}"/>) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty statusForm.id}">
  <c:if test="${statusForm.deletable}">
<input type="submit" onclick="return confirmRequest('<bean:write name="statusForm" property="name"/>')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="statusForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

