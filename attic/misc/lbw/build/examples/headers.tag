<%@ tag body-content="empty" dynamic-attributes="dynattrs" %> 
<%@ attribute name="caption" required="true" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<table 
<c:forEach items="${dynattrs}" var="a"> 
${a.key}="${a.value}" 
</c:forEach> 
> 
<caption>${caption}</caption> 
<tr> 
<th>Name</th> 
<th>Value</th> 
</tr> 
<c:forEach items="${header}" var="h"> 
<tr> 
<td>${h.key}</td> 
<td>${h.value}</td> 
</tr> 
</c:forEach> 
</table>  