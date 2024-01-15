<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 

<div class="title"><html:link page="/departments.do" styleClass="title"><bean:message key="department.departments"/></html:link> / <bean:message key="department.edit"/>: ${departmentForm.name}</div>
<br />


<misc:confirm key="department.delete"/>
<html:form action="/storeDepartment.do" method="post" onsubmit="return validateDepartmentForm(this);">
<html:hidden property="id" />
<html:hidden property="page" />

<c:choose>
<c:when test="${departmentForm.page == 0}">
<%@ include file="departmentedit0.jsp.inc"%>
</c:when>
<c:when test="${departmentForm.page == 1}">
<%@ include file="departmentedit1.jsp.inc"%>
</c:when>
<c:when test="${departmentForm.page == 2}">
<%@ include file="departmentedit2.jsp.inc"%>
 </c:when> 
</c:choose>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty departmentForm.id}">
  <c:if test="${departmentForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${departmentForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="departmentForm" page="${departmentForm.page}"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

