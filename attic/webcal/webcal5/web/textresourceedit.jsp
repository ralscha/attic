<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
  
  	
	<html:form action="/textResourceEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="textresourceedit.title" formid="frmTextResource" width="460">

        <logic:iterate id="entry" indexId="ix" name="TextResourceForm" property="entry" type="ch.ess.base.web.NameEntry">
	      <c:set var="label">#<bean:message key="textresource.text" /> (${entry.language})</c:set>
 
          <html:hidden name="entry" indexed="true" property="locale"/>
          <forms:textarea valign="top" label="${label}" rows="12" cols="120"  property="entry[${ix}].name"/>
        </logic:iterate>	
		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>
  
	<c:if test="${not empty sessionScope.TextResourceForm.variables}">
	<br />
	<table cellspacing="2" cellpadding="2" class="normaltext">
	<tr><th colspan="2" align="left"><bean:message key="textresource.variables"/></th></tr>
	<c:forEach var="item" items="${sessionScope.TextResourceForm.variables}">
	<tr>
		<td><strong>$!{${item.key}}</strong></td>
		<td>${item.value}</td>
	</tr>
	</c:forEach>
	</table>
	</c:if>  
</body>
</html>
