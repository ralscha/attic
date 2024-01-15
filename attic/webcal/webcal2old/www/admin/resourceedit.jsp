<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



<html:form focus="name" action="/storeResource.do" method="post" onsubmit="return validateResourceForm(this);">
<table class="inputForm">
<tr>
 <td class="mandatoryInput"><bean:message key="Name"/>:&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td class="mandatoryInput"><bean:message key="ResourceGroup"/>:&nbsp;</td>
 <td>
   <html:select property="resourceGroupId">
     <html:optionsCollection property="resourceGroupOptions"/>
   </html:select>  
 </td>
</tr>
<tr>
 <td valign="top" class="optionalInput"><bean:message key="Description"/>:&nbsp;</td>
 <td><html:textarea cols="60" rows="5" property="description"/></td>
</tr>

</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="Save"/>">&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="SaveAndNew"/>">&nbsp;
<html:cancel><bean:message key="Cancel"/></html:cancel>
</html:form>
<html:javascript formName="resourceForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" language="Javascript1.1" src="<c:url value='/staticValidator.jsp'/>"></script>
<br>
