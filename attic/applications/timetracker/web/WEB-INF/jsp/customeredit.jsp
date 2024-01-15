<%@ include file="include/taglibs.inc"%>

<div class="title"><html:link page="/customers.do" styleClass="title"><bean:message key="customer.customers"/></html:link> / <bean:message key="customer.edit"/>: ${customerForm.name}</div>
<br />

<misc:confirm key="customer.delete"/>
<html:form focus="name" action="/storeCustomer.do" method="post" onsubmit="return validateCustomerForm(this);">
<html:hidden property="id" />

<table class="inputform">

<tr>
 <td><misc:label property="name" key="customer.name"/>&nbsp;</td>
 <td><html:text property="name" size="40" maxlength="255"/></td>
</tr>
<tr>
 <td><misc:label property="description" key="customer.description"/>&nbsp;</td>
 <td><html:textarea property="description" rows="5" cols="40"/></td>
</tr>
</table>
<p>&nbsp;</p>
<input type="submit" name="store" value="<bean:message key="common.save"/>" />&nbsp;
<input type="submit" name="storeadd" value="<bean:message key="common.saveAndNew"/>" />&nbsp;
<c:if test="${not empty customerForm.id}">
  <c:if test="${customerForm.deletable}">
<input type="submit" onclick="bCancel=true;return confirmRequest('${customerForm.name}')" name="delete" value="<bean:message key="common.delete"/>" />&nbsp;
  </c:if>
</c:if>
<html:cancel><bean:message key="common.cancel"/></html:cancel>
</html:form>
<html:javascript cdata="false" formName="customerForm"
        dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/staticValidator.jsp'/>"></script>

