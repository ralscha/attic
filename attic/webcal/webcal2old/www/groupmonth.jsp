<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<script language="JavaScript" src="overlib.js"></script>  

<table width="100%">
<tr>
  <td width="8%" align="left">
	<c:url value="groupMonth.do" var="backYearUrl">
	  <c:param name="inputYear" value="${sessionScope.monthBean.year-1}"/>
	  <c:param name="inputMonth" value="${sessionScope.monthBean.month}"/>	  
	</c:url>
	<a href="<c:out value='${backYearUrl}'/>"><c:out value="${sessionScope.monthBean.year-1}"/></a>
  </td>
  <td align="center">
  <c:forEach items="${sessionScope.monthBean.monthNames}" var="month" varStatus="stat">

    <c:choose>
	 <c:when test="${month == sessionScope.monthBean.monthName}">
	    <b><c:out value="${month}"/></b>&nbsp;&nbsp;
	 </c:when>
	 <c:otherwise>
		<c:url value="groupMonth.do" var="monthUrl">
		  <c:param name="inputYear" value="${sessionScope.monthBean.year}"/>
		  <c:param name="inputMonth" value="${stat.index}"/>	  
		</c:url>
	    <a href="<c:out value='${monthUrl}'/>"><c:out value="${month}"/></a>
	   &nbsp;&nbsp;
	 </c:otherwise>

	</c:choose>
  </c:forEach>
  </td>
  <td width="8%" align="right">
	<c:url value="groupMonth.do" var="nextYearUrl">
	  <c:param name="inputYear" value="${sessionScope.monthBean.year+1}"/>
	  <c:param name="inputMonth" value="${sessionScope.monthBean.month}"/>	  
	</c:url>
	<a href="<c:out value='${nextYearUrl}'/>"><c:out value="${sessionScope.monthBean.year+1}"/></a>
  </td>  
</tr>
</table>
<p>&nbsp;</p>
<table width="100%" class="border">
<tr>
   <td class="b" rowspan="3">
   <c:out value="${sessionScope.monthBean.monthName}"/>&nbsp;<c:out value="${sessionScope.monthBean.year}"/>
   </td>
<c:forEach items="${sessionScope.monthBean.weekNos}" var="weekNo" varStatus="stat">
   <td align="center" class="small" colspan="<c:out value='${sessionScope.monthBean.weekColspan[stat.index]}'/>"><c:out value="${weekNo}"/></td>
</c:forEach>
</tr>
<tr>
<c:forEach begin="0" end="${sessionScope.monthBean.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.monthBean.today == stat.index+1}">
   <td align="center" class="smalltoday">
   </c:when>
   <c:when test="${not empty sessionScope.monthBean.holidays[stat.index]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${sessionScope.monthBean.holidays[stat.index]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallholiday">
   </c:when>
   <c:otherwise>
   <td align="center" class="small">
   </c:otherwise>
   </c:choose>
   <c:out value="${stat.index+1}"/>
   </td>
</c:forEach>
</tr>

<tr>
<c:forEach items="${sessionScope.monthBean.weekdayNames}" var="weekday" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.monthBean.today == stat.index+1}">
   <td align="center" class="smallertoday">
   </c:when>
   <c:when test="${not empty sessionScope.monthBean.holidays[stat.index]}">
   <td onmouseover="return overlib('<c:out escapeXml="false" value="${sessionScope.monthBean.holidays[stat.index]}"/>', FGCOLOR, '#FFD700', WIDTH, 110);" onmouseout="return nd();" align="center" class="smallerholiday">
   </c:when>
   <c:otherwise>
   <td align="center" class="smaller">
   </c:otherwise>
   </c:choose>
   <c:out value="${weekday}"/>
   </td>
</c:forEach>
</tr>

<tr>
  <td class="small">Daniel Ramseier Riem</td>
  <c:forEach begin="0" end="${sessionScope.monthBean.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.monthBean.today == stat.index+1}">  
   <td class="bt" align="center">
   </c:when>
   <c:when test="${not empty sessionScope.monthBean.holidays[stat.index]}">
   <td class="bh" align="center">
   </c:when>   
   <c:otherwise>
   <td class="b" align="center">
   </c:otherwise>
   </c:choose>   
   <img src="images\full\FFFFFF.gif" alt="" width="20" height="18" border="0"></td>
  </c:forEach>
</tr>
<tr>
  <td class="small">Daniel Ramseier Riem</td>
  <c:forEach begin="0" end="${sessionScope.monthBean.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.monthBean.today == stat.index+1}">  
   <td class="bt" align="center">
   </c:when>
   <c:when test="${not empty sessionScope.monthBean.holidays[stat.index]}">
   <td class="bh" align="center">
   </c:when>     
   <c:otherwise>
   <td class="b" align="center">
   </c:otherwise>
   </c:choose>   
   <img src="images\full\FFFFFF.gif" alt="" width="20" height="18" border="0"></td>
  </c:forEach>
</tr>
<tr>
  <td class="small">Daniel Ramseier Riem</td>
  <c:forEach begin="0" end="${sessionScope.monthBean.length-1}" varStatus="stat">
   <c:choose>
   <c:when test="${sessionScope.monthBean.today == stat.index+1}">  
   <td class="bt" align="center">
   </c:when>
   <c:when test="${not empty sessionScope.monthBean.holidays[stat.index]}">
   <td class="bh" align="center">
   </c:when>     
   <c:otherwise>
   <td class="b" align="center">
   </c:otherwise>
   </c:choose>   
   <img src="images\full\FFFFFF.gif" alt="" width="20" height="18" border="0"></td>
  </c:forEach>
</tr>
</table>