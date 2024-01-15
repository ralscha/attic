<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/users.do" styleClass="title"><bean:message key="user.users"/></html:link> / <bean:message key="user.edit"/>: ${userForm.userName}</div>
<br />

<misc:confirm key="user.delete"/>
<html:form action="/storeUser.do" method="post" onsubmit="return validateUserForm(this);">
<html:hidden property="id" />
<html:hidden property="page" />

<c:choose>
<c:when test="${userForm.page == 0}">
<%@ include file="useredit0.jsp.inc"%>
</c:when>
<c:when test="${userForm.page == 1}">
<%@ include file="useredit1.jsp.inc"%>
</c:when>
<c:when test="${userForm.page == 2}">
<%@ include file="useredit2.jsp.inc"%>
 </c:when> 
</c:choose>

<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty userForm.id}">
  <c:if test="${userForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${userForm.userName}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="userForm" page="${userForm.page}"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

