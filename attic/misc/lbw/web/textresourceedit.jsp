<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
  
  	
	<html:form action="/textResourceEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

    <c:set var="headline"><bean:message key="textresourceedit.title" /></c:set>
	<forms:form type="edit" caption="${headline}" formid="frmTextResource" width="460" locale="false">

        <logic:iterate id="entry" indexId="ix" name="TextResourceForm" property="entry" type="ch.ess.base.web.NameEntry">
	      <c:set var="label"><bean:message key="textresource.text" /> (${entry.language})</c:set>
 
          <html:hidden name="entry" indexed="true" property="locale"/>
          <forms:textarea label="${label}" rows="6" cols="80"  property="entry[${ix}].name"/>
        </logic:iterate>	
		
		<forms:buttonsection join="false"> 
         <c:set var="buttonsbase"><bean:message key="buttons.src" /></c:set>
         <c:set var="buttontitlesave"><bean:message key="button.title.save" /></c:set>
         <c:set var="buttontitleback"><bean:message key="button.title.back" /></c:set>         
			<forms:button   base="${buttonsbase}" default="true" name="btnSave"  src="btnSave1.gif"  title="${buttontitlesave}"/>
			<forms:button   base="${buttonsbase}" name="btnBack"  src="btnBack1.gif"  title="${buttontitleback}"/>
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
