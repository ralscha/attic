<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
  
    <menu:crumbs value="crumb2" labellength="40">
	    <menu:crumb	crumbid="crumb1"	action="/listTextResource.do" title="textResource.textResources"/>
	    <menu:crumb	crumbid="crumb2"	title="textResource.headline"/>
    </menu:crumbs>
  <br>
  	
	<html:form action="/editTextResource">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

    <c:set var="headline"><bean:message key="textResource.headline" /></c:set>
	<forms:form type="edit" caption="${headline}" formid="frmTextResource" width="460" locale="false">

        <logic:iterate id="entry" indexId="ix" name="textResourceForm" property="entry" type="ch.ess.cal.web.NameEntry">
	      <c:set var="label"><bean:message key="textResource.text" /> (${entry.language})</c:set>
 
          <html:hidden name="entry" indexed="true" property="locale"/>
          <forms:textarea label="${label}" rows="6" cols="80"  property="entry[${ix}].name"/>
        </logic:iterate>	
		
		<forms:buttonsection join="false"> 
         <c:set var="buttonsbase"><bean:message key="buttons.src" /></c:set>
			<forms:button   base="${buttonsbase}" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
			<forms:button   base="${buttonsbase}" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>
  
	<c:if test="${not empty sessionScope.textResourceForm.variables}">
	<br />
	<table cellspacing="2" cellpadding="2" class="normaltext">
	<tr><th colspan="2" align="left"><bean:message key="textResource.variables"/></th></tr>
	<c:forEach var="item" items="${sessionScope.textResourceForm.variables}">
	<tr>
		<td><strong>$!{${item.key}}</strong></td>
		<td>${item.value}</td>
	</tr>
	</c:forEach>
	</table>
	</c:if>  
</body>
</html>