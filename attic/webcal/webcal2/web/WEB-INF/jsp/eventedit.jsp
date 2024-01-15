<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 
<misc:popupCalendarJs showWeekNumber="true" showToday="true" />

<div class="title"><html:link page="/events.do" styleClass="title"><bean:message key="event.events"/></html:link> / <bean:message key="event.edit"/>: ${eventForm.subject}</div>
<br />

<misc:confirm key="event.delete"/>
<html:form action="/storeEvent.do" method="post" onsubmit="return validateEventForm(this);">
<html:hidden property="id" />
<html:hidden property="page" />

<c:choose>
<c:when test="${eventForm.page == 0}">
<%@ include file="eventedit0.jsp.inc"%>
</c:when>
<c:when test="${eventForm.page == 1}">
<%@ include file="eventedit1.jsp.inc"%>
</c:when>
<c:when test="${eventForm.page == 2}">
<%@ include file="eventedit2.jsp.inc"%>
 </c:when> 
</c:choose>

<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty eventForm.id}">
  <c:if test="${eventForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${eventForm.subject}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>

<html:javascript cdata="false" formName="eventForm" page="${eventForm.page}"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

