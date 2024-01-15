<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



<html:form focus="name" action="/storeCategory.do" method="post" onsubmit="return validateCategoryForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="Name"/>:&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="optionalInput"><bean:message key="ICalKey"/>:&nbsp;</td>
 <td><html:text property="icalKey" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="optionalInput"><bean:message key="Default"/>:&nbsp;</td>
 <c:choose>
   <c:when test="${not sessionScope.categoryForm.unknown}">
     <td><html:checkbox property="unknown"/></td>
   </c:when>
   <c:otherwise>
     <td><img src="<c:url value='/images/checkedw.gif'/>" alt="" width="13" height="13" border="0"></td>
   </c:otherwise>
 </c:choose>
</tr>
<tr>
 <td valign="top" class="optionalInput"><bean:message key="Description"/>:&nbsp;</td>
 <td><html:textarea cols="60" rows="5" property="description"/></td>
</tr>

<tr>
 <td valign="top" class="mandatoryInput"><bean:message key="Colour"/>:&nbsp;</td>
 <td>
	<table>
	<c:forTokens items="00,33,66,99,CC,FF" delims="," var="r">
	  <tr>
	  <c:forTokens items="00,33,66" delims="," var="g">
	    
	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	    
		  <c:set value="${r}${g}${b}" var="tmpColour"/>  
	      <td style="background-color:#<c:out value='${tmpColour}'/>"><html:radio property="colour" value="<%= (String)pageContext.getAttribute("tmpColour") %>"/></td>
	    </c:forTokens>
	  </c:forTokens>
	  </tr>
	  <tr>
	  <c:forTokens items="99,CC,FF" delims="," var="g">
	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	
		  <c:set value="${r}${g}${b}" var="tmpColour"/>       
	      <td style="background-color:#<c:out value='${tmpColour}'/>"><html:radio property="colour" value="<%= (String)pageContext.getAttribute("tmpColour") %>"/></td>
	    </c:forTokens>
	  </c:forTokens>
	  </tr>
	</c:forTokens>
	</table>  
 </td>
</tr>

<tr>
 <td valign="top" class="mandatoryInput"><bean:message key="BWColour"/>:&nbsp;</td>
 <td>
	<table>
     <tr>  
	   <c:forTokens items="00,10,20,30,40,50,60,70,80,90,A0,B0,C0,D0,E0,F0,FF" delims="," var="r">	  
		<c:set value="${r}${r}${r}" var="tmpColour"/>       
	    <td style="background-color:#<c:out value='${tmpColour}'/>"><html:radio property="bwColour" value="<%= (String)pageContext.getAttribute("tmpColour") %>"/></td>	  
	   </c:forTokens>
      </tr>
	</table>  
 </td>
</tr>



</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>">&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="SaveAndNew"/>">&nbsp;
<html:cancel><bean:message key="Cancel"/></html:cancel>
</html:form>

<html:javascript formName="categoryForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>

