<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
  </head>

  <body>
	
  <menu:crumbs value="crumb2" labellength="40">
	  <menu:crumb	crumbid="crumb1"	action="/listGroup.do" title="group.groups"/>
	  <menu:crumb	crumbid="crumb2"	title="group.headline"/>
   </menu:crumbs>
   
  <br>
    
  <misc:initSelectOptions id="userOption" name="userOptions" scope="request" />
  <misc:initSelectOptions id="resourceGroupOption" name="resourceGroupOptions" scope="request" />  
  <c:set var="buttonsbase"><bean:message key="buttons.src" /></c:set>
  
	<html:form action="/editGroup">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <c:set var="label"><bean:message key="group.headline" /></c:set>						          
	<forms:form type="edit" caption="${label}" formid="frmGroup" width="720" locale="false">

    <logic:iterate id="entry" indexId="ix" name="groupForm" property="entry" type="ch.ess.cal.web.NameEntry">
     <c:set var="label"><bean:message key="group.groupName" /> (${entry.language})</c:set>
  
     <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>	
        
    <forms:html label="" valign="top" join="false">
                 
        <c:set var="useremail"><bean:message key="user.eMail" /></c:set>  
        <c:set var="userusers"><bean:message key="user.users" /></c:set>  
        <c:set var="groupaccessuser"><bean:message key="group.accessUser" /></c:set>  
                       
	   <ctrl:tabset
            name="tabset"
				id="grouptab"
				width="100%"
				bgcolor="#DADFE0"
				runat="client"
            value="tab1"
				formElement="true">

        <ctrl:tab tabid="tab1"  title="${useremail}" tooltip="user.eMail">   
          <forms:form noframe="true" type="edit" formid="frmGroupMail" locale="true">       
          <forms:html>
          		<ctrl:list id="emailList" 
          			 property="emailList"     			 
          			 rows="${sessionScope.noRows}"  
                 title="user.eMail"	              		
                 formElement="true">
          		<ctrl:columntext title="user.eMail" property="email" width="400" maxlength="50" sortable="true"/>
              <ctrl:columncheck title="user.eMail.default" property="default"/>
          		<ctrl:columndelete tooltip="common.delete"/>		
          	  </ctrl:list>
          </forms:html>

          <forms:row>
            <forms:html>
        			  <forms:text property="addEmail" required="false" size="40" maxlength="255"/>
			        <ctrl:button base="buttons.src" name="btnAdd"  src="btnAdd1.gif"  title="button.title.add"/>                
            </forms:html>
          </forms:row>
          </forms:form>
        </ctrl:tab>

				<ctrl:tab	tabid="tab2"	title="${userusers}"	tooltip="user.users">
              <forms:form noframe="true" type="edit" formid="frmGroupUsers" locale="true">                   
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

				<ctrl:tab	tabid="tab3"	title="${groupaccessuser}"	tooltip="group.accessUser">
              <forms:form noframe="true" type="edit" formid="frmGroupAccessUsers" locale="true">                   
                   <forms:swapselect 
                    property="accessUserIds"              
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
           <% /* %>
				<ctrl:tab	tabid="tab4"	title="resourceGroup.resourceGroups"	tooltip="resourceGroup.resourceGroups"> 
              <forms:form noframe="true" type="edit" formid="frmGroupResourceGroup">                   
                   <forms:swapselect 
                    property="resourceGroupIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="resourceGroupOption"/>
                   </forms:swapselect>                      
              </forms:form>                	
            </ctrl:tab>        
         <% */ %>
			</ctrl:tabset>
		  		    	
    </forms:html>   
                		                            			
		<forms:buttonsection join="false"> 
			<forms:button   base="${buttonsbase}" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="${buttonsbase}" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>			
						
      <c:if test="${not empty groupForm.modelId}">
			<forms:button onclick="return confirmRequest('');" base="${buttonsbase}" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="${buttonsbase}" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
          
	</forms:form>
  </html:form>	
  <misc:confirm key="group.delete"/>
  
</body>
</html>