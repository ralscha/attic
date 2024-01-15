<%@ tag body-content="empty" %> 
<%@ attribute name="items" rtexprvalue="true" required="true" %> 
<%@ attribute name="even" fragment="true" required="true" %> 
<%@ attribute name="odd" fragment="true" required="true" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:forEach items="${items}" varStatus="status"> 
<c:choose> 
<c:when test="${status.count % 2 == 0}"> 
<jsp:invoke fragment="even" /> 
</c:when> 
<c:otherwise> 
<jsp:invoke fragment="odd" /> 
</c:otherwise> 
</c:choose> 
</c:forEach>  