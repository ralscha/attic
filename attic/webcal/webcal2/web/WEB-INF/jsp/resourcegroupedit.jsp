<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 

<div class="title"><html:link page="/resourcegroups.do" styleClass="title"><bean:message key="resourcegroup.resourcegroups"/></html:link> / <bean:message key="resourcegroup.edit"/>: ${resourceGroupForm.name}</div>
<br />


<misc:confirm key="resourcegroup.delete"/>
<html:form action="/storeResourceGroup.do" method="post">
<html:hidden property="id" />

<html:hidden property="page" />

<c:choose>
<c:when test="${resourceGroupForm.page == 0}">
<%@ include file="resourcegroupedit0.jsp.inc"%>
</c:when>
<c:when test="${resourceGroupForm.page == 1}">
<%@ include file="resourcegroupedit1.jsp.inc"%>
</c:when>
<c:when test="${resourceGroupForm.page == 2}">
<%@ include file="resourcegroupedit2.jsp.inc"%>
 </c:when> 
</c:choose>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty resourceGroupForm.id}">
  <c:if test="${resourceGroupForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${resourceGroupForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>



