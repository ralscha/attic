<%@ include file="include/taglibs.jspf"%>

<html>
  <head>
    <title></title>
  </head>
  <body>
	
  <menu:crumbs value="crumb2" labellength="40">
	    <menu:crumb	crumbid="crumb1"	action="/listResource.do" title="resource.resources"/>
	    <menu:crumb	crumbid="crumb2"	title="resource.headline"/>
  </menu:crumbs>   
  <br>  
  
  <misc:initSelectOptions id="resourceGroupOption" name="resourceGroupOptions" scope="request" />
	<html:form action="/editResource">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <c:set var="label"><bean:message key="resource.headline" /></c:set>	  
	<forms:form type="edit" caption="${label}" formid="frmResource" width="720" locale="false">
            
    <logic:iterate id="entry" indexId="ix" name="resourceForm" property="entry" type="ch.ess.cal.web.NameEntry">
     <c:set var="label"><bean:message key="resource.resourceName" /> (${entry.language})</c:set>
  
    <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>							        
                    
    <c:set var="label"><bean:message key="resourceGroup" /></c:set>	                      
    <forms:select  label="${label}"  property="resourceGroupId"  required="true" >		    
		  <base:options  empty="empty" name="resourceGroupOption"/>
		</forms:select>    
    						
		<forms:buttonsection join="false">
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
						
      <c:if test="${not empty resourceForm.modelId}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="resource.delete"/>
</body>
</html>