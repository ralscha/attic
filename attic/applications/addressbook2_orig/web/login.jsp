<%@ page language="java" errorPage="/error.jsp" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<c:choose>
  <c:when test="${not empty cookie.ab_remember}">
    <logic:redirect page="/login"/>
  </c:when>
  <c:otherwise>
   <tiles:insert definition=".login" flush="true"/>
  </c:otherwise>
</c:choose>

