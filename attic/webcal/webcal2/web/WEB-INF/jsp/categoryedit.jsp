<%@ include file="include/taglibs.inc"%>
<html:xhtml /> 

<div class="title"><html:link page="/categories.do" styleClass="title"><bean:message key="category.categories"/></html:link> / <bean:message key="category.edit"/>: ${categoryForm.name}</div>
<br />

<misc:confirm key="category.delete"/>
<html:form action="/storeCategory.do" method="post" onsubmit="return validateCategoryForm(this);">
<html:hidden property="id" />

<table class="inputform">


<logic:iterate id="entry" indexId="ix" name="categoryForm" property="entries" type="ch.ess.common.web.NameEntry">
<html:hidden name="entry" indexed="true" property="locale"/>
<tr>
  <td><label for="entry[${ix}].name" class="required"><bean:message key="category.name" /> (${entry.language}) *:</label></td>
  <td><html:text size="40" maxlength="100" name="entry" indexed="true" property="name" /></td>
</tr>
</logic:iterate>

<tr>
 <td><misc:label property="icalKey" key="category.icalKey"/>&nbsp;</td>
 <td><html:text property="icalKey" size="40" maxlength="100"/></td>
</tr>

<tr>
 <td><misc:label property="unknown" key="category.default"/>&nbsp;</td>
 <c:choose>
   <c:when test="${not sessionScope.categoryForm.unknown}">
     <td><html:checkbox property="unknown"/></td>
   </c:when>
   <c:otherwise>
     <td><img src="<c:url value='/images/checked.gif'/>" alt="" width="16" height="16" border="0"></td>
   </c:otherwise>
 </c:choose>
</tr>

<tr>
 <td><misc:label property="colour" key="category.colour"/>&nbsp;</td>
 <td>
	<table>
	<c:forTokens items="00,33,66,99,CC,FF" delims="," var="r">
	  <tr>
	  <c:forTokens items="00,33,66" delims="," var="g">
	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	    
		  <c:set value="${r}${g}${b}" var="tmpColour"/>  
	      <td style="background-color:#${tmpColour}"><html:radio property="colour" value="${tmpColour}"/></td>
	    </c:forTokens>
	  </c:forTokens>
	  </tr>
	</c:forTokens>
	<c:forTokens items="00,33,66,99,CC,FF" delims="," var="r">
	  <tr>
	  <c:forTokens items="99,CC,FF" delims="," var="g">
	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	
		  <c:set value="${r}${g}${b}" var="tmpColour"/>       
	      <td style="background-color:#${tmpColour}"><html:radio property="colour" value="${tmpColour}"/></td>
	    </c:forTokens>
	  </c:forTokens>
	  </tr>
	</c:forTokens>	
	</table>  
 </td>
</tr>

<tr>
 <td><misc:label property="bwColour" key="category.bwColour"/>&nbsp;</td>
 <td>
	<table>
     <tr>  
	   <c:forTokens items="00,10,20,30,40,50,60,70,80,90,A0,B0,C0,D0,E0,F0,FF" delims="," var="r">	  
		<c:set value="${r}${r}${r}" var="tmpColour"/>       
	    <td style="background-color:#${tmpColour}"><html:radio property="bwColour" value="${tmpColour}"/></td>	  
	   </c:forTokens>
      </tr>
	</table>  
 </td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty categoryForm.id}">
  <c:if test="${categoryForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${categoryForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="categoryForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

