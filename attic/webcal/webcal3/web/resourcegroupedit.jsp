<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
  </head>
  
  <body>
	
  <menu:crumbs value="crumb2" labellength="40">
	  <menu:crumb	crumbid="crumb1"	action="/listResourceGroup.do" title="resourceGroup.resourceGroups"/>
	  <menu:crumb	crumbid="crumb2"	title="resourceGroup.headline"/>
   </menu:crumbs>
   
  <br>  
  
  <misc:initSelectOptions id="userOption" name="userOptions" scope="request" />
  <misc:initSelectOptions id="groupOption" name="groupOptions" scope="request" />
  <misc:initSelectOptions id="resourceOption" name="resourceOptions" scope="request" />  
  
  <html:form action="/editResourceGroup">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <c:set var="label"><bean:message key="resourceGroup.headline" /></c:set>	
	<forms:form type="edit" caption="${label}" formid="frmResourceGroup" width="720" locale="false">
            
    <logic:iterate id="entry" indexId="ix" name="resourceGroupForm" property="entry" type="ch.ess.cal.web.NameEntry">
     <c:set var="label"><bean:message key="resourceGroup.resourceGroupName" /> (${entry.language})</c:set>
  
     <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>		

     <forms:html label="" valign="top" join="false">

	   <ctrl:tabset
        name="tabset"
				id="grouptab"
				width="100%"
				bgcolor="#DADFE0"
				runat="client"
        value="tab1"
				formElement="true">

				<ctrl:tab	tabid="tab1"	title="user.users" tooltip="user.users">          
              <forms:form noframe="true" type="edit" formid="frmResourceGroupUser">                   
                   <forms:swapselect 
                    property="userIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="userOption"/>
                   </forms:swapselect>                      
              </forms:form>           
          
        </ctrl:tab>

				<ctrl:tab	tabid="tab2"	title="group.groups" tooltip="group.groups">
              <forms:form noframe="true" type="edit" formid="frmResourceGroupGroup">                   
                   <forms:swapselect 
                    property="groupIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="groupOption"/>
                   </forms:swapselect>                      
              </forms:form>           
            	
        </ctrl:tab>
        
				<ctrl:tab	tabid="tab3"	title="resource.resources" tooltip="resource.resources">
          
              <forms:form noframe="true" type="edit" formid="frmResourceGroupResource">                   
                   <forms:swapselect 
                    property="resourceIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="resourceOption"/>
                   </forms:swapselect>                      
              </forms:form>             
            	
        </ctrl:tab>        
        
			</ctrl:tabset>
     
    </forms:html>   
		  		    				      		  		
      						
	<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
						
      <c:if test="${not empty resourceGroupForm.modelId}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
	</forms:buttonsection>
  
  
	</forms:form>
  </html:form>	
  <misc:confirm key="resourceGroup.delete"/>
</body>
</html>