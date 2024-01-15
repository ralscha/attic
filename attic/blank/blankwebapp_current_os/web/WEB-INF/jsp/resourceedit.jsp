<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/resources.do" styleClass="title"><bean:message key="resource.resources"/></html:link> / <bean:message key="resource.edit"/>: ${resourceForm.name} </div>
<br />

<misc:confirm key="resource.delete"/>
<html:form action="/storeResource.do" method="post">
<html:hidden property="id" />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="resourceForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td valign="top"><label for="entry[${ix}].name" class="required"><bean:message key="resource.name" /> (${entry.language}) *:</label></td>
  <td><html:textarea rows="6" cols="80" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<c:if test="${not empty resourceForm.id}">
  <c:if test="${resourceForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${resourceForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>

<c:if test="${not empty sessionScope.resourceForm.variables}">
<p>&nbsp;</p>
<table cellspacing="2" cellpadding="2" class="show">
<tr><th colspan="2" align="left"><bean:message key="resource.variables"/></th></tr>
<c:forEach var="item" items="${sessionScope.resourceForm.variables}">
<tr>
	<td><strong>$!{${item.key}}</strong></td>
	<td>${item.value}</td>
</tr>
</c:forEach>
</table>
</c:if>


