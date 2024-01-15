<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<c:choose>
  <c:when test="${not empty cookie.remember}">
    <logic:redirect page="/logonRemember.do?clean=1"/>
  </c:when>
  <c:otherwise>
    <logic:redirect page="/default.do?clean=1"/>
  </c:otherwise>
</c:choose>
<%--

Redirect default requests to Welcome global ActionForward.
By using a redirect, the user-agent will change address to match the path of our Welcome ActionForward. 

--%>
